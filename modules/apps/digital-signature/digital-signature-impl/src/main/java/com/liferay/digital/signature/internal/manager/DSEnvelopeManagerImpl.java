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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	public void deleteDSEnvelopes(long groupId, String... dsEnvelopeIds) {
		_dsHttp.put(
			groupId, "folders/recyclebin",
			JSONUtil.put(
				"envelopeIds",
				JSONUtil.toJSONArray(
					dsEnvelopeIds, dsEnvelopeId -> dsEnvelopeId, _log)));
	}

	@Override
	public DSEnvelope getDSEnvelope(long groupId, String dsEnvelopeId) {
		JSONObject jsonObject = _dsHttp.get(
			groupId,
			StringBundler.concat(
				"envelopes/", dsEnvelopeId, "?include=documents,recipients"));

		return _toDSEnvelope(jsonObject);
	}

	@Override
	public List<DSEnvelope> getDSEnvelopes(
		long groupId, String... dsEnvelopeIds) {

		JSONObject jsonObject = _dsHttp.get(
			groupId,
			StringBundler.concat(
				"envelopes/?envelope_ids=",
				ArrayUtil.toString(dsEnvelopeIds, StringPool.BLANK),
				"&include=documents,recipients"));

		return JSONUtil.toList(
			jsonObject.getJSONArray("envelopes"),
			evenlopeJSONObject -> _toDSEnvelope(evenlopeJSONObject), _log);
	}

	@Override
	public List<DSEnvelope> getDSEnvelopes(
		long groupId, String fromDateString) {

		JSONObject jsonObject = _dsHttp.get(
			groupId,
			StringBundler.concat(
				"envelopes?from_date=", fromDateString,
				"&include=recipients,documents&order=desc"));

		return JSONUtil.toList(
			jsonObject.getJSONArray("envelopes"),
			evenlopeJSONObject -> _toDSEnvelope(evenlopeJSONObject), _log);
	}

	private DSEnvelope _toDSEnvelope(JSONObject jsonObject) {
		if (jsonObject == null) {
			return new DSEnvelope();
		}

		return new DSEnvelope() {
			{
				createdLocalDateTime = _toLocalDateTime(
					jsonObject.getString("createdDateTime"));
				dsEnvelopeId = jsonObject.getString("envelopeId");
				emailBlurb = jsonObject.getString("emailBlurb");
				emailSubject = jsonObject.getString("emailSubject");
				status = jsonObject.getString("status");
			}
		};
	}

	private JSONObject _toJSONObject(DSEnvelope dsEnvelope) {
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
				),
				_log)
		).put(
			"emailBlurb", dsEnvelope.getEmailBlurb()
		).put(
			"emailSubject", dsEnvelope.getEmailSubject()
		).put(
			"envelopeId", dsEnvelope.getDSEnvelopeId()
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
					),
					_log))
		).put(
			"status", dsEnvelope.getStatus()
		);
	}

	private LocalDateTime _toLocalDateTime(String localDateTimeString) {
		try {
			return LocalDateTime.parse(
				localDateTimeString,
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX"));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Invalid local date time " + localDateTimeString);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DSEnvelopeManagerImpl.class);

	@Reference
	private DSHttp _dsHttp;

}