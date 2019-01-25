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

	public static final String API_DIR_NAME = "../sample-api/src/main/java";

	public static final String API_PACKAGE_PATH = "com.example.sample";

	public static final String APPLICATION_BASE_URI = "/sample";

	public static final String APPLICATION_CLASS_NAME = "SampleApplication";

	public static final String APPLICATION_NAME = "sample";

	public static final String AUTHOR = "Brian Wing Shun Chan";

	public static final String INPUT_FILE_NAME = "rest.yaml";

	public String getApiDirName() {
		return _apiDirName;
	}

	public String getApiPackagePath() {
		return _apiPackagePath;
	}

	public String getApplicationBaseURI() {
		return _applicationBaseURI;
	}

	public String getApplicationClassName() {
		return _applicationClassName;
	}

	public String getApplicationName() {
		return _applicationName;
	}

	public String getAuthor() {
		return _author;
	}

	public String getCopyrightFileName() {
		return _copyrightFileName;
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

	public void setApplicationBaseURI(String applicationBaseURI) {
		_applicationBaseURI = applicationBaseURI;
	}

	public void setApplicationClassName(String applicationClassName) {
		_applicationClassName = applicationClassName;
	}

	public void setApplicationName(String applicationName) {
		_applicationName = applicationName;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public void setCopyrightFileName(String copyrightFileName) {
		_copyrightFileName = copyrightFileName;
	}

	public void setInputFileName(String inputFileName) {
		_inputFileName = inputFileName;
	}

	private String _apiDirName = API_DIR_NAME;
	private String _apiPackagePath = API_PACKAGE_PATH;
	private String _applicationBaseURI = APPLICATION_BASE_URI;
	private String _applicationClassName = APPLICATION_BASE_URI;
	private String _applicationName = APPLICATION_NAME;
	private String _author = AUTHOR;
	private String _copyrightFileName;
	private String _inputFileName = INPUT_FILE_NAME;

}