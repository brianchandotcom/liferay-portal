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

package com.liferay.dispatch.internal.executor;

import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Matija Petanjek
 * @author Joe Duffy
 * @author Igor Beslic
 */
@Component(service = DispatchTaskExecutorRegistry.class)
public class DispatchTaskExecutorRegistryImpl
	implements DispatchTaskExecutorRegistry {

	@Override
	public DispatchTaskExecutor fetchDispatchTaskExecutor(
		String dispatchTaskExecutorType) {

		return _serviceTrackerMap.getService(dispatchTaskExecutorType);
	}

	@Override
	public String fetchDispatchTaskExecutorName(
		String dispatchTaskExecutorType) {

		DispatchTaskExecutor dispatchTaskExecutor = fetchDispatchTaskExecutor(
			dispatchTaskExecutorType);

		if ((dispatchTaskExecutor != null) &&
			!dispatchTaskExecutor.isHiddenInUI()) {

			return dispatchTaskExecutor.getName();
		}

		return null;
	}

	@Override
	public Set<String> getDispatchTaskExecutorTypes() {
		return _serviceTrackerMap.keySet();
	}

	@Override
	public boolean isClusterModeSingle(String type) {
		DispatchTaskExecutor dispatchTaskExecutor = fetchDispatchTaskExecutor(
			type);

		if (dispatchTaskExecutor != null) {
			return dispatchTaskExecutor.isClusterModeSingle();
		}

		return false;
	}

	@Override
	public boolean isHiddenInUI(String type) {
		DispatchTaskExecutor dispatchTaskExecutor = fetchDispatchTaskExecutor(
			type);

		if (dispatchTaskExecutor != null) {
			return dispatchTaskExecutor.isHiddenInUI();
		}

		return false;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		Properties properties = PropsUtil.getProperties("feature.flag.", true);

		Set<String> disabledFeatureFlags = new HashSet<>();

		for (String propertyName : properties.stringPropertyNames()) {
			if (GetterUtil.getBoolean(properties.getProperty(propertyName))) {
				disabledFeatureFlags.add(propertyName);
			}
		}

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DispatchTaskExecutor.class, null,
			(serviceReference, emitter) -> {
				String dispatchTaskFeatureFlag =
					(String)serviceReference.getProperty(
						_KEY_DISPATCH_TASK_FEATURE_FLAG);

				if (Validator.isNull(dispatchTaskFeatureFlag) ||
					!disabledFeatureFlags.contains(dispatchTaskFeatureFlag)) {

					emitter.emit(
						(String)serviceReference.getProperty(
							_KEY_DISPATCH_TASK_EXECUTOR_TYPE));
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final String _KEY_DISPATCH_TASK_EXECUTOR_TYPE =
		"dispatch.task.executor.type";

	private static final String _KEY_DISPATCH_TASK_FEATURE_FLAG =
		"dispatch.task.executor.feature.flag";

	private ServiceTrackerMap<String, DispatchTaskExecutor> _serviceTrackerMap;

}