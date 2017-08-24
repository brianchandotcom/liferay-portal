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
	public MSBFragmentEntry addFragmentEntry(
			long groupId, long msbFragmentCollectionId, String name, String css,
			String html, String js, ServiceContext serviceContext)
		throws PortalException {

		MSBFragmentPermission.check(
			getPermissionChecker(), groupId,
			MSBFragmentActionKeys.ADD_MSB_FRAGMENT_ENTRY);

		return msbFragmentEntryLocalService.addFragmentEntry(
			groupId, getUserId(), msbFragmentCollectionId, name, css, html, js,
			serviceContext);
	}

	@Override
	public List<MSBFragmentEntry> deleteFragmentEntries(
			long[] fragmentEntriesIds)
		throws PortalException {

		List<MSBFragmentEntry> failedFragmentEntries = new ArrayList<>();

		for (long fragmentEntryId : fragmentEntriesIds) {
			try {
				MSBFragmentEntryPermission.check(
					getPermissionChecker(), fragmentEntryId, ActionKeys.DELETE);

				msbFragmentEntryLocalService.deleteMSBFragmentEntry(
					fragmentEntryId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				MSBFragmentEntry mSBFragmentEntry =
					msbFragmentEntryPersistence.fetchByPrimaryKey(
						fragmentEntryId);

				failedFragmentEntries.add(mSBFragmentEntry);
			}
		}

		return failedFragmentEntries;
	}

	@Override
	public MSBFragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException {

		MSBFragmentEntryPermission.check(
			getPermissionChecker(), fragmentEntryId, ActionKeys.DELETE);

		return msbFragmentEntryLocalService.deleteMSBFragmentEntry(
			fragmentEntryId);
	}

	@Override
	public List<MSBFragmentEntry> fetchFragmentEntries(
			long msbFragmentCollectionId)
		throws PortalException {

		return msbFragmentEntryLocalService.fetchFragmentEntries(
			msbFragmentCollectionId);
	}

	@Override
	public MSBFragmentEntry fetchFragmentEntry(long fragmentEntryId) {
		return msbFragmentEntryLocalService.fetchMSBFragmentEntry(
			fragmentEntryId);
	}

	@Override
	public List<MSBFragmentEntry> getFragmentEntries(
			long msbFragmentCollectionId, int start, int end)
		throws PortalException {

		return msbFragmentEntryLocalService.getFragmentEntries(
			msbFragmentCollectionId, start, end);
	}

	@Override
	public List<MSBFragmentEntry> getFragmentEntries(
			long groupId, long msbFragmentCollectionId, int start, int end,
			OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws PortalException {

		return msbFragmentEntryLocalService.getFragmentEntries(
			groupId, msbFragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<MSBFragmentEntry> getFragmentEntries(
		long groupId, long msbFragmentCollectionId, String name, int start,
		int end, OrderByComparator<MSBFragmentEntry> obc) {

		return msbFragmentEntryLocalService.getFragmentEntries(
			groupId, msbFragmentCollectionId, name, start, end, obc);
	}

	@Override
	public int getGroupFragmentCollectionsCount(long msbFragmentCollectionId) {
		return msbFragmentEntryPersistence.countByMSBFragmentCollectionId(
			msbFragmentCollectionId);
	}

	@Override
	public MSBFragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js)
		throws PortalException {

		MSBFragmentEntryPermission.check(
			getPermissionChecker(), fragmentEntryId, ActionKeys.UPDATE);

		return msbFragmentEntryLocalService.updateFragmentEntry(
			fragmentEntryId, name, css, html, js);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MSBFragmentEntryServiceImpl.class);

}