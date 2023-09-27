/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.extender.internal.context;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.configurator.ConfigurableApplicationContextConfigurator;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;

import java.util.Dictionary;

import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Miguel Pastor
 */
@Component(service = {})
public class ModuleApplicationContextExtender
	implements BundleTrackerCustomizer
		<ModuleApplicationContextExtender.ModuleApplicationContextExtension> {

	@Override
	public ModuleApplicationContextExtension addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (headers.get("Liferay-Spring-Context") == null) {
			return null;
		}

		try {
			ModuleApplicationContextExtension
				moduleApplicationContextExtension =
					new ModuleApplicationContextExtension(bundle);

			moduleApplicationContextExtension.start();

			return moduleApplicationContextExtension;
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		ModuleApplicationContextExtension moduleApplicationContextExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		ModuleApplicationContextExtension moduleApplicationContextExtension) {

		moduleApplicationContextExtension.destroy();
	}

	public class ModuleApplicationContextExtension {

		public ModuleApplicationContextExtension(Bundle bundle) {
			_bundle = bundle;

			_dependencyManager = new DependencyManager(
				bundle.getBundleContext());
		}

		public void destroy() {
			if (_component != null) {
				_dependencyManager.remove(_component);
			}
		}

		public void start() throws Exception {
			_component = _dependencyManager.createComponent();

			BundleContext bundleContext =
				ModuleApplicationContextExtender.this._bundleContext;

			_component.setImplementation(
				new ModuleApplicationContextRegistrator(
					_configurableApplicationContextConfigurator, _bundle,
					bundleContext.getBundle()));

			BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

			ClassLoader classLoader = bundleWiring.getClassLoader();

			_processServiceReferences(classLoader);

			Dictionary<String, String> headers = _bundle.getHeaders(
				StringPool.BLANK);

			String liferayService = headers.get("Liferay-Service");

			if (liferayService != null) {
				_generateConfigurationDependency(classLoader, "portlet");
				_generateConfigurationDependency(classLoader, "service");
			}

			String requireSchemaVersion = headers.get(
				"Liferay-Require-SchemaVersion");

			if (Validator.isNull(requireSchemaVersion)) {
				_generateReleaseDependency();
			}

			_dependencyManager.add(_component);
		}

		private void _generateConfigurationDependency(
			ClassLoader classLoader, String name) {

			if (ConfigurationFactoryUtil.getConfiguration(classLoader, name) !=
					null) {

				ServiceDependency serviceDependency =
					_dependencyManager.createServiceDependency();

				serviceDependency.setRequired(true);
				serviceDependency.setService(
					Configuration.class,
					StringBundler.concat(
						"(&(origin.bundle.symbolic.name=",
						_bundle.getSymbolicName(), ")(name=", name, "))"));

				_component.add(serviceDependency);
			}
		}

		private void _generateReleaseDependency() {
			ServiceDependency serviceDependency =
				_dependencyManager.createServiceDependency();

			serviceDependency.setRequired(true);
			serviceDependency.setService(
				Release.class,
				StringBundler.concat(
					"(&(release.bundle.symbolic.name=",
					_bundle.getSymbolicName(), ")(release.schema.version=",
					_bundle.getVersion(), "))"));

			_component.add(serviceDependency);
		}

		private void _processServiceReferences(ClassLoader classLoader)
			throws Exception {

			URL url = _bundle.getEntry("OSGI-INF/context/context.dependencies");

			if (url == null) {
				return;
			}

			try (InputStream inputStream = url.openStream();
				Reader reader = new InputStreamReader(inputStream);
				UnsyncBufferedReader unsyncBufferedReader =
					new UnsyncBufferedReader(reader)) {

				String line = null;

				while ((line = unsyncBufferedReader.readLine()) != null) {
					line = line.trim();

					int index = line.indexOf(CharPool.SPACE);

					String serviceClassName = line;

					String filterString = null;

					if (index != -1) {
						serviceClassName = line.substring(0, index);
						filterString = line.substring(index + 1);
					}

					ServiceDependency serviceDependency =
						_dependencyManager.createServiceDependency();

					serviceDependency.setRequired(true);

					Class<?> serviceClass = Class.forName(
						serviceClassName, false, classLoader);

					serviceDependency.setService(serviceClass, filterString);

					_component.add(serviceDependency);
				}
			}
		}

		private final Bundle _bundle;
		private org.apache.felix.dm.Component _component;
		private final DependencyManager _dependencyManager;

	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModuleApplicationContextExtender.class);

	private BundleContext _bundleContext;
	private BundleTracker<?> _bundleTracker;

	@Reference
	private ConfigurableApplicationContextConfigurator
		_configurableApplicationContextConfigurator;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}