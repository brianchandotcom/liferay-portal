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

package com.liferay.object.service.test.util;

import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;

/**
 * @author Feliphe Marinho
 */
public class TestObjectActionExecutor implements ObjectActionExecutor {

	public TestObjectActionExecutor(
		List<String> allowedObjectDefinitionNames, long companyId,
		String key) {

		_allowedObjectDefinitionNames = allowedObjectDefinitionNames;
		_companyId = companyId;
		_key = key;
	}

	@Override
	public void execute(
		long companyId, UnicodeProperties parametersUnicodeProperties,
		JSONObject payloadJSONObject, long userId) throws Exception {
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public boolean isAllowedCompany(long companyId) {
		if ((_companyId == 0) || (_companyId == companyId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isAllowedObjectDefinition(String objectDefinitionName) {
		if (_allowedObjectDefinitionNames.isEmpty()) {
			return true;
		}

		return _allowedObjectDefinitionNames.contains(objectDefinitionName);
	}

	private final List<String> _allowedObjectDefinitionNames;
	private final long _companyId;
	private final String _key;
}
