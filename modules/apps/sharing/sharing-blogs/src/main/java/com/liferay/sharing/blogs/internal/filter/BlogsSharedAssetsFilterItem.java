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

package com.liferay.sharing.blogs.internal.filter;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.sharing.filter.SharedAssetsFilterItem;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, property = "navigation.item.order:Integer=1000",
	service = SharedAssetsFilterItem.class
)
public class BlogsSharedAssetsFilterItem implements SharedAssetsFilterItem {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

}