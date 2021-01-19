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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.segments.SegmentsExperienceUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.service.SegmentsExperienceService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/update_segments_experience_priority"
	},
	service = MVCActionCommand.class
)
public class UpdateSegmentsExperiencePriorityMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");

		int newPriority = ParamUtil.getInteger(actionRequest, "newPriority");

		_segmentsExperienceService.updateSegmentsExperiencePriority(
			segmentsExperienceId, newPriority);

		return JSONUtil.put(
			"availableSegmentsExperiences",
			SegmentsExperienceUtil.getAvailableSegmentsExperiences(
				_portal.getHttpServletRequest(actionRequest)));
	}

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

}