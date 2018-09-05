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

package com.liferay.portal.workflow.web.internal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearchEntry;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionLinkSearchEntryWorkflowComparator
	extends OrderByComparator<WorkflowDefinitionLinkSearchEntry> {

	public WorkflowDefinitionLinkSearchEntryWorkflowComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(
		WorkflowDefinitionLinkSearchEntry workflowDefinitionLinkSearchEntry1,
		WorkflowDefinitionLinkSearchEntry workflowDefinitionLinkSearchEntry2) {

		String workflowDefinitionLabel1 = StringUtil.toLowerCase(
			workflowDefinitionLinkSearchEntry1.getWorkflowDefinitionLabel());
		String workflowDefinitionLabel2 = StringUtil.toLowerCase(
			workflowDefinitionLinkSearchEntry2.getWorkflowDefinitionLabel());

		int value = workflowDefinitionLabel1.compareTo(
			workflowDefinitionLabel2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;

}