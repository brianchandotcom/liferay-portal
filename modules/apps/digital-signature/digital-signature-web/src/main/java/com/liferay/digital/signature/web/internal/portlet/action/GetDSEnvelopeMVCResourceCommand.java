/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.signature.web.internal.portlet.action;

import com.liferay.digital.signature.constants.DigitalSignaturePortletKeys;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.ImageProcessor;
import com.liferay.document.library.kernel.util.PDFProcessorUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
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

import java.util.Objects;
import java.util.Set;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Keven Leone
 */
@Component(
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

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DSEnvelope dsEnvelope = _dsEnvelopeManager.getDSEnvelope(
			themeDisplay.getCompanyId(), themeDisplay.getCompanyGroupId(),
			ParamUtil.getString(resourceRequest, "dsEnvelopeId"));

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"envelope", dsEnvelope.toJSONObject()
			).put(
				"fileEntries",
				JSONUtil.toJSONArray(
					dsEnvelope.getDSDocuments(),
					dsDocument -> _toJSONObject(
						dsDocument.getDSDocumentId(), themeDisplay),
					_log)
			));
	}

	private JSONObject _toJSONObject(
			String dsDocumentId, ThemeDisplay themeDisplay)
		throws Exception {

		if (Objects.equals(dsDocumentId, "certificate")) {
			return null;
		}

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			GetterUtil.getLong(dsDocumentId));

		FileVersion fileVersion = fileEntry.getFileVersion();

		Set<String> imageMimeTypes = _imageProcessor.getImageMimeTypes();

		if (imageMimeTypes.contains(fileEntry.getMimeType())) {
			return JSONUtil.put(
				"imageURL",
				DLURLHelperUtil.getPreviewURL(
					fileEntry, fileVersion, themeDisplay, "&imagePreview=1"));
		}

		if (PDFProcessorUtil.isDocumentSupported(fileVersion)) {
			return JSONUtil.put(
				"initialPage", 1
			).put(
				"previewFileCount",
				PDFProcessorUtil.getPreviewFileCount(fileVersion)
			).put(
				"previewFileURL",
				DLURLHelperUtil.getPreviewURL(
					fileEntry, fileVersion, themeDisplay, "&previewFileIndex=")
			);
		}

		return JSONUtil.put("title", fileEntry.getTitle());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetDSEnvelopeMVCResourceCommand.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DSEnvelopeManager _dsEnvelopeManager;

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=" + DLProcessorConstants.IMAGE_PROCESSOR + ")"
	)
	private ImageProcessor _imageProcessor;

}