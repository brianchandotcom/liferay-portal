/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.executor;

import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.Validator;

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
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DispatchTaskExecutor.class, null,
			(serviceReference, emitter) -> {
				String dispatchTaskFeatureFlagKey =
					(String)serviceReference.getProperty(
						_KEY_DISPATCH_TASK_FEATURE_FLAG);

				if (Validator.isNull(dispatchTaskFeatureFlagKey) ||
					FeatureFlagManagerUtil.isEnabled(
						dispatchTaskFeatureFlagKey)) {

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