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

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author José Abelenda
 */
public class DSEnvelope {

	public List<DSDocument> getDocuments() {
		return documents;
	}

	public String getDSEnvelopeId() {
		return dsEnvelopeId;
	}

	public String getEmailBlurb() {
		return emailBlurb;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public List<DSRecipient> getRecipients() {
		return recipients;
	}

	public String getStatus() {
		return status;
	}

	public void setDocuments(List<DSDocument> documents) {
		this.documents = documents;
	}

	public void setDSEnvelopeId(String dsEnvelopeId) {
		this.dsEnvelopeId = dsEnvelopeId;
	}

	public void setEmailBlurb(String emailBlurb) {
		this.emailBlurb = emailBlurb;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public void setRecipients(List<DSRecipient> recipients) {
		this.recipients = recipients;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	protected List<DSDocument> documents;
	protected String dsEnvelopeId;
	protected String emailBlurb;
	protected String emailSubject;
	protected List<DSRecipient> recipients;
	protected String status;

}