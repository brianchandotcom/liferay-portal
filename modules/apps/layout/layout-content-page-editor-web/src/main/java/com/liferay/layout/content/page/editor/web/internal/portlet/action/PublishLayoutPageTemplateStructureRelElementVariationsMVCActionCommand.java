/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelElementVariationService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"jakarta.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/publish_layout_page_template_structure_rel_element_variations"
	},
	service = MVCActionCommand.class
)
public class
	PublishLayoutPageTemplateStructureRelElementVariationsMVCActionCommand
		extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long plid = ParamUtil.getLong(actionRequest, "plid");

		_layoutPageTemplateStructureRelElementVariationService.
			deleteLayoutPageTemplateStructureRelElementVariations(plid);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray elementVariationsJSONArray = _jsonFactory.createJSONArray(
			ParamUtil.getString(actionRequest, "elementVariations"));

		for (int i = 0; i < elementVariationsJSONArray.length(); i++) {
			JSONObject elementVariationJSONObject =
				elementVariationsJSONArray.getJSONObject(i);

			_layoutPageTemplateStructureRelElementVariationService.
				addLayoutPageTemplateStructureRelElementVariation(
					elementVariationJSONObject.getString(
						"externalReferenceCode"),
					themeDisplay.getScopeGroupId(),
					elementVariationJSONObject.getString("audienceEntryERC"),
					_toLocalizedMap(
						elementVariationJSONObject.getJSONObject("hideMap")),
					_toLocalizedMap(
						elementVariationJSONObject.getJSONObject("htmlMap")),
					_toLocalizedMap(
						elementVariationJSONObject.getJSONObject("jsMap")),
					elementVariationJSONObject.getString("name"), plid,
					elementVariationJSONObject.getString(
						"segmentsExperienceERC"),
					elementVariationJSONObject.getString("targetElement"),
					ServiceContextFactory.getInstance(actionRequest));
		}

		return _jsonFactory.createJSONObject();
	}

	@Override
	protected boolean isLayoutLockRequired() {
		return false;
	}

	private Map<Locale, String> _toLocalizedMap(JSONObject jsonObject) {
		Map<Locale, String> map = new HashMap<>();

		if (jsonObject == null) {
			return map;
		}

		for (String languageId : jsonObject.keySet()) {
			map.put(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return map;
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutPageTemplateStructureRelElementVariationService
		_layoutPageTemplateStructureRelElementVariationService;

}