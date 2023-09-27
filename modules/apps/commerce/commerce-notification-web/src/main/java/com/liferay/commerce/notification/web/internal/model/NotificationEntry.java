/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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