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
public class FileRequest implements Serializable {

	public FileRequest(
		Path path, long lastModifiedTime, boolean deleteAfterFetch) {

		_lastModifiedTime = lastModifiedTime;
		_deleteAfterFetch = deleteAfterFetch;

		_pathHolder = new PathHolder(path);
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

		if ((_deleteAfterFetch == fileRequest._deleteAfterFetch) &&
			(_lastModifiedTime == fileRequest._lastModifiedTime) &&
			_pathHolder.equals(fileRequest._pathHolder)) {

			return true;
		}

		return false;
	}

	public long getLastModifiedTime() {
		return _lastModifiedTime;
	}

	public Path getPath() {
		return _pathHolder.getPath();
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _deleteAfterFetch);

		hash = HashUtil.hash(hash, _lastModifiedTime);
		hash = HashUtil.hash(hash, _pathHolder);

		return hash;
	}

	public boolean isDeleteAfterFetch() {
		return _deleteAfterFetch;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{deleteAfterFetch=");
		sb.append(_deleteAfterFetch);
		sb.append(", lastModifiedTime=");
		sb.append(_lastModifiedTime);
		sb.append(", pathHolder=");
		sb.append(_pathHolder);
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private final boolean _deleteAfterFetch;
	private final long _lastModifiedTime;
	private final PathHolder _pathHolder;

}