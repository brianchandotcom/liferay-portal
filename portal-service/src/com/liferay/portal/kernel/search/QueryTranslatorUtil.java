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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Raymond Augé
 */
public class QueryTranslatorUtil {

	public static QueryTranslator getQueryTranslator() {
		PortalRuntimePermission.checkGetBeanProperty(QueryTranslatorUtil.class);

		return _queryTranslator;
	}

	public static Object translate(Query query) throws ParseException {
		return getQueryTranslator().translate(query);
	}

	public static Object translateForSolr(Query query) throws ParseException {
		return getQueryTranslator().translateForSolr(query);
	}

	public void setQueryTranslator(QueryTranslator queryTranslator) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_queryTranslator = queryTranslator;
	}

	private static QueryTranslator _queryTranslator;

}