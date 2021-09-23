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

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.exception.SXPBlueprintConfigurationsJSONException;
import com.liferay.search.experiences.exception.SXPBlueprintTitleException;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintService;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintWebKeys;
import com.liferay.search.experiences.web.internal.blueprint.handler.SXPBlueprintExceptionRequestHandler;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintRequestUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT,
		"mvc.command.name=" + SXPBlueprintMVCCommandNames.EDIT_SXP_BLUEPRINT
	},
	service = MVCActionCommand.class
)
public class EditSXPBlueprintMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String configurationsJSON =
			SXPBlueprintRequestUtil.getConfigurationsJSON(actionRequest);

		Map<Locale, String> descriptionMap =
			SXPBlueprintRequestUtil.getDescription(actionRequest);

		String elementInstancesJSON =
			SXPBlueprintRequestUtil.getElementInstancesJSON(actionRequest);

		Map<Locale, String> titleMap = SXPBlueprintRequestUtil.getTitle(
			actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			ServiceContext serviceContext = _createServiceContext(
				actionRequest);

			JSONObject jsonObject = JSONUtil.put("title", titleMap);

			if (Constants.ADD.equals(cmd)) {
				SXPBlueprint sxpBlueprint =
					_sxpSXPBlueprintService.addSXPBlueprint(
						configurationsJSON, descriptionMap,
						elementInstancesJSON, titleMap, serviceContext);

				jsonObject = JSONUtil.put(
					"redirectURL",
					_getRedirectURL(
						actionRequest, actionResponse,
						sxpBlueprint.getSXPBlueprintId()));
			}
			else {
				_sxpSXPBlueprintService.updateSXPBlueprint(
					SXPBlueprintRequestUtil.getSXPBlueprintId(actionRequest),
					configurationsJSON, descriptionMap, elementInstancesJSON,
					titleMap, serviceContext);
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (SXPBlueprintConfigurationsJSONException
					sxpBlueprintConfigurationsJSONException) {

			_log.error(
				sxpBlueprintConfigurationsJSONException.getMessage(),
				sxpBlueprintConfigurationsJSONException);

			SessionErrors.add(
				actionRequest, SXPBlueprintWebKeys.ERROR,
				sxpBlueprintConfigurationsJSONException.getMessage());

			_sxpBlueprintExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse,
				sxpBlueprintConfigurationsJSONException);
		}
		catch (SXPBlueprintTitleException sxpBlueprintTitleException) {
			_log.error(
				sxpBlueprintTitleException.getMessage(),
				sxpBlueprintTitleException);

			SessionErrors.add(
				actionRequest, SXPBlueprintWebKeys.ERROR,
				sxpBlueprintTitleException.getMessage());

			_sxpBlueprintExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, sxpBlueprintTitleException);
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(
				actionRequest, SXPBlueprintWebKeys.ERROR,
				portalException.getMessage());

			_sxpBlueprintExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, portalException);
		}
	}

	private ServiceContext _createServiceContext(ActionRequest actionRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			SXPBlueprint.class.getName(), actionRequest);

		serviceContext.setAttribute(
			"skip.sxpblueprint.validation", Boolean.TRUE);

		return serviceContext;
	}

	private String _getRedirectURL(
		ActionRequest actionRequest, ActionResponse actionResponse,
		long sxpBlueprintId) {

		LiferayActionResponse liferayActionResponse =
			(LiferayActionResponse)actionResponse;

		return PortletURLBuilder.createRenderURL(
			liferayActionResponse
		).setMVCRenderCommandName(
			SXPBlueprintMVCCommandNames.EDIT_SXP_BLUEPRINT
		).setRedirect(
			ParamUtil.getString(actionRequest, "redirect")
		).setParameter(
			SXPBlueprintWebKeys.SXP_BLUEPRINT_ID, sxpBlueprintId
		).buildString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditSXPBlueprintMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SXPBlueprintExceptionRequestHandler
		_sxpBlueprintExceptionRequestHandler;

	@Reference
	private SXPBlueprintService _sxpSXPBlueprintService;

}