/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.web.internal.display.context;

import com.liferay.friendly.url.configuration.manager.FriendlyURLSeparatorConfigurationManager;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Mikel Lorza
 */
public class FriendlyURLSeparatorCompanyConfigurationDisplayContext {

	public FriendlyURLSeparatorCompanyConfigurationDisplayContext(
		FriendlyURLSeparatorConfigurationManager
			friendlyURLSeparatorConfigurationManager,
		JSONFactory jsonFactory, Language language, ThemeDisplay themeDisplay) {

		_friendlyURLSeparatorConfigurationManager =
			friendlyURLSeparatorConfigurationManager;
		_jsonFactory = jsonFactory;
		_language = language;
		_themeDisplay = themeDisplay;
	}

	public JSONArray getConfigurableFriendlyURLSeparatorsJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		List<FriendlyURLSeparator> friendlyURLSeparators = new ArrayList<>();

		JSONObject configuredFriendlyURLSeparatorsJSONObject =
			_getConfiguredFriendlyURLSeparatorsJSONObject();

		for (FriendlyURLResolver friendlyURLResolver :
				FriendlyURLResolverRegistryUtil.
					getFriendlyURLResolversAsCollection()) {

			if (!friendlyURLResolver.isURLSeparatorConfigurable() ||
				Validator.isNull(friendlyURLResolver.getKey())) {

				continue;
			}

			friendlyURLSeparators.add(
				new FriendlyURLSeparator(
					friendlyURLResolver.getKey(),
					_language.get(
						_themeDisplay.getLocale(),
						friendlyURLResolver.getKey() + "-url-separator"),
					_getFriendlyURLSeparator(
						configuredFriendlyURLSeparatorsJSONObject,
						friendlyURLResolver.getDefaultURLSeparator(),
						friendlyURLResolver.getKey())));
		}

		Collections.sort(
			friendlyURLSeparators,
			Comparator.comparing(FriendlyURLSeparator::getLabel));

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		for (FriendlyURLSeparator friendlyURLSeparator :
				friendlyURLSeparators) {

			jsonArray.put(
				JSONUtil.put(
					"label", friendlyURLSeparator.getLabel()
				).put(
					"name",
					portletDisplay.getNamespace() +
						friendlyURLSeparator.getKey()
				).put(
					"value", friendlyURLSeparator.getUrlSeparator()
				));
		}

		return jsonArray;
	}

	private JSONObject _getConfiguredFriendlyURLSeparatorsJSONObject() {
		try {
			return _jsonFactory.createJSONObject(
				_friendlyURLSeparatorConfigurationManager.
					getFriendlyURLSeparators(_themeDisplay.getCompanyId()));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return _jsonFactory.createJSONObject();
	}

	private String _getFriendlyURLSeparator(
		JSONObject configuredURLSeparatorsJSONObject,
		String defaultURLSeparator, String key) {

		String friendlyURLSeparator =
			configuredURLSeparatorsJSONObject.getString(key);

		if (Validator.isNull(friendlyURLSeparator)) {
			return defaultURLSeparator;
		}

		return friendlyURLSeparator;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLSeparatorCompanyConfigurationDisplayContext.class.getName());

	private final FriendlyURLSeparatorConfigurationManager
		_friendlyURLSeparatorConfigurationManager;
	private final JSONFactory _jsonFactory;
	private final Language _language;
	private final ThemeDisplay _themeDisplay;

	private class FriendlyURLSeparator {

		public FriendlyURLSeparator(
			String key, String label, String urlSeparator) {

			_key = key;
			_label = label;
			_urlSeparator = urlSeparator;
		}

		public String getKey() {
			return _key;
		}

		public String getLabel() {
			return _label;
		}

		public String getUrlSeparator() {
			return _urlSeparator;
		}

		private final String _key;
		private final String _label;
		private final String _urlSeparator;

	}

}