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

import com.liferay.modern.site.building.fragment.exception.DuplicateMSBFragmentEntryException;
import com.liferay.modern.site.building.fragment.exception.MSBFragmentEntryNameException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.service.base.MSBFragmentEntryLocalServiceBaseImpl;
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
public class MSBFragmentEntryLocalServiceImpl
	extends MSBFragmentEntryLocalServiceBaseImpl {

	@Override
	public MSBFragmentEntry addMSBFragmentEntry(
			long groupId, long userId, long msbFragmentCollectionId,
			String name, String css, String html, String js,
			ServiceContext serviceContext)
		throws PortalException {

		// Modern site building fragment entry

		User user = userLocalService.getUser(userId);

		validate(groupId, name);

		long msbFragmentEntryId = counterLocalService.increment();

		MSBFragmentEntry msbFragmentEntry = msbFragmentEntryPersistence.create(
			msbFragmentEntryId);

		msbFragmentEntry.setGroupId(groupId);
		msbFragmentEntry.setCompanyId(user.getCompanyId());
		msbFragmentEntry.setUserId(user.getUserId());
		msbFragmentEntry.setUserName(user.getFullName());
		msbFragmentEntry.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		msbFragmentEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		msbFragmentEntry.setMsbFragmentCollectionId(msbFragmentCollectionId);
		msbFragmentEntry.setName(name);
		msbFragmentEntry.setCss(css);
		msbFragmentEntry.setHtml(html);
		msbFragmentEntry.setJs(js);

		msbFragmentEntryPersistence.update(msbFragmentEntry);

		// Resources

		resourceLocalService.addModelResources(
			msbFragmentEntry, serviceContext);

		return msbFragmentEntry;
	}

	@Override
	public MSBFragmentEntry deleteMSBFragmentEntry(long msbFragmentEntryId)
		throws PortalException {

		MSBFragmentEntry msbFragmentEntry = getMSBFragmentEntry(
			msbFragmentEntryId);

		return deleteMSBFragmentEntry(msbFragmentEntry);
	}

	@Override
	public MSBFragmentEntry deleteMSBFragmentEntry(
			MSBFragmentEntry msbFragmentEntry)
		throws PortalException {

		// Modern site building fragment entry

		msbFragmentEntryPersistence.remove(msbFragmentEntry);

		// Resources

		resourceLocalService.deleteResource(
			msbFragmentEntry.getCompanyId(), MSBFragmentEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			msbFragmentEntry.getMsbFragmentEntryId());

		return msbFragmentEntry;
	}

	@Override
	public List<MSBFragmentEntry> fetchMSBFragmentEntries(
		long msbFragmentCollectionId) {

		return msbFragmentEntryPersistence.findByMSBFragmentCollectionId(
			msbFragmentCollectionId);
	}

	@Override
	public MSBFragmentEntry fetchMSBFragmentEntry(long msbFragmentEntryId) {
		return msbFragmentEntryPersistence.fetchByPrimaryKey(
			msbFragmentEntryId);
	}

	@Override
	public List<MSBFragmentEntry> getMSBFragmentEntries(
			long msbFragmentCollectionId, int start, int end)
		throws PortalException {

		return msbFragmentEntryPersistence.findByMSBFragmentCollectionId(
			msbFragmentCollectionId, start, end);
	}

	@Override
	public List<MSBFragmentEntry> getMSBFragmentEntries(
			long groupId, long msbFragmentCollectionId, int start, int end,
			OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws PortalException {

		return msbFragmentEntryPersistence.findByG_MSBFCI(
			groupId, msbFragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<MSBFragmentEntry> getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, String name, int start,
		int end, OrderByComparator<MSBFragmentEntry> orderByComparator) {

		if (Validator.isNull(name)) {
			return msbFragmentEntryPersistence.findByG_MSBFCI(
				groupId, msbFragmentCollectionId, start, end,
				orderByComparator);
		}

		return msbFragmentEntryPersistence.findByG_MSBFCI_LikeN(
			groupId, msbFragmentCollectionId, name, start, end,
			orderByComparator);
	}

	@Override
	public MSBFragmentEntry updateMSBFragmentEntry(
			long msbFragmentEntryId, String name, String css, String html,
			String js)
		throws PortalException {

		MSBFragmentEntry msbFragmentEntry =
			msbFragmentEntryPersistence.findByPrimaryKey(msbFragmentEntryId);

		if (!Objects.equals(msbFragmentEntry.getName(), name)) {
			validate(msbFragmentEntry.getGroupId(), name);
		}

		msbFragmentEntry.setModifiedDate(new Date());
		msbFragmentEntry.setName(name);
		msbFragmentEntry.setCss(css);
		msbFragmentEntry.setHtml(html);
		msbFragmentEntry.setJs(js);

		msbFragmentEntryPersistence.update(msbFragmentEntry);

		return msbFragmentEntry;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new MSBFragmentEntryNameException(
				"Name must not be null for group " + groupId);
		}

		MSBFragmentEntry msbFragmentEntry =
			msbFragmentEntryPersistence.fetchByG_N(groupId, name);

		if (msbFragmentEntry != null) {
			throw new DuplicateMSBFragmentEntryException(name);
		}
	}

}