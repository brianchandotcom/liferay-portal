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

package com.liferay.poshi.runner.logger;

import com.liferay.poshi.runner.util.Validator;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class LoggerElement {

	public LoggerElement() {
		try {
			Thread.sleep(1);
		}
		catch (Exception e) {
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");

		_id = simpleDateFormat.format(new Date());
	}

	public LoggerElement(String id) {
		_id = id;

		_writtenToLogger = true;
	}

	public void addChildLoggerElement(LoggerElement childLoggerElement) {
		_childLoggerElements.add(childLoggerElement);

		if (_writtenToLogger) {
			LoggerUtil.addChildLoggerElement(this, childLoggerElement);

			childLoggerElement.setWrittenToLogger(true);
		}
	}

	public String getClassName() {
		return _className;
	}

	public String getID() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getText() {
		return _text;
	}

	public void setClassName(String className) {
		_className = className;

		if (_writtenToLogger) {
			LoggerUtil.setClassName(this);
		}
	}

	public void setID(String id) {
		_id = id;

		if (_writtenToLogger) {
			LoggerUtil.setID(this);
		}
	}

	public void setName(String name) {
		_name = name;
	}

	public void setText(String text) {
		_text = text;

		if (_writtenToLogger) {
			LoggerUtil.setText(this);
		}
	}

	public void setWrittenToLogger(boolean writtenToLogger) {
		_writtenToLogger = writtenToLogger;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<");
		sb.append(_name);

		if (Validator.isNotNull(_className)) {
			sb.append(" class=\"");
			sb.append(_className);
			sb.append("\"");
		}

		sb.append(" id=\"");
		sb.append(_id);
		sb.append("\"");

		boolean hasChildren = _childLoggerElements.size() > 0;
		boolean hasText = Validator.isNotNull(_text);

		if (hasChildren || hasText) {
			sb.append(">\n");

			if (hasText) {
				sb.append(_text);
			}

			if (hasChildren) {
				for (LoggerElement childLoggerElement : _childLoggerElements) {
					sb.append(childLoggerElement.toString());
				}
			}

			sb.append("</");
			sb.append(_name);
			sb.append(">\n");
		}
		else {
			sb.append(" />\n");
		}

		return sb.toString();
	}

	private final List<LoggerElement> _childLoggerElements = new ArrayList<>();
	private String _className = "";
	private String _id;
	private String _name = "div";
	private String _text = "";
	private boolean _writtenToLogger = false;

}