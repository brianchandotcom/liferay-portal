/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.antivirus;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Michael C. Han
 */
public abstract class BaseInputStreamAntivirusScanner
	implements AntivirusScanner {

	@Override
	public boolean isActive() {
		return _ACTIVE;
	}

	@Override
	public void scan(File file)
		throws AntivirusScannerException, SystemException {

		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);

			scan(inputStream);
		}
		catch (FileNotFoundException fnfe) {
			throw new SystemException("Unable to scan file", fnfe);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	private static final boolean _ACTIVE = true;

}