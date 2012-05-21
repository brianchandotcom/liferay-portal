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

package com.liferay.portal.kernel.repository.cmis.search;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Mika Koivisto
 */
public class CMISSearchQueryBuilderUtil {

	public static String buildQuery(SearchContext searchContext, Query query)
		throws SearchException {

		return getCmisSearchQueryBuilder().buildQuery(searchContext, query);
	}

	public static CMISSearchQueryBuilder getCmisSearchQueryBuilder() {
		PortalRuntimePermission.checkGetBeanProperty(
			CMISSearchQueryBuilderUtil.class);

		return _cmisSearchQueryBuilder;
	}

	public void setCmisSearchQueryBuilder(
		CMISSearchQueryBuilder cmisSearchQueryBuilder) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_cmisSearchQueryBuilder = cmisSearchQueryBuilder;
	}

	private static CMISSearchQueryBuilder _cmisSearchQueryBuilder;

}