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

import com.liferay.modern.site.building.fragment.exception.DuplicateFragmentCollectionException;
import com.liferay.modern.site.building.fragment.exception.FragmentCollectionNameException;
import com.liferay.modern.site.building.fragment.model.FragmentCollection;
import com.liferay.modern.site.building.fragment.service.base.FragmentCollectionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class FragmentCollectionLocalServiceImpl
	extends FragmentCollectionLocalServiceBaseImpl {

	@Override
	public FragmentCollection addFragmentCollection(
			long userId, long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// Collection

		validate(groupId, name);

		User user = userLocalService.getUser(userId);

		long fragmentCollectionId = counterLocalService.increment();

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.create(fragmentCollectionId);

		fragmentCollection.setGroupId(groupId);
		fragmentCollection.setCompanyId(user.getCompanyId());
		fragmentCollection.setUserId(user.getUserId());
		fragmentCollection.setUserName(user.getFullName());
		fragmentCollection.setName(name);
		fragmentCollection.setDescription(description);

		fragmentCollectionPersistence.update(fragmentCollection);

		// Resources

		resourceLocalService.addModelResources(
			fragmentCollection, serviceContext);

		return fragmentCollection;
	}

	@Override
	public FragmentCollection deleteFragmentCollection(
			FragmentCollection fragmentCollection)
		throws PortalException {

		// Collection

		fragmentCollectionPersistence.remove(fragmentCollection);

		// Resources

		resourceLocalService.deleteResource(
			fragmentCollection.getCompanyId(),
			FragmentCollection.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fragmentCollection.getFragmentCollectionId());

		return fragmentCollection;
	}

	@Override
	public FragmentCollection deleteFragmentCollection(
			long fragmentCollectionId)
		throws PortalException {

		FragmentCollection fragmentCollection = getFragmentCollection(
			fragmentCollectionId);

		return deleteFragmentCollection(fragmentCollection);
	}

	@Override
	public FragmentCollection fetchFragmentCollection(
		long fragmentCollectionId) {

		return fragmentCollectionPersistence.fetchByPrimaryKey(
			fragmentCollectionId);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
			long groupId, int start, int end)
		throws PortalException {

		return getFragmentCollections(groupId, start, end, null);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
			long groupId, int start, int end,
			OrderByComparator<FragmentCollection> orderByComparator)
		throws PortalException {

		return fragmentCollectionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long groupId, String name, int start, int end,
		OrderByComparator<FragmentCollection> obc) {

		if (Validator.isNull(name)) {
			return fragmentCollectionPersistence.findByGroupId(
				groupId, start, end, obc);
		}

		return fragmentCollectionPersistence.findByG_LikeN(
			groupId, name, start, end, obc);
	}

	@Override
	public FragmentCollection updateFragmentCollection(
			long fragmentCollectionId, String name, String description)
		throws PortalException {

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.findByPrimaryKey(
				fragmentCollectionId);

		if (!Objects.equals(fragmentCollection.getName(), name)) {
			validate(fragmentCollection.getGroupId(), name);
		}

		fragmentCollection.setName(name);
		fragmentCollection.setDescription(description);

		fragmentCollectionPersistence.update(fragmentCollection);

		return fragmentCollection;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new FragmentCollectionNameException(
				"Fragment collection name cannot be null for group " + groupId);
		}

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.fetchByG_N(groupId, name);

		if (fragmentCollection != null) {
			throw new DuplicateFragmentCollectionException(
				"A fragment collection with name " + name + " already exists");
		}
	}

}