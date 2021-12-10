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
import com.liferay.portal.kernel.settings.ParameterMapSettings;

/**
 * @author André de Oliveira
 */
public class PortletPreferencesJspUtil {

	public static String getInputName(String key) {
		return ParameterMapSettings.PREFERENCES_PREFIX + key +
			StringPool.DOUBLE_DASH;
	}

}