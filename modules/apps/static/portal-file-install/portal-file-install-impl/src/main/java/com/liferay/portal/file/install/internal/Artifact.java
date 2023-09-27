/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.internal;

import com.liferay.portal.file.install.FileInstaller;

import java.io.File;

/**
 * @author Matthew Tambara
 */
public class Artifact {

	public long getBundleId() {
		return _bundleId;
	}

	public long getChecksum() {
		return _checksum;
	}

	public File getFile() {
		return _file;
	}

	public FileInstaller getFileInstaller() {
		return _fileInstaller;
	}

	public void setBundleId(long bundleId) {
		_bundleId = bundleId;
	}

	public void setChecksum(long checksum) {
		_checksum = checksum;
	}

	public void setFile(File file) {
		_file = file;
	}

	public void setFileInstaller(FileInstaller fileInstaller) {
		_fileInstaller = fileInstaller;
	}

	private long _bundleId = -1;
	private long _checksum;
	private File _file;
	private FileInstaller _fileInstaller;

}