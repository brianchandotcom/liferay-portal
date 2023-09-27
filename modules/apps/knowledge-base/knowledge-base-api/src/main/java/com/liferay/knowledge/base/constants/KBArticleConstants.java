/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.constants;

import com.liferay.knowledge.base.model.KBArticle;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class KBArticleConstants {

	public static final long DEFAULT_PARENT_RESOURCE_PRIM_KEY = 0;

	public static final double DEFAULT_PRIORITY = 1.0;

	public static final int DEFAULT_VERSION = 1;

	public static String getClassName() {
		return KBArticle.class.getName();
	}

}