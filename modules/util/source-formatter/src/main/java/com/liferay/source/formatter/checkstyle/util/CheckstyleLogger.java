/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.check.util.SourceUtil;
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
	}

	@Override
	public void addError(AuditEvent auditEvent) {
		addError(auditEvent, getRelativizedFileName(auditEvent));
	}

	public void clearSourceFormatterMessages() {
		_sourceFormatterMessages.clear();
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
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private String _getDocumentationURLString(
		String checkName, String simpleCheckName) {

		if (!checkName.startsWith("com.liferay.")) {
			return _getCheckstyleDocumentationURLString(simpleCheckName);
		}

		ClassLoader classLoader = CheckstyleLogger.class.getClassLoader();

		try {
			return SourceFormatterUtil.getDocumentationURLString(
				classLoader.loadClass(checkName));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckstyleLogger.class);

	private static final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new TreeSet<>();

	private final String _baseDirName;

}