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

import java.io.InputStream;
import java.io.OutputStream;

import java.util.concurrent.CountDownLatch;

import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;

/**
 * @author Tina Tian
 */
public abstract class BaseReceiver implements Receiver {

	@Override
	public void block() {
	}

	@Override
	public void getState(OutputStream outputStream) {
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
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}

		doReceive(message);
	}

	@Override
	public void setState(InputStream inputStream) {
	}

	@Override
	public void suspect(Address address) {
	}

	@Override
	public void unblock() {
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
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}

		View oldView = _view;

		_view = view;

		doViewAccepted(oldView, view);
	}

	protected abstract void doReceive(Message message);

	protected void doViewAccepted(View oldView, View newView) {
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseReceiver.class);

	private final CountDownLatch _countDownLatch = new CountDownLatch(1);
	private volatile View _view;

}