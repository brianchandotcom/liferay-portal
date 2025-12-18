/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.LayoutStructureRule;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"jakarta.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/update_rules"
	},
	service = MVCActionCommand.class
)
public class UpdateRulesMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return JSONUtil.put(
			"layoutData",
			LayoutStructureUtil.updateLayoutPageTemplateData(
				themeDisplay.getScopeGroupId(),
				ParamUtil.getLong(actionRequest, "segmentsExperienceId"),
				themeDisplay.getPlid(),
				layoutStructure -> {
					layoutStructure.deleteLayoutStructureRules();

					JSONArray rulesJSONArray = _jsonFactory.createJSONArray(
						ParamUtil.getString(actionRequest, "rules"));

					for (Object object : rulesJSONArray) {
						JSONObject ruleJSONObject = (JSONObject)object;

						LayoutStructureRule layoutStructureRule =
							layoutStructure.addLayoutStructureRule(
								ruleJSONObject.getString("id"),
								ruleJSONObject.getString("name"));

						layoutStructureRule.setActionsJSONArray(
							ruleJSONObject.getJSONArray("actions"));
						layoutStructureRule.setConditionsJSONArray(
							ruleJSONObject.getJSONArray("conditions"));
						layoutStructureRule.setConditionType(
							ruleJSONObject.getString("conditionType"));
						layoutStructureRule.setScript(
							ruleJSONObject.getString("script", null));
					}
				}));
	}

	@Reference
	private JSONFactory _jsonFactory;

}