/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.util;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.constants.FrontendTokenDefinitionConstants;
import com.liferay.frontend.token.definition.util.FrontendTokenDefinitionUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Gabriel Lima
 */
public class StyleBookEntryFrontendTokenDefinitionUtil {

	public static JSONArray getFrontendTokenDefinitionsJSONArray(
			FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry,
			Locale locale, StyleBookEntry styleBookEntry)
		throws Exception {

		List<FrontendTokenDefinition> frontendTokenDefinitions = ListUtil.sort(
			ListUtil.filter(
				frontendTokenDefinitionRegistry.getFrontendTokenDefinitions(
					styleBookEntry.getCompanyId()),
				frontendTokenDefinition ->
					Objects.equals(
						frontendTokenDefinition.getThemeId(),
						styleBookEntry.getThemeId()) ||
					Objects.equals(
						frontendTokenDefinition.getThemeType(),
						FrontendTokenDefinitionConstants.THEME_TYPE_GLOBAL)),
			(frontendTokenDefinition1, frontendTokenDefinition2) ->
				Integer.compare(
					frontendTokenDefinition2.getPriority(),
					frontendTokenDefinition1.getPriority()));

		return JSONUtil.toJSONArray(
			frontendTokenDefinitions,
			frontendTokenDefinition -> {
				JSONObject frontendTokenDefinitionJSONObject =
					_getFrontendTokenDefinitionJSONObject(
						frontendTokenDefinition, locale, styleBookEntry);

				return frontendTokenDefinitionJSONObject.put(
					"id", frontendTokenDefinition.getThemeId()
				).put(
					"name", frontendTokenDefinition.getThemeName(locale)
				).put(
					"priority", frontendTokenDefinition.getPriority()
				);
			});
	}

	private static JSONObject _getFrontendTokenDefinitionJSONObject(
			FrontendTokenDefinition frontendTokenDefinition, Locale locale,
			StyleBookEntry styleBookEntry)
		throws Exception {

		JSONObject frontendTokenDefinitionJSONObject =
			frontendTokenDefinition.getJSONObject(locale);

		if (!DefaultStyleBookEntryUtil.isStyleBookEntryApplicable(
				frontendTokenDefinition, styleBookEntry)) {

			return frontendTokenDefinitionJSONObject;
		}

		JSONObject overrideFrontendTokenDefinitionJSONObject =
			FrontendTokenDefinitionUtil.parseFrontendTokenDefinitionJSONObject(
				styleBookEntry.getFrontendTokenDefinition());

		if (overrideFrontendTokenDefinitionJSONObject == null) {
			return frontendTokenDefinitionJSONObject;
		}

		return FrontendTokenDefinitionUtil.
			mergeFrontendTokenDefinitionJSONObject(
				frontendTokenDefinitionJSONObject,
				overrideFrontendTokenDefinitionJSONObject);
	}

}