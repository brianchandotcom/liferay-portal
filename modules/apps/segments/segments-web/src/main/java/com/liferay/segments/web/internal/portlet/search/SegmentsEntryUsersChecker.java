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

package com.liferay.segments.web.internal.portlet.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryRelService;

import javax.portlet.RenderResponse;

/**
 * @author Eduardo García
 */
public class SegmentsEntryUsersChecker extends EmptyOnClickRowChecker {

	public SegmentsEntryUsersChecker(
		RenderResponse renderResponse,
		SegmentsEntryRelService segmentsEntryRelService, Portal portal,
		SegmentsEntry segmentsEntry) {

		super(renderResponse);

		_segmentsEntryRelService = segmentsEntryRelService;
		_portal = portal;
		_segmentsEntry = segmentsEntry;
	}

	@Override
	public boolean isChecked(Object obj) {
		User user = null;

		if (obj instanceof User) {
			user = (User)obj;
		}
		else if (obj instanceof Object[]) {
			user = (User)((Object[])obj)[0];
		}
		else {
			throw new IllegalArgumentException(obj + " is not a user");
		}

		return _segmentsEntryRelService.hasSegmentsEntryRel(
			_segmentsEntry.getSegmentsEntryId(),
			_portal.getClassNameId(User.class), user.getUserId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		User user = (User)obj;

		return isChecked(user);
	}

	private final Portal _portal;
	private final SegmentsEntry _segmentsEntry;
	private final SegmentsEntryRelService _segmentsEntryRelService;

}