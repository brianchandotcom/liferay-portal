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

package com.liferay.headless.builder.internal.dto;

import com.liferay.portal.vulcan.message.RootElementProvider;

import java.util.HashMap;

/**
 * @author Carlos Correa
 */
public class HeadlessBuilderDTO
	extends HashMap<String, Object> implements RootElementProvider {

	public String getName() {
		return _name;
	}

	@Override
	public String getRootElement() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private String _name;

}