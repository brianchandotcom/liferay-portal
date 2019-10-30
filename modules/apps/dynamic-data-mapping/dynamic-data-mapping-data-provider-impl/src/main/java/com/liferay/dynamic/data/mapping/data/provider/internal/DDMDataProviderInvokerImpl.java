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
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInstanceSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInvoker;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseStatus;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.data.provider.internal.rest.DDMRESTDataProviderSettings;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.Validator;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import java.lang.reflect.Field;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMDataProviderInvoker.class)
public class DDMDataProviderInvokerImpl implements DDMDataProviderInvoker {

	@Override
	public DDMDataProviderResponse invoke(
		DDMDataProviderRequest ddmDataProviderRequest) {

		_invoked = true;

		try {
			return doInvoke(ddmDataProviderRequest);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to invoke DDM Data Provider instance ID " +
						ddmDataProviderRequest.getDDMDataProviderId(),
					e);
			}

			return createDDMDataProviderErrorResponse(e);
		}
	}

	protected DDMDataProviderResponse createDDMDataProviderErrorResponse(
		Exception e) {

		DDMDataProviderResponse.Builder builder =
			DDMDataProviderResponse.Builder.newBuilder();

		if (e instanceof HystrixRuntimeException) {
			HystrixRuntimeException.FailureType failureType =
				getHystrixFailureType(e);

			if (failureType ==
					HystrixRuntimeException.FailureType.COMMAND_EXCEPTION) {

				builder = builder.withStatus(
					DDMDataProviderResponseStatus.COMMAND_EXCEPTION);
			}
			else if (failureType ==
						HystrixRuntimeException.FailureType.SHORTCIRCUIT) {

				builder = builder.withStatus(
					DDMDataProviderResponseStatus.SHORT_CIRCUIT);
			}
			else if (failureType ==
						HystrixRuntimeException.FailureType.TIMEOUT) {

				builder = builder.withStatus(
					DDMDataProviderResponseStatus.TIMEOUT);
			}
		}
		else if (e instanceof PrincipalException) {
			builder = builder.withStatus(
				DDMDataProviderResponseStatus.UNAUTHORIZED);
		}
		else {
			builder = builder.withStatus(
				DDMDataProviderResponseStatus.UNKNOWN_ERROR);
		}

		return builder.build();
	}

	@Deactivate
	protected void deactivate() throws Exception {
		if (!_invoked) {
			return;
		}

		Hystrix.reset();

		Field field = ReflectionUtil.getDeclaredField(
			Hystrix.class, "currentCommand");

		ThreadLocal<?> threadLocal = (ThreadLocal<?>)field.get(null);

		threadLocal.remove();
	}

	protected DDMDataProviderResponse doInvoke(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws Exception {

		String ddmDataProviderId =
			ddmDataProviderRequest.getDDMDataProviderId();

		Optional<DDMDataProviderInstance> ddmDataProviderInstanceOptional =
			fetchDDMDataProviderInstanceOptional(ddmDataProviderId);

		DDMDataProvider ddmDataProvider = getDDMDataProvider(
			ddmDataProviderId, ddmDataProviderInstanceOptional);

		if (ddmDataProviderInstanceOptional.isPresent()) {
			return doInvokeExternal(
				ddmDataProviderInstanceOptional.get(), ddmDataProvider,
				ddmDataProviderRequest);
		}

		return ddmDataProvider.getData(ddmDataProviderRequest);
	}

	protected DDMDataProviderResponse doInvokeExternal(
		DDMDataProviderInstance ddmDataProviderInstance,
		DDMDataProvider ddmDataProvider,
		DDMDataProviderRequest ddmDataProviderRequest) {

		DDMDataProviderInvokeCommand ddmDataProviderInvokeCommand =
			new DDMDataProviderInvokeCommand(
				ddmDataProviderInstance.getNameCurrentValue(), ddmDataProvider,
				ddmDataProviderRequest,
				ddmDataProviderInstanceSettings.getSettings(
					ddmDataProviderInstance,
					DDMRESTDataProviderSettings.class));

		return ddmDataProviderInvokeCommand.execute();
	}

	protected Optional<DDMDataProviderInstance>
			fetchDDMDataProviderInstanceOptional(
				String ddmDataProviderInstanceId)
		throws PortalException {

		DDMDataProviderInstance ddmDataProviderInstance =
			ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid(
				ddmDataProviderInstanceId);

		if ((ddmDataProviderInstance == null) &&
			Validator.isNumber(ddmDataProviderInstanceId)) {

			ddmDataProviderInstance =
				ddmDataProviderInstanceService.fetchDataProviderInstance(
					Long.valueOf(ddmDataProviderInstanceId));
		}

		return Optional.ofNullable(ddmDataProviderInstance);
	}

	protected DDMDataProvider getDDMDataProvider(
		String ddmDataProviderInstanceId,
		Optional<DDMDataProviderInstance> ddmDataProviderInstanceOptional) {

		Optional<DDMDataProvider> ddmDataProviderTypeOptional =
			ddmDataProviderInstanceOptional.map(
				ddmDataProviderInstance ->
					ddmDataProviderTracker.getDDMDataProvider(
						ddmDataProviderInstance.getType()));

		return ddmDataProviderTypeOptional.orElseGet(
			() -> ddmDataProviderTracker.getDDMDataProviderByInstanceId(
				ddmDataProviderInstanceId));
	}

	protected HystrixRuntimeException.FailureType getHystrixFailureType(
		Exception e) {

		HystrixRuntimeException hystrixRuntimeException =
			(HystrixRuntimeException)e;

		return hystrixRuntimeException.getFailureType();
	}

	@Reference
	protected DDMDataProviderInstanceService ddmDataProviderInstanceService;

	@Reference
	protected DDMDataProviderInstanceSettings ddmDataProviderInstanceSettings;

	@Reference
	protected DDMDataProviderTracker ddmDataProviderTracker;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderInvokerImpl.class);

	private boolean _invoked;

}