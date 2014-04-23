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

package com.liferay.portal.json.model;

import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Igor Spasic
 */
public class FileData {

	public FileData(File file) {
		byte[] fileContent;

		try {
			fileContent = FileUtil.getBytes(file);
		}
		catch (IOException ex) {
			fileContent = null;
		}

		_content = Base64.encode(fileContent);
		_name = file.getName();
		_size = file.length();
	}

	public String getContent() {
		return _content;
	}

	public String getName() {
		return _name;
	}

	public long getSize() {
		return _size;
	}

	public void setContent(String _content) {
		this._content = _content;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public void setSize(long _size) {
		this._size = _size;
	}

	private String _content;
	private String _name;
	private long _size;

}