/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.util.comparator;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorAdapter;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

/**
 * @author Inácio Nery
 */
public class KaleoDefinitionVersionOrderByComparator
	extends OrderByComparatorAdapter
		<KaleoDefinitionVersion, WorkflowDefinition> {

	public static OrderByComparator<KaleoDefinitionVersion>
		getOrderByComparator(
			OrderByComparator<WorkflowDefinition> orderByComparator,
			KaleoWorkflowModelConverter kaleoWorkflowModelConverter) {

		if (orderByComparator == null) {
			return null;
		}

		return new KaleoDefinitionVersionOrderByComparator(
			orderByComparator, kaleoWorkflowModelConverter);
	}

	@Override
	public WorkflowDefinition adapt(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		try {
			return _kaleoWorkflowModelConverter.toWorkflowDefinition(
				kaleoDefinitionVersion);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private KaleoDefinitionVersionOrderByComparator(
		OrderByComparator<WorkflowDefinition> orderByComparator,
		KaleoWorkflowModelConverter kaleoWorkflowModelConverter) {

		super(orderByComparator);

		_kaleoWorkflowModelConverter = kaleoWorkflowModelConverter;
	}

	private final KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

}