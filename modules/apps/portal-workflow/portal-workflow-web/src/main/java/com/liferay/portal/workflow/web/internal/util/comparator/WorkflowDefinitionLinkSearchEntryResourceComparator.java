/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearchEntry;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionLinkSearchEntryResourceComparator
	extends OrderByComparator<WorkflowDefinitionLinkSearchEntry> {

	public static WorkflowDefinitionLinkSearchEntryResourceComparator
		getInstance(boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		WorkflowDefinitionLinkSearchEntry workflowDefinitionLinkSearchEntry1,
		WorkflowDefinitionLinkSearchEntry workflowDefinitionLinkSearchEntry2) {

		String resource1 = StringUtil.toLowerCase(
			workflowDefinitionLinkSearchEntry1.getResource());
		String resource2 = StringUtil.toLowerCase(
			workflowDefinitionLinkSearchEntry2.getResource());

		int value = resource1.compareTo(resource2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private WorkflowDefinitionLinkSearchEntryResourceComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	private static final WorkflowDefinitionLinkSearchEntryResourceComparator
		_INSTANCE_ASCENDING =
			new WorkflowDefinitionLinkSearchEntryResourceComparator(true);

	private static final WorkflowDefinitionLinkSearchEntryResourceComparator
		_INSTANCE_DESCENDING =
			new WorkflowDefinitionLinkSearchEntryResourceComparator(false);

	private final boolean _ascending;

}