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

package com.liferay.digital.signature.internal.manager;

import com.liferay.digital.signature.internal.http.DSHttp;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.model.DSDocument;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.digital.signature.model.DSRecipient;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = DSEnvelopeManager.class)
public class DSEnvelopeManagerImpl implements DSEnvelopeManager {

	@Override
	public DSEnvelope addDSEnvelope(long groupId, DSEnvelope dsEnvelope) {
		dsEnvelope = _toDSEnvelope(
			_dsHttp.post(groupId, "envelopes", _toJSONObject(dsEnvelope)));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Added digital signature envelope ID " +
					dsEnvelope.getDSEnvelopeId());
		}

		return dsEnvelope;
	}

	@Override
	public DSEnvelope getDSEnvelope(long groupId, String dsEnvelopeId) {
		DSEnvelope dsEnvelope = _toDSEnvelope(
			_dsHttp.get(groupId, "envelopes/" + dsEnvelopeId));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Retrieved digital signature envelope ID " +
					dsEnvelope.getDSEnvelopeId());
		}

		return dsEnvelope;
	}

	private DSEnvelope _toDSEnvelope(JSONObject jsonObject) {
		if (jsonObject == null) {
			return new DSEnvelope();
		}

		return new DSEnvelope() {
			{
				dsEnvelopeId = jsonObject.getString("envelopeId");
				emailSubject = jsonObject.getString("emailSubject");
				status = jsonObject.getString("status");
			}
		};
	}

	private JSONObject _toJSONObject(DSEnvelope dsEnvelope) {
		JSONObject dsEnvelopeJSONObject = JSONUtil.put(
			"emailBlurb", dsEnvelope.getEmailBlurb()
		).put(
			"emailSubject", dsEnvelope.getEmailSubject()
		).put(
			"envelopeId", dsEnvelope.getDSEnvelopeId()
		).put(
			"status", dsEnvelope.getStatus()
		);

		if (Validator.isNotNull(dsEnvelope.getDocuments())) {
			JSONArray documentsJSONArray = JSONFactoryUtil.createJSONArray();

			for (DSDocument dsDocument : dsEnvelope.getDocuments()) {
				documentsJSONArray.put(
					JSONUtil.put(
						"documentBase64", dsDocument.getDocumentBase64()
					).put(
						"documentId", dsDocument.getDSDocumentId()
					).put(
						"name", dsDocument.getName()
					));
			}

			dsEnvelopeJSONObject.put("documents", documentsJSONArray);
		}

		if (Validator.isNotNull(dsEnvelope.getRecipients())) {
			JSONArray signersJSONArray = JSONFactoryUtil.createJSONArray();

			for (DSRecipient dsRecipient : dsEnvelope.getRecipients()) {
				signersJSONArray.put(
					JSONUtil.put(
						"email", dsRecipient.getEmail()
					).put(
						"name", dsRecipient.getName()
					).put(
						"recipientId", dsRecipient.getDSRecipientId()
					));
			}

			dsEnvelopeJSONObject.put(
				"recipients", JSONUtil.put("signers", signersJSONArray));
		}

		return dsEnvelopeJSONObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DSEnvelopeManagerImpl.class);

	@Reference
	private DSHttp _dsHttp;

}