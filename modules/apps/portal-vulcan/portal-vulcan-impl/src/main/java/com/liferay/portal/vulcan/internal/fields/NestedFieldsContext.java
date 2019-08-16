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

package com.liferay.portal.vulcan.internal.fields;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.message.Message;

/**
 * @author Ivica Cardic
 */
public class NestedFieldsContext {

	public NestedFieldsContext(
		List<String> fieldNames, Message message,
		MultivaluedMap<String, String> pathParameters, String resourceVersion,
		MultivaluedMap<String, String> queryParameters) {

		_fieldNames = fieldNames;
		_message = message;
		_pathParameters = pathParameters;
		_resourceVersion = resourceVersion;
		_queryParameters = queryParameters;
	}

	public List<String> getFieldNames() {
		return _fieldNames;
	}

	public Message getMessage() {
		return _message;
	}

	public MultivaluedMap<String, String> getPathParameters() {
		return _pathParameters;
	}

	public MultivaluedMap<String, String> getQueryParameters() {
		return _queryParameters;
	}

	public String getResourceVersion() {
		return _resourceVersion;
	}

	private final List<String> _fieldNames;
	private final Message _message;
	private final MultivaluedMap<String, String> _pathParameters;
	private final MultivaluedMap<String, String> _queryParameters;
	private final String _resourceVersion;

}