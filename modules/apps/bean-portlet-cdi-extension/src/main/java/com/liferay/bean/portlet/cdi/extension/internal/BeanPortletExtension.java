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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import javax.portlet.annotations.PortletConfiguration;

import javax.servlet.ServletContext;

/**
 * @author Neil Griffin
 * @author Raymond Augé
 */
public class BeanPortletExtension implements Extension {

	public void afterBeanDiscovery(
		@Observes AfterBeanDiscovery afterBeanDiscovery) {

		// TODO

	}

	public void applicationScopedInitialized(
		@Initialized(ApplicationScoped.class) @Observes ServletContext context,
		BeanManager beanManager) {

		// TODO

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Discovered ", _beanPortlets.size(), " bean portlets and ",
					_beanFilters.size(), " bean filters for ",
					context.getServletContextName()));
		}
	}

	public void beforeBeanDiscovery(
		@Observes BeforeBeanDiscovery beforeBeanDiscovery,
		BeanManager beanManager) {

		if (_log.isDebugEnabled()) {
			_log.debug("Scanning for bean portlets and bean filters");
		}

		// TODO

	}

	public <T> void processAnnotatedType(
		@Observes ProcessAnnotatedType<T> processAnnotatedType) {

		// TODO

	}

	protected void addBeanPortlet(
		Class<?> beanPortletClass, PortletConfiguration portletConfiguration) {

		// TODO

	}

	protected void applicationScopedBeforeDestroyed(
		@Destroyed(ApplicationScoped.class) @Observes Object ignore) {

		// TODO

	}

	protected void sessionScopeBeforeDestroyed(
		@Destroyed(SessionScoped.class) @Observes Object httpSessionObject) {

		// TODO

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanPortletExtension.class);

	private final List<BeanFilter> _beanFilters = new ArrayList<>();
	private final Map<String, BeanPortlet> _beanPortlets = new HashMap<>();

}