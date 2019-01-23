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

package com.liferay.change.tracking.internal.process.model;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class CTProcessLogEntryModel implements Serializable {

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

	public static class Builder {

		public CTProcessLogEntryModel build() {
			return new CTProcessLogEntryModel(this);
		}

		public Builder date(Date date) {
			_date = date;

			return this;
		}

		public Builder level(String level) {
			_level = level;

			return this;
		}

		public Builder messageKey(String messageKey) {
			_messageKey = messageKey;

			return this;
		}

		public Builder messageParameters(
			Map<String, Serializable> messageParameters) {

			_messageParameters = messageParameters;

			return this;
		}

		private Date _date;
		private String _level;
		private String _messageKey;
		private Map<String, Serializable> _messageParameters;

	}

	private CTProcessLogEntryModel(Builder builder) {
		_date = builder._date;
		_level = builder._level;
		_messageKey = builder._messageKey;
		_messageParameters = builder._messageParameters;
	}

	private final Date _date;
	private final String _level;
	private final String _messageKey;
	private final Map<String, Serializable> _messageParameters;

}