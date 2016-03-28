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

package com.liferay.dynamic.data.mapping.form.field.type.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Comparator;

/**
 * @author Leonardo Barros
 */
public class DDMFormFieldTypeDisplayOrderComparator
	implements Comparator<ServiceWrapper<DDMFormFieldType>> {

	public DDMFormFieldTypeDisplayOrderComparator() {
		this(true);
	}

	public DDMFormFieldTypeDisplayOrderComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceWrapper<DDMFormFieldType> serviceWrapper1,
		ServiceWrapper<DDMFormFieldType> serviceWrapper2) {

		Integer propertyValue1 = MapUtil.getInteger(
			serviceWrapper1.getProperties(),
			_DDM_FORM_FIELD_TYPE_DISPLAY_ORDER_PROPERTY, Integer.MAX_VALUE);

		Integer propertyValue2 = MapUtil.getInteger(
			serviceWrapper2.getProperties(),
			_DDM_FORM_FIELD_TYPE_DISPLAY_ORDER_PROPERTY, Integer.MAX_VALUE);

		int value = propertyValue1.compareTo(propertyValue2);

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	public boolean isAscending() {
		return _ascending;
	}

	private static final String _DDM_FORM_FIELD_TYPE_DISPLAY_ORDER_PROPERTY =
		"ddm.form.field.type.display.order";

	private final boolean _ascending;

}