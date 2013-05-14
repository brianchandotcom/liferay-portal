/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MBMessageDisplayImpl implements MBMessageDisplay {

	public MBMessageDisplayImpl(
		MBMessage message, MBMessage parentMessage, MBCategory category,
		MBThread thread, MBThread previousThread, MBThread nextThread,
		int status, String threadView,
		MBMessageLocalService messageLocalService) {

		_message = message;
		_parentMessage = parentMessage;
		_category = category;
		_thread = thread;

		if (!threadView.equals(MBThreadConstants.THREAD_VIEW_FLAT)) {
			_treeWalker = new MBTreeWalkerImpl(
				message, status, messageLocalService);
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

	private MBCategory _category;
	private MBMessage _message;
	private MBThread _nextThread;
	private MBMessage _parentMessage;
	private MBThread _previousThread;
	private MBThread _thread;
	private String _threadView;
	private MBTreeWalker _treeWalker;

}