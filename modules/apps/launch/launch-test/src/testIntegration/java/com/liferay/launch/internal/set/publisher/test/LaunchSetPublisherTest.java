/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.set.publisher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.launch.entry.manager.LaunchEntryManager;
import com.liferay.launch.entry.manager.LaunchEntryManagerRegistry;
import com.liferay.launch.set.publisher.LaunchSetPublisher;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alejandro Tardín
 * @author Jose Luis Navarro
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class LaunchSetPublisherTest {

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

		_launchSetObjectEntry = _addLaunchSetObjectEntry();
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

		if (_failedLaunchSetObjectEntry != null) {
			_launchSetPublisher.unschedulePublish(
				_failedLaunchSetObjectEntry.getObjectEntryId());
		}

		_launchSetPublisher.unschedulePublish(
			_launchSetObjectEntry.getObjectEntryId());

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testPublish() throws Throwable {

		// Launch entry whose publish fails with a blank message

		_assertPublishErrorMessage(
			PortalException.class.getName(), StringPool.SPACE);

		// Launch entry whose publish fails with a long message

		String longMessage = RandomTestUtil.randomString(70000);

		_assertPublishErrorMessage(
			longMessage.substring(0, 65000), longMessage);

		// Launch entry whose publish fails with a long message of characters
		// outside the BMP

		longMessage = "a" + "\uD83D\uDE00".repeat(35000);

		_assertPublishErrorMessage(
			longMessage.substring(0, 64999), longMessage);

		// Launch entry whose publish fails with a message at the length limit

		longMessage = RandomTestUtil.randomString(65000);

		_assertPublishErrorMessage(longMessage, longMessage);

		// Launch entry with a title longer than a text field

		String longTitle = RandomTestUtil.randomString(300);

		JournalArticle longTitleJournalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, longTitle,
			RandomTestUtil.randomString());

		ObjectEntry longTitleLaunchSetObjectEntry = _addLaunchSetObjectEntry();

		long longTitleLaunchSetId =
			longTitleLaunchSetObjectEntry.getObjectEntryId();

		ObjectEntry longTitleLaunchEntryObjectEntry =
			_addLaunchEntryObjectEntry(
				longTitleLaunchSetId, JournalArticle.class.getName(),
				longTitleJournalArticle.getResourcePrimKey(),
				String.valueOf(longTitleJournalArticle.getVersion()));

		long longTitleDraftId = MapUtil.getLong(
			_getValues(longTitleLaunchEntryObjectEntry), "draftId");

		LaunchEntryManager launchEntryManager =
			_getJournalArticleLaunchEntryManager();

		launchEntryManager.deleteDraft(
			longTitleJournalArticle.getResourcePrimKey(), longTitleDraftId);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.WARN)) {

			_launchSetPublisher.publish(
				longTitleLaunchSetId, TestPropsValues.getUserId());

			_assertLogEntries(
				NoSuchArticleException.class,
				"No JournalArticle exists with the key {resourcePrimKey=" +
					longTitleDraftId + "}",
				longTitleLaunchEntryObjectEntry, longTitleLaunchSetId,
				logCapture);
		}

		_assertBackgroundTaskStatuses(
			longTitleLaunchSetId, BackgroundTaskConstants.STATUS_FAILED);

		List<ObjectEntry> launchSetErrorObjectEntries =
			_getLaunchSetErrorObjectEntries(longTitleLaunchSetObjectEntry);

		Assert.assertEquals(
			launchSetErrorObjectEntries.toString(), 1,
			launchSetErrorObjectEntries.size());

		ObjectEntry launchSetErrorObjectEntry = launchSetErrorObjectEntries.get(
			0);

		Map<String, Serializable> values =
			launchSetErrorObjectEntry.getValues();

		Assert.assertEquals(
			longTitleLaunchEntryObjectEntry.getObjectEntryId(),
			MapUtil.getLong(values, "launchEntryId"));
		Assert.assertEquals(
			longTitle, MapUtil.getString(values, "launchEntryTitle"));

		// Launch set approved by another publish while this one fails

		ObjectEntry concurrentLaunchSetObjectEntry = _addLaunchSetObjectEntry();

		long concurrentLaunchSetId =
			concurrentLaunchSetObjectEntry.getObjectEntryId();

		String concurrentClassName = RandomTestUtil.randomString();

		ObjectEntry concurrentLaunchEntryObjectEntry =
			_addLaunchEntryObjectEntry(
				concurrentLaunchSetId, concurrentClassName,
				RandomTestUtil.randomInt(), null);

		String concurrentMessage = RandomTestUtil.randomString();

		ServiceRegistration<LaunchEntryManager> serviceRegistration =
			_registerLaunchEntryManager(
				concurrentClassName,
				() -> {
					TransactionInvokerUtil.invoke(
						TransactionConfig.Factory.create(
							Propagation.REQUIRES_NEW,
							new Class<?>[] {Exception.class}),
						() -> _objectEntryLocalService.updateStatus(
							TestPropsValues.getUserId(), concurrentLaunchSetId,
							WorkflowConstants.STATUS_APPROVED,
							_serviceContext));

					throw new PortalException(concurrentMessage);
				});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.WARN)) {

			_launchSetPublisher.publish(
				concurrentLaunchSetId, TestPropsValues.getUserId());

			_assertLogEntries(
				PortalException.class, concurrentMessage,
				concurrentLaunchEntryObjectEntry, concurrentLaunchSetId,
				logCapture);
		}
		finally {
			serviceRegistration.unregister();
		}

		_assertBackgroundTaskStatuses(
			concurrentLaunchSetId, BackgroundTaskConstants.STATUS_FAILED);

		concurrentLaunchSetObjectEntry =
			_objectEntryLocalService.getObjectEntry(concurrentLaunchSetId);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			concurrentLaunchSetObjectEntry.getStatus());

		launchSetErrorObjectEntries = _getLaunchSetErrorObjectEntries(
			concurrentLaunchSetObjectEntry);

		Assert.assertEquals(
			launchSetErrorObjectEntries.toString(), 0,
			launchSetErrorObjectEntries.size());

		// Launch set deleted before its background task runs

		ObjectEntry deletedLaunchSetObjectEntry = _addLaunchSetObjectEntry();

		long deletedLaunchSetId =
			deletedLaunchSetObjectEntry.getObjectEntryId();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.ERROR)) {

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_launchSetPublisher.publish(
						deletedLaunchSetId, TestPropsValues.getUserId());

					_objectEntryLocalService.deleteObjectEntry(
						deletedLaunchSetId);

					return null;
				});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertTrue(throwable instanceof NoSuchObjectEntryException);

			String message = StringBundler.concat(
				"Unable to publish launch set ", deletedLaunchSetId, ": ",
				throwable.getMessage());

			Assert.assertEquals(message, logEntry.getMessage());

			_assertBackgroundTaskStatuses(
				deletedLaunchSetId, BackgroundTaskConstants.STATUS_FAILED);

			List<BackgroundTask> backgroundTasks = _getBackgroundTasks(
				deletedLaunchSetId);

			BackgroundTask backgroundTask = backgroundTasks.get(0);

			Assert.assertEquals(message, backgroundTask.getStatusMessage());
		}

		// Launch set that failed to publish before its background task runs

		ObjectEntry failedLaunchSetObjectEntry = _addLaunchSetObjectEntry();

		long failedLaunchSetId = failedLaunchSetObjectEntry.getObjectEntryId();

		String className = RandomTestUtil.randomString();

		ObjectEntry failedLaunchEntryObjectEntry = _addLaunchEntryObjectEntry(
			failedLaunchSetId, className, RandomTestUtil.randomInt(),
			RandomTestUtil.randomString());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.WARN)) {

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					for (int i = 0; i < 2; i++) {
						_backgroundTaskManager.addBackgroundTask(
							TestPropsValues.getUserId(),
							BackgroundTaskConstants.GROUP_ID_DEFAULT,
							String.valueOf(failedLaunchSetId),
							_BACKGROUND_TASK_EXECUTOR_CLASS_NAME,
							HashMapBuilder.<String, Serializable>put(
								"launchSetId", failedLaunchSetId
							).build(),
							_serviceContext);
					}

					return null;
				});

			Map<String, LogEntry> logEntriesMap = _getLogEntriesMap(
				3, logCapture);

			_assertLogEntry(
				PortalException.class,
				"Unable to publish 1 launch entries of launch set " +
					failedLaunchSetId,
				logEntriesMap,
				"Unable to publish launch set " + failedLaunchSetId, "ERROR");
			_assertLogEntry(
				PortalException.class,
				"No launch entry manager exists for " + className,
				logEntriesMap,
				"Unable to publish launch entry " +
					failedLaunchEntryObjectEntry.getObjectEntryId(),
				"WARN");

			String message = StringBundler.concat(
				"Launch set ", failedLaunchSetId,
				" failed to publish after its background task was queued");

			LogEntry logEntry = logEntriesMap.get(message);

			Assert.assertNotNull(logEntriesMap.toString(), logEntry);
			Assert.assertEquals("ERROR", logEntry.getPriority());
			Assert.assertNull(logEntry.getThrowable());

			_assertBackgroundTaskStatuses(
				failedLaunchSetId, BackgroundTaskConstants.STATUS_FAILED,
				BackgroundTaskConstants.STATUS_FAILED);

			List<BackgroundTask> backgroundTasks = ListUtil.sort(
				_getBackgroundTasks(failedLaunchSetId),
				Comparator.comparingLong(BackgroundTask::getBackgroundTaskId));

			BackgroundTask backgroundTask = backgroundTasks.get(1);

			Assert.assertEquals(message, backgroundTask.getStatusMessage());
		}

		_assertPublishErrorsCount(1, failedLaunchSetId);

		// Launch set that is already being published

		ObjectEntry publishingLaunchSetObjectEntry = _addLaunchSetObjectEntry();

		long publishingLaunchSetId =
			publishingLaunchSetObjectEntry.getObjectEntryId();

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				_launchSetPublisher.publish(
					publishingLaunchSetId, TestPropsValues.getUserId());

				_assertPublishFails(
					publishingLaunchSetId, "is already being published");

				return null;
			});

		_assertBackgroundTaskStatuses(
			publishingLaunchSetId, BackgroundTaskConstants.STATUS_SUCCESSFUL);

		// Launch set that was already published

		ObjectEntry publishedLaunchSetObjectEntry = _addLaunchSetObjectEntry();

		long publishedLaunchSetId =
			publishedLaunchSetObjectEntry.getObjectEntryId();

		_launchSetPublisher.publish(
			publishedLaunchSetId, TestPropsValues.getUserId());

		_assertPublishFails(publishedLaunchSetId, "was already published");

		_assertBackgroundTaskStatuses(
			publishedLaunchSetId, BackgroundTaskConstants.STATUS_SUCCESSFUL);

		// Launch set that was already published when its background task runs

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_backgroundTaskManager.addBackgroundTask(
				TestPropsValues.getUserId(),
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				String.valueOf(publishedLaunchSetId),
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME,
				HashMapBuilder.<String, Serializable>put(
					"launchSetId", publishedLaunchSetId
				).build(),
				_serviceContext);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			String message =
				"Launch set " + publishedLaunchSetId + " was already published";

			Assert.assertEquals(message, logEntry.getMessage());

			_assertBackgroundTaskStatuses(
				publishedLaunchSetId, BackgroundTaskConstants.STATUS_SUCCESSFUL,
				BackgroundTaskConstants.STATUS_FAILED);

			List<BackgroundTask> backgroundTasks = ListUtil.sort(
				_getBackgroundTasks(publishedLaunchSetId),
				Comparator.comparingLong(BackgroundTask::getBackgroundTaskId));

			BackgroundTask backgroundTask = backgroundTasks.get(1);

			Assert.assertEquals(message, backgroundTask.getStatusMessage());
		}

		publishedLaunchSetObjectEntry = _objectEntryLocalService.getObjectEntry(
			publishedLaunchSetId);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			publishedLaunchSetObjectEntry.getStatus());

		// Launch set unscheduled before its background task runs

		ObjectEntry unscheduledLaunchSetObjectEntry =
			_addLaunchSetObjectEntry();

		long unscheduledLaunchSetId =
			unscheduledLaunchSetObjectEntry.getObjectEntryId();

		_launchSetPublisher.schedulePublish(
			new Date(System.currentTimeMillis() + Time.DAY),
			unscheduledLaunchSetId, TestPropsValues.getUserId());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.ERROR)) {

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_launchSetPublisher.publish(
						unscheduledLaunchSetId, TestPropsValues.getUserId());

					_launchSetPublisher.unschedulePublish(
						unscheduledLaunchSetId);

					return null;
				});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			String message =
				"Launch set " + unscheduledLaunchSetId +
					" is no longer scheduled";

			Assert.assertEquals(message, logEntry.getMessage());

			_assertBackgroundTaskStatuses(
				unscheduledLaunchSetId, BackgroundTaskConstants.STATUS_FAILED);

			List<BackgroundTask> backgroundTasks = _getBackgroundTasks(
				unscheduledLaunchSetId);

			BackgroundTask backgroundTask = backgroundTasks.get(0);

			Assert.assertEquals(message, backgroundTask.getStatusMessage());
		}

		unscheduledLaunchSetObjectEntry =
			_objectEntryLocalService.getObjectEntry(unscheduledLaunchSetId);

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			unscheduledLaunchSetObjectEntry.getStatus());

		// Launch set with launch entries that have no launch entry manager

		long launchSetId = _launchSetObjectEntry.getObjectEntryId();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		ObjectEntry launchEntryObjectEntry = _addLaunchEntryObjectEntry(
			launchSetId, JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey(),
			String.valueOf(journalArticle.getVersion()));

		String title = RandomTestUtil.randomString();

		long draftId = _updateLaunchDraft(launchEntryObjectEntry, title);

		String unknownClassName1 = RandomTestUtil.randomString();

		ObjectEntry unknownLaunchEntryObjectEntry1 = _addLaunchEntryObjectEntry(
			launchSetId, unknownClassName1, RandomTestUtil.randomInt(),
			RandomTestUtil.randomString());

		String unknownClassName2 = RandomTestUtil.randomString();

		ObjectEntry unknownLaunchEntryObjectEntry2 = _addLaunchEntryObjectEntry(
			launchSetId, unknownClassName2, RandomTestUtil.randomInt(),
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
			"launchSetId", String.valueOf(launchSetId));
		mockLiferayPortletActionRequest.setParameter(
			"redirect", "/" + RandomTestUtil.randomString());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.WARN)) {

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest, new MockActionResponse());

			Map<String, LogEntry> logEntriesMap = _getLogEntriesMap(
				3, logCapture);

			_assertLogEntry(
				PortalException.class,
				"No launch entry manager exists for " + unknownClassName1,
				logEntriesMap,
				"Unable to publish launch entry " +
					unknownLaunchEntryObjectEntry1.getObjectEntryId(),
				"WARN");
			_assertLogEntry(
				PortalException.class,
				"No launch entry manager exists for " + unknownClassName2,
				logEntriesMap,
				"Unable to publish launch entry " +
					unknownLaunchEntryObjectEntry2.getObjectEntryId(),
				"WARN");
			_assertLogEntry(
				PortalException.class,
				"Unable to publish 2 launch entries of launch set " +
					launchSetId,
				logEntriesMap, "Unable to publish launch set " + launchSetId,
				"ERROR");
		}

		_assertBackgroundTaskStatuses(
			launchSetId, BackgroundTaskConstants.STATUS_FAILED);

		List<BackgroundTask> backgroundTasks = _getBackgroundTasks(launchSetId);

		BackgroundTask backgroundTask = backgroundTasks.get(0);

		Assert.assertEquals(
			"Unable to publish 2 launch entries of launch set " + launchSetId,
			backgroundTask.getStatusMessage());

		LaunchEntryManager.Version version =
			launchEntryManager.fetchPublishedVersion(
				journalArticle.getResourcePrimKey());

		Assert.assertEquals(
			String.valueOf(journalArticle.getVersion()),
			version.getVersionKey());

		Assert.assertEquals(
			draftId,
			MapUtil.getLong(_getValues(launchEntryObjectEntry), "draftId"));

		ObjectEntry launchSetObjectEntry =
			_objectEntryLocalService.getObjectEntry(launchSetId);

		Assert.assertEquals(
			2,
			MapUtil.getLong(
				launchSetObjectEntry.getValues(), "publishErrorsCount"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, launchSetObjectEntry.getStatus());

		launchSetErrorObjectEntries = _getLaunchSetErrorObjectEntries(
			launchSetObjectEntry);

		Assert.assertEquals(
			launchSetErrorObjectEntries.toString(), 2,
			launchSetErrorObjectEntries.size());

		_assertLaunchSetError(
			unknownLaunchEntryObjectEntry1, launchSetErrorObjectEntries,
			"No launch entry manager exists for " + unknownClassName1);
		_assertLaunchSetError(
			unknownLaunchEntryObjectEntry2, launchSetErrorObjectEntries,
			"No launch entry manager exists for " + unknownClassName2);

		// Launch set with launch entries that can all be published

		_objectEntryLocalService.deleteObjectEntry(
			unknownLaunchEntryObjectEntry1);
		_objectEntryLocalService.deleteObjectEntry(
			unknownLaunchEntryObjectEntry2);

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				_launchSetPublisher.publish(
					launchSetId, TestPropsValues.getUserId());

				_assertPublishErrorsCount(0, launchSetId);

				return null;
			});

		_assertBackgroundTaskStatuses(
			launchSetId, BackgroundTaskConstants.STATUS_FAILED,
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

		launchSetObjectEntry = _objectEntryLocalService.getObjectEntry(
			launchSetId);

		Assert.assertEquals(
			0,
			MapUtil.getLong(
				launchSetObjectEntry.getValues(), "publishErrorsCount"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			launchSetObjectEntry.getStatus());

		launchSetErrorObjectEntries = _getLaunchSetErrorObjectEntries(
			launchSetObjectEntry);

		Assert.assertEquals(
			launchSetErrorObjectEntries.toString(), 0,
			launchSetErrorObjectEntries.size());

		// Object entry that is not a launch set

		Assert.assertThrows(
			PortalException.class,
			() -> _launchSetPublisher.publish(
				launchEntryObjectEntry.getObjectEntryId(),
				TestPropsValues.getUserId()));

		// Other launch set with a launch draft of the same journal article

		ObjectEntry otherLaunchSetObjectEntry = _addLaunchSetObjectEntry();

		ObjectEntry otherLaunchEntryObjectEntry = _addLaunchEntryObjectEntry(
			otherLaunchSetObjectEntry.getObjectEntryId(),
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

		_launchSetPublisher.publish(
			otherLaunchSetObjectEntry.getObjectEntryId(),
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
	public void testSchedulePublish() throws Throwable {
		long launchSetId = _launchSetObjectEntry.getObjectEntryId();

		ObjectEntry launchEntryObjectEntry = _addLaunchEntryObjectEntry(
			launchSetId, RandomTestUtil.randomString(),
			RandomTestUtil.randomInt(), RandomTestUtil.randomString());

		Assert.assertThrows(
			PortalException.class,
			() -> _launchSetPublisher.schedulePublish(
				new Date(System.currentTimeMillis() + Time.DAY),
				launchEntryObjectEntry.getObjectEntryId(),
				TestPropsValues.getUserId()));

		Assert.assertThrows(
			SchedulerException.class,
			() -> _launchSetPublisher.schedulePublish(
				new Date(System.currentTimeMillis() + (Time.SECOND * 30)),
				launchSetId, TestPropsValues.getUserId()));
		Assert.assertThrows(
			SchedulerException.class,
			() -> _launchSetPublisher.schedulePublish(
				new Date(System.currentTimeMillis() - Time.MINUTE), launchSetId,
				TestPropsValues.getUserId()));
		Assert.assertThrows(
			SchedulerException.class,
			() -> _launchSetPublisher.schedulePublish(
				null, launchSetId, TestPropsValues.getUserId()));

		_assertScheduledDateAndStatus(
			null, WorkflowConstants.STATUS_DRAFT, launchSetId);

		Date date = new Date(System.currentTimeMillis() + (Time.MINUTE * 2));

		_launchSetPublisher.schedulePublish(
			date, launchSetId, TestPropsValues.getUserId());

		_assertScheduledDateAndStatus(
			date, WorkflowConstants.STATUS_SCHEDULED, launchSetId);

		Assert.assertThrows(
			PortalException.class,
			() -> _launchSetPublisher.schedulePublish(
				new Date(System.currentTimeMillis() + Time.DAY), launchSetId,
				TestPropsValues.getUserId()));
		_assertScheduledDateAndStatus(
			date, WorkflowConstants.STATUS_SCHEDULED, launchSetId);

		// Failed launch set

		_failedLaunchSetObjectEntry = _addFailedLaunchSetObjectEntry();

		long failedLaunchSetId = _failedLaunchSetObjectEntry.getObjectEntryId();

		Assert.assertThrows(
			PortalException.class,
			() -> _launchSetPublisher.schedulePublish(
				new Date(System.currentTimeMillis() - Time.DAY),
				failedLaunchSetId, TestPropsValues.getUserId()));

		_assertPublishErrorsCount(1, failedLaunchSetId);
		_assertScheduledDateAndStatus(
			null, WorkflowConstants.STATUS_DRAFT, failedLaunchSetId);

		date = new Date(System.currentTimeMillis() + Time.DAY);

		_launchSetPublisher.schedulePublish(
			date, failedLaunchSetId, TestPropsValues.getUserId());

		_assertPublishErrorsCount(0, failedLaunchSetId);
		_assertScheduledDateAndStatus(
			date, WorkflowConstants.STATUS_SCHEDULED, failedLaunchSetId);

		// Launch set rescheduled after a manual publish of it failed

		ObjectEntry rescheduledLaunchSetObjectEntry =
			_addLaunchSetObjectEntry();

		long rescheduledLaunchSetId =
			rescheduledLaunchSetObjectEntry.getObjectEntryId();

		String className = RandomTestUtil.randomString();

		ObjectEntry rescheduledLaunchEntryObjectEntry =
			_addLaunchEntryObjectEntry(
				rescheduledLaunchSetId, className, RandomTestUtil.randomInt(),
				RandomTestUtil.randomString());

		_launchSetPublisher.schedulePublish(
			new Date(System.currentTimeMillis() + Time.DAY),
			rescheduledLaunchSetId, TestPropsValues.getUserId());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.WARN)) {

			_launchSetPublisher.publish(
				rescheduledLaunchSetId, TestPropsValues.getUserId());

			_assertLogEntries(
				PortalException.class,
				"No launch entry manager exists for " + className,
				rescheduledLaunchEntryObjectEntry, rescheduledLaunchSetId,
				logCapture);
		}

		_assertPublishErrorsCount(1, rescheduledLaunchSetId);

		date = new Date(System.currentTimeMillis() + (2 * Time.DAY));

		_launchSetPublisher.schedulePublish(
			date, rescheduledLaunchSetId, TestPropsValues.getUserId());

		_assertScheduledDateAndStatus(
			date, WorkflowConstants.STATUS_SCHEDULED, rescheduledLaunchSetId);

		_launchSetPublisher.unschedulePublish(rescheduledLaunchSetId);

		_assertScheduledDateAndStatus(
			null, WorkflowConstants.STATUS_DRAFT, rescheduledLaunchSetId);

		// Launch set that is being published

		ObjectEntry publishingLaunchSetObjectEntry = _addLaunchSetObjectEntry();

		long publishingLaunchSetId =
			publishingLaunchSetObjectEntry.getObjectEntryId();

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				_launchSetPublisher.publish(
					publishingLaunchSetId, TestPropsValues.getUserId());

				PortalException portalException = Assert.assertThrows(
					PortalException.class,
					() -> _launchSetPublisher.schedulePublish(
						new Date(System.currentTimeMillis() + Time.DAY),
						publishingLaunchSetId, TestPropsValues.getUserId()));

				Assert.assertEquals(
					"Launch set " + publishingLaunchSetId +
						" is already being published",
					portalException.getMessage());

				_assertScheduledDateAndStatus(
					null, WorkflowConstants.STATUS_DRAFT,
					publishingLaunchSetId);

				return null;
			});
	}

	@Test
	public void testUnschedulePublish() throws Exception {
		long launchSetId = _launchSetObjectEntry.getObjectEntryId();

		ObjectEntry launchEntryObjectEntry = _addLaunchEntryObjectEntry(
			launchSetId, RandomTestUtil.randomString(),
			RandomTestUtil.randomInt(), RandomTestUtil.randomString());

		Assert.assertThrows(
			PortalException.class,
			() -> _launchSetPublisher.unschedulePublish(
				launchEntryObjectEntry.getObjectEntryId()));

		_launchSetPublisher.unschedulePublish(launchSetId);

		_assertScheduledDateAndStatus(
			null, WorkflowConstants.STATUS_DRAFT, launchSetId);

		_launchSetPublisher.schedulePublish(
			new Date(System.currentTimeMillis() + Time.DAY), launchSetId,
			TestPropsValues.getUserId());

		_launchSetPublisher.unschedulePublish(launchSetId);

		_assertScheduledDateAndStatus(
			null, WorkflowConstants.STATUS_DRAFT, launchSetId);

		Date date = new Date(System.currentTimeMillis() + Time.WEEK);

		_launchSetPublisher.schedulePublish(
			date, launchSetId, TestPropsValues.getUserId());

		_assertScheduledDateAndStatus(
			date, WorkflowConstants.STATUS_SCHEDULED, launchSetId);

		// Failed launch set

		ObjectEntry failedLaunchSetObjectEntry =
			_addFailedLaunchSetObjectEntry();

		long failedLaunchSetId = failedLaunchSetObjectEntry.getObjectEntryId();

		_launchSetPublisher.schedulePublish(
			new Date(System.currentTimeMillis() + Time.DAY), failedLaunchSetId,
			TestPropsValues.getUserId());

		_launchSetPublisher.unschedulePublish(failedLaunchSetId);

		_assertPublishErrorsCount(0, failedLaunchSetId);
		_assertScheduledDateAndStatus(
			null, WorkflowConstants.STATUS_DRAFT, failedLaunchSetId);

		// Launch set that is being published

		ObjectEntry publishingLaunchSetObjectEntry = _addLaunchSetObjectEntry();

		long publishingLaunchSetId =
			publishingLaunchSetObjectEntry.getObjectEntryId();

		String publishingClassName = RandomTestUtil.randomString();

		ObjectEntry publishingLaunchEntryObjectEntry =
			_addLaunchEntryObjectEntry(
				publishingLaunchSetId, publishingClassName,
				RandomTestUtil.randomInt(), null);

		_launchSetPublisher.schedulePublish(
			new Date(System.currentTimeMillis() + Time.DAY),
			publishingLaunchSetId, TestPropsValues.getUserId());

		String publishingMessage = RandomTestUtil.randomString();
		AtomicReference<PortalException> portalExceptionAtomicReference =
			new AtomicReference<>();

		ServiceRegistration<LaunchEntryManager> serviceRegistration =
			_registerLaunchEntryManager(
				publishingClassName,
				() -> {
					portalExceptionAtomicReference.set(
						Assert.assertThrows(
							PortalException.class,
							() -> _launchSetPublisher.unschedulePublish(
								publishingLaunchSetId)));

					throw new PortalException(publishingMessage);
				});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.WARN)) {

			_launchSetPublisher.publish(
				publishingLaunchSetId, TestPropsValues.getUserId());

			_assertLogEntries(
				PortalException.class, publishingMessage,
				publishingLaunchEntryObjectEntry, publishingLaunchSetId,
				logCapture);
		}
		finally {
			serviceRegistration.unregister();
		}

		PortalException portalException = portalExceptionAtomicReference.get();

		Assert.assertEquals(
			"Launch set " + publishingLaunchSetId +
				" is already being published",
			portalException.getMessage());

		_launchSetPublisher.unschedulePublish(publishingLaunchSetId);
	}

	private ObjectEntry _addFailedLaunchSetObjectEntry() throws Exception {
		ObjectEntry launchSetObjectEntry = _addLaunchSetObjectEntry();

		long launchSetId = launchSetObjectEntry.getObjectEntryId();

		String className = RandomTestUtil.randomString();

		ObjectEntry launchEntryObjectEntry = _addLaunchEntryObjectEntry(
			launchSetId, className, RandomTestUtil.randomInt(),
			RandomTestUtil.randomString());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.WARN)) {

			_launchSetPublisher.publish(
				launchSetId, TestPropsValues.getUserId());

			_assertLogEntries(
				PortalException.class,
				"No launch entry manager exists for " + className,
				launchEntryObjectEntry, launchSetId, logCapture);
		}

		_assertBackgroundTaskStatuses(
			launchSetId, BackgroundTaskConstants.STATUS_FAILED);
		_assertPublishErrorsCount(1, launchSetId);

		return launchSetObjectEntry;
	}

	private ObjectEntry _addLaunchEntryObjectEntry(
			long launchSetId, String className, long classPK,
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
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()), values,
			serviceContext);
	}

	private void _assertBackgroundTaskStatuses(
		long launchSetId, int... expectedStatuses) {

		List<BackgroundTask> backgroundTasks = ListUtil.sort(
			_getBackgroundTasks(launchSetId),
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

	private void _assertLaunchSetError(
		ObjectEntry launchEntryObjectEntry,
		List<ObjectEntry> launchSetErrorObjectEntries, String message) {

		Map<String, Serializable> values = null;

		for (ObjectEntry launchSetErrorObjectEntry :
				launchSetErrorObjectEntries) {

			long launchEntryId = MapUtil.getLong(
				launchSetErrorObjectEntry.getValues(), "launchEntryId");

			if (launchEntryId == launchEntryObjectEntry.getObjectEntryId()) {
				values = launchSetErrorObjectEntry.getValues();

				break;
			}
		}

		Assert.assertNotNull(launchSetErrorObjectEntries.toString(), values);
		Assert.assertEquals(
			StringPool.BLANK, MapUtil.getString(values, "launchEntryTitle"));
		Assert.assertEquals(message, MapUtil.getString(values, "message"));
		Assert.assertEquals("error", MapUtil.getString(values, "severity"));
	}

	private void _assertLogEntries(
		Class<?> exceptionClass, String exceptionMessage,
		ObjectEntry launchEntryObjectEntry, long launchSetId,
		LogCapture logCapture) {

		Map<String, LogEntry> logEntriesMap = _getLogEntriesMap(2, logCapture);

		_assertLogEntry(
			PortalException.class,
			"Unable to publish 1 launch entries of launch set " + launchSetId,
			logEntriesMap, "Unable to publish launch set " + launchSetId,
			"ERROR");
		_assertLogEntry(
			exceptionClass, exceptionMessage, logEntriesMap,
			"Unable to publish launch entry " +
				launchEntryObjectEntry.getObjectEntryId(),
			"WARN");
	}

	private void _assertLogEntry(
		Class<?> exceptionClass, String exceptionMessage,
		Map<String, LogEntry> logEntriesMap, String message, String priority) {

		LogEntry logEntry = logEntriesMap.get(message);

		Assert.assertNotNull(logEntriesMap.toString(), logEntry);
		Assert.assertEquals(priority, logEntry.getPriority());

		Throwable throwable = logEntry.getThrowable();

		Assert.assertSame(exceptionClass, throwable.getClass());
		Assert.assertEquals(exceptionMessage, throwable.getMessage());
		Assert.assertArrayEquals(new Throwable[0], throwable.getSuppressed());
	}

	private void _assertPublishErrorMessage(
			String expectedMessage, String message)
		throws Exception {

		ObjectEntry launchSetObjectEntry = _addLaunchSetObjectEntry();

		long launchSetId = launchSetObjectEntry.getObjectEntryId();

		String className = RandomTestUtil.randomString();

		ObjectEntry launchEntryObjectEntry = _addLaunchEntryObjectEntry(
			launchSetId, className, RandomTestUtil.randomInt(), null);

		ServiceRegistration<LaunchEntryManager> serviceRegistration =
			_registerLaunchEntryManager(
				className,
				() -> {
					throw new PortalException(message);
				});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME, LoggerTestUtil.WARN)) {

			_launchSetPublisher.publish(
				launchSetId, TestPropsValues.getUserId());

			_assertLogEntries(
				PortalException.class, message, launchEntryObjectEntry,
				launchSetId, logCapture);
		}
		finally {
			serviceRegistration.unregister();
		}

		_assertBackgroundTaskStatuses(
			launchSetId, BackgroundTaskConstants.STATUS_FAILED);
		_assertPublishErrorsCount(1, launchSetId);

		launchSetObjectEntry = _objectEntryLocalService.getObjectEntry(
			launchSetId);

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, launchSetObjectEntry.getStatus());

		List<ObjectEntry> launchSetErrorObjectEntries =
			_getLaunchSetErrorObjectEntries(launchSetObjectEntry);

		Assert.assertEquals(
			launchSetErrorObjectEntries.toString(), 1,
			launchSetErrorObjectEntries.size());

		_assertLaunchSetError(
			launchEntryObjectEntry, launchSetErrorObjectEntries,
			expectedMessage);
	}

	private void _assertPublishErrorsCount(
			long expectedPublishErrorsCount, long launchSetId)
		throws Exception {

		ObjectEntry launchSetObjectEntry =
			_objectEntryLocalService.getObjectEntry(launchSetId);

		Assert.assertEquals(
			expectedPublishErrorsCount,
			MapUtil.getLong(
				launchSetObjectEntry.getValues(), "publishErrorsCount"));
	}

	private void _assertPublishFails(long launchSetId, String messageSuffix) {
		PortalException portalException = Assert.assertThrows(
			PortalException.class,
			() -> _launchSetPublisher.publish(
				launchSetId, TestPropsValues.getUserId()));

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
			Date expectedDate, int expectedStatus, long launchSetId)
		throws Exception {

		ObjectEntry launchSetObjectEntry =
			_objectEntryLocalService.getObjectEntry(launchSetId);

		Assert.assertEquals(expectedStatus, launchSetObjectEntry.getStatus());

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				StringBundler.concat(
					launchSetId, StringPool.AT,
					launchSetObjectEntry.getCompanyId()),
				"liferay/launch_set_scheduled_publish", StorageType.PERSISTED);

		Date scheduledDate = null;

		if (schedulerResponse != null) {
			scheduledDate = _schedulerEngineHelper.getStartDate(
				schedulerResponse);
		}

		Map<String, Serializable> values = launchSetObjectEntry.getValues();

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

	private List<BackgroundTask> _getBackgroundTasks(long launchSetId) {
		return ListUtil.filter(
			_backgroundTaskManager.getBackgroundTasks(
				BackgroundTaskConstants.GROUP_ID_DEFAULT,
				_BACKGROUND_TASK_EXECUTOR_CLASS_NAME),
			backgroundTask -> Objects.equals(
				backgroundTask.getName(), String.valueOf(launchSetId)));
	}

	private LaunchEntryManager _getJournalArticleLaunchEntryManager() {
		return _launchEntryManagerRegistry.getLaunchEntryManager(
			JournalArticle.class.getName());
	}

	private List<ObjectEntry> _getLaunchSetErrorObjectEntries(
			ObjectEntry launchSetObjectEntry)
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				launchSetObjectEntry.getObjectDefinitionId(),
				"launchSetToLaunchSetErrors");

		return _objectEntryLocalService.getOneToManyObjectEntries(
			launchSetObjectEntry.getGroupId(),
			objectRelationship.getObjectRelationshipId(), null, false,
			launchSetObjectEntry.getObjectEntryId(), true, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	private Map<String, LogEntry> _getLogEntriesMap(
		int expectedCount, LogCapture logCapture) {

		Map<String, LogEntry> logEntriesMap = new HashMap<>();

		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(
			logEntries.toString(), expectedCount, logEntries.size());

		for (LogEntry logEntry : logEntries) {
			logEntriesMap.put(logEntry.getMessage(), logEntry);
		}

		return logEntriesMap;
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

	private ServiceRegistration<LaunchEntryManager> _registerLaunchEntryManager(
		String className,
		UnsafeSupplier<LaunchEntryManager.Version, Throwable> unsafeSupplier) {

		Bundle bundle = FrameworkUtil.getBundle(LaunchSetPublisherTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			LaunchEntryManager.class,
			(LaunchEntryManager)ProxyUtil.newProxyInstance(
				LaunchEntryManager.class.getClassLoader(),
				new Class<?>[] {LaunchEntryManager.class},
				(proxy, method, arguments) -> {
					if (Objects.equals(method.getName(), "publishDraft")) {
						return unsafeSupplier.get();
					}

					return null;
				}),
			HashMapDictionaryBuilder.<String, Object>put(
				"launch.entry.manager.class.name", className
			).build());
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
			"LaunchSetPublishBackgroundTaskExecutor";

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Inject
	private BackgroundTaskManager _backgroundTaskManager;

	private ObjectEntry _failedLaunchSetObjectEntry;
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private LaunchEntryManagerRegistry _launchEntryManagerRegistry;

	private ObjectEntry _launchSetObjectEntry;

	@Inject
	private LaunchSetPublisher _launchSetPublisher;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "mvc.command.name=/launch/publish_launch")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private SchedulerEngineHelper _schedulerEngineHelper;

	private ServiceContext _serviceContext;

}