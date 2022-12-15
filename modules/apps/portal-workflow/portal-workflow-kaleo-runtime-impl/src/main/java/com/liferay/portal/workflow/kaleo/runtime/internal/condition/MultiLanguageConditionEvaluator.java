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

package com.liferay.portal.workflow.kaleo.runtime.internal.condition;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.definition.ScriptLanguage;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.model.KaleoCondition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.condition.ConditionEvaluator;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Michael C. Han
 */
@Component(service = ConditionEvaluator.class)
public class MultiLanguageConditionEvaluator implements ConditionEvaluator {

	@Override
	public String evaluate(
			KaleoCondition kaleoCondition, ExecutionContext executionContext)
		throws PortalException {

		String conditionEvaluatorKey = _getConditionEvaluatorKey(
			kaleoCondition.getScriptLanguage(),
			StringUtil.trim(kaleoCondition.getScript()));

		ConditionEvaluator conditionEvaluator = _serviceTrackerMap.getService(
			conditionEvaluatorKey);

		if (conditionEvaluator == null) {
			throw new IllegalArgumentException(
				"No condition evaluator found for script language " +
					conditionEvaluatorKey);
		}

		return conditionEvaluator.evaluate(kaleoCondition, executionContext);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ConditionEvaluator.class, "(scripting.language=*)",
			(serviceReference, emitter) -> {
				Object propertyValue = serviceReference.getProperty(
					"scripting.language");

				ConditionEvaluator conditionEvaluator =
					bundleContext.getService(serviceReference);

				try {
					for (String scriptingLanguage :
							GetterUtil.getStringValues(
								propertyValue,
								new String[] {String.valueOf(propertyValue)})) {

						emitter.emit(
							_getConditionEvaluatorKey(
								scriptingLanguage,
								ClassUtil.getClassName(conditionEvaluator)));
					}
				}
				catch (KaleoDefinitionValidationException
							kaleoDefinitionValidationException) {

					throw new RuntimeException(
						kaleoDefinitionValidationException);
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getConditionEvaluatorKey(
			String language, String conditionEvaluatorClassName)
		throws KaleoDefinitionValidationException {

		ScriptLanguage scriptLanguage = ScriptLanguage.parse(language);

		if (scriptLanguage.equals(ScriptLanguage.JAVA)) {
			return language + StringPool.COLON + conditionEvaluatorClassName;
		}

		return language;
	}

	private ServiceTrackerMap<String, ConditionEvaluator> _serviceTrackerMap;

}