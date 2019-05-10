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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;
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

	public static void registerBeanFilter(
		List<ServiceRegistration<?>> registrations, BundleContext bundleContext,
		String portletName, Set<String> allPortletNames, BeanFilter beanFilter,
		BeanManager beanManager, ServletContext servletContext) {

		if (Objects.equals(portletName, "*")) {
			for (String curPortletName : allPortletNames) {
				String portletId = _getPortletId(
					curPortletName, servletContext.getServletContextName());

				registrations.add(
					_registerBeanFilter(
						bundleContext, portletId, beanFilter, beanManager));
			}
		}
		else {
			if (!allPortletNames.contains(portletName)) {
				_log.error(
					StringBundler.concat(
						"Unable to register filter ",
						beanFilter.getFilterName(), " for nonexistent portlet ",
						portletName));
			}
			else {
				String portletId = _getPortletId(
					portletName, servletContext.getServletContextName());

				registrations.add(
					_registerBeanFilter(
						bundleContext, portletId, beanFilter, beanManager));
			}
		}

		@SuppressWarnings("unchecked")
		List<String> beanFilterNames =
			(List<String>)servletContext.getAttribute(
				WebKeys.BEAN_FILTER_NAMES);

		if (beanFilterNames == null) {
			beanFilterNames = new ArrayList<>();

			servletContext.setAttribute(
				WebKeys.BEAN_FILTER_NAMES, beanFilterNames);
		}

		beanFilterNames.add(beanFilter.getFilterName());
	}

	public static ServiceRegistration<Portlet> registerBeanPortlet(
		BundleContext bundleContext, BeanApp beanApp, BeanPortlet beanPortlet,
		ServletContext servletContext, List<String> beanPortletIds) {

		try {
			String portletId = _getPortletId(
				beanPortlet.getPortletName(),
				servletContext.getServletContextName());

			if (_log.isDebugEnabled()) {
				_log.debug("Registering bean portlet " + portletId);
			}

			Dictionary<String, Object> dictionary = beanPortlet.toDictionary(
				beanApp);

			dictionary.put("javax.portlet.name", portletId);

			ServiceRegistration<Portlet> portletServiceRegistration =
				bundleContext.registerService(
					Portlet.class,
					new BeanPortletInvokerPortlet(beanPortlet.getBeanMethods()),
					dictionary);

			beanPortletIds.add(portletId);

			return portletServiceRegistration;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	public static ServiceRegistration<ResourceBundleLoader>
		registerResourceBundleLoader(
			BundleContext bundleContext, BeanPortlet beanPortlet,
			ServletContext servletContext) {

		String resourceBundle = beanPortlet.getResourceBundle();

		if (Validator.isNull(resourceBundle)) {
			return null;
		}

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleUtil.getResourceBundleLoader(
				resourceBundle, servletContext.getClassLoader());

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("resource.bundle.base.name", resourceBundle);
		properties.put("service.ranking", Integer.MIN_VALUE);
		properties.put(
			"servlet.context.name", servletContext.getServletContextName());

		return bundleContext.registerService(
			ResourceBundleLoader.class, resourceBundleLoader, properties);
	}

	private static String _getPortletId(
		String portletName, String servletContextName) {

		if (Validator.isNotNull(servletContextName)) {
			portletName = portletName.concat(
				PortletConstants.WAR_SEPARATOR
			).concat(
				servletContextName
			);
		}

		return PortalUtil.getJsSafePortletId(portletName);
	}

	private static ServiceRegistration<PortletFilter> _registerBeanFilter(
		BundleContext bundleContext, String portletId, BeanFilter beanFilter,
		BeanManager beanManager) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Registering bean filter ", beanFilter.getFilterName(),
					" for portlet ", portletId));
		}

		Dictionary<String, Object> dictionary = beanFilter.toDictionary();

		dictionary.put("javax.portlet.name", portletId);

		return bundleContext.registerService(
			PortletFilter.class,
			new BeanFilterInvokerPortletFilter(
				beanFilter.getFilterClass(), beanManager),
			dictionary);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RegistrationUtil.class);

}