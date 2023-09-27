/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInstanceSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInvoker;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRegistry;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseStatus;
import com.liferay.dynamic.data.mapping.data.provider.internal.rest.DDMRESTDataProviderSettings;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import java.lang.reflect.Field;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(service = DDMDataProviderInvoker.class)
public class DDMDataProviderInvokerImpl implements DDMDataProviderInvoker {

	@Override
	public DDMDataProviderResponse invoke(
		DDMDataProviderRequest ddmDataProviderRequest) {

		_invoked = true;

		try {
			return doInvoke(ddmDataProviderRequest);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to invoke DDM Data Provider instance ID " +
						ddmDataProviderRequest.getDDMDataProviderId(),
					exception);
			}

			return createDDMDataProviderErrorResponse(exception);
		}
	}

	protected DDMDataProviderResponse createDDMDataProviderErrorResponse(
		Exception exception) {

		DDMDataProviderResponse.Builder builder =
			DDMDataProviderResponse.Builder.newBuilder();

		if (exception instanceof HystrixRuntimeException) {
			HystrixRuntimeException.FailureType failureType =
				getHystrixFailureType(exception);

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
		else if (exception instanceof PrincipalException) {
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

		DDMDataProviderInstance ddmDataProviderInstance =
			fetchDDMDataProviderInstance(ddmDataProviderId);

		DDMDataProvider ddmDataProvider = getDDMDataProvider(
			ddmDataProviderId, ddmDataProviderInstance);

		if (ddmDataProviderInstance != null) {
			return doInvokeExternal(
				ddmDataProviderInstance, ddmDataProvider,
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

	protected DDMDataProviderInstance fetchDDMDataProviderInstance(
			String ddmDataProviderInstanceId)
		throws PortalException {

		DDMDataProviderInstance ddmDataProviderInstance =
			ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid(
				ddmDataProviderInstanceId);

		if ((ddmDataProviderInstance == null) &&
			Validator.isNumber(ddmDataProviderInstanceId)) {

			ddmDataProviderInstance =
				ddmDataProviderInstanceService.fetchDataProviderInstance(
					GetterUtil.getLong(ddmDataProviderInstanceId));
		}

		return ddmDataProviderInstance;
	}

	protected DDMDataProvider getDDMDataProvider(
		String ddmDataProviderInstanceId,
		DDMDataProviderInstance ddmDataProviderInstance) {

		if (ddmDataProviderInstance != null) {
			return ddmDataProviderRegistry.getDDMDataProvider(
				ddmDataProviderInstance.getType());
		}

		return ddmDataProviderRegistry.getDDMDataProviderByInstanceId(
			ddmDataProviderInstanceId);
	}

	protected HystrixRuntimeException.FailureType getHystrixFailureType(
		Exception exception) {

		HystrixRuntimeException hystrixRuntimeException =
			(HystrixRuntimeException)exception;

		return hystrixRuntimeException.getFailureType();
	}

	@Reference
	protected DDMDataProviderInstanceService ddmDataProviderInstanceService;

	@Reference
	protected DDMDataProviderInstanceSettings ddmDataProviderInstanceSettings;

	@Reference
	protected DDMDataProviderRegistry ddmDataProviderRegistry;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderInvokerImpl.class);

	private boolean _invoked;

}