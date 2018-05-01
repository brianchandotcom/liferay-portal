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

package com.liferay.user.associated.data.display;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Locale;
import java.util.Map;

/**
 * @author William Newbury
 */
public interface UADDisplay<T> {

	public String getApplicationName();

	public default String[] getColumnFieldNames() {
		return getDisplayFieldNames();
	}

	public String[] getDisplayFieldNames();

	public default String getEditURL(
			T t, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return null;
	}

	public Map<String, Object> getFieldValues(T t, String[] fieldNames);

	public String getKey();

	public String getTypeName(Locale locale);

}