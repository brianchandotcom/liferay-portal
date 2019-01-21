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

package com.liferay.document.library.test.util.search;

import java.io.InputStream;

/**
 * @author Wade Cao
 */
public class FileEntryBlueprint {

	public String[] getAssetTagNames() {
		return _assetTagNames;
	}

	public String getFileName() {
		return _fileName;
	}

	public long getGroupId() {
		return _groupId;
	}

	public InputStream getInputStream() {
		return _inputStream;
	}

	public String getTitle() {
		return _title;
	}

	public Long getUserId() {
		return _userId;
	}

	protected void setAssetTagNames(String[] assetTagNames) {
		_assetTagNames = assetTagNames;
	}

	protected void setFileName(String fileName) {
		_fileName = fileName;
	}

	protected void setGroupId(long groupId) {
		_groupId = groupId;
	}

	protected void setInputStream(InputStream inputStream) {
		_inputStream = inputStream;
	}

	protected void setTitle(String title) {
		_title = title;
	}

	protected void setUserId(Long userId) {
		_userId = userId;
	}

	private String[] _assetTagNames;
	private String _fileName;
	private long _groupId;
	private InputStream _inputStream;
	private String _title;
	private Long _userId;

}