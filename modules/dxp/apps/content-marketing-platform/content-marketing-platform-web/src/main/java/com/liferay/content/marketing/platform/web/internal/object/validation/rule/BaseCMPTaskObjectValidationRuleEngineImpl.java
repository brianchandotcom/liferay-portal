/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.marketing.platform.web.internal.object.validation.rule;

import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Larissa Ribeiro
 */
public abstract class BaseCMPTaskObjectValidationRuleEngineImpl
	implements ObjectDefinitionScoped, ObjectValidationRuleEngine {

	@Override
	public Map<String, Object> execute(
		Map<String, Object> inputObjects, String script) {

		return HashMapBuilder.<String, Object>put(
			"validationCriteriaMet",
			() -> hasValidationCriteriaMet(
				_getParentObjectEntryId(
					(Map<String, Object>)inputObjects.get("originalEntryDTO")),
				_getParentObjectEntryId(
					(Map<String, Object>)inputObjects.get("entryDTO")))
		).build();
	}

	@Override
	public List<String> getAllowedObjectDefinitionNames() {
		return Collections.singletonList("CMPTask");
	}

	@Override
	public String getKey() {
		return "javaDelegate#CMPTask#" + getName();
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, getName());
	}

	protected abstract String getName();

	protected abstract boolean hasValidationCriteriaMet(
			Long originalParentObjectEntryId, long parentObjectEntryId)
		throws Exception;

	private Long _getParentObjectEntryId(Map<String, Object> entryDTO) {
		if (MapUtil.isEmpty(entryDTO)) {
			return null;
		}

		Map<String, Object> properties = (Map<String, Object>)entryDTO.get(
			"properties");

		if (properties == null) {
			return 0L;
		}

		return GetterUtil.getLong(
			properties.get("r_cmpTaskToCMPTasks_c_cmpTaskId"));
	}

}