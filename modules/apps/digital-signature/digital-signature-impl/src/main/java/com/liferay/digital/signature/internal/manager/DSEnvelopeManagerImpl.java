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
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.digital.signature.model.DSRecipient;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = DSEnvelopeManager.class)
public class DSEnvelopeManagerImpl implements DSEnvelopeManager {

	@Override
	public DSEnvelope addDSEnvelope(long groupId, DSEnvelope dsEnvelope) {
		try {
			dsEnvelope = _toDSEnvelope(
				_dsHttp.post(groupId, "envelopes", _toJSONObject(dsEnvelope)));

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Added digital signature envelope ID " +
						dsEnvelope.getDSEnvelopeId());
			}

			return dsEnvelope;
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
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

	@Override
	public List<DSEnvelope> getDSEnvelopes(
		long groupId, List<String> dsEnvelopeIds) {

		// LPS-132126

		// envelopes?envelopeIds=

		return Collections.emptyList();
	}
	
	public List<DSEnvelope> getDSEnvelopeList(long groupId, String fromDate) {
		JSONObject jsonObject = _dsHttp.get(
			groupId,
			StringBundler.concat(
				"envelopes?from_date=", fromDate,
				"&include=recipients,documents&order=desc"));

		List<DSEnvelope> dsEnvelopes = new ArrayList<>();

		JSONArray envelopesJSONArray = jsonObject.getJSONArray("envelopes");

		envelopesJSONArray.forEach(
			dsEnvelopeJSONObject -> dsEnvelopes.add(
				_toDSEnvelope((JSONObject)dsEnvelopeJSONObject)));

		return dsEnvelopes;
	}

	private DSEnvelope _toDSEnvelope(JSONObject jsonObject) {
		if (jsonObject == null) {
			return new DSEnvelope();
		}

		return new DSEnvelope() {
			{
				dsEnvelopeId = jsonObject.getString("dsEnvelopeId");
				emailSubject = jsonObject.getString("emailSubject");
				status = jsonObject.getString("status");
			}
		};
	}

	private JSONObject _toJSONObject(DSEnvelope dsEnvelope) throws Exception {
		return JSONUtil.put(
			"documents",
			JSONUtil.toJSONArray(
				dsEnvelope.getDSDocuments(),
				dsDocument -> JSONUtil.put(
					"documentBase64", dsDocument.getData()
				).put(
					"documentId", dsDocument.getDSDocumentId()
				).put(
					"name", dsDocument.getName()
				))
		).put(
			"dsEnvelopeId", dsEnvelope.getDSEnvelopeId()
		).put(
			"emailBlurb", dsEnvelope.getEmailBlurb()
		).put(
			"emailSubject", dsEnvelope.getEmailSubject()
		).put(
			"recipients",
			JSONUtil.put(
				"signers",
				JSONUtil.toJSONArray(
					dsEnvelope.getDSRecipients(),
					dsRecipient -> JSONUtil.put(
						"email", dsRecipient.getEmailAddress()
					).put(
						"name", dsRecipient.getName()
					).put(
						"recipientId", dsRecipient.getDSRecipientId()
					)))
		).put(
			"status", dsEnvelope.getStatus()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DSEnvelopeManagerImpl.class);

	@Reference
	private DSHttp _dsHttp;

}