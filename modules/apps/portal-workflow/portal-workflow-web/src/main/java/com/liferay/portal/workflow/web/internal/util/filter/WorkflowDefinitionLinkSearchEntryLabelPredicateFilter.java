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

package com.liferay.portal.workflow.web.internal.util.filter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearchEntry;

/**
 * @author Marcellus Tavares
 */
public class WorkflowDefinitionLinkSearchEntryLabelPredicateFilter
	implements PredicateFilter<WorkflowDefinitionLinkSearchEntry> {

	public WorkflowDefinitionLinkSearchEntryLabelPredicateFilter(
		String keywords) {

		_keywords = keywords;
	}

	@Override
	public boolean filter(
		WorkflowDefinitionLinkSearchEntry workflowDefinitionLinkSearchEntry) {

		if (Validator.isNull(_keywords)) {
			return true;
		}

		return StringUtil.containsIgnoreCase(
			workflowDefinitionLinkSearchEntry.getWorkflowDefinitionLabel(),
			_keywords, StringPool.SPACE);
	}

	private final String _keywords;

}