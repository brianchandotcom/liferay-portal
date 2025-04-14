/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v4_3_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.workflow.kaleo.definition.LogType;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class KaleoLogUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		_kaleoInstance = _kaleoInstanceLocalService.addKaleoInstance(
			1, 1, RandomTestUtil.randomString(), 1,
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
				BlogsEntry.class.getName()
			).put(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT, serviceContext
			).build(),
			serviceContext);

		KaleoNode kaleoNode = _kaleoNodeLocalService.addKaleoNode(
			_kaleoInstance.getKaleoDefinitionId(),
			_kaleoInstance.getKaleoDefinitionVersionId(),
			new Task(RandomTestUtil.randomString(), StringPool.BLANK),
			serviceContext);

		KaleoInstanceToken kaleoInstanceToken =
			_kaleoInstanceTokenLocalService.addKaleoInstanceToken(
				kaleoNode.getKaleoNodeId(),
				_kaleoInstance.getKaleoDefinitionId(),
				_kaleoInstance.getKaleoDefinitionVersionId(),
				_kaleoInstance.getKaleoInstanceId(), 0,
				WorkflowContextUtil.convert(
					_kaleoInstance.getWorkflowContext()),
				serviceContext);

		_updateKaleoLogType(
			_kaleoLogLocalService.addInstanceStartKaleoLog(
				kaleoInstanceToken, serviceContext),
			"WORKFLOW_INSTANCE_START");

		_updateKaleoLogType(
			_kaleoLogLocalService.addInstanceEndKaleoLog(
				kaleoInstanceToken, serviceContext),
			"WORKFLOW_INSTANCE_END");
	}

	@Test
	public void testUpgrade() throws Exception {
		Assert.assertEquals(
			0, _getKaleoInstanceKaleoLogsCount(LogType.INSTANCE_END.name()));
		Assert.assertEquals(
			0, _getKaleoInstanceKaleoLogsCount(LogType.INSTANCE_START.name()));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}

		Assert.assertEquals(
			1, _getKaleoInstanceKaleoLogsCount(LogType.INSTANCE_END.name()));
		Assert.assertEquals(
			1, _getKaleoInstanceKaleoLogsCount(LogType.INSTANCE_START.name()));
	}

	private long _getKaleoInstanceKaleoLogsCount(String type) throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoLogLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"kaleoInstanceId",
						_kaleoInstance.getKaleoInstanceId()));
				dynamicQuery.add(RestrictionsFactoryUtil.eq("type", type));
			});

		return actionableDynamicQuery.performCount();
	}

	private void _updateKaleoLogType(KaleoLog kaleoLog, String type) {
		kaleoLog.setType(type);

		_kaleoLogLocalService.updateKaleoLog(kaleoLog);
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.workflow.kaleo.internal.upgrade.v4_3_0." +
			"KaleoLogUpgradeProcess";

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.kaleo.internal.upgrade.registry.KaleoServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private KaleoInstance _kaleoInstance;

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Inject
	private KaleoLogLocalService _kaleoLogLocalService;

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}