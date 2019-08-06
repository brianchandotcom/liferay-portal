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

package com.liferay.change.tracking.internal.process.log;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class CTProcessLogEntry implements Serializable {

	public CTProcessLogEntry(
		Date date, String level, String messageKey,
		Map<String, Serializable> messageParameters) {

		_date = date;
		_level = level;
		_messageKey = messageKey;
		_messageParameters = messageParameters;
	}

	public Date getDate() {
		return _date;
	}

	public String getLevel() {
		return _level;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public Map<String, Serializable> getMessageParameters() {
		return _messageParameters;
	}

	private final Date _date;
	private final String _level;
	private final String _messageKey;
	private final Map<String, Serializable> _messageParameters;

}