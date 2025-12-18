/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.workflow.kaleo.runtime.node.util;

import com.liferay.ai.hub.site.initializer.internal.web.search.LiferayWebSearchEngine;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.WebSearchContentRetriever;

import java.util.Map;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class ContentRetrieverUtil {

	public static ContentRetriever createContentRetriever(
		Map<String, String> kaleoNodeSettingValues) {

		if (kaleoNodeSettingValues.get("retrievalAugmentor") == null) {
			return null;
		}

		try {
			JSONObject retrievalAugmentorJSONObject =
				JSONFactoryUtil.createJSONObject(
					kaleoNodeSettingValues.get("retrievalAugmentor"));

			JSONObject contentRetrieverJSONObject =
				retrievalAugmentorJSONObject.getJSONObject("contentRetriever");

			if (Objects.equals(
					contentRetrieverJSONObject.getString("key"), "liferay")) {

				return WebSearchContentRetriever.builder(
				).webSearchEngine(
					new LiferayWebSearchEngine(
						contentRetrieverJSONObject.getString(
							"blueprintExternalReferenceCode"))
				).build();
			}
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}

		return null;
	}

}