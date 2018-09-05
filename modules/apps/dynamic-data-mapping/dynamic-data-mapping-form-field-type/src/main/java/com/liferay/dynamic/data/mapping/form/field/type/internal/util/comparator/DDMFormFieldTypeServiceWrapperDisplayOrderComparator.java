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

package com.liferay.dynamic.data.mapping.form.field.type.internal.util.comparator;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Comparator;

/**
 * @author Leonardo Barros
 */
public class DDMFormFieldTypeServiceWrapperDisplayOrderComparator
	implements Comparator<ServiceWrapper<DDMFormFieldType>> {

	public DDMFormFieldTypeServiceWrapperDisplayOrderComparator() {
		this(true);
	}

	public DDMFormFieldTypeServiceWrapperDisplayOrderComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceWrapper<DDMFormFieldType> serviceWrapper1,
		ServiceWrapper<DDMFormFieldType> serviceWrapper2) {

		Integer displayOrder1 = MapUtil.getInteger(
			serviceWrapper1.getProperties(),
			"ddm.form.field.type.display.order", Integer.MAX_VALUE);
		Integer displayOrder2 = MapUtil.getInteger(
			serviceWrapper2.getProperties(),
			"ddm.form.field.type.display.order", Integer.MAX_VALUE);

		int value = displayOrder1.compareTo(displayOrder2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}