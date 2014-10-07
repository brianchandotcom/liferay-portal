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

package com.liferay.portal.fabric.netty.fileserver;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Shuyang Zhou
 */
public class FileResponse implements Serializable {

	public static final long FILE_NOT_FOUND = 0;

	public static final long FILE_NOT_MODIFIED = -1;

	public FileResponse(
		Path path, long size, long lastModifiedTime, boolean folder) {

		path = path.toAbsolutePath();

		_path = path.toString();
		_size = size;
		_lastModifiedTime = lastModifiedTime;
		_folder = folder;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FileResponse)) {
			return false;
		}

		FileResponse fileResponse = (FileResponse)obj;

		if (_path.equals(fileResponse._path) &&
			(_size == fileResponse._size) &&
			(_lastModifiedTime == fileResponse._lastModifiedTime) &&
			(_folder == fileResponse._folder)) {

			return true;
		}

		return false;
	}

	public long getLastModifiedTime() {
		return _lastModifiedTime;
	}

	public Path getLocalFile() {
		return _localFile;
	}

	public Path getPath() {
		return Paths.get(_path);
	}

	public long getSize() {
		return _size;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _path);

		hash = HashUtil.hash(hash, _size);
		hash = HashUtil.hash(hash, _lastModifiedTime);
		hash = HashUtil.hash(hash, _folder);

		return hash;
	}

	public boolean isFileNotFound() {
		return _size == FILE_NOT_FOUND;
	}

	public boolean isFileNotModified() {
		return _size == FILE_NOT_MODIFIED;
	}

	public boolean isFolder() {
		return _folder;
	}

	public void setLocalFile(Path localFile) {
		_localFile = localFile;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(_size > 0 ? 11 : 10);

		sb.append("{path=");
		sb.append(_path);
		sb.append(", isFolder=");
		sb.append(_folder);

		if (_size == FILE_NOT_FOUND) {
			sb.append(", status=File Not Found");
		}
		else if (_size == FILE_NOT_MODIFIED) {
			sb.append(", status=File Not Modified");
		}
		else {
			sb.append(", size=");
			sb.append(_size);
		}

		sb.append(", lastModifiedTime=");
		sb.append(_lastModifiedTime);
		sb.append(", localFile=");
		sb.append(_localFile);
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private final boolean _folder;
	private final long _lastModifiedTime;
	private transient Path _localFile;
	private final String _path;
	private final long _size;

}