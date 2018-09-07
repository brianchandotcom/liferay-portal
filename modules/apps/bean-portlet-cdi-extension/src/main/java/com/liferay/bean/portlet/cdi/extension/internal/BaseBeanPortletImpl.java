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
import java.util.Dictionary;
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

	public BaseBeanPortletImpl(BeanApp beanApp) {
		_beanApp = beanApp;
	}

	@Override
	public void addBeanMethod(BeanMethod beanMethod) {
		MethodType methodType = beanMethod.getType();

		_beanMethods.compute(
			methodType,
			(keyMethodType, beanMethods) -> {
				if (beanMethods == null) {
					beanMethods = new ArrayList<>();
				}

				beanMethods.add(beanMethod);

				return beanMethods;
			});
	}

	@Override
	public void addLiferayConfiguration(
		Map<String, String> liferayConfiguration) {

		_liferayConfiguration.putAll(liferayConfiguration);
	}

	@Override
	public void addLiferayConfiguration(String name, String value) {
		_liferayConfiguration.put(name, value);
	}

	@Override
	public void addPortletDependency(PortletDependency portletDependency) {
		_resourceDependencies.add(portletDependency);
	}

	@Override
	public BeanApp getBeanApp() {
		return _beanApp;
	}

	@Override
	public List<BeanMethod> getBeanMethods(MethodType methodType) {
		return _beanMethods.getOrDefault(methodType, Collections.emptyList());
	}

	@Override
	public Dictionary<String, Object> toDictionary(String portletId) {
		HashMapDictionary<String, Object> dictionary =
			new HashMapDictionary<>();

		String defaultNamespace = _beanApp.getDefaultNamespace();

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
				_beanApp.getURLGenerationListeners()) {

			String toString = urlGenerationListener.toString();

			if (toString != null) {
				urlGenerationListeners.add(toString);
			}
		}

		if (!urlGenerationListeners.isEmpty()) {
			dictionary.put("javax.portlet.listener", urlGenerationListeners);
		}

		if (portletId != null) {
			dictionary.put("javax.portlet.name", portletId);
		}

		Set<String> supportedPublishingEvents = new HashSet<>();

		for (BeanMethod beanMethod : getBeanMethods(MethodType.ACTION)) {
			Method beanActionMethod = beanMethod.getMethod();

			ActionMethod actionMethod = beanActionMethod.getAnnotation(
				ActionMethod.class);

			if (actionMethod == null) {
				continue;
			}

			for (PortletQName portletQName : actionMethod.publishingEvents()) {
				supportedPublishingEvents.add(
					toNameValuePair(
						portletQName.localPart(), portletQName.namespaceURI()));
			}
		}

		Set<String> supportedProcessingEvents = new HashSet<>();

		for (BeanMethod beanMethod : getBeanMethods(MethodType.EVENT)) {
			Method beanEventMethod = beanMethod.getMethod();

			EventMethod eventMethod = beanEventMethod.getAnnotation(
				EventMethod.class);

			if (eventMethod == null) {
				continue;
			}

			for (PortletQName portletQName : eventMethod.publishingEvents()) {
				supportedPublishingEvents.add(
					toNameValuePair(
						portletQName.localPart(), portletQName.namespaceURI()));
			}

			for (PortletQName portletQName : eventMethod.processingEvents()) {
				supportedProcessingEvents.add(
					toNameValuePair(
						portletQName.localPart(), portletQName.namespaceURI()));
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

		dictionary.put("javax.portlet.version", _beanApp.getSpecVersion());

		dictionary.putAll(_liferayConfiguration);

		return dictionary;
	}

	protected static String toNameValuePair(String name, String value) {
		if (Validator.isNull(value)) {
			return name;
		}

		return name.concat(StringPool.SEMICOLON).concat(value);
	}

	protected String getPublicRenderParameterNamespaceURI(String id) {
		Map<String, PublicRenderParameter> publicRenderParameterMap =
			_beanApp.getPublicRenderParameterMap();

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

	private static final Set<String> _liferayPortletModes = new HashSet<>();

	static {
		try {
			for (Field field : LiferayPortletMode.class.getFields()) {
				if (Modifier.isStatic(field.getModifiers()) &&
					(field.getType() == PortletMode.class)) {

					PortletMode portletMode = (PortletMode)field.get(null);

					_liferayPortletModes.add(portletMode.toString());
				}
			}
		}
		catch (IllegalAccessException iae) {
			throw new ExceptionInInitializerError(iae);
		}
	}

	private final BeanApp _beanApp;
	private final EnumMap<MethodType, List<BeanMethod>> _beanMethods =
		new EnumMap<>(MethodType.class);
	private final Map<String, String> _liferayConfiguration = new HashMap<>();
	private final List<PortletDependency> _resourceDependencies =
		new ArrayList<>();

}