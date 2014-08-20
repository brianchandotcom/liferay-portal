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

package com.liferay.portlet.dynamicdatamapping.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

/**
 * @author Eduardo Garcia
 */
public class TemplateIdComparator extends OrderByComparator<DDMTemplate> {

	public static final String ORDER_BY_ASC = "DDMTemplate.templateId ASC";

	public static final String ORDER_BY_DESC = "DDMTemplate.templateId DESC";

	public static final String[] ORDER_BY_FIELDS = {"templateId"};

	public TemplateIdComparator() {
		this(false);
	}

	public TemplateIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(DDMTemplate template1, DDMTemplate template2) {
		long templateId1 = template1.getTemplateId();
		long templateId2 = template2.getTemplateId();

		int value = 0;

		if (templateId1 < templateId2) {
			value = -1;
		}
		else if (templateId1 > templateId2) {
			value = 1;
		}

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