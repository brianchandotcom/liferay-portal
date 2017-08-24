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

package com.liferay.modern.site.building.fragment.service.impl;

import com.liferay.modern.site.building.fragment.constants.MSBFragmentActionKeys;
import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;
import com.liferay.modern.site.building.fragment.service.base.MSBFragmentCollectionServiceBaseImpl;
import com.liferay.modern.site.building.fragment.service.permission.MSBFragmentCollectionPermission;
import com.liferay.modern.site.building.fragment.service.permission.MSBFragmentPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class MSBFragmentCollectionServiceImpl
	extends MSBFragmentCollectionServiceBaseImpl {

	@Override
	public MSBFragmentCollection addFragmentCollection(
			long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		MSBFragmentPermission.check(
			getPermissionChecker(), groupId,
			MSBFragmentActionKeys.ADD_MSB_FRAGMENT_COLLECTION);

		return msbFragmentCollectionLocalService.addFragmentCollection(
			groupId, getUserId(), name, description, serviceContext);
	}

	@Override
	public MSBFragmentCollection deleteFragmentCollection(
			long fragmentCollectionId)
		throws PortalException {

		MSBFragmentCollectionPermission.check(
			getPermissionChecker(), fragmentCollectionId, ActionKeys.DELETE);

		return msbFragmentCollectionLocalService.deleteMSBFragmentCollection(
			fragmentCollectionId);
	}

	@Override
	public List<MSBFragmentCollection> deleteFragmentCollections(
			long[] fragmentCollectionIds)
		throws PortalException {

		List<MSBFragmentCollection> failedFragmentCollections =
			new ArrayList<>();

		for (long fragmentCollectionId : fragmentCollectionIds) {
			try {
				MSBFragmentCollectionPermission.check(
					getPermissionChecker(), fragmentCollectionId,
					ActionKeys.DELETE);

				msbFragmentCollectionLocalService.deleteMSBFragmentCollection(
					fragmentCollectionId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				MSBFragmentCollection fragmentCollection =
					msbFragmentCollectionPersistence.fetchByPrimaryKey(
						fragmentCollectionId);

				failedFragmentCollections.add(fragmentCollection);
			}
		}

		return failedFragmentCollections;
	}

	@Override
	public MSBFragmentCollection fetchFragmentCollection(
			long fragmentCollectionId)
		throws PortalException {

		return msbFragmentCollectionLocalService.fetchMSBFragmentCollection(
			fragmentCollectionId);
	}

	@Override
	public List<MSBFragmentCollection> getFragmentCollections(
			long groupId, int start, int end)
		throws PortalException {

		return msbFragmentCollectionLocalService.getFragmentCollections(
			groupId, start, end);
	}

	@Override
	public List<MSBFragmentCollection> getFragmentCollections(
			long groupId, int start, int end,
			OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws PortalException {

		return msbFragmentCollectionLocalService.getFragmentCollections(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<MSBFragmentCollection> getFragmentCollections(
			long groupId, String name, int start, int end,
			OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws PortalException {

		return msbFragmentCollectionLocalService.getFragmentCollections(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public int getGroupFragmentCollectionsCount(long groupId) {
		return msbFragmentCollectionPersistence.countByGroupId(groupId);
	}

	@Override
	public MSBFragmentCollection updateFragmentCollection(
			long fragmentCollectionId, String name, String description)
		throws PortalException {

		MSBFragmentCollectionPermission.check(
			getPermissionChecker(), fragmentCollectionId, ActionKeys.UPDATE);

		return msbFragmentCollectionLocalService.updateFragmentCollection(
			fragmentCollectionId, name, description);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MSBFragmentCollectionServiceImpl.class);

}