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

import java.io.File;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class AntivirusScannerWrapper implements AntivirusScanner {

	public AntivirusScannerWrapper(AntivirusScanner antivirusScanner) {
		_originalAntivirusScanner = antivirusScanner;
		_antivirusScanner = antivirusScanner;
	}

	@Override
	public boolean isActive() {
		return _antivirusScanner.isActive();
	}

	@Override
	public void scan(byte[] bytes) throws AntivirusScannerException {
		_antivirusScanner.scan(bytes);
	}

	@Override
	public void scan(File file) throws AntivirusScannerException {
		_antivirusScanner.scan(file);
	}

	@Override
	public void scan(InputStream inputStream) throws AntivirusScannerException {
		_antivirusScanner.scan(inputStream);
	}

	public void setAntivirusScanner(AntivirusScanner antivirusScanner) {
		if (antivirusScanner == null) {
			_antivirusScanner = _originalAntivirusScanner;
		}
		else {
			_antivirusScanner = antivirusScanner;
		}
	}

	private AntivirusScanner _antivirusScanner;
	private final AntivirusScanner _originalAntivirusScanner;

}