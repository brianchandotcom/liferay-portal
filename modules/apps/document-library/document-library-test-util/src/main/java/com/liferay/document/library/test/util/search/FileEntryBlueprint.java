/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.test.util.search;

import java.io.InputStream;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wade Cao
 */
public class FileEntryBlueprint {

	public void addAttributes(Map<String, Serializable> attributes) {
		_attributes.putAll(attributes);
	}

	public String[] getAssetTagNames() {
		return _assetTagNames;
	}

	public Map<String, Serializable> getAttributes() {
		return _attributes;
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
	private final Map<String, Serializable> _attributes = new HashMap<>();
	private String _fileName;
	private long _groupId;
	private InputStream _inputStream;
	private String _title;
	private Long _userId;

}