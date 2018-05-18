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

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceRegion;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceRegionNameComparator
	extends OrderByComparator<CommerceRegion> {

	public static final String ORDER_BY_ASC = "CommerceRegion.name ASC";

	public static final String ORDER_BY_DESC = "CommerceRegion.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public CommerceRegionNameComparator() {
		this(false);
	}

	public CommerceRegionNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceRegion commerceRegion1, CommerceRegion commerceRegion2) {

		String name1 = StringUtil.toLowerCase(commerceRegion1.getName());
		String name2 = StringUtil.toLowerCase(commerceRegion2.getName());

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