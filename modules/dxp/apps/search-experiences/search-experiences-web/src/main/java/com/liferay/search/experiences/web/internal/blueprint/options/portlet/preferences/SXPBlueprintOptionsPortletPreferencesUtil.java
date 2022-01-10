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

package com.liferay.search.experiences.web.internal.blueprint.options.portlet.preferences;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Kevin Tan
 */
public class SXPBlueprintOptionsPortletPreferencesUtil {

	public static String getValue(
		Optional<PortletPreferences> portletPreferencesOptional, String key) {

		if (!portletPreferencesOptional.isPresent()) {
			return StringPool.BLANK;
		}

		return getValue(portletPreferencesOptional.get(), key);
	}

	public static String getValue(
		PortletPreferences portletPreferences, String key) {

		String value = portletPreferences.getValue(key, StringPool.BLANK);

		value = StringUtil.trim(value);

		if (Validator.isBlank(value)) {
			return StringPool.BLANK;
		}

		return value;
	}

}