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

import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactory;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.scripting.executor.constants.ScriptingExecutorConstants;
import com.liferay.portal.scripting.executor.internal.ScriptingExecutorMessagingConstants;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(immediate = true)
public class ScriptingExecutorExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE,
			new ScriptingExecutorBundleTrackerCustomizer());

		_bundleTracker.open();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeScriptingExecutor"
	)
	protected void addScriptingExecutor(ScriptingExecutor scriptingExecutor) {
		_scriptingLanguages.add(scriptingExecutor.getLanguage());
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();

		_singleDestinationMessageSender = null;

		_bundleTracker = null;
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

	@Reference(unbind = "-")
	protected void setSingleDestinationMessageSenderFactory(
		SingleDestinationMessageSenderFactory
			singleDestinationMessageSenderFactory) {

		_singleDestinationMessageSender =
			singleDestinationMessageSenderFactory.
				createSingleDestinationMessageSender(
					ScriptingExecutorMessagingConstants.DESTINATION_NAME);
	}

	private static final String _SCRIPTS_DIR = "/META-INF/resources/scripts/";

	private static final Log _log = LogFactoryUtil.getLog(
		ScriptingExecutorExtender.class);

	private BundleTracker<Object> _bundleTracker;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	private final Set<String> _scriptingLanguages = new HashSet<>();
	private SingleDestinationMessageSender _singleDestinationMessageSender;

	private class ScriptingExecutorBundleTrackerCustomizer
		implements BundleTrackerCustomizer<Object> {

		@Override
		public Object addingBundle(Bundle bundle, BundleEvent bundleEvent) {
			Dictionary<String, String> headers = bundle.getHeaders();

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
						"No " + scriptingLanguage + " executors available " +
							"to process scripts from " +
								bundle.getSymbolicName());
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

			_singleDestinationMessageSender.send(message);

			return null;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent, Object object) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent, Object object) {
		}

	}

}