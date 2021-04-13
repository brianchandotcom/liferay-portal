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

package com.liferay.users.admin.management.toolbar;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Drew Brokke
 */
public interface FilterContributor {

	public String getDefaultValue();

	public default String[] getFilterLabelValues() {
		return ArrayUtil.filter(
			getValues(), value -> !value.equals(getDefaultValue()));
	}

	public String getLabel(Locale locale);

	public String getManagementToolbarKey();

	public String getParameter();

	public Map<String, Object> getSearchParameters(String currentValue);

	public String getShortLabel(Locale locale);

	public String getValueLabel(Locale locale, String value);

	public String[] getValues();

}