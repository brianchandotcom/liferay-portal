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

package com.liferay.portal.workflow.kaleo.definition.internal.export.builder;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.definition.Condition;
import com.liferay.portal.workflow.kaleo.model.KaleoCondition;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoConditionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "node.type=CONDITION",
	service = NodeBuilder.class
)
public class ConditionNodeBuilder
	extends BaseNodeBuilder<Condition> implements NodeBuilder {

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

	@Reference
	private KaleoConditionLocalService _kaleoConditionLocalService;

}