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

package com.liferay.portal.kernel.mail;

import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * @author Thiago Moreira
 */
public abstract class Account implements Serializable {

	public static final int PORT_IMAP = 143;

	public static final int PORT_IMAPS = 993;

	public static final int PORT_POP = 110;

	public static final int PORT_POPS = 995;

	public static final int PORT_SMTP = 25;

	public static final int PORT_SMTPS = 465;

	public static final String PROTOCOL_IMAP = "imap";

	public static final String PROTOCOL_IMAPS = "imaps";

	public static final String PROTOCOL_POP = "pop3";

	public static final String PROTOCOL_POPS = "pop3s";

	public static final String PROTOCOL_SMTP = "smtp";

	public static final String PROTOCOL_SMTPS = "smtps";

	public static Account getInstance(String protocol) {
		return getInstance(protocol, 0);
	}

	public static Account getInstance(String protocol, int port) {
		Account account = null;

		if (protocol.startsWith(PROTOCOL_IMAP)) {
			boolean secure = false;
			int defaultPort = PORT_IMAP;

			if (protocol.endsWith("s")) {
				secure = true;
				defaultPort = PORT_IMAPS;
			}

			if (port <= 0) {
				port = defaultPort;
			}

			account = new IMAPAccount(protocol, secure, port);
		}
		else if (protocol.startsWith(PROTOCOL_POP)) {
			boolean secure = false;
			int defaultPort = PORT_POP;

			if (protocol.endsWith("s")) {
				secure = true;
				defaultPort = PORT_POPS;
			}

			if (port <= 0) {
				port = defaultPort;
			}

			account = new POPAccount(protocol, secure, port);
		}
		else {
			boolean secure = false;
			int defaultPort = PORT_SMTP;

			if (protocol.endsWith("s")) {
				secure = true;
				defaultPort = PORT_SMTPS;
			}

			if (port <= 0) {
				port = defaultPort;
			}

			account = new SMTPAccount(protocol, secure, port);
		}

		return account;
	}

	public String getHost() {
		return _host;
	}

	public String getPassword() {
		return _password;
	}

	public int getPort() {
		return _port;
	}

	public String getProtocol() {
		return _protocol;
	}

	public String getUser() {
		return _user;
	}

	public boolean isRequiresAuthentication() {
		if (Validator.isNotNull(_user) && Validator.isNotNull(_password)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSecure() {
		return _secure;
	}

	public void setHost(String host) {
		_host = host;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setPort(int port) {
		_port = port;
	}

	public void setUser(String user) {
		_user = user;
	}

	protected Account(String protocol, boolean secure, int port) {
		_protocol = protocol;
		_secure = secure;
		_port = port;
	}

	private String _host;
	private String _password;
	private int _port;
	private final String _protocol;
	private final boolean _secure;
	private String _user;

}