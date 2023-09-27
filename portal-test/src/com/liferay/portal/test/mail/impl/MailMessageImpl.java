/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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