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
import com.liferay.blogs.uad.entity.BlogsEntryUADEntity;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.BaseUADEntityAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY}, service = UADEntityAnonymizer.class)
public class BlogsEntryUADEntityAnonymizer extends BaseUADEntityAnonymizer {
	@Override
	public void autoAnonymize(UADEntity uadEntity) throws PortalException {
		BlogsEntry blogsEntry = _getBlogsEntry(uadEntity);

		_autoAnonymize(blogsEntry, uadEntity.getUserId());
	}

	@Override
	public void autoAnonymizeAll(final long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery = _getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<BlogsEntry>() {
				@Override
				public void performAction(BlogsEntry blogsEntry)
					throws PortalException {
					_autoAnonymize(blogsEntry, userId);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void delete(UADEntity uadEntity) throws PortalException {
		_blogsEntryLocalService.deleteBlogsEntry(_getBlogsEntry(uadEntity));
	}

	@Override
	public void deleteAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery = _getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<BlogsEntry>() {
				@Override
				public void performAction(BlogsEntry blogsEntry)
					throws PortalException {
					_blogsEntryLocalService.deleteBlogsEntry(blogsEntry);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
	}

	@Override
	public List<String> getUADEntityNonanonymizableFieldNames() {
		return Arrays.asList("title", "subtitle", "urlTitle", "description",
			"content", "smallImage", "smallImageId");
	}

	private void _autoAnonymize(BlogsEntry blogsEntry, long userId)
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

	private ActionableDynamicQuery _getActionableDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addActionableDynamicQueryCriteria(_blogsEntryLocalService.getActionableDynamicQuery(),
			BlogsUADConstants.USER_ID_FIELD_NAMES_BLOGS_ENTRY, userId);
	}

	private BlogsEntry _getBlogsEntry(UADEntity uadEntity)
		throws PortalException {
		_validate(uadEntity);

		BlogsEntryUADEntity blogsEntryUADEntity = (BlogsEntryUADEntity)uadEntity;

		return blogsEntryUADEntity.getBlogsEntry();
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof BlogsEntryUADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;
	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;
	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;
	@Reference(target = "(model.class.name=" +
	BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY + ")")
	private UADEntityAggregator _uadEntityAggregator;
}