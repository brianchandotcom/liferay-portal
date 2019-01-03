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

package com.liferay.segments.web.internal.portlet.action;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.service.SegmentsEntryRelService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS,
		"mvc.command.name=deleteSegmentsEntryUser"
	},
	service = MVCActionCommand.class
)
public class DeleteSegmentsEntryUserMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long segmentsEntryId = ParamUtil.getLong(
			actionRequest, "segmentsEntryId");

		long[] deleteSegmentsEntryUserIds = null;

		long segmentsEntryUserId = ParamUtil.getLong(actionRequest, "userId");

		if (segmentsEntryUserId > 0) {
			deleteSegmentsEntryUserIds = new long[] {segmentsEntryUserId};
		}
		else {
			deleteSegmentsEntryUserIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		for (long deleteSegmentsEntryUserId : deleteSegmentsEntryUserIds) {
			_segmentsEntryRelService.deleteSegmentsEntryRel(
				segmentsEntryId, _portal.getClassNameId(User.class),
				deleteSegmentsEntryUserId);
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryRelService _segmentsEntryRelService;

}