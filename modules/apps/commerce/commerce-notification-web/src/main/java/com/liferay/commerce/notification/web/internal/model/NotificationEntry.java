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

package com.liferay.commerce.notification.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Alessio Antonio Rendina
 */
public class NotificationEntry {

	public NotificationEntry(
		String from, long notificationEntryId, double priority, LabelField sent,
		String to, String type) {

		_from = from;
		_notificationEntryId = notificationEntryId;
		_sent = sent;
		_to = to;
		_type = type;
	}

	public String getFrom() {
		return _from;
	}

	public long getNotificationEntryId() {
		return _notificationEntryId;
	}

	public LabelField getSent() {
		return _sent;
	}

	public String getTo() {
		return _to;
	}

	public String getType() {
		return _type;
	}

	private final String _from;
	private final long _notificationEntryId;
	private final LabelField _sent;
	private final String _to;
	private final String _type;

}