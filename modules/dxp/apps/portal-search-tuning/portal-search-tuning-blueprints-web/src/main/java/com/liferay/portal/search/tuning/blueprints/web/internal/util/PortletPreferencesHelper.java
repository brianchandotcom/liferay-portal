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

package com.liferay.portal.search.tuning.blueprints.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class PortletPreferencesHelper {

	public PortletPreferencesHelper(PortletPreferences portletPreferences) {
		_portletPreferencesOptional = Optional.of(portletPreferences);
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

	public long getLong(String key, long defaultValue) {
		Optional<Long> value = getLongOptional(key);

		return value.orElse(defaultValue);
	}

	public Optional<Long> getLongOptional(String key) {
		Optional<String> value = getValue(key);

		return value.map(GetterUtil::getLong);
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
			portletPreferences -> _maybe(
				portletPreferences.getValue(key, StringPool.BLANK)));
	}

	private Optional<String> _maybe(String s) {
		s = StringUtil.trim(s);

		if (Validator.isBlank(s)) {
			return Optional.empty();
		}

		return Optional.of(s);
	}

	private final Optional<PortletPreferences> _portletPreferencesOptional;

}