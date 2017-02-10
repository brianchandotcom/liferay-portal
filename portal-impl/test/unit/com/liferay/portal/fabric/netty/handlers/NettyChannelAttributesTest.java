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

package com.liferay.portal.fabric.netty.handlers;

import com.liferay.portal.fabric.local.worker.EmbeddedProcessChannel;
import com.liferay.portal.fabric.local.worker.LocalFabricWorker;
import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.fabric.netty.agent.NettyFabricAgentStub;
import com.liferay.portal.fabric.repository.MockRepository;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.concurrent.AsyncBroker;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.Attribute;

import java.io.Serializable;

import java.nio.file.Paths;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyChannelAttributesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, AspectJNewEnvTestRule.INSTANCE);

	@AdviseWith(adviceClasses = AttributeAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testConcurrentGetAsyncBroker() {
		AsyncBroker<Long, Serializable> asyncBroker = new AsyncBroker<>();

		AttributeAdvice.setConcurrentValue(asyncBroker);

		Assert.assertSame(
			asyncBroker,
			NettyChannelAttributes.getAsyncBroker(_embeddedChannel));

		// Get from cache

		Assert.assertSame(
			asyncBroker,
			NettyChannelAttributes.getAsyncBroker(_embeddedChannel));
	}

	@AdviseWith(adviceClasses = AttributeAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testConcurrentNextId() {
		AttributeAdvice.setConcurrentValue(new AtomicLong());

		testNextId();
	}

	@AdviseWith(adviceClasses = AttributeAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testConcurrentPutFabricWorker() {
		AttributeAdvice.setConcurrentValue(
			new ConcurrentHashMap<Long, FabricWorker<?>>());

		DefaultNoticeableFuture<Serializable> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		FabricWorker<Serializable> fabricWorker = new LocalFabricWorker<>(
			new EmbeddedProcessChannel<Serializable>(defaultNoticeableFuture));

		NettyChannelAttributes.putFabricWorker(
			_embeddedChannel, 0, fabricWorker);

		Assert.assertSame(
			fabricWorker,
			NettyChannelAttributes.getFabricWorker(_embeddedChannel, 0));

		defaultNoticeableFuture.set(null);

		Assert.assertNull(
			NettyChannelAttributes.getFabricWorker(_embeddedChannel, 0));
	}

	@Test
	public void testConstructor() {
		new NettyChannelAttributes();
	}

	@Test
	public void testGetAsyncBroker() {
		AsyncBroker<Long, Serializable> asyncBroker =
			NettyChannelAttributes.getAsyncBroker(_embeddedChannel);

		Assert.assertSame(
			asyncBroker,
			NettyChannelAttributes.getAsyncBroker(_embeddedChannel));
	}

	@Test
	public void testGetPutFabricWorker() {
		Assert.assertNull(
			NettyChannelAttributes.getFabricWorker(_embeddedChannel, 0));

		DefaultNoticeableFuture<Serializable> defaultNoticeableFuture1 =
			new DefaultNoticeableFuture<>();

		FabricWorker<Serializable> fabricWorker1 = new LocalFabricWorker<>(
			new EmbeddedProcessChannel<Serializable>(defaultNoticeableFuture1));

		NettyChannelAttributes.putFabricWorker(
			_embeddedChannel, 0, fabricWorker1);

		Assert.assertSame(
			fabricWorker1,
			NettyChannelAttributes.getFabricWorker(_embeddedChannel, 0));

		defaultNoticeableFuture1.set(null);

		Assert.assertNull(
			NettyChannelAttributes.getFabricWorker(_embeddedChannel, 0));

		DefaultNoticeableFuture<Serializable> defaultNoticeableFuture2 =
			new DefaultNoticeableFuture<>();

		FabricWorker<Serializable> fabricWorker2 = new LocalFabricWorker<>(
			new EmbeddedProcessChannel<Serializable>(defaultNoticeableFuture2));

		NettyChannelAttributes.putFabricWorker(
			_embeddedChannel, 1, fabricWorker2);

		Assert.assertSame(
			fabricWorker2,
			NettyChannelAttributes.getFabricWorker(_embeddedChannel, 1));

		defaultNoticeableFuture2.set(null);

		Assert.assertNull(
			NettyChannelAttributes.getFabricWorker(_embeddedChannel, 1));
	}

	@Test
	public void testGetSetNettyFabricAgentStub() {
		Assert.assertNull(
			NettyChannelAttributes.getNettyFabricAgentStub(_embeddedChannel));

		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository<Channel>(),
			Paths.get("remoteRepositoryPath"), 0, 0);

		NettyChannelAttributes.setNettyFabricAgentStub(
			_embeddedChannel, nettyFabricAgentStub);

		Assert.assertSame(
			nettyFabricAgentStub,
			NettyChannelAttributes.getNettyFabricAgentStub(_embeddedChannel));
	}

	@Test
	public void testNextId() {
		Assert.assertEquals(0, NettyChannelAttributes.nextId(_embeddedChannel));
		Assert.assertEquals(1, NettyChannelAttributes.nextId(_embeddedChannel));
	}

	@Aspect
	public static class AttributeAdvice {

		public static void setConcurrentValue(Object concurrentValue) {
			_concurrentValue = concurrentValue;
		}

		@Around(
			"execution(public Object io.netty.util.Attribute.setIfAbsent(" +
				"Object))"
		)
		public Object setIfAbsent(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Attribute<Object> attribute =
				(Attribute<Object>)proceedingJoinPoint.getThis();

			attribute.set(_concurrentValue);

			return proceedingJoinPoint.proceed();
		}

		private static Object _concurrentValue;

	}

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();

}