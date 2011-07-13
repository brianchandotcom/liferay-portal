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
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 */
public class TermQueryFactoryUtil
	extends BaseQueryFactoryUtil<TermQueryFactory> {

	public static TermQuery create(
		SearchContext searchContext, String field, long value) {

		return getTermQueryFactory(searchContext).create(field, value);
	}

	public static TermQuery create(
		SearchContext searchContext, String field, String value) {

		return getTermQueryFactory(searchContext).create(field, value);
	}

	public static TermQueryFactory getTermQueryFactory(
		SearchContext searchContext) {

		return _instance.getQueryFactory(searchContext);
	}

	@BeanReference(type = TermQueryFactoryUtil.class)
	private static TermQueryFactoryUtil _instance;

}