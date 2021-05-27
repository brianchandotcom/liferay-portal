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
import com.liferay.digital.signature.manager.DSCustomFieldManager;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.model.DSCustomField;
import com.liferay.digital.signature.model.DSDocument;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.digital.signature.model.DSRecipient;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = DSEnvelopeManager.class)
public class DSEnvelopeManagerImpl implements DSEnvelopeManager {

	@Override
	public DSEnvelope addDSEnvelope(
		long companyId, long groupId, DSEnvelope dsEnvelope) {

		String dsEnvelopeName = dsEnvelope.getName();
		String dsEnvelopeSenderEmailAddress =
			dsEnvelope.getSenderEmailAddress();

		dsEnvelope = _toDSEnvelope(
			_dsHttp.post(
				companyId, groupId, "envelopes", _toJSONObject(dsEnvelope)));

		_dsCustomFieldManager.addDSCustomFields(
			companyId, groupId, dsEnvelope.getDSEnvelopeId(),
			new DSCustomField() {
				{
					name = "envelopeName";
					show = true;
					value = dsEnvelopeName;
				}
			},
			new DSCustomField() {
				{
					name = "envelopeSenderEmailAddress";
					show = true;
					value = dsEnvelopeSenderEmailAddress;
				}
			});

		return dsEnvelope;
	}

	@Override
	public void deleteDSEnvelopes(
		long companyId, long groupId, String... dsEnvelopeIds) {

		_dsHttp.put(
			companyId, groupId, "folders/recyclebin",
			JSONUtil.put(
				"envelopeIds",
				JSONUtil.toJSONArray(
					dsEnvelopeIds, dsEnvelopeId -> dsEnvelopeId, _log)));
	}

	@Override
	public DSEnvelope getDSEnvelope(
		long companyId, long groupId, String dsEnvelopeId) {

		JSONObject jsonObject = _dsHttp.get(
			companyId, groupId,
			StringBundler.concat(
				"envelopes/", dsEnvelopeId,
				"?include=custom_fields,documents,recipients"));

		return _toDSEnvelope(jsonObject);
	}

	@Override
	public Page<DSEnvelope> getDSEnvelopesPage(
		long companyId, long groupId, String fromDateString, String order,
		int page, int pageSize) {

		String startPosition = String.valueOf(pageSize * (page - 1));

		JSONObject jsonObject = _dsHttp.get(
			companyId, groupId,
			StringBundler.concat(
				"envelopes?from_date=", fromDateString, "&count=",
				String.valueOf(pageSize), "&start_position=", startPosition,
				"&folder_types=sentitems",
				"&include=custom_fields,documents,recipients&order=", order));

		return Page.of(
			JSONUtil.toList(
				jsonObject.getJSONArray("envelopes"),
				envelopeJSONObject -> _toDSEnvelope(envelopeJSONObject), _log),
			Pagination.of(page, jsonObject.getInt("resultSetSize")),
			jsonObject.getInt("totalSetSize"));
	}

	private List<DSDocument> _getDSDocuments(JSONArray jsonArray) {
		return JSONUtil.toList(
			jsonArray,
			jsonObject -> new DSDocument() {
				{
					dsDocumentId = jsonObject.getString("documentId");
					name = jsonObject.getString("name");
					uri = jsonObject.getString("uri");
				}
			},
			_log);
	}

	private List<DSRecipient> _getDSRecipients(JSONObject jsonObject) {
		if (jsonObject == null) {
			return Collections.emptyList();
		}

		return JSONUtil.toList(
			jsonObject.getJSONArray("signers"),
			signerJSONObject -> new DSRecipient() {
				{
					dsRecipientId = signerJSONObject.getString("recipientId");
					emailAddress = signerJSONObject.getString("email");
					name = signerJSONObject.getString("name");
					status = signerJSONObject.getString("status");
				}
			},
			_log);
	}

	private void _setDSEnvelopeCustomField(
		DSEnvelope dsEnvelope, JSONObject jsonObject) {

		String name = jsonObject.getString("name");
		String value = jsonObject.getString("value");

		if (Objects.equals(name, "envelopeName")) {
			dsEnvelope.setName(value);
		}
		else if (Objects.equals(name, "envelopeSenderEmailAddress")) {
			dsEnvelope.setSenderEmailAddress(value);
		}
	}

	private void _setDSEnvelopeCustomFields(
		DSEnvelope dsEnvelope, JSONObject jsonObject) {

		if (jsonObject == null) {
			return;
		}

		JSONArray jsonArray = jsonObject.getJSONArray("textCustomFields");

		jsonArray.forEach(
			element -> _setDSEnvelopeCustomField(
				dsEnvelope, (JSONObject)element));
	}

	private DSEnvelope _toDSEnvelope(JSONObject jsonObject) {
		if (jsonObject == null) {
			return new DSEnvelope();
		}

		DSEnvelope dsEnvelope = new DSEnvelope() {
			{
				createdLocalDateTime = _toLocalDateTime(
					jsonObject.getString("createdDateTime"));
				dsDocuments = _getDSDocuments(
					jsonObject.getJSONArray("envelopeDocuments"));
				dsEnvelopeId = jsonObject.getString("envelopeId");
				dsRecipients = _getDSRecipients(
					jsonObject.getJSONObject("recipients"));
				emailBlurb = jsonObject.getString("emailBlurb");
				emailSubject = jsonObject.getString("emailSubject");
				status = jsonObject.getString("status");
			}
		};

		_setDSEnvelopeCustomFields(
			dsEnvelope, jsonObject.getJSONObject("customFields"));

		return dsEnvelope;
	}

	private JSONObject _toJSONObject(DSEnvelope dsEnvelope) {
		return JSONUtil.put(
			"createdLocalDateTime", dsEnvelope.getCreatedLocalDateTime()
		).put(
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
			"name", dsEnvelope.getName()
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
					).put(
						"status", dsRecipient.getStatus()
					),
					_log))
		).put(
			"senderEmailAddress", dsEnvelope.getSenderEmailAddress()
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
	private DSCustomFieldManager _dsCustomFieldManager;

	@Reference
	private DSHttp _dsHttp;

}