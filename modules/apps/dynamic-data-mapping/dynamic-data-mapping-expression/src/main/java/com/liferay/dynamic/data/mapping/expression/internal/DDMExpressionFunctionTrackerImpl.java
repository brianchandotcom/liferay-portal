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

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.dynamic.data.mapping.expression.internal.helper.DDMExpressionFunctionTrackerHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DDMExpressionFunctionTracker.class)
public class DDMExpressionFunctionTrackerImpl
	implements DDMExpressionFunctionTracker {

	@Override
	public Map<String, DDMExpressionFunction> getDDMExpressionFunctions(
		Set<String> functionNames) {

		Map<String, DDMExpressionFunction> ddmExpressionFunctionsMap =
			new HashMap<>(functionNames.size());

		for (String functionName : functionNames) {
			DDMExpressionFunction ddmExpressionFunction =
				ddmExpressionFunctionTrackerHelper.getDDMExpressionFunction(
					functionName);

			if (ddmExpressionFunction != null) {
				ddmExpressionFunctionsMap.put(
					functionName, ddmExpressionFunction);
			}
		}

		return ddmExpressionFunctionsMap;
	}

	@Override
	public void ungetDDMExpressionFunctions(
		Map<String, DDMExpressionFunction> ddmExpressionFunctionsMap) {

		for (Map.Entry<String, DDMExpressionFunction> entry :
				ddmExpressionFunctionsMap.entrySet()) {

			ddmExpressionFunctionTrackerHelper.ungetDDMExpressionFunction(
				entry.getValue());
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(component.factory=" + DDMConstants.EXPRESSION_FUNCTION_FACTORY_NAME + ")",
		unbind = "unsetComponentFactory"
	)
	protected void addComponentFactory(ComponentFactory componentFactory) {
		ddmExpressionFunctionTrackerHelper.addComponentFactory(
			componentFactory);
	}

	@Deactivate
	protected void deactivate() {
		ddmExpressionFunctionTrackerHelper.clear();
	}

	protected void unsetComponentFactory(ComponentFactory componentFactory) {
		ddmExpressionFunctionTrackerHelper.removeComponentFactory(
			componentFactory);
	}

	protected DDMExpressionFunctionTrackerHelper
		ddmExpressionFunctionTrackerHelper =
			new DDMExpressionFunctionTrackerHelper();

}