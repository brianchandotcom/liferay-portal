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

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.configuration.configurator.ServiceConfigurator;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import javax.sql.DataSource;

import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;
import org.apache.felix.utils.extender.AbstractExtender;
import org.apache.felix.utils.extender.Extension;
import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 */
@Component(immediate = true)
public class ModuleApplicationContextExtender extends AbstractExtender {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		setSynchronous(true);

		_bundleContext = bundleContext;

		_dependencyManager = new DependencyManager(bundleContext);
		_logger = new Logger(bundleContext);

		start(bundleContext);
	}

	@Deactivate
	protected void deactivate() throws Exception {
		stop(_bundleContext);

		_bundleContext = null;
	}

	@Override
	protected void debug(Bundle bundle, String s) {
		_logger.log(Logger.LOG_DEBUG, "[" + bundle + "] " + s);
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) throws Exception {
		Dictionary<String, String> headers = bundle.getHeaders();

		if (headers.get("Spring-Context") == null) {
			return null;
		}

		return new ModuleApplicationContextExtension(bundle);
	}

	@Override
	protected void error(String s, Throwable throwable) {
		_logger.log(Logger.LOG_ERROR, s, throwable);
	}

	@Reference(target = "(&(bean.id=liferayDataSource)(original.bean=true))")
	protected void setDataSource(DataSource dataSource) {
	}

	@Reference(target = "(original.bean=true)")
	protected void setInfrastructureUtil(
		InfrastructureUtil infrastructureUtil) {
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(target = "(original.bean=true)")
	protected void setSaxReaderUtil(SAXReaderUtil saxReaderUtil) {
	}

	@Reference(target = "(original.bean=true)")
	protected void setServiceConfigurator(
		ServiceConfigurator serviceConfigurator) {

		_serviceConfigurator = serviceConfigurator;
	}

	@Override
	protected void warn(Bundle bundle, String s, Throwable throwable) {
		_logger.log(Logger.LOG_DEBUG, "[" + bundle + "] " + s);
	}

	private BundleContext _bundleContext;
	private DependencyManager _dependencyManager;
	private Logger _logger;
	private ServiceConfigurator _serviceConfigurator;

	private class ModuleApplicationContextExtension implements Extension {

		public ModuleApplicationContextExtension(Bundle bundle) {
			_bundle = bundle;
		}

		@Override
		public void destroy() throws Exception {
			if (_component != null) {
				_dependencyManager.remove(_component);
			}
		}

		@Override
		public void start() throws Exception {
			_component = _dependencyManager.createComponent();

			_component.setImplementation(
				new ModuleApplicationContextRegistrator(
					_bundle, _bundleContext.getBundle(), _serviceConfigurator));

			BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

			ClassLoader classLoader = bundleWiring.getClassLoader();

			List<ContextDependency> contextDependencies =
				_processServiceReferences(_bundle);

			for (ContextDependency contextDependency : contextDependencies) {
				ServiceDependency serviceDependency =
					_dependencyManager.createServiceDependency();

				serviceDependency.setRequired(true);

				Class<?> serviceClass = Class.forName(
					contextDependency.getServiceClassName(), false,
					classLoader);

				serviceDependency.setService(
					serviceClass, contextDependency.getFilterString());

				_component.add(serviceDependency);
			}

			_dependencyManager.add(_component);
		}

		private List<ContextDependency> _processServiceReferences(Bundle bundle)
			throws IOException {

			List<ContextDependency> contextDependencies = new ArrayList<>();

			URL url = bundle.getEntry("OSGI-INF/context/context.dependencies");

			if (url == null) {
				return contextDependencies;
			}

			List<String> lines = new ArrayList<>();

			StringUtil.readLines(url.openStream(), lines);

			for (String line : lines) {
				line = line.trim();

				String[] array = line.split(" ");

				String filterString = "";

				if (array.length > 1) {
					filterString = array[1];
				}

				contextDependencies.add(
					new ContextDependency(array[0], filterString));
			}

			return contextDependencies;
		}

		private final Bundle _bundle;
		private org.apache.felix.dm.Component _component;

		private class ContextDependency {

			public ContextDependency(
				String serviceClassName, String filterString) {

				this.serviceClassName = serviceClassName;

				if (!filterString.equals("")) {
					this.filterString = filterString;
				}
			}

			public String getFilterString() {
				return filterString;
			}

			public String getServiceClassName() {
				return serviceClassName;
			}

			protected String filterString;
			protected final String serviceClassName;

		}

	}

}