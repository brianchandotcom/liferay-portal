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

import java.lang.reflect.Method;

/**
 * @author Brian Wing Shun Chan
 * @author Brett Randall
 * @author Bruno Farache
 * @author Shuyang Zhou
 */
public class YMConnector {

	public static void disconnect() {
		if (_instance != null) {
			_instance._disconnect();
		}
	}

	public static void send(String to, String msg)
		throws IllegalStateException {

		_instance._send(to, msg);
	}

	private YMConnector() {
	}

	private void _connect() {
		try {
			Class<?> clazz = Class.forName(_SESSION);

			_ym = clazz.newInstance();

			_loginMethod = clazz.getMethod("login", String.class, String.class);
			_logoutMethod = clazz.getMethod("logout");
			_sendMessageMethod = clazz.getMethod(
				"sendMessage", String.class, String.class);
		}
		catch (Exception e) {
			_jYMSGLibraryFound = false;

			if (_log.isWarnEnabled()) {
				_log.warn(
					"jYMSG library could not be loaded: " + e.getMessage());
			}
		}

		try {
			if (_jYMSGLibraryFound) {
				String login = PropsUtil.get(PropsKeys.YM_LOGIN);
				String password = PropsUtil.get(PropsKeys.YM_PASSWORD);

				_loginMethod.invoke(_ym, login, password);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private void _disconnect() {
		try {
			if (_ym != null) {
				_logoutMethod.invoke(_ym);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}
	}

	private void _send(String to, String msg) throws IllegalStateException {
		try {
			if (_ym == null) {
				_connect();
			}

			if (_jYMSGLibraryFound) {
				_sendMessageMethod.invoke(_ym, to, msg);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static final String _SESSION = "ymsg.network.Session";

	private static final Log _log = LogFactoryUtil.getLog(YMConnector.class);

	private static final YMConnector _instance = new YMConnector();

	private boolean _jYMSGLibraryFound = true;
	private Method _loginMethod;
	private Method _logoutMethod;
	private Method _sendMessageMethod;
	private Object _ym;

}