/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.social;

import com.liferay.petra.string.StringPool;

/**
 * @author Alejandro Tardín
 */
public class SocialBookmarksUtil {

	public static String getSocialBookmarksTypes(String socialBookmarksTypes) {
		if (StringPool.STAR.equals(socialBookmarksTypes)) {
			return null;
		}

		return socialBookmarksTypes;
	}

}