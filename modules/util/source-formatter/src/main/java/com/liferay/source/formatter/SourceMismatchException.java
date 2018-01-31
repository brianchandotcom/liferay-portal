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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import org.junit.Assert;

/**
 * @author André de Oliveira
 * @author Hugo Huijser
 */
public class SourceMismatchException extends PortalException {

	public SourceMismatchException(
		String fileName, String originalSource, String formattedSource) {

		_fileName = fileName;
		_originalSource = originalSource;
		_formattedSource = formattedSource;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getFormattedSource() {
		return _formattedSource;
	}

	@Override
	public String getMessage() {
		try {
			Assert.assertEquals(_fileName, _formattedSource, _originalSource);
		}
		catch (AssertionError ae) {
			String message = ae.getMessage();

			if (message.length() >= _MAX_MESSAGE_SIZE) {
				message =
					"Truncated message :\n" +
						message.substring(0, _MAX_MESSAGE_SIZE);
			}

			return message;
		}

		return StringPool.BLANK;
	}

	public String getOriginalSource() {
		return _originalSource;
	}

	private static final int _MAX_MESSAGE_SIZE = 10000;

	private final String _fileName;
	private final String _formattedSource;
	private final String _originalSource;

}