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

package com.liferay.portal.json.data;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Igor Spasic
 */
public class FileData {

	public FileData(File file) {
		byte[] bytes = null;

		try {
			bytes = FileUtil.getBytes(file);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}

			bytes = null;
		}

		_content = Base64.encode(bytes);

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

	public void setContent(String content) {
		_content = content;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSize(long size) {
		_size = size;
	}

	private static final Log _log = LogFactoryUtil.getLog(FileData.class);

	private String _content;
	private String _name;
	private long _size;

}