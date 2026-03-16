/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.internal.rest.DDMRESTDataProviderSettings;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;

import java.util.concurrent.Callable;

/**
 * @author Marcellus Tavares
 */
public class DDMDataProviderInvokeCommand
	extends HystrixCommand<DDMDataProviderResponse> {

	public DDMDataProviderInvokeCommand(
		Callable<DDMDataProviderResponse> callable,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings,
		String nameCurrentValue) {

		// Skip JavaParser

		super(
			Setter.withGroupKey(
				_hystrixCommandGroupKey
			).andCommandKey(
				HystrixCommandKey.Factory.asKey(
					"DDMDataProviderInvokeCommand#" + nameCurrentValue)
			).andCommandPropertiesDefaults(
				HystrixCommandProperties.Setter()
					.withExecutionIsolationStrategy(
						HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
					.withExecutionTimeoutInMilliseconds(
						_getTimeout(ddmRESTDataProviderSettings))
					.withFallbackEnabled(false)
			).andThreadPoolPropertiesDefaults(
				HystrixThreadPoolProperties.Setter()
					.withAllowMaximumSizeToDivergeFromCoreSize(true)
					.withCoreSize(5)
					.withMetricsRollingStatisticalWindowInMilliseconds(1000)
			));

		_callable = callable;

		_permissionChecker = PermissionThreadLocal.getPermissionChecker();
	}

	@Override
	protected DDMDataProviderResponse run() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);

		return _callable.call();
	}

	private static int _getTimeout(
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		int timeout = GetterUtil.getInteger(
			ddmRESTDataProviderSettings.timeout());

		if ((timeout >= _TIMEOUT_MIN) && (timeout <= _TIMEOUT_MAX)) {
			return timeout;
		}

		return _TIMEOUT_MIN;
	}

	private static final int _TIMEOUT_MAX = 30000;

	private static final int _TIMEOUT_MIN = 1000;

	private static final HystrixCommandGroupKey _hystrixCommandGroupKey =
		HystrixCommandGroupKey.Factory.asKey(
			"DDMDataProviderInvokeCommandGroup");

	static {
		HystrixPlugins histrixPlugins = HystrixPlugins.getInstance();

		histrixPlugins.registerPropertiesStrategy(
			new HystrixPropertiesStrategy() {

				public String getCommandPropertiesCacheKey(
					HystrixCommandKey hystrixCommandKey,
					HystrixCommandProperties.Setter setter) {

					return null;
				}

			});
	}

	private final Callable<DDMDataProviderResponse> _callable;
	private final PermissionChecker _permissionChecker;

}