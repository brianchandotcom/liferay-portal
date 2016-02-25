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

package com.liferay.document.library.kernel.antivirus;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Michael C. Han
 */
public abstract class BaseFileAntivirusScanner implements AntivirusScanner {

	@Override
	public boolean isActive() {
		return _ACTIVE;
	}

	@Override
	public void scan(byte[] bytes) throws AntivirusScannerException {
		File file = null;

		try {
			file = FileUtil.createTempFile(_ANTIVIRUS_EXTENSION);

			FileUtil.write(file, bytes);

			scan(file);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	@Override
	public void scan(InputStream inputStream) throws AntivirusScannerException {
		File file = null;

		try {
			file = FileUtil.createTempFile(_ANTIVIRUS_EXTENSION);

			FileUtil.write(file, inputStream);

			scan(file);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	private static final boolean _ACTIVE = true;

	private static final String _ANTIVIRUS_EXTENSION = "avs";

}