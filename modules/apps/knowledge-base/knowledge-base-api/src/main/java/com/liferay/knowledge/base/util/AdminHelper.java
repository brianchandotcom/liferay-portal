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

package com.liferay.knowledge.base.util;

import com.liferay.portal.kernel.diff.DiffVersionsInfo;

/**
 * @author Lance Ji
 */
public interface AdminHelper {

	public String[] escapeSections(String[] sections);

	public DiffVersionsInfo getDiffVersionsInfo(
		long groupId, long kbArticleResourcePrimKey, int sourceVersion,
		int targetVersion);

	public String getKBArticleDiff(
			long resourcePrimKey, int sourceVersion, int targetVersion,
			String param)
		throws Exception;

	public String[] unescapeSections(String sections);

}