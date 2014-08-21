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

package com.liferay.portlet.mobiledevicerules.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;

/**
 * @author Edward Han
 */
public class RuleGroupInstancePriorityComparator
	extends OrderByComparator<MDRRuleGroupInstance> {

	public static final String ORDER_BY_ASC =
		"MDRRuleGroupInstance.priority ASC";

	public static final String ORDER_BY_DESC =
		"MDRRuleGroupInstance.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public RuleGroupInstancePriorityComparator() {
		this(true);
	}

	public RuleGroupInstancePriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		MDRRuleGroupInstance ruleGroupInstance1,
		MDRRuleGroupInstance ruleGroupInstance2) {

		int value =
			ruleGroupInstance2.getPriority() - ruleGroupInstance1.getPriority();

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

}