/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.Collections;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/undo_form_item_config"
	},
	service = MVCActionCommand.class
)
public class UndoFormItemConfigMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] addedItemIds = ParamUtil.getStringValues(
			actionRequest, "addedItemIds");
		JSONObject configJSONObject = _jsonFactory.createJSONObject(
			ParamUtil.getString(actionRequest, "config"));
		String itemId = ParamUtil.getString(actionRequest, "itemId");
		JSONArray movedItemsJSONArray = _jsonFactory.createJSONArray(
			ParamUtil.getString(actionRequest, "movedItemIds"));
		String[] removedItemIds = ParamUtil.getStringValues(
			actionRequest, "removedItemIds");
		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");

		return JSONUtil.put(
			"layoutData",
			LayoutStructureUtil.updateLayoutPageTemplateData(
				themeDisplay.getScopeGroupId(), segmentsExperienceId,
				themeDisplay.getPlid(),
				layoutStructure -> {
					LayoutStructureItem layoutStructureItem =
						layoutStructure.getLayoutStructureItem(itemId);

					layoutStructureItem.updateItemConfig(configJSONObject);

					for (int i = 0; i < movedItemsJSONArray.length(); i++) {
						JSONObject jsonObject =
							movedItemsJSONArray.getJSONObject(i);

						layoutStructure.moveLayoutStructureItem(
							jsonObject.getString("itemId"),
							jsonObject.getString("parentId"), -1);
					}

					layoutStructure.markLayoutStructureItemForDeletion(
						Arrays.asList(removedItemIds), Collections.emptyList());

					for (String addedItemId : addedItemIds) {
						layoutStructure.unmarkLayoutStructureItemForDeletion(
							addedItemId);
					}
				}));
	}

	@Reference
	private JSONFactory _jsonFactory;

}