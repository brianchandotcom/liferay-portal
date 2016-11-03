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

package com.liferay.portal.workflow.kaleo.runtime.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorAdapter;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;

/**
 * @author William Newbury
 */
public class KaleoDefinitionOrderByComparator
	extends OrderByComparatorAdapter<KaleoDefinition, WorkflowDefinition> {

	public static OrderByComparator<KaleoDefinition> getOrderByComparator(
		OrderByComparator<WorkflowDefinition> orderByComparator,
		KaleoWorkflowModelConverter kaleoWorkflowModelConverter) {

		if (orderByComparator == null) {
			return null;
		}

		return new KaleoDefinitionOrderByComparator(
			orderByComparator, kaleoWorkflowModelConverter);
	}

	@Override
	public WorkflowDefinition adapt(KaleoDefinition kaleoDefinition) {
		return _kaleoWorkflowModelConverter.toWorkflowDefinition(
			kaleoDefinition);
	}

	private KaleoDefinitionOrderByComparator(
		OrderByComparator<WorkflowDefinition> orderByComparator,
		KaleoWorkflowModelConverter kaleoWorkflowModelConverter) {

		super(orderByComparator);

		_kaleoWorkflowModelConverter = kaleoWorkflowModelConverter;
	}

	private final KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

}