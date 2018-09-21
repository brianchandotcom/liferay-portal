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

package com.liferay.portal.spring.extender.internal.context;

import com.liferay.osgi.felix.util.AbstractExtender;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.spring.context.PortletBeanFactoryPostProcessor;
import com.liferay.portal.spring.extender.internal.bean.ApplicationContextServicePublisherUtil;
import com.liferay.portal.spring.extender.internal.bundle.CompositeResourceLoaderBundle;
import com.liferay.portal.spring.extender.internal.classloader.BundleResolverClassLoader;

import java.util.Dictionary;
import java.util.List;

import org.apache.felix.utils.extender.Extension;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class ParentModuleApplicationContextExtender extends AbstractExtender {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		start(bundleContext);
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) throws Exception {
		stop(bundleContext);
	}

	@Override
	protected void debug(Bundle bundle, String s) {
		if (_log.isDebugEnabled()) {
			_log.debug(s);
		}
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) throws Exception {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (headers.get("Liferay-Service") == null) {
			return null;
		}

		return new ParentModuleApplicationContextExtension(bundle);
	}

	@Override
	protected void error(String s, Throwable throwable) {
		_log.error(s, throwable);
	}

	@Reference(target = "(original.bean=true)", unbind = "-")
	protected void setInfrastructureUtil(
		InfrastructureUtil infrastructureUtil) {
	}

	@Override
	protected void warn(Bundle bundle, String s, Throwable throwable) {
		if (_log.isWarnEnabled()) {
			_log.warn(s, throwable);
		}
	}

	private static final String[] _PARENT_CONFIG_LOCATIONS =
		{"META-INF/spring/parent"};

	private static final Log _log = LogFactoryUtil.getLog(
		ParentModuleApplicationContextExtender.class);

	private class ParentModuleApplicationContextExtension implements Extension {

		@Override
		public void destroy() {
			ApplicationContextServicePublisherUtil.unregisterContext(
				_serviceRegistrations);

			ParentModuleApplicationContextHolder.removeApplicationContext(
				_bundle);
		}

		@Override
		public void start() throws Exception {
			BundleContext extenderBundleContext =
				ParentModuleApplicationContextExtender.this.getBundleContext();

			Bundle extenderBundle = extenderBundleContext.getBundle();

			Bundle compositeResourceLoaderBundle =
				new CompositeResourceLoaderBundle(_bundle, extenderBundle);

			ClassLoader classLoader = new BundleResolverClassLoader(
				_bundle, extenderBundle);

			ModuleApplicationContext moduleApplicationContext =
				new ModuleApplicationContext(
					compositeResourceLoaderBundle, classLoader,
					_PARENT_CONFIG_LOCATIONS);

			moduleApplicationContext.addBeanFactoryPostProcessor(
				new PortletBeanFactoryPostProcessor() {

					@Override
					protected ClassLoader getClassLoader() {
						return classLoader;
					}

				});

			moduleApplicationContext.refresh();

			ParentModuleApplicationContextHolder.setApplicationContext(
				_bundle, moduleApplicationContext);

			_serviceRegistrations =
				ApplicationContextServicePublisherUtil.registerContext(
					moduleApplicationContext, _bundle.getBundleContext(), true);
		}

		private ParentModuleApplicationContextExtension(Bundle bundle) {
			_bundle = bundle;
		}

		private final Bundle _bundle;
		private List<ServiceRegistration<?>> _serviceRegistrations;

	}

}