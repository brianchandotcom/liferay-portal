/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.registry;

import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_0_0.KaleoTaskInstanceTokenUpgradeProcess;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_1_0.WorkflowContextUpgradeProcess;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_0.UpgradePortletId;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_1.KaleoNotificationRecipientUpgradeProcess;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.KaleoActionUpgradeProcess;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.KaleoDefinitionUpgradeProcess;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.UpgradeClassNames;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_2.KaleoClassNameAndKaleoClassPKUpgradeProcess;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_3.UpgradeBlogsClassName;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.KaleoDefinitionVersionUpgradeProcess;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoActionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoConditionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoDefinitionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoDefinitionVersionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoInstanceTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoLogTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoNodeTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoNotificationRecipientTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoNotificationTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoTaskAssignmentInstanceTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoTaskAssignmentTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoTaskFormInstanceTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoTaskFormTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoTaskInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoTaskTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoTimerInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoTimerTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util.KaleoTransitionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_1.UpgradeMessageBoardsClassName;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_1_1.KaleoNotificationUpgradeProcess;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_2_0.KaleoInstanceUpgradeProcess;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(service = UpgradeStepRegistrator.class)
public class KaleoServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "1.0.0", new KaleoTaskInstanceTokenUpgradeProcess(),
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_0_0.
				SchemaUpgradeProcess());

		registry.register(
			"1.0.0", "1.1.0", new WorkflowContextUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_0.
				SchemaUpgradeProcess(),
			new UpgradePortletId());

		registry.register(
			"1.2.0", "1.2.1",
			UpgradeProcessFactory.alterColumnType(
				"KaleoLog", "comment_", "TEXT null"),
			new KaleoNotificationRecipientUpgradeProcess());

		registry.register(
			"1.2.1", "1.3.0", new UpgradeClassNames(),
			new KaleoActionUpgradeProcess(),
			new KaleoDefinitionUpgradeProcess());

		registry.register(
			"1.3.0", "1.3.1",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_1.
				SchemaUpgradeProcess());

		registry.register(
			"1.3.1", "1.3.2",
			new KaleoClassNameAndKaleoClassPKUpgradeProcess());

		registry.register("1.3.2", "1.3.3", new UpgradeBlogsClassName());

		registry.register(
			"1.3.3", "1.4.0",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_0.
				SchemaUpgradeProcess());

		registry.register(
			"1.4.0", "1.4.1", new KaleoDefinitionVersionUpgradeProcess());

		registry.register("1.4.1", "1.4.2", new DummyUpgradeProcess());

		registry.register(
			"1.4.2", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {
					KaleoActionTable.class, KaleoConditionTable.class,
					KaleoDefinitionTable.class,
					KaleoDefinitionVersionTable.class, KaleoInstanceTable.class,
					KaleoInstanceTokenTable.class, KaleoLogTable.class,
					KaleoNodeTable.class, KaleoNotificationRecipientTable.class,
					KaleoNotificationTable.class,
					KaleoTaskAssignmentInstanceTable.class,
					KaleoTaskAssignmentTable.class,
					KaleoTaskFormInstanceTable.class, KaleoTaskFormTable.class,
					KaleoTaskInstanceTokenTable.class, KaleoTaskTable.class,
					KaleoTimerInstanceTokenTable.class, KaleoTimerTable.class,
					KaleoTransitionTable.class
				}),
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.
				SchemaUpgradeProcess());

		registry.register(
			"2.0.0", "2.0.1", new UpgradeMessageBoardsClassName());

		registry.register(
			"2.0.1", "3.0.0",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.
				SchemaUpgradeProcess());

		registry.register(
			"3.0.0", "3.1.0",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v3_1_0.
				KaleoDefinitionUpgradeProcess());

		registry.register(
			"3.1.0", "3.1.1", new KaleoNotificationUpgradeProcess());

		registry.register("3.1.1", "3.2.0", new KaleoInstanceUpgradeProcess());

		registry.register(
			"3.2.0", "3.3.0",
			UpgradeProcessFactory.addColumns("KaleoNode", "label STRING null"));

		registry.register(
			"3.3.0", "3.4.0",
			UpgradeProcessFactory.addColumns(
				"KaleoTransition", "label STRING null"));

		registry.register(
			"3.4.0", "3.5.0",
			new CTModelUpgradeProcess(
				"KaleoAction", "KaleoCondition", "KaleoDefinition",
				"KaleoDefinitionVersion", "KaleoInstance", "KaleoInstanceToken",
				"KaleoLog", "KaleoNode", "KaleoNotification",
				"KaleoNotificationRecipient", "KaleoTask",
				"KaleoTaskAssignment", "KaleoTaskAssignmentInstance",
				"KaleoTaskForm", "KaleoTaskFormInstance",
				"KaleoTaskInstanceToken", "KaleoTimer",
				"KaleoTimerInstanceToken", "KaleoTransition"));

		registry.register(
			"3.5.0", "3.5.1",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v3_5_1.
				KaleoActionUpgradeProcess());

		registry.register(
			"3.5.1", "3.5.2",
			UpgradeProcessFactory.alterColumnType(
				"KaleoAction", "scriptLanguage", "VARCHAR(255) null"));
	}

}