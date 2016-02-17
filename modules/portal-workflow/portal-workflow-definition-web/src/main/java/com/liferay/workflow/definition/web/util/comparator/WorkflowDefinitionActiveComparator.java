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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

import java.util.Locale;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionActiveComparator
	extends OrderByComparator<WorkflowDefinition> {

	public static final String ORDER_BY_ASC = "active ASC";

	public static final String ORDER_BY_DESC = "active DESC";

	public static final String[] ORDER_BY_FIELDS = {"active"};

	public WorkflowDefinitionActiveComparator() {
		this(false, Locale.getDefault());
	}

	public WorkflowDefinitionActiveComparator(
		boolean ascending, Locale locale) {

		_ascending = ascending;
		_locale = locale;
	}

	@Override
	public int compare(
		WorkflowDefinition workflowDefinition1,
		WorkflowDefinition workflowDefinition2) {

		String activeLabel1 = getActiveLabel(workflowDefinition1.isActive());
		String activeLabel2 = getActiveLabel(workflowDefinition2.isActive());

		int value = activeLabel1.compareTo(activeLabel2);

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

	protected String getActiveLabel(boolean active) {
		if (active) {
			return LanguageUtil.get(_locale, "yes");
		}

		return LanguageUtil.get(_locale, "no");
	}

	private final boolean _ascending;
	private final Locale _locale;

}