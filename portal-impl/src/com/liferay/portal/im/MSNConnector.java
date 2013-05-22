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

package com.liferay.portal.im;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import rath.msnm.MSNMessenger;
import rath.msnm.UserStatus;

/**
 * @author Brian Wing Shun Chan
 * @author Brett Randall
 */
public class MSNConnector {

	public static void disconnect() {
		if (_instance != null) {
			_instance._disconnect();
		}
	}

	public static void send(String to, String msg) {
		_instance._send(to, msg);
	}

	private MSNConnector() {
		_login = PropsUtil.get(PropsKeys.MSN_LOGIN);
		_password = PropsUtil.get(PropsKeys.MSN_PASSWORD);

		_msn = new MSNMessenger(_login, _password);
		_msn.setInitialStatus(UserStatus.ONLINE);
	}

	private void _connect() {
		if (_msn.isLoggedIn()) {
			return;
		}

		_msn.login();

		// Spend 5 seconds to attempt to login

		for (int i = 0; i < 50 && !_msn.isLoggedIn(); i++) {
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ie) {
				_log.warn(ie);

				break;
			}
		}

		if (!_msn.isLoggedIn()) {
			_log.error("Unable to connect as " + _login);
		}
	}

	private void _disconnect() {
		try {
			if (_msn.isLoggedIn()) {
				_msn.logout();
			}
		}
		catch (Exception e) {
			_log.warn(e);
		}
	}

	private void _send(String to, String msg) {
		_connect();

		_msn.addMsnListener(new MSNMessageAdapter(_msn, to, msg));

		try {
			Thread.sleep(1500);

			_msn.doCallWait(to);
		}
		catch (Exception e) {
			_log.warn(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MSNConnector.class);

	private static MSNConnector _instance = new MSNConnector();

	private String _login;
	private MSNMessenger _msn;
	private String _password;

}