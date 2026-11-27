/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class FriendlyURLEntryTitles {

	public FriendlyURLEntryTitles(
		String defaultLanguageId, Map<String, String> languageIdToUrlTitleMap) {

		_defaultLanguageId = defaultLanguageId;
		_languageIdToUrlTitleMap = languageIdToUrlTitleMap;
	}

	public Map<String, String> getLanguageIdToUrlTitleMap() {
		return new HashMap<>(_languageIdToUrlTitleMap);
	}

	public String getUrlTitle(String languageId) {
		String urlTitle = _languageIdToUrlTitleMap.get(languageId);

		if (Validator.isBlank(urlTitle)) {
			return _languageIdToUrlTitleMap.getOrDefault(
				_defaultLanguageId, StringPool.BLANK);
		}

		return urlTitle;
	}

	private final String _defaultLanguageId;
	private final Map<String, String> _languageIdToUrlTitleMap;

}