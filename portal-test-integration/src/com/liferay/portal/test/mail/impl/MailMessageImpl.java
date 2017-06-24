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

package com.liferay.portal.test.mail.impl;

import com.liferay.portal.test.mail.MailMessage;

import java.util.Iterator;

/**
 * @author Adam Brandizzi
 */
public class MailMessageImpl implements MailMessage {

	public MailMessageImpl(com.dumbster.smtp.MailMessage mailMessage) {
		_mailMessage = mailMessage;
	}

	@Override
	public String getBody() {
		return _mailMessage.getBody();
	}

	@Override
	public String getFirstHeaderValue(String headerName) {
		return _mailMessage.getFirstHeaderValue(headerName);
	}

	@Override
	public Iterator<String> getHeaderNames() {
		return _mailMessage.getHeaderNames();
	}

	@Override
	public String[] getHeaderValues(String headerName) {
		return _mailMessage.getHeaderValues(headerName);
	}

	public com.dumbster.smtp.MailMessage getMailMessage() {
		return _mailMessage;
	}

	private final com.dumbster.smtp.MailMessage _mailMessage;

}