/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_0_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.util.GetterUtil;

import java.text.NumberFormat;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryVersionUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public DLFileEntryVersionUpgradeColumnImpl(String name) {
		super(name);
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		double version = GetterUtil.getDouble(String.valueOf(oldValue));

		NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);

		numberFormat.setMaximumFractionDigits(1);
		numberFormat.setMinimumFractionDigits(1);

		return numberFormat.format(version);
	}

}