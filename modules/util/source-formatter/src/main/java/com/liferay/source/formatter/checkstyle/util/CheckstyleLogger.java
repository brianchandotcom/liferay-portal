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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.util.CheckType;

import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;

import java.io.OutputStream;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Hugo Huijser
 */
public class CheckstyleLogger extends DefaultLogger {

	public CheckstyleLogger(
		OutputStream outputStream, boolean closeStreamsAfterUse,
		String baseDirName) {

		super(outputStream, closeStreamsAfterUse);

		_baseDirName = baseDirName;

		_sourceFormatterMessages.clear();
	}

	@Override
	public void addError(AuditEvent auditEvent) {
		String sourceName = StringUtil.extractLast(
			auditEvent.getSourceName(), CharPool.PERIOD);

		_sourceFormatterMessages.add(
			new SourceFormatterMessage(
				_getRelativizedFileName(auditEvent), auditEvent.getMessage(),
				CheckType.CHECKSTYLE, sourceName, null, auditEvent.getLine()));

		super.addError(auditEvent);
	}

	public Set<SourceFormatterMessage> getSourceFormatterMessages() {
		return _sourceFormatterMessages;
	}

	private Path _getAbsoluteNormalizedPath(String pathName) {
		Path path = Paths.get(pathName);

		path = path.toAbsolutePath();

		return path.normalize();
	}

	private String _getRelativizedFileName(AuditEvent auditEvent) {
		if (Validator.isNull(_baseDirName)) {
			return auditEvent.getFileName();
		}

		Path baseDirPath = _getAbsoluteNormalizedPath(_baseDirName);

		Path relativizedPath = baseDirPath.relativize(
			_getAbsoluteNormalizedPath(auditEvent.getFileName()));

		return _baseDirName +
			StringUtil.replace(
				relativizedPath.toString(), CharPool.BACK_SLASH,
				CharPool.SLASH);
	}

	private static final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new TreeSet<>();

	private final String _baseDirName;

}