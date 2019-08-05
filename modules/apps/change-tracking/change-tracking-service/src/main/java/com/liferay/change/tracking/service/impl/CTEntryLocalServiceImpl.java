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
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTEntry",
	service = AopService.class
)
public class CTEntryLocalServiceImpl extends CTEntryLocalServiceBaseImpl {

	@Override
	public CTEntry addCTEntry(
			long userId, long modelClassNameId, long modelClassPK,
			long modelResourcePrimKey, int changeType, long ctCollectionId,
			ServiceContext serviceContext)
		throws PortalException {

		boolean force = GetterUtil.getBoolean(
			serviceContext.getAttribute("force"));

		CTEntry ctEntry = ctEntryPersistence.fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);

		_validate(ctEntry, changeType, ctCollectionId, force);

		User user = userLocalService.getUser(userId);

		if (ctEntry != null) {
			return _updateCTEntry(ctEntry, user, changeType, serviceContext);
		}

		return _addCTEntry(
			user, modelClassNameId, modelClassPK, modelResourcePrimKey,
			changeType, ctCollectionId, serviceContext);
	}

	@Override
	public List<CTEntry> fetchCTEntries(long modelClassNameId) {
		return ctEntryPersistence.findByModelClassNameId(modelClassNameId);
	}

	@Override
	public List<CTEntry> fetchCTEntries(
		long ctCollectionId, long modelResourcePrimKey,
		QueryDefinition<CTEntry> queryDefinition) {

		if (modelResourcePrimKey == 0) {
			return fetchCTEntries(ctCollectionId, queryDefinition);
		}

		int status = queryDefinition.getStatus();

		if (status == WorkflowConstants.STATUS_ANY) {
			return ctEntryPersistence.findByC_MRPK(
				ctCollectionId, modelResourcePrimKey,
				queryDefinition.getStart(), queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}

		return ctEntryPersistence.findByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status,
			queryDefinition.getStart(), queryDefinition.getEnd(),
			queryDefinition.getOrderByComparator());
	}

	@Override
	public List<CTEntry> fetchCTEntries(
		long ctCollectionId, QueryDefinition<CTEntry> queryDefinition) {

		int status = queryDefinition.getStatus();

		if (status == WorkflowConstants.STATUS_ANY) {
			return ctEntryPersistence.findByCTCollectionId(
				ctCollectionId, queryDefinition.getStart(),
				queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}

		return ctEntryPersistence.findByC_S(
			ctCollectionId, status, queryDefinition.getStart(),
			queryDefinition.getEnd(), queryDefinition.getOrderByComparator());
	}

	@Override
	public List<CTEntry> fetchCTEntries(String modelClassName) {
		return fetchCTEntries(_portal.getClassNameId(modelClassName));
	}

	@Override
	public List<CTEntry> fetchCTEntriesByModelClassNameId(
		long ctCollectionId, long modelClassNameId,
		QueryDefinition<CTEntry> queryDefinition) {

		int status = queryDefinition.getStatus();

		if (status == WorkflowConstants.STATUS_ANY) {
			return ctEntryPersistence.findByC_MCNI(
				ctCollectionId, modelClassNameId, queryDefinition.getStart(),
				queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}

		return ctEntryPersistence.findByC_MCNI_S(
			ctCollectionId, modelClassNameId, status,
			queryDefinition.getStart(), queryDefinition.getEnd(),
			queryDefinition.getOrderByComparator());
	}

	@Override
	public CTEntry fetchCTEntry(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		return ctEntryPersistence.fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntries(long ctCollectionId) {
		return getCTCollectionCTEntries(
			ctCollectionId, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntries(
		long ctCollectionId, int start, int end) {

		return getCTCollectionCTEntries(
			ctCollectionId, WorkflowConstants.STATUS_DRAFT, start, end, null);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntries(
		long ctCollectionId, int status, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		if (_isProductionCTCollectionId(ctCollectionId)) {
			return Collections.emptyList();
		}

		if (status == WorkflowConstants.STATUS_ANY) {
			return ctEntryPersistence.findByCTCollectionId(
				ctCollectionId, start, end, orderByComparator);
		}

		return ctEntryPersistence.findByC_S(
			ctCollectionId, status, start, end, orderByComparator);
	}

	@Override
	public int getCTEntriesCount(
		long ctCollectionId, QueryDefinition<CTEntry> queryDefinition) {

		if (queryDefinition.getStatus() == WorkflowConstants.STATUS_ANY) {
			return ctEntryPersistence.countByCTCollectionId(ctCollectionId);
		}

		return ctEntryPersistence.countByC_S(
			ctCollectionId, queryDefinition.getStatus());
	}

	@Override
	public CTEntry updateCollision(long ctEntryId, boolean collision) {
		CTEntry ctEntry = ctEntryPersistence.fetchByPrimaryKey(ctEntryId);

		ctEntry.setCollision(collision);

		return ctEntryPersistence.update(ctEntry);
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
		User user, long modelClassNameId, long modelClassPK,
		long modelResourcePrimKey, int changeType, long ctCollectionId,
		ServiceContext serviceContext) {

		long ctEntryId = counterLocalService.increment();

		CTEntry ctEntry = ctEntryPersistence.create(ctEntryId);

		ctEntry.setCompanyId(user.getCompanyId());
		ctEntry.setUserId(user.getUserId());
		ctEntry.setUserName(user.getFullName());

		Date now = new Date();

		ctEntry.setCreateDate(serviceContext.getCreateDate(now));
		ctEntry.setModifiedDate(serviceContext.getModifiedDate(now));

		ctEntry.setCtCollectionId(ctCollectionId);
		ctEntry.setOriginalCTCollectionId(ctCollectionId);
		ctEntry.setModelClassNameId(modelClassNameId);
		ctEntry.setModelClassPK(modelClassPK);
		ctEntry.setModelResourcePrimKey(modelResourcePrimKey);
		ctEntry.setChangeType(changeType);

		int status = WorkflowConstants.STATUS_DRAFT;

		if (_isProductionCTCollectionId(ctCollectionId)) {
			status = WorkflowConstants.STATUS_APPROVED;
		}

		ctEntry.setStatus(status);

		ctEntry = ctEntryPersistence.update(ctEntry);

		return ctEntry;
	}

	private boolean _isProductionCTCollectionId(long ctCollectionId) {
		CTCollection ctCollection = ctCollectionPersistence.fetchByPrimaryKey(
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

		ctCollectionPersistence.findByPrimaryKey(ctCollectionId);

		if ((changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) &&
			(changeType != CTConstants.CT_CHANGE_TYPE_DELETION) &&
			(changeType != CTConstants.CT_CHANGE_TYPE_MODIFICATION)) {

			throw new IllegalArgumentException("Change type value is invalid");
		}
	}

	@Reference
	private Portal _portal;

}