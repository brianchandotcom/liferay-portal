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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.util.Time;

import java.util.Collection;

import javax.servlet.http.HttpSession;

/**
 * @author Alexander Chow
 */
public class MaintenanceUtil {

	public static void appendStatus(String status) {
		_instance._appendStatus(status);
	}

	public static void cancel() {
		_instance._cancel();
	}

	public static String getClassName() {
		return _instance._getClassName();
	}

	public static String getSessionId() {
		return _instance._getSessionId();
	}

	public static String getStatus() {
		return _instance._getStatus();
	}

	public static boolean isMaintaining() {
		return _instance._isMaintaining();
	}

	public static void maintain(String sessionId, String className) {
		_instance._maintain(sessionId, className);
	}

	private MaintenanceUtil() {
	}

	private void _appendStatus(String status) {
		if (_log.isDebugEnabled()) {
			_log.debug(status);
		}

		_status.append(Time.getRFC822() + " " + status + "<br />");
	}

	private void _cancel() {
		HttpSession session = PortalSessionContext.get(_sessionId);

		if (session != null) {
			session.invalidate();
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Session " + _sessionId + " is null");
			}
		}

		_maintaining = false;
	}

	private String _getClassName() {
		return _className;
	}

	private String _getSessionId() {
		return _sessionId;
	}

	private String _getStatus() {
		return _status.toString();
	}

	private boolean _isMaintaining() {
		return _maintaining;
	}

	private void _maintain(String sessionId, String className) {
		_sessionId = sessionId;
		_className = className;
		_maintaining = true;
		_status = new StringBuffer();

		_appendStatus("Executing " + _className);

		Collection<HttpSession> sessions = PortalSessionContext.values();

		for (HttpSession session : sessions) {
			if (!sessionId.equals(session.getId())) {
				try {
					session.invalidate();
				}
				catch (IllegalStateException ise) {
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MaintenanceUtil.class);

	private static final MaintenanceUtil _instance = new MaintenanceUtil();

	private String _className;
	private boolean _maintaining = false;
	private String _sessionId;
	private StringBuffer _status = new StringBuffer();

}