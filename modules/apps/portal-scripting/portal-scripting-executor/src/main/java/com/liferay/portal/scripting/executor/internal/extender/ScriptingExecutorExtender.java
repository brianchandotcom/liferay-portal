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

package com.liferay.portal.scripting.executor.internal.extender;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.scripting.ScriptBundleProvider;
import com.liferay.portal.scripting.executor.internal.ScriptingExecutorMessagingConstants;
import com.liferay.portal.scripting.executor.internal.constants.ScriptingExecutorConstants;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = {})
public class ScriptingExecutorExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.create(
			bundleContext, ScriptBundleProvider.class,
			new ScriptBundleProviderServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addScriptingExecutor(ScriptingExecutor scriptingExecutor) {
		_scriptingLanguages.add(scriptingExecutor.getLanguage());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_bundleContext = null;

		_serviceTracker = null;
	}

	protected void removeScriptingExecutor(
		ScriptingExecutor scriptingExecutor) {

		_scriptingLanguages.remove(scriptingExecutor.getLanguage());
	}

	@Reference(
		target = "(destination.name=" + ScriptingExecutorMessagingConstants.DESTINATION_NAME + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	private static final String _SCRIPTS_DIR = "/META-INF/resources/scripts/";

	private static final Log _log = LogFactoryUtil.getLog(
		ScriptingExecutorExtender.class);

	private BundleContext _bundleContext;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(
		target = "(destination.name=" + ScriptingExecutorMessagingConstants.DESTINATION_NAME + ")"
	)
	private Destination _destination;

	@Reference
	private Portal _portal;

	private final Set<String> _scriptingLanguages = new HashSet<>();
	private ServiceTracker<ScriptBundleProvider, ScriptBundleProvider>
		_serviceTracker;

	@Reference
	private UserLocalService _userLocalService;

	private class ScriptBundleProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ScriptBundleProvider, ScriptBundleProvider> {

		@Override
		public ScriptBundleProvider addingService(
			ServiceReference<ScriptBundleProvider> serviceReference) {

			ScriptBundleProvider scriptBundleProvider =
				_bundleContext.getService(serviceReference);

			Bundle bundle = scriptBundleProvider.getBundle();

			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			if (GetterUtil.getBoolean(
					headers.get(
						ScriptingExecutorConstants.
							LIFERAY_SCRIPTING_EXECUTOR_CLUSTER_MASTER_ONLY)) &&
				!_clusterMasterExecutor.isMaster()) {

				return null;
			}

			String scriptingLanguage = headers.get(
				ScriptingExecutorConstants.
					LIFERAY_SCRIPTING_EXECUTOR_SCRIPTING_LANGUAGE);

			if (scriptingLanguage == null) {
				scriptingLanguage =
					ScriptingExecutorMessagingConstants.
						SCRIPTING_LANGUAGE_DEFAULT;
			}

			if (!_scriptingLanguages.contains(scriptingLanguage)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"No ", scriptingLanguage,
							" executors available to process scripts from ",
							bundle.getSymbolicName()));
				}

				return null;
			}

			Enumeration<URL> enumeration = bundle.findEntries(
				_SCRIPTS_DIR, "*", true);

			if ((enumeration == null) || !enumeration.hasMoreElements()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"No scripts in bundle " + bundle.getSymbolicName());
				}

				return null;
			}

			Message message = new Message();

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			ClassLoader bundleClassLoader = bundleWiring.getClassLoader();

			message.put(
				ScriptingExecutorMessagingConstants.
					MESSAGE_KEY_BUNDLE_CLASS_LOADER,
				bundleClassLoader);

			message.put(
				ScriptingExecutorMessagingConstants.
					MESSAGE_KEY_SCRIPTING_LANGUAGE,
				scriptingLanguage);

			List<URL> scriptURLs = new ArrayList<>();

			while (enumeration.hasMoreElements()) {
				scriptURLs.add(enumeration.nextElement());
			}

			message.put(
				ScriptingExecutorMessagingConstants.MESSAGE_KEY_URLS,
				scriptURLs);

			_destination.send(message);

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<ScriptBundleProvider> serviceReference,
			ScriptBundleProvider scriptBundleProvider) {
		}

		@Override
		public void removedService(
			ServiceReference<ScriptBundleProvider> serviceReference,
			ScriptBundleProvider scriptBundleProvider) {
		}

	}

}