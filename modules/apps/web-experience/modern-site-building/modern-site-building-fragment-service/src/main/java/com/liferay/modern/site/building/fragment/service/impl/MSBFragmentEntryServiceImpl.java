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
import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.service.base.MSBFragmentEntryServiceBaseImpl;
import com.liferay.modern.site.building.fragment.service.permission.MSBFragmentEntryPermission;
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
public class MSBFragmentEntryServiceImpl
	extends MSBFragmentEntryServiceBaseImpl {

	@Override
	public MSBFragmentEntry addMSBFragmentEntry(
			long groupId, long msbFragmentCollectionId, String name, String css,
			String html, String js, ServiceContext serviceContext)
		throws PortalException {

		MSBFragmentPermission.check(
			getPermissionChecker(), groupId,
			MSBFragmentActionKeys.ADD_MSB_FRAGMENT_ENTRY);

		return msbFragmentEntryLocalService.addMSBFragmentEntry(
			groupId, getUserId(), msbFragmentCollectionId, name, css, html, js,
			serviceContext);
	}

	@Override
	public List<MSBFragmentEntry> deleteMSBFragmentEntries(
			long[] msbFragmentEntriesIds)
		throws PortalException {

		List<MSBFragmentEntry> undeletableMSBFragmentEntries =
			new ArrayList<>();

		for (long msbFragmentEntryId : msbFragmentEntriesIds) {
			try {
				MSBFragmentEntryPermission.check(
					getPermissionChecker(), msbFragmentEntryId,
					ActionKeys.DELETE);

				msbFragmentEntryLocalService.deleteMSBFragmentEntry(
					msbFragmentEntryId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				MSBFragmentEntry mSBFragmentEntry =
					msbFragmentEntryPersistence.fetchByPrimaryKey(
						msbFragmentEntryId);

				undeletableMSBFragmentEntries.add(mSBFragmentEntry);
			}
		}

		return undeletableMSBFragmentEntries;
	}

	@Override
	public MSBFragmentEntry deleteMSBFragmentEntry(long msbFragmentEntryId)
		throws PortalException {

		MSBFragmentEntryPermission.check(
			getPermissionChecker(), msbFragmentEntryId, ActionKeys.DELETE);

		return msbFragmentEntryLocalService.deleteMSBFragmentEntry(
			msbFragmentEntryId);
	}

	@Override
	public List<MSBFragmentEntry> fetchMSBFragmentEntries(
			long msbFragmentCollectionId)
		throws PortalException {

		return msbFragmentEntryLocalService.fetchMSBFragmentEntries(
			msbFragmentCollectionId);
	}

	@Override
	public MSBFragmentEntry fetchMSBFragmentEntry(long msbFragmentEntryId) {
		return msbFragmentEntryLocalService.fetchMSBFragmentEntry(
			msbFragmentEntryId);
	}

	@Override
	public int getMSBFragmentCollectionsCount(long msbFragmentCollectionId) {
		return msbFragmentEntryPersistence.countByMSBFragmentCollectionId(
			msbFragmentCollectionId);
	}

	@Override
	public List<MSBFragmentEntry> getMSBFragmentEntries(
			long msbFragmentCollectionId, int start, int end)
		throws PortalException {

		return msbFragmentEntryLocalService.getMSBFragmentEntries(
			msbFragmentCollectionId, start, end);
	}

	@Override
	public List<MSBFragmentEntry> getMSBFragmentEntries(
			long groupId, long msbFragmentCollectionId, int start, int end,
			OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws PortalException {

		return msbFragmentEntryLocalService.getMSBFragmentEntries(
			groupId, msbFragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<MSBFragmentEntry> getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, String name, int start,
		int end, OrderByComparator<MSBFragmentEntry> orderByComparator) {

		return msbFragmentEntryLocalService.getMSBFragmentEntries(
			groupId, msbFragmentCollectionId, name, start, end,
			orderByComparator);
	}

	@Override
	public MSBFragmentEntry updateMSBFragmentEntry(
			long msbFragmentEntryId, String name, String css, String html,
			String js)
		throws PortalException {

		MSBFragmentEntryPermission.check(
			getPermissionChecker(), msbFragmentEntryId, ActionKeys.UPDATE);

		return msbFragmentEntryLocalService.updateMSBFragmentEntry(
			msbFragmentEntryId, name, css, html, js);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MSBFragmentEntryServiceImpl.class);

}