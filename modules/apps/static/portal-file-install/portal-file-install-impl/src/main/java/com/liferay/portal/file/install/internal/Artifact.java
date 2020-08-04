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

	public void setFileInstaller(FileInstaller fileInstaller) {
		_fileInstaller = fileInstaller;
	}

	public void setPath(File file) {
		_file = file;
	}

	private long _bundleId = -1;
	private long _checksum;
	private File _file;
	private FileInstaller _fileInstaller;

}