/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.util.comparator;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

import java.text.Collator;

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
		this(false, LocaleUtil.getDefault());
	}

	public WorkflowDefinitionActiveComparator(
		boolean ascending, Locale locale) {

		_ascending = ascending;
		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(
		WorkflowDefinition workflowDefinition1,
		WorkflowDefinition workflowDefinition2) {

		String activeLabel1 = _getActiveLabel(workflowDefinition1.isActive());
		String activeLabel2 = _getActiveLabel(workflowDefinition2.isActive());

		int value = _collator.compare(activeLabel1, activeLabel2);

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

	private String _getActiveLabel(boolean active) {
		if (active) {
			return LanguageUtil.get(_locale, "yes");
		}

		return LanguageUtil.get(_locale, "no");
	}

	private final boolean _ascending;
	private final Collator _collator;
	private final Locale _locale;

}