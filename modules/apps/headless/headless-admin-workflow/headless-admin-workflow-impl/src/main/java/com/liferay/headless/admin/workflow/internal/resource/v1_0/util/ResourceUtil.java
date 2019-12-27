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

package com.liferay.headless.admin.workflow.internal.resource.v1_0.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

/**
 * @author Rafael Praxedes
 */
public class ResourceUtil {

	public static int getEndPosition(Pagination pagination) {
		if (pagination == null) {
			return QueryUtil.ALL_POS;
		}

		return pagination.getEndPosition();
	}

	public static int getStartPosition(Pagination pagination) {
		if (pagination == null) {
			return QueryUtil.ALL_POS;
		}

		return pagination.getStartPosition();
	}

}