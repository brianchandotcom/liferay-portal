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

import com.liferay.change.tracking.exception.CollectionNameException;
import com.liferay.change.tracking.model.ChangeTrackingCollection;
import com.liferay.change.tracking.model.ChangeTrackingEntry;
import com.liferay.change.tracking.service.base.ChangeTrackingCollectionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public class ChangeTrackingCollectionLocalServiceImpl
	extends ChangeTrackingCollectionLocalServiceBaseImpl {

	@Override
	public ChangeTrackingCollection addChangeTrackingCollection(
			long companyId, long userId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(name);

		long id = counterLocalService.increment();

		ChangeTrackingCollection changeTrackingCollection =
			changeTrackingCollectionPersistence.create(id);

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		changeTrackingCollection.setCompanyId(companyId);
		changeTrackingCollection.setUserId(user.getUserId());
		changeTrackingCollection.setUserName(user.getFullName());
		changeTrackingCollection.setCreateDate(
			serviceContext.getCreateDate(now));
		changeTrackingCollection.setModifiedDate(
			serviceContext.getModifiedDate(now));
		changeTrackingCollection.setName(name);
		changeTrackingCollection.setDescription(description);
		changeTrackingCollection.setStatus(WorkflowConstants.STATUS_APPROVED);

		return changeTrackingCollectionPersistence.update(
			changeTrackingCollection);
	}

	@Override
	public ChangeTrackingCollection deleteChangeTrackingCollection(
		ChangeTrackingCollection changeTrackingCollection) {

		List<ChangeTrackingEntry> changeTrackingEntries =
			changeTrackingCollectionPersistence.getChangeTrackingEntries(
				changeTrackingCollection.getChangeTrackingCollectionId());

		for (ChangeTrackingEntry changeTrackingEntry : changeTrackingEntries) {
			int collectionsSize =
				changeTrackingEntryPersistence.getChangeTrackingCollectionsSize(
					changeTrackingCollection.getChangeTrackingCollectionId());

			if (collectionsSize > 1) {
				continue;
			}

			changeTrackingEntryLocalService.deleteChangeTrackingEntry(
				changeTrackingEntry);
		}

		changeTrackingCollectionPersistence.remove(changeTrackingCollection);
		changeTrackingCollectionPersistence.clearChangeTrackingEntries(
			changeTrackingCollection.getChangeTrackingCollectionId());

		return changeTrackingCollection;
	}

	@Override
	public void deleteCompanyChangeTrackingCollections(long companyId) {
		List<ChangeTrackingCollection> changeTrackingCollections =
			changeTrackingCollectionPersistence.findByCompanyId(companyId);

		for (ChangeTrackingCollection changeTrackingCollection :
				changeTrackingCollections) {

			changeTrackingCollectionLocalService.deleteChangeTrackingCollection(
				changeTrackingCollection);
		}
	}

	@Override
	public ChangeTrackingCollection fetchChangeTrackingCollection(
		long companyId, String name) {

		return changeTrackingCollectionPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public List<ChangeTrackingCollection> getChangeTrackingCollections(
		long companyId) {

		return changeTrackingCollectionPersistence.findByCompanyId(companyId);
	}

	@Override
	public ChangeTrackingCollection updateStatus(
			long userId, ChangeTrackingCollection collection, int status,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		Date now = new Date();

		Date modifiedDate = serviceContext.getModifiedDate(now);

		collection.setModifiedDate(modifiedDate);

		collection.setStatus(status);
		collection.setStatusByUserId(user.getUserId());
		collection.setStatusByUserName(user.getFullName());
		collection.setStatusDate(modifiedDate);

		return changeTrackingCollectionPersistence.update(collection);
	}

	private void _validate(String name) throws CollectionNameException {
		if (Validator.isNull(name)) {
			throw new CollectionNameException();
		}
	}

}