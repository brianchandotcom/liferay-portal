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