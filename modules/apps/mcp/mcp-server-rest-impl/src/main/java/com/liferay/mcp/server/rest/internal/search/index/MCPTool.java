/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.search.index;

import com.liferay.mcp.server.rest.internal.util.ToolSetUtil;

/**
 * @author Petteri Karttunen
 */
public class MCPTool {

	public MCPTool(
		boolean collection, boolean deprecated, String description,
		String entityName, String[] expansions, String intent, String method,
		String operationVariant, String[] parameters, String path,
		String[] requiredReferences, String[] schemaProperties, String toolName,
		String toolSetName) {

		_collection = collection;
		_deprecated = deprecated;
		_description = description;
		_entityName = entityName;
		_expansions = expansions;
		_intent = intent;
		_method = method;
		_operationVariant = operationVariant;
		_parameters = parameters;
		_path = path;
		_requiredReferences = requiredReferences;
		_schemaProperties = schemaProperties;
		_toolName = toolName;
		_toolSetName = toolSetName;
	}

	public String getDescription() {
		return _description;
	}

	public String getEntityName() {
		return _entityName;
	}

	public String[] getExpansions() {
		return _expansions;
	}

	public String getIntent() {
		return _intent;
	}

	public String getMethod() {
		return _method;
	}

	public String getOperationVariant() {
		return _operationVariant;
	}

	public String[] getParameters() {
		return _parameters;
	}

	public String getPath() {
		return _path;
	}

	public String[] getRequiredReferences() {
		return _requiredReferences;
	}

	public String[] getSchemaProperties() {
		return _schemaProperties;
	}

	public String getToolName() {
		return _toolName;
	}

	public String getToolSetName() {
		return _toolSetName;
	}

	public String getUID() {
		return ToolSetUtil.getToolKey(_toolName, _toolSetName);
	}

	public boolean isCollection() {
		return _collection;
	}

	public boolean isDeprecated() {
		return _deprecated;
	}

	private final boolean _collection;
	private final boolean _deprecated;
	private final String _description;
	private final String _entityName;
	private final String[] _expansions;
	private final String _intent;
	private final String _method;
	private final String _operationVariant;
	private final String[] _parameters;
	private final String _path;
	private final String[] _requiredReferences;
	private final String[] _schemaProperties;
	private final String _toolName;
	private final String _toolSetName;

}