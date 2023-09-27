/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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