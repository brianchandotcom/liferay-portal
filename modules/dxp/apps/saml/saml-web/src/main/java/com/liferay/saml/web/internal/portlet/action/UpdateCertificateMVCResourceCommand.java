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

package com.liferay.saml.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.constants.SamlPortletKeys;
import com.liferay.saml.web.internal.upload.CertificateUploadFileEntryHandler;
import com.liferay.saml.web.internal.upload.CertificateUploadResponseHandler;
import com.liferay.saml.web.internal.util.SamlTempFileEntryUtil;
import com.liferay.upload.UploadHandler;

import java.io.IOException;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/update_certificate"
	},
	service = MVCResourceCommand.class
)
public class UpdateCertificateMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin()) {
			throw new PrincipalException();
		}

		String cmd = ParamUtil.get(
			resourceRequest, Constants.CMD, Constants.GET_TEMP);

		if (cmd.equals(Constants.ADD_TEMP)) {
			_addTempFile(resourceRequest, resourceResponse);
		}
		else if (cmd.equals(Constants.DELETE_TEMP)) {
			_deleteTempFile(resourceRequest, resourceResponse, themeDisplay);
		}
		else if (cmd.equals(Constants.GET_TEMP)) {
			_includeTempFileName(
				resourceRequest, resourceResponse, themeDisplay.getUser());
		}
	}

	private void _addTempFile(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		_uploadHandler.upload(
			_certificateUploadFileEntryHandler,
			_certificateUploadResponseHandler, resourceRequest,
			resourceResponse);
	}

	private void _deleteTempFile(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			SamlTempFileEntryUtil.deleteTempFileEntry(
				themeDisplay.getUser(),
				ParamUtil.getString(resourceRequest, "fileName"));

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			String errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put(
				"deleted", Boolean.FALSE
			).put(
				"errorMessage", errorMessage
			);
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	private void _includeTempFileName(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			User user)
		throws IOException {

		String selectUploadedFile = ParamUtil.getString(
			resourceRequest, "selectUploadedFile");

		if (Validator.isNotNull(selectUploadedFile)) {
			try {
				FileEntry tempFileEntry =
					SamlTempFileEntryUtil.getTempFileEntry(
						user, selectUploadedFile);

				if (tempFileEntry != null) {
					JSONPortletResponseUtil.writeJSON(
						resourceRequest, resourceResponse,
						JSONUtil.put(selectUploadedFile));

					return;
				}
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException.toString(), portalException);
				}
			}
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, JSONUtil.putAll());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateCertificateMVCResourceCommand.class);

	@Reference
	private CertificateUploadFileEntryHandler
		_certificateUploadFileEntryHandler;

	@Reference
	private CertificateUploadResponseHandler _certificateUploadResponseHandler;

	@Reference
	private UploadHandler _uploadHandler;

}