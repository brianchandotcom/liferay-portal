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
import com.liferay.modern.site.building.fragment.model.FragmentEntry;
import com.liferay.modern.site.building.fragment.service.base.FragmentEntryLocalServiceBaseImpl;
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
public class FragmentEntryLocalServiceImpl
	extends FragmentEntryLocalServiceBaseImpl {

	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, ServiceContext serviceContext)
		throws PortalException {

		validate(groupId, name);

		User user = userLocalService.getUser(userId);

		long fragmentEntryId = counterLocalService.increment();

		FragmentEntry fragmentEntry = fragmentEntryPersistence.create(
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

		fragmentEntryPersistence.update(fragmentEntry);

		resourceLocalService.addModelResources(fragmentEntry, serviceContext);

		return fragmentEntry;
	}

	@Override
	public FragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry = getFragmentEntry(fragmentEntryId);

		resourceLocalService.deleteResource(
			fragmentEntry.getCompanyId(), FragmentEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fragmentEntry.getFragmentEntryId());

		fragmentEntryPersistence.remove(fragmentEntry);

		return fragmentEntry;
	}

	@Override
	public List<FragmentEntry> fetchFragmentEntries(long fragmentCollectionId) {
		return fragmentEntryPersistence.findByFragmentCollectionId(
			fragmentCollectionId);
	}

	@Override
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId) {
		return fragmentEntryPersistence.fetchByPrimaryKey(fragmentEntryId);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
			long fragmentCollectionId, int start, int end)
		throws PortalException {

		return fragmentEntryPersistence.findByFragmentCollectionId(
			fragmentCollectionId, start, end);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
			long groupId, long fragmentCollectionId, int start, int end,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws PortalException {

		return fragmentEntryPersistence.findByG_FC(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> obc) {

		if (Validator.isNull(name)) {
			return fragmentEntryPersistence.findByG_FC(
				groupId, fragmentCollectionId, start, end, obc);
		}

		return fragmentEntryPersistence.findByG_LikeN_FC(
			groupId, name, fragmentCollectionId, start, end, obc);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js)
		throws PortalException {

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			fragmentEntryId);

		if (!Objects.equals(fragmentEntry.getName(), name)) {
			validate(fragmentEntry.getGroupId(), name);
		}

		fragmentEntry.setName(name);
		fragmentEntry.setCss(css);
		fragmentEntry.setHtml(html);
		fragmentEntry.setJs(js);

		fragmentEntryPersistence.update(fragmentEntry);

		return fragmentEntry;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new FragmentEntryNameException(
				"Fragment entry name cannot be null for group " + groupId);
		}

		FragmentEntry fragmentEntry = fragmentEntryPersistence.fetchByG_N(
			groupId, name);

		if (fragmentEntry != null) {
			throw new DuplicateFragmentEntryException(
				"A fragment entry with name " + name + " already exists");
		}
	}

}