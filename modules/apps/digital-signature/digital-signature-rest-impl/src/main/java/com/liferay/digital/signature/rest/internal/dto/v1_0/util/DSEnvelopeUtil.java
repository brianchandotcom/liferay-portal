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
				dsEnvelopeId = dsEnvelope.getId();
				dsRecipients = _getDSRecipients(dsEnvelope.getDsRecipient());
				emailBlurb = dsEnvelope.getEmailBlurb();
				emailSubject = dsEnvelope.getEmailSubject();
				name = dsEnvelope.getName();
				senderEmailAddress = dsEnvelope.getSenderEmailAddress();
				status = dsEnvelope.getStatus();
			}
		};
	}

	private static List<DSRecipient> _getDSRecipients(
		com.liferay.digital.signature.rest.dto.v1_0.DSRecipient[]
			dsRecipients) {

		return ListUtil.toList(
			ListUtil.fromArray(dsRecipients),
			dsRecipient -> _toDSRecipient(dsRecipient));
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

	private com.liferay.digital.signature.rest.dto.v1_0.DSRecipient
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