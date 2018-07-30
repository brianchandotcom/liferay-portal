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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import javax.portlet.PortletMode;
import javax.portlet.ProcessAction;
import javax.portlet.RenderMode;
import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.HeaderMethod;
import javax.portlet.annotations.PortletQName;
import javax.portlet.annotations.RenderMethod;
import javax.portlet.annotations.ServeResourceMethod;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public class BeanMethod {

	public BeanMethod(
		BeanManager beanManager, MethodType type, Class<?> beanClass,
		Method method, int ordinal) {

		_beanManager = beanManager;
		_type = type;
		_beanClass = beanClass;
		_method = method;

		Set<Bean<?>> beans = beanManager.getBeans(beanClass);

		_bean = beanManager.resolve(beans);

		_ordinal = ordinal;
	}

	public String getActionName() {
		ActionMethod actionMethod = _method.getAnnotation(ActionMethod.class);

		if (actionMethod != null) {
			String actionName = actionMethod.actionName();

			if (actionName != null) {
				return actionName;
			}
		}

		ProcessAction processAction = _method.getAnnotation(
			ProcessAction.class);

		if (processAction == null) {
			return null;
		}

		return processAction.name();
	}

	public Class<?> getBeanClass() {
		return _beanClass;
	}

	public String getInclude() {
		if (_type == MethodType.HEADER) {
			HeaderMethod headerMethod = _method.getAnnotation(
				HeaderMethod.class);

			if (headerMethod != null) {
				return headerMethod.include();
			}
		}
		else if (_type == MethodType.RENDER) {
			RenderMethod renderMethod = _method.getAnnotation(
				RenderMethod.class);

			if (renderMethod != null) {
				return renderMethod.include();
			}
		}
		else if (_type == MethodType.SERVE_RESOURCE) {
			ServeResourceMethod serveResourceMethod = _method.getAnnotation(
				ServeResourceMethod.class);

			if (serveResourceMethod != null) {
				return serveResourceMethod.include();
			}
		}

		return null;
	}

	public Method getMethod() {
		return _method;
	}

	public int getOrdinal() {
		return _ordinal;
	}

	public int getParameterCount() {
		return _method.getParameterCount();
	}

	public PortletMode getPortletMode() {
		RenderMethod renderMethod = _method.getAnnotation(RenderMethod.class);

		if (renderMethod != null) {
			String portletMode = renderMethod.portletMode();

			if (portletMode != null) {
				return new PortletMode(portletMode);
			}
		}

		RenderMode renderMode = _method.getAnnotation(RenderMode.class);

		if (renderMode == null) {
			return null;
		}

		return new PortletMode(renderMode.name());
	}

	public MethodType getType() {
		return _type;
	}

	public Object invoke(Object... args) throws IllegalAccessException,
		InvocationTargetException {

		Object beanInstance = _beanManager.getReference(
			_bean, _bean.getBeanClass(),
			_beanManager.createCreationalContext(_bean));

		try {
			return _method.invoke(beanInstance, args);
		}
		catch (IllegalArgumentException iae) {
			throw new InvocationTargetException(iae);
		}
	}

	public boolean isEventProcessor(QName qName) {
		EventMethod eventMethod = _method.getAnnotation(EventMethod.class);

		if (eventMethod != null) {
			PortletQName[] portletQNames = eventMethod.processingEvents();

			for (PortletQName portletQName : portletQNames) {
				String namespaceURI = portletQName.namespaceURI();

				String localPart = portletQName.localPart();

				if (localPart.equals(qName.getLocalPart()) &&
					namespaceURI.equals(qName.getNamespaceURI())) {

					return true;
				}
			}
		}

		return false;
	}

	private final Bean<?> _bean;
	private final Class<?> _beanClass;
	private final BeanManager _beanManager;
	private final Method _method;
	private final int _ordinal;
	private MethodType _type;

}