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
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContextContributor;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInvoker;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse.Status;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.Validator;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixRuntimeException.FailureType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true)
public class DDMDataProviderInvokerImpl implements DDMDataProviderInvoker {

	public DDMDataProviderResponse invoke(
		DDMDataProviderRequest ddmDataProviderRequest) {

		try {
			return doInvoke(ddmDataProviderRequest);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to invoke DDM Data Provider instance ID " +
						ddmDataProviderRequest.getDDMDataProviderInstanceId(),
					e);
			}

			return createDDMDataProviderErrorResponse(e);
		}
	}

	protected void addDDMDataProviderRequestParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMDataProviderInstance ddmDataProviderInstance) {

		// Context Contributors

		List<DDMDataProviderContextContributor>
			ddmDataProviderContextContributors =
				ddmDataProviderTracker.getDDMDataProviderContextContributors(
					ddmDataProviderInstance.getType());

		for (DDMDataProviderContextContributor
				ddmDataProviderContextContributor :
					ddmDataProviderContextContributors) {

			Map<String, String> parameters =
				ddmDataProviderContextContributor.getParameters(
					ddmDataProviderRequest.getHttpServletRequest());

			if (parameters == null) {
				continue;
			}

			ddmDataProviderRequest.queryString(parameters);
		}
	}

	protected DDMDataProviderContext createDDMDataProviderContext(
		DDMDataProviderInstance ddmDataProviderInstance) {

		try {
			DDMDataProvider ddmDataProvider =
				ddmDataProviderTracker.getDDMDataProvider(
					ddmDataProviderInstance.getType());

			DDMForm ddmForm = DDMFormFactory.create(
				ddmDataProvider.getSettings());

			DDMFormValues ddmFormValues =
				ddmFormValuesJSONDeserializer.deserialize(
					ddmForm, ddmDataProviderInstance.getDefinition());

			return new DDMDataProviderContext(ddmFormValues);
		}
		catch (PortalException pe) {
			throw new IllegalStateException(pe);
		}
	}

	protected DDMDataProviderResponse createDDMDataProviderErrorResponse(
		Exception e) {

		if (e instanceof HystrixRuntimeException) {
			FailureType failureType = getHystrixFailureType(e);

			if (failureType == FailureType.TIMEOUT) {
				return DDMDataProviderResponse.error(Status.TIMEOUT);
			}
			else if (failureType == FailureType.SHORTCIRCUIT) {
				return DDMDataProviderResponse.error(Status.SHORTCIRCUIT);
			}
		}
		else if (e instanceof PrincipalException) {
			return DDMDataProviderResponse.error(Status.UNAUTHORIZED);
		}

		return DDMDataProviderResponse.error(Status.UNKNOWN_ERROR);
	}

	protected DDMDataProviderResponse doInvoke(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws Exception {

		String ddmDataProviderInstanceId =
			ddmDataProviderRequest.getDDMDataProviderInstanceId();

		Optional<DDMDataProviderInstance> ddmDataProviderInstanceOptional =
			fetchDDMDataProviderInstance(ddmDataProviderInstanceId);

		setDDMDataProviderRequestAttributes(
			ddmDataProviderRequest, ddmDataProviderInstanceOptional);

		DDMDataProvider ddmDataProvider = getDDMDataProvider(
			ddmDataProviderInstanceId, ddmDataProviderInstanceOptional);

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
				ddmDataProviderRequest);

		return ddmDataProviderInvokeCommand.execute();
	}

	protected Optional<DDMDataProviderInstance> fetchDDMDataProviderInstance(
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

	protected FailureType getHystrixFailureType(Exception e) {
		HystrixRuntimeException hystrixRuntimeException =
			(HystrixRuntimeException)e;

		return hystrixRuntimeException.getFailureType();
	}

	protected void setDDMDataProviderRequestAttributes(
		DDMDataProviderRequest ddmDataProviderRequest,
		Optional<DDMDataProviderInstance> ddmDataProviderInstanceOptional) {

		ddmDataProviderInstanceOptional.ifPresent(
			ddmDataProviderInstance -> {
				addDDMDataProviderRequestParameters(
					ddmDataProviderRequest, ddmDataProviderInstance);

				setDDMDataProviderRequestContext(
					ddmDataProviderRequest, ddmDataProviderInstance);
			});
	}

	protected void setDDMDataProviderRequestContext(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMDataProviderInstance ddmDataProviderInstance) {

		DDMDataProviderContext ddmDataProviderContext =
			createDDMDataProviderContext(ddmDataProviderInstance);

		ddmDataProviderRequest.setDDMDataProviderContext(
			ddmDataProviderContext);

		// Backwards compatibility

		ddmDataProviderContext.addParameters(
			ddmDataProviderRequest.getParameters());
	}

	@Reference
	protected DDMDataProviderInstanceService ddmDataProviderInstanceService;

	@Reference
	protected DDMDataProviderTracker ddmDataProviderTracker;

	@Reference
	protected DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderInvokerImpl.class);

	private static class DDMDataProviderInvokeCommand
		extends HystrixCommand<DDMDataProviderResponse> {

		public DDMDataProviderInvokeCommand(
			String ddmDataProviderInstanceName, DDMDataProvider ddmDataProvider,
			DDMDataProviderRequest ddmDataProviderRequest) {

			super(
				Setter.withGroupKey(_hystrixCommandGroupKey).andCommandKey(
					HystrixCommandKey.Factory.asKey(
						"DDMDataProviderInvokeCommand#" +
							ddmDataProviderInstanceName)));

			_ddmDataProvider = ddmDataProvider;
			_ddmDataProviderRequest = ddmDataProviderRequest;
		}

		@Override
		protected DDMDataProviderResponse run() throws Exception {
			return _ddmDataProvider.getData(_ddmDataProviderRequest);
		}

		private static final HystrixCommandGroupKey _hystrixCommandGroupKey =
			HystrixCommandGroupKey.Factory.asKey(
				"DDMDataProviderInvokeCommandGroup");

		private final DDMDataProvider _ddmDataProvider;
		private final DDMDataProviderRequest _ddmDataProviderRequest;

	}

}