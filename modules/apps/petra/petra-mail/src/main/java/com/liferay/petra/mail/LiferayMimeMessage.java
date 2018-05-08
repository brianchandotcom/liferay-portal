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

package com.liferay.petra.mail;

import com.liferay.portal.kernel.util.ArrayUtil;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * @author Jorge Ferrer
 * @see    com.liferay.util.mail.LiferayMimeMessage
 */
public class LiferayMimeMessage extends MimeMessage {

	public LiferayMimeMessage(Session session) {
		super(session);
	}

	@Override
	protected void updateMessageID() throws MessagingException {
		String[] messageIds = getHeader("Message-ID");

		if (ArrayUtil.isNotEmpty(messageIds)) {

			// Keep current value

			return;
		}

		super.updateMessageID();
	}

}