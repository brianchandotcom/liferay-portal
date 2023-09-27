/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.fields;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.message.Message;

/**
 * @author Ivica Cardic
 */
public class NestedFieldsContext {

	public NestedFieldsContext(int depth, List<String> fieldNames) {
		this(depth, fieldNames, null, null, null, null);
	}

	public NestedFieldsContext(
		int depth, List<String> fieldNames, Message message,
		MultivaluedMap<String, String> pathParameters, String resourceVersion,
		MultivaluedMap<String, String> queryParameters) {

		_depth = depth;
		_fieldNames = ListUtil.copy(fieldNames);
		_message = message;
		_pathParameters = pathParameters;
		_resourceVersion = resourceVersion;
		_queryParameters = queryParameters;
	}

	public void addFieldName(String fieldName) {
		_fieldNames.add(fieldName);
	}

	public void decrementCurrentDepth() {
		_currentDepth--;
	}

	public int getCurrentDepth() {
		return _currentDepth;
	}

	public int getDepth() {
		return _depth;
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

	public void incrementCurrentDepth() {
		_currentDepth++;
	}

	private int _currentDepth;
	private final int _depth;
	private final List<String> _fieldNames;
	private final Message _message;
	private final MultivaluedMap<String, String> _pathParameters;
	private final MultivaluedMap<String, String> _queryParameters;
	private final String _resourceVersion;

}