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

package com.liferay.document.library.web.internal.display.context.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class MockHttpServletRequestBuilder {

	public MockHttpServletRequestBuilder() {
	}

	public MockHttpServletRequest build() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Set<Map.Entry<String, Object>> entries = _attributes.entrySet();

		entries.forEach(
			entry -> mockHttpServletRequest.setAttribute(
				entry.getKey(), entry.getValue()));

		mockHttpServletRequest.setParameters(_parameters);

		return mockHttpServletRequest;
	}

	public MockHttpServletRequestBuilder withAttribute(
		String key, Object value) {

		_attributes.put(key, value);

		return this;
	}

	public MockHttpServletRequestBuilder withParameter(
		String key, String value) {

		_parameters.put(key, value);

		return this;
	}

	private HashMap<String, Object> _attributes = new HashMap<>();
	private HashMap<String, String> _parameters = new HashMap<>();

}