/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.ai.hub.internal.web.search.LiferayWebSearchEngine;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.WebSearchContentRetriever;

import java.util.Map;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class ContentRetrieverUtil {

	public static ContentRetriever createContentRetriever(
		Map<String, String> kaleoNodeSettingValues, String userToken) {

		if (kaleoNodeSettingValues.get("rag") == null) {
			return null;
		}

		try {
			JSONObject ragJSONObject = JSONFactoryUtil.createJSONObject(
				kaleoNodeSettingValues.get("rag"));

			JSONObject contentRetrieverJSONObject = ragJSONObject.getJSONObject(
				"contentRetriever");

			if (Objects.equals(
					contentRetrieverJSONObject.getString("key"), "liferay")) {

				return WebSearchContentRetriever.builder(
				).webSearchEngine(
					new LiferayWebSearchEngine(
						contentRetrieverJSONObject.getString(
							"blueprintExternalReferenceCode"),
						userToken)
				).build();
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentRetrieverUtil.class);

}