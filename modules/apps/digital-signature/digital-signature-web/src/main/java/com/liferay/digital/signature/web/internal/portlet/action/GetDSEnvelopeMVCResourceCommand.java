/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.digital.signature.web.internal.portlet.action;

import com.liferay.digital.signature.constants.DigitalSignaturePortletKeys;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.model.DSDocument;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.PDFProcessorUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Keven Leone
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DigitalSignaturePortletKeys.DIGITAL_SIGNATURE,
		"mvc.command.name=/digital_signature/get_ds_envelope"
	},
	service = MVCResourceCommand.class
)
public class GetDSEnvelopeMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String envelopeId = ParamUtil.getString(resourceRequest, "envelopeId");

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray dsDocumentsJSONArray = JSONFactoryUtil.createJSONArray();
		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject();

		try {
			DSEnvelope dsEnvelope = _dsEnvelopeManager.getDSEnvelope(
				themeDisplay.getCompanyId(), themeDisplay.getCompanyGroupId(),
				envelopeId);

			for (DSDocument dsDocument : dsEnvelope.getDSDocuments()) {
				JSONObject fileEntryDetailsJSONObject =
					_getFileEntryDetailsJSONObject(themeDisplay, dsDocument);

				if (fileEntryDetailsJSONObject != null) {
					dsDocumentsJSONArray.put(fileEntryDetailsJSONObject);
				}
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				responseJSONObject.put(
					"envelope", dsEnvelope.toJSONObject()
				).put(
					"fileEntries", dsDocumentsJSONArray
				));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	private JSONObject _getFileEntryDetailsJSONObject(
			ThemeDisplay themeDisplay, DSDocument dsDocument)
		throws PortalException {

		String dsDocumentId = dsDocument.getDSDocumentId();

		if (!dsDocumentId.equals("certificate")) {
			Long fileEntryId = GetterUtil.getLong(dsDocumentId);

			try {
				FileEntry fileEntry = _dlAppLocalService.getFileEntry(
					fileEntryId);

				FileVersion fileVersion = fileEntry.getFileVersion();

				return JSONUtil.put(
					"fileEntryId", fileEntryId
				).put(
					"initialPage", 1
				).put(
					"previewFileCount",
					PDFProcessorUtil.getPreviewFileCount(fileVersion)
				).put(
					"previewFileURL",
					DLURLHelperUtil.getPreviewURL(
						fileEntry, fileVersion, themeDisplay,
						"&previewFileIndex=")
				);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetDSEnvelopeMVCResourceCommand.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DSEnvelopeManager _dsEnvelopeManager;

}