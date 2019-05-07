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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

import java.io.Serializable;

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
public class ProgressTracker implements Serializable {

	public static final String PERCENT =
		ProgressTracker.class.getName() + "_PERCENT";

	public ProgressTracker(String progressId) {
		_progressId = progressId;

		addProgress(ProgressStatusConstants.PREPARED, 0, StringPool.BLANK);
	}

	public void addProgress(int status, int percent, String message) {
		Tuple tuple = new Tuple(percent, message);

		_progress.put(status, tuple);
	}

	public void finish(HttpServletRequest httpServletRequest) {
		finish(httpServletRequest.getSession());
	}

	public void finish(HttpSession session) {
		session.removeAttribute(PERCENT + _progressId);
	}

	public void finish(PortletRequest portletRequest) {
		finish(portletRequest.getPortletSession());
	}

	public void finish(PortletSession portletSession) {
		portletSession.removeAttribute(
			PERCENT + _progressId, PortletSession.APPLICATION_SCOPE);
	}

	public String getMessage() {
		Tuple tuple = _progress.get(_status);

		return GetterUtil.getString(tuple.getObject(1));
	}

	public int getPercent() {
		return _percent;
	}

	public int getStatus() {
		return _status;
	}

	public void initialize(HttpServletRequest httpServletRequest) {
		initialize(httpServletRequest.getSession());
	}

	public void initialize(HttpSession session) {
		session.setAttribute(PERCENT + _progressId, this);
	}

	public void initialize(PortletRequest portletRequest) {
		initialize(portletRequest.getPortletSession());
	}

	public void initialize(PortletSession portletSession) {
		portletSession.setAttribute(
			PERCENT + _progressId, this, PortletSession.APPLICATION_SCOPE);
	}

	public void setPercent(int percent) {
		_percent = percent;
	}

	public void setStatus(int status) {
		_status = status;

		Tuple tuple = _progress.get(_status);

		_percent = GetterUtil.getInteger(tuple.getObject(0));
	}

	public void start(HttpServletRequest httpServletRequest) {
		start(httpServletRequest.getSession());
	}

	public void start(HttpSession session) {
		initialize(session);

		setPercent(1);
	}

	public void start(PortletRequest portletRequest) {
		start(portletRequest.getPortletSession());
	}

	public void start(PortletSession portletSession) {
		initialize(portletSession);

		setPercent(1);
	}

	private int _percent;
	private final Map<Integer, Tuple> _progress = new HashMap<>();
	private final String _progressId;
	private int _status = ProgressStatusConstants.PREPARED;

}