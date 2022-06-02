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

package com.liferay.object.internal.action.executor;

import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.internal.action.util.ObjectActionVariablesUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.runtime.scripting.executor.GroovyScriptingExecutor;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.util.HashSet;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ObjectActionExecutor.class)
public class GroovyObjectActionExecutorImpl implements ObjectActionExecutor {

	@Override
	public void execute(
			long companyId, UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				payloadJSONObject.getLong("objectDefinitionId"));

		Map<String, Object> results = _groovyScriptingExecutor.execute(
			ObjectActionVariablesUtil.toVariables(
				_dtoConverterRegistry, objectDefinition, payloadJSONObject),
			new HashSet<>(), parametersUnicodeProperties.get("script"));

		if (GetterUtil.getBoolean(results.get("invalidScript"))) {
			throw new ScriptingException();
		}
	}

	@Override
	public String getKey() {
		return ObjectActionExecutorConstants.KEY_GROOVY;
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroovyScriptingExecutor _groovyScriptingExecutor;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}