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

package com.liferay.bean.portlet.registration;

import com.liferay.bean.portlet.extension.BeanFilterMethodFactory;
import com.liferay.bean.portlet.extension.BeanFilterMethodInvoker;
import com.liferay.bean.portlet.extension.BeanPortletMethodFactory;
import com.liferay.bean.portlet.extension.BeanPortletMethodInvoker;

import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Neil Griffin
 */
@ProviderType
public interface BeanPortletRegistrar {

	public void register(
		Set<Class<?>> discoveredClasses, ServletContext servletContext,
		BeanFilterMethodFactory beanFilterMethodFactory,
		BeanFilterMethodInvoker beanFilterMethodInvoker,
		BeanPortletMethodFactory beanPortletMethodFactory,
		BeanPortletMethodInvoker beanPortletMethodInvoker);

	public void unregister(ServletContext servletContext);

}