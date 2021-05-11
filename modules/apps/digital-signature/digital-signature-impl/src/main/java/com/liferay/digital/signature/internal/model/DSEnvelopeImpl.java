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

package com.liferay.digital.signature.internal.model;

import com.liferay.digital.signature.model.DSEnvelope;

/**
 * @author Brian Wing Shun Chan
 */
public class DSEnvelopeImpl implements DSEnvelope {
	
	@Override
	public String getEnvelopeId() {
		return _envelopeId;
	}
	
	@Override
	public String getEmailSubject() {
		return _emailSubject;
	}
	
	@Override
	public String getStatus() {
		return _status;
	}

	public void setEmailSubject(String emailSubject) {
		_emailSubject = emailSubject;
	}

	public void setEnvelopeId(String envelopeId) {
		_envelopeId = envelopeId;
	}

	@Override
	public void setStatus(String status) {
		_status = status;
		
	}

	private String _emailSubject;
	
	private String _envelopeId;
	
	private String _status;
}