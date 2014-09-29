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

import com.liferay.portal.fabric.netty.rpc.RPCUtil;
import com.liferay.portal.kernel.concurrent.AsyncBroker;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.io.Serializable;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class NettyChannelAttributes {

	public static <T extends Serializable> AsyncBroker<Long, T> getAsyncBroker(
		Channel channel) {

		Attribute<AsyncBroker<Long, Serializable>> attribute = channel.attr(
			_asyncBrokerKey);

		AsyncBroker<Long, Serializable> asyncBroker = attribute.get();

		if (asyncBroker == null) {
			asyncBroker = new AsyncBroker<Long, Serializable>();

			AsyncBroker<Long, Serializable> previousAsyncBroker =
				attribute.setIfAbsent(asyncBroker);

			if (previousAsyncBroker != null) {
				asyncBroker = previousAsyncBroker;
			}
		}

		return (AsyncBroker<Long, T>)asyncBroker;
	}

	public static long nextId(Channel channel) {
		Attribute<AtomicLong> attribute = channel.attr(_idGeneratorKey);

		AtomicLong attachmentIdGenerator = attribute.get();

		if (attachmentIdGenerator == null) {
			attachmentIdGenerator = new AtomicLong();

			AtomicLong previousAttachmentIdGenerator = attribute.setIfAbsent(
				attachmentIdGenerator);

			if (previousAttachmentIdGenerator != null) {
				attachmentIdGenerator = previousAttachmentIdGenerator;
			}
		}

		return attachmentIdGenerator.getAndIncrement();
	}

	private static final AttributeKey<AsyncBroker<Long, Serializable>>
		_asyncBrokerKey = AttributeKey.valueOf(
			RPCUtil.class.getName() + "-AsyncBroker");
	private static final AttributeKey<AtomicLong> _idGeneratorKey =
		AttributeKey.valueOf(RPCUtil.class.getName() + "-IdGenerator");

}