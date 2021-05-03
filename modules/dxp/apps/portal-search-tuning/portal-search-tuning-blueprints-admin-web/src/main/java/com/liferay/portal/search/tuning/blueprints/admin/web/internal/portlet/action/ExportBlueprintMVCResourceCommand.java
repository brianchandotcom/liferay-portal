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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminExportUtil;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminRequestUtil;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsPortletKeys;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;

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
		"javax.portlet.name=" + BlueprintsPortletKeys.BLUEPRINTS_ADMIN,
		"mvc.command.name=" + BlueprintsAdminMVCCommandNames.EXPORT_BLUEPRINT
	},
	service = MVCResourceCommand.class
)
public class ExportBlueprintMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Optional<Blueprint> optional = BlueprintsAdminRequestUtil.getBlueprint(
			resourceRequest, resourceResponse);

		if (!optional.isPresent()) {
			return;
		}

		Blueprint blueprint = optional.get();

		String responseString = _buildResponseString(blueprint);

		String title = _getFileTitle(resourceRequest, blueprint);

		BlueprintsAdminExportUtil.writeResponse(
			resourceRequest, resourceResponse, title, responseString);
	}

	private String _buildResponseString(Blueprint blueprint) throws Exception {
		return JSONUtil.put(
			"blueprint-payload",
			JSONUtil.put(
				"configuration",
				_jsonFactory.createJSONObject(blueprint.getConfiguration())
			).put(
				"description",
				BlueprintsAdminExportUtil.mapToJSONObject(
					blueprint.getDescriptionMap())
			).put(
				"selectedElements",
				_jsonFactory.createJSONObject(blueprint.getSelectedElements())
			).put(
				"title",
				BlueprintsAdminExportUtil.mapToJSONObject(
					blueprint.getTitleMap())
			)
		).toString();
	}

	private String _getFileTitle(
		ResourceRequest resourceRequest, Blueprint blueprint) {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String title = blueprint.getTitle(themeDisplay.getLocale(), true);

		return title + ".json";
	}

	@Reference
	private BlueprintService _blueprintService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}