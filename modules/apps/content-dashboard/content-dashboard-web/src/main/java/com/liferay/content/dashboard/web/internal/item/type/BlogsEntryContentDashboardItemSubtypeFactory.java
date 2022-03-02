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

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemSubtypeFactory.class)
public class BlogsEntryContentDashboardItemSubtypeFactory
	implements ContentDashboardItemSubtypeFactory<BlogsEntry> {

	@Override
	public ContentDashboardItemSubtype<BlogsEntry> create(long classPK)
		throws PortalException {

		return new BlogsEntryContentDashboardItemSubtype();
	}

}