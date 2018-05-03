/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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