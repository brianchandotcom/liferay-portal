/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.model;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Alexander Chow
 */
public class WikiPageConstants {

	public static final String BASE_ATTACHMENTS_DIR = "wiki/";

	public static final String DEFAULT_FORMAT = PropsUtil.get(
		PropsKeys.WIKI_FORMATS_DEFAULT);

	public static final String[] FORMATS = PropsUtil.getArray(
		PropsKeys.WIKI_FORMATS);

	public static final String FRONT_PAGE = PropsUtil.get(
		PropsKeys.WIKI_FRONT_PAGE_NAME);

	public static final String MOVED = "Moved";

	public static final String NEW = "New";

	public static final String REVERTED = "Reverted";

	public static final double VERSION_DEFAULT = 1.0;

}