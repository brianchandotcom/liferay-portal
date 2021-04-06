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

package com.liferay.blogs.test.util.search;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class BlogsEntrySearchFixture {

	public BlogsEntrySearchFixture(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	public BlogsEntry addBlogsEntry(BlogsEntryBlueprint blogsEntryBlueprint) {
		try {
			ServiceContext serviceContext =
				blogsEntryBlueprint.getServiceContext();

			if (serviceContext == null) {
				serviceContext = ServiceContextTestUtil.getServiceContext(
					blogsEntryBlueprint.getGroupId());
			}

			BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
				blogsEntryBlueprint.getUserId(), blogsEntryBlueprint.getTitle(),
				blogsEntryBlueprint.getContent(), serviceContext);

			_blogsEntries.add(blogsEntry);

			return blogsEntry;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public List<BlogsEntry> getBlogsEntries() {
		return _blogsEntries;
	}

	private final List<BlogsEntry> _blogsEntries = new ArrayList<>();
	private final BlogsEntryLocalService _blogsEntryLocalService;

}