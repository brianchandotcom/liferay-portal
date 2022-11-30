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

package com.liferay.lang.sanitizer;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.File;

/**
 * @author Seiphon Wang
 */
public class LangSanitizerMessage {

	public LangSanitizerMessage(
		String key, File file, String originalContent, String santizedContent) {

		_key = key;
		_file = file;
		_originalContent = originalContent;
		_santizedContent = santizedContent;
	}

	public File getFile() {
		return _file;
	}

	public String getKey() {
		return _key;
	}

	public String getOriginalContent() {
		return _originalContent;
	}

	public String getSantizedContent() {
		return _santizedContent;
	}

	public void setFile(File file) {
		_file = file;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setOriginalContent(String originalContent) {
		_originalContent = originalContent;
	}

	public void setSantizedContent(String santizedContent) {
		_santizedContent = santizedContent;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"File: ", _file.getAbsolutePath(), System.lineSeparator(),
			"\tKey: ", _key, System.lineSeparator(), "\toriginal Content: ",
			_originalContent, System.lineSeparator(), "\tsantized Content: ",
			_santizedContent);
	}

	private File _file;
	private String _key;
	private String _originalContent;
	private String _santizedContent;

}