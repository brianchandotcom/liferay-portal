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

import com.liferay.modern.site.building.fragment.exception.DuplicateFragmentEntryException;
import com.liferay.modern.site.building.fragment.exception.FragmentEntryNameException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.service.base.MSBFragmentEntryLocalServiceBaseImpl;
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
public class MSBFragmentEntryLocalServiceImpl
	extends MSBFragmentEntryLocalServiceBaseImpl {

	@Override
	public MSBFragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, ServiceContext serviceContext)
		throws PortalException {

		// Entry

		validate(groupId, name);

		User user = userLocalService.getUser(userId);

		long fragmentEntryId = counterLocalService.increment();

		MSBFragmentEntry fragmentEntry = msbFragmentEntryPersistence.create(
			fragmentEntryId);

		fragmentEntry.setGroupId(groupId);
		fragmentEntry.setCompanyId(user.getCompanyId());
		fragmentEntry.setUserId(user.getUserId());
		fragmentEntry.setUserName(user.getFullName());
		fragmentEntry.setFragmentCollectionId(fragmentCollectionId);
		fragmentEntry.setName(name);
		fragmentEntry.setCss(css);
		fragmentEntry.setHtml(html);
		fragmentEntry.setJs(js);

		msbFragmentEntryPersistence.update(fragmentEntry);

		// Resources

		resourceLocalService.addModelResources(fragmentEntry, serviceContext);

		return fragmentEntry;
	}

	@Override
	public MSBFragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException {

		MSBFragmentEntry fragmentEntry = getMSBFragmentEntry(fragmentEntryId);

		return deleteMSBFragmentEntry(fragmentEntry);
	}

	@Override
	public MSBFragmentEntry deleteFragmentEntry(MSBFragmentEntry fragmentEntry)
		throws PortalException {

		// Entry

		msbFragmentEntryPersistence.remove(fragmentEntry);

		// Resources

		resourceLocalService.deleteResource(
			fragmentEntry.getCompanyId(), MSBFragmentEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fragmentEntry.getFragmentEntryId());

		return fragmentEntry;
	}

	@Override
	public List<MSBFragmentEntry> fetchFragmentEntries(
		long fragmentCollectionId) {

		return msbFragmentEntryPersistence.findByFragmentCollectionId(
			fragmentCollectionId);
	}

	@Override
	public MSBFragmentEntry fetchFragmentEntry(long fragmentEntryId) {
		return msbFragmentEntryPersistence.fetchByPrimaryKey(fragmentEntryId);
	}

	@Override
	public List<MSBFragmentEntry> getFragmentEntries(
			long fragmentCollectionId, int start, int end)
		throws PortalException {

		return msbFragmentEntryPersistence.findByFragmentCollectionId(
			fragmentCollectionId, start, end);
	}

	@Override
	public List<MSBFragmentEntry> getFragmentEntries(
			long groupId, long fragmentCollectionId, int start, int end,
			OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws PortalException {

		return msbFragmentEntryPersistence.findByG_FC(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<MSBFragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<MSBFragmentEntry> obc) {

		if (Validator.isNull(name)) {
			return msbFragmentEntryPersistence.findByG_FC(
				groupId, fragmentCollectionId, start, end, obc);
		}

		return msbFragmentEntryPersistence.findByG_LikeN_FC(
			groupId, name, fragmentCollectionId, start, end, obc);
	}

	@Override
	public MSBFragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js)
		throws PortalException {

		MSBFragmentEntry fragmentEntry =
			msbFragmentEntryPersistence.findByPrimaryKey(fragmentEntryId);

		if (!Objects.equals(fragmentEntry.getName(), name)) {
			validate(fragmentEntry.getGroupId(), name);
		}

		fragmentEntry.setName(name);
		fragmentEntry.setCss(css);
		fragmentEntry.setHtml(html);
		fragmentEntry.setJs(js);

		msbFragmentEntryPersistence.update(fragmentEntry);

		return fragmentEntry;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new FragmentEntryNameException(
				"Fragment entry name cannot be null for group " + groupId);
		}

		MSBFragmentEntry fragmentEntry = msbFragmentEntryPersistence.fetchByG_N(
			groupId, name);

		if (fragmentEntry != null) {
			throw new DuplicateFragmentEntryException(
				"A fragment entry with name " + name + " already exists");
		}
	}

}