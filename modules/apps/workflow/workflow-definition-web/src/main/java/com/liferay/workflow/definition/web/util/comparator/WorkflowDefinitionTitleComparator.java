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

package com.liferay.workflow.definition.web.util.comparator;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

import java.util.Locale;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionTitleComparator
	extends OrderByComparator<WorkflowDefinition> {

	public static final String ORDER_BY_ASC = "title ASC";

	public static final String ORDER_BY_DESC = "title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public WorkflowDefinitionTitleComparator() {
		this(false, LocaleUtil.toLanguageId(Locale.getDefault()));
	}

	public WorkflowDefinitionTitleComparator(
		boolean ascending, String languageId) {

		_ascending = ascending;
		_languageId = languageId;
	}

	@Override
	public int compare(
		WorkflowDefinition workflowDefinition1,
		WorkflowDefinition workflowDefinition2) {

		int value = workflowDefinition1.getTitle(_languageId).compareTo(
			workflowDefinition2.getTitle(_languageId));

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;
	private final String _languageId;

}