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
import java.util.concurrent.RejectedExecutionException;

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

		boolean hasDoViewAccepted = false;

		Class<?> clazz = getClass();

		try {
			clazz.getDeclaredMethod("doViewAccepted", View.class, View.class);

			hasDoViewAccepted = true;
		}
		catch (ReflectiveOperationException roe) {
		}

		_hasDoViewAccepted = hasDoViewAccepted;
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

			_executorService.execute(new MessageCallBackJob(message));
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}
		catch (RejectedExecutionException ree) {
			_log.error("Unable to handle received message " + message, ree);
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

		View oldView = _view;

		try {
			_countDownLatch.await();

			_view = view;

			if (_hasDoViewAccepted) {
				_executorService.execute(new ViewCallBackJob(oldView, view));
			}
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}
		catch (RejectedExecutionException ree) {
			_log.error(
				"Unable to handle view update from " + oldView + " to " + view,
				ree);
		}
	}

	protected abstract void doReceive(Message message);

	protected void doViewAccepted(View oldView, View newView) {
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseReceiver.class);

	private final CountDownLatch _countDownLatch = new CountDownLatch(1);
	private final ExecutorService _executorService;
	private final boolean _hasDoViewAccepted;
	private volatile View _view;

	private class MessageCallBackJob implements Runnable {

		@Override
		public void run() {
			doReceive(_message);
		}

		private MessageCallBackJob(Message message) {
			_message = message;
		}

		private final Message _message;

	}

	private class ViewCallBackJob implements Runnable {

		@Override
		public void run() {
			doViewAccepted(_oldView, _newView);
		}

		private ViewCallBackJob(View oldView, View newView) {
			_oldView = oldView;
			_newView = newView;
		}

		private final View _newView;
		private final View _oldView;

	}

}