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

import com.liferay.fragment.exception.DuplicateFragmentCollectionException;
import com.liferay.fragment.exception.FragmentCollectionNameException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.base.FragmentCollectionLocalServiceBaseImpl;
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
public class FragmentCollectionLocalServiceImpl
	extends FragmentCollectionLocalServiceBaseImpl {

	@Override
	public FragmentCollection addFragmentCollection(
			long userId, long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// Fragment collection

		User user = userLocalService.getUser(userId);

		validate(groupId, name);

		long fragmentCollectionId = counterLocalService.increment();

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.create(fragmentCollectionId);

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

		/// Fragment collection

		fragmentCollectionPersistence.remove(fragmentCollection);

		// Resources

		resourceLocalService.deleteResource(
			fragmentCollection.getCompanyId(),
			FragmentCollection.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fragmentCollection.getFragmentCollectionId());

		// Fragment entries

		List<FragmentEntry> fragmentEntries =
			fragmentEntryPersistence.findByFragmentCollectionId(
				fragmentCollection.getFragmentCollectionId());

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			fragmentEntryLocalService.deleteFragmentEntry(fragmentEntry);
		}

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
		OrderByComparator<FragmentCollection> orderByComparator) {

		if (Validator.isNull(name)) {
			return fragmentCollectionPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}

		return fragmentCollectionPersistence.findByG_LikeN(
			groupId, name, start, end, orderByComparator);
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

		fragmentCollection.setModifiedDate(new Date());
		fragmentCollection.setName(name);
		fragmentCollection.setDescription(description);

		fragmentCollectionPersistence.update(fragmentCollection);

		return fragmentCollection;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new FragmentCollectionNameException(
				"Name must not be null for group " + groupId);
		}

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.fetchByG_N(groupId, name);

		if (fragmentCollection != null) {
			throw new DuplicateFragmentCollectionException(name);
		}
	}

}