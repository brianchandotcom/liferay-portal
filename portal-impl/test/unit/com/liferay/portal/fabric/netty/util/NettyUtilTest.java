
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

import com.liferay.portal.kernel.test.CodeCoverageAssertor;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() {
		new NettyUtil();
	}

	@Test
	public void testCreateEmptyChannelPipeline() {
		ChannelPipeline channelPipeline =
			NettyUtil.createEmptyChannelPipeline();

		Assert.assertEquals(Collections.emptyMap(), channelPipeline.toMap());

		Channel channel = channelPipeline.channel();

		Assert.assertTrue(channel.isActive());
		Assert.assertTrue(channel.isOpen());
		Assert.assertTrue(channel.isRegistered());
	}

}