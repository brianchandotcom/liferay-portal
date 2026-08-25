/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class AddInstanceMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testProcessActionEncryptsDefaultAdminPassword()
		throws Exception {

		String defaultAdminPassword = RandomTestUtil.randomString();

		_processAction(TestPropsValues.getUser(), defaultAdminPassword);

		BackgroundTask backgroundTask = _fetchBackgroundTask();

		Assert.assertNotNull(backgroundTask);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		Serializable storedDefaultAdminPassword = taskContextMap.get(
			"defaultAdminPassword");

		Assert.assertNotNull(storedDefaultAdminPassword);
		Assert.assertNotEquals(
			defaultAdminPassword, storedDefaultAdminPassword);

		_waitForCompletion(backgroundTask.getBackgroundTaskId());

		_company = _companyLocalService.getCompanyByWebId(_WEB_ID);
	}

	@Test
	public void testProcessActionWhenAddIsAlreadyRunning() throws Exception {
		_backgroundTask = _addRunningBackgroundTask();

		int backgroundTasksCount =
			_backgroundTaskLocalService.getBackgroundTasksCount(
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				_TASK_EXECUTOR_CLASS_NAME);

		JSONObject jsonObject = _getResponseJSONObject(
			_processAction(TestPropsValues.getUser(), null));

		Assert.assertEquals(
			"A virtual instance with this web ID is already being added.",
			jsonObject.getString("error"));

		Assert.assertEquals(
			backgroundTasksCount,
			_backgroundTaskLocalService.getBackgroundTasksCount(
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				_TASK_EXECUTOR_CLASS_NAME));

		Assert.assertThrows(
			NoSuchCompanyException.class,
			() -> _companyLocalService.getCompanyByWebId(_WEB_ID));
	}

	@Test
	public void testProcessActionWhenUserIsNotOmniadmin() throws Exception {
		int backgroundTasksCount =
			_backgroundTaskLocalService.getBackgroundTasksCount(
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				_TASK_EXECUTOR_CLASS_NAME);

		_user = UserTestUtil.addUser();

		_processAction(_user, null);

		Assert.assertEquals(
			backgroundTasksCount,
			_backgroundTaskLocalService.getBackgroundTasksCount(
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				_TASK_EXECUTOR_CLASS_NAME));

		Assert.assertThrows(
			NoSuchCompanyException.class,
			() -> _companyLocalService.getCompanyByWebId(_WEB_ID));
	}

	private BackgroundTask _addRunningBackgroundTask() throws Exception {
		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.createBackgroundTask(
				_counterLocalService.increment());

		backgroundTask.setGroupId(BackgroundTaskConstants.GROUP_ID_DEFAULT);
		backgroundTask.setCompanyId(TestPropsValues.getCompanyId());
		backgroundTask.setUserId(TestPropsValues.getUserId());
		backgroundTask.setName("AddVirtualInstance#" + _WEB_ID);
		backgroundTask.setTaskExecutorClassName(_TASK_EXECUTOR_CLASS_NAME);
		backgroundTask.setCompleted(false);
		backgroundTask.setStatus(BackgroundTaskConstants.STATUS_IN_PROGRESS);

		return _backgroundTaskLocalService.updateBackgroundTask(backgroundTask);
	}

	private BackgroundTask _fetchBackgroundTask() {
		for (BackgroundTask backgroundTask :
				_backgroundTaskLocalService.getBackgroundTasks(
					BackgroundTaskConstants.GROUP_ID_DEFAULT,
					_TASK_EXECUTOR_CLASS_NAME)) {

			String name = backgroundTask.getName();

			if (name.endsWith("#" + _WEB_ID)) {
				_backgroundTasks.add(backgroundTask);

				return backgroundTask;
			}
		}

		return null;
	}

	private JSONObject _getResponseJSONObject(
			MockLiferayPortletActionResponse mockLiferayPortletActionResponse)
		throws Exception {

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				mockLiferayPortletActionResponse.getHttpServletResponse();

		return JSONFactoryUtil.createJSONObject(
			mockHttpServletResponse.getContentAsString());
	}

	private MockLiferayPortletActionResponse _processAction(
			User user, String defaultAdminPassword)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		mockLiferayPortletActionRequest.setAttribute(
			JavaConstants.JAKARTA_PORTLET_RESPONSE,
			mockLiferayPortletActionResponse);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setUser(user);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletActionRequest.setParameter("maxUsers", "0");
		mockLiferayPortletActionRequest.setParameter("mx", _VIRTUAL_HOSTNAME);
		mockLiferayPortletActionRequest.setParameter(
			"virtualHostname", _VIRTUAL_HOSTNAME);
		mockLiferayPortletActionRequest.setParameter("webId", _WEB_ID);

		if (defaultAdminPassword != null) {
			mockLiferayPortletActionRequest.setParameter(
				"defaultAdminPassword", defaultAdminPassword);
		}

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		return mockLiferayPortletActionResponse;
	}

	private void _waitForCompletion(long backgroundTaskId) throws Exception {
		long endTime = System.currentTimeMillis() + 600000;

		while (System.currentTimeMillis() < endTime) {
			BackgroundTask backgroundTask =
				_backgroundTaskLocalService.fetchBackgroundTask(
					backgroundTaskId);

			if ((backgroundTask != null) && backgroundTask.isCompleted()) {
				return;
			}

			Thread.sleep(500);
		}

		throw new AssertionError(
			"Background task " + backgroundTaskId + " did not complete");
	}

	private static final String _TASK_EXECUTOR_CLASS_NAME =
		"com.liferay.portal.instances.web.internal.background.task." +
			"AddVirtualInstanceBackgroundTaskExecutor";

	private static final String _VIRTUAL_HOSTNAME =
		StringUtil.toLowerCase(RandomTestUtil.randomString()) + ".com";

	private static final String _WEB_ID = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	@DeleteAfterTestRun
	private BackgroundTask _backgroundTask;

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@DeleteAfterTestRun
	private final List<BackgroundTask> _backgroundTasks = new ArrayList<>();

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject(filter = "mvc.command.name=/portal_instances/add_instance")
	private MVCActionCommand _mvcActionCommand;

	@DeleteAfterTestRun
	private User _user;

}