/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.blueprint.portlet.action;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.blueprint.util.SXPEngineContextHelper;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.service.SXPBlueprintService;
import com.liferay.search.experiences.service.SXPElementService;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintWebKeys;
import com.liferay.search.experiences.web.internal.blueprint.display.context.EditSXPElementDisplayBuilder;
import com.liferay.search.experiences.web.internal.blueprint.display.context.EntryDisplayContext;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT,
		"mvc.command.name=" + SXPBlueprintMVCCommandNames.EDIT_SXP_ELEMENT
	},
	service = MVCRenderCommand.class
)
public class EditSXPElementMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		EntryDisplayContext entryDisplayContext =
			new EditSXPElementDisplayBuilder(
				renderRequest, renderResponse, _sxpEngineContextHelper,
				_sxpElementService, _jsonFactory, _language
			).build();

		renderRequest.setAttribute(
			SXPBlueprintWebKeys.ENTRY_DISPLAY_CONTEXT, entryDisplayContext);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(entryDisplayContext.getRedirect());

		return "/edit_sxp_element.jsp";
	}

	// TODO see the comments in EditSXPElementDisplayBuilder, line 54

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private SXPElementService _sxpElementService;

	@Reference
	private SXPEngineContextHelper _sxpEngineContextHelper;

	@Reference
	private SXPBlueprintService _sxpSXPBlueprintService;

}