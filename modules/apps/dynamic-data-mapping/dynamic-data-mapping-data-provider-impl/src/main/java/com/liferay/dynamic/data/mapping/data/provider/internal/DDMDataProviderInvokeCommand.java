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

package com.liferay.dynamic.data.mapping.data.provider.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.internal.rest.DDMRESTDataProviderSettings;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * @author Marcellus Tavares
 */
public class DDMDataProviderInvokeCommand
	extends HystrixCommand<DDMDataProviderResponse> {

	public DDMDataProviderInvokeCommand(
		String nameCurrentValue, DDMDataProvider ddmDataProvider,
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		// Skip JavaParser

		super(
			Setter.withGroupKey(
				_hystrixCommandGroupKey
			).andCommandKey(
				HystrixCommandKey.Factory.asKey(
					"DDMDataProviderInvokeCommand#" + nameCurrentValue)
			).andCommandPropertiesDefaults(
				HystrixCommandProperties.Setter().
					withExecutionTimeoutInMilliseconds(
						getTimeout(ddmRESTDataProviderSettings))
			));

		_ddmDataProvider = ddmDataProvider;
		_ddmDataProviderRequest = ddmDataProviderRequest;
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();
	}

	protected static int getTimeout(
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		int timeout = GetterUtil.getInteger(
			ddmRESTDataProviderSettings.timeout());

		if ((timeout >= _TIMEOUT_MIN) && (timeout <= _TIMEOUT_MAX)) {
			return timeout;
		}

		return _TIMEOUT_MIN;
	}

	@Override
	protected DDMDataProviderResponse run() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);

		return _ddmDataProvider.getData(_ddmDataProviderRequest);
	}

	private static final int _TIMEOUT_MAX = 30000;

	private static final int _TIMEOUT_MIN = 1000;

	private static final HystrixCommandGroupKey _hystrixCommandGroupKey =
		HystrixCommandGroupKey.Factory.asKey(
			"DDMDataProviderInvokeCommandGroup");

	private final DDMDataProvider _ddmDataProvider;
	private final DDMDataProviderRequest _ddmDataProviderRequest;
	private final PermissionChecker _permissionChecker;

}