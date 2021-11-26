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
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.service.base.FragmentCompositionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"json.web.service.context.name=fragment",
		"json.web.service.context.path=FragmentComposition"
	},
	service = AopService.class
)
public class FragmentCompositionServiceImpl
	extends FragmentCompositionServiceBaseImpl {

	@Override
	public FragmentComposition addFragmentComposition(
			long groupId, long fragmentCollectionId,
			String fragmentCompositionKey, String name, String description,
			String data, long previewFileEntryId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentCompositionLocalService.addFragmentComposition(
			getUserId(), groupId, fragmentCollectionId, fragmentCompositionKey,
			name, description, data, previewFileEntryId, status,
			serviceContext);
	}

	@Override
	public FragmentComposition deleteFragmentComposition(
			long fragmentCompositionId)
		throws PortalException {

		FragmentComposition fragmentComposition =
			fragmentCompositionPersistence.findByPrimaryKey(
				fragmentCompositionId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentComposition.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentCompositionLocalService.deleteFragmentComposition(
			fragmentCompositionId);
	}

	@Override
	public FragmentComposition fetchFragmentComposition(
		long fragmentCompositionId) {

		return fragmentCompositionPersistence.fetchByPrimaryKey(
			fragmentCompositionId);
	}

	@Override
	public FragmentComposition fetchFragmentComposition(
		long groupId, String fragmentCompositionKey) {

		return fragmentCompositionLocalService.fetchFragmentComposition(
			groupId, fragmentCompositionKey);
	}

	@Override
	public List<FragmentComposition> getFragmentCompositions(
		long fragmentCollectionId) {

		return fragmentCompositionPersistence.findByFragmentCollectionId(
			fragmentCollectionId);
	}

	@Override
	public List<FragmentComposition> getFragmentCompositions(
		long fragmentCollectionId, int start, int end) {

		return fragmentCompositionPersistence.findByFragmentCollectionId(
			fragmentCollectionId, start, end);
	}

	@Override
	public List<FragmentComposition> getFragmentCompositions(
		long groupId, long fragmentCollectionId, int status) {

		return fragmentCompositionPersistence.findByG_FCI_S(
			groupId, fragmentCollectionId, status);
	}

	@Override
	public List<FragmentComposition> getFragmentCompositions(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentComposition> orderByComparator) {

		return fragmentCompositionPersistence.findByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentComposition> getFragmentCompositions(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentComposition> orderByComparator) {

		if (Validator.isNull(name)) {
			return fragmentCompositionPersistence.findByG_FCI(
				groupId, fragmentCollectionId, start, end, orderByComparator);
		}

		return fragmentCompositionPersistence.findByG_FCI_LikeN(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
	}

	@Override
	public int getFragmentCompositionsCount(long fragmentCollectionId) {
		return fragmentCompositionPersistence.countByFragmentCollectionId(
			fragmentCollectionId);
	}

	@Override
	public FragmentComposition moveFragmentComposition(
			long fragmentCompositionId, long fragmentCollectionId)
		throws PortalException {

		FragmentComposition fragmentComposition =
			fragmentCompositionPersistence.findByPrimaryKey(
				fragmentCompositionId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentComposition.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentCompositionLocalService.moveFragmentComposition(
			fragmentCompositionId, fragmentCollectionId);
	}

	@Override
	public FragmentComposition updateFragmentComposition(
			long fragmentCompositionId, long previewFileEntryId)
		throws PortalException {

		FragmentComposition fragmentComposition =
			fragmentCompositionPersistence.findByPrimaryKey(
				fragmentCompositionId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentComposition.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentCompositionLocalService.updateFragmentComposition(
			fragmentCompositionId, previewFileEntryId);
	}

	@Override
	public FragmentComposition updateFragmentComposition(
			long fragmentCompositionId, long fragmentCollectionId, String name,
			String description, String data, long previewFileEntryId,
			int status)
		throws PortalException {

		FragmentComposition fragmentComposition =
			fragmentCompositionPersistence.findByPrimaryKey(
				fragmentCompositionId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentComposition.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentCompositionLocalService.updateFragmentComposition(
			getUserId(), fragmentCompositionId, fragmentCollectionId, name,
			description, data, previewFileEntryId, status);
	}

	@Override
	public FragmentComposition updateFragmentComposition(
			long fragmentCompositionId, String name)
		throws PortalException {

		FragmentComposition fragmentComposition =
			fragmentCompositionPersistence.findByPrimaryKey(
				fragmentCompositionId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentComposition.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentCompositionLocalService.updateFragmentComposition(
			fragmentCompositionId, name);
	}

	@Override
	public FragmentComposition updateFragmentComposition(
			long fragmentCompositionId, String name, String description,
			String data, long previewFileEntryId, int status)
		throws PortalException {

		FragmentComposition fragmentComposition =
			fragmentCompositionPersistence.findByPrimaryKey(
				fragmentCompositionId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentComposition.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentCompositionLocalService.updateFragmentComposition(
			getUserId(), fragmentCompositionId,
			fragmentComposition.getFragmentCollectionId(), name, description,
			data, previewFileEntryId, status);
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference(
		target = "(resource.name=" + FragmentConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}