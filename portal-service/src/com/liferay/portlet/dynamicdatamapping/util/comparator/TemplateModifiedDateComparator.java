/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.Date;

/**
 * @author Eduardo Garcia
 */
public class TemplateModifiedDateComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC = "DDMTemplate.modifiedDate ASC";

	public static final String ORDER_BY_DESC = "DDMTemplate.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public TemplateModifiedDateComparator() {
		this(false);
	}

	public TemplateModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		DDMTemplate template1 = (DDMTemplate)obj1;
		DDMTemplate template2 = (DDMTemplate)obj2;

		Date modifiedDate1 = template1.getModifiedDate();
		Date modifiedDate2 = template2.getModifiedDate();

		int value = DateUtil.compareTo(modifiedDate1, modifiedDate2);

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

	private boolean _ascending;

}