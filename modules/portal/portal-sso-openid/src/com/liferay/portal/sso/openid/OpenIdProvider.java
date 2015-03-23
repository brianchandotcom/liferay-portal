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

package com.liferay.portal.sso.openid;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Michael C. Han
 */
public class OpenIdProvider {

	public String[] getAxSchema() {
		return _axSchema;
	}

	public String getAxTypeEmail() {
		return _axTypeEmail;
	}

	public String getAxTypeFirstName() {
		return _axTypeFirstName;
	}

	public String getAxTypeFullName() {
		return _axTypeFullName;
	}

	public String getAxTypeLastName() {
		return _axTypeLastName;
	}

	public String getName() {
		return _name;
	}

	public String getUrl() {
		return _url;
	}

	public void setAxSchema(String axSchema) {
		StringUtil.split(axSchema, StringPool.COMMA);
	}

	public void setAxSchema(String[] axSchema) {
		_axSchema = axSchema;
	}

	public void setAxTypeEmail(String axTypeEmail) {
		_axTypeEmail = axTypeEmail;
	}

	public void setAxTypeFirstName(String axTypeFirstName) {
		_axTypeFirstName = axTypeFirstName;
	}

	public void setAxTypeFullName(String axTypeFullName) {
		_axTypeFullName = axTypeFullName;
	}

	public void setAxTypeLastName(String axTypeLastName) {
		_axTypeLastName = axTypeLastName;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private String[] _axSchema;
	private String _axTypeEmail;
	private String _axTypeFirstName;
	private String _axTypeFullName;
	private String _axTypeLastName;
	private String _name;
	private String _url;

}