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

import com.liferay.modern.site.building.fragment.constants.FragmentActionKeys;
import com.liferay.modern.site.building.fragment.model.FragmentEntry;
import com.liferay.modern.site.building.fragment.service.base.FragmentEntryServiceBaseImpl;
import com.liferay.modern.site.building.fragment.service.permission.FragmentEntryPermission;
import com.liferay.modern.site.building.fragment.service.permission.FragmentPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the fragment entry remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.modern.site.building.fragment.service.FragmentEntryService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryServiceBaseImpl
 * @see com.liferay.modern.site.building.fragment.service.FragmentEntryServiceUtil
 */
public class FragmentEntryServiceImpl extends FragmentEntryServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.modern.site.building.fragment.service.FragmentEntryServiceUtil} to access the fragment entry remote service.
	 */

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

		List<FragmentEntry> failedFragmentEntries = new ArrayList<>();

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

				failedFragmentEntries.add(fragmentEntry);
			}
		}

		return failedFragmentEntries;
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
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId) {
		return fragmentEntryLocalService.fetchFragmentEntry(fragmentEntryId);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
			long fragmentCollectionId, int start, int end)
		throws PortalException {

		return fragmentEntryLocalService.getFragmentEntries(
			fragmentCollectionId, start, end);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
			long groupId, long fragmentCollectionId, int start, int end,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws PortalException {

		return fragmentEntryLocalService.getFragmentEntries(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> obc) {

		return fragmentEntryLocalService.getFragmentEntries(
			groupId, fragmentCollectionId, name, start, end, obc);
	}

	@Override
	public int getGroupFragmentCollectionsCount(long fragmentCollectionId) {
		return fragmentEntryPersistence.countByFragmentCollectionId(
			fragmentCollectionId);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js)
		throws PortalException {

		FragmentEntryPermission.check(
			getPermissionChecker(), fragmentEntryId, ActionKeys.UPDATE);

		return fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntryId, name, css, html, js);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryServiceImpl.class);

}