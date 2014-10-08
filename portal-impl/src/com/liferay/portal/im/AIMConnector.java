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

package com.liferay.portal.im;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import org.walluck.oscar.AIMConnection;
import org.walluck.oscar.AIMSession;
import org.walluck.oscar.client.Oscar;

/**
 * @author Brian Wing Shun Chan
 * @author Brett Randall
 * @author Bruno Farache
 */
public class AIMConnector {

	public static void disconnect() {
		if (_instance != null) {
			_instance._disconnect();
		}
	}

	public static void send(String to, String msg) {
		_instance._send(to, msg);
	}

	private AIMConnector() {
	}

	private void _connect() {
		String login = PropsUtil.get(PropsKeys.AIM_LOGIN);
		String password = PropsUtil.get(PropsKeys.AIM_PASSWORD);

		AIMSession ses = new AIMSession();

		ses.setSN(login);

		_oscar = new Oscar();

		_oscar.setPassword(password);
		_oscar.setSN(login);

		ses.init();
	}

	private void _disconnect() {
		if (_aim != null) {
			AIMConnection.killAllInSess(_aim);
		}
	}

	private void _send(String to, String msg) {
		try {
			if (_aim == null) {
				_connect();

				// Daim's listeners are buggy. Instead, just wait a second
				// before sending the first message.

				Thread.sleep(1000);
			}

			_oscar.sendIM(_aim, to, msg, Oscar.getICQCaps());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not send AIM message");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(AIMConnector.class);

	private static final AIMConnector _instance = new AIMConnector();

	private AIMSession _aim;
	private Oscar _oscar;

}