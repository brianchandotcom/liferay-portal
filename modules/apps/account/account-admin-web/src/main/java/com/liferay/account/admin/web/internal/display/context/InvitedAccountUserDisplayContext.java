/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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