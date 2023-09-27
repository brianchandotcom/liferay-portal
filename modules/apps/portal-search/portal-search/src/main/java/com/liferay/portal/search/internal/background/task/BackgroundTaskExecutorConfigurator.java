/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.background.task;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.search.index.ConcurrentReindexManager;
import com.liferay.portal.search.index.SyncReindexManager;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = {})
public class BackgroundTaskExecutorConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		BackgroundTaskExecutor reindexPortalBackgroundTaskExecutor =
			new ReindexPortalBackgroundTaskExecutor(
				bundleContext, _concurrentReindexManagerSnapshot.get(),
				_portalExecutorManager, _syncReindexManagerSnapshot.get());

		_registerBackgroundTaskExecutor(
			bundleContext, reindexPortalBackgroundTaskExecutor);

		_registerBackgroundTaskExecutor(
			bundleContext, _reindexSingleIndexerBackgroundTaskExecutor);
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<BackgroundTaskExecutor> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private void _registerBackgroundTaskExecutor(
		BundleContext bundleContext,
		BackgroundTaskExecutor backgroundTaskExecutor) {

		Class<?> clazz = backgroundTaskExecutor.getClass();

		ServiceRegistration<BackgroundTaskExecutor> serviceRegistration =
			bundleContext.registerService(
				BackgroundTaskExecutor.class, backgroundTaskExecutor,
				HashMapDictionaryBuilder.<String, Object>put(
					"background.task.executor.class.name", clazz.getName()
				).build());

		_serviceRegistrations.add(serviceRegistration);
	}

	private static final Snapshot<ConcurrentReindexManager>
		_concurrentReindexManagerSnapshot = new Snapshot<>(
			BackgroundTaskExecutorConfigurator.class,
			ConcurrentReindexManager.class, null, true);
	private static final Snapshot<SyncReindexManager>
		_syncReindexManagerSnapshot = new Snapshot<>(
			BackgroundTaskExecutorConfigurator.class, SyncReindexManager.class,
			null, true);

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	@Reference
	private ReindexSingleIndexerBackgroundTaskExecutor
		_reindexSingleIndexerBackgroundTaskExecutor;

	private final Set<ServiceRegistration<BackgroundTaskExecutor>>
		_serviceRegistrations = new HashSet<>();

}