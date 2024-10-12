/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.dispatch.executor.DispatchTaskExecutorRegistry;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kevin Lee
 */
@RunWith(Arquillian.class)
public class QuartzUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_schedulerEngine.delete(_GROUP_NAME, StorageType.PERSISTED);
	}

	@Test
	public void testUpgradeWithCTJobs() throws Exception {
		Message message = new Message();

		long companyId = TestPropsValues.getCompanyId();

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, companyId, TestPropsValues.getUserId(), 0, "test", null);

		long ctCollectionId = _ctCollection.getCtCollectionId();

		message.put("ctCollectionId", ctCollectionId);

		_scheduleJob(
			String.valueOf(ctCollectionId), _CT_DESTINATION_NAME, message);

		_runUpgrade();

		Assert.assertNotNull(
			_schedulerEngine.getScheduledJob(
				StringBundler.concat(ctCollectionId, StringPool.AT, companyId),
				_GROUP_NAME, StorageType.PERSISTED));
	}

	@Test
	public void testUpgradeWithDispatchJobs() throws Exception {
		Message message = new Message();

		long companyId = TestPropsValues.getCompanyId();

		_dispatchTrigger = _dispatchTriggerLocalService.addDispatchTrigger(
			null, TestPropsValues.getUserId(),
			SetUtil.randomElement(
				_dispatchTaskExecutorRegistry.getDispatchTaskExecutorTypes()),
			null, "test", false);

		long dispatchTriggerId = _dispatchTrigger.getDispatchTriggerId();

		message.setPayload(
			StringBundler.concat(
				"{\"dispatchTriggerId\": ", dispatchTriggerId, "}"));

		_scheduleJob(
			String.valueOf(dispatchTriggerId), _DISPATCH_DESTINATION_NAME,
			message);

		_runUpgrade();

		Assert.assertNotNull(
			_schedulerEngine.getScheduledJob(
				StringBundler.concat(
					dispatchTriggerId, StringPool.AT, companyId),
				_GROUP_NAME, StorageType.PERSISTED));
	}

	@Test
	public void testUpgradeWithExportImportJobs() throws Exception {
		Message message1 = new Message();

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					TestPropsValues.getUserId(), 0, false, new long[0], null,
					LocaleUtil.US, TimeZoneUtil.GMT);

		_exportImportConfiguration1 =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(), StringPool.BLANK,
					ExportImportConfigurationConstants.
						TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL,
					exportLayoutSettingsMap);

		message1.setPayload(
			_exportImportConfiguration1.getExportImportConfigurationId());

		String jobName1 = PortalUUIDUtil.generate();

		_scheduleJob(jobName1, _LAYOUTS_LOCAL_DESTINATION_NAME, message1);

		String jobName2 = PortalUUIDUtil.generate();

		Message message2 = new Message();

		_exportImportConfiguration2 =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(), StringPool.BLANK,
					ExportImportConfigurationConstants.
						TYPE_SCHEDULED_PUBLISH_LAYOUT_REMOTE,
					exportLayoutSettingsMap);

		message2.setPayload(
			_exportImportConfiguration2.getExportImportConfigurationId());

		_scheduleJob(jobName2, _LAYOUTS_REMOTE_DESTINATION_NAME, message2);

		_runUpgrade();

		long companyId = TestPropsValues.getCompanyId();

		Set<String> expectedJobNames = SetUtil.fromArray(
			StringBundler.concat(jobName1, StringPool.AT, companyId),
			StringBundler.concat(jobName2, StringPool.AT, companyId));

		for (SchedulerResponse schedulerResponse :
				_schedulerEngine.getScheduledJobs(
					_GROUP_NAME, StorageType.PERSISTED)) {

			expectedJobNames.remove(schedulerResponse.getJobName());
		}

		Assert.assertTrue(
			expectedJobNames.toString(), expectedJobNames.isEmpty());
	}

	@Test
	public void testUpgradeWithJobsFromDifferentCompany() throws Exception {
		_company = CompanyTestUtil.addCompany(true);

		long companyId2 = _company.getCompanyId();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(companyId2)) {

			_ctCollection = _ctCollectionLocalService.addCTCollection(
				null, companyId2, TestPropsValues.getUserId(), 0, "test", null);
		}

		Message message1 = new Message();

		long companyId1 = TestPropsValues.getCompanyId();

		message1.put("companyId", companyId1);

		_scheduleJob("test1", _GROUP_NAME, message1);

		Message message2 = new Message();

		message2.put("companyId", companyId2);

		_scheduleJob("test2", _GROUP_NAME, message2);

		Message message3 = new Message();

		message3.put("ctCollectionId", _ctCollection.getCtCollectionId());

		_scheduleJob("test3", _CT_DESTINATION_NAME, message3);

		_runUpgrade();

		Set<String> expectedJobNames = SetUtil.fromArray(
			"test1@" + companyId1, "test2@" + companyId2,
			"test3@" + companyId2);

		for (SchedulerResponse schedulerResponse :
				_schedulerEngine.getScheduledJobs(
					_GROUP_NAME, StorageType.PERSISTED)) {

			expectedJobNames.remove(schedulerResponse.getJobName());
		}

		Assert.assertTrue(
			expectedJobNames.toString(), expectedJobNames.isEmpty());
	}

	@Test
	public void testUpgradeWithJobsWithCompanyIds() throws Exception {
		Message message1 = new Message();

		long companyId1 = RandomTestUtil.randomLong();

		message1.put("companyId", companyId1);

		_scheduleJob("test1", _GROUP_NAME, message1);
		_scheduleJob("test2", _GROUP_NAME, message1);

		long companyId2 = RandomTestUtil.randomLong();

		Message message2 = new Message();

		message2.put("companyId", companyId2);

		_scheduleJob("test3", _GROUP_NAME, message2);
		_scheduleJob("test4@" + companyId2, _GROUP_NAME, message2);

		_runUpgrade();

		Set<String> expectedJobNames = SetUtil.fromArray(
			"test1@" + companyId1, "test2@" + companyId1, "test3@" + companyId2,
			"test4@" + companyId2);

		for (SchedulerResponse schedulerResponse :
				_schedulerEngine.getScheduledJobs(
					_GROUP_NAME, StorageType.PERSISTED)) {

			expectedJobNames.remove(schedulerResponse.getJobName());
		}

		Assert.assertTrue(
			expectedJobNames.toString(), expectedJobNames.isEmpty());
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();
		}
	}

	private void _scheduleJob(
			String jobName, String destinationName, Message message)
		throws Exception {

		Trigger trigger = _triggerFactory.createTrigger(
			jobName, _GROUP_NAME, null, null, 1, TimeUnit.DAY);

		_schedulerEngine.schedule(
			trigger, StringPool.BLANK, destinationName, message,
			StorageType.PERSISTED);
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_1." +
			"QuartzUpgradeProcess";

	private static final String _CT_DESTINATION_NAME =
		"liferay/ct_collection_scheduled_publish";

	private static final String _DISPATCH_DESTINATION_NAME =
		"liferay/dispatch/executor";

	private static final String _GROUP_NAME = "liferay/test";

	private static final String _LAYOUTS_LOCAL_DESTINATION_NAME =
		"liferay/layouts_local_publisher";

	private static final String _LAYOUTS_REMOTE_DESTINATION_NAME =
		"liferay/layouts_remote_publisher";

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private DispatchTaskExecutorRegistry _dispatchTaskExecutorRegistry;

	@DeleteAfterTestRun
	private DispatchTrigger _dispatchTrigger;

	@Inject
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@DeleteAfterTestRun
	private ExportImportConfiguration _exportImportConfiguration1;

	@DeleteAfterTestRun
	private ExportImportConfiguration _exportImportConfiguration2;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject(
		filter = "component.name=com.liferay.portal.scheduler.quartz.internal.QuartzSchedulerEngine"
	)
	private SchedulerEngine _schedulerEngine;

	@Inject(
		filter = "component.name=com.liferay.portal.scheduler.quartz.internal.QuartzTriggerFactory"
	)
	private TriggerFactory _triggerFactory;

	@Inject(
		filter = "(&(component.name=com.liferay.portal.scheduler.quartz.internal.upgrade.registry.QuartzServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}