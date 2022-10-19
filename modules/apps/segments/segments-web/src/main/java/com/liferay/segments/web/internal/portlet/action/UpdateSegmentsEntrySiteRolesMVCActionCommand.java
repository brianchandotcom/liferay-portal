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

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.service.SegmentsEntryRoleLocalService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS,
		"mvc.command.name=/segments/update_segments_entry_site_roles"
	},
	service = MVCActionCommand.class
)
public class UpdateSegmentsEntrySiteRolesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long segmentsEntryId = ParamUtil.getLong(
			actionRequest, "segmentsEntryId");

		long[] siteRoleIds = ParamUtil.getLongValues(
			actionRequest, "siteRoleIds");

		_segmentsEntryRoleLocalService.setSegmentsEntrySiteRoles(
			segmentsEntryId, siteRoleIds,
			ServiceContextFactory.getInstance(actionRequest));
	}

	@Reference
	private SegmentsEntryRoleLocalService _segmentsEntryRoleLocalService;

}