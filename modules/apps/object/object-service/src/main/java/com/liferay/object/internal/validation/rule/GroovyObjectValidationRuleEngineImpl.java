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

package com.liferay.object.internal.validation.rule;

import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.scripting.executor.ObjectScriptingExecutor;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ObjectValidationRuleEngine.class)
public class GroovyObjectValidationRuleEngineImpl
	implements ObjectValidationRuleEngine {

	@Override
	public Map<String, Object> execute(
		Map<String, Object> inputObjects, String script) {

		return _objectScriptingExecutor.execute(
			inputObjects, SetUtil.fromArray("invalidFields"), script);
	}

	@Override
	public String getName() {
		return ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY;
	}

	@Reference(target = "(scripting.language=groovy)")
	private ObjectScriptingExecutor _objectScriptingExecutor;

}