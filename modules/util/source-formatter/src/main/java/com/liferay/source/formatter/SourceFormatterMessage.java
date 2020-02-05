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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.InputStream;

import java.util.List;
import java.util.Objects;

import org.dom4j.Document;
import org.dom4j.Element;

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
		String checkSuperclassName, String markdownFileName, int lineNumber) {

		_fileName = fileName;
		_message = message;
		_checkType = checkType;
		_checkName = checkName;
		_checkSuperclassName = checkSuperclassName;
		_markdownFileName = markdownFileName;
		_lineNumber = lineNumber;
	}

	public SourceFormatterMessage(
		String fileName, String message, int lineNumber) {

		this(fileName, message, null, lineNumber);
	}

	public SourceFormatterMessage(
		String fileName, String message, String markdownFileName,
		int lineNumber) {

		this(fileName, message, null, null, null, markdownFileName, lineNumber);
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
		if (_markdownFileName != null) {
			return _OLD_DOCUMENTATION_URL + _markdownFileName;
		}

		String markdownFilePath = _getMarkdownFilePath(_checkName);

		if (markdownFilePath != null) {
			return markdownFilePath;
		}

		markdownFilePath = _getMarkdownFilePath(_checkSuperclassName);

		if (markdownFilePath != null) {
			return markdownFilePath;
		}

		if (_checkType.equals(CheckType.CHECKSTYLE)) {
			return _getCheckstyleDocumentationURLString(_checkName);
		}

		return null;
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

		String documentationURLString = getDocumentationURLString();

		if (documentationURLString != null) {
			sb.append(", see ");
			sb.append(documentationURLString);
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

	private String _getCheckstyleDocumentationURLString(
		Element element, String checkName) {

		if (checkName.equals(element.attributeValue("name"))) {
			for (Element propertyElement :
					(List<Element>)element.elements("property")) {

				if (Objects.equals(
						propertyElement.attributeValue("name"),
						"documentationLocation")) {

					return SourceFormatterUtil.
						CHECKSTYLE_DOCUMENTATION_URL_BASE +
							propertyElement.attributeValue("value");
				}
			}
		}

		for (Element moduleElement :
				(List<Element>)element.elements("module")) {

			String checkstyleURLFilePath = _getCheckstyleDocumentationURLString(
				moduleElement, checkName);

			if (checkstyleURLFilePath != null) {
				return checkstyleURLFilePath;
			}
		}

		return null;
	}

	private String _getCheckstyleDocumentationURLString(String checkName) {
		try {
			ClassLoader classLoader =
				SourceFormatterMessage.class.getClassLoader();

			Document document = SourceUtil.readXML(
				StringUtil.read(
					classLoader.getResourceAsStream("checkstyle.xml")));

			return _getCheckstyleDocumentationURLString(
				document.getRootElement(), checkName);
		}
		catch (Exception exception) {
			return null;
		}
	}

	private String _getMarkdownFilePath(String checkName) {
		String markdownFileName = SourceFormatterUtil.getMarkdownFileName(
			checkName);

		ClassLoader classLoader = SourceFormatterMessage.class.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"documentation/checks/" + markdownFileName);

		if (inputStream != null) {
			return _DOCUMENTATION_URL + markdownFileName;
		}

		return null;
	}

	private static final String _DOCUMENTATION_URL =
		"https://github.com/liferay/liferay-portal/blob/master/modules/util" +
			"/source-formatter/src/main/resources/documentation/checks/";

	private static final String _OLD_DOCUMENTATION_URL =
		"https://github.com/liferay/liferay-portal/blob/master/modules/util" +
			"/source-formatter/documentation/";

	private final String _checkName;
	private final String _checkSuperclassName;
	private final CheckType _checkType;
	private final String _fileName;
	private final int _lineNumber;
	private final String _markdownFileName;
	private final String _message;

}