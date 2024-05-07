/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.validation.rule;

import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = ObjectValidationRuleEngine.class)
public class CommerceReturnReturnStatusObjectValidationRuleEngineImpl
	implements ObjectDefinitionScoped, ObjectValidationRuleEngine {

	public static final String RETURN_STATUS_VALIDATION_RULE_ENGINE =
		"commerce-return-return-status-validation-rule-engine";

	@Override
	public Map<String, Object> execute(
		Map<String, Object> inputObjects, String script) {

		Map<String, Object> results = HashMapBuilder.<String, Object>put(
			"validationCriteriaMet", true
		).build();

		Map<String, Object> entryDTO = (Map<String, Object>)inputObjects.get(
			"entryDTO");

		Map<String, Object> properties = (Map<String, Object>)entryDTO.get(
			"properties");

		if (!properties.containsKey("returnStatus")) {
			results.put("validationCriteriaMet", false);
		}

		Map<String, String> returnStatusMap =
			(Map<String, String>)properties.get("returnStatus");

		if ((returnStatusMap == null) || returnStatusMap.isEmpty()) {
			results.put("validationCriteriaMet", false);
		}

		return results;
	}

	@Override
	public List<String> getAllowedObjectDefinitionNames() {
		return Arrays.asList("CommerceReturn");
	}

	@Override
	public String getKey() {
		return ObjectValidationRuleConstants.ENGINE_TYPE_JAVA_DELEGATE_PREFIX +
			RETURN_STATUS_VALIDATION_RULE_ENGINE;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, RETURN_STATUS_VALIDATION_RULE_ENGINE);
	}

	@Reference
	private Language _language;

}