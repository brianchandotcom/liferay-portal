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

package com.liferay.portal.vulcan.yaml.config;

/**
 * @author Peter Shin
 */
public class Application {

	public String getBaseURI() {
		return _baseURI;
	}

	public String getClassName() {
		return _className;
	}

	public String getName() {
		return _name;
	}

	public Security getSecurity() {
		return _security;
	}

	public void setBaseURI(String baseURI) {
		_baseURI = baseURI;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSecurity(Security security) {
		_security = security;
	}

	private String _baseURI;
	private String _className;
	private String _name;
	private Security _security;

}