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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionOptionRelNameComparator
	extends OrderByComparator<CPDefinitionOptionRel> {

	public static final String ORDER_BY_ASC = "CPDefinitionOptionRel.name ASC";

	public static final String ORDER_BY_DESC =
		"CPDefinitionOptionRel.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public CPDefinitionOptionRelNameComparator() {
		this(false);
	}

	public CPDefinitionOptionRelNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CPDefinitionOptionRel cpDefinitionOptionRel1,
		CPDefinitionOptionRel cpDefinitionOptionRel2) {

		String name1 = StringUtil.toLowerCase(cpDefinitionOptionRel1.getName());
		String name2 = StringUtil.toLowerCase(cpDefinitionOptionRel2.getName());

		int value = name1.compareTo(name2);

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