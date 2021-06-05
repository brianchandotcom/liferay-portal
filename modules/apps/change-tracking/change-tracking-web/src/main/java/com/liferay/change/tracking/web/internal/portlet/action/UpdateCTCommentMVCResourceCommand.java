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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTComment;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/update_ct_comment"
	},
	service = MVCResourceCommand.class
)
public class UpdateCTCommentMVCResourceCommand
	extends GetCTCommentsMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		CTComment ctComment = null;

		long ctCommentId = ParamUtil.getLong(resourceRequest, "ctCommentId");

		String value = ParamUtil.getString(resourceRequest, "value");

		if (ctCommentId > 0) {
			ctComment = ctCommentLocalService.updateCTComment(
				ctCommentId, value);
		}
		else {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			long ctCollectionId = ParamUtil.getLong(
				resourceRequest, "ctCollectionId");
			long ctEntryId = ParamUtil.getLong(resourceRequest, "ctEntryId");

			ctComment = ctCommentLocalService.addCTComment(
				themeDisplay.getUserId(), ctCollectionId, ctEntryId, value);
		}

		JSONObject jsonObject = getCTCommentsJSONObject(resourceRequest);

		jsonObject.put("updatedCommentId", ctComment.getCtCommentId());

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

}