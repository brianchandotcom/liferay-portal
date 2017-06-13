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
import com.liferay.portal.fabric.netty.rpc.ChannelThreadLocal;
import com.liferay.portal.fabric.netty.util.NettyUtilAdvice;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import io.netty.channel.embedded.EmbeddedChannel;

import java.io.Serializable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class NettyFabricWorkerBridgeRPCCallableTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Before
	public void setUp() {
		ChannelThreadLocal.setChannel(_embeddedChannel);
	}

	@After
	public void tearDown() {
		ChannelThreadLocal.removeChannel();
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testCall() throws Exception {
		FabricWorker<Serializable> fabricWorker = new LocalFabricWorker<>(
			new EmbeddedProcessChannel<Serializable>(
				new DefaultNoticeableFuture<Serializable>()));

		NettyChannelAttributes.putFabricWorker(
			_embeddedChannel, 0, fabricWorker);

		NettyFabricWorkerBridgeRPCCallable<Serializable>
			nettyFabricWorkerBridgeRPCCallable =
				new NettyFabricWorkerBridgeRPCCallable<Serializable>(
					0,
					new ProcessCallable<Serializable>() {

						@Override
						public Serializable call() {
							return null;
						}

					},
					0);

		NoticeableFuture<Serializable> noticeableFuture =
			nettyFabricWorkerBridgeRPCCallable.call();

		Assert.assertNull(noticeableFuture.get());
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testCallTimeoutCancelled() throws ProcessException {
		FabricWorker<Serializable> fabricWorker =
			new LocalFabricWorker<Serializable>(
				new EmbeddedProcessChannel<Serializable>(
					new DefaultNoticeableFuture<Serializable>()) {

					@Override
					public <V extends Serializable> NoticeableFuture<V>
						write(ProcessCallable<V> processCallable) {

						return new DefaultNoticeableFuture<>();
					}

				});

		NettyChannelAttributes.putFabricWorker(
			_embeddedChannel, 0, fabricWorker);

		NettyFabricWorkerBridgeRPCCallable<Serializable>
			nettyFabricWorkerBridgeRPCCallable =
				new NettyFabricWorkerBridgeRPCCallable<Serializable>(
					0,
					new ProcessCallable<Serializable>() {

						@Override
						public Serializable call() {
							return null;
						}

					},
					0);

		NoticeableFuture<Serializable> noticeableFuture =
			nettyFabricWorkerBridgeRPCCallable.call();

		Assert.assertTrue(noticeableFuture.isCancelled());
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testCallUnableToLocateFabricWorker() {
		NettyFabricWorkerBridgeRPCCallable<Serializable>
			nettyFabricWorkerBridgeRPCCallable =
				new NettyFabricWorkerBridgeRPCCallable<Serializable>(
					0,
					new ProcessCallable<Serializable>() {

						@Override
						public Serializable call() {
							return null;
						}

					},
					0);

		try {
			nettyFabricWorkerBridgeRPCCallable.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Assert.assertEquals(
				"Unable to locate fabric worker with ID 0", pe.getMessage());
		}
	}

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();

}