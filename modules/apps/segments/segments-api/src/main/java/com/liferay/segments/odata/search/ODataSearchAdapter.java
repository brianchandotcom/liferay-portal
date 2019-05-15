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

package com.liferay.segments.odata.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.FilterParser;

import java.util.Locale;

/**
 * @author Eduardo García
 */
public interface ODataSearchAdapter {

	public Hits search(
			long companyId, FilterParser filterParser, String filterString,
			String className, EntityModel entityModel, Locale locale, int start,
			int end)
		throws PortalException;

	public int searchCount(
			long companyId, FilterParser filterParser, String filterString,
			String className, EntityModel entityModel, Locale locale)
		throws PortalException;

}