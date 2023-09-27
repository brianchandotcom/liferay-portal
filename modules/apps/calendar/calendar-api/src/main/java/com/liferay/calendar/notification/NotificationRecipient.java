/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.notification;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ContentTypes;

/**
 * @author Eduardo Lundgren
 */
public class NotificationRecipient {

	public NotificationRecipient(User user) {
		_user = user;

		_emailAddress = user.getEmailAddress();

		_name = user.getFullName();
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getFormat() {
		return _format;
	}

	public String getName() {
		return _name;
	}

	public User getUser() {
		return _user;
	}

	public boolean isHTMLFormat() {
		return _format.equals(ContentTypes.TEXT_HTML);
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setFormat(String format) {
		_format = format;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setUser(User user) {
		_user = user;
	}

	private String _emailAddress;
	private String _format = ContentTypes.TEXT_HTML;
	private String _name;
	private User _user;

}