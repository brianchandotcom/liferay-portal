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

package com.liferay.roles.admin.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryRoleLocalServiceUtil;

import javax.portlet.RenderResponse;

/**
 * @author Pei-Jung Lan
 */
public class SegmentsEntryRoleChecker extends EmptyOnClickRowChecker {

	public SegmentsEntryRoleChecker(
		RenderResponse renderResponse, long roleId) {

		super(renderResponse);

		_roleId = roleId;
	}

	@Override
	public boolean isChecked(Object obj) {
		SegmentsEntry segmentsEntry = (SegmentsEntry)obj;

		return SegmentsEntryRoleLocalServiceUtil.hasSegmentEntryRole(
			segmentsEntry.getSegmentsEntryId(), _roleId);
	}

	@Override
	public boolean isDisabled(Object obj) {
		return isChecked(obj);
	}

	private final long _roleId;

}