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

package com.liferay.digital.signature.model;

import java.time.LocalDateTime;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author José Abelenda
 */
public class DSEnvelope {

	public LocalDateTime getCreatedLocalDateTime() {
		return createdLocalDateTime;
	}

	public List<DSDocument> getDSDocuments() {
		return dsDocuments;
	}

	public String getDSEnvelopeId() {
		return dsEnvelopeId;
	}

	public List<DSRecipient> getDSRecipients() {
		return dsRecipients;
	}

	public String getEmailBlurb() {
		return emailBlurb;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public String getName() {
		return name;
	}

	public String getSenderEmailAddress() {
		return senderEmailAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setCreatedLocalDateTime(LocalDateTime createdLocalDateTime) {
		this.createdLocalDateTime = createdLocalDateTime;
	}

	public void setDSDocuments(List<DSDocument> dsDocuments) {
		this.dsDocuments = dsDocuments;
	}

	public void setDSEnvelopeId(String dsEnvelopeId) {
		this.dsEnvelopeId = dsEnvelopeId;
	}

	public void setDSRecipients(List<DSRecipient> dsRecipients) {
		this.dsRecipients = dsRecipients;
	}

	public void setEmailBlurb(String emailBlurb) {
		this.emailBlurb = emailBlurb;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSenderEmailAddress(String senderEmailAddress) {
		this.senderEmailAddress = senderEmailAddress;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	protected LocalDateTime createdLocalDateTime;
	protected List<DSDocument> dsDocuments;
	protected String dsEnvelopeId;
	protected List<DSRecipient> dsRecipients;
	protected String emailBlurb;
	protected String emailSubject;
	protected String name;
	protected String senderEmailAddress;
	protected String status;

}