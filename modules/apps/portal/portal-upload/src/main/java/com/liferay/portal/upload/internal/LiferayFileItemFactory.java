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

package com.liferay.portal.upload.internal;

import java.io.File;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayFileItemFactory extends DiskFileItemFactory {

	public LiferayFileItemFactory(
		File tempDir, int sizeThreshold, String encoding) {

		_tempDir = tempDir;

		if (sizeThreshold > 0) {
			_sizeThreshold = sizeThreshold;
		}
		else {
			_sizeThreshold = _DEFAULT_SIZE;
		}

		_encoding = encoding;
	}

	@Override
	public LiferayFileItem createItem(
		String fieldName, String contentType, boolean formField,
		String fileName) {

		return new LiferayFileItem(
			fieldName, contentType, formField, fileName, _sizeThreshold,
			_tempDir, _encoding);
	}

	private static final int _DEFAULT_SIZE = 1024;

	private final String _encoding;
	private final int _sizeThreshold;
	private final File _tempDir;

}