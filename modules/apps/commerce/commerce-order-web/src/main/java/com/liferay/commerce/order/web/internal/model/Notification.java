/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.model;

import com.liferay.commerce.frontend.model.AuthorField;
import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Alessio Antonio Rendina
 */
public class Notification {

	public Notification(
		long notificationId, AuthorField author, String date, LabelField status,
		String subject, String summary, String href) {

		_notificationId = notificationId;
		_author = author;
		_date = date;
		_status = status;
		_subject = subject;
		_summary = summary;
		_href = href;
	}

	public AuthorField getAuthor() {
		return _author;
	}

	public String getDate() {
		return _date;
	}

	public String getHref() {
		return _href;
	}

	public long getNotificationId() {
		return _notificationId;
	}

	public LabelField getStatus() {
		return _status;
	}

	public String getSubject() {
		return _subject;
	}

	public String getSummary() {
		return _summary;
	}

	private final AuthorField _author;
	private final String _date;
	private final String _href;
	private final long _notificationId;
	private final LabelField _status;
	private final String _subject;
	private final String _summary;

}