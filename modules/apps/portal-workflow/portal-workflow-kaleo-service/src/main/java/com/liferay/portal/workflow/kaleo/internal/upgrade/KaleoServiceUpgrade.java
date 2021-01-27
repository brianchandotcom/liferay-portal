/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade;

import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_0_0.UpgradeKaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_1_0.UpgradeWorkflowContext;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_0.UpgradePortletId;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_1.UpgradeKaleoLog;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_1.UpgradeKaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.UpgradeClassNames;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.UpgradeKaleoAction;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.UpgradeKaleoDefinition;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_2.UpgradeKaleoClassNameAndKaleoClassPK;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_3.UpgradeBlogsClassName;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.UpgradeKaleoDefinitionVersion;
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

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	service = {KaleoServiceUpgrade.class, UpgradeStepRegistrator.class}
)
public class KaleoServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "1.0.0", new UpgradeKaleoTaskInstanceToken(),
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_0_0.
				UpgradeSchema());

		registry.register("1.0.0", "1.1.0", new UpgradeWorkflowContext());

		registry.register(
			"1.1.0", "1.2.0",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_0.
				UpgradeSchema(),
			new UpgradePortletId());

		registry.register(
			"1.2.0", "1.2.1", new UpgradeKaleoLog(),
			new UpgradeKaleoNotificationRecipient());

		registry.register(
			"1.2.1", "1.3.0", new UpgradeClassNames(), new UpgradeKaleoAction(),
			new UpgradeKaleoDefinition());

		registry.register(
			"1.3.0", "1.3.1",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_1.
				UpgradeSchema());

		registry.register(
			"1.3.1", "1.3.2", new UpgradeKaleoClassNameAndKaleoClassPK());

		registry.register("1.3.2", "1.3.3", new UpgradeBlogsClassName());

		registry.register(
			"1.3.3", "1.4.0",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_0.
				UpgradeSchema());

		registry.register(
			"1.4.0", "1.4.1", new UpgradeKaleoDefinitionVersion());

		registry.register("1.4.1", "1.4.2", new DummyUpgradeProcess());

		registry.register(
			"1.4.2", "2.0.0",
			new BaseUpgradeSQLServerDatetime(
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
				UpgradeSchema());

		registry.register(
			"2.0.0", "2.0.1", new UpgradeMessageBoardsClassName());

		registry.register(
			"2.0.1", "3.0.0",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.
				UpgradeSchema());

		registry.register(
			"3.0.0", "3.1.0",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v3_1_0.
				UpgradeKaleoDefinition());
	}

}