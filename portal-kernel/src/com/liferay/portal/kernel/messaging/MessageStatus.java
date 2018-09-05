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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class MessageStatus implements Serializable {

	public long getDuration() {
		return _endTime - _startTime;
	}

	public String getExceptionMessage() {
		return _exceptionMessage;
	}

	public String getExceptionStackTrace() {
		return _exceptionStackTrace;
	}

	public Object getPayload() {
		return _payload;
	}

	public boolean hasException() {
		if (_exceptionStackTrace != null) {
			return true;
		}

		return false;
	}

	public void setException(Exception e) {
		_exceptionMessage = e.getMessage();
		_exceptionStackTrace = StackTraceUtil.getStackTrace(e);
	}

	public void setPayload(Object payload) {
		_payload = payload;
	}

	public void startTimer() {
		_startTime = System.currentTimeMillis();
	}

	public void stopTimer() {
		_endTime = System.currentTimeMillis();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{startTime=");
		sb.append(_startTime);
		sb.append(", endTime=");
		sb.append(_endTime);
		sb.append(", payload=");
		sb.append(_payload);
		sb.append(", errorMessage=");
		sb.append(_exceptionMessage);
		sb.append(", errorStackTrace=");
		sb.append(_exceptionStackTrace);
		sb.append("}");

		return sb.toString();
	}

	private long _endTime;
	private String _exceptionMessage;
	private String _exceptionStackTrace;
	private Object _payload;
	private long _startTime;

}