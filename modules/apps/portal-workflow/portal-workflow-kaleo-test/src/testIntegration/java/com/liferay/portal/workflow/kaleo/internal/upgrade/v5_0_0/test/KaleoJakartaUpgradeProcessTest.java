/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v5_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.definition.Condition;
import com.liferay.portal.workflow.kaleo.definition.Notification;
import com.liferay.portal.workflow.kaleo.definition.ScriptAction;
import com.liferay.portal.workflow.kaleo.definition.ScriptAssignment;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoCondition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoActionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoConditionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
public class KaleoJakartaUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<?> clazz = upgradeStep.getClass();

					if (Objects.equals(
							clazz.getName(),
							"com.liferay.portal.workflow.kaleo.internal." +
								"upgrade.v5_0_0.KaleoJakartaUpgradeProcess")) {

						_upgradeProcess = (UpgradeProcess)upgradeStep;
					}
				}
			});
	}

	@Test
	@TestInfo("LPD-52638")
	public void testUpgrade() throws Exception {
		KaleoAction kaleoAction = null;
		KaleoCondition kaleoCondition = null;
		KaleoDefinition kaleoDefinition = null;
		KaleoInstance kaleoInstance = null;
		KaleoInstanceToken kaleoInstanceToken = null;
		KaleoLog kaleoLog = null;
		KaleoNode kaleoNode = null;
		KaleoNotification kaleoNotification = null;
		KaleoTaskAssignment kaleoTaskAssignment = null;
		KaleoTaskInstanceToken kaleoTaskInstanceToken = null;

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext();

			kaleoInstance = _kaleoInstanceLocalService.addKaleoInstance(
				1, 1, "Test", 1,
				HashMapBuilder.<String, Serializable>put(
					WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
					(Serializable)BlogsEntry.class.getName()
				).put(
					WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
					(Serializable)serviceContext
				).put(
					WorkflowConstants.CONTEXT_URL, _JAVAX_URL
				).build(),
				serviceContext);

			kaleoNode = _kaleoNodeLocalService.addKaleoNode(
				kaleoInstance.getKaleoDefinitionId(),
				kaleoInstance.getKaleoDefinitionVersionId(),
				new Task("task", StringPool.BLANK), serviceContext);

			kaleoAction = _kaleoActionLocalService.addKaleoAction(
				KaleoNode.class.getName(), kaleoNode.getKaleoNodeId(),
				kaleoInstance.getKaleoDefinitionId(),
				kaleoInstance.getKaleoDefinitionVersionId(),
				kaleoNode.getName(),
				new ScriptAction(
					StringUtil.randomString(), StringUtil.randomString(),
					"onAssignment", StringPool.BLANK, "groovy",
					StringPool.BLANK, 0),
				serviceContext);

			kaleoAction = _kaleoActionLocalService.getKaleoAction(
				kaleoAction.getKaleoActionId());

			kaleoAction.setScript(_JAVAX_SCRIPT);

			kaleoAction = _kaleoActionLocalService.updateKaleoAction(
				kaleoAction);

			Condition condition = new Condition(
				RandomTestUtil.randomString(), StringPool.BLANK, _JAVAX_SCRIPT,
				"java", StringPool.BLANK);

			kaleoCondition = _kaleoConditionLocalService.addKaleoCondition(
				kaleoInstance.getKaleoDefinitionId(),
				kaleoInstance.getKaleoDefinitionVersionId(),
				kaleoNode.getKaleoNodeId(), condition, serviceContext);

			kaleoDefinition = _kaleoDefinitionLocalService.addKaleoDefinition(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				_read("valid-javax-workflow-definition.xml"),
				WorkflowDefinitionConstants.SCOPE_ALL, 1, serviceContext);

			kaleoInstanceToken =
				_kaleoInstanceTokenLocalService.addKaleoInstanceToken(
					kaleoNode.getKaleoNodeId(),
					kaleoInstance.getKaleoDefinitionId(),
					kaleoInstance.getKaleoDefinitionVersionId(),
					kaleoInstance.getKaleoInstanceId(), 0,
					WorkflowContextUtil.convert(
						kaleoInstance.getWorkflowContext()),
					serviceContext);

			kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
					kaleoInstanceToken.getKaleoInstanceTokenId(), 1, "task",
					Collections.emptyList(), null,
					WorkflowContextUtil.convert(
						kaleoInstance.getWorkflowContext()),
					serviceContext);

			kaleoLog = _kaleoLogLocalService.addTaskAssignmentKaleoLog(
				Collections.emptyList(), null, kaleoTaskInstanceToken,
				StringPool.BLANK,
				WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
				serviceContext);

			kaleoNotification =
				_kaleoNotificationLocalService.addKaleoNotification(
					KaleoNode.class.getName(), kaleoInstance.getClassPK(),
					kaleoDefinition.getKaleoDefinitionId(),
					kaleoDefinition.getKaleoDefinitionVersions(
					).get(
						0
					).getKaleoDefinitionVersionId(),
					kaleoNode.getName(),
					new Notification(
						StringUtil.randomString(), StringUtil.randomString(),
						"onTimer", _JAVAX_SCRIPT, "freemarker"),
					serviceContext);

			kaleoTaskAssignment =
				_kaleoTaskAssignmentLocalService.addKaleoTaskAssignment(
					KaleoNode.class.getName(), kaleoInstance.getClassPK(),
					kaleoInstance.getKaleoDefinitionId(),
					kaleoInstance.getKaleoDefinitionVersionId(),
					new ScriptAssignment(
						_JAVAX_SCRIPT, "java", RandomTestUtil.randomString()),
					serviceContext);

			_upgradeProcess.upgrade();

			_multiVMPool.clear();

			KaleoAction updatedKaleoAction =
				_kaleoActionLocalService.getKaleoAction(
					kaleoAction.getKaleoActionId());

			Assert.assertNotNull(updatedKaleoAction);

			Assert.assertEquals(
				_JAKARTA_SCRIPT, updatedKaleoAction.getScript());

			KaleoCondition updatedKaleoCondition =
				_kaleoConditionLocalService.getKaleoCondition(
					kaleoCondition.getKaleoConditionId());

			Assert.assertNotNull(updatedKaleoCondition);

			Assert.assertEquals(
				_JAKARTA_SCRIPT, updatedKaleoCondition.getScript());

			KaleoDefinition updatedKaleoDefinition =
				_kaleoDefinitionLocalService.getKaleoDefinition(
					kaleoDefinition.getKaleoDefinitionId());

			Assert.assertNotNull(updatedKaleoDefinition);

			Assert.assertTrue(
				updatedKaleoDefinition.getContentAsXML(
				).contains(
					_JAKARTA_SCRIPT
				));

			List<KaleoDefinitionVersion> kaleoDefinitionVersions =
				kaleoDefinition.getKaleoDefinitionVersions();

			Assert.assertEquals(
				kaleoDefinitionVersions.toString(), 1,
				kaleoDefinitionVersions.size());

			Assert.assertTrue(
				kaleoDefinitionVersions.get(
					0
				).getContentAsXML(
				).contains(
					_JAKARTA_SCRIPT
				));

			KaleoInstance updatedKaleoInstance =
				_kaleoInstanceLocalService.getKaleoInstance(
					kaleoInstance.getKaleoInstanceId());

			Assert.assertNotNull(updatedKaleoInstance);

			Map<String, Serializable> workflowContext =
				WorkflowContextUtil.convert(
					updatedKaleoInstance.getWorkflowContext());

			Assert.assertEquals(
				_JAKARTA_URL,
				workflowContext.get(WorkflowConstants.CONTEXT_URL));

			KaleoLog updatedKaleoLog = _kaleoLogLocalService.getKaleoLog(
				kaleoLog.getKaleoLogId());

			Assert.assertNotNull(updatedKaleoLog);

			workflowContext = WorkflowContextUtil.convert(
				updatedKaleoLog.getWorkflowContext());

			Assert.assertEquals(
				_JAKARTA_URL,
				workflowContext.get(WorkflowConstants.CONTEXT_URL));

			KaleoNotification updatedKaleoNotification =
				_kaleoNotificationLocalService.getKaleoNotification(
					kaleoNotification.getKaleoNotificationId());

			Assert.assertNotNull(updatedKaleoNotification);

			Assert.assertEquals(
				_JAKARTA_SCRIPT, updatedKaleoNotification.getTemplate());

			KaleoTaskAssignment updatedKaleoTaskAssignment =
				_kaleoTaskAssignmentLocalService.getKaleoTaskAssignment(
					kaleoTaskAssignment.getKaleoTaskAssignmentId());

			Assert.assertNotNull(updatedKaleoTaskAssignment);

			Assert.assertEquals(
				_JAKARTA_SCRIPT,
				updatedKaleoTaskAssignment.getAssigneeScript());

			KaleoTaskInstanceToken updatedKaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

			Assert.assertNotNull(updatedKaleoTaskInstanceToken);

			workflowContext = WorkflowContextUtil.convert(
				updatedKaleoTaskInstanceToken.getWorkflowContext());

			Assert.assertEquals(
				_JAKARTA_URL,
				workflowContext.get(WorkflowConstants.CONTEXT_URL));
		}
		finally {
			if (kaleoAction != null) {
				_kaleoActionLocalService.deleteKaleoAction(kaleoAction);
			}

			if (kaleoCondition != null) {
				_kaleoConditionLocalService.deleteKaleoCondition(
					kaleoCondition);
			}

			if (kaleoDefinition != null) {
				_kaleoDefinitionLocalService.deleteKaleoDefinition(
					kaleoDefinition);
			}

			if (kaleoLog != null) {
				_kaleoLogLocalService.deleteKaleoLog(kaleoLog);
			}

			if (kaleoNotification != null) {
				_kaleoNotificationLocalService.deleteKaleoNotification(
					kaleoNotification);
			}

			if (kaleoTaskAssignment != null) {
				_kaleoTaskAssignmentLocalService.deleteKaleoTaskAssignment(
					kaleoTaskAssignment);
			}

			if (kaleoTaskInstanceToken != null) {
				_kaleoTaskInstanceTokenLocalService.
					deleteKaleoTaskInstanceToken(kaleoTaskInstanceToken);
			}

			if (kaleoInstance != null) {
				_kaleoInstanceLocalService.deleteKaleoInstance(kaleoInstance);
			}

			if (kaleoNode != null) {
				_kaleoNodeLocalService.deleteKaleoNode(kaleoNode);
			}
		}
	}

	private String _read(String name) throws IOException {
		ClassLoader classLoader =
			KaleoJakartaUpgradeProcessTest.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/workflow/kaleo/dependencies/" + name)) {

			return StringUtil.read(inputStream);
		}
	}

	private static final String _JAKARTA_SCRIPT =
		"import jakarta.servlet.test.KaleoJakartaUpgradeProcessTest;";

	private static final String _JAKARTA_URL =
		"https://liferay.com?portletAction=jakarta.servlet.action";

	private static final String _JAVAX_SCRIPT =
		"import javax.servlet.test.KaleoJakartaUpgradeProcessTest;";

	private static final String _JAVAX_URL =
		"https://liferay.com?portletAction=javax.servlet.action";

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.kaleo.internal.upgrade.registry.KaleoServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private KaleoActionLocalService _kaleoActionLocalService;

	@Inject
	private KaleoConditionLocalService _kaleoConditionLocalService;

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Inject
	private KaleoLogLocalService _kaleoLogLocalService;

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Inject
	private KaleoNotificationLocalService _kaleoNotificationLocalService;

	@Inject
	private KaleoTaskAssignmentLocalService _kaleoTaskAssignmentLocalService;

	@Inject
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	private UpgradeProcess _upgradeProcess;

}