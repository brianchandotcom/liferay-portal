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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayActionResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.handler.BlueprintExceptionRequestHandler;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminRequestHelper;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsPortletKeys;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlueprintsPortletKeys.BLUEPRINTS_ADMIN,
		"mvc.command.name=" + BlueprintsAdminMVCCommandNames.EDIT_BLUEPRINT
	},
	service = MVCActionCommand.class
)
public class EditBlueprintMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, BlueprintsAdminWebKeys.TITLE);

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, BlueprintsAdminWebKeys.DESCRIPTION);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Blueprint.class.getName(), actionRequest);

			String configuration = ParamUtil.getString(
				actionRequest, "configuration");

			String selectedElements = ParamUtil.getString(
				actionRequest, "selectedElements");

			JSONObject jsonObject = JSONUtil.put("title", titleMap);

			if (Constants.ADD.equals(cmd)) {
				Blueprint blueprint = _blueprintService.addCompanyBlueprint(
					titleMap, descriptionMap, configuration, selectedElements,
					_blueprintsAdminRequestHelper.getTypeFromRequest(
						actionRequest),
					serviceContext);

				jsonObject = JSONUtil.put(
					"redirectURL",
					_getRedirectURL(
						actionRequest, actionResponse,
						blueprint.getBlueprintId()));
			}
			else {
				_blueprintService.updateBlueprint(
					_blueprintsAdminRequestHelper.getIdFromRequest(
						actionRequest),
					titleMap, descriptionMap, configuration, selectedElements,
					serviceContext);
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			hideDefaultErrorMessage(actionRequest);

			_blueprintExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, portalException);
		}
	}

	private String _getRedirectURL(
		ActionRequest actionRequest, ActionResponse actionResponse,
		long blueprintId) {

		LiferayActionResponse liferayActionResponse =
			(LiferayActionResponse)actionResponse;

		PortletURL portletURL = liferayActionResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			BlueprintsAdminMVCCommandNames.EDIT_BLUEPRINT);
		portletURL.setParameter(
			"redirect", ParamUtil.getString(actionRequest, "redirect"));
		portletURL.setParameter(
			BlueprintsAdminWebKeys.BLUEPRINT_ID, String.valueOf(blueprintId));

		return portletURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditBlueprintMVCActionCommand.class);

	@Reference
	private BlueprintExceptionRequestHandler _blueprintExceptionRequestHandler;

	@Reference
	private BlueprintsAdminRequestHelper _blueprintsAdminRequestHelper;

	@Reference
	private BlueprintService _blueprintService;

	@Reference
	private JSONFactory _jsonFactory;

}