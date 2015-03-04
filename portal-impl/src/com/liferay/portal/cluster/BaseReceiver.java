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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * @author Tina Tian
 */
public abstract class BaseReceiver extends ReceiverAdapter {

	public BaseReceiver(ExecutorService executorService) {
		if (executorService == null) {
			throw new NullPointerException("Executor service is null");
		}

		_executorService = executorService;
	}

	public View getView() {
		return _view;
	}

	public void openLatch() {
		_countDownLatch.countDown();
	}

	@Override
	public void receive(Message message) {
		try {
			_countDownLatch.await();

			if (_executorService.isShutdown()) {
				_log.error("Executor server is already closed");

				return;
			}

			_executorService.execute(new MessageCallBackJob(this, message));
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}
	}

	@Override
	public void viewAccepted(View view) {
		if (_log.isInfoEnabled()) {
			_log.info("Accepted view " + view);
		}

		if (_view == null) {
			_view = view;

			return;
		}

		try {
			_countDownLatch.await();

			if (_executorService.isShutdown()) {
				_log.error("Executor server is already closed");

				return;
			}

			View oldView = _view;

			_view = view;

			_executorService.execute(new ViewCallBackJob(this, oldView, view));
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}
	}

	protected abstract void doReceive(Message message);

	protected void doViewAccepted(View oldView, View newView) {
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseReceiver.class);

	private final CountDownLatch _countDownLatch = new CountDownLatch(1);
	private final ExecutorService _executorService;
	private volatile View _view;

	private class MessageCallBackJob implements Runnable {

		public MessageCallBackJob(BaseReceiver baseReceiver, Message message) {
			_baseReceiver = baseReceiver;
			_message = message;
		}

		@Override
		public void run() {
			_baseReceiver.doReceive(_message);
		}

		private final BaseReceiver _baseReceiver;
		private final Message _message;

	}

	private class ViewCallBackJob implements Runnable {

		public ViewCallBackJob(
			BaseReceiver baseReceiver, View oldView, View newView) {

			_baseReceiver = baseReceiver;
			_oldView = oldView;
			_newView = newView;
		}

		@Override
		public void run() {
			_baseReceiver.doViewAccepted(_oldView, _newView);
		}

		private final BaseReceiver _baseReceiver;
		private final View _newView;
		private final View _oldView;

	}

}