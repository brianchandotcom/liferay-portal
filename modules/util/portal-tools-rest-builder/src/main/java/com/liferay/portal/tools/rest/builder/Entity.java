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
public class Entity {

	public String getApiDir() {
		return _apiDir;
	}

	public String getApiPackagePath() {
		return _apiPackagePath;
	}

	public String getAuthor() {
		return _author;
	}

	public String getName() {
		return _name;
	}

	public void setApiDir(String apiDir) {
		_apiDir = apiDir;
	}

	public void setApiPackagePath(String apiPackagePath) {
		_apiPackagePath = apiPackagePath;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public void setName(String name) {
		_name = name;
	}

	private String _apiDir;
	private String _apiPackagePath;
	private String _author;
	private String _name;

}