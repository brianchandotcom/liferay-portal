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

package com.liferay.portal.tools.rest.builder;

/**
 * @author Peter Shin
 */
public class RESTBuilderArgs {

	public static final String API_DIR_NAME = "../rest-api/src/main/java";

	public static final String API_PACKAGE_PATH = "com.example.sample";

	public static final String AUTHOR = "John Doe";

	public static final String INPUT_FILE_NAME = "rest.yaml";

	public String getApiDirName() {
		return _apiDirName;
	}

	public String getApiPackagePath() {
		return _apiPackagePath;
	}

	public String getAuthor() {
		return _author;
	}

	public String getInputFileName() {
		return _inputFileName;
	}

	public void setApiDirName(String apiDirName) {
		_apiDirName = apiDirName;
	}

	public void setApiPackagePath(String apiPackagePath) {
		_apiPackagePath = apiPackagePath;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public void setInputFileName(String inputFileName) {
		_inputFileName = inputFileName;
	}

	private String _apiDirName = API_DIR_NAME;
	private String _apiPackagePath = API_PACKAGE_PATH;
	private String _author = AUTHOR;
	private String _inputFileName = INPUT_FILE_NAME;

}