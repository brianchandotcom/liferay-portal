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

import com.liferay.document.library.kernel.exception.FileMimeTypeException;
import com.liferay.document.library.kernel.exception.FileSizeException;
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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.exception.SXPBlueprintConfigurationsJSONException;
import com.liferay.search.experiences.exception.SXPBlueprintTitleException;
import com.liferay.search.experiences.exception.SXPElementElementDefinitionJSONException;
import com.liferay.search.experiences.exception.SXPElementTitleException;
import com.liferay.search.experiences.problem.Problem;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintWebKeys;
import com.liferay.search.experiences.web.internal.blueprint.handler.SXPBlueprintExceptionRequestHandler;

import java.io.InputStream;

import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT,
		"mvc.command.name=" + SXPBlueprintMVCCommandNames.IMPORT
	},
	service = MVCActionCommand.class
)
public class ImportSXPBlueprintMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		hideDefaultErrorMessage(actionRequest);
		hideDefaultSuccessMessage(actionRequest);

		try {
			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(actionRequest);

			_checkExceededSizeLimit(uploadPortletRequest);

			_checkContentType(uploadPortletRequest.getContentType("file"));

			_import(actionRequest, actionResponse, uploadPortletRequest);
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

	private void _checkContentType(String contentType)
		throws FileMimeTypeException {

		if (!Objects.equals("application/json", contentType)) {
			throw new FileMimeTypeException(
				"Unsupported content type: " + contentType);
		}
	}

	private void _checkExceededSizeLimit(HttpServletRequest httpServletRequest)
		throws PortalException {

		UploadException uploadException =
			(UploadException)httpServletRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			Throwable throwable = uploadException.getCause();

			if (uploadException.isExceededFileSizeLimit()) {
				throw new FileSizeException(throwable);
			}

			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException(throwable);
			}

			if (uploadException.isExceededUploadRequestSizeLimit()) {
				throw new UploadRequestSizeException(throwable);
			}

			throw new PortalException(throwable);
		}
	}

	private String _getRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws WindowStateException {

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = _getSuccessRedirectURL(actionResponse);
		}

		return redirect;
	}

	private String _getSuccessRedirectURL(ActionResponse actionResponse)
		throws WindowStateException {

		LiferayActionResponse liferayActionResponse =
			(LiferayActionResponse)_portal.getLiferayPortletResponse(
				actionResponse);

		return PortletURLBuilder.createRenderURL(
			liferayActionResponse
		).setWindowState(
			WindowState.MAXIMIZED
		).buildString();
	}

	private void _import(
			ActionRequest actionRequest, ActionResponse actionResponse,
			UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"file")) {

			String json = StringUtil.read(inputStream);

			JSONObject jsonObject = _jsonFactory.createJSONObject(json);

			// TODO: rewrite import

			if (jsonObject.has("sxpBlueprint-payload")) {
				_sxpBlueprintsImporter.importBlueprint(
					themeDisplay.getCompanyId(),
					themeDisplay.getCompanyGroupId(), themeDisplay.getUserId(),
					jsonObject);
			}

			// TODO: rewrite import

			else if (jsonObject.has("sxpElement-payload")) {
				_sxpBlueprintsImporter.importElement(
					themeDisplay.getCompanyId(),
					themeDisplay.getCompanyGroupId(), themeDisplay.getUserId(),
					jsonObject, false);
			}
			else {
				throw new PortalException("Payload not found in import file");
			}

			String redirect = _getRedirect(actionRequest, actionResponse);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("redirectURL", redirect));
		}
		catch (SXPBlueprintConfigurationsJSONException
					sxpBlueprintConfigurationsJSONException) {

			_log.error(
				sxpBlueprintConfigurationsJSONException.getMessage(),
				sxpBlueprintConfigurationsJSONException);
			_logValidationMessages(
				sxpBlueprintConfigurationsJSONException.getProblems());

			_sxpBlueprintExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse,
				sxpBlueprintConfigurationsJSONException);
		}
		catch (SXPBlueprintTitleException sxpBlueprintTitleException) {
			_log.error(
				sxpBlueprintTitleException.getMessage(),
				sxpBlueprintTitleException);
			_logValidationMessages(sxpBlueprintTitleException.getProblems());

			_sxpBlueprintExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, sxpBlueprintTitleException);
		}
		catch (SXPElementElementDefinitionJSONException
					sxpElementDefinitionJSONException) {

			_log.error(
				sxpElementDefinitionJSONException.getMessage(),
				sxpElementDefinitionJSONException);
			_logValidationMessages(
				sxpElementDefinitionJSONException.getProblems());

			_sxpBlueprintExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse,
				sxpElementDefinitionJSONException);
		}
		catch (SXPElementTitleException sxpElementTitleException) {
			_log.error(
				sxpElementTitleException.getMessage(),
				sxpElementTitleException);
			_logValidationMessages(sxpElementTitleException.getProblems());

			_sxpBlueprintExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, sxpElementTitleException);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private void _logValidationMessages(List<Problem> problems) {
		problems.forEach(problem -> _log.error(problem.toString()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportSXPBlueprintMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private SXPBlueprintExceptionRequestHandler
		_sxpBlueprintExceptionRequestHandler;

}