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

package com.liferay.user.associated.data.web.internal.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.user.associated.data.display.UADDisplay;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class UADHierarchyChecker extends EmptyOnClickRowChecker {

	public UADHierarchyChecker(
		PortletResponse portletResponse, UADDisplay<?>[] uadDisplays) {

		super(portletResponse);

		_uadDisplays = uadDisplays;
	}

	@Override
	protected String getRowCheckBox(
		HttpServletRequest httpServletRequest, boolean checked,
		boolean disabled, String name, String value, String checkBoxRowIds,
		String checkBoxAllRowIds, String checkBoxPostOnClick) {

		for (UADDisplay uadDisplay : _uadDisplays) {
			try {
				long primaryKey = GetterUtil.getLong(value);

				uadDisplay.get(primaryKey);

				Class<?> typeClass = uadDisplay.getTypeClass();

				name += typeClass.getSimpleName();

				return super.getRowCheckBox(
					httpServletRequest, checked, disabled, name, value,
					checkBoxRowIds, checkBoxAllRowIds, checkBoxPostOnClick);
			}
			catch (Exception e) {
			}
		}

		return StringPool.BLANK;
	}

	private final UADDisplay<?>[] _uadDisplays;

}