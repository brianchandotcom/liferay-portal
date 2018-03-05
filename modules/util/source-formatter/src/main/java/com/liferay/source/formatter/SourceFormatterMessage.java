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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.source.formatter.util.CheckType;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterMessage
	implements Comparable<SourceFormatterMessage> {

	public SourceFormatterMessage(String fileName, String message) {
		this(fileName, message, -1);
	}

	public SourceFormatterMessage(
		String fileName, String message, CheckType checkType, String checkName,
		String markdownFileName, int lineCount) {

		_fileName = fileName;
		_message = message;
		_checkType = checkType;
		_checkName = checkName;
		_markdownFileName = markdownFileName;
		_lineCount = lineCount;
	}

	public SourceFormatterMessage(
		String fileName, String message, int lineCount) {

		this(fileName, message, null, lineCount);
	}

	public SourceFormatterMessage(
		String fileName, String message, String markdownFileName,
		int lineCount) {

		this(fileName, message, null, null, markdownFileName, -1);
	}

	@Override
	public int compareTo(SourceFormatterMessage sourceFormatterMessage) {
		if (!_fileName.equals(sourceFormatterMessage.getFileName())) {
			return _fileName.compareTo(sourceFormatterMessage.getFileName());
		}

		if (_lineCount != sourceFormatterMessage.getLineCount()) {
			return _lineCount - sourceFormatterMessage.getLineCount();
		}

		return _message.compareTo(sourceFormatterMessage.getMessage());
	}

	public String getCheckName() {
		return _checkName;
	}

	public CheckType getCheckType() {
		return _checkType;
	}

	public String getFileName() {
		return _fileName;
	}

	public int getLineCount() {
		return _lineCount;
	}

	public String getMarkdownFilePath() {
		if (_markdownFileName == null) {
			return null;
		}

		return _DOCUMENTATION_URL + _markdownFileName;
	}

	public String getMessage() {
		return _message;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(14);

		sb.append(_message);

		if (_markdownFileName != null) {
			sb.append(", see ");
			sb.append(_DOCUMENTATION_URL);
			sb.append(_markdownFileName);
		}

		sb.append(": ");
		sb.append(_fileName);

		if (_lineCount > -1) {
			sb.append(StringPool.SPACE);
			sb.append(_lineCount);
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

	private static final String _DOCUMENTATION_URL =
		"https://github.com/liferay/liferay-portal/blob/master/modules/util" +
			"/source-formatter/documentation/";

	private final String _checkName;
	private final CheckType _checkType;
	private final String _fileName;
	private final int _lineCount;
	private final String _markdownFileName;
	private final String _message;

}