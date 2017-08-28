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
	public MSBFragmentCollection addMSBFragmentCollection(
			long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		MSBFragmentPermission.check(
			getPermissionChecker(), groupId,
			MSBFragmentActionKeys.ADD_MSB_FRAGMENT_COLLECTION);

		return msbFragmentCollectionLocalService.addMSBFragmentCollection(
			groupId, getUserId(), name, description, serviceContext);
	}

	@Override
	public MSBFragmentCollection deleteMSBFragmentCollection(
			long msbFragmentCollectionId)
		throws PortalException {

		MSBFragmentCollectionPermission.check(
			getPermissionChecker(), msbFragmentCollectionId, ActionKeys.DELETE);

		return msbFragmentCollectionLocalService.deleteMSBFragmentCollection(
			msbFragmentCollectionId);
	}

	@Override
	public List<MSBFragmentCollection> deleteMSBFragmentCollections(
			long[] msbFragmentCollectionIds)
		throws PortalException {

		List<MSBFragmentCollection> undeletableMSBFragmentCollections =
			new ArrayList<>();

		for (long msbFragmentCollectionId : msbFragmentCollectionIds) {
			try {
				MSBFragmentCollectionPermission.check(
					getPermissionChecker(), msbFragmentCollectionId,
					ActionKeys.DELETE);

				msbFragmentCollectionLocalService.deleteMSBFragmentCollection(
					msbFragmentCollectionId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				MSBFragmentCollection msbFragmentCollection =
					msbFragmentCollectionPersistence.fetchByPrimaryKey(
						msbFragmentCollectionId);

				undeletableMSBFragmentCollections.add(msbFragmentCollection);
			}
		}

		return undeletableMSBFragmentCollections;
	}

	@Override
	public MSBFragmentCollection fetchMSBFragmentCollection(
			long msbFragmentCollectionId)
		throws PortalException {

		MSBFragmentCollection msbFragmentCollection =
			msbFragmentCollectionLocalService.fetchMSBFragmentCollection(
				msbFragmentCollectionId);

		if (msbFragmentCollection != null) {
			MSBFragmentCollectionPermission.check(
				getPermissionChecker(), msbFragmentCollection, ActionKeys.VIEW);
		}

		return msbFragmentCollectionLocalService.fetchMSBFragmentCollection(
			msbFragmentCollectionId);
	}

	@Override
	public List<MSBFragmentCollection> getMSBFragmentCollections(
			long groupId, int start, int end)
		throws PortalException {

		return msbFragmentCollectionPersistence.filterFindByGroupId(
			groupId, start, end);
	}

	@Override
	public List<MSBFragmentCollection> getMSBFragmentCollections(
			long groupId, int start, int end,
			OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws PortalException {

		return msbFragmentCollectionPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<MSBFragmentCollection> getMSBFragmentCollections(
			long groupId, String name, int start, int end,
			OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws PortalException {

		return msbFragmentCollectionPersistence.filterFindByG_LikeN(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public int getMSBFragmentCollectionsCount(long groupId) {
		return msbFragmentCollectionPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public int getMSBFragmentCollectionsCount(long groupId, String name) {
		return msbFragmentCollectionPersistence.filterCountByG_LikeN(
			groupId, name);
	}

	@Override
	public MSBFragmentCollection updateMSBFragmentCollection(
			long msbFragmentCollectionId, String name, String description)
		throws PortalException {

		MSBFragmentCollectionPermission.check(
			getPermissionChecker(), msbFragmentCollectionId, ActionKeys.UPDATE);

		return msbFragmentCollectionLocalService.updateMSBFragmentCollection(
			msbFragmentCollectionId, name, description);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MSBFragmentCollectionServiceImpl.class);

}