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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import javax.mvc.security.Csrf;

/**
 * @author  Neil Griffin
 */
public class CsrfImpl implements Csrf {

	public CsrfImpl(String name, String token) {
		_name = name;
		_token = token;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getToken() {
		return _token;
	}

	private final String _name;
	private final String _token;

}