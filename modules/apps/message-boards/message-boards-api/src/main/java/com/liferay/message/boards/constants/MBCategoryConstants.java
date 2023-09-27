/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.constants;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Alexander Chow
 * @author Sergio González
 */
public class MBCategoryConstants {

	public static final String DEFAULT_DISPLAY_STYLE = PropsUtil.get(
		PropsKeys.MESSAGE_BOARDS_CATEGORY_DISPLAY_STYLES_DEFAULT);

	public static final long DEFAULT_PARENT_CATEGORY_ID = 0;

	public static final long DISCUSSION_CATEGORY_ID = -1;

	public static final String[] DISPLAY_STYLES = PropsUtil.getArray(
		PropsKeys.MESSAGE_BOARDS_CATEGORY_DISPLAY_STYLES);

}