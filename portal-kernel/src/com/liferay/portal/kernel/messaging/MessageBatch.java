/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		_messages = new ArrayList<>(initialSize);
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