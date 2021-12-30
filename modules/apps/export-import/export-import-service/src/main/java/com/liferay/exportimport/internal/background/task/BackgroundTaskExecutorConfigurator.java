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

package com.liferay.exportimport.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = BackgroundTaskExecutorConfigurator.class)
public class BackgroundTaskExecutorConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_registerBackgroundTaskExecutor(
			bundleContext, new LayoutExportBackgroundTaskExecutor());

		_registerBackgroundTaskExecutor(
			bundleContext, new LayoutImportBackgroundTaskExecutor());

		_registerBackgroundTaskExecutor(
			bundleContext, new LayoutRemoteStagingBackgroundTaskExecutor());

		_registerBackgroundTaskExecutor(
			bundleContext,
			new LayoutSetPrototypeImportBackgroundTaskExecutor());

		_registerBackgroundTaskExecutor(
			bundleContext, new LayoutStagingBackgroundTaskExecutor());

		_registerBackgroundTaskExecutor(
			bundleContext, new PortletExportBackgroundTaskExecutor());

		_registerBackgroundTaskExecutor(
			bundleContext, new PortletImportBackgroundTaskExecutor());

		_registerBackgroundTaskExecutor(
			bundleContext, new PortletRemoteStagingBackgroundTaskExecutor());

		_registerBackgroundTaskExecutor(
			bundleContext, new PortletStagingBackgroundTaskExecutor());
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

	private final Set<ServiceRegistration<BackgroundTaskExecutor>>
		_serviceRegistrations = new HashSet<>();

}