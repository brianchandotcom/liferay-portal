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
import com.liferay.blogs.uad.entity.BlogsEntryUADEntity;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import com.liferay.user.associated.data.aggregator.BaseUADEntityAggregator;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY}, service = UADEntityAggregator.class)
public class BlogsEntryUADEntityAggregator extends BaseUADEntityAggregator {
	@Override
	public int count(long userId) {
		return (int)_blogsEntryLocalService.dynamicQueryCount(_getDynamicQuery(
				userId));
	}

	@Override
	public List<UADEntity> getUADEntities(long userId, int start, int end) {
		List<BlogsEntry> blogsEntries = _blogsEntryLocalService.dynamicQuery(_getDynamicQuery(
					userId), start, end);

		List<UADEntity> uadEntities = new ArrayList<UADEntity>(blogsEntries.size());

		for (BlogsEntry blogsEntry : blogsEntries) {
			uadEntities.add(new BlogsEntryUADEntity(userId,
					_getUADEntityId(userId, blogsEntry), blogsEntry));
		}

		return uadEntities;
	}

	@Override
	public UADEntity getUADEntity(String uadEntityId) throws PortalException {
		BlogsEntry blogsEntry = _blogsEntryLocalService.getBlogsEntry(_getEntryId(
					uadEntityId));

		return new BlogsEntryUADEntity(_getUserId(uadEntityId), uadEntityId,
			blogsEntry);
	}

	@Override
	public String getUADEntitySetName() {
		return BlogsUADConstants.UAD_ENTITY_SET_NAME;
	}

	private DynamicQuery _getDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addDynamicQueryCriteria(_blogsEntryLocalService.dynamicQuery(),
			BlogsUADConstants.USER_ID_FIELD_NAMES_BLOGS_ENTRY, userId);
	}

	private long _getEntryId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split("#");

		return Long.parseLong(uadEntityIdParts[0]);
	}

	private String _getUADEntityId(long userId, BlogsEntry blogsEntry) {
		return String.valueOf(blogsEntry.getEntryId()) + "#" +
		String.valueOf(userId);
	}

	private long _getUserId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split("#");

		return Long.parseLong(uadEntityIdParts[1]);
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;
	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;
}