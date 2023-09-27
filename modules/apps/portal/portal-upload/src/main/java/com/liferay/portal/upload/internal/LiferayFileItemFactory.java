/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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