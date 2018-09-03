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

import com.liferay.portal.kernel.portlet.LiferayPortletMode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
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

		if (methodType == MethodType.ACTION) {
			if (_actionMethods.isEmpty()) {
				_actionMethods = new ArrayList<>();
			}

			_actionMethods.add(beanMethod);
		}
		else if (methodType == MethodType.DESTROY) {
			if (_destroyMethods.isEmpty()) {
				_destroyMethods = new ArrayList<>();
			}

			_destroyMethods.add(beanMethod);
		}
		else if (methodType == MethodType.EVENT) {
			if (_eventMethods.isEmpty()) {
				_eventMethods = new ArrayList<>();
			}

			_eventMethods.add(beanMethod);
		}
		else if (methodType == MethodType.HEADER) {
			if (_headerMethods.isEmpty()) {
				_headerMethods = new ArrayList<>();
			}

			_headerMethods.add(beanMethod);
		}
		else if (methodType == MethodType.INIT) {
			if (_initMethods.isEmpty()) {
				_initMethods = new ArrayList<>();
			}

			_initMethods.add(beanMethod);
		}
		else if (methodType == MethodType.RENDER) {
			if (_renderMethods.isEmpty()) {
				_renderMethods = new ArrayList<>();
			}

			_renderMethods.add(beanMethod);
		}
		else {
			if (_serveResourceMethods.isEmpty()) {
				_serveResourceMethods = new ArrayList<>();
			}

			_serveResourceMethods.add(beanMethod);
		}
	}

	@Override
	public void addLiferayConfiguration(
		Map<String, String> liferayConfiguration) {

		if (_liferayConfiguration.isEmpty()) {
			_liferayConfiguration = new HashMap<>();
		}

		_liferayConfiguration.putAll(liferayConfiguration);
	}

	@Override
	public void addLiferayConfiguration(String name, String value) {
		_liferayConfiguration.put(name, value);
	}

	@Override
	public void addPortletDependency(PortletDependency portletDependency) {
		if (_resourceDependencies.isEmpty()) {
			_resourceDependencies = new ArrayList<>();
		}

		_resourceDependencies.add(portletDependency);
	}

	@Override
	public BeanApp getBeanApp() {
		return _beanApp;
	}

	@Override
	public List<BeanMethod> getBeanMethods(MethodType methodType) {
		if (methodType == MethodType.ACTION) {
			return _actionMethods;
		}
		else if (methodType == MethodType.DESTROY) {
			return _destroyMethods;
		}
		else if (methodType == MethodType.EVENT) {
			return _eventMethods;
		}
		else if (methodType == MethodType.HEADER) {
			return _headerMethods;
		}
		else if (methodType == MethodType.INIT) {
			return _initMethods;
		}
		else if (methodType == MethodType.RENDER) {
			return _renderMethods;
		}
		else {
			return _serveResourceMethods;
		}
	}

	@Override
	public Dictionary<String, Object> toDictionary(String portletId) {
		PortletDictionary portletDictionary = new PortletDictionary();

		portletDictionary.putIfNotNull(
			"javax.portlet.default-namespace", _beanApp.getDefaultNamespace());

		List<String> portletDependencies = new ArrayList<>();

		for (PortletDependency portletDependency : getPortletDependencies()) {
			String toString = portletDependency.toString();

			if (toString != null) {
				portletDependencies.add(toString);
			}
		}

		portletDictionary.putIfNotEmpty(
			"javax.portlet.dependency", portletDependencies);

		List<String> urlGenerationListeners = new ArrayList<>();

		for (URLGenerationListener urlGenerationListener :
				_beanApp.getURLGenerationListeners()) {

			String toString = urlGenerationListener.toString();

			if (toString != null) {
				urlGenerationListeners.add(toString);
			}
		}

		portletDictionary.putIfNotEmpty(
			"javax.portlet.listener", urlGenerationListeners);

		portletDictionary.putIfNotNull("javax.portlet.name", portletId);

		for (BeanMethod beanMethod : getBeanMethods(MethodType.ACTION)) {
			Set<String> supportedPublishingEvents = (Set<String>)
				portletDictionary.get(
					"javax.portlet.supported-publishing-event");

			if (supportedPublishingEvents == null) {
				supportedPublishingEvents = new HashSet<>();
			}

			Method beanActionMethod = beanMethod.getMethod();

			ActionMethod actionMethod = beanActionMethod.getAnnotation(
				ActionMethod.class);

			if (actionMethod != null) {
				for (PortletQName portletQName :
						actionMethod.publishingEvents()) {

					supportedPublishingEvents.add(
						PortletDictionaryUtil.formatNameValuePair(
							portletQName.localPart(),
							portletQName.namespaceURI()));
				}
			}

			portletDictionary.putIfNotEmpty(
				"javax.portlet.supported-publishing-event",
				supportedPublishingEvents);
		}

		for (BeanMethod beanMethod : getBeanMethods(MethodType.EVENT)) {
			Set<String> supportedPublishingEvents = (Set<String>)
				portletDictionary.get(
					"javax.portlet.supported-publishing-event");

			if (supportedPublishingEvents == null) {
				supportedPublishingEvents = new HashSet<>();
			}

			Method beanEventMethod = beanMethod.getMethod();

			EventMethod eventMethod = beanEventMethod.getAnnotation(
				EventMethod.class);

			if (eventMethod != null) {
				for (PortletQName portletQName :
						eventMethod.publishingEvents()) {

					supportedPublishingEvents.add(
						PortletDictionaryUtil.formatNameValuePair(
							portletQName.localPart(),
							portletQName.namespaceURI()));
				}
			}

			portletDictionary.putIfNotEmpty(
				"javax.portlet.supported-publishing-event",
				supportedPublishingEvents);

			Set<String> supportedProcessingEvents = (Set<String>)
				portletDictionary.get(
					"javax.portlet.supported-processing-event");

			if (supportedProcessingEvents == null) {
				supportedProcessingEvents = new HashSet<>();
			}

			if (eventMethod != null) {
				for (PortletQName portletQName :
						eventMethod.processingEvents()) {

					supportedProcessingEvents.add(
						PortletDictionaryUtil.formatNameValuePair(
							portletQName.localPart(),
							portletQName.namespaceURI()));
				}
			}

			portletDictionary.putIfNotEmpty(
				"javax.portlet.supported-processing-event",
				supportedProcessingEvents);
		}

		portletDictionary.put(
			"javax.portlet.version", _beanApp.getSpecVersion());

		return portletDictionary;
	}

	protected Map<String, String> getLiferayConfiguration() {
		return _liferayConfiguration;
	}

	protected Set<String> getLiferayPortletModes() {
		return _liferayPortletModes;
	}

	protected List<PortletDependency> getPortletDependencies() {
		return _resourceDependencies;
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

	private List<BeanMethod> _actionMethods = Collections.emptyList();
	private final BeanApp _beanApp;
	private List<BeanMethod> _destroyMethods = Collections.emptyList();
	private List<BeanMethod> _eventMethods = Collections.emptyList();
	private List<BeanMethod> _headerMethods = Collections.emptyList();
	private List<BeanMethod> _initMethods = Collections.emptyList();
	private Map<String, String> _liferayConfiguration = Collections.emptyMap();
	private List<BeanMethod> _renderMethods = Collections.emptyList();
	private List<PortletDependency> _resourceDependencies =
		Collections.emptyList();
	private List<BeanMethod> _serveResourceMethods = Collections.emptyList();

}