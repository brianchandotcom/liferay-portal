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

package com.liferay.portal.fabric.netty.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @author Shuyang Zhou
 */
public class NettyUtil {

	public static ChannelPipeline createEmptyChannelPipeline() {
		Channel channel = new EmbeddedChannel(
			new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel channel) {
				}

			});

		ChannelPipeline channelPipeline = channel.pipeline();

		channelPipeline.removeLast();

		return channelPipeline;
	}

}