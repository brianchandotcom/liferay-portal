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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.digital.signature.constants.DigitalSignaturePortletKeys;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.model.DSCustomField;
import com.liferay.digital.signature.model.DSDocument;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.digital.signature.model.DSRecipient;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author José Abelenda
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DigitalSignaturePortletKeys.COLLECT_DIGITAL_SIGNATURE,
		"mvc.command.name=/digital_signature/add_ds_envelope"
	},
	service = MVCResourceCommand.class
)
public class AddDSEnvelopeMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			ParamUtil.getLong(resourceRequest, "fileEntryId"));

		String fileData = Base64.encode(
			FileUtil.getBytes(fileEntry.getContentStream()));

		String recipientsString = ParamUtil.getString(
			resourceRequest, "_recipients");

		JSONArray recipientsJSONArray = JSONFactoryUtil.createJSONArray(
			recipientsString);

		List<DSRecipient> recipients = new ArrayList<>();

		for (int i = 0; i < recipientsJSONArray.length(); i++) {
			JSONObject recipientJSONObject =
				(JSONObject)recipientsJSONArray.get(i);

			DSRecipient dsRecipient = new DSRecipient();

			dsRecipient.setDSRecipientId(String.valueOf(i + 1));
			dsRecipient.setEmailAddress(recipientJSONObject.getString("email"));
			dsRecipient.setName(recipientJSONObject.getString("fullName"));

			recipients.add(dsRecipient);
		}
		
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		DSEnvelope dsEnvelope = new DSEnvelope() {
			{
				dsDocuments = Collections.singletonList(
					new DSDocument() {
						{
							data = fileData;
							dsDocumentId = String.valueOf(
								fileEntry.getFileEntryId());
							name = fileEntry.getFileName();
						}
					});

				dsRecipients = recipients;
				emailBlurb = ParamUtil.getString(
					resourceRequest, "_emailMessage");
				emailSubject = ParamUtil.getString(
					resourceRequest, "_emailSubject");
				name = ParamUtil.getString(
					resourceRequest, "_envelopeName");
				senderEmailAddress = themeDisplay.getUser().getDisplayEmailAddress();
				status = "sent";
			}
		};

		dsEnvelope = _dsEnvelopeManager.addDSEnvelope(
			themeDisplay.getSiteGroupId(), dsEnvelope);

		JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put("dsEnvelopeId", dsEnvelope.getDSEnvelopeId()));
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DSEnvelopeManager _dsEnvelopeManager;

}