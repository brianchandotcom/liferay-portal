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

import JOscarLib.Core.OscarConnection;

import JOscarLib.Tool.OscarInterface;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * @author Brian Wing Shun Chan
 * @author Brett Randall
 */
public class ICQConnector implements Observer {

	public static void disconnect() {
		if (_instance != null) {
			_instance._disconnect();
		}
	}

	public static void send(String to, String msg) {
		_instance._send(to, msg);
	}

	@Override
	public void update(Observable obs, Object obj) {
		_connecting = false;

		for (KeyValuePair kvp : _messages) {
			OscarInterface.sendMessage(_icq, kvp.getKey(), kvp.getValue());
		}
	}

	private ICQConnector() {
		_messages = new Vector<KeyValuePair>();
	}

	private void _connect() {
		_connecting = true;

		String login = PropsUtil.get(PropsKeys.ICQ_LOGIN);
		String password = PropsUtil.get(PropsKeys.ICQ_PASSWORD);

		_icq = new OscarConnection("login.icq.com", 5190, login, password);

		//_icq.getPacketAnalyser().setDebug(true);

		_icq.addObserver(this);
	}

	private void _disconnect() {
		try {
			if (_icq != null) {
				_icq.close();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}
	}

	private synchronized void _send(String to, String msg) {
		if (((_icq == null) || !_icq.isLogged()) && !_connecting) {
			_connect();

			_messages.add(new KeyValuePair(to, msg));
		}
		else {
			OscarInterface.sendMessage(_icq, to, msg);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ICQConnector.class);

	private static final ICQConnector _instance = new ICQConnector();

	private boolean _connecting;
	private OscarConnection _icq;
	private final List<KeyValuePair> _messages;

}