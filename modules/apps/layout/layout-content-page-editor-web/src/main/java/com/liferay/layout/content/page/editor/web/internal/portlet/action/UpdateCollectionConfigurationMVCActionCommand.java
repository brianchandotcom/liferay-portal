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
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/update_collection_configuration"
	},
	service = MVCActionCommand.class
)
public class UpdateCollectionConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");
		long plid = ParamUtil.getLong(actionRequest, "plid");

		try {
			LayoutStructureUtil.updateLayoutPageTemplateData(
				themeDisplay.getScopeGroupId(), segmentsExperienceId, plid,
				layoutStructure -> {
					String itemId = ParamUtil.getString(
						actionRequest, "itemId");

					LayoutStructureItem layoutStructureItem =
						layoutStructure.getLayoutStructureItem(itemId);

					if (!(layoutStructureItem instanceof
							CollectionStyledLayoutStructureItem)) {

						return;
					}

					String collectionConfig = ParamUtil.getString(
						actionRequest, "collectionConfig");

					CollectionStyledLayoutStructureItem
						collectionStyledLayoutStructureItem =
							(CollectionStyledLayoutStructureItem)
								layoutStructureItem;

					JSONObject collectionJSONObject =
						collectionStyledLayoutStructureItem.
							getCollectionJSONObject();

					collectionJSONObject.put(
						"config",
						_jsonFactory.createJSONObject(collectionConfig));

					collectionStyledLayoutStructureItem.setCollectionJSONObject(
						collectionJSONObject);

					layoutStructure.updateItemConfig(
						collectionStyledLayoutStructureItem.
							getItemConfigJSONObject(),
						itemId);
				});

			sendRedirect(
				actionRequest, actionResponse,
				ParamUtil.getString(actionRequest, "redirect"));
		}
		catch (Exception exception) {
			_log.error(exception);

			hideDefaultErrorMessage(actionRequest);

			SessionErrors.add(actionRequest, "anUnexpectedErrorOccurred");

			actionResponse.setRenderParameter(
				"mvcRenderCommandName",
				"/layout_content_page_editor/edit_collection_configuration");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateCollectionConfigurationMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

}