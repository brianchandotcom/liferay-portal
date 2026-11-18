/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.launch.rest.client.http.HttpInvoker;
import com.liferay.launch.rest.client.resource.v1_0.LaunchResource;
import com.liferay.launch.set.publisher.LaunchSetPublisher;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Moreno
 */
@FeatureFlag("LPD-72278")
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class LaunchResourceTest extends BaseLaunchResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		for (BackgroundTask backgroundTask :
				_backgroundTaskManager.getBackgroundTasks(
					BackgroundTaskConstants.GROUP_ID_DEFAULT,
					_TASK_EXECUTOR_CLASS_NAME)) {

			_backgroundTaskManager.deleteBackgroundTask(
				backgroundTask.getBackgroundTaskId());
		}

		super.tearDown();
	}

	@Override
	@Test
	public void testPostLaunchPublish() throws Exception {
		_testPostLaunchPublish();
		_testPostLaunchPublishWithEmptyLaunch();
		_testPostLaunchPublishWithFeatureFlagDisabled();
		_testPostLaunchPublishWithMissingLaunch();
		_testPostLaunchPublishWithoutViewPermission();
		_testPostLaunchPublishWithScheduledLaunch();
	}

	private ObjectEntry _addLaunchEntryObjectEntry(long launchSetId)
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _addObjectEntry(
			"LaunchEntry",
			HashMapBuilder.<String, Serializable>put(
				"baseVersionKey", String.valueOf(journalArticle.getVersion())
			).put(
				"className", JournalArticle.class.getName()
			).put(
				"classPK", journalArticle.getResourcePrimKey()
			).put(
				"r_launchSetToLaunchEntries_c_launchSetId", launchSetId
			).build());
	}

	private ObjectEntry _addLaunchSetObjectEntry() throws Exception {
		return _addObjectEntry(
			"LaunchSet",
			HashMapBuilder.<String, Serializable>put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).build());
	}

	private ObjectEntry _addObjectEntry(
			String objectDefinitionName, Map<String, Serializable> values)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				TestPropsValues.getCompanyId(), objectDefinitionName);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()), values,
			serviceContext);
	}

	private void _assertPostLaunchPublishProblem(
			int expectedStatusCode, ObjectEntry launchSetObjectEntry,
			String messageKey)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			launchResource.postLaunchPublishHttpResponse(
				launchSetObjectEntry.getObjectEntryId());

		assertHttpResponseStatusCode(expectedStatusCode, httpResponse);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			httpResponse.getContent());

		Assert.assertEquals(
			_language.format(
				LocaleUtil.getDefault(), messageKey,
				launchSetObjectEntry.getTitleValue(), false),
			jsonObject.getString("title"));
	}

	private void _assertStatus(int expectedStatus, long launchSetId)
		throws Exception {

		ObjectEntry launchSetObjectEntry =
			_objectEntryLocalService.getObjectEntry(launchSetId);

		Assert.assertEquals(expectedStatus, launchSetObjectEntry.getStatus());
	}

	private void _testPostLaunchPublish() throws Exception {
		ObjectEntry launchSetObjectEntry = _addLaunchSetObjectEntry();

		long launchSetId = launchSetObjectEntry.getObjectEntryId();

		_addLaunchEntryObjectEntry(launchSetId);

		assertHttpResponseStatusCode(
			202, launchResource.postLaunchPublishHttpResponse(launchSetId));

		_assertStatus(WorkflowConstants.STATUS_APPROVED, launchSetId);

		List<BackgroundTask> backgroundTasks = ListUtil.filter(
			_backgroundTaskManager.getBackgroundTasks(
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				_TASK_EXECUTOR_CLASS_NAME),
			backgroundTask -> Objects.equals(
				backgroundTask.getName(), String.valueOf(launchSetId)));

		Assert.assertEquals(
			backgroundTasks.toString(), 1, backgroundTasks.size());

		BackgroundTask backgroundTask = backgroundTasks.get(0);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_SUCCESSFUL,
			backgroundTask.getStatus());

		_assertPostLaunchPublishProblem(
			409, launchSetObjectEntry, "the-launch-x-was-already-published");
	}

	private void _testPostLaunchPublishWithEmptyLaunch() throws Exception {
		ObjectEntry launchSetObjectEntry = _addLaunchSetObjectEntry();

		_assertPostLaunchPublishProblem(
			400, launchSetObjectEntry,
			"the-launch-x-must-contain-at-least-one-asset");

		_assertStatus(
			WorkflowConstants.STATUS_DRAFT,
			launchSetObjectEntry.getObjectEntryId());
	}

	private void _testPostLaunchPublishWithFeatureFlagDisabled()
		throws Exception {

		ObjectEntry launchSetObjectEntry = _addLaunchSetObjectEntry();

		long launchSetId = launchSetObjectEntry.getObjectEntryId();

		try (SafeCloseable safeCloseable =
				FeatureFlagTestUtil.setFeatureFlagsWithSafeCloseable(
					false, "LPD-72278")) {

			assertHttpResponseStatusCode(
				404, launchResource.postLaunchPublishHttpResponse(launchSetId));
		}

		_assertStatus(WorkflowConstants.STATUS_DRAFT, launchSetId);
	}

	private void _testPostLaunchPublishWithMissingLaunch() throws Exception {
		assertHttpResponseStatusCode(
			404,
			launchResource.postLaunchPublishHttpResponse(
				RandomTestUtil.randomLong()));

		ObjectEntry launchEntryObjectEntry = _addObjectEntry(
			"LaunchEntry",
			HashMapBuilder.<String, Serializable>put(
				"className", RandomTestUtil.randomString()
			).put(
				"classPK", RandomTestUtil.randomInt()
			).put(
				"r_launchSetToLaunchEntries_c_launchSetId",
				() -> {
					ObjectEntry launchSetObjectEntry =
						_addLaunchSetObjectEntry();

					return launchSetObjectEntry.getObjectEntryId();
				}
			).build());

		assertHttpResponseStatusCode(
			404,
			launchResource.postLaunchPublishHttpResponse(
				launchEntryObjectEntry.getObjectEntryId()));
	}

	private void _testPostLaunchPublishWithScheduledLaunch() throws Exception {
		ObjectEntry launchSetObjectEntry = _addLaunchSetObjectEntry();

		long launchSetId = launchSetObjectEntry.getObjectEntryId();

		_launchSetPublisher.schedulePublish(
			new Date(System.currentTimeMillis() + Time.DAY), launchSetId,
			TestPropsValues.getUserId());

		try {
			_assertPostLaunchPublishProblem(
				409, launchSetObjectEntry,
				"the-launch-x-is-scheduled-to-be-published");

			_assertStatus(WorkflowConstants.STATUS_SCHEDULED, launchSetId);
		}
		finally {
			_launchSetPublisher.unschedulePublish(launchSetId);
		}
	}

	private void _testPostLaunchPublishWithoutViewPermission()
		throws Exception {

		String password = RandomTestUtil.randomString();

		User user = UserTestUtil.addUser(testCompany, password);

		LaunchResource launchResource = LaunchResource.builder(
		).authentication(
			user.getEmailAddress(), password
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();

		ObjectEntry launchSetObjectEntry = _addLaunchSetObjectEntry();

		long launchSetId = launchSetObjectEntry.getObjectEntryId();

		assertHttpResponseStatusCode(
			403, launchResource.postLaunchPublishHttpResponse(launchSetId));

		_assertStatus(WorkflowConstants.STATUS_DRAFT, launchSetId);
	}

	private static final String _TASK_EXECUTOR_CLASS_NAME =
		"com.liferay.launch.internal.background.task." +
			"LaunchSetPublishBackgroundTaskExecutor";

	@Inject
	private BackgroundTaskManager _backgroundTaskManager;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Language _language;

	@Inject
	private LaunchSetPublisher _launchSetPublisher;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}