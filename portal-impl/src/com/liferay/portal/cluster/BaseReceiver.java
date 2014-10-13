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

import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;

/**
 * @author Tina Tian
 */
public class BaseReceiver implements Receiver {

	@Override
	public void block() {
	}

	@Override
	public void getState(OutputStream outputStream) throws Exception {
	}

	public View getView() {
		return view;
	}

	@Override
	public void receive(Message message) {
	}

	@Override
	public void setState(InputStream inputStream) throws Exception {
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

		this.view = view;
	}

	protected volatile View view;

	private static final Log _log = LogFactoryUtil.getLog(BaseReceiver.class);

}