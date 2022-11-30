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

/**
 * @author Seiphon Wang
 */
public class LangSanitizerArges {

	public static final String BASE_DIR_NAME = "./";

	public static final String OUTPUT_KEY_SANTIZED_FILES =
		"lang.santized.files";

	public static final boolean SANITIZE_LOCAL_CHANGES = false;

	public String getBaseDirName() {
		return _baseDirName;
	}

	public void setBaseDirName(String baseDirName) {
		_baseDirName = baseDirName;
	}

	private String _baseDirName = BASE_DIR_NAME;

}