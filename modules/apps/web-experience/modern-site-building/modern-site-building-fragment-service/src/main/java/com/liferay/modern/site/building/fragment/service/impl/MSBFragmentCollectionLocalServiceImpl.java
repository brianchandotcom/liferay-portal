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

import com.liferay.modern.site.building.fragment.exception.DuplicateMSBFragmentCollectionException;
import com.liferay.modern.site.building.fragment.exception.MSBFragmentCollectionNameException;
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
	public MSBFragmentCollection addMSBFragmentCollection(
			long groupId, long userId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// Modern site building fragment collection

		User user = userLocalService.getUser(userId);

		validate(groupId, name);

		long msbFragmentCollectionId = counterLocalService.increment();

		MSBFragmentCollection msbFragmentCollection =
			msbFragmentCollectionPersistence.create(msbFragmentCollectionId);

		msbFragmentCollection.setGroupId(groupId);
		msbFragmentCollection.setCompanyId(user.getCompanyId());
		msbFragmentCollection.setUserId(user.getUserId());
		msbFragmentCollection.setUserName(user.getFullName());
		msbFragmentCollection.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		msbFragmentCollection.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		msbFragmentCollection.setName(name);
		msbFragmentCollection.setDescription(description);

		msbFragmentCollectionPersistence.update(msbFragmentCollection);

		// Resources

		resourceLocalService.addModelResources(
			msbFragmentCollection, serviceContext);

		return msbFragmentCollection;
	}

	@Override
	public MSBFragmentCollection deleteMSBFragmentCollection(
			long msbFragmentCollectionId)
		throws PortalException {

		MSBFragmentCollection msbFragmentCollection = getMSBFragmentCollection(
			msbFragmentCollectionId);

		return deleteMSBFragmentCollection(msbFragmentCollection);
	}

	@Override
	public MSBFragmentCollection deleteMSBFragmentCollection(
			MSBFragmentCollection msbFragmentCollection)
		throws PortalException {

		/// Modern site building fragment collection

		msbFragmentCollectionPersistence.remove(msbFragmentCollection);

		// Resources

		resourceLocalService.deleteResource(
			msbFragmentCollection.getCompanyId(),
			MSBFragmentCollection.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			msbFragmentCollection.getMsbFragmentCollectionId());

		// Modern site building fragment entries

		List<MSBFragmentEntry> msbFragmentEntries =
			msbFragmentEntryPersistence.findByMSBFragmentCollectionId(
				msbFragmentCollection.getMsbFragmentCollectionId());

		for (MSBFragmentEntry msbFragmentEntry : msbFragmentEntries) {
			msbFragmentEntryLocalService.deleteMSBFragmentEntry(
				msbFragmentEntry);
		}

		return msbFragmentCollection;
	}

	@Override
	public MSBFragmentCollection fetchMSBFragmentCollection(
		long msbFragmentCollectionId) {

		return msbFragmentCollectionPersistence.fetchByPrimaryKey(
			msbFragmentCollectionId);
	}

	@Override
	public List<MSBFragmentCollection> getMSBFragmentCollections(
			long groupId, int start, int end)
		throws PortalException {

		return getMSBFragmentCollections(groupId, start, end, null);
	}

	@Override
	public List<MSBFragmentCollection> getMSBFragmentCollections(
			long groupId, int start, int end,
			OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws PortalException {

		return msbFragmentCollectionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, String name, int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {

		if (Validator.isNull(name)) {
			return msbFragmentCollectionPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}

		return msbFragmentCollectionPersistence.findByG_LikeN(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public MSBFragmentCollection updateMSBFragmentCollection(
			long msbFragmentCollectionId, String name, String description)
		throws PortalException {

		MSBFragmentCollection msbFragmentCollection =
			msbFragmentCollectionPersistence.findByPrimaryKey(
				msbFragmentCollectionId);

		if (!Objects.equals(msbFragmentCollection.getName(), name)) {
			validate(msbFragmentCollection.getGroupId(), name);
		}

		msbFragmentCollection.setModifiedDate(new Date());
		msbFragmentCollection.setName(name);
		msbFragmentCollection.setDescription(description);

		msbFragmentCollectionPersistence.update(msbFragmentCollection);

		return msbFragmentCollection;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new MSBFragmentCollectionNameException(
				"Name must not be null for group " + groupId);
		}

		MSBFragmentCollection msbFragmentCollection =
			msbFragmentCollectionPersistence.fetchByG_N(groupId, name);

		if (msbFragmentCollection != null) {
			throw new DuplicateMSBFragmentCollectionException(name);
		}
	}

}