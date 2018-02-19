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

package com.liferay.lcs.task;

import com.liferay.lcs.messaging.Message;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Igor Beslic
 */
public abstract class BaseScheduledTask implements ScheduledTask {

	@Override
	public void run() {
		if (!_lcsConnectionManager.isReady()) {
			if (_log.isDebugEnabled()) {
				_log.debug(getClass() + " waiting for LCS connection manager");
			}

			return;
		}

		try {
			doRun();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	protected abstract void doRun() throws Exception;

	protected String getKey() {
		return _keyGenerator.getKey();
	}

	protected void sendMessage(Message message) throws PortalException {
		if (_log.isTraceEnabled()) {
			_log.trace("Sending message: " + message.getPayload());
		}

		try {
			_lcsConnectionManager.sendMessage(message);
		}
		catch (Exception e) {
			_log.error("Unable to send message", e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseScheduledTask.class);

	private KeyGenerator _keyGenerator;
	private LCSConnectionManager _lcsConnectionManager;

}