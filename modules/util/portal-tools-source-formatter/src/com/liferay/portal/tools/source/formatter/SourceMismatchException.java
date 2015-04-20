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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.exception.PortalException;

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

	public String getOriginalSource() {
		return _originalSource;
	}

	private final String _fileName;
	private final String _formattedSource;
	private final String _originalSource;

}