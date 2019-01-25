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

	public static final String REST_CONFIG_FILE_NAME = "rest-config.yaml";

	public static final String REST_OPENAPI_FILE_NAME = "rest-openapi.yaml";

	public String getCopyrightFileName() {
		return _copyrightFileName;
	}

	public String getRESTConfigFileName() {
		return _restConfigFileName;
	}

	public String getRESTOpenAPIFileName() {
		return _restOpenAPIFileName;
	}

	public void setCopyrightFileName(String copyrightFileName) {
		_copyrightFileName = copyrightFileName;
	}

	public void setRESTConfigFileName(String restConfigFileName) {
		_restConfigFileName = restConfigFileName;
	}

	public void setRESTOpenAPIFileName(String restOpenAPIFileName) {
		_restOpenAPIFileName = restOpenAPIFileName;
	}

	private String _copyrightFileName;
	private String _restConfigFileName = REST_CONFIG_FILE_NAME;
	private String _restOpenAPIFileName = REST_OPENAPI_FILE_NAME;

}