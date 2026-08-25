/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.background.task.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.exception.CompanyWebIdException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class AddVirtualInstanceBackgroundTaskExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testExecute() throws Exception {
		BackgroundTask backgroundTask = _addBackgroundTask(null, null);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_SUCCESSFUL,
			backgroundTask.getStatus());

		_company = _companyLocalService.getCompanyByWebId(_WEB_ID);

		JSONObject payloadJSONObject = _getPayloadJSONObject();

		Assert.assertEquals(
			_company.getCompanyId(), payloadJSONObject.getLong("companyId"));
		Assert.assertEquals(
			BackgroundTaskConstants.LABEL_SUCCESSFUL,
			payloadJSONObject.getString("status"));
		Assert.assertEquals(_WEB_ID, payloadJSONObject.getString("webId"));

		JSONObject statusMessageJSONObject = _jsonFactory.createJSONObject(
			backgroundTask.getStatusMessage());

		Assert.assertEquals(
			_company.getCompanyId(),
			statusMessageJSONObject.getLong("companyId"));
	}

	@Test
	public void testExecuteWhenDefaultAdminEmailAddressIsInvalid()
		throws Exception {

		BackgroundTask backgroundTask = _addBackgroundTask(
			RandomTestUtil.randomString(), null);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_FAILED, backgroundTask.getStatus());

		JSONObject payloadJSONObject = _getPayloadJSONObject();

		Assert.assertEquals(
			"please-enter-a-valid-email-address",
			payloadJSONObject.getString("errorMessage"));
		Assert.assertEquals(
			BackgroundTaskConstants.LABEL_FAILED,
			payloadJSONObject.getString("status"));

		_company = _companyLocalService.getCompanyByWebId(_WEB_ID);
	}

	@Test
	public void testExecuteWhenDefaultAdminPasswordIsEncrypted()
		throws Exception {

		String defaultAdminPassword = RandomTestUtil.randomString();

		Company defaultCompany = _companyLocalService.getCompany(
			PortalUtil.getDefaultCompanyId());

		BackgroundTask backgroundTask = _addBackgroundTask(
			null,
			EncryptorUtil.encrypt(
				defaultCompany.getKeyObj(), defaultAdminPassword));

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_SUCCESSFUL,
			backgroundTask.getStatus());

		_company = _companyLocalService.getCompanyByWebId(_WEB_ID);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		Assert.assertNotEquals(
			defaultAdminPassword, taskContextMap.get("defaultAdminPassword"));

		String emailAddress =
			PropsUtil.get(PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX) +
				StringPool.AT + _VIRTUAL_HOSTNAME;

		Assert.assertEquals(
			Authenticator.SUCCESS,
			_userLocalService.authenticateByEmailAddress(
				_company.getCompanyId(), emailAddress, defaultAdminPassword,
				new HashMap<>(), new HashMap<>(), new HashMap<>()));
	}

	@Test
	public void testValidateCompanyWhenWebIdIsDuplicate() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Assert.assertThrows(
			CompanyWebIdException.class,
			() -> _companyLocalService.validateCompany(
				company.getWebId(), _VIRTUAL_HOSTNAME, _VIRTUAL_HOSTNAME, 0));
	}

	private BackgroundTask _addBackgroundTask(
			String defaultAdminEmailAddress, String defaultAdminPassword)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				"active", true
			).put(
				"defaultAdminEmailAddress", () -> defaultAdminEmailAddress
			).put(
				"defaultAdminPassword", () -> defaultAdminPassword
			).put(
				"maxUsers", 0
			).put(
				"mx", _VIRTUAL_HOSTNAME
			).put(
				"siteInitializerKey", StringPool.BLANK
			).put(
				"virtualHostname", _VIRTUAL_HOSTNAME
			).put(
				"webId", _WEB_ID
			).build();

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.addBackgroundTask(
				TestPropsValues.getUserId(),
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				"AddVirtualInstance#" + _WEB_ID,
				"com.liferay.portal.instances.web.internal.background.task." +
					"AddVirtualInstanceBackgroundTaskExecutor",
				taskContextMap, new ServiceContext());

		_backgroundTasks.add(backgroundTask);

		return _waitForCompletion(backgroundTask.getBackgroundTaskId());
	}

	private JSONObject _getPayloadJSONObject() throws Exception {
		List<UserNotificationEvent> userNotificationEvents =
			_userNotificationEventLocalService.getUserNotificationEvents(
				TestPropsValues.getUserId(),
				UserNotificationDeliveryConstants.TYPE_WEBSITE);

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			String type = userNotificationEvent.getType();

			if (!type.equals(
					"com_liferay_portal_instances_web_portlet_" +
						"PortalInstancesPortlet")) {

				continue;
			}

			JSONObject payloadJSONObject = _jsonFactory.createJSONObject(
				userNotificationEvent.getPayload());

			String webId = payloadJSONObject.getString("webId");

			if (webId.equals(_WEB_ID)) {
				_userNotificationEvents.add(userNotificationEvent);

				return payloadJSONObject;
			}
		}

		throw new AssertionError(
			"No user notification event was sent for web ID " + _WEB_ID);
	}

	private BackgroundTask _waitForCompletion(long backgroundTaskId)
		throws Exception {

		long endTime = System.currentTimeMillis() + 600000;

		while (System.currentTimeMillis() < endTime) {
			BackgroundTask backgroundTask =
				_backgroundTaskLocalService.fetchBackgroundTask(
					backgroundTaskId);

			if ((backgroundTask != null) && backgroundTask.isCompleted()) {
				return backgroundTask;
			}

			Thread.sleep(500);
		}

		throw new AssertionError(
			"Background task " + backgroundTaskId + " did not complete");
	}

	private static final String _VIRTUAL_HOSTNAME =
		StringUtil.toLowerCase(RandomTestUtil.randomString()) + ".com";

	private static final String _WEB_ID = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@DeleteAfterTestRun
	private final List<BackgroundTask> _backgroundTasks = new ArrayList<>();

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

	@DeleteAfterTestRun
	private final List<UserNotificationEvent> _userNotificationEvents =
		new ArrayList<>();

}