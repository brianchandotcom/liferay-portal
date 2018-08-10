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

import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.List;
import java.util.Set;

import javax.enterprise.inject.spi.BeanManager;

import javax.portlet.Portlet;
import javax.portlet.filter.PortletFilter;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Neil Griffin
 */
public class RegistrationUtil {

	public static List<ServiceRegistration<PortletFilter>> registerBeanFilter(
		BundleContext bundleContext, String portletName,
		Set<String> allPortletNames, BeanFilter beanFilter,
		BeanManager beanManager, ServletContext servletContext) {

		// TODO

		return null;
	}

	public static ServiceRegistration<Portlet> registerBeanPortlet(
		BundleContext bundleContext, BeanPortlet beanPortlet,
		ServletContext servletContext) {

		// TODO

		return null;
	}

	public static ServiceRegistration<ResourceBundleLoader>
		registerResourceBundleLoader(
			BundleContext bundleContext, BeanPortlet beanPortlet,
			ServletContext servletContext) {

		// TODO

		return null;
	}

}