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

package com.liferay.digital.signature.rest.internal.dto.v1_0.util;

import com.liferay.digital.signature.model.DSDocument;
import com.liferay.digital.signature.model.DSRecipient;
import com.liferay.digital.signature.rest.dto.v1_0.DSEnvelope;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author José Abelenda
 */
public class DSEnvelopeUtil {

	public static DSEnvelope toDSEnvelope(
		com.liferay.digital.signature.model.DSEnvelope dsEnvelope) {

		return new DSEnvelope() {
			{
				dsRecipient = _getDSRecipients(dsEnvelope.getDSRecipients());
				emailBlurb = dsEnvelope.getEmailBlurb();
				emailSubject = dsEnvelope.getEmailSubject();
				dsEnvelopeId = dsEnvelope.getDSEnvelopeId();
				name = dsEnvelope.getName();
				senderEmailAddress = dsEnvelope.getSenderEmailAddress();
				status = dsEnvelope.getStatus();
			}
		};
	}

	public static com.liferay.digital.signature.model.DSEnvelope toDSEnvelope(
		DSEnvelope dsEnvelope) {

		return new com.liferay.digital.signature.model.DSEnvelope() {
			{
				dsDocuments = _getDSDocuments(dsEnvelope.getDsDocument());
				dsEnvelopeId = dsEnvelope.getDsEnvelopeId();
				dsRecipients = _getDSRecipients(dsEnvelope.getDsRecipient());
				emailBlurb = dsEnvelope.getEmailBlurb();
				emailSubject = dsEnvelope.getEmailSubject();
				name = dsEnvelope.getName();
				senderEmailAddress = dsEnvelope.getSenderEmailAddress();
				status = dsEnvelope.getStatus();
			}
		};
	}

	private static List<DSDocument> _getDSDocuments(
		com.liferay.digital.signature.rest.dto.v1_0.DSDocument[] dsDocuments) {

		return ListUtil.toList(
			ListUtil.fromArray(dsDocuments),
			dsDocument -> _toDSDocument(dsDocument));
	}

	private static com.liferay.digital.signature.rest.dto.v1_0.DSDocument[]
		_getDSDocuments(List<DSDocument> dsDocuments) {

		com.liferay.digital.signature.rest.dto.v1_0.DSDocument[]
			dsDocumentArray =
				new com.liferay.digital.signature.rest.dto.v1_0.DSDocument
					[dsDocuments.size()];

		List<com.liferay.digital.signature.rest.dto.v1_0.DSDocument>
			dsDocumentList = ListUtil.toList(
				dsDocuments, dsDocument -> _toDSDocument(dsDocument));

		return dsDocumentList.toArray(dsDocumentArray);
	}

	private static List<DSRecipient> _getDSRecipients(
		com.liferay.digital.signature.rest.dto.v1_0.DSRecipient[]
			dsRecipients) {

		return ListUtil.toList(
			ListUtil.fromArray(dsRecipients),
			dsRecipient -> _toDSRecipient(dsRecipient));
	}

	private static com.liferay.digital.signature.rest.dto.v1_0.DSRecipient[]
		_getDSRecipients(List<DSRecipient> dsRecipients) {

		com.liferay.digital.signature.rest.dto.v1_0.DSRecipient[]
			dsRecipientArray =
				new com.liferay.digital.signature.rest.dto.v1_0.DSRecipient
					[dsRecipients.size()];

		List<com.liferay.digital.signature.rest.dto.v1_0.DSRecipient>
			dsRecipientList = ListUtil.toList(
				dsRecipients, dsRecipient -> _toDSRecipient(dsRecipient));

		return dsRecipientList.toArray(dsRecipientArray);
	}

	private static DSDocument _toDSDocument(
		com.liferay.digital.signature.rest.dto.v1_0.DSDocument dsDocument) {

		return new DSDocument() {
			{
				data = dsDocument.getData();
				dsDocumentId = dsDocument.getDsDocumentId();
				fileExtension = dsDocument.getFileExtension();
				name = dsDocument.getName();
				uri = dsDocument.getUri();
			}
		};
	}

	private static com.liferay.digital.signature.rest.dto.v1_0.DSDocument
		_toDSDocument(DSDocument dsDocument) {

		return new com.liferay.digital.signature.rest.dto.v1_0.DSDocument() {
			{
				data = dsDocument.getData();
				dsDocumentId = dsDocument.getDSDocumentId();
				fileExtension = dsDocument.getFileExtension();
				name = dsDocument.getName();
				uri = dsDocument.getURI();
			}
		};
	}

	private static DSRecipient _toDSRecipient(
		com.liferay.digital.signature.rest.dto.v1_0.DSRecipient dsRecipient) {

		return new DSRecipient() {
			{
				dsRecipientId = dsRecipient.getDsRecipientId();
				emailAddress = dsRecipient.getEmailAddress();
				name = dsRecipient.getName();
				status = dsRecipient.getStatus();
			}
		};
	}

	private static com.liferay.digital.signature.rest.dto.v1_0.DSRecipient
		_toDSRecipient(DSRecipient dsRecipient) {

		return new com.liferay.digital.signature.rest.dto.v1_0.DSRecipient() {
			{
				dsRecipientId = dsRecipient.getDSRecipientId();
				emailAddress = dsRecipient.getEmailAddress();
				name = dsRecipient.getName();
				status = dsRecipient.getStatus();
			}
		};
	}

}