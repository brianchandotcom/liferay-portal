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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.NoSuchAvailabilityRangeException;
import com.liferay.commerce.model.CAvailabilityRangeEntry;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.service.base.CAvailabilityRangeEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;

/**
 * @author Alessio Antonio Rendina
 */
public class CAvailabilityRangeEntryLocalServiceImpl
	extends CAvailabilityRangeEntryLocalServiceBaseImpl {

	@Override
	public void deleteCAvailabilityRangeEntries(long groupId) {
		cAvailabilityRangeEntryPersistence.removeByGroupId(groupId);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CAvailabilityRangeEntry deleteCAvailabilityRangeEntry(
		CAvailabilityRangeEntry cAvailabilityRangeEntry) {

		return cAvailabilityRangeEntryPersistence.remove(
			cAvailabilityRangeEntry);
	}

	@Override
	public CAvailabilityRangeEntry deleteCAvailabilityRangeEntry(
			long cAvailabilityRangeEntryId)
		throws PortalException {

		CAvailabilityRangeEntry cAvailabilityRangeEntry =
			cAvailabilityRangeEntryPersistence.findByPrimaryKey(
				cAvailabilityRangeEntryId);

		return cAvailabilityRangeEntryLocalService.
			deleteCAvailabilityRangeEntry(cAvailabilityRangeEntry);
	}

	@Override
	public CAvailabilityRangeEntry fetchCAvailabilityRangeEntry(
		long groupId, long cpDefinitionId) {

		return cAvailabilityRangeEntryPersistence.fetchByG_C(
			groupId, cpDefinitionId);
	}

	@Override
	public CAvailabilityRangeEntry updateCAvailabilityRangeEntry(
			long cAvailabilityRangeEntryId, long cpDefinitionId,
			long commerceAvailabilityRangeId, ServiceContext serviceContext)
		throws PortalException {

		validate(commerceAvailabilityRangeId);

		if (cAvailabilityRangeEntryId > 0) {
			CAvailabilityRangeEntry cAvailabilityRangeEntry =
				cAvailabilityRangeEntryPersistence.findByPrimaryKey(
					cAvailabilityRangeEntryId);

			cAvailabilityRangeEntry.setCommerceAvailabilityRangeId(
				commerceAvailabilityRangeId);

			return cAvailabilityRangeEntryPersistence.update(
				cAvailabilityRangeEntry);
		}

		return addCAvailabilityRangeEntry(
			cpDefinitionId, commerceAvailabilityRangeId, serviceContext);
	}

	protected CAvailabilityRangeEntry addCAvailabilityRangeEntry(
			long cpDefinitionId, long commerceAvailabilityRangeId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cAvailabilityRangeEntryId = counterLocalService.increment();

		CAvailabilityRangeEntry cAvailabilityRangeEntry =
			cAvailabilityRangeEntryPersistence.create(
				cAvailabilityRangeEntryId);

		cAvailabilityRangeEntry.setUuid(serviceContext.getUuid());
		cAvailabilityRangeEntry.setGroupId(groupId);
		cAvailabilityRangeEntry.setCompanyId(user.getCompanyId());
		cAvailabilityRangeEntry.setUserId(user.getUserId());
		cAvailabilityRangeEntry.setUserName(user.getFullName());
		cAvailabilityRangeEntry.setCPDefinitionId(cpDefinitionId);
		cAvailabilityRangeEntry.setCommerceAvailabilityRangeId(
			commerceAvailabilityRangeId);

		cAvailabilityRangeEntryPersistence.update(cAvailabilityRangeEntry);

		return cAvailabilityRangeEntry;
	}

	protected void validate(long commerceAvailabilityRangeId)
		throws PortalException {

		if (commerceAvailabilityRangeId > 0) {
			CommerceAvailabilityRange commerceAvailabilityRange =
				commerceAvailabilityRangeLocalService.
					fetchCommerceAvailabilityRange(commerceAvailabilityRangeId);

			if (commerceAvailabilityRange == null) {
				throw new NoSuchAvailabilityRangeException();
			}
		}
	}

}