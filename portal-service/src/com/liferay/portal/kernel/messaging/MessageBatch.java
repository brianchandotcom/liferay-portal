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

package com.liferay.portal.kernel.messaging;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class MessageBatch implements Serializable {

	public MessageBatch(int initialSize) {
		this(null, initialSize);
	}

	public MessageBatch(String messageBatchId) {
		this(messageBatchId, 10);
	}

	public MessageBatch(String messageBatchId, int initialSize) {
		_messageBatchId = messageBatchId;
		_messages = new ArrayList<Message>(initialSize);
	}

	public void addMessage(Message message) {
		_messages.add(message);
	}

	public String getMessageBatchId() {
		return _messageBatchId;
	}

	public List<Message> getMessages() {
		return _messages;
	}

	private final String _messageBatchId;
	private final List<Message> _messages;

}