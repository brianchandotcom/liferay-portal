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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class LoggerElement {

	public LoggerElement() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");

		long time = System.currentTimeMillis();

		while (true) {
			String id = simpleDateFormat.format(new Date(time++));

			if (!_usedIds.contains(id)) {
				_usedIds.add(id);

				_id = id;

				break;
			}
		}
	}

	public LoggerElement(String id) {
		_id = id;

		if (_isWrittenToLogger()) {
			String className = LoggerUtil.getClassName(this);

			if (Validator.isNotNull(className)) {
				_className = className;
			}

			String name = LoggerUtil.getName(this);

			if (Validator.isNotNull(name)) {
				_name = name;
			}

			String text = LoggerUtil.getText(this);

			if (Validator.isNotNull(text)) {
				_text = text;
			}
		}
	}

	public void addChildLoggerElement(LoggerElement childLoggerElement) {
		_childLoggerElements.add(childLoggerElement);

		if (_isWrittenToLogger()) {
			LoggerUtil.addChildLoggerElement(this, childLoggerElement);

			childLoggerElement.writeChildLoggerElements();
		}
	}

	public List<String> getAttributeNames() {
		List<String> attributeNames = new ArrayList<>();

		for (String attributeName : _attributes.keySet()) {
			attributeNames.add(attributeName);
		}

		return attributeNames;
	}

	public String getAttributeValue(String key) {
		return _attributes.get(key);
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

	public LoggerElement loggerElement(String name) {
		List<LoggerElement> loggerElements = loggerElements(name);

		if (!loggerElements.isEmpty()) {
			return loggerElements.get(0);
		}

		return null;
	}

	public List<LoggerElement> loggerElements() {
		return _childLoggerElements;
	}

	public List<LoggerElement> loggerElements(String name) {
		List<LoggerElement> childLoggerElements = new ArrayList<>();

		for (LoggerElement childLoggerElement : _childLoggerElements) {
			if (Validator.equals(childLoggerElement.getName(), name)) {
				childLoggerElements.add(childLoggerElement);
			}
		}

		return childLoggerElements;
	}

	public void setAttribute(String attributeName, String attributeValue) {
		_attributes.put(attributeName, attributeValue);

		if (_isWrittenToLogger()) {
			LoggerUtil.setAttribute(this, attributeName, attributeValue);
		}
	}

	public void setClassName(String className) {
		_className = className;

		if (_isWrittenToLogger()) {
			LoggerUtil.setClassName(this);
		}
	}

	public void setID(String id) {
		_id = id;

		if (_isWrittenToLogger()) {
			LoggerUtil.setID(this);
		}
	}

	public void setName(String name) {
		_name = name;

		if (_isWrittenToLogger()) {
			LoggerUtil.setName(this);
		}
	}

	public void setText(String text) {
		_text = text;

		if (_isWrittenToLogger()) {
			LoggerUtil.setText(this);
		}
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

	public void writeChildLoggerElements() {
		if (_isWrittenToLogger()) {
			for (LoggerElement childLoggerElement : _childLoggerElements) {
				LoggerUtil.addChildLoggerElement(this, childLoggerElement);

				childLoggerElement.writeChildLoggerElements();
			}
		}
	}

	private boolean _isWrittenToLogger() {
		if (LoggerUtil.isWrittenToLogger(this)) {
			return true;
		}

		return false;
	}

	private static final Set<String> _usedIds = new HashSet<>();

	private final Map<String, String> _attributes = new HashMap<>();
	private final List<LoggerElement> _childLoggerElements = new ArrayList<>();
	private String _className = "";
	private String _id;
	private String _name = "div";
	private String _text = "";

}