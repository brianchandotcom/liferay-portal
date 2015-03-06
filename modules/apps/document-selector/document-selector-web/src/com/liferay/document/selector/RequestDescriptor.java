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

package com.liferay.document.selector;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class RequestDescriptor {

	public RequestDescriptor(String url, Map<String, String> params) {
		_url = url;
		_params = params;
	}

	public Map<String, String> getParams() {
		return _params;
	}

	public String getURL() {
		return _url;
	}

	private final Map<String, String> _params;
	private final String _url;

}