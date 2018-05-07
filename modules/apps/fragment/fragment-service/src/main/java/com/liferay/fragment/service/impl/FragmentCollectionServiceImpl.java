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
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.base.FragmentCollectionServiceBaseImpl;
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
public class FragmentCollectionServiceImpl
	extends FragmentCollectionServiceBaseImpl {

	@Override
	public FragmentCollection addFragmentCollection(
			long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.ADD_FRAGMENT_COLLECTION);

		return fragmentCollectionLocalService.addFragmentCollection(
			getUserId(), groupId, name, description, serviceContext);
	}

	@Override
	public FragmentCollection addFragmentCollection(
			long groupId, String fragmentCollectionKey, String name,
			String description, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.ADD_FRAGMENT_COLLECTION);

		return fragmentCollectionLocalService.addFragmentCollection(
			getUserId(), groupId, fragmentCollectionKey, name, description,
			serviceContext);
	}

	@Override
	public FragmentCollection deleteFragmentCollection(
			long fragmentCollectionId)
		throws PortalException {

		_fragmentCollectionModelResourcePermission.check(
			getPermissionChecker(), fragmentCollectionId, ActionKeys.DELETE);

		return fragmentCollectionLocalService.deleteFragmentCollection(
			fragmentCollectionId);
	}

	@Override
	public void deleteFragmentCollections(long[] fragmentCollectionIds)
		throws PortalException {

		for (long fragmentCollectionId : fragmentCollectionIds) {
			_fragmentCollectionModelResourcePermission.check(
				getPermissionChecker(), fragmentCollectionId,
				ActionKeys.DELETE);

			fragmentCollectionLocalService.deleteFragmentCollection(
				fragmentCollectionId);
		}
	}

	@Override
	public FragmentCollection fetchFragmentCollection(long fragmentCollectionId)
		throws PortalException {

		FragmentCollection fragmentCollection =
			fragmentCollectionLocalService.fetchFragmentCollection(
				fragmentCollectionId);

		if (fragmentCollection != null) {
			_fragmentCollectionModelResourcePermission.check(
				getPermissionChecker(), fragmentCollection, ActionKeys.VIEW);
		}

		return fragmentCollection;
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(long groupId) {
		return fragmentCollectionPersistence.filterFindByGroupId(groupId);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long groupId, int start, int end) {

		return fragmentCollectionPersistence.filterFindByGroupId(
			groupId, start, end);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long groupId, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {

		return fragmentCollectionPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long groupId, String name, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {

		return fragmentCollectionPersistence.filterFindByG_LikeN(
			groupId, _customSQL.keywords(name, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
	}

	@Override
	public int getFragmentCollectionsCount(long groupId) {
		return fragmentCollectionPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public int getFragmentCollectionsCount(long groupId, String name) {
		return fragmentCollectionPersistence.filterCountByG_LikeN(
			groupId, _customSQL.keywords(name, WildcardMode.SURROUND)[0]);
	}

	@Override
	public String[] getTempFileNames(long groupId, String folderName)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.ADD_FRAGMENT_COLLECTION);

		return fragmentEntryLocalService.getTempFileNames(
			getUserId(), groupId, folderName);
	}

	@Override
	public FragmentCollection updateFragmentCollection(
			long fragmentCollectionId, String name, String description)
		throws PortalException {

		_fragmentCollectionModelResourcePermission.check(
			getPermissionChecker(), fragmentCollectionId, ActionKeys.UPDATE);

		return fragmentCollectionLocalService.updateFragmentCollection(
			fragmentCollectionId, name, description);
	}

	private static volatile ModelResourcePermission<FragmentCollection>
		_fragmentCollectionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				FragmentCollectionServiceImpl.class,
				"_fragmentCollectionModelResourcePermission",
				FragmentCollection.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				FragmentCollectionServiceImpl.class,
				"_portletResourcePermission", FragmentConstants.RESOURCE_NAME);

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}