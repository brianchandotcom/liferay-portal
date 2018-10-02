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
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseStatus;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.data.provider.internal.rest.DDMRESTDataProviderSettings;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.portal.kernel.security.auth.PrincipalException;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderInvokerImplTest extends PowerMockito {

	@Test
	public void testDDMDataProviderInvokeCommand() throws Exception {
		DDMDataProvider ddmDataProvider = mock(DDMDataProvider.class);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.build();

		DDMDataProviderResponse.Builder responseBuilder =
			DDMDataProviderResponse.Builder.newBuilder();

		DDMDataProviderResponse expectedDDMDataProviderResponse =
			responseBuilder.withOutput(
				"output", "value"
			).build();

		when(
			ddmDataProvider.getData(ddmDataProviderRequest)
		).thenReturn(
			expectedDDMDataProviderResponse
		);

		DDMDataProviderInvokeCommand command = new DDMDataProviderInvokeCommand(
			"ddmDataProviderInstanceName", ddmDataProvider,
			ddmDataProviderRequest, mock(DDMRESTDataProviderSettings.class));

		DDMDataProviderResponse ddmDataProviderResponse = command.run();

		Optional<String> optional = ddmDataProviderResponse.getOutputOptional(
			"output", String.class);

		Assert.assertEquals("value", optional.get());
	}

	@Test
	public void testDoInvoke() throws Exception {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker = mock(
			DDMDataProviderInvokerImpl.class);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				"2"
			).build();

		Optional<DDMDataProviderInstance> optional = Optional.empty();

		when(
			ddmDataProviderInvoker.fetchDDMDataProviderInstanceOptional("2")
		).thenReturn(
			optional
		);

		DDMDataProvider ddmDataProvider = mock(DDMDataProvider.class);

		when(
			ddmDataProviderInvoker.getDDMDataProvider("2", optional)
		).thenReturn(
			ddmDataProvider
		);

		DDMDataProviderResponse.Builder responseBuilder =
			DDMDataProviderResponse.Builder.newBuilder();

		DDMDataProviderResponse expectedDDMDataProviderResponse =
			responseBuilder.withOutput(
				"output", 2
			).build();

		when(
			ddmDataProvider.getData(ddmDataProviderRequest)
		).thenReturn(
			expectedDDMDataProviderResponse
		);

		when(
			ddmDataProviderInvoker.doInvoke(ddmDataProviderRequest)
		).thenCallRealMethod();

		DDMDataProviderResponse ddmDataProviderResponse =
			ddmDataProviderInvoker.doInvoke(ddmDataProviderRequest);

		Optional<Number> outputOptional =
			ddmDataProviderResponse.getOutputOptional("output", Number.class);

		Assert.assertEquals(2, outputOptional.get());
	}

	@Test
	public void testDoInvokeExternal() throws Exception {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker = mock(
			DDMDataProviderInvokerImpl.class);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				"1"
			).build();

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		Optional<DDMDataProviderInstance> optional = Optional.of(
			ddmDataProviderInstance);

		when(
			ddmDataProviderInvoker.fetchDDMDataProviderInstanceOptional("1")
		).thenReturn(
			optional
		);

		DDMDataProvider ddmDataProvider = mock(DDMDataProvider.class);

		when(
			ddmDataProviderInvoker.getDDMDataProvider("1", optional)
		).thenReturn(
			ddmDataProvider
		);

		DDMDataProviderResponse.Builder responseBuilder =
			DDMDataProviderResponse.Builder.newBuilder();

		DDMDataProviderResponse expectedDDMDataProviderResponse =
			responseBuilder.withOutput(
				"test", "value"
			).build();

		when(
			ddmDataProviderInvoker.doInvokeExternal(
				ddmDataProviderInstance, ddmDataProvider,
				ddmDataProviderRequest)
		).thenReturn(
			expectedDDMDataProviderResponse
		);

		when(
			ddmDataProviderInvoker.doInvoke(ddmDataProviderRequest)
		).thenCallRealMethod();

		DDMDataProviderResponse ddmDataProviderResponse =
			ddmDataProviderInvoker.doInvoke(ddmDataProviderRequest);

		Optional<String> outputOptional =
			ddmDataProviderResponse.getOutputOptional("test", String.class);

		Assert.assertEquals("value", outputOptional.get());
	}

	@Test
	public void testFetchDataProviderNotFound() throws Exception {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker =
			new DDMDataProviderInvokerImpl();

		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		ddmDataProviderInvoker.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid(
				"test")
		).thenReturn(
			null
		);

		Optional<DDMDataProviderInstance> optional =
			ddmDataProviderInvoker.fetchDDMDataProviderInstanceOptional("test");

		Assert.assertFalse(optional.isPresent());
	}

	@Test
	public void testFetchDDMDataProviderInstance1() throws Exception {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker =
			new DDMDataProviderInvokerImpl();

		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		ddmDataProviderInvoker.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;

		Optional<DDMDataProviderInstance> optional =
			ddmDataProviderInvoker.fetchDDMDataProviderInstanceOptional("1");

		Assert.assertFalse(optional.isPresent());

		Mockito.verify(
			ddmDataProviderInstanceService, Mockito.times(1)
		).fetchDataProviderInstanceByUuid(
			"1"
		);

		Mockito.verify(
			ddmDataProviderInstanceService, Mockito.times(1)
		).fetchDataProviderInstance(
			1
		);
	}

	@Test
	public void testFetchDDMDataProviderInstance2() throws Exception {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker =
			new DDMDataProviderInvokerImpl();

		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		ddmDataProviderInvoker.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstance(1)
		).thenReturn(
			ddmDataProviderInstance
		);

		Optional<DDMDataProviderInstance> optional =
			ddmDataProviderInvoker.fetchDDMDataProviderInstanceOptional("1");

		Assert.assertTrue(optional.isPresent());

		Mockito.verify(
			ddmDataProviderInstanceService, Mockito.times(1)
		).fetchDataProviderInstanceByUuid(
			"1"
		);

		Mockito.verify(
			ddmDataProviderInstanceService, Mockito.times(1)
		).fetchDataProviderInstance(
			1
		);
	}

	@Test
	public void testGetDDMDataProviderByInstanceId() {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker =
			new DDMDataProviderInvokerImpl();

		DDMDataProviderTracker ddmDataProviderTracker = mock(
			DDMDataProviderTracker.class);

		ddmDataProviderInvoker.ddmDataProviderTracker = ddmDataProviderTracker;

		when(
			ddmDataProviderTracker.getDDMDataProvider(Matchers.anyString())
		).thenReturn(
			null
		);

		DDMDataProvider ddmDataProvider = mock(DDMDataProvider.class);

		when(
			ddmDataProviderTracker.getDDMDataProviderByInstanceId("1")
		).thenReturn(
			ddmDataProvider
		);

		DDMDataProvider result = ddmDataProviderInvoker.getDDMDataProvider(
			"1", Optional.empty());

		Assert.assertNotNull(result);

		Mockito.verify(
			ddmDataProviderTracker, Mockito.times(1)
		).getDDMDataProviderByInstanceId(
			"1"
		);
	}

	@Test
	public void testGetDDMDataProviderFromTracker() {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker =
			new DDMDataProviderInvokerImpl();

		DDMDataProviderTracker ddmDataProviderTracker = mock(
			DDMDataProviderTracker.class);

		ddmDataProviderInvoker.ddmDataProviderTracker = ddmDataProviderTracker;

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		when(
			ddmDataProviderInstance.getType()
		).thenReturn(
			"rest"
		);

		DDMDataProvider ddmDataProvider = mock(DDMDataProvider.class);

		when(
			ddmDataProviderTracker.getDDMDataProvider("rest")
		).thenReturn(
			ddmDataProvider
		);

		DDMDataProvider result = ddmDataProviderInvoker.getDDMDataProvider(
			"1", Optional.of(ddmDataProviderInstance));

		Assert.assertNotNull(result);

		Mockito.verify(
			ddmDataProviderTracker, Mockito.times(1)
		).getDDMDataProvider(
			"rest"
		);
	}

	@Test
	public void testGetHystrixFailureType() {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker =
			new DDMDataProviderInvokerImpl();

		HystrixRuntimeException hystrixRuntimeException =
			new HystrixRuntimeException(
				HystrixRuntimeException.FailureType.TIMEOUT, null, null, null,
				null);

		HystrixRuntimeException.FailureType failureType =
			ddmDataProviderInvoker.getHystrixFailureType(
				hystrixRuntimeException);

		Assert.assertEquals(
			HystrixRuntimeException.FailureType.TIMEOUT, failureType);
	}

	@Test
	public void testInvoke() throws Exception {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker = mock(
			DDMDataProviderInvokerImpl.class);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.build();

		when(
			ddmDataProviderInvoker.doInvoke(ddmDataProviderRequest)
		).thenThrow(
			Exception.class
		);

		when(
			ddmDataProviderInvoker.createDDMDataProviderErrorResponse(
				Matchers.any(Exception.class))
		).thenCallRealMethod();

		when(
			ddmDataProviderInvoker.invoke(ddmDataProviderRequest)
		).thenCallRealMethod();

		DDMDataProviderResponse ddmDataProviderResponse =
			ddmDataProviderInvoker.invoke(ddmDataProviderRequest);

		Assert.assertEquals(
			DDMDataProviderResponseStatus.UNKNOWN_ERROR,
			ddmDataProviderResponse.getStatus());
	}

	@Test
	public void testPrincipalException() {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker =
			new DDMDataProviderInvokerImpl();

		DDMDataProviderResponse ddmDataProviderResponse =
			ddmDataProviderInvoker.createDDMDataProviderErrorResponse(
				new PrincipalException());

		Assert.assertEquals(
			DDMDataProviderResponseStatus.UNAUTHORIZED,
			ddmDataProviderResponse.getStatus());
	}

	@Test
	public void testShortCircuitException() {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker = mock(
			DDMDataProviderInvokerImpl.class);

		HystrixRuntimeException hystrixRuntimeException = mock(
			HystrixRuntimeException.class);

		when(
			ddmDataProviderInvoker.getHystrixFailureType(
				hystrixRuntimeException)
		).thenReturn(
			HystrixRuntimeException.FailureType.SHORTCIRCUIT
		);

		when(
			ddmDataProviderInvoker.createDDMDataProviderErrorResponse(
				hystrixRuntimeException)
		).thenCallRealMethod();

		DDMDataProviderResponse ddmDataProviderResponse =
			ddmDataProviderInvoker.createDDMDataProviderErrorResponse(
				hystrixRuntimeException);

		Assert.assertEquals(
			DDMDataProviderResponseStatus.SHORT_CIRCUIT,
			ddmDataProviderResponse.getStatus());
	}

	@Test
	public void testTimeOutException() {
		DDMDataProviderInvokerImpl ddmDataProviderInvoker = mock(
			DDMDataProviderInvokerImpl.class);

		HystrixRuntimeException hystrixRuntimeException = mock(
			HystrixRuntimeException.class);

		when(
			ddmDataProviderInvoker.getHystrixFailureType(
				hystrixRuntimeException)
		).thenReturn(
			HystrixRuntimeException.FailureType.TIMEOUT
		);

		when(
			ddmDataProviderInvoker.createDDMDataProviderErrorResponse(
				hystrixRuntimeException)
		).thenCallRealMethod();

		DDMDataProviderResponse ddmDataProviderResponse =
			ddmDataProviderInvoker.createDDMDataProviderErrorResponse(
				hystrixRuntimeException);

		Assert.assertEquals(
			DDMDataProviderResponseStatus.TIMEOUT,
			ddmDataProviderResponse.getStatus());
	}

}