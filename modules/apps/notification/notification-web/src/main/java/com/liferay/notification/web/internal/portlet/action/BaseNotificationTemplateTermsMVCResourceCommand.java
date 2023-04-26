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

package com.liferay.notification.web.internal.portlet.action;

import com.liferay.object.definition.notification.term.util.ObjectDefinitionNotificationTermUtil;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Mateus Santana
 */
public abstract class BaseNotificationTemplateTermsMVCResourceCommand
	extends BaseMVCResourceCommand {

	protected Set<Map.Entry<String, String>> getTermNamesEntries(
		List<ObjectField> objectFields, String partialTermName,
		ThemeDisplay themeDisplay) {

		Map<String, String> termNames = new LinkedHashMap<>();

		for (ObjectField objectField : objectFields) {
			if (StringUtil.equals(objectField.getName(), "creator")) {
				authorObjectFieldNames.forEach(
					(termLabel, objectFieldName) -> termNames.put(
						termLabel,
						ObjectDefinitionNotificationTermUtil.
							getObjectFieldTermName(
								partialTermName, objectFieldName)));
			}
			else {
				termNames.put(
					objectField.getLabel(themeDisplay.getLocale()),
					ObjectDefinitionNotificationTermUtil.getObjectFieldTermName(
						partialTermName, objectField.getName()));
			}
		}

		return termNames.entrySet();
	}

	protected JSONArray getTermsJSONArray(
		List<ObjectField> objectFields, String partialTermName,
		ThemeDisplay themeDisplay) {

		JSONArray termsJSONArray = jsonFactory.createJSONArray();

		for (Map.Entry<String, String> entry :
				getTermNamesEntries(
					objectFields, partialTermName, themeDisplay)) {

			termsJSONArray.put(
				JSONUtil.put(
					"termLabel",
					language.get(themeDisplay.getLocale(), entry.getKey())
				).put(
					"termName", entry.getValue()
				));
		}

		return termsJSONArray;
	}

	protected final Map<String, String> authorObjectFieldNames =
		HashMapBuilder.put(
			"author-email-address", "AUTHOR_EMAIL_ADDRESS"
		).put(
			"author-first-name", "AUTHOR_FIRST_NAME"
		).put(
			"author-id", "AUTHOR_ID"
		).put(
			"author-last-name", "AUTHOR_LAST_NAME"
		).put(
			"author-middle-name", "AUTHOR_MIDDLE_NAME"
		).put(
			"author-prefix", "AUTHOR_PREFIX"
		).put(
			"author-suffix", "AUTHOR_SUFFIX"
		).build();

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Language language;

	@Reference
	protected UserLocalService userLocalService;

}