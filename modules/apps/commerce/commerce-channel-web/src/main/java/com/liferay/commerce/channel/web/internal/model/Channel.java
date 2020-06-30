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

package com.liferay.commerce.channel.web.internal.model;

/**
 * @author Marco Leo
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