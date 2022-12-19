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
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

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

		return _dispatchTaskExecutors.get(dispatchTaskExecutorType);
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
		return _dispatchTaskExecutors.keySet();
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

		_disabledFeatureFlags = new HashSet<>();

		for (String propertyName : properties.stringPropertyNames()) {
			if (GetterUtil.getBoolean(properties.getProperty(propertyName))) {
				_disabledFeatureFlags.add(propertyName);
			}
		}

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, DispatchTaskExecutor.class,
			new DispatchTaskExecutorServiceTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private void _validateDispatchTaskExecutorProperties(
		DispatchTaskExecutor dispatchTaskExecutor,
		String dispatchTaskExecutorType) {

		if (!_dispatchTaskExecutors.containsKey(dispatchTaskExecutorType)) {
			return;
		}

		DispatchTaskExecutor curDispatchTaskExecutor =
			_dispatchTaskExecutors.get(dispatchTaskExecutorType);

		Class<?> clazz1 = curDispatchTaskExecutor.getClass();

		Class<?> clazz2 = dispatchTaskExecutor.getClass();

		_log.error(
			StringBundler.concat(
				_KEY_DISPATCH_TASK_EXECUTOR_TYPE, " property must have unique ",
				"value. The same value is found in ", clazz1.getName(), " and ",
				clazz2.getName(), StringPool.PERIOD));
	}

	private static final String _KEY_DISPATCH_TASK_EXECUTOR_TYPE =
		"dispatch.task.executor.type";

	private static final String _KEY_DISPATCH_TASK_FEATURE_FLAG =
		"dispatch.task.executor.feature.flag";

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTaskExecutorRegistryImpl.class);

	private Set<String> _disabledFeatureFlags;
	private final Map<String, DispatchTaskExecutor> _dispatchTaskExecutors =
		new ConcurrentHashMap<>();
	private ServiceTracker<DispatchTaskExecutor, DispatchTaskExecutor>
		_serviceTracker;

	private class DispatchTaskExecutorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<DispatchTaskExecutor, DispatchTaskExecutor> {

		public DispatchTaskExecutorServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public DispatchTaskExecutor addingService(
			ServiceReference<DispatchTaskExecutor> serviceReference) {

			DispatchTaskExecutor dispatchTaskExecutor =
				_bundleContext.getService(serviceReference);

			String dispatchTaskFeatureFlag =
				(String)serviceReference.getProperty(
					_KEY_DISPATCH_TASK_FEATURE_FLAG);

			if (Validator.isNotNull(dispatchTaskFeatureFlag) &&
				_disabledFeatureFlags.contains(dispatchTaskFeatureFlag)) {

				return dispatchTaskExecutor;
			}

			String dispatchTaskExecutorType =
				(String)serviceReference.getProperty(
					_KEY_DISPATCH_TASK_EXECUTOR_TYPE);

			_validateDispatchTaskExecutorProperties(
				dispatchTaskExecutor, dispatchTaskExecutorType);

			_dispatchTaskExecutors.put(
				dispatchTaskExecutorType, dispatchTaskExecutor);

			return dispatchTaskExecutor;
		}

		@Override
		public void modifiedService(
			ServiceReference<DispatchTaskExecutor> serviceReference,
			DispatchTaskExecutor dispatchTaskExecutor) {
		}

		@Override
		public void removedService(
			ServiceReference<DispatchTaskExecutor> serviceReference,
			DispatchTaskExecutor dispatchTaskExecutor) {

			String dispatchTaskExecutorType =
				(String)serviceReference.getProperty(
					_KEY_DISPATCH_TASK_EXECUTOR_TYPE);

			_dispatchTaskExecutors.remove(dispatchTaskExecutorType);

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}