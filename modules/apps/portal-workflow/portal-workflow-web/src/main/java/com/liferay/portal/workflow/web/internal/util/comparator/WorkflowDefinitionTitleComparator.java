/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.util.comparator;

import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
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
		this(false, LocaleUtil.getDefault());
	}

	public WorkflowDefinitionTitleComparator(boolean ascending, Locale locale) {
		_ascending = ascending;
		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
		_languageId = LocaleUtil.toLanguageId(locale);
	}

	@Override
	public int compare(
		WorkflowDefinition workflowDefinition1,
		WorkflowDefinition workflowDefinition2) {

		int value = Boolean.compare(
			workflowDefinition1.isActive(), workflowDefinition2.isActive());

		if (value == 0) {
			String workflowDefinitionTitle1 = StringUtil.toLowerCase(
				workflowDefinition1.getTitle(_languageId));
			String workflowDefinitionTitle2 = StringUtil.toLowerCase(
				workflowDefinition2.getTitle(_languageId));

			value = _collator.compare(
				workflowDefinitionTitle1, workflowDefinitionTitle2);

			if (_ascending) {
				return value;
			}
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