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
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
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
		BackgroundTaskExecutor layoutExportBackgroundTaskExecutor =
			new LayoutExportBackgroundTaskExecutor();

		registerBackgroundTaskExecutor(
			bundleContext, layoutExportBackgroundTaskExecutor);

		BackgroundTaskExecutor layoutImportBackgroundTaskExecutor =
			new LayoutImportBackgroundTaskExecutor();

		registerBackgroundTaskExecutor(
			bundleContext, layoutImportBackgroundTaskExecutor);

		BackgroundTaskExecutor layoutRemoteStagingBackgroundTaskExecutor =
			new LayoutRemoteStagingBackgroundTaskExecutor();

		registerBackgroundTaskExecutor(
			bundleContext, layoutRemoteStagingBackgroundTaskExecutor);

		BackgroundTaskExecutor layoutStagingBackgroundTaskExecutor =
			new LayoutStagingBackgroundTaskExecutor();

		registerBackgroundTaskExecutor(
			bundleContext, layoutStagingBackgroundTaskExecutor);

		BackgroundTaskExecutor portletExportBackgroundTaskExecutor =
			new PortletExportBackgroundTaskExecutor();

		registerBackgroundTaskExecutor(
			bundleContext, portletExportBackgroundTaskExecutor);

		BackgroundTaskExecutor portletImportBackgroundTaskExecutor =
			new PortletImportBackgroundTaskExecutor();

		registerBackgroundTaskExecutor(
			bundleContext, portletImportBackgroundTaskExecutor);

		BackgroundTaskExecutor portletStagingBackgroundTaskExecutor =
			new PortletStagingBackgroundTaskExecutor();

		registerBackgroundTaskExecutor(
			bundleContext, portletStagingBackgroundTaskExecutor);
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<BackgroundTaskExecutor> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	protected void registerBackgroundTaskExecutor(
		BundleContext bundleContext,
		BackgroundTaskExecutor backgroundTaskExecutor) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		Class<?> clazz = backgroundTaskExecutor.getClass();

		properties.put("background.task.executor.class.name", clazz.getName());

		ServiceRegistration<BackgroundTaskExecutor> serviceRegistration =
			bundleContext.registerService(
				BackgroundTaskExecutor.class, backgroundTaskExecutor,
				properties);

		_serviceRegistrations.add(serviceRegistration);
	}

	private final Set<ServiceRegistration<BackgroundTaskExecutor>>
		_serviceRegistrations = new HashSet<>();

}