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

package com.liferay.portal.fabric.netty.worker;

import com.liferay.portal.fabric.local.worker.EmbeddedProcessChannel;
import com.liferay.portal.fabric.local.worker.LocalFabricWorker;
import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.fabric.netty.handlers.NettyChannelAttributes;
import com.liferay.portal.fabric.netty.rpc.handlers.NettyRPCChannelHandler;
import com.liferay.portal.fabric.netty.util.NettyUtilAdvice;
import com.liferay.portal.fabric.status.JMXProxyUtil.ProcessCallableExecutor;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.embedded.EmbeddedChannel;

import java.io.Serializable;

import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricWorkerProcessCallableExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testExecute() throws Exception {
		EmbeddedChannel embeddedChannel =
			NettyTestUtil.createEmptyEmbeddedChannel();

		ChannelPipeline channelPipeline = embeddedChannel.pipeline();

		channelPipeline.addFirst(
			NettyRPCChannelHandler.NAME, NettyRPCChannelHandler.INSTANCE);

		NettyChannelAttributes.putFabricWorker(
			embeddedChannel, 0,
			new LocalFabricWorker<Serializable>(
				new EmbeddedProcessChannel<Serializable>(
					new DefaultNoticeableFuture<Serializable>())));

		ProcessCallableExecutor processCallableExecutor =
			new NettyFabricWorkerProcessCallableExecutor(
				embeddedChannel, 0, Long.MAX_VALUE);

		NoticeableFuture<Serializable> noticeableFuture =
			processCallableExecutor.execute(
				new ProcessCallable<Serializable>() {

					@Override
					public Serializable call() {
						return StringPool.BLANK;
					}

				});

		embeddedChannel.writeInbound(embeddedChannel.readOutbound());
		embeddedChannel.writeInbound(embeddedChannel.readOutbound());

		Assert.assertEquals(StringPool.BLANK, noticeableFuture.get());

		final ProcessException processException = new ProcessException("");

		noticeableFuture = processCallableExecutor.execute(
			new ProcessCallable<Serializable>() {

				@Override
				public Serializable call() throws ProcessException {
					throw processException;
				}

			});

		embeddedChannel.writeInbound(embeddedChannel.readOutbound());
		embeddedChannel.writeInbound(embeddedChannel.readOutbound());

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(processException, ee.getCause());
		}
	}

}