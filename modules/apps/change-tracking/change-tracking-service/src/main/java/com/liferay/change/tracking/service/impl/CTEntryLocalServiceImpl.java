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

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.DuplicateCTEntryException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.base.CTEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public class CTEntryLocalServiceImpl extends CTEntryLocalServiceBaseImpl {

	@Override
	public CTEntry addCTEntry(
			long userId, long classNameId, long classPK, long resourcePrimKey,
			int changeType, long ctCollectionId, ServiceContext serviceContext)
		throws PortalException {

		boolean force = GetterUtil.getBoolean(
			serviceContext.getAttribute("force"));

		CTEntry ctEntry = ctEntryPersistence.fetchByC_C(classNameId, classPK);

		_validate(ctEntry, changeType, ctCollectionId, force);

		User user = userLocalService.getUser(userId);

		if (ctEntry != null) {
			return _updateCTEntry(ctEntry, user, changeType, serviceContext);
		}

		return _addCTEntry(
			user, classNameId, classPK, resourcePrimKey, changeType,
			ctCollectionId, serviceContext);
	}

	@Override
	public List<CTEntry> fetchCTEntries(
		long ctCollectionId, long resourcePrimKey,
		QueryDefinition<CTEntry> queryDefinition) {

		return ctEntryFinder.findByC_R(
			ctCollectionId, resourcePrimKey, queryDefinition);
	}

	@Override
	public List<CTEntry> fetchCTEntries(
		long ctCollectionId, QueryDefinition<CTEntry> queryDefinition) {

		return ctEntryFinder.findByC_R(ctCollectionId, 0, queryDefinition);
	}

	@Override
	public CTEntry fetchCTEntry(long classNameId, long classPK) {
		return ctEntryPersistence.fetchByC_C(classNameId, classPK);
	}

	@Override
	public CTEntry fetchCTEntry(
		long ctCollectionId, long classNameId, long classPK) {

		return ctEntryFinder.findByC_C_C(ctCollectionId, classNameId, classPK);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntries(long ctCollectionId) {
		if (_isProductionCTCollectionId(ctCollectionId)) {
			return super.getCTCollectionCTEntries(ctCollectionId);
		}

		return getCTCollectionCTEntriesByStatus(
			ctCollectionId, WorkflowConstants.STATUS_DRAFT);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntriesByStatus(
		long ctCollectionId, int status) {

		return ctEntryFinder.findByC_S(
			ctCollectionId, status, new QueryDefinition<>());
	}

	@Override
	public CTEntry updateStatus(long ctEntryId, int status) {
		if ((status != WorkflowConstants.STATUS_APPROVED) &&
			(status != WorkflowConstants.STATUS_DRAFT)) {

			throw new IllegalArgumentException(
				"Change status value is invalid");
		}

		CTEntry ctEntry = ctEntryPersistence.fetchByPrimaryKey(ctEntryId);

		ctEntry.setStatus(status);

		return ctEntryPersistence.update(ctEntry);
	}

	private CTEntry _addCTEntry(
		User user, long classNameId, long classPK, long resourcePrimKey,
		int changeType, long ctCollectionId, ServiceContext serviceContext) {

		long ctEntryId = counterLocalService.increment();

		CTEntry ctEntry = ctEntryPersistence.create(ctEntryId);

		ctEntry.setCompanyId(user.getCompanyId());
		ctEntry.setUserId(user.getUserId());
		ctEntry.setUserName(user.getFullName());

		Date now = new Date();

		ctEntry.setCreateDate(serviceContext.getCreateDate(now));
		ctEntry.setModifiedDate(serviceContext.getModifiedDate(now));

		ctEntry.setClassNameId(classNameId);
		ctEntry.setClassPK(classPK);
		ctEntry.setResourcePrimKey(resourcePrimKey);
		ctEntry.setChangeType(changeType);

		int status = WorkflowConstants.STATUS_DRAFT;

		if (_isProductionCTCollectionId(ctCollectionId)) {
			status = WorkflowConstants.STATUS_APPROVED;
		}

		ctEntry.setStatus(status);

		ctEntry = ctEntryPersistence.update(ctEntry);

		ctCollectionLocalService.addCTEntryCTCollection(
			ctEntry.getCtEntryId(), ctCollectionId);

		return ctEntry;
	}

	private boolean _isProductionCTCollectionId(long ctCollectionId) {
		CTCollection ctCollection = ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if (ctCollection == null) {
			return false;
		}

		return ctCollection.isProduction();
	}

	private CTEntry _updateCTEntry(
		CTEntry ctEntry, User user, int changeType,
		ServiceContext serviceContext) {

		ctEntry.setUserId(user.getUserId());
		ctEntry.setUserName(user.getFullName());

		ctEntry.setModifiedDate(serviceContext.getModifiedDate(new Date()));
		ctEntry.setChangeType(changeType);

		return ctEntryPersistence.update(ctEntry);
	}

	private void _validate(
			CTEntry ctEntry, int changeType, long ctCollectionId, boolean force)
		throws PortalException {

		if (!force && (ctEntry != null)) {
			throw new DuplicateCTEntryException();
		}

		ctCollectionLocalService.getCTCollection(ctCollectionId);

		if ((changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) &&
			(changeType != CTConstants.CT_CHANGE_TYPE_DELETION) &&
			(changeType != CTConstants.CT_CHANGE_TYPE_MODIFICATION)) {

			throw new IllegalArgumentException("Change type value is invalid");
		}
	}

}