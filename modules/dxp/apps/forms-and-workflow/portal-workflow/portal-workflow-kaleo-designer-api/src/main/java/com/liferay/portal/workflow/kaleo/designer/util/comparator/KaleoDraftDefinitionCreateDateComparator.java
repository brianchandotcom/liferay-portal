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

package com.liferay.portal.workflow.kaleo.designer.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition;

/**
 * Orders Kaleo draft definitions according to their creation date during
 * listing operations. The order can be ascending or descending and is defined
 * by the value specified in the class constructor.
 *
 * @author Rafael Praxedes
 * @see    com.liferay.portal.workflow.kaleo.designer.service.KaleoDraftDefinitionLocalService#getKaleoDraftDefinitions(
 *         String, int, int, int, OrderByComparator, ServiceContext)
 */
public class KaleoDraftDefinitionCreateDateComparator
	extends OrderByComparator<KaleoDraftDefinition> {

	public static final String ORDER_BY_ASC =
		"KaleoDraftDefinition.createDate ASC";

	public static final String ORDER_BY_DESC =
		"KaleoDraftDefinition.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public KaleoDraftDefinitionCreateDateComparator() {
		this(false);
	}

	public KaleoDraftDefinitionCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		KaleoDraftDefinition kaleoDraftDefinition1,
		KaleoDraftDefinition kaleoDraftDefinition2) {

		int value = DateUtil.compareTo(
			kaleoDraftDefinition1.getCreateDate(),
			kaleoDraftDefinition2.getCreateDate());

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