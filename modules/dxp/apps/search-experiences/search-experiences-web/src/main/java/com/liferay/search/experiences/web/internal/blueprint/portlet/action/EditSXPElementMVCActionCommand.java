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
import com.liferay.search.experiences.exception.SXPElementElementDefinitionJSONException;
import com.liferay.search.experiences.exception.SXPElementTitleException;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementService;
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
		"mvc.command.name=" + SXPBlueprintMVCCommandNames.EDIT_SXP_ELEMENT
	},
	service = MVCActionCommand.class
)
public class EditSXPElementMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Map<Locale, String> titleMap = SXPBlueprintRequestUtil.getTitle(
			actionRequest);

		Map<Locale, String> descriptionMap =
			SXPBlueprintRequestUtil.getDescription(actionRequest);

		String elementDefinitionJSON =
			SXPBlueprintRequestUtil.getElementDefinitionJSON(actionRequest);

		boolean hidden = SXPBlueprintRequestUtil.getHidden(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			ServiceContext serviceContext = _createServiceContext(
				actionRequest);

			JSONObject jsonObject = JSONUtil.put("title", titleMap);

			if (Constants.ADD.equals(cmd)) {
				SXPElement sxpElement = _sxpElementService.addSXPElement(
					descriptionMap, elementDefinitionJSON, false, titleMap,
					SXPBlueprintRequestUtil.getSXPElementType(actionRequest),
					serviceContext);

				jsonObject = JSONUtil.put(
					"redirectURL",
					_getRedirectURL(
						actionRequest, actionResponse,
						sxpElement.getSXPElementId()));

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}
			else if (SXPBlueprintWebKeys.HIDE.equals(cmd)) {
				long[] hideConfigurationIds = _getElementIds(actionRequest);

				for (long sxpElementId : hideConfigurationIds) {
					SXPElement sxpElement = _sxpElementService.getSXPElement(
						sxpElementId);

					_sxpElementService.updateSXPElement(
						sxpElement.getSXPElementId(),
						sxpElement.getDescriptionMap(),
						sxpElement.getElementDefinitionJSON(), hidden,
						sxpElement.getTitleMap(), serviceContext);
				}

				sendRedirect(
					actionRequest, actionResponse,
					ParamUtil.getString(actionRequest, "redirect"));
			}
			else {
				_sxpElementService.updateSXPElement(
					SXPBlueprintRequestUtil.getSXPElementId(actionRequest),
					descriptionMap, elementDefinitionJSON, hidden, titleMap,
					serviceContext);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}
		}
		catch (SXPElementElementDefinitionJSONException
					sxpElementDefinitionJSONException) {

			_log.error(
				sxpElementDefinitionJSONException.getMessage(),
				sxpElementDefinitionJSONException);

			SessionErrors.add(
				actionRequest, SXPBlueprintWebKeys.ERROR,
				sxpElementDefinitionJSONException.getMessage());

			_sxpBlueprintExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse,
				sxpElementDefinitionJSONException);
		}
		catch (SXPElementTitleException SXPElementTitleException) {
			_log.error(
				SXPElementTitleException.getMessage(),
				SXPElementTitleException);

			SessionErrors.add(
				actionRequest, SXPBlueprintWebKeys.ERROR,
				SXPElementTitleException.getMessage());

			_sxpBlueprintExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, SXPElementTitleException);
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
			SXPElement.class.getName(), actionRequest);

		serviceContext.setAttribute("skip.sxpElement.validation", Boolean.TRUE);

		return serviceContext;
	}

	private long[] _getElementIds(ActionRequest actionRequest) {
		long[] sxpElementIds = null;

		long sxpElementId = SXPBlueprintRequestUtil.getSXPElementId(
			actionRequest);

		if (sxpElementId > 0) {
			sxpElementIds = new long[] {sxpElementId};
		}
		else {
			sxpElementIds = ParamUtil.getLongValues(
				actionRequest, SXPBlueprintWebKeys.ROW_IDS);
		}

		return sxpElementIds;
	}

	private String _getRedirectURL(
		ActionRequest actionRequest, ActionResponse actionResponse,
		long sxpElementId) {

		LiferayActionResponse liferayActionResponse =
			(LiferayActionResponse)actionResponse;

		return PortletURLBuilder.createRenderURL(
			liferayActionResponse
		).setMVCRenderCommandName(
			SXPBlueprintMVCCommandNames.EDIT_SXP_ELEMENT
		).setRedirect(
			ParamUtil.getString(actionRequest, "redirect")
		).setParameter(
			SXPBlueprintWebKeys.SXP_ELEMENT_ID, sxpElementId
		).buildString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditSXPElementMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SXPBlueprintExceptionRequestHandler
		_sxpBlueprintExceptionRequestHandler;

	@Reference
	private SXPElementService _sxpElementService;

}