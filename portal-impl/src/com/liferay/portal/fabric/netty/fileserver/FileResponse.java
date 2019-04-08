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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.process.PathHolder;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

import java.nio.file.Path;

/**
 * @author Shuyang Zhou
 */
public class FileResponse implements Serializable {

	public static final long FILE_NOT_FOUND = 0;

	public static final long FILE_NOT_MODIFIED = -1;

	public FileResponse(
		Path path, long size, long lastModifiedTime, boolean folder) {

		_size = size;
		_lastModifiedTime = lastModifiedTime;
		_folder = folder;

		_pathHolder = new PathHolder(path);
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

		if ((_folder == fileResponse._folder) &&
			(_lastModifiedTime == fileResponse._lastModifiedTime) &&
			_pathHolder.equals(fileResponse._pathHolder) &&
			(_size == fileResponse._size)) {

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
		return _pathHolder.getPath();
	}

	public long getSize() {
		return _size;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _folder);

		hash = HashUtil.hash(hash, _lastModifiedTime);
		hash = HashUtil.hash(hash, _pathHolder);
		hash = HashUtil.hash(hash, _size);

		return hash;
	}

	public boolean isFileNotFound() {
		if (_size == FILE_NOT_FOUND) {
			return true;
		}

		return false;
	}

	public boolean isFileNotModified() {
		if (_size == FILE_NOT_MODIFIED) {
			return true;
		}

		return false;
	}

	public boolean isFolder() {
		return _folder;
	}

	public void setLocalFile(Path localFile) {
		_localFile = localFile;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler((_size > 0) ? 11 : 10);

		sb.append("{folder=");
		sb.append(_folder);
		sb.append(", lastModifiedTime=");
		sb.append(_lastModifiedTime);
		sb.append(", localFile=");
		sb.append(_localFile);
		sb.append(", pathHolder=");
		sb.append(_pathHolder);

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

		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private final boolean _folder;
	private final long _lastModifiedTime;
	private transient Path _localFile;
	private final PathHolder _pathHolder;
	private final long _size;

}