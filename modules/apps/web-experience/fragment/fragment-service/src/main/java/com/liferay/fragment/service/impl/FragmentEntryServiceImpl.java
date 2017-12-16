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

package com.liferay.fragment.service.impl;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.base.FragmentEntryServiceBaseImpl;
import com.liferay.fragment.service.permission.FragmentEntryPermission;
import com.liferay.fragment.service.permission.FragmentPermission;
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
public class FragmentEntryServiceImpl extends FragmentEntryServiceBaseImpl {

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, String css,
			String html, String js, ServiceContext serviceContext)
		throws PortalException {

		FragmentPermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.ADD_FRAGMENT_ENTRY);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, name, css, html, js,
			serviceContext);
	}

	@Override
	public List<FragmentEntry> deleteFragmentEntries(long[] fragmentEntriesIds)
		throws PortalException {

		List<FragmentEntry> undeletableFragmentEntries = new ArrayList<>();

		for (long fragmentEntryId : fragmentEntriesIds) {
			try {
				FragmentEntryPermission.check(
					getPermissionChecker(), fragmentEntryId, ActionKeys.DELETE);

				fragmentEntryLocalService.deleteFragmentEntry(fragmentEntryId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				FragmentEntry fragmentEntry =
					fragmentEntryPersistence.fetchByPrimaryKey(fragmentEntryId);

				undeletableFragmentEntries.add(fragmentEntry);
			}
		}

		return undeletableFragmentEntries;
	}

	@Override
	public FragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException {

		FragmentEntryPermission.check(
			getPermissionChecker(), fragmentEntryId, ActionKeys.DELETE);

		return fragmentEntryLocalService.deleteFragmentEntry(fragmentEntryId);
	}

	@Override
	public List<FragmentEntry> fetchFragmentEntries(long fragmentCollectionId)
		throws PortalException {

		return fragmentEntryLocalService.fetchFragmentEntries(
			fragmentCollectionId);
	}

	@Override
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.fetchFragmentEntry(fragmentEntryId);

		if (fragmentEntry != null) {
			FragmentEntryPermission.check(
				getPermissionChecker(), fragmentEntry, ActionKeys.VIEW);
		}

		return fragmentEntry;
	}

	@Override
	public int getFragmentCollectionsCount(
		long groupId, long fragmentCollectionId) {

		return fragmentEntryPersistence.filterCountByG_FCI(
			groupId, fragmentCollectionId);
	}

	@Override
	public int getFragmentCollectionsCount(
		long groupId, long fragmentCollectionId, String name) {

		return fragmentEntryPersistence.filterCountByG_FCI_LikeN(
			groupId, fragmentCollectionId, name);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
			long groupId, long fragmentCollectionId, int start, int end)
		throws PortalException {

		return fragmentEntryPersistence.filterFindByG_FCI(
			groupId, fragmentCollectionId, start, end);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
			long groupId, long fragmentCollectionId, int start, int end,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws PortalException {

		return fragmentEntryPersistence.filterFindByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.filterFindByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, start, end, orderByComparator);
	}

	@Override
	public FragmentEntry updateFragmentEntry(long fragmentEntryId, String name)
		throws PortalException {

		FragmentEntryPermission.check(
			getPermissionChecker(), fragmentEntryId, ActionKeys.UPDATE);

		return fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntryId, name);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, ServiceContext serviceContext)
		throws PortalException {

		FragmentEntryPermission.check(
			getPermissionChecker(), fragmentEntryId, ActionKeys.UPDATE);

		return fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntryId, name, css, html, js, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryServiceImpl.class);

}