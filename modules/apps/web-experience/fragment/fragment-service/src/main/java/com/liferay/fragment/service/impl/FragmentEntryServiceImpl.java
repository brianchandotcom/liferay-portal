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
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.base.FragmentEntryServiceBaseImpl;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class FragmentEntryServiceImpl extends FragmentEntryServiceBaseImpl {

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, int status,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.ADD_FRAGMENT_ENTRY);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, name, status,
			serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, int status, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.ADD_FRAGMENT_ENTRY);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, fragmentEntryKey, name,
			status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, String css,
			String html, String js, int status, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.ADD_FRAGMENT_ENTRY);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, name, css, html, js,
			status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js, int status,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.ADD_FRAGMENT_ENTRY);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, fragmentEntryKey, name,
			css, html, js, status, serviceContext);
	}

	@Override
	public void deleteFragmentEntries(long[] fragmentEntriesIds)
		throws PortalException {

		for (long fragmentEntryId : fragmentEntriesIds) {
			_fragmentEntryModelResourcePermission.check(
				getPermissionChecker(), fragmentEntryId, ActionKeys.DELETE);

			fragmentEntryLocalService.deleteFragmentEntry(fragmentEntryId);
		}
	}

	@Override
	public FragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException {

		_fragmentEntryModelResourcePermission.check(
			getPermissionChecker(), fragmentEntryId, ActionKeys.DELETE);

		return fragmentEntryLocalService.deleteFragmentEntry(fragmentEntryId);
	}

	@Override
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.fetchFragmentEntry(fragmentEntryId);

		if (fragmentEntry != null) {
			_fragmentEntryModelResourcePermission.check(
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
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, WildcardMode.SURROUND)[0]);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(long fragmentCollectionId) {
		return fragmentEntryLocalService.getFragmentEntries(
			fragmentCollectionId);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long fragmentCollectionId, int status) {

		return fragmentEntryLocalService.getFragmentEntries(
			fragmentCollectionId, status);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end) {

		return fragmentEntryPersistence.filterFindByG_FCI(
			groupId, fragmentCollectionId, start, end);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.filterFindByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.filterFindByG_FCI_LikeN(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, WildcardMode.SURROUND)[0], start, end,
			orderByComparator);
	}

	@Override
	public String[] getTempFileNames(long groupId, String folderName)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.ADD_FRAGMENT_ENTRY);

		return fragmentEntryLocalService.getTempFileNames(
			getUserId(), groupId, folderName);
	}

	@Override
	public FragmentEntry updateFragmentEntry(long fragmentEntryId, String name)
		throws PortalException {

		_fragmentEntryModelResourcePermission.check(
			getPermissionChecker(), fragmentEntryId, ActionKeys.UPDATE);

		return fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntryId, name);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, int status, ServiceContext serviceContext)
		throws PortalException {

		_fragmentEntryModelResourcePermission.check(
			getPermissionChecker(), fragmentEntryId, ActionKeys.UPDATE);

		return fragmentEntryLocalService.updateFragmentEntry(
			getUserId(), fragmentEntryId, name, css, html, js, status,
			serviceContext);
	}

	private static volatile ModelResourcePermission<FragmentEntry>
		_fragmentEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				FragmentEntryServiceImpl.class,
				"_fragmentEntryModelResourcePermission", FragmentEntry.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				FragmentEntryServiceImpl.class, "_portletResourcePermission",
				FragmentConstants.RESOURCE_NAME);

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}