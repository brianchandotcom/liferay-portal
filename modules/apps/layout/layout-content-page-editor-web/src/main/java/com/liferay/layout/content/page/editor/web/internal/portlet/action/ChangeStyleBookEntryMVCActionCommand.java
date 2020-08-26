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

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Locale;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/change_style_book_entry"
	},
	service = MVCActionCommand.class
)
public class ChangeStyleBookEntryMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = _layoutLocalService.fetchLayout(themeDisplay.getPlid());

		LayoutPermissionUtil.check(
			themeDisplay.getPermissionChecker(), layout, ActionKeys.UPDATE);

		long styleBookEntryId = ParamUtil.getLong(
			actionRequest, "styleBookEntryId");

		_layoutLocalService.updateStyleBookEntryId(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			styleBookEntryId);

		return JSONUtil.put(
			"tokenValues",
			_getFrontendTokensValuesJSONArray(
				themeDisplay.getSiteGroupId(), themeDisplay.getLocale(),
				styleBookEntryId));
	}

	private JSONArray _getFrontendTokensValuesJSONArray(
			long groupId, Locale locale, long styleBookEntryId)
		throws Exception {

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.fetchStyleBookEntry(styleBookEntryId);

		JSONArray frontendTokensValuesJSONArray =
			JSONFactoryUtil.createJSONArray();

		JSONObject frontendTokenValuesJSONObject =
			JSONFactoryUtil.createJSONObject();

		if (styleBookEntry != null) {
			frontendTokenValuesJSONObject = JSONFactoryUtil.createJSONObject(
				styleBookEntry.getFrontendTokensValues());
		}

		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			groupId, false);

		FrontendTokenDefinition frontendTokenDefinition =
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				layoutSet.getThemeId());

		JSONObject frontendTokenDefinitionJSONObject =
			JSONFactoryUtil.createJSONObject(
				frontendTokenDefinition.getJSON(locale));

		JSONArray frontendTokenCategoriesJSONArray =
			frontendTokenDefinitionJSONObject.getJSONArray(
				"frontendTokenCategories");

		for (int i = 0; i < frontendTokenCategoriesJSONArray.length(); i++) {
			JSONObject frontendTokenCategoryJSONObject =
				frontendTokenCategoriesJSONArray.getJSONObject(i);

			JSONArray frontendTokenSetsJSONArray =
				frontendTokenCategoryJSONObject.getJSONArray(
					"frontendTokenSets");

			for (int j = 0; j < frontendTokenSetsJSONArray.length(); j++) {
				JSONObject frontendTokenSetJSONObject =
					frontendTokenSetsJSONArray.getJSONObject(j);

				JSONArray frontendTokensJSONArray =
					frontendTokenSetJSONObject.getJSONArray("frontendTokens");

				for (int k = 0; k < frontendTokensJSONArray.length(); k++) {
					JSONObject frontendTokenJSONObject =
						frontendTokensJSONArray.getJSONObject(k);

					String name = frontendTokenJSONObject.getString("name");

					JSONObject valueJSONObject =
						frontendTokenValuesJSONObject.getJSONObject(name);

					String value = StringPool.BLANK;

					if (valueJSONObject != null) {
						value = valueJSONObject.getString("value");
					}
					else {
						value = frontendTokenJSONObject.getString(
							"defaultValue");
					}

					JSONArray mappingsJSONArray =
						frontendTokenJSONObject.getJSONArray("mappings");
					String cssVariable = StringPool.BLANK;

					for (int l = 0; l < mappingsJSONArray.length(); l++) {
						JSONObject mapping = mappingsJSONArray.getJSONObject(l);

						if (Objects.equals(
								mapping.getString("type"), "cssVariable")) {

							cssVariable = mapping.getString("value");
						}
					}

					frontendTokensValuesJSONArray.put(
						JSONUtil.put(
							"cssVariable", cssVariable
						).put(
							"value", value
						));
				}
			}
		}

		return frontendTokensValuesJSONArray;
	}

	@Reference
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}