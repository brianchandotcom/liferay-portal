/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.background.task.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_webId = StringUtil.toLowerCase(RandomTestUtil.randomString());

		_virtualHostname = _webId + ".com";
	}

	@After
	public void tearDown() throws Exception {
		for (UserNotificationEvent userNotificationEvent :
				_userNotificationEvents) {

			_userNotificationEventLocalService.deleteUserNotificationEvent(
				userNotificationEvent);
		}
	}

	@Test
	public void testExecute() throws Exception {
		BackgroundTask backgroundTask = _addBackgroundTask(null, null);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_SUCCESSFUL,
			backgroundTask.getStatus());

		_company = _companyLocalService.getCompanyByWebId(_webId);

		JSONObject payloadJSONObject = _getPayloadJSONObject();

		Assert.assertEquals(
			BackgroundTaskConstants.LABEL_SUCCESSFUL,
			payloadJSONObject.getString("status"));
		Assert.assertEquals(_webId, payloadJSONObject.getString("webId"));
		Assert.assertEquals(
			_company.getCompanyId(), payloadJSONObject.getLong("companyId"));

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
			BackgroundTaskConstants.LABEL_FAILED,
			payloadJSONObject.getString("status"));
		Assert.assertEquals(
			"please-enter-a-valid-email-address",
			payloadJSONObject.getString("errorMessage"));

		_company = _companyLocalService.getCompanyByWebId(_webId);
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

		_company = _companyLocalService.getCompanyByWebId(_webId);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		Assert.assertNotEquals(
			defaultAdminPassword, taskContextMap.get("defaultAdminPassword"));

		String emailAddress =
			PropsUtil.get(PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX) +
				StringPool.AT + _virtualHostname;

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
				company.getWebId(), _virtualHostname, _virtualHostname, 0));
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
				"mx", _virtualHostname
			).put(
				"siteInitializerKey", StringPool.BLANK
			).put(
				"virtualHostname", _virtualHostname
			).put(
				"webId", _webId
			).build();

		BackgroundTask backgroundTask =
			_backgroundTaskManager.addBackgroundTask(
				TestPropsValues.getUserId(),
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				"AddVirtualInstance#" + _webId,
				"com.liferay.portal.instances.web.internal.background.task." +
					"AddVirtualInstanceBackgroundTaskExecutor",
				taskContextMap, new ServiceContext());

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

			if (webId.equals(_webId)) {
				_userNotificationEvents.add(userNotificationEvent);

				return payloadJSONObject;
			}
		}

		throw new AssertionError(
			"No user notification event was sent for web ID " + _webId);
	}

	private BackgroundTask _waitForCompletion(long backgroundTaskId)
		throws Exception {

		long endTime = System.currentTimeMillis() + 600000;

		while (System.currentTimeMillis() < endTime) {
			BackgroundTask backgroundTask =
				_backgroundTaskManager.fetchBackgroundTask(backgroundTaskId);

			if ((backgroundTask != null) && backgroundTask.isCompleted()) {
				return backgroundTask;
			}

			Thread.sleep(500);
		}

		throw new AssertionError(
			"Background task " + backgroundTaskId + " did not complete");
	}

	@Inject
	private BackgroundTaskManager _backgroundTaskManager;

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

	private final List<UserNotificationEvent> _userNotificationEvents =
		new ArrayList<>();
	private String _virtualHostname;
	private String _webId;

}