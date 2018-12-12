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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.exception.DuplicateCTEEntryException;
import com.liferay.change.tracking.model.ChangeTrackingCollection;
import com.liferay.change.tracking.model.ChangeTrackingEntry;
import com.liferay.change.tracking.service.base.ChangeTrackingEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public class ChangeTrackingEntryLocalServiceImpl
	extends ChangeTrackingEntryLocalServiceBaseImpl {

	@Override
	public ChangeTrackingEntry addChangeTrackingEntry(
			long companyId, long userId, long changeTrackingCollectionId,
			long classNameId, long classPK, long resourcePrimKey,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(changeTrackingCollectionId, classNameId, classPK);

		long id = counterLocalService.increment();

		ChangeTrackingEntry changeTrackingEntry =
			changeTrackingEntryPersistence.create(id);

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		changeTrackingEntry.setCompanyId(companyId);
		changeTrackingEntry.setUserId(user.getUserId());
		changeTrackingEntry.setUserName(user.getFullName());
		changeTrackingEntry.setCreateDate(serviceContext.getCreateDate(now));
		changeTrackingEntry.setModifiedDate(
			serviceContext.getModifiedDate(now));
		changeTrackingEntry.setClassNameId(classNameId);
		changeTrackingEntry.setClassPK(classPK);
		changeTrackingEntry.setResourcePrimKey(resourcePrimKey);

		changeTrackingEntry = changeTrackingEntryPersistence.update(
			changeTrackingEntry);

		changeTrackingCollectionLocalService.
			addChangeTrackingEntryChangeTrackingCollection(
				changeTrackingEntry.getChangeTrackingEntryId(),
				changeTrackingCollectionId);

		return changeTrackingEntry;
	}

	private void _validate(
			long changeTrackingCollectionId, long classNameId, long classPK)
		throws PortalException {

		changeTrackingCollectionLocalService.getChangeTrackingCollection(
			changeTrackingCollectionId);

		List<ChangeTrackingEntry> changeTrackingEntries =
			changeTrackingEntryPersistence.findByC_C(classNameId, classPK);

		for (ChangeTrackingEntry changeTrackingEntry : changeTrackingEntries) {
			List<ChangeTrackingCollection> changeTrackingCollections =
				changeTrackingCollectionLocalService.
					getChangeTrackingEntryChangeTrackingCollections(
						changeTrackingEntry.getChangeTrackingEntryId());

			for (ChangeTrackingCollection changeTrackingCollection :
					changeTrackingCollections) {

				if (changeTrackingCollection.getChangeTrackingCollectionId() ==
						changeTrackingCollectionId) {

					throw new DuplicateCTEEntryException();
				}
			}
		}
	}

}