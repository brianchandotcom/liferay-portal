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

package com.liferay.portal.fabric.netty.codec.serialization;

import com.liferay.petra.io.AnnotatedObjectOutputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.fabric.netty.util.NettyUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class AnnotatedObjectDecoderTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		_channelPipeline.addLast(_annotatedObjectDecoder);

		ReflectionTestUtil.setFieldValue(_dateChannelHandler, "_added", true);
		ReflectionTestUtil.setFieldValue(_uuidChannelHandler, "_added", true);
	}

	@Test
	public void testAddFirstBatch() {
		_annotatedObjectDecoder.addFirst(
			_dateChannelHandler, _uuidChannelHandler);

		_assertChannelPipeline(_dateChannelHandler, _uuidChannelHandler);
	}

	@Test
	public void testAddFirstWithName() {
		_annotatedObjectDecoder.addFirst(
			DateChannelHandler.class.getName(), _dateChannelHandler);
		_annotatedObjectDecoder.addFirst(
			UUIDChannelHandler.class.getName(), _uuidChannelHandler);

		_assertChannelPipeline(_uuidChannelHandler, _dateChannelHandler);
	}

	@Test
	public void testAddLastBatch() {
		_annotatedObjectDecoder.addLast(
			_dateChannelHandler, _uuidChannelHandler);

		_assertChannelPipeline(_dateChannelHandler, _uuidChannelHandler);
	}

	@Test
	public void testAddLastWithName() {
		_annotatedObjectDecoder.addLast(
			DateChannelHandler.class.getName(), _dateChannelHandler);
		_annotatedObjectDecoder.addLast(
			UUIDChannelHandler.class.getName(), _uuidChannelHandler);

		_assertChannelPipeline(_dateChannelHandler, _uuidChannelHandler);
	}

	@Test
	public void testDecode() throws Exception {
		_annotatedObjectDecoder.addFirst(
			_dateChannelHandler, _uuidChannelHandler);

		Date date = new Date();

		ByteBuf byteBuf = _toByteBuf(date);

		byteBuf.markWriterIndex();

		byteBuf.setIndex(0, byteBuf.writerIndex() / 2);

		Assert.assertNull(_annotatedObjectDecoder.decode(null, byteBuf));

		byteBuf.resetWriterIndex();

		Assert.assertEquals(
			date, _annotatedObjectDecoder.decode(null, byteBuf));

		UUID uuid = UUID.randomUUID();

		Assert.assertNull(_uuidChannelHandler.getUuid());
		Assert.assertNull(
			_annotatedObjectDecoder.decode(null, _toByteBuf(uuid)));
		Assert.assertEquals(uuid, _uuidChannelHandler.getUuid());
	}

	@Test
	public void testRemoveByInstance() {
		_annotatedObjectDecoder.addFirst(
			_dateChannelHandler, _uuidChannelHandler);

		_assertChannelPipeline(_dateChannelHandler, _uuidChannelHandler);

		_annotatedObjectDecoder.remove(_uuidChannelHandler);

		_assertChannelPipeline(_dateChannelHandler);

		try {
			_annotatedObjectDecoder.remove(_uuidChannelHandler);

			Assert.fail();
		}
		catch (NoSuchElementException nsee) {
		}

		_assertChannelPipeline(_dateChannelHandler);

		_annotatedObjectDecoder.remove(_dateChannelHandler);

		try {
			_annotatedObjectDecoder.remove(_dateChannelHandler);

			Assert.fail();
		}
		catch (NoSuchElementException nsee) {
		}

		_assertChannelPipeline();
	}

	@Test
	public void testRemoveByName() {
		_annotatedObjectDecoder.addFirst(
			DateChannelHandler.class.getName(), _dateChannelHandler);
		_annotatedObjectDecoder.addFirst(
			UUIDChannelHandler.class.getName(), _uuidChannelHandler);

		_assertChannelPipeline(_uuidChannelHandler, _dateChannelHandler);

		Assert.assertSame(
			_dateChannelHandler,
			_annotatedObjectDecoder.remove(DateChannelHandler.class.getName()));

		_assertChannelPipeline(_uuidChannelHandler);

		Assert.assertSame(
			_uuidChannelHandler,
			_annotatedObjectDecoder.remove(UUIDChannelHandler.class.getName()));

		_assertChannelPipeline();
	}

	@Test
	public void testRemoveByType() {
		_annotatedObjectDecoder.addFirst(
			_dateChannelHandler, _uuidChannelHandler);

		_assertChannelPipeline(_dateChannelHandler, _uuidChannelHandler);

		Assert.assertSame(
			_uuidChannelHandler,
			_annotatedObjectDecoder.remove(UUIDChannelHandler.class));

		_assertChannelPipeline(_dateChannelHandler);

		try {
			_annotatedObjectDecoder.remove(UUIDChannelHandler.class);

			Assert.fail();
		}
		catch (NoSuchElementException nsee) {
		}

		_assertChannelPipeline(_dateChannelHandler);

		Assert.assertSame(
			_dateChannelHandler,
			_annotatedObjectDecoder.remove(DateChannelHandler.class));

		try {
			_annotatedObjectDecoder.remove(DateChannelHandler.class);

			Assert.fail();
		}
		catch (NoSuchElementException nsee) {
		}

		_assertChannelPipeline();
	}

	@Test
	public void testRemoveFirst() {
		_annotatedObjectDecoder.addFirst(
			_dateChannelHandler, _uuidChannelHandler);

		_assertChannelPipeline(_dateChannelHandler, _uuidChannelHandler);

		Assert.assertSame(
			_dateChannelHandler, _annotatedObjectDecoder.removeFirst());

		_assertChannelPipeline(_uuidChannelHandler);

		Assert.assertSame(
			_uuidChannelHandler, _annotatedObjectDecoder.removeFirst());

		_assertChannelPipeline();
	}

	@Test
	public void testRemoveLast() {
		_annotatedObjectDecoder.addFirst(
			_dateChannelHandler, _uuidChannelHandler);

		_assertChannelPipeline(_dateChannelHandler, _uuidChannelHandler);

		Assert.assertSame(
			_uuidChannelHandler, _annotatedObjectDecoder.removeLast());

		_assertChannelPipeline(_dateChannelHandler);

		Assert.assertSame(
			_dateChannelHandler, _annotatedObjectDecoder.removeFirst());

		_assertChannelPipeline();
	}

	private void _assertChannelPipeline(ChannelHandler... channelHandlers) {
		Map<String, ChannelHandler> map = _innerChannelPipeline.toMap();

		Assert.assertEquals(map.toString(), channelHandlers.length, map.size());
		Assert.assertEquals(
			Arrays.asList(channelHandlers),
			new ArrayList<ChannelHandler>(map.values()));
	}

	private ByteBuf _toByteBuf(Serializable serializable) throws IOException {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (AnnotatedObjectOutputStream annotatedObjectOutputStream =
				new AnnotatedObjectOutputStream(unsyncByteArrayOutputStream)) {

			annotatedObjectOutputStream.writeObject(serializable);
		}

		ByteBuf byteBuf = Unpooled.buffer();

		byteBuf.writeInt(unsyncByteArrayOutputStream.size());
		byteBuf.writeBytes(unsyncByteArrayOutputStream.toByteArray());

		return byteBuf;
	}

	private final AnnotatedObjectDecoder _annotatedObjectDecoder =
		new AnnotatedObjectDecoder();
	private final ChannelPipeline _channelPipeline =
		NettyUtil.createEmptyChannelPipeline();
	private final DateChannelHandler _dateChannelHandler =
		new DateChannelHandler();
	private final ChannelPipeline _innerChannelPipeline =
		(ChannelPipeline)ReflectionTestUtil.getFieldValue(
			_annotatedObjectDecoder, "_channelPipeline");
	private final UUIDChannelHandler _uuidChannelHandler =
		new UUIDChannelHandler();

	private static class DateChannelHandler
		extends ObjectDecodeChannelInboundHandler<Date> {

		@Override
		public Date channelRead0(
			ChannelHandlerContext channelHandlerContext, Date date,
			ByteBuf byteBuf) {

			return date;
		}

	}

	private static class UUIDChannelHandler
		extends ObjectDecodeChannelInboundHandler<UUID> {

		@Override
		public UUID channelRead0(
			ChannelHandlerContext channelHandlerContext, UUID uuid,
			ByteBuf byteBuf) {

			_uuid = uuid;

			return null;
		}

		public UUID getUuid() {
			return _uuid;
		}

		private UUID _uuid;

	}

}