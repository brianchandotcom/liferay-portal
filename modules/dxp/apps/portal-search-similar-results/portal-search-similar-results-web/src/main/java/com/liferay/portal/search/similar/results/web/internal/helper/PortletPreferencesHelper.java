/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.helper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.similar.results.web.internal.util.SearchStringUtil;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class PortletPreferencesHelper {

	public PortletPreferencesHelper(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesOptional = portletPreferencesOptional;
	}

	public Optional<Boolean> getBoolean(String key) {
		Optional<String> valueOptional = _getValue(key);

		return valueOptional.map(GetterUtil::getBoolean);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		Optional<Boolean> valueOptional = getBoolean(key);

		return valueOptional.orElse(defaultValue);
	}

	public Optional<Integer> getInteger(String key) {
		Optional<String> valueOptional = _getValue(key);

		return valueOptional.map(GetterUtil::getInteger);
	}

	public int getInteger(String key, int defaultValue) {
		Optional<Integer> valueOptional = getInteger(key);

		return valueOptional.orElse(defaultValue);
	}

	public Optional<String> getString(String key) {
		return _getValue(key);
	}

	public String getString(String key, String defaultValue) {
		Optional<String> valueOptional = getString(key);

		return valueOptional.orElse(defaultValue);
	}

	private Optional<String> _getValue(String key) {
		return _portletPreferencesOptional.flatMap(
			portletPreferences -> SearchStringUtil.maybe(
				portletPreferences.getValue(key, StringPool.BLANK)));
	}

	private final Optional<PortletPreferences> _portletPreferencesOptional;

}