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

package com.liferay.blogs.uad.anonymizer;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.uad.constants.BlogsUADConstants;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY}, service = UADAnonymizer.class)
public class BlogsEntryUADAnonymizer extends DynamicQueryUADAnonymizer<BlogsEntry> {
	@Override
	public void autoAnonymize(BlogsEntry blogsEntry, long userId)
		throws PortalException {
		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser();

		if (blogsEntry.getUserId() == userId) {
			blogsEntry.setUserId(anonymousUser.getUserId());
			blogsEntry.setUserName(anonymousUser.getFullName());
		}

		if (blogsEntry.getStatusByUserId() == userId) {
			blogsEntry.setStatusByUserId(anonymousUser.getUserId());
			blogsEntry.setStatusByUserName(anonymousUser.getFullName());
		}

		_blogsEntryLocalService.updateBlogsEntry(blogsEntry);
	}

	@Override
	public void delete(BlogsEntry blogsEntry) throws PortalException {
		_blogsEntryLocalService.deleteBlogsEntry(blogsEntry);
	}

	@Override
	public List<String> getNonanonymizableFieldNames() {
		return Arrays.asList("title", "subtitle", "urlTitle", "description",
			"content", "smallImage", "smallImageId");
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return _blogsEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return BlogsUADConstants.USER_ID_FIELD_NAMES_BLOGS_ENTRY;
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;
	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;
}