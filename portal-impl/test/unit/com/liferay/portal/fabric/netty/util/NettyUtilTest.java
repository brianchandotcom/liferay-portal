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

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoop;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testBindShutdownSuccess() throws InterruptedException {
		MockEventLoopGroup masterEventLoopGroup = new MockEventLoopGroup();
		MockEventLoopGroup salveEventLoopGroup = new MockEventLoopGroup();

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyUtil.class.getName(), Level.WARNING)) {

			NettyUtil.bindShutdown(
				masterEventLoopGroup, salveEventLoopGroup, 0, 10);

			Future<?> masterFuture = masterEventLoopGroup.shutdownGracefully();

			SyncFutureListener syncFutureListener = new SyncFutureListener();

			masterFuture.addListener(syncFutureListener);

			syncFutureListener.sync();

			Future<?> slaveFuture = salveEventLoopGroup.terminationFuture();

			slaveFuture.sync();

			Assert.assertTrue(slaveFuture.isSuccess());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
	}

	@Test
	public void testBindShutdownTimeout() throws InterruptedException {
		MockEventLoopGroup masterEventLoopGroup = new MockEventLoopGroup();
		MockEventLoopGroup salveEventLoopGroup = new MockEventLoopGroup() {

			@Override
			public boolean awaitTermination(long timeout, TimeUnit unit) {
				return false;
			}

		};

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyUtil.class.getName(), Level.WARNING)) {

			NettyUtil.bindShutdown(
				masterEventLoopGroup, salveEventLoopGroup, 0, 10);

			Future<?> masterFuture = masterEventLoopGroup.shutdownGracefully();

			SyncFutureListener syncFutureListener = new SyncFutureListener();

			masterFuture.addListener(syncFutureListener);

			syncFutureListener.sync();

			Future<?> slaveFuture = salveEventLoopGroup.terminationFuture();

			slaveFuture.sync();

			Assert.assertTrue(slaveFuture.isSuccess());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Bind shutdown timeout " + salveEventLoopGroup,
				logEntry.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		new NettyUtil();
	}

	@Test
	public void testCreateEmptyChannelPipeline() {
		ChannelPipeline channelPipeline =
			NettyUtil.createEmptyChannelPipeline();

		Assert.assertEquals(
			Collections.<String, ChannelHandler>emptyMap(),
			channelPipeline.toMap());

		Channel channel = channelPipeline.channel();

		Assert.assertTrue(channel.isActive());
		Assert.assertTrue(channel.isOpen());
		Assert.assertTrue(channel.isRegistered());
	}

	@Test
	public void testScheduleCancellation() throws Exception {

		// Normal finish without log

		MockEventLoopGroup mockEventLoopGroup = new MockEventLoopGroup();

		ReflectionTestUtil.setFieldValue(
			_embeddedChannel, "eventLoop", mockEventLoopGroup.next());

		DefaultNoticeableFuture<Object> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyUtil.class.getName(), Level.OFF)) {

			NettyUtil.scheduleCancellation(
				_embeddedChannel, defaultNoticeableFuture,
				TimeUnit.HOURS.toMillis(1));

			ScheduledFuture<?> scheduledFuture =
				mockEventLoopGroup.getScheduledFuture();

			Assert.assertNotNull(scheduledFuture);
			Assert.assertFalse(scheduledFuture.isDone());

			defaultNoticeableFuture.set(new Object());

			Assert.assertTrue(scheduledFuture.isDone());
			Assert.assertTrue(scheduledFuture.isCancelled());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}

		// Normal finish with log

		defaultNoticeableFuture = new DefaultNoticeableFuture<>();

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyUtil.class.getName(), Level.FINEST)) {

			NettyUtil.scheduleCancellation(
				_embeddedChannel, defaultNoticeableFuture,
				TimeUnit.HOURS.toMillis(1));

			ScheduledFuture<?> scheduledFuture =
				mockEventLoopGroup.getScheduledFuture();

			Assert.assertNotNull(scheduledFuture);
			Assert.assertFalse(scheduledFuture.isDone());

			defaultNoticeableFuture.set(new Object());

			Assert.assertTrue(scheduledFuture.isDone());
			Assert.assertTrue(scheduledFuture.isCancelled());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Cancelled scheduled cancellation for " +
					defaultNoticeableFuture,
				logEntry.getMessage());
		}

		// Timeout cancel without log

		defaultNoticeableFuture = new DefaultNoticeableFuture<>();

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyUtil.class.getName(), Level.OFF)) {

			NettyUtil.scheduleCancellation(
				_embeddedChannel, defaultNoticeableFuture, 0);

			ScheduledFuture<?> scheduledFuture =
				mockEventLoopGroup.getScheduledFuture();

			Assert.assertNotNull(scheduledFuture);

			scheduledFuture.get(1, TimeUnit.HOURS);

			Assert.assertFalse(scheduledFuture.isCancelled());

			Assert.assertTrue(defaultNoticeableFuture.isCancelled());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}

		// Timeout cancel with log

		defaultNoticeableFuture = new DefaultNoticeableFuture<>();

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyUtil.class.getName(), Level.WARNING)) {

			NettyUtil.scheduleCancellation(
				_embeddedChannel, defaultNoticeableFuture, 0);

			ScheduledFuture<?> scheduledFuture =
				mockEventLoopGroup.getScheduledFuture();

			Assert.assertNotNull(scheduledFuture);

			scheduledFuture.get(1, TimeUnit.HOURS);

			Assert.assertFalse(scheduledFuture.isCancelled());

			Assert.assertTrue(defaultNoticeableFuture.isCancelled());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Cancelled timeout " + defaultNoticeableFuture,
				logEntry.getMessage());
		}

		mockEventLoopGroup.shutdownGracefully();
	}

	protected class SyncFutureListener implements FutureListener<Object> {

		@Override
		public void operationComplete(Future<Object> f) throws Exception {
			_countDownLatch.countDown();
		}

		public void sync() throws InterruptedException {
			_countDownLatch.await();
		}

		private final CountDownLatch _countDownLatch = new CountDownLatch(1);

	}

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();

	private static class MockEventLoopGroup extends DefaultEventLoopGroup {

		public MockEventLoopGroup() {
			super(1);
		}

		public ScheduledFuture<?> getScheduledFuture() {
			return _reference.get();
		}

		@Override
		protected EventLoop newChild(Executor executor, Object... args) {
			return new SingleThreadEventLoop(this, executor, true) {

				@Override
				public ScheduledFuture<?> schedule(
					Runnable command, long delay, TimeUnit unit) {

					ScheduledFuture<?> scheduledFuture = super.schedule(
						command, delay, unit);

					_reference.set(scheduledFuture);

					return scheduledFuture;
				}

				@Override
				protected void run() {
					while (!confirmShutdown()) {
						Runnable task = takeTask();

						if (task != null) {
							task.run();

							updateLastExecutionTime();
						}
					}
				}

			};
		}

		private final AtomicReference<ScheduledFuture<?>> _reference =
			new AtomicReference<>();

	}

}