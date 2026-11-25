/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.publisher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.launch.entry.manager.LaunchEntryManager;
import com.liferay.launch.entry.manager.LaunchEntryManagerRegistry;
import com.liferay.launch.publisher.LaunchPublisher;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockActionResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 * @author Jose Luis Navarro
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class LaunchPublisherTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_launchObjectEntry = _addLaunchObjectEntry();
	}

	@After
	public void tearDown() throws Exception {
		for (BackgroundTask backgroundTask :
				_backgroundTaskManager.getBackgroundTasks(
					BackgroundTaskConstants.GROUP_ID_DEFAULT,
					_BACKGROUND_TASK_EXECUTOR_CLASS_NAME)) {

			_backgroundTaskManager.deleteBackgroundTask(
				backgroundTask.getBackgroundTaskId());
		}

		_launchPublisher.unschedulePublish(
			_launchObjectEntry.getObjectEntryId());

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testPublish() throws Throwable {

		// Launch deleted before its background task runs

		ObjectEntry deletedLaunchObjectEntry = _addLaunchObjectEntry();

		long deletedLaunchId = deletedLaunchObjectEntry.getObjectEntryId();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.ERROR)) {

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_launchPublisher.publish(
						deletedLaunchId, TestPropsValues.getUserId());

					_objectEntryLocalService.deleteObjectEntry(deletedLaunchId);

					return null;
				});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertTrue(throwable instanceof NoSuchObjectEntryException);

			String message = StringBundler.concat(
				"Unable to publish launch ", deletedLaunchId, ": ",
				throwable.getMessage());

			Assert.assertEquals(message, logEntry.getMessage());

			_assertBackgroundTaskStatuses(
				deletedLaunchId, BackgroundTaskConstants.STATUS_FAILED);

			List<BackgroundTask> backgroundTasks = _getBackgroundTasks(
				deletedLaunchId);

			BackgroundTask backgroundTask = backgroundTasks.get(0);

			Assert.assertEquals(message, backgroundTask.getStatusMessage());
		}

		// Launch that is already being published

		ObjectEntry publishingLaunchObjectEntry = _addLaunchObjectEntry();

		long publishingLaunchId =
			publishingLaunchObjectEntry.getObjectEntryId();

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				_launchPublisher.publish(
					publishingLaunchId, TestPropsValues.getUserId());

				_assertPublishFails(
					publishingLaunchId, "is already being published");

				return null;
			});

		_assertBackgroundTaskStatuses(
			publishingLaunchId, BackgroundTaskConstants.STATUS_SUCCESSFUL);

		// Launch that was already published

		ObjectEntry publishedLaunchObjectEntry = _addLaunchObjectEntry();

		long publishedLaunchId = publishedLaunchObjectEntry.getObjectEntryId();

		_launchPublisher.publish(
			publishedLaunchId, TestPropsValues.getUserId());

		_assertPublishFails(publishedLaunchId, "was already published");

		_assertBackgroundTaskStatuses(
			publishedLaunchId, BackgroundTaskConstants.STATUS_SUCCESSFUL);

		// Launch that was already published when its background task runs

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_backgroundTaskManager.addBackgroundTask(
				TestPropsValues.getUserId(),
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				String.valueOf(publishedLaunchId),
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME,
				HashMapBuilder.<String, Serializable>put(
					"launchId", publishedLaunchId
				).build(),
				_serviceContext);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			String message =
				"Launch " + publishedLaunchId + " was already published";

			Assert.assertEquals(message, logEntry.getMessage());

			_assertBackgroundTaskStatuses(
				publishedLaunchId, BackgroundTaskConstants.STATUS_SUCCESSFUL,
				BackgroundTaskConstants.STATUS_FAILED);

			List<BackgroundTask> backgroundTasks = ListUtil.sort(
				_getBackgroundTasks(publishedLaunchId),
				Comparator.comparingLong(BackgroundTask::getBackgroundTaskId));

			BackgroundTask backgroundTask = backgroundTasks.get(1);

			Assert.assertEquals(message, backgroundTask.getStatusMessage());
		}

		publishedLaunchObjectEntry = _objectEntryLocalService.getObjectEntry(
			publishedLaunchId);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			publishedLaunchObjectEntry.getStatus());

		// Launch unscheduled before its background task runs

		ObjectEntry unscheduledLaunchObjectEntry = _addLaunchObjectEntry();

		long unscheduledLaunchId =
			unscheduledLaunchObjectEntry.getObjectEntryId();

		_launchPublisher.schedulePublish(
			new Date(System.currentTimeMillis() + Time.DAY),
			unscheduledLaunchId, TestPropsValues.getUserId());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.ERROR)) {

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_launchPublisher.publish(
						unscheduledLaunchId, TestPropsValues.getUserId());

					_launchPublisher.unschedulePublish(unscheduledLaunchId);

					return null;
				});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			String message =
				"Launch " + unscheduledLaunchId + " is no longer scheduled";

			Assert.assertEquals(message, logEntry.getMessage());

			_assertBackgroundTaskStatuses(
				unscheduledLaunchId, BackgroundTaskConstants.STATUS_FAILED);

			List<BackgroundTask> backgroundTasks = _getBackgroundTasks(
				unscheduledLaunchId);

			BackgroundTask backgroundTask = backgroundTasks.get(0);

			Assert.assertEquals(message, backgroundTask.getStatusMessage());
		}

		unscheduledLaunchObjectEntry = _objectEntryLocalService.getObjectEntry(
			unscheduledLaunchId);

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			unscheduledLaunchObjectEntry.getStatus());

		// Launch with a launch entry that has no launch entry manager

		long launchId = _launchObjectEntry.getObjectEntryId();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		ObjectEntry launchEntryObjectEntry = _addLaunchEntryObjectEntry(
			launchId, JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey(),
			String.valueOf(journalArticle.getVersion()));

		String title = RandomTestUtil.randomString();

		long draftId = _updateLaunchDraft(launchEntryObjectEntry, title);

		String unknownClassName = RandomTestUtil.randomString();

		ObjectEntry unknownLaunchEntryObjectEntry = _addLaunchEntryObjectEntry(
			launchId, unknownClassName, RandomTestUtil.randomInt(),
			RandomTestUtil.randomString());

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(
			_layoutLocalService.getLayout(
				_portal.getControlPanelPlid(TestPropsValues.getCompanyId())));
		themeDisplay.setUser(TestPropsValues.getUser());

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletActionRequest.setParameter(
			"launchId", String.valueOf(launchId));
		mockLiferayPortletActionRequest.setParameter(
			"redirect", "/" + RandomTestUtil.randomString());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest, new MockActionResponse());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertEquals(
				"No launch entry manager exists for " + unknownClassName,
				throwable.getMessage());
		}

		_assertBackgroundTaskStatuses(
			launchId, BackgroundTaskConstants.STATUS_FAILED);

		LaunchEntryManager launchEntryManager =
			_getJournalArticleLaunchEntryManager();

		LaunchEntryManager.Version version =
			launchEntryManager.fetchPublishedVersion(
				journalArticle.getResourcePrimKey());

		Assert.assertEquals(
			String.valueOf(journalArticle.getVersion()),
			version.getVersionKey());

		ObjectEntry launchObjectEntry = _objectEntryLocalService.getObjectEntry(
			launchId);

		Assert.assertNotEquals(
			WorkflowConstants.STATUS_APPROVED, launchObjectEntry.getStatus());

		// Launch with launch entries that can all be published

		_objectEntryLocalService.deleteObjectEntry(
			unknownLaunchEntryObjectEntry);

		_launchPublisher.publish(launchId, TestPropsValues.getUserId());

		_assertBackgroundTaskStatuses(
			launchId, BackgroundTaskConstants.STATUS_FAILED,
			BackgroundTaskConstants.STATUS_SUCCESSFUL);

		LaunchEntryManager.Version publishedVersion =
			launchEntryManager.fetchPublishedVersion(
				journalArticle.getResourcePrimKey());

		Assert.assertEquals(title, _getTitle(journalArticle));
		Assert.assertNotEquals(
			String.valueOf(journalArticle.getVersion()),
			publishedVersion.getVersionKey());

		_assertPublished(
			draftId, launchEntryObjectEntry, launchEntryManager,
			publishedVersion);

		launchObjectEntry = _objectEntryLocalService.getObjectEntry(launchId);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, launchObjectEntry.getStatus());

		// Object entry that is not a launch

		Assert.assertThrows(
			PortalException.class,
			() -> _launchPublisher.publish(
				launchEntryObjectEntry.getObjectEntryId(),
				TestPropsValues.getUserId()));

		// Other launch with a launch draft of the same journal article

		ObjectEntry otherLaunchObjectEntry = _addLaunchObjectEntry();

		ObjectEntry otherLaunchEntryObjectEntry = _addLaunchEntryObjectEntry(
			otherLaunchObjectEntry.getObjectEntryId(),
			JournalArticle.class.getName(), journalArticle.getResourcePrimKey(),
			String.valueOf(journalArticle.getVersion()));

		String otherTitle = RandomTestUtil.randomString();

		long otherDraftId = _updateLaunchDraft(
			otherLaunchEntryObjectEntry, otherTitle);

		JournalTestUtil.updateArticle(
			_journalArticleLocalService.getLatestArticle(
				journalArticle.getResourcePrimKey(),
				WorkflowConstants.STATUS_APPROVED),
			RandomTestUtil.randomString());

		_launchPublisher.publish(
			otherLaunchObjectEntry.getObjectEntryId(),
			TestPropsValues.getUserId());

		LaunchEntryManager.Version otherPublishedVersion =
			launchEntryManager.fetchPublishedVersion(
				journalArticle.getResourcePrimKey());

		Assert.assertEquals(otherTitle, _getTitle(journalArticle));
		Assert.assertNotEquals(
			publishedVersion.getVersionKey(),
			otherPublishedVersion.getVersionKey());

		_assertPublished(
			otherDraftId, otherLaunchEntryObjectEntry, launchEntryManager,
			otherPublishedVersion);
	}

	@Test
	public void testSchedulePublish() throws Exception {
		long launchId = _launchObjectEntry.getObjectEntryId();

		ObjectEntry launchEntryObjectEntry = _addLaunchEntryObjectEntry(
			launchId, RandomTestUtil.randomString(), RandomTestUtil.randomInt(),
			RandomTestUtil.randomString());

		Assert.assertThrows(
			PortalException.class,
			() -> _launchPublisher.schedulePublish(
				new Date(System.currentTimeMillis() + Time.DAY),
				launchEntryObjectEntry.getObjectEntryId(),
				TestPropsValues.getUserId()));

		Assert.assertThrows(
			SchedulerException.class,
			() -> _launchPublisher.schedulePublish(
				new Date(System.currentTimeMillis() + (Time.SECOND * 30)),
				launchId, TestPropsValues.getUserId()));
		Assert.assertThrows(
			SchedulerException.class,
			() -> _launchPublisher.schedulePublish(
				new Date(System.currentTimeMillis() - Time.MINUTE), launchId,
				TestPropsValues.getUserId()));
		Assert.assertThrows(
			SchedulerException.class,
			() -> _launchPublisher.schedulePublish(
				null, launchId, TestPropsValues.getUserId()));

		_assertScheduledDateAndStatus(null, WorkflowConstants.STATUS_DRAFT);

		Date date = new Date(System.currentTimeMillis() + (Time.MINUTE * 2));

		_launchPublisher.schedulePublish(
			date, launchId, TestPropsValues.getUserId());

		_assertScheduledDateAndStatus(date, WorkflowConstants.STATUS_SCHEDULED);

		Assert.assertThrows(
			PortalException.class,
			() -> _launchPublisher.schedulePublish(
				new Date(System.currentTimeMillis() + Time.DAY), launchId,
				TestPropsValues.getUserId()));
		_assertScheduledDateAndStatus(date, WorkflowConstants.STATUS_SCHEDULED);
	}

	@Test
	public void testUnschedulePublish() throws Exception {
		long launchId = _launchObjectEntry.getObjectEntryId();

		ObjectEntry launchEntryObjectEntry = _addLaunchEntryObjectEntry(
			launchId, RandomTestUtil.randomString(), RandomTestUtil.randomInt(),
			RandomTestUtil.randomString());

		Assert.assertThrows(
			PortalException.class,
			() -> _launchPublisher.unschedulePublish(
				launchEntryObjectEntry.getObjectEntryId()));

		_launchPublisher.unschedulePublish(launchId);

		_assertScheduledDateAndStatus(null, WorkflowConstants.STATUS_DRAFT);

		_launchPublisher.schedulePublish(
			new Date(System.currentTimeMillis() + Time.DAY), launchId,
			TestPropsValues.getUserId());

		_launchPublisher.unschedulePublish(launchId);

		_assertScheduledDateAndStatus(null, WorkflowConstants.STATUS_DRAFT);

		Date date = new Date(System.currentTimeMillis() + Time.WEEK);

		_launchPublisher.schedulePublish(
			date, launchId, TestPropsValues.getUserId());

		_assertScheduledDateAndStatus(date, WorkflowConstants.STATUS_SCHEDULED);
	}

	private ObjectEntry _addLaunchEntryObjectEntry(
			long launchId, String className, long classPK,
			String baseVersionKey)
		throws Exception {

		return _addObjectEntry(
			"LaunchEntry",
			HashMapBuilder.<String, Serializable>put(
				"baseVersionKey", baseVersionKey
			).put(
				"className", className
			).put(
				"classPK", classPK
			).put(
				"r_launchToLaunchEntries_c_launchId", launchId
			).build());
	}

	private ObjectEntry _addLaunchObjectEntry() throws Exception {
		return _addObjectEntry(
			"Launch",
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
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()), values,
			serviceContext);
	}

	private void _assertBackgroundTaskStatuses(
		long launchId, int... expectedStatuses) {

		List<BackgroundTask> backgroundTasks = ListUtil.sort(
			_getBackgroundTasks(launchId),
			Comparator.comparingLong(BackgroundTask::getBackgroundTaskId));

		Assert.assertEquals(
			backgroundTasks.toString(), expectedStatuses.length,
			backgroundTasks.size());

		for (int i = 0; i < expectedStatuses.length; i++) {
			BackgroundTask backgroundTask = backgroundTasks.get(i);

			Assert.assertEquals(
				expectedStatuses[i], backgroundTask.getStatus());
		}
	}

	private void _assertPublishFails(long launchId, String messageSuffix) {
		PortalException portalException = Assert.assertThrows(
			PortalException.class,
			() -> _launchPublisher.publish(
				launchId, TestPropsValues.getUserId()));

		Assert.assertTrue(
			StringUtil.endsWith(portalException.getMessage(), messageSuffix));
	}

	private void _assertPublished(
			long draftId, ObjectEntry launchEntryObjectEntry,
			LaunchEntryManager launchEntryManager,
			LaunchEntryManager.Version publishedVersion)
		throws Exception {

		Map<String, Serializable> values = _getValues(launchEntryObjectEntry);

		Assert.assertEquals(
			publishedVersion.getVersionKey(),
			MapUtil.getString(values, "versionKey"));
		Assert.assertEquals(0, MapUtil.getLong(values, "draftId"));

		Assert.assertNull(
			launchEntryManager.fetchDraft(
				MapUtil.getLong(values, "classPK"), draftId));
	}

	private void _assertScheduledDateAndStatus(
			Date expectedDate, int expectedStatus)
		throws Exception {

		long launchId = _launchObjectEntry.getObjectEntryId();

		ObjectEntry launchObjectEntry = _objectEntryLocalService.getObjectEntry(
			launchId);

		Assert.assertEquals(expectedStatus, launchObjectEntry.getStatus());

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				StringBundler.concat(
					launchId, StringPool.AT, launchObjectEntry.getCompanyId()),
				"liferay/launch_scheduled_publish", StorageType.PERSISTED);

		Date scheduledDate = null;

		if (schedulerResponse != null) {
			scheduledDate = _schedulerEngineHelper.getStartDate(
				schedulerResponse);
		}

		Map<String, Serializable> values = launchObjectEntry.getValues();

		Date scheduledDateValue = (Date)values.get("scheduledDate");

		if (expectedDate == null) {
			Assert.assertNull(scheduledDate);
			Assert.assertNull(scheduledDateValue);

			return;
		}

		Assert.assertEquals(expectedDate, scheduledDate);
		Assert.assertEquals(
			expectedDate.getTime() / Time.SECOND,
			scheduledDateValue.getTime() / Time.SECOND);
	}

	private List<BackgroundTask> _getBackgroundTasks(long launchId) {
		return ListUtil.filter(
			_backgroundTaskManager.getBackgroundTasks(
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME),
			backgroundTask -> Objects.equals(
				backgroundTask.getName(), String.valueOf(launchId)));
	}

	private LaunchEntryManager _getJournalArticleLaunchEntryManager() {
		return _launchEntryManagerRegistry.getLaunchEntryManager(
			JournalArticle.class.getName());
	}

	private String _getTitle(JournalArticle journalArticle) throws Exception {
		JournalArticle latestJournalArticle =
			_journalArticleLocalService.getLatestArticle(
				journalArticle.getResourcePrimKey(),
				WorkflowConstants.STATUS_APPROVED);

		return latestJournalArticle.getTitle(LocaleUtil.getSiteDefault());
	}

	private Map<String, Serializable> _getValues(ObjectEntry objectEntry)
		throws Exception {

		ObjectEntry reloadedObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				objectEntry.getObjectEntryId());

		return reloadedObjectEntry.getValues();
	}

	private long _updateLaunchDraft(
			ObjectEntry launchEntryObjectEntry, String title)
		throws Exception {

		long draftId = MapUtil.getLong(
			_getValues(launchEntryObjectEntry), "draftId");

		JournalArticle launchDraftJournalArticle =
			_journalArticleLocalService.getLatestArticle(
				draftId, WorkflowConstants.STATUS_ANY, false);

		ServiceContext launchDraftServiceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		launchDraftServiceContext.setWorkflowAction(
			WorkflowConstants.ACTION_SAVE_DRAFT);

		_journalArticleLocalService.updateArticle(
			TestPropsValues.getUserId(), launchDraftJournalArticle.getGroupId(),
			launchDraftJournalArticle.getFolderId(),
			launchDraftJournalArticle.getArticleId(),
			launchDraftJournalArticle.getVersion(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), title
			).build(),
			launchDraftJournalArticle.getDescriptionMap(),
			launchDraftJournalArticle.getContent(),
			launchDraftJournalArticle.getLayoutUuid(),
			launchDraftServiceContext);

		return draftId;
	}

	private static final String _BACKGROUND_TASK_EXECUTOR_CLASS_NAME =
		"com.liferay.launch.internal.background.task." +
			"LaunchPublishBackgroundTaskExecutor";

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Inject
	private BackgroundTaskManager _backgroundTaskManager;

	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private LaunchEntryManagerRegistry _launchEntryManagerRegistry;

	private ObjectEntry _launchObjectEntry;

	@Inject
	private LaunchPublisher _launchPublisher;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "mvc.command.name=/launch/publish_launch")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private SchedulerEngineHelper _schedulerEngineHelper;

	private ServiceContext _serviceContext;

}