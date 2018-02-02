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

package com.liferay.message.boards.web.internal.util;

import com.liferay.message.boards.model.MBMessage;

import java.util.Iterator;
import java.util.List;

/**
 * @author Sergio González
 */
public class MBMessageIterator implements Iterator<MBMessage> {

	public MBMessageIterator(List<MBMessage> messages, int from, int to) {
		_messages = messages;
		_from = from;
		_to = to;
	}

	public int getIndexPage() {
		return _from;
	}

	@Override
	public boolean hasNext() {
		if (_from < _to) {
			return true;
		}

		return false;
	}

	@Override
	public MBMessage next() {
		MBMessage message = _messages.get(_from);

		_from++;

		return message;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	private int _from;
	private final List<MBMessage> _messages;
	private final int _to;

}