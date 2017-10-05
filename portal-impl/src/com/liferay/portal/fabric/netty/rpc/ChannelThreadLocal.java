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

package com.liferay.portal.fabric.netty.rpc;

import com.liferay.petra.lang.CentralizedThreadLocal;

import io.netty.channel.Channel;

/**
 * @author Shuyang Zhou
 */
public class ChannelThreadLocal {

	public static Channel getChannel() {
		Channel channel = _channelThreadLocal.get();

		if (channel == null) {
			throw new IllegalStateException("Channel is null");
		}

		return channel;
	}

	public static void removeChannel() {
		_channelThreadLocal.remove();
	}

	public static void setChannel(Channel channel) {
		_channelThreadLocal.set(channel);
	}

	private static final ThreadLocal<Channel> _channelThreadLocal =
		new CentralizedThreadLocal<>(false);

}