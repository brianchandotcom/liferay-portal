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

import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionLinkPriorityComparator
	extends OrderByComparator<CPDefinitionLink> {

	public static final String ORDER_BY_ASC = "CPDefinitionLink.priority ASC";

	public static final String ORDER_BY_DESC = "CPDefinitionLink.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public CPDefinitionLinkPriorityComparator() {
		this(false);
	}

	public CPDefinitionLinkPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CPDefinitionLink cpDefinitionLink1,
		CPDefinitionLink cpDefinitionLink2) {

		int value = Double.compare(
			cpDefinitionLink1.getPriority(), cpDefinitionLink1.getPriority());

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