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
 */
public class BooleanQueryFactoryUtil
	extends BaseQueryFactoryUtil<BooleanQueryFactory> {

	public static BooleanQuery create(SearchContext searchContext) {
		return getBooleanQueryFactory(searchContext).create();
	}

	public static BooleanQueryFactory getBooleanQueryFactory(
		SearchContext searchContext) {

		return _instance.getQueryFactory(searchContext);
	}

	@BeanReference(type = BooleanQueryFactoryUtil.class)
	private static BooleanQueryFactoryUtil _instance;
}