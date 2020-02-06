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

package com.liferay.source.formatter.checkstyle.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class CheckstyleLogger extends DefaultLogger {

	public CheckstyleLogger(String baseDirName) {
		super(new UnsyncByteArrayOutputStream(), OutputStreamOptions.CLOSE);

		_baseDirName = baseDirName;

		_sourceFormatterMessages.clear();
	}

	@Override
	public void addError(AuditEvent auditEvent) {
		addError(auditEvent, getRelativizedFileName(auditEvent));
	}

	public Set<SourceFormatterMessage> getSourceFormatterMessages() {
		return _sourceFormatterMessages;
	}

	protected void addError(AuditEvent auditEvent, String fileName) {
		String checkName = auditEvent.getSourceName();

		String simpleCheckName = SourceFormatterUtil.getSimpleName(checkName);

		_sourceFormatterMessages.add(
			new SourceFormatterMessage(
				fileName, auditEvent.getMessage(), CheckType.CHECKSTYLE,
				simpleCheckName,
				_getDocumentationURLString(checkName, simpleCheckName),
				auditEvent.getLine()));

		super.addError(auditEvent);
	}

	protected String getRelativizedFileName(AuditEvent auditEvent) {
		if (Validator.isNull(_baseDirName)) {
			return auditEvent.getFileName();
		}

		Path baseDirPath = _getAbsoluteNormalizedPath(_baseDirName);

		Path relativizedPath = baseDirPath.relativize(
			_getAbsoluteNormalizedPath(auditEvent.getFileName()));

		String relativizedPathString = StringUtil.replace(
			relativizedPath.toString(), CharPool.BACK_SLASH, CharPool.SLASH);

		return _baseDirName + relativizedPathString;
	}

	private Path _getAbsoluteNormalizedPath(String pathName) {
		Path path = Paths.get(pathName);

		path = path.toAbsolutePath();

		return path.normalize();
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

	private String _getDocumentationURLString(
		String checkName, String simpleCheckName) {

		if (!checkName.startsWith("com.liferay.")) {
			return _getCheckstyleDocumentationURLString(simpleCheckName);
		}

		String markdownURLString = SourceFormatterUtil.getMarkdownURLString(
			simpleCheckName);

		if (markdownURLString != null) {
			return markdownURLString;
		}

		ClassLoader classLoader = CheckstyleLogger.class.getClassLoader();

		try {
			Class<?> checkClass = classLoader.loadClass(checkName);

			Class<?> superClass = checkClass.getSuperclass();

			return SourceFormatterUtil.getMarkdownURLString(
				superClass.getSimpleName());
		}
		catch (Exception exception) {
			return null;
		}
	}

	private static final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new TreeSet<>();

	private final String _baseDirName;

}