/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.validation.rule;

import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Crescenzo Rega
 */
public abstract class BasePermissionObjectValidationRuleEngineImpl
	implements ObjectDefinitionScoped, ObjectValidationRuleEngine {

	@Override
	public Map<String, Object> execute(
		Map<String, Object> inputObjects, String script) {

		return HashMapBuilder.<String, Object>put(
			"validationCriteriaMet",
			() -> hasValidationCriteriaMet(inputObjects)
		).build();
	}

	@Override
	public List<String> getAllowedObjectDefinitionNames() {
		return Arrays.asList(getObjectDefinitionName());
	}

	@Override
	public String getKey() {
		return StringBundler.concat(
			"javaDelegate#", getObjectDefinitionName(), "#permission");
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale,
			TextFormatter.format(
				getObjectDefinitionName() + "-permission", TextFormatter.K));
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2, String... keys) {

		for (String key : keys) {
			if (!Objects.equals(map1.get(key), map2.get(key))) {
				return false;
			}
		}

		return true;
	}

	protected abstract String getObjectDefinitionName();

	protected abstract boolean hasValidationCriteriaMet(
			Map<String, Object> inputObjects)
		throws Exception;

}