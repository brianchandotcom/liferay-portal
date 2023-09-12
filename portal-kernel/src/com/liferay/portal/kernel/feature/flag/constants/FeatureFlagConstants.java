/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.feature.flag.constants;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Drew Brokke
 */
public class FeatureFlagConstants {

	public static final String FEATURE_FLAG = "feature.flag";

	public static String getKey(String... parts) {
		if (ArrayUtil.isEmpty(parts)) {
			return FEATURE_FLAG;
		}

		return StringBundler.concat(
			FEATURE_FLAG, StringPool.PERIOD,
			StringUtil.merge(parts, StringPool.PERIOD));
	}

}