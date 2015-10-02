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

import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class LoggerElement {

	public LoggerElement() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmssSSS");

		long time = System.currentTimeMillis();

		while (true) {
			String id = "id" + simpleDateFormat.format(new Date(time++));

			if (!_usedIds.contains(id)) {
				_usedIds.add(id);

				_id = id;

				break;
			}
		}
	}

	public LoggerElement(String id) {
		_id = id;

		if (Validator.isNotNull(id) && LoggerUtil.isWrittenToLogger(this)) {
			_writtenToLogger = true;
		}
	}

	public void addChildLoggerElement(LoggerElement childLoggerElement) {
		_childLoggerElements.add(childLoggerElement);

		if (_writtenToLogger) {
			LoggerUtil.addChildLoggerElement(this, childLoggerElement);

			childLoggerElement.setWrittenToLogger(true);

			childLoggerElement.writeChildLoggerElements();
		}
	}

	public void addClassName(String className) {
		setClassName(_className + " " + className);
	}

	public LoggerElement copy() {
		LoggerElement loggerElement = new LoggerElement();

		List<LoggerElement> childLoggerElements = loggerElements();

		for (LoggerElement childLoggerElement : childLoggerElements) {
			loggerElement.addChildLoggerElement(childLoggerElement.copy());
		}

		List<String> attributeNames = getAttributeNames();

		for (String attributeName : attributeNames) {
			loggerElement.setAttribute(
				attributeName, getAttributeValue(attributeName));
		}

		loggerElement.setClassName(getClassName());
		loggerElement.setName(getName());
		loggerElement.setText(getText());

		return loggerElement;
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

	public void removeChildLoggerElement(LoggerElement childLoggerElement) {
		_childLoggerElements.remove(childLoggerElement);
	}

	public void removeChildLoggerElements(String name) {
		List<LoggerElement> childLoggerElements = new ArrayList<>();

		for (LoggerElement childLoggerElement : _childLoggerElements) {
			if (Validator.equals(childLoggerElement.getName(), name)) {
				childLoggerElements.add(childLoggerElement);
			}
		}

		for (LoggerElement childLoggerElement : childLoggerElements) {
			_childLoggerElements.remove(childLoggerElement);
		}
	}

	public void removeClassName(String className) {
		String[] classNames = StringUtil.split(_className, " ");

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < classNames.length; i++) {
			if (Validator.isNull(classNames[i])) {
				continue;
			}

			if (Validator.equals(classNames[i], className)) {
				continue;
			}

			sb.append(classNames[i]);
			sb.append(" ");
		}

		setClassName(sb.toString());
	}

	public void setAttribute(String attributeName, String attributeValue) {
		_attributes.put(attributeName, attributeValue);

		if (_writtenToLogger) {
			LoggerUtil.setAttribute(this, attributeName, attributeValue);
		}
	}

	public void setClassName(String className) {
		_className = _fixClassName(className);

		if (_writtenToLogger) {
			LoggerUtil.setClassName(this);
		}
	}

	public void setID(String id) {
		String oldID = _id;

		_id = id;

		if (_writtenToLogger) {
			LoggerUtil.setID(oldID, id);
		}
	}

	public void setName(String name) {
		_name = name;

		if (_writtenToLogger) {
			LoggerUtil.setName(this);
		}
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

		for (Entry<String, String> entry : _attributes.entrySet()) {
			sb.append(" ");
			sb.append(entry.getKey());
			sb.append("=\"");
			sb.append(entry.getValue());
			sb.append("\"");
		}

		if (Validator.isNotNull(_className)) {
			sb.append(" class=\"");
			sb.append(_className);
			sb.append("\"");
		}

		if (Validator.isNotNull(_id)) {
			sb.append(" id=\"");
			sb.append(_id);
			sb.append("\"");
		}

		sb.append(">");

		boolean hasChildren = _childLoggerElements.size() > 0;
		boolean hasText = Validator.isNotNull(_text);

		if (hasChildren || hasText) {
			if (hasText) {
				sb.append(_text);
			}

			if (hasChildren) {
				for (LoggerElement childLoggerElement : _childLoggerElements) {
					sb.append(childLoggerElement.toString());
				}
			}
		}

		sb.append("</");
		sb.append(_name);
		sb.append(">");

		return sb.toString();
	}

	public void writeChildLoggerElements() {
		if (_writtenToLogger) {
			for (LoggerElement childLoggerElement : _childLoggerElements) {
				LoggerUtil.addChildLoggerElement(this, childLoggerElement);

				childLoggerElement.setWrittenToLogger(true);

				childLoggerElement.writeChildLoggerElements();
			}
		}
	}

	private String _fixClassName(String className) {
		String[] classNames = StringUtil.split(className, " ");

		Arrays.sort(classNames);

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < classNames.length; i++) {
			if (Validator.isNull(classNames[i])) {
				continue;
			}

			sb.append(classNames[i]);
			sb.append(" ");
		}

		className = sb.toString();

		return className.trim();
	}

	private static final Set<String> _usedIds = new HashSet<>();

	private final Map<String, String> _attributes = new HashMap<>();
	private final List<LoggerElement> _childLoggerElements = new ArrayList<>();
	private String _className = "";
	private String _id;
	private String _name = "div";
	private String _text = "";
	private boolean _writtenToLogger;

}