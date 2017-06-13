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
import com.liferay.portal.fabric.repository.MockRepository;
import com.liferay.portal.fabric.status.FabricStatus;
import com.liferay.portal.fabric.status.RemoteFabricStatus;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.local.ReturnProcessCallable;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.embedded.EmbeddedChannel;

import java.nio.file.Path;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricWorkerStubTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Test
	public void testConstructor() {
		try {
			new NettyFabricWorkerStub<String>(0, null, null, null, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Channel is null", npe.getMessage());
		}

		try {
			new NettyFabricWorkerStub<String>(
				0, NettyTestUtil.createEmptyEmbeddedChannel(), null, null, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Repository is null", npe.getMessage());
		}

		try {
			new NettyFabricWorkerStub<String>(
				0, NettyTestUtil.createEmptyEmbeddedChannel(),
				new MockRepository<Channel>(), null, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Output path map is null", npe.getMessage());
		}

		Channel channel = NettyTestUtil.createEmptyEmbeddedChannel();

		ChannelFuture channelFuture = channel.closeFuture();

		Assert.assertFalse(channelFuture.isDone());

		NettyFabricWorkerStub<String> nettyFabricWorkerStub =
			new NettyFabricWorkerStub<>(
				0, channel, new MockRepository<Channel>(),
				Collections.<Path, Path>emptyMap(), 0);

		Assert.assertNotNull(
			ReflectionTestUtil.getFieldValue(channelFuture, "listeners"));

		NoticeableFuture<String> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		Assert.assertFalse(noticeableFuture.isDone());

		Assert.assertTrue(channelFuture.cancel(true));
		Assert.assertTrue(noticeableFuture.isCancelled());
		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(channelFuture, "listeners"));

		channel = NettyTestUtil.createEmptyEmbeddedChannel();

		channelFuture = channel.closeFuture();

		Assert.assertFalse(channelFuture.isDone());

		nettyFabricWorkerStub = new NettyFabricWorkerStub<>(
			0, channel, new MockRepository<Channel>(),
			Collections.<Path, Path>emptyMap(), 0);

		Assert.assertNotNull(
			ReflectionTestUtil.getFieldValue(channelFuture, "listeners"));

		noticeableFuture = nettyFabricWorkerStub.getProcessNoticeableFuture();

		Assert.assertFalse(noticeableFuture.isDone());
		Assert.assertTrue(noticeableFuture.cancel(true));

		Assert.assertFalse(channelFuture.isDone());
		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(channelFuture, "listeners"));
	}

	@Test
	public void testGetFabricStatus() {
		NettyFabricWorkerStub<String> nettyFabricWorkerStub =
			new NettyFabricWorkerStub<>(
				0, NettyTestUtil.createEmptyEmbeddedChannel(),
				new MockRepository<Channel>(),
				Collections.<Path, Path>emptyMap(), 0);

		FabricStatus fabricStatus = nettyFabricWorkerStub.getFabricStatus();

		Assert.assertSame(RemoteFabricStatus.class, fabricStatus.getClass());
	}

	@Test
	public void testSetCancellation() throws Exception {
		NettyFabricWorkerStub<String> nettyFabricWorkerStub =
			new NettyFabricWorkerStub<>(
				0, NettyTestUtil.createEmptyEmbeddedChannel(),
				new MockRepository<Channel>(),
				Collections.<Path, Path>emptyMap(), 0);

		nettyFabricWorkerStub.setCancel();

		NoticeableFuture<String> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		Assert.assertTrue(noticeableFuture.isCancelled());
	}

	@Test
	public void testSetException() throws InterruptedException {
		NettyFabricWorkerStub<String> nettyFabricWorkerStub =
			new NettyFabricWorkerStub<>(
				0, NettyTestUtil.createEmptyEmbeddedChannel(),
				new MockRepository<Channel>(),
				Collections.<Path, Path>emptyMap(), 0);

		Throwable throwable = new Throwable();

		nettyFabricWorkerStub.setException(throwable);

		NoticeableFuture<String> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(throwable, ee.getCause());
		}
	}

	@Test
	public void testSetResult() throws Exception {
		final DefaultNoticeableFuture<Map<Path, Path>> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		NettyFabricWorkerStub<String> nettyFabricWorkerStub =
			new NettyFabricWorkerStub<String>(
				0, NettyTestUtil.createEmptyEmbeddedChannel(),
				new MockRepository<Channel>() {

					@Override
					public NoticeableFuture<Map<Path, Path>> getFiles(
						Channel channel, Map<Path, Path> pathMap,
						boolean deleteAfterFetch) {

						return defaultNoticeableFuture;
					}

				},
				Collections.<Path, Path>emptyMap(), 0);

		String result = "Test result";

		nettyFabricWorkerStub.setResult(result);

		NoticeableFuture<String> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		Assert.assertFalse(noticeableFuture.isDone());

		defaultNoticeableFuture.set(Collections.<Path, Path>emptyMap());

		Assert.assertEquals(result, noticeableFuture.get());
	}

	@Test
	public void testSetResultWithCancellation() {
		final DefaultNoticeableFuture<Map<Path, Path>> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		NettyFabricWorkerStub<String> nettyFabricWorkerStub =
			new NettyFabricWorkerStub<String>(
				0, NettyTestUtil.createEmptyEmbeddedChannel(),
				new MockRepository<Channel>() {

					@Override
					public NoticeableFuture<Map<Path, Path>> getFiles(
						Channel channel, Map<Path, Path> pathMap,
						boolean deleteAfterFetch) {

						return defaultNoticeableFuture;
					}

				},
				Collections.<Path, Path>emptyMap(), 0);

		nettyFabricWorkerStub.setResult("Test result");

		NoticeableFuture<String> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		Assert.assertFalse(noticeableFuture.isDone());

		Assert.assertTrue(defaultNoticeableFuture.cancel(true));
		Assert.assertTrue(noticeableFuture.isCancelled());
	}

	@Test
	public void testSetResultWithException() throws InterruptedException {
		final DefaultNoticeableFuture<Map<Path, Path>> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		NettyFabricWorkerStub<String> nettyFabricWorkerStub =
			new NettyFabricWorkerStub<String>(
				0, NettyTestUtil.createEmptyEmbeddedChannel(),
				new MockRepository<Channel>() {

					@Override
					public NoticeableFuture<Map<Path, Path>> getFiles(
						Channel channel, Map<Path, Path> pathMap,
						boolean deleteAfterFetch) {

						return defaultNoticeableFuture;
					}

				},
				Collections.<Path, Path>emptyMap(), 0);

		nettyFabricWorkerStub.setResult("Test result");

		NoticeableFuture<String> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		Assert.assertFalse(noticeableFuture.isDone());

		Throwable throwable = new Throwable();

		defaultNoticeableFuture.setException(throwable);

		try {
			defaultNoticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(throwable, ee.getCause());
		}
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testWrite() throws Exception {
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(
			NettyRPCChannelHandler.INSTANCE);

		NettyFabricWorkerStub<String> nettyFabricWorkerStub =
			new NettyFabricWorkerStub<>(
				0, embeddedChannel, new MockRepository<Channel>(),
				Collections.<Path, Path>emptyMap(), 0);

		NettyChannelAttributes.putFabricWorker(
			embeddedChannel, 0,
			new LocalFabricWorker<String>(
				new EmbeddedProcessChannel<String>(
					new DefaultNoticeableFuture<String>())));

		String result = "Test result";

		NoticeableFuture<String> noticeableFuture = nettyFabricWorkerStub.write(
			new ReturnProcessCallable<String>(result));

		embeddedChannel.writeInbound(embeddedChannel.readOutbound());
		embeddedChannel.writeInbound(embeddedChannel.readOutbound());

		Assert.assertEquals(result, noticeableFuture.get());
	}

}