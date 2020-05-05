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
public interface BeanPortletMethod extends Comparable<BeanPortletMethod> {

	public String getActionName();

	public Class<?> getBeanClass();

	public BeanPortletMethodType getBeanPortletMethodType();

	public Method getMethod();

	public int getOrdinal();

	public PortletMode getPortletMode();

	public String getResourceID();

	public Object invoke(Object... arguments)
		throws ReflectiveOperationException;

	public boolean isEventProcessor(QName qName);

}