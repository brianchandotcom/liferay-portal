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
public class FileRequest implements Serializable {

	public FileRequest(
		Path path, long lastModifiedTime, boolean deleteAfterFetch) {

		path = path.toAbsolutePath();

		_path = path.toString();
		_lastModifiedTime = lastModifiedTime;
		_deleteAfterFetch = deleteAfterFetch;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FileRequest)) {
			return false;
		}

		FileRequest fileRequest = (FileRequest)obj;

		if (_path.equals(fileRequest._path) &&
			(_lastModifiedTime == fileRequest._lastModifiedTime) &&
			(_deleteAfterFetch == fileRequest._deleteAfterFetch)) {

			return true;
		}

		return false;
	}

	public long getLastModifiedTime() {
		return _lastModifiedTime;
	}

	public Path getPath() {
		return Paths.get(_path);
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _path);

		hash = HashUtil.hash(hash, _lastModifiedTime);
		hash = HashUtil.hash(hash, _deleteAfterFetch);

		return hash;
	}

	public boolean isDeleteAfterFetch() {
		return _deleteAfterFetch;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{path=");
		sb.append(_path);
		sb.append(", lastModifiedTime=");
		sb.append(_lastModifiedTime);
		sb.append(", deleteAfterFetch=");
		sb.append(_deleteAfterFetch);
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private final boolean _deleteAfterFetch;
	private final long _lastModifiedTime;
	private final String _path;

}