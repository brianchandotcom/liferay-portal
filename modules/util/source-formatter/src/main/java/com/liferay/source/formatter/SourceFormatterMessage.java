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

package com.liferay.source.formatter;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.util.CheckType;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterMessage
	implements Comparable<SourceFormatterMessage> {

	public SourceFormatterMessage(String fileName, String message) {
		this(fileName, message, null, null, null, -1);
	}

	public SourceFormatterMessage(
		String fileName, String message, CheckType checkType, String checkName,
		String documentationURLString, int lineNumber) {

		_fileName = fileName;
		_message = message;
		_checkType = checkType;
		_checkName = checkName;
		_documentationURLString = documentationURLString;
		_lineNumber = lineNumber;
	}

	@Override
	public int compareTo(SourceFormatterMessage sourceFormatterMessage) {
		if (!_fileName.equals(sourceFormatterMessage.getFileName())) {
			return _fileName.compareTo(sourceFormatterMessage.getFileName());
		}

		if (_lineNumber != sourceFormatterMessage.getLineNumber()) {
			return _lineNumber - sourceFormatterMessage.getLineNumber();
		}

		return _message.compareTo(sourceFormatterMessage.getMessage());
	}

	public String getCheckName() {
		return _checkName;
	}

	public CheckType getCheckType() {
		return _checkType;
	}

	public String getDocumentationURLString() {
		return _documentationURLString;
	}

	public String getFileName() {
		return _fileName;
	}

	public int getLineNumber() {
		return _lineNumber;
	}

	public String getMessage() {
		return _message;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append(_message);

		if (_documentationURLString != null) {
			sb.append(", see ");
			sb.append(_documentationURLString);
		}

		sb.append(": ");
		sb.append(_fileName);

		if (_lineNumber > -1) {
			sb.append(StringPool.SPACE);
			sb.append(_lineNumber);
		}

		if (_checkName != null) {
			sb.append(CharPool.SPACE);
			sb.append(CharPool.OPEN_PARENTHESIS);

			if (_checkType != null) {
				sb.append(_checkType.getValue());
				sb.append(CharPool.COLON);
			}

			sb.append(_checkName);
			sb.append(CharPool.CLOSE_PARENTHESIS);
		}

		return sb.toString();
	}

	private final String _checkName;
	private final CheckType _checkType;
	private final String _documentationURLString;
	private final String _fileName;
	private final int _lineNumber;
	private final String _message;

}