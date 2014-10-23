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

package com.liferay.portal.fabric.agent.selectors;

import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ProcessCallableAssignmentFabricAgentSelector
	extends SystemPropertiesFilterFabricAgentSelector {

	public static final String PROCESS_CALLABLE_ASSIGNMENT_EXPRESSION_KEY =
		ProcessCallableAssignmentFabricAgentSelector.class.getName() +
			StringPool.POUND + "processCallableAssignmentExpressionKey";

	@Override
	protected boolean accept(
		Map<String, String> systemProperties,
		ProcessCallable<?> processCallable) {

		String processCallableAssignmentExpressionValue = systemProperties.get(
			PROCESS_CALLABLE_ASSIGNMENT_EXPRESSION_KEY);

		if (processCallableAssignmentExpressionValue == null) {
			return false;
		}

		Class<?> clazz = processCallable.getClass();

		String className = clazz.getName();

		return className.matches(processCallableAssignmentExpressionValue);
	}

}