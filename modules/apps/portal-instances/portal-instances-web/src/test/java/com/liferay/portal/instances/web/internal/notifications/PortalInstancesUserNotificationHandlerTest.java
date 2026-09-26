/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.notifications;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instances.constants.PortalInstancesNotificationConstants;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Luis Ortiz
 */
public class PortalInstancesUserNotificationHandlerTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_portalInstancesUserNotificationHandler, "_jsonFactory",
			new JSONFactoryImpl());

		Mockito.when(
			_serviceContext.translate(Mockito.anyString())
		).thenAnswer(
			invocationOnMock -> invocationOnMock.getArgument(0)
		);

		Mockito.when(
			_serviceContext.translate(
				Mockito.anyString(), Mockito.<Object>any())
		).thenAnswer(
			invocationOnMock ->
				invocationOnMock.getArgument(0) + ":" +
					invocationOnMock.getArgument(1)
		);
	}

	@Test
	public void testGetBody() throws Exception {
		String portalInstanceId = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				"<h2 class=\"title\">the-instance-x-could-not-be-deleted:",
				portalInstanceId, "</h2><div class=\"body\">",
				"the-default-instance-cannot-be-deleted</div>"),
			_getBody(
				_toPayloadJSONObject(
					"the-default-instance-cannot-be-deleted", portalInstanceId,
					PortalInstancesNotificationConstants.STATUS_FAILED)));
		Assert.assertEquals(
			StringBundler.concat(
				"<h2 class=\"title\">the-instance-x-was-deleted:",
				portalInstanceId, "</h2><div class=\"body\">",
				"the-instance-x-is-no-longer-available:", portalInstanceId,
				"</div>"),
			_getBody(
				_toPayloadJSONObject(
					null, portalInstanceId,
					PortalInstancesNotificationConstants.STATUS_SUCCESS)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetBodyWithUnknownOperationType() throws Exception {
		JSONObject payloadJSONObject = _toPayloadJSONObject(
			null, RandomTestUtil.randomString(),
			PortalInstancesNotificationConstants.STATUS_SUCCESS);

		payloadJSONObject.put("operationType", RandomTestUtil.randomString());

		_getBody(payloadJSONObject);
	}

	@Test
	public void testGetTitle() throws Exception {
		String portalInstanceId = RandomTestUtil.randomString();

		Assert.assertEquals(
			"the-instance-x-could-not-be-deleted:" + portalInstanceId,
			_getTitle(
				_toPayloadJSONObject(
					"the-default-instance-cannot-be-deleted", portalInstanceId,
					PortalInstancesNotificationConstants.STATUS_FAILED)));
		Assert.assertEquals(
			"the-instance-x-was-deleted:" + portalInstanceId,
			_getTitle(
				_toPayloadJSONObject(
					null, portalInstanceId,
					PortalInstancesNotificationConstants.STATUS_SUCCESS)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTitleWithUnknownOperationType() throws Exception {
		JSONObject payloadJSONObject = _toPayloadJSONObject(
			null, RandomTestUtil.randomString(),
			PortalInstancesNotificationConstants.STATUS_SUCCESS);

		payloadJSONObject.put("operationType", RandomTestUtil.randomString());

		_getTitle(payloadJSONObject);
	}

	private String _getBody(JSONObject payloadJSONObject) throws Exception {
		return _portalInstancesUserNotificationHandler.getBody(
			_toUserNotificationEvent(payloadJSONObject), _serviceContext);
	}

	private String _getTitle(JSONObject payloadJSONObject) throws Exception {
		return _portalInstancesUserNotificationHandler.getTitle(
			_toUserNotificationEvent(payloadJSONObject), _serviceContext);
	}

	private JSONObject _toPayloadJSONObject(
		String errorMessageKey, String portalInstanceId, String status) {

		return JSONUtil.put(
			"errorMessageKey", errorMessageKey
		).put(
			"operationType",
			PortalInstancesNotificationConstants.OPERATION_TYPE_DELETE
		).put(
			"portalInstanceId", portalInstanceId
		).put(
			"status", status
		);
	}

	private UserNotificationEvent _toUserNotificationEvent(
		JSONObject payloadJSONObject) {

		UserNotificationEvent userNotificationEvent = Mockito.mock(
			UserNotificationEvent.class);

		Mockito.when(
			userNotificationEvent.getPayload()
		).thenReturn(
			payloadJSONObject.toString()
		);

		return userNotificationEvent;
	}

	private final PortalInstancesUserNotificationHandler
		_portalInstancesUserNotificationHandler =
			new PortalInstancesUserNotificationHandler();
	private final ServiceContext _serviceContext = Mockito.mock(
		ServiceContext.class);

}