/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.BeanReference;

/**
 * @author Raymond Augé
 * @author Michael C. Han
 */
public class TermRangeQueryFactoryUtil
	extends BaseQueryFactoryUtil<TermRangeQueryFactory> {

	public static TermRangeQuery create(
		SearchContext searchContext, String field, String lowerTerm,
		String upperTerm, boolean includesLower, boolean includesUpper) {

		return getTermRangeQueryFactory(searchContext).create(
			field, lowerTerm, upperTerm, includesLower, includesUpper);
	}

	public static TermRangeQueryFactory getTermRangeQueryFactory(
		SearchContext searchContext) {

		return _instance.getQueryFactory(searchContext);
	}

	@BeanReference(type = TermRangeQueryFactoryUtil.class)
	private static TermRangeQueryFactoryUtil _instance;

}