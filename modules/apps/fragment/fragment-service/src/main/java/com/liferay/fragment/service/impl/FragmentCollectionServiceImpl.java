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
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.base.FragmentCollectionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"json.web.service.context.name=fragment",
		"json.web.service.context.path=FragmentCollection"
	},
	service = AopService.class
)
public class FragmentCollectionServiceImpl
	extends FragmentCollectionServiceBaseImpl {

	@Override
	public FragmentCollection addFragmentCollection(
			long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

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
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentCollectionLocalService.addFragmentCollection(
			getUserId(), groupId, fragmentCollectionKey, name, description,
			serviceContext);
	}

	@Override
	public FragmentCollection deleteFragmentCollection(
			long fragmentCollectionId)
		throws PortalException {

		FragmentCollection fragmentCollection =
			fragmentCollectionLocalService.getFragmentCollection(
				fragmentCollectionId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentCollection.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentCollectionLocalService.deleteFragmentCollection(
			fragmentCollection);
	}

	@Override
	public void deleteFragmentCollections(long[] fragmentCollectionIds)
		throws PortalException {

		for (long fragmentCollectionId : fragmentCollectionIds) {
			FragmentCollection fragmentCollection =
				fragmentCollectionLocalService.getFragmentCollection(
					fragmentCollectionId);

			_portletResourcePermission.check(
				getPermissionChecker(), fragmentCollection.getGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

			fragmentCollectionLocalService.deleteFragmentCollection(
				fragmentCollection);
		}
	}

	@Override
	public FragmentCollection fetchFragmentCollection(long fragmentCollectionId)
		throws PortalException {

		return fragmentCollectionLocalService.fetchFragmentCollection(
			fragmentCollectionId);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(long groupId) {
		return fragmentCollectionPersistence.findByGroupId(groupId);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long groupId, int start, int end) {

		return fragmentCollectionPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long groupId, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {

		return fragmentCollectionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long groupId, String name, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {

		return fragmentCollectionPersistence.findByG_LikeN(
			groupId, _customSQL.keywords(name, false, WildcardMode.SURROUND)[0],
			start, end, orderByComparator);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(long[] groupIds) {
		List<FragmentCollection> fragmentCollections = new ArrayList<>();

		for (long groupId : groupIds) {
			fragmentCollections.addAll(getFragmentCollections(groupId));
		}

		return fragmentCollections;
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long[] groupIds, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {

		return fragmentCollectionPersistence.findByGroupId(
			groupIds, start, end, orderByComparator);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {

		return fragmentCollectionPersistence.findByG_LikeN(
			groupIds,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
	}

	@Override
	public int getFragmentCollectionsCount(long groupId) {
		return fragmentCollectionPersistence.countByGroupId(groupId);
	}

	@Override
	public int getFragmentCollectionsCount(long groupId, String name) {
		return fragmentCollectionPersistence.countByG_LikeN(
			groupId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0]);
	}

	@Override
	public int getFragmentCollectionsCount(long[] groupIds) {
		return fragmentCollectionPersistence.countByGroupId(groupIds);
	}

	@Override
	public int getFragmentCollectionsCount(long[] groupIds, String name) {
		return fragmentCollectionPersistence.countByG_LikeN(
			groupIds,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0]);
	}

	@Override
	public String[] getTempFileNames(long groupId, String folderName)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return _fragmentEntryLocalService.getTempFileNames(
			getUserId(), groupId, folderName);
	}

	@Override
	public FragmentCollection updateFragmentCollection(
			long fragmentCollectionId, String name, String description)
		throws PortalException {

		FragmentCollection fragmentCollection =
			fragmentCollectionLocalService.getFragmentCollection(
				fragmentCollectionId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentCollection.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentCollectionLocalService.updateFragmentCollection(
			fragmentCollectionId, name, description);
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference(
		target = "(resource.name=" + FragmentConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}