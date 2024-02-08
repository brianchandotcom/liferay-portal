/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.export.builder;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.definition.Condition;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoCondition;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoConditionLocalService;

/**
 * @author Michael C. Han
 */
public class ConditionNodeBuilder extends BaseNodeBuilder<Condition> {

	public ConditionNodeBuilder(
		KaleoConditionLocalService kaleoConditionLocalService) {

		_kaleoConditionLocalService = kaleoConditionLocalService;
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.CONDITION;
	}

	@Override
	protected Condition createNode(KaleoNode kaleoNode) throws PortalException {
		KaleoCondition kaleoCondition =
			_kaleoConditionLocalService.getKaleoNodeKaleoCondition(
				kaleoNode.getKaleoNodeId());

		return new Condition(
			kaleoNode.getName(), kaleoNode.getDescription(),
			kaleoCondition.getScript(), kaleoCondition.getScriptLanguage(),
			kaleoCondition.getScriptRequiredContexts());
	}

	private final KaleoConditionLocalService _kaleoConditionLocalService;

}