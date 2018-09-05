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

import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

import java.text.Collator;

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
		this(false, Locale.getDefault());
	}

	public WorkflowDefinitionTitleComparator(boolean ascending, Locale locale) {
		_ascending = ascending;
		_locale = locale;

		_collator = CollatorUtil.getInstance(_locale);

		_languageId = LocaleUtil.toLanguageId(locale);
	}

	@Override
	public int compare(
		WorkflowDefinition workflowDefinition1,
		WorkflowDefinition workflowDefinition2) {

		String workflowDefinitionTitle1 = workflowDefinition1.getTitle(
			_languageId);
		String workflowDefinitionTitle2 = workflowDefinition2.getTitle(
			_languageId);

		int value = _collator.compare(
			workflowDefinitionTitle1, workflowDefinitionTitle2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
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
	private final Collator _collator;
	private final String _languageId;
	private final Locale _locale;

}