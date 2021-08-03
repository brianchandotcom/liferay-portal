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

package com.liferay.content.dashboard.web.internal.search.request;

import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina Goonzález
 */
public class ContentDashboardItemSubtypeChecker extends EmptyOnClickRowChecker {

	public ContentDashboardItemSubtypeChecker(
		List<? extends ContentDashboardItemSubtype>
			checkedContentDashboardItemSubtypes,
		RenderResponse renderResponse) {

		super(renderResponse);

		_checkedContentDashboardItemSubtypes =
			checkedContentDashboardItemSubtypes;
	}

	@Override
	public String getAllRowsCheckBox() {
		return null;
	}

	@Override
	public String getAllRowsCheckBox(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean isChecked(Object object) {
		ContentDashboardItemSubtype contentDashboardItemSubtype =
			(ContentDashboardItemSubtype)object;

		return _checkedContentDashboardItemSubtypes.contains(
			contentDashboardItemSubtype);
	}

	private final List<? extends ContentDashboardItemSubtype>
		_checkedContentDashboardItemSubtypes;

}