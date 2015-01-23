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

package com.liferay.wiki.web.path;

import com.liferay.portal.kernel.struts.path.AuthPublicPath;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class BaseAuthPublicPath implements AuthPublicPath {

	public static final String AUTH_PUBLIC_PATH = "auth.public.path";

	@Override
	public String path() {
		return _path;
	}

	protected void updatePath(Map<String, String> properties) {
		if (!properties.containsKey(AUTH_PUBLIC_PATH)) {
			_path = null;

			return;
		}

		String path = properties.get(AUTH_PUBLIC_PATH);

		_path = path;
	}

	private String _path;

}