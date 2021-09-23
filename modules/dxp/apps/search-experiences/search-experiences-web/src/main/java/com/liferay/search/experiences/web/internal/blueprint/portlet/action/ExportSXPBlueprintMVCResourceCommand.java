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
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintService;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintExportUtil;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintRequestUtil;

import java.util.Optional;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT,
		"mvc.command.name=" + SXPBlueprintMVCCommandNames.EXPORT_SXP_BLUEPRINT
	},
	service = MVCResourceCommand.class
)
public class ExportSXPBlueprintMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Optional<SXPBlueprint> optional =
			SXPBlueprintRequestUtil.getSXPBlueprint(
				resourceRequest, resourceResponse);

		if (!optional.isPresent()) {
			return;
		}

		SXPBlueprint sxpBlueprint = optional.get();

		String responseString = _buildResponseString(sxpBlueprint);

		String title = _getFileTitle(resourceRequest, sxpBlueprint);

		SXPBlueprintExportUtil.writeResponse(
			resourceRequest, resourceResponse, title, responseString);
	}

	private String _buildResponseString(SXPBlueprint sxpBlueprint)
		throws Exception {

		return JSONUtil.put(
			"sxp-blueprint-payload",
			JSONUtil.put(
				"configurationsJSON",
				_jsonFactory.createJSONObject(
					sxpBlueprint.getConfigurationsJSON())
			).put(
				"description",
				SXPBlueprintExportUtil.mapToJSONObject(
					sxpBlueprint.getDescriptionMap())
			).put(
				"elementInstancesJSON",
				_jsonFactory.createJSONObject(
					sxpBlueprint.getElementInstancesJSON())
			).put(
				"title",
				SXPBlueprintExportUtil.mapToJSONObject(
					sxpBlueprint.getTitleMap())
			)
		).toString();
	}

	private String _getFileTitle(
		ResourceRequest resourceRequest, SXPBlueprint sxpBlueprint) {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String title = sxpBlueprint.getTitle(themeDisplay.getLocale(), true);

		return title + ".json";
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private SXPBlueprintService _sxpSXPBlueprintService;

}