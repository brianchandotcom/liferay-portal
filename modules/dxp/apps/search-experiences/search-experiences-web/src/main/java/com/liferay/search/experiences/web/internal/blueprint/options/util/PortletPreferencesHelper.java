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

package com.liferay.search.experiences.web.internal.blueprint.options.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

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
		Optional<String> value = getValue(key);

		return value.map(GetterUtil::getBoolean);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		Optional<Boolean> value = getBoolean(key);

		return value.orElse(defaultValue);
	}

	public Optional<Integer> getInteger(String key) {
		Optional<String> value = getValue(key);

		return value.map(GetterUtil::getInteger);
	}

	public int getInteger(String key, int defaultValue) {
		Optional<Integer> value = getInteger(key);

		return value.orElse(defaultValue);
	}

	public Optional<String> getString(String key) {
		return getValue(key);
	}

	public String getString(String key, String defaultValue) {
		Optional<String> value = getString(key);

		return value.orElse(defaultValue);
	}

	protected Optional<String> getValue(String key) {
		return _portletPreferencesOptional.flatMap(
			portletPreferences -> SearchStringUtil.maybe(
				portletPreferences.getValue(key, StringPool.BLANK)));
	}

	private final Optional<PortletPreferences> _portletPreferencesOptional;

}