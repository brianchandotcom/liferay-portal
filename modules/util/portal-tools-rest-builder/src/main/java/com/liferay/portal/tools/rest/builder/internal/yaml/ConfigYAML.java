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

package com.liferay.portal.tools.rest.builder.internal.yaml;

import com.liferay.portal.tools.rest.builder.internal.yaml.config.Application;

/**
 * @author Peter Shin
 */
public class ConfigYAML {

	public static final String IMPL_DIR = "src/main/java";

	public String getApiDir() {
		return _apiDir;
	}

	public String getApiPackagePath() {
		return _apiPackagePath;
	}

	public Application getApplication() {
		return _application;
	}

	public String getAuthor() {
		return _author;
	}

	public String getImplDir() {
		return _implDir;
	}

	public void setApiDir(String apiDir) {
		_apiDir = apiDir;
	}

	public void setApiPackagePath(String apiPackagePath) {
		_apiPackagePath = apiPackagePath;
	}

	public void setApplication(Application application) {
		_application = application;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public void setImplDir(String implDir) {
		_implDir = implDir;
	}

	private String _apiDir;
	private String _apiPackagePath;
	private Application _application;
	private String _author;
	private String _implDir = IMPL_DIR;

}