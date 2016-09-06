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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterMessage
	implements Comparable<SourceFormatterMessage> {

	public SourceFormatterMessage(String fileName, String message) {
		this(fileName, message, -1);
	}

	public SourceFormatterMessage(
		String fileName, String message, int lineCount) {

		_fileName = fileName;
		_message = message;
		_lineCount = lineCount;
	}

	@Override
	public int compareTo(SourceFormatterMessage sourceFormatterMessage) {
		if (!_fileName.equals(sourceFormatterMessage.getFileName())) {
			return _fileName.compareTo(sourceFormatterMessage.getFileName());
		}

		if ((_lineCount != -1) ||
			(sourceFormatterMessage.getLineCount() != -1)) {

			return _lineCount - sourceFormatterMessage.getLineCount();
		}

		return _message.compareTo(sourceFormatterMessage.getMessage());
	}

	public String getFileName() {
		return _fileName;
	}

	public int getLineCount() {
		return _lineCount;
	}

	public String getMessage() {
		return _message;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append(_message);
		sb.append(": ");
		sb.append(_fileName);

		if (_lineCount > -1) {
			sb.append(StringPool.SPACE);
			sb.append(_lineCount);
		}

		return sb.toString();
	}

	private final String _fileName;
	private final int _lineCount;
	private final String _message;

}