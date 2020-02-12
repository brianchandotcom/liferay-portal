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

import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.ArrayList;
import java.util.List;

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
		"mvc.command.name=/content_layout/update_row_columns"
	},
	service = MVCActionCommand.class
)
public class UpdateRowColumnsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId",
			SegmentsExperienceConstants.ID_DEFAULT);
		String itemId = ParamUtil.getString(actionRequest, "itemId");
		int numberOfColumns = ParamUtil.getInteger(
			actionRequest, "numberOfColumns");

		List<Long> deletedFragmentEntryLinkIds = new ArrayList<>();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			JSONObject layoutDataJSONObject =
				LayoutStructureUtil.updateLayoutPageTemplateData(
					themeDisplay.getScopeGroupId(), segmentsExperienceId,
					themeDisplay.getPlid(),
					layoutStructure -> {
						List<LayoutStructureItem> deletedLayoutStructureItems =
							layoutStructure.updateRowColumnsLayoutStructureItem(
								itemId, numberOfColumns);

						for (long fragmentEntryLinkId :
								LayoutStructureUtil.getFragmentEntryLinkIds(
									deletedLayoutStructureItems)) {

							FragmentEntryLinkUtil.deleteFragmentEntryLink(
								themeDisplay.getCompanyId(),
								fragmentEntryLinkId, themeDisplay.getPlid(),
								_portletRegistry);

							deletedFragmentEntryLinkIds.add(
								fragmentEntryLinkId);
						}
					});

			jsonObject = JSONUtil.put(
				"deletedFragmentEntryLinkIds",
				deletedFragmentEntryLinkIds.toArray()
			).put(
				"layoutData", layoutDataJSONObject
			);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateRowColumnsMVCActionCommand.class);

	@Reference
	private PortletRegistry _portletRegistry;

}