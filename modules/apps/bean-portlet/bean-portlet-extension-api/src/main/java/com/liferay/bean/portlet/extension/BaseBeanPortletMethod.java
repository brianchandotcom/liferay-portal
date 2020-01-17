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

package com.liferay.bean.portlet.extension;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.util.Objects;

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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Neil Griffin
 */
@ProviderType
public abstract class BaseBeanPortletMethod implements BeanPortletMethod {

	public BaseBeanPortletMethod(
		BeanPortletMethodType beanPortletMethodType, Method method) {

		_beanPortletMethodType = beanPortletMethodType;
		_method = method;

		_ordinal = beanPortletMethodType.getOrdinal(method);
	}

	@Override
	public int compareTo(BeanPortletMethod beanPortletMethod) {
		return Integer.compare(_ordinal, beanPortletMethod.getOrdinal());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BeanPortletMethod)) {
			return false;
		}

		BeanPortletMethod beanPortletMethod = (BeanPortletMethod)obj;

		if ((_ordinal == beanPortletMethod.getOrdinal()) &&
			Objects.equals(_method, beanPortletMethod.getMethod()) &&
			(_beanPortletMethodType == beanPortletMethod.getMethodType())) {

			return true;
		}

		return false;
	}

	@Override
	public String getActionName() {
		ActionMethod actionMethod = _method.getAnnotation(ActionMethod.class);

		if (actionMethod != null) {
			String actionName = actionMethod.actionName();

			if (Validator.isNotNull(actionName)) {
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

	@Override
	public Method getMethod() {
		return _method;
	}

	@Override
	public BeanPortletMethodType getMethodType() {
		return _beanPortletMethodType;
	}

	@Override
	public int getOrdinal() {
		return _ordinal;
	}

	@Override
	public PortletMode getPortletMode() {
		HeaderMethod headerMethod = _method.getAnnotation(HeaderMethod.class);

		if (headerMethod != null) {
			String portletMode = headerMethod.portletMode();

			if (Validator.isNull(portletMode)) {
				return null;
			}

			return new PortletMode(portletMode);
		}

		RenderMethod renderMethod = _method.getAnnotation(RenderMethod.class);

		if (renderMethod != null) {
			String portletMode = renderMethod.portletMode();

			if (Validator.isNull(portletMode)) {
				return null;
			}

			return new PortletMode(portletMode);
		}

		RenderMode renderMode = _method.getAnnotation(RenderMode.class);

		if (renderMode == null) {
			return null;
		}

		String name = renderMode.name();

		if (Validator.isNull(name)) {
			return null;
		}

		return new PortletMode(name);
	}

	@Override
	public String getResourceID() {
		ServeResourceMethod serveResourceMethod = _method.getAnnotation(
			ServeResourceMethod.class);

		if (serveResourceMethod != null) {
			String resourceID = serveResourceMethod.resourceID();

			if (Validator.isNotNull(resourceID)) {
				return resourceID;
			}
		}

		return null;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _method);

		hashCode = HashUtil.hash(hashCode, _ordinal);

		return HashUtil.hash(hashCode, _beanPortletMethodType);
	}

	@Override
	public boolean isEventProcessor(QName qName) {
		EventMethod eventMethod = _method.getAnnotation(EventMethod.class);

		if (eventMethod == null) {
			return false;
		}

		PortletQName[] portletQNames = eventMethod.processingEvents();

		for (PortletQName portletQName : portletQNames) {
			String localPart = portletQName.localPart();

			if (localPart.equals(qName.getLocalPart())) {
				String namespaceURI = portletQName.namespaceURI();

				if (namespaceURI.equals(qName.getNamespaceURI())) {
					return true;
				}
			}
		}

		return false;
	}

	private final BeanPortletMethodType _beanPortletMethodType;
	private final Method _method;
	private final int _ordinal;

}