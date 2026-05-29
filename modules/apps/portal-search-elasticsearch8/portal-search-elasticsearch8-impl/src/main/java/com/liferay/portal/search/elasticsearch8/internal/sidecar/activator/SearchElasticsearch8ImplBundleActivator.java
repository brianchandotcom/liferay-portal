/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.sidecar.activator;

import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.concurrent.SystemExecutorServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.module.util.ServiceLatch;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.search.elasticsearch8.internal.sidecar.PersistedProcessUtil;
import com.liferay.portal.tools.DBUpgrader;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Tina Tian
 */
public class SearchElasticsearch8ImplBundleActivator
	implements BundleActivator {

	public static Future<ObjectValuePair<ProcessChannel<Serializable>, byte[]>>
		getFuture() {

		return _future;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		File sidecarProcessFile = bundleContext.getDataFile("sidecar.process");

		if (sidecarProcessFile.exists()) {
			ServiceReference<ProcessExecutor> serviceReference =
				bundleContext.getServiceReference(ProcessExecutor.class);

			ExecutorService executorService =
				SystemExecutorServiceUtil.getExecutorService();

			_future = executorService.submit(
				() -> PersistedProcessUtil.start(
					bundleContext.getService(serviceReference),
					sidecarProcessFile));
		}

		if ((!UpgradeProcessUtil.isUpgradeClient() &&
			 !DBUpgrader.isUpgradeDatabaseAutoRunEnabled() &&
			 !StartupHelperUtil.isUpgrading()) ||
			!_hasLegacyElasticsearchConfiguration(bundleContext)) {

			_publishElasticsearchConfigurationReady(bundleContext);

			return;
		}

		Bundle bundle = bundleContext.getBundle();

		ServiceLatch serviceLatch = new ServiceLatch(bundleContext);

		serviceLatch.waitFor(
			StringBundler.concat(
				"(&(objectClass=", Release.class.getName(),
				")(release.bundle.symbolic.name=", bundle.getSymbolicName(),
				")(release.schema.version>=1.0.0))"));

		serviceLatch.openOn(
			() -> _publishElasticsearchConfigurationReady(bundleContext));
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if (_elasticsearchConfigurationReadyServiceRegistration != null) {
			_elasticsearchConfigurationReadyServiceRegistration.unregister();

			_elasticsearchConfigurationReadyServiceRegistration = null;
		}
	}

	private int _getElasticsearchVersion(String string) {
		String elasticsearch = "elasticsearch";

		int index = string.indexOf(elasticsearch);

		if (index == -1) {
			return -1;
		}

		int beginIndex = index + elasticsearch.length();

		int endIndex = beginIndex;

		while ((endIndex < string.length()) &&
			   Character.isDigit(string.charAt(endIndex))) {

			endIndex++;
		}

		if (endIndex == beginIndex) {
			return -1;
		}

		return GetterUtil.getInteger(string.substring(beginIndex, endIndex));
	}

	private boolean _hasLegacyElasticsearchConfiguration(
		BundleContext bundleContext) {

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		if (serviceReference == null) {
			return false;
		}

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		try {
			Configuration[] configurations =
				configurationAdmin.listConfigurations(
					"(|(service.pid=*Elasticsearch*Configuration)" +
						"(service.factoryPid=*Elasticsearch*Configuration))");

			if (configurations == null) {
				return false;
			}

			Bundle bundle = bundleContext.getBundle();

			int runtimeVersion = _getElasticsearchVersion(
				bundle.getSymbolicName());

			for (Configuration configuration : configurations) {
				String pid = configuration.getFactoryPid();

				if (pid == null) {
					pid = configuration.getPid();
				}

				int configurationVersion = _getElasticsearchVersion(pid);

				if (configurationVersion != runtimeVersion) {
					return true;
				}
			}

			return false;
		}
		catch (InvalidSyntaxException | IOException exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get legacy Elasticsearch configurations",
					exception);
			}

			return false;
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	private void _publishElasticsearchConfigurationReady(
		BundleContext bundleContext) {

		_elasticsearchConfigurationReadyServiceRegistration =
			bundleContext.registerService(
				Object.class, new Object(),
				HashMapDictionaryBuilder.<String, Object>put(
					"elasticsearch.configuration.ready", "true"
				).build());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchElasticsearch8ImplBundleActivator.class);

	private static volatile Future
		<ObjectValuePair<ProcessChannel<Serializable>, byte[]>> _future;

	private ServiceRegistration<Object>
		_elasticsearchConfigurationReadyServiceRegistration;

}