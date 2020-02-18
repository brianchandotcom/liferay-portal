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

package com.liferay.configuration.admin.web.internal.search;

import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.util.Collection;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Tina Tian
 */
@Component(
	immediate = true,
	service = {
		ClusterConfigurationModelIndexer.class, IdentifiableOSGiService.class
	}
)
public class ClusterConfigurationModelIndexer
	implements IdentifiableOSGiService {

	@Override
	public String getOSGiServiceIdentifier() {
		return ClusterConfigurationModelIndexer.class.getName();
	}

	public void initialize() throws Exception {
		if (!_initialized) {
			_initialize();
		}
	}

	@Activate
	protected void activate() {
		if (_clusterExecutor.isEnabled()) {
			_configurationModelsClusterMasterTokenTransitionListener =
				new ConfigurationModelsClusterMasterTokenTransitionListener();

			_clusterMasterExecutor.addClusterMasterTokenTransitionListener(
				_configurationModelsClusterMasterTokenTransitionListener);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_configurationModelsClusterMasterTokenTransitionListener != null) {
			_clusterMasterExecutor.removeClusterMasterTokenTransitionListener(
				_configurationModelsClusterMasterTokenTransitionListener);
		}

		_stopBundleTracker();
	}

	private static void _initialize(String osgiServiceIdentifier)
		throws Exception {

		ClusterConfigurationModelIndexer clusterConfigurationModelIndexer =
			(ClusterConfigurationModelIndexer)
				IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
					osgiServiceIdentifier);

		clusterConfigurationModelIndexer.initialize();
	}

	private static void _reset(String osgiServiceIdentifier) {
		ClusterConfigurationModelIndexer clusterConfigurationModelIndexer =
			(ClusterConfigurationModelIndexer)
				IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
					osgiServiceIdentifier);

		clusterConfigurationModelIndexer._reset();
	}

	private synchronized void _initialize() throws Exception {
		if (_initialized) {
			return;
		}

		if (_clusterMasterExecutor.isMaster()) {
			try (SafeClosable safeClosable =
					ProxyModeThreadLocal.setWithSafeClosable(true)) {

				_bundleTracker = _configurationModelIndexer.initialize();
			}
		}
		else {
			NoticeableFuture<Void> noticeableFuture =
				_clusterMasterExecutor.executeOnMaster(
					new MethodHandler(
						_initializeMethodKey, getOSGiServiceIdentifier()));

			noticeableFuture.get();
		}

		_initialized = true;
	}

	private synchronized void _reset() {
		_initialized = false;
	}

	private synchronized void _stopBundleTracker() {
		if (_bundleTracker != null) {
			try (SafeClosable safeClosable =
					ProxyModeThreadLocal.setWithSafeClosable(true)) {

				_bundleTracker.close();
			}

			_bundleTracker = null;
		}
	}

	private static final MethodKey _initializeMethodKey = new MethodKey(
		ClusterConfigurationModelIndexer.class, "_initialize", String.class);
	private static final MethodKey _resetMethodKey = new MethodKey(
		ClusterConfigurationModelIndexer.class, "_reset", String.class);

	private BundleTracker<Collection<ConfigurationModel>> _bundleTracker;

	@Reference
	private ClusterExecutor _clusterExecutor;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	@Reference
	private ConfigurationModelIndexer _configurationModelIndexer;

	private ConfigurationModelsClusterMasterTokenTransitionListener
		_configurationModelsClusterMasterTokenTransitionListener;
	private volatile boolean _initialized;

	private class ConfigurationModelsClusterMasterTokenTransitionListener
		implements ClusterMasterTokenTransitionListener {

		@Override
		public void masterTokenAcquired() {
			_reset();

			ClusterRequest clusterRequest =
				ClusterRequest.createMulticastRequest(
					new MethodHandler(
						_resetMethodKey, getOSGiServiceIdentifier()),
					true);

			clusterRequest.setFireAndForget(true);

			_clusterExecutor.execute(clusterRequest);
		}

		@Override
		public void masterTokenReleased() {
			_stopBundleTracker();
		}

	}

}