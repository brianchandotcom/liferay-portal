/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		Double displayOrder1 = MapUtil.getDouble(
			serviceWrapper1.getProperties(),
			"ddm.form.field.type.display.order", Integer.MAX_VALUE);
		Double displayOrder2 = MapUtil.getDouble(
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