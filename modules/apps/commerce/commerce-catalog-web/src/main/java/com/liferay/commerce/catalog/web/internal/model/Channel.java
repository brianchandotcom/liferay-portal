/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.catalog.web.internal.model;

/**
 * @author Gianmarco Brunialti Masera
 */
public class Channel {

	public Channel(long channelId, String name, String type) {
		_channelId = channelId;
		_name = name;
		_type = type;
	}

	public long getChannelId() {
		return _channelId;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	private final long _channelId;
	private final String _name;
	private final String _type;

}