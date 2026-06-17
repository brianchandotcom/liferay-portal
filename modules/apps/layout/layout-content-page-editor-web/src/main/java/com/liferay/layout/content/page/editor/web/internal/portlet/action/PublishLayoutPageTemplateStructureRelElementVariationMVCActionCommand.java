/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRelElementVariation;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelElementVariationService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Localization;
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
		"mvc.command.name=/layout_content_page_editor/publish_layout_page_template_structure_rel_element_variation"
	},
	service = MVCActionCommand.class
)
public class
	PublishLayoutPageTemplateStructureRelElementVariationMVCActionCommand
		extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LayoutPageTemplateStructureRelElementVariation
			layoutPageTemplateStructureRelElementVariation =
				_layoutPageTemplateStructureRelElementVariationService.
					addLayoutPageTemplateStructureRelElementVariation(
						null, themeDisplay.getScopeGroupId(),
						ParamUtil.getString(actionRequest, "audienceEntryERC"),
						_localization.getLocalizationMap(actionRequest, "hide"),
						_localization.getLocalizationMap(actionRequest, "html"),
						_localization.getLocalizationMap(actionRequest, "js"),
						ParamUtil.getString(actionRequest, "name"),
						ParamUtil.getLong(
							actionRequest, "plid", themeDisplay.getPlid()),
						ParamUtil.getString(
							actionRequest, "segmentsExperienceERC"),
						ParamUtil.getString(actionRequest, "targetElement"),
						serviceContext);

		return JSONUtil.put(
			"layoutPageTemplateStructureRelElementVariationId",
			layoutPageTemplateStructureRelElementVariation.
				getLayoutPageTemplateStructureRelElementVariationId());
	}

	@Override
	protected boolean isLayoutLockRequired() {
		return false;
	}

	@Reference
	private LayoutPageTemplateStructureRelElementVariationService
		_layoutPageTemplateStructureRelElementVariationService;

	@Reference
	private Localization _localization;

}