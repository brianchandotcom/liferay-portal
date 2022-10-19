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

import com.liferay.digital.signature.rest.dto.v1_0.DSDocument;
import com.liferay.digital.signature.rest.dto.v1_0.DSEnvelope;
import com.liferay.digital.signature.rest.dto.v1_0.DSRecipient;
import com.liferay.petra.function.transform.TransformUtil;

/**
 * @author José Abelenda
 */
public class DSEnvelopeUtil {

	public static DSEnvelope toDSEnvelope(
		com.liferay.digital.signature.model.DSEnvelope dsEnvelope) {

		return new DSEnvelope() {
			{
				dsDocument = TransformUtil.transformToArray(
					dsEnvelope.getDSDocuments(),
					dsDocument -> _toDSDocument(dsDocument), DSDocument.class);
				dsRecipient = TransformUtil.transformToArray(
					dsEnvelope.getDSRecipients(),
					dsRecipient -> _toDSRecipient(dsRecipient),
					DSRecipient.class);
				emailBlurb = dsEnvelope.getEmailBlurb();
				emailSubject = dsEnvelope.getEmailSubject();
				id = dsEnvelope.getDSEnvelopeId();
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
				dsDocuments = TransformUtil.transformToList(
					dsEnvelope.getDsDocument(),
					dsDocument -> _toDSDocument(dsDocument));
				dsEnvelopeId = dsEnvelope.getId();
				dsRecipients = TransformUtil.transformToList(
					dsEnvelope.getDsRecipient(),
					dsRecipient -> _toDSRecipient(dsRecipient));
				emailBlurb = dsEnvelope.getEmailBlurb();
				emailSubject = dsEnvelope.getEmailSubject();
				name = dsEnvelope.getName();
				senderEmailAddress = dsEnvelope.getSenderEmailAddress();
				status = dsEnvelope.getStatus();
			}
		};
	}

	private static DSDocument _toDSDocument(
		com.liferay.digital.signature.model.DSDocument dsDocument) {

		return new DSDocument() {
			{
				data = dsDocument.getData();
				fileExtension = dsDocument.getFileExtension();
				id = dsDocument.getDSDocumentId();
				name = dsDocument.getName();
				uri = dsDocument.getURI();
			}
		};
	}

	private static com.liferay.digital.signature.model.DSDocument _toDSDocument(
		DSDocument dsDocument) {

		return new com.liferay.digital.signature.model.DSDocument() {
			{
				data = dsDocument.getData();
				dsDocumentId = dsDocument.getId();
				fileExtension = dsDocument.getFileExtension();
				name = dsDocument.getName();
				uri = dsDocument.getUri();
			}
		};
	}

	private static DSRecipient _toDSRecipient(
		com.liferay.digital.signature.model.DSRecipient dsRecipient) {

		return new DSRecipient() {
			{
				emailAddress = dsRecipient.getEmailAddress();
				id = dsRecipient.getDSRecipientId();
				name = dsRecipient.getName();
				status = dsRecipient.getStatus();
			}
		};
	}

	private static com.liferay.digital.signature.model.DSRecipient
		_toDSRecipient(DSRecipient dsRecipient) {

		return new com.liferay.digital.signature.model.DSRecipient() {
			{
				dsRecipientId = dsRecipient.getId();
				emailAddress = dsRecipient.getEmailAddress();
				name = dsRecipient.getName();
				status = dsRecipient.getStatus();
			}
		};
	}

}