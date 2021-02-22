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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.segments.service.SegmentsExperienceService;

import java.util.Collections;

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
		"mvc.command.name=/layout_content_page_editor/update_segments_experience"
	},
	service = MVCActionCommand.class
)
public class UpdateSegmentsExperienceMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");

		long segmentsEntryId = ParamUtil.getLong(
			actionRequest, "segmentsEntryId");
		String name = ParamUtil.getString(actionRequest, "name");
		String[] languageIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "languageIds"));

		UnicodeProperties typeSettingsUnicodeProperties = new UnicodeProperties(
			true);

		typeSettingsUnicodeProperties.setProperty(
			PropsKeys.LOCALES, StringUtil.merge(languageIds));

		return JSONUtil.put(
			"segmentsExperience",
			_segmentsExperienceService.updateSegmentsExperience(
				segmentsExperienceId, segmentsEntryId,
				Collections.singletonMap(LocaleUtil.getSiteDefault(), name),
				true, typeSettingsUnicodeProperties));
	}

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

}