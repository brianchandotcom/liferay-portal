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

import java.lang.reflect.Method;

import javax.portlet.PortletMode;

import javax.xml.namespace.QName;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Neil Griffin
 */
@ProviderType
public abstract class BeanPortletMethodWrapper implements BeanPortletMethod {

	public BeanPortletMethodWrapper(BeanPortletMethod beanPortletMethod) {
		_beanPortletMethod = beanPortletMethod;
	}

	@Override
	public int compareTo(BeanPortletMethod beanPortletMethod) {
		return _beanPortletMethod.compareTo(beanPortletMethod);
	}

	@Override
	public String getActionName() {
		return _beanPortletMethod.getActionName();
	}

	@Override
	public Class<?> getBeanType() {
		return _beanPortletMethod.getBeanType();
	}

	@Override
	public Method getMethod() {
		return _beanPortletMethod.getMethod();
	}

	@Override
	public BeanPortletMethodType getMethodType() {
		return _beanPortletMethod.getMethodType();
	}

	@Override
	public int getOrdinal() {
		return _beanPortletMethod.getOrdinal();
	}

	@Override
	public PortletMode getPortletMode() {
		return _beanPortletMethod.getPortletMode();
	}

	@Override
	public String getResourceID() {
		return _beanPortletMethod.getResourceID();
	}

	public BeanPortletMethod getWrapped() {
		return _beanPortletMethod;
	}

	@Override
	public Object invoke(Object... args) throws ReflectiveOperationException {
		return _beanPortletMethod.invoke(args);
	}

	@Override
	public boolean isEventProcessor(QName qName) {
		return _beanPortletMethod.isEventProcessor(qName);
	}

	private final BeanPortletMethod _beanPortletMethod;

}