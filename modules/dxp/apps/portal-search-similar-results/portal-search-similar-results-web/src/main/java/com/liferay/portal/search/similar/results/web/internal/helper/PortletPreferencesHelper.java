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

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class PortletPreferencesHelper {

	public PortletPreferencesHelper(PortletPreferences portletPreferences) {
		_portletPreferences = portletPreferences;
	}

	public Integer getInteger(String key) {
		String stringValue = _getStringValue(key);

		if (stringValue == null) {
			return null;
		}

		return GetterUtil.getInteger(stringValue);
	}

	public int getInteger(String key, int defaultValue) {
		return GetterUtil.getInteger(_getStringValue(key), defaultValue);
	}

	public String getString(String key) {
		return _getStringValue(key);
	}

	public String getString(String key, String defaultValue) {
		String string = _getStringValue(key);

		if (string == null) {
			return defaultValue;
		}

		return string;
	}

	private String _getStringValue(String key) {
		if (_portletPreferences == null) {
			return null;
		}

		return SearchStringUtil.maybe(
			_portletPreferences.getValue(key, StringPool.BLANK));
	}

	private final PortletPreferences _portletPreferences;

}