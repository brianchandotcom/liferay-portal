/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.portal.instances.internal.batch.engine.exception.handler;

import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.headless.portal.instances.dto.v1_0.PortalInstance;
import com.liferay.portal.instances.constants.PortalInstancesPortletKeys;
import com.liferay.portal.kernel.exception.RequiredCompanyException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Luis Ortiz
 */
public class PortalInstanceBatchEngineImportTaskExceptionHandlerTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_portalInstanceBatchEngineImportTaskExceptionHandler,
			"_userNotificationEventLocalService",
			_userNotificationEventLocalService);

		Mockito.when(
			_batchEngineImportTask.getOperation()
		).thenReturn(
			BatchEngineTaskOperation.DELETE.name()
		);

		Mockito.when(
			_batchEngineImportTask.getUserId()
		).thenReturn(
			_USER_ID
		);
	}

	@Test
	public void testHandleIgnoresAnotherOperation() {
		Mockito.when(
			_batchEngineImportTask.getOperation()
		).thenReturn(
			BatchEngineTaskOperation.CREATE.name()
		);

		_handle(new RequiredCompanyException(), RandomTestUtil.randomString());

		Mockito.verifyNoInteractions(_userNotificationEventLocalService);
	}

	@Test
	public void testHandleIgnoresItemsOfAnotherType() {
		_portalInstanceBatchEngineImportTaskExceptionHandler.handle(
			_batchEngineImportTask, null, new RequiredCompanyException(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Mockito.verifyNoInteractions(_userNotificationEventLocalService);
	}

	@Test
	public void testHandleMapsRequiredCompanyException() throws Exception {
		_handle(new RequiredCompanyException(), RandomTestUtil.randomString());

		JSONObject payloadJSONObject = _capturePayloadJSONObject();

		Assert.assertEquals(
			"the-default-instance-cannot-be-deleted",
			payloadJSONObject.getString("errorMessageKey"));
	}

	@Test
	public void testHandleMapsUnknownExceptionToTheDefaultMessage()
		throws Exception {

		_handle(new Exception(), RandomTestUtil.randomString());

		JSONObject payloadJSONObject = _capturePayloadJSONObject();

		Assert.assertEquals(
			"an-unexpected-error-occurred",
			payloadJSONObject.getString("errorMessageKey"));
	}

	@Test
	public void testHandleSendsUserNotificationEvent() throws Exception {
		String portalInstanceId = RandomTestUtil.randomString();

		_handle(new RequiredCompanyException(), portalInstanceId);

		JSONObject payloadJSONObject = _capturePayloadJSONObject();

		Assert.assertEquals(
			"DELETE", payloadJSONObject.getString("operationType"));
		Assert.assertEquals("FAILED", payloadJSONObject.getString("status"));
		Assert.assertEquals(
			portalInstanceId, payloadJSONObject.getString("portalInstanceId"));
	}

	private JSONObject _capturePayloadJSONObject() throws Exception {
		ArgumentCaptor<JSONObject> argumentCaptor = ArgumentCaptor.forClass(
			JSONObject.class);

		Mockito.verify(
			_userNotificationEventLocalService
		).sendUserNotificationEvents(
			Mockito.eq(_USER_ID),
			Mockito.eq(PortalInstancesPortletKeys.PORTAL_INSTANCES),
			Mockito.eq(UserNotificationDeliveryConstants.TYPE_WEBSITE),
			argumentCaptor.capture()
		);

		return argumentCaptor.getValue();
	}

	private void _handle(Exception exception, String portalInstanceId) {
		PortalInstance portalInstance = new PortalInstance();

		portalInstance.setPortalInstanceId(() -> portalInstanceId);

		_portalInstanceBatchEngineImportTaskExceptionHandler.handle(
			_batchEngineImportTask, null, exception, portalInstance,
			RandomTestUtil.randomString());
	}

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private final BatchEngineImportTask _batchEngineImportTask = Mockito.mock(
		BatchEngineImportTask.class);
	private final PortalInstanceBatchEngineImportTaskExceptionHandler
		_portalInstanceBatchEngineImportTaskExceptionHandler =
			new PortalInstanceBatchEngineImportTaskExceptionHandler();
	private final UserNotificationEventLocalService
		_userNotificationEventLocalService = Mockito.mock(
			UserNotificationEventLocalService.class);

}