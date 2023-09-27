/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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