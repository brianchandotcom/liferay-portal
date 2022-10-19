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

package com.liferay.account.admin.web.internal.display.context;

/**
 * @author Pei-Jung Lan
 */
public class InvitedAccountUserDisplayContext {

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getTicketKey() {
		return _ticketKey;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setTicketKey(String ticketKey) {
		_ticketKey = ticketKey;
	}

	private String _emailAddress;
	private String _ticketKey;

}