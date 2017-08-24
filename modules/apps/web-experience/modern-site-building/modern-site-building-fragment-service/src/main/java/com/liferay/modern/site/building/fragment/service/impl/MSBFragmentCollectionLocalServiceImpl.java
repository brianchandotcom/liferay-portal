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
import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;
import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.service.base.MSBFragmentCollectionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class MSBFragmentCollectionLocalServiceImpl
	extends MSBFragmentCollectionLocalServiceBaseImpl {

	@Override
	public MSBFragmentCollection addFragmentCollection(
			long groupId, long userId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// Collection

		validate(groupId, name);

		User user = userLocalService.getUser(userId);

		long fragmentCollectionId = counterLocalService.increment();

		MSBFragmentCollection fragmentCollection =
			msbFragmentCollectionPersistence.create(fragmentCollectionId);

		fragmentCollection.setGroupId(groupId);
		fragmentCollection.setCompanyId(user.getCompanyId());
		fragmentCollection.setUserId(user.getUserId());
		fragmentCollection.setUserName(user.getFullName());
		fragmentCollection.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		fragmentCollection.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		fragmentCollection.setName(name);
		fragmentCollection.setDescription(description);

		msbFragmentCollectionPersistence.update(fragmentCollection);

		// Resources

		resourceLocalService.addModelResources(
			fragmentCollection, serviceContext);

		return fragmentCollection;
	}

	@Override
	public MSBFragmentCollection deleteFragmentCollection(
			long fragmentCollectionId)
		throws PortalException {

		MSBFragmentCollection fragmentCollection = getMSBFragmentCollection(
			fragmentCollectionId);

		return deleteFragmentCollection(fragmentCollection);
	}

	@Override
	public MSBFragmentCollection deleteFragmentCollection(
			MSBFragmentCollection fragmentCollection)
		throws PortalException {

		// Entries

		List<MSBFragmentEntry> fragmentEntries =
			msbFragmentEntryPersistence.findByMSBFragmentCollectionId(
				fragmentCollection.getFragmentCollectionId());

		for (MSBFragmentEntry fragmentEntry : fragmentEntries) {
			msbFragmentEntryLocalService.deleteMSBFragmentEntry(fragmentEntry);
		}

		// Collection

		msbFragmentCollectionPersistence.remove(fragmentCollection);

		// Resources

		resourceLocalService.deleteResource(
			fragmentCollection.getCompanyId(),
			MSBFragmentCollection.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fragmentCollection.getFragmentCollectionId());

		return fragmentCollection;
	}

	@Override
	public MSBFragmentCollection fetchFragmentCollection(
		long fragmentCollectionId) {

		return msbFragmentCollectionPersistence.fetchByPrimaryKey(
			fragmentCollectionId);
	}

	@Override
	public List<MSBFragmentCollection> getFragmentCollections(
			long groupId, int start, int end)
		throws PortalException {

		return getFragmentCollections(groupId, start, end, null);
	}

	@Override
	public List<MSBFragmentCollection> getFragmentCollections(
			long groupId, int start, int end,
			OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws PortalException {

		return msbFragmentCollectionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<MSBFragmentCollection> getFragmentCollections(
		long groupId, String name, int start, int end,
		OrderByComparator<MSBFragmentCollection> obc) {

		if (Validator.isNull(name)) {
			return msbFragmentCollectionPersistence.findByGroupId(
				groupId, start, end, obc);
		}

		return msbFragmentCollectionPersistence.findByG_LikeN(
			groupId, name, start, end, obc);
	}

	@Override
	public MSBFragmentCollection updateFragmentCollection(
			long fragmentCollectionId, String name, String description)
		throws PortalException {

		MSBFragmentCollection fragmentCollection =
			msbFragmentCollectionPersistence.findByPrimaryKey(
				fragmentCollectionId);

		if (!Objects.equals(fragmentCollection.getName(), name)) {
			validate(fragmentCollection.getGroupId(), name);
		}

		fragmentCollection.setModifiedDate(new Date());
		fragmentCollection.setName(name);
		fragmentCollection.setDescription(description);

		msbFragmentCollectionPersistence.update(fragmentCollection);

		return fragmentCollection;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new FragmentCollectionNameException(
				"Fragment collection name cannot be null for group " + groupId);
		}

		MSBFragmentCollection fragmentCollection =
			msbFragmentCollectionPersistence.fetchByG_N(groupId, name);

		if (fragmentCollection != null) {
			throw new DuplicateFragmentCollectionException(
				"A fragment collection with name " + name + " already exists");
		}
	}

}