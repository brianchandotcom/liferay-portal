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

package com.liferay.blogs.uad.aggregator;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.uad.constants.BlogsUADConstants;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import com.liferay.user.associated.data.aggregator.DynamicQueryUADEntityAggregator;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY}, service = UADEntityAggregator.class)
public class BlogsEntryUADEntityAggregator
	extends DynamicQueryUADEntityAggregator<BlogsEntry> {
	@Override
	public String getApplicationName() {
		return BlogsUADConstants.APPLICATION_NAME;
	}

	@Override
	public BlogsEntry getEntity(Serializable entityId)
		throws PortalException {
		return _blogsEntryLocalService.getBlogsEntry(Long.valueOf(
				entityId.toString()));
	}

	@Override
	protected long doCount(DynamicQuery dynamicQuery) {
		return _blogsEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	protected DynamicQuery doGetDynamicQuery() {
		return _blogsEntryLocalService.dynamicQuery();
	}

	@Override
	protected List<BlogsEntry> doGetEntities(DynamicQuery dynamicQuery,
		int start, int end) {
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return BlogsUADConstants.USER_ID_FIELD_NAMES_BLOGS_ENTRY;
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;
}