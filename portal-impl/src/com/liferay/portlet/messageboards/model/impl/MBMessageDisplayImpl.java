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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadConstants;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MBMessageDisplayImpl implements MBMessageDisplay {

	public MBMessageDisplayImpl(
		MBMessage message, MBMessage parentMessage, MBCategory category,
		MBThread thread, MBThread previousThread, MBThread nextThread,
		int status, String threadView,
		MBMessageLocalService messageLocalService,
		Comparator<MBMessage> comparator) {

		_message = message;
		_parentMessage = parentMessage;
		_category = category;
		_thread = thread;

		if (!threadView.equals(MBThreadConstants.THREAD_VIEW_FLAT)) {
			_treeWalker = new MBTreeWalkerImpl(
				message.getThreadId(), status, messageLocalService, comparator);
		}
		else {
			_treeWalker = null;
		}

		_previousThread = previousThread;
		_nextThread = nextThread;
		_threadView = threadView;
	}

	@Override
	public MBCategory getCategory() {
		return _category;
	}

	@Override
	public MBMessage getMessage() {
		return _message;
	}

	@Override
	public MBThread getNextThread() {
		return _nextThread;
	}

	@Override
	public MBMessage getParentMessage() {
		return _parentMessage;
	}

	@Override
	public MBThread getPreviousThread() {
		return _previousThread;
	}

	@Override
	public MBThread getThread() {
		return _thread;
	}

	@Override
	public String getThreadView() {
		return _threadView;
	}

	@Override
	public MBTreeWalker getTreeWalker() {
		return _treeWalker;
	}

	private final MBCategory _category;
	private final MBMessage _message;
	private final MBThread _nextThread;
	private final MBMessage _parentMessage;
	private final MBThread _previousThread;
	private final MBThread _thread;
	private final String _threadView;
	private final MBTreeWalker _treeWalker;

}