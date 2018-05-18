/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.util.comparator;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionDisplayDateComparator
	extends OrderByComparator<CPDefinition> {

	public static final String ORDER_BY_ASC = "displayDate ASC";

	public static final String ORDER_BY_DESC = "displayDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"displayDate"};

	public CPDefinitionDisplayDateComparator() {
		this(false);
	}

	public CPDefinitionDisplayDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(CPDefinition cpDefinition1, CPDefinition cpDefinition2) {
		int value = DateUtil.compareTo(
			cpDefinition1.getDisplayDate(), cpDefinition2.getDisplayDate());

		if (_ascending) {
			return value;
		}
		else {
			return Math.negateExact(value);
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