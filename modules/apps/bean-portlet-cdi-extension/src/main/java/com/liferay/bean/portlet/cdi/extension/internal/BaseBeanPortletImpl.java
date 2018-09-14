/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.bean.portlet.cdi.extension.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.PortletQName;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public abstract class BaseBeanPortletImpl implements BeanPortlet {

	@Override
	public void addBeanMethod(BeanMethod beanMethod) {
		MethodType methodType = beanMethod.getType();

		_beanMethods.compute(
			methodType,
			(keyMethodType, beanMethods) -> {
				if (beanMethods == null) {
					beanMethods = new ArrayList<>();
				}

				if ((methodType == MethodType.HEADER) ||
					(methodType == MethodType.RENDER) ||
					(methodType == MethodType.SERVE_RESOURCE)) {

					int index = Collections.binarySearch(
						beanMethods, beanMethod,
						Comparator.comparingInt(BeanMethod::getOrdinal));

					if (index < 0) {
						index = -index - 1;
					}

					beanMethods.add(index, beanMethod);
				}
				else {
					beanMethods.add(beanMethod);
				}

				return beanMethods;
			});
	}

	@Override
	public void addLiferayConfiguration(
		Map<String, String> liferayConfiguration) {

		_liferayConfiguration.putAll(liferayConfiguration);
	}

	@Override
	public void addPortletDependency(PortletDependency portletDependency) {
		_resourceDependencies.add(portletDependency);
	}

	@Override
	public Map<MethodType, List<BeanMethod>> getBeanMethods() {
		return _beanMethods;
	}

	protected static String toNameValuePair(String name, String value) {
		if (Validator.isNull(value)) {
			return name;
		}

		return name.concat(StringPool.SEMICOLON).concat(value);
	}

	protected String getPublicRenderParameterNamespaceURI(
		BeanApp beanApp, String id) {

		Map<String, PublicRenderParameter> publicRenderParameterMap =
			beanApp.getPublicRenderParameterMap();

		PublicRenderParameter publicRenderParameter =
			publicRenderParameterMap.get(id);

		if (publicRenderParameter == null) {
			return XMLConstants.NULL_NS_URI;
		}

		QName qName = publicRenderParameter.getQName();

		if (qName == null) {
			return XMLConstants.NULL_NS_URI;
		}

		String namespaceURI = qName.getNamespaceURI();

		if (namespaceURI == null) {
			return XMLConstants.NULL_NS_URI;
		}

		return namespaceURI;
	}

	protected HashMapDictionary<String, Object> toDictionary(BeanApp beanApp) {
		HashMapDictionary<String, Object> dictionary =
			new HashMapDictionary<>();

		String displayCategory = getDisplayCategory();

		if (displayCategory != null) {
			dictionary.put(
				"com.liferay.portlet.display-category", displayCategory);
		}

		String defaultNamespace = beanApp.getDefaultNamespace();

		if (defaultNamespace != null) {
			dictionary.put("javax.portlet.default-namespace", defaultNamespace);
		}

		if (!_resourceDependencies.isEmpty()) {
			List<String> portletDependencies = new ArrayList<>();

			for (PortletDependency portletDependency : _resourceDependencies) {
				portletDependencies.add(portletDependency.toString());
			}

			dictionary.put("javax.portlet.dependency", portletDependencies);
		}

		List<String> urlGenerationListeners = new ArrayList<>();

		for (URLGenerationListener urlGenerationListener :
				beanApp.getURLGenerationListeners()) {

			String toString = urlGenerationListener.toString();

			if (toString != null) {
				urlGenerationListeners.add(toString);
			}
		}

		if (!urlGenerationListeners.isEmpty()) {
			dictionary.put("javax.portlet.listener", urlGenerationListeners);
		}

		Set<String> supportedPublishingEvents = new HashSet<>();

		List<BeanMethod> actionBeanMethods = _beanMethods.get(
			MethodType.ACTION);

		if (actionBeanMethods != null) {
			for (BeanMethod beanMethod : actionBeanMethods) {
				Method beanActionMethod = beanMethod.getMethod();

				ActionMethod actionMethod = beanActionMethod.getAnnotation(
					ActionMethod.class);

				if (actionMethod == null) {
					continue;
				}

				for (PortletQName portletQName :
						actionMethod.publishingEvents()) {

					supportedPublishingEvents.add(
						toNameValuePair(
							portletQName.localPart(),
							portletQName.namespaceURI()));
				}
			}
		}

		Set<String> supportedProcessingEvents = new HashSet<>();

		List<BeanMethod> eventBeanMethods = _beanMethods.get(MethodType.EVENT);

		if (eventBeanMethods != null) {
			for (BeanMethod beanMethod : eventBeanMethods) {
				Method beanEventMethod = beanMethod.getMethod();

				EventMethod eventMethod = beanEventMethod.getAnnotation(
					EventMethod.class);

				if (eventMethod == null) {
					continue;
				}

				for (PortletQName portletQName :
						eventMethod.publishingEvents()) {

					supportedPublishingEvents.add(
						toNameValuePair(
							portletQName.localPart(),
							portletQName.namespaceURI()));
				}

				for (PortletQName portletQName :
						eventMethod.processingEvents()) {

					supportedProcessingEvents.add(
						toNameValuePair(
							portletQName.localPart(),
							portletQName.namespaceURI()));
				}
			}
		}

		if (!supportedPublishingEvents.isEmpty()) {
			dictionary.put(
				"javax.portlet.supported-publishing-event",
				supportedPublishingEvents);
		}

		if (!supportedProcessingEvents.isEmpty()) {
			dictionary.put(
				"javax.portlet.supported-processing-event",
				supportedProcessingEvents);
		}

		dictionary.put("javax.portlet.version", beanApp.getSpecVersion());

		dictionary.putAll(_liferayConfiguration);

		return dictionary;
	}

	protected static final Set<String> liferayPortletModes =
		new HashSet<String>() {
			{
				try {
					for (Field field : LiferayPortletMode.class.getFields()) {
						if (Modifier.isStatic(field.getModifiers()) &&
							(field.getType() == PortletMode.class)) {

							PortletMode portletMode = (PortletMode)field.get(
								null);

							add(portletMode.toString());
						}
					}
				}
				catch (IllegalAccessException iae) {
					throw new ExceptionInInitializerError(iae);
				}
			}
		};

	private final EnumMap<MethodType, List<BeanMethod>> _beanMethods =
		new EnumMap<>(MethodType.class);
	private final Map<String, String> _liferayConfiguration = new HashMap<>();
	private final List<PortletDependency> _resourceDependencies =
		new ArrayList<>();

}