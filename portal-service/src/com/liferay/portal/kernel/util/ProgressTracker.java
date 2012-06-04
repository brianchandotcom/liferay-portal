/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 */
public class ProgressTracker {

	public static final String PERCENT =
		ProgressTracker.class.getName() + "_PERCENT";

	public static void addProgress(int status, int percent, String message) {
		Tuple tuple = new Tuple(percent, message);

		_progress.put(status, tuple);
	}

	public ProgressTracker(HttpServletRequest request, String progressId) {
		this(request.getSession(), progressId);
	}

	public ProgressTracker(PortletRequest portletRequest, String progressId) {
		this(portletRequest.getPortletSession(), progressId);
	}

	public ProgressTracker(HttpSession session, String progressId) {
		_session = session;
		_progressId = progressId;
	}

	public ProgressTracker(PortletSession portletSession, String progressId) {
		_portletSession = portletSession;
		_progressId = progressId;
	}

	public void finish() {
		if (_session != null) {
			_session.removeAttribute(PERCENT + _progressId);
		}
		else {
			_portletSession.removeAttribute(
				PERCENT + _progressId, PortletSession.APPLICATION_SCOPE);
		}
	}

	public String getMessage() {
		Tuple tuple = _progress.get(_status);

		String message = GetterUtil.getString(tuple.getObject(1));

		return message;
	}

	public int getPercent() {
		return _percent;
	}

	public int getStatus() {
		return _status;
	}

	public void initialize() {
		if (_session != null) {
			_session.setAttribute(PERCENT + _progressId, this);
		}
		else {
			_portletSession.setAttribute(
				PERCENT + _progressId, this, PortletSession.APPLICATION_SCOPE);
		}
	}

	public void setPercent(int percent) {
		_percent = percent;
	}

	public void setStatus(int status) {
		_status = status;

		Tuple tuple = _progress.get(_status);

		_percent = GetterUtil.getInteger(tuple.getObject(0));
	}

	public void start() {
		setPercent(1);
	}

	private static Map<Integer, Tuple> _progress =
		new HashMap<Integer, Tuple>();

	private int _percent = 0;
	private PortletSession _portletSession;
	private String _progressId;
	private HttpSession _session;

	private int _status = ProgressStatusConstants.PREPARED;

	static {
		addProgress(ProgressStatusConstants.PREPARED, 0, StringPool.BLANK);
	}

}