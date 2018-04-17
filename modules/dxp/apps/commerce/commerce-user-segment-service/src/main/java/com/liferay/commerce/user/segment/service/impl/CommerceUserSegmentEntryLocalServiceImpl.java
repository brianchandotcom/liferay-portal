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

package com.liferay.commerce.user.segment.service.impl;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.base.CommerceUserSegmentEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CommerceUserSegmentEntryLocalServiceImpl
	extends CommerceUserSegmentEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceUserSegmentEntry addCommerceUserSegmentEntry(
			Map<Locale, String> nameMap, double priority, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce user segment entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceUserSegmentEntryId = counterLocalService.increment();

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			commerceUserSegmentEntryPersistence.create(
				commerceUserSegmentEntryId);

		commerceUserSegmentEntry.setGroupId(groupId);
		commerceUserSegmentEntry.setCompanyId(user.getCompanyId());
		commerceUserSegmentEntry.setUserId(user.getUserId());
		commerceUserSegmentEntry.setUserName(user.getFullName());
		commerceUserSegmentEntry.setNameMap(nameMap);
		commerceUserSegmentEntry.setPriority(priority);
		commerceUserSegmentEntry.setActive(active);
		commerceUserSegmentEntry.setExpandoBridgeAttributes(serviceContext);

		commerceUserSegmentEntryPersistence.update(commerceUserSegmentEntry);

		// Resources

		resourceLocalService.addModelResources(
			commerceUserSegmentEntry, serviceContext);

		return commerceUserSegmentEntry;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
			CommerceUserSegmentEntry commerceUserSegmentEntry)
		throws PortalException {

		// Commerce user segment entry

		commerceUserSegmentEntryPersistence.remove(commerceUserSegmentEntry);

		// Resources

		resourceLocalService.deleteResource(
			commerceUserSegmentEntry, ResourceConstants.SCOPE_INDIVIDUAL);

		// Expando

		expandoRowLocalService.deleteRows(
			commerceUserSegmentEntry.getCommerceUserSegmentEntryId());

		return commerceUserSegmentEntry;
	}

	@Override
	public CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
			long commerceUserSegmentEntryId)
		throws PortalException {

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			commerceUserSegmentEntryPersistence.findByPrimaryKey(
				commerceUserSegmentEntryId);

		return commerceUserSegmentEntryLocalService.
			deleteCommerceUserSegmentEntry(commerceUserSegmentEntry);
	}

	@Override
	public List<CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		long groupId, int start, int end) {

		return commerceUserSegmentEntryPersistence.findByGroupId(
			groupId, start, end);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
			long commerceUserSegmentEntryId, Map<Locale, String> nameMap,
			double priority, boolean active, ServiceContext serviceContext)
		throws PortalException {

		// Commerce user segment entry

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			commerceUserSegmentEntryPersistence.findByPrimaryKey(
				commerceUserSegmentEntryId);

		commerceUserSegmentEntry.setNameMap(nameMap);
		commerceUserSegmentEntry.setPriority(priority);
		commerceUserSegmentEntry.setActive(active);
		commerceUserSegmentEntry.setExpandoBridgeAttributes(serviceContext);

		return commerceUserSegmentEntryPersistence.update(
			commerceUserSegmentEntry);
	}

}