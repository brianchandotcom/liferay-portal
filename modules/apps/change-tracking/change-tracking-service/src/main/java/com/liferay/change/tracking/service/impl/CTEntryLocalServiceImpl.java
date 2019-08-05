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
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

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
			long ctCollectionId, long modelClassNameId, CTModel<?> ctModel,
			long userId, int changeType)
		throws PortalException {

		CTCollection ctCollection = ctCollectionPersistence.findByPrimaryKey(
			ctCollectionId);

		long ctEntryId = counterLocalService.increment(CTEntry.class.getName());

		CTEntry ctEntry = ctEntryPersistence.create(ctEntryId);

		ctEntry.setCompanyId(ctCollection.getCompanyId());
		ctEntry.setUserId(userId);
		ctEntry.setCtCollectionId(ctCollectionId);
		ctEntry.setModelClassNameId(modelClassNameId);
		ctEntry.setModelClassPK(ctModel.getPrimaryKey());
		ctEntry.setModelMvccVersion(ctModel.getMvccVersion());
		ctEntry.setChangeType(changeType);

		return ctEntryPersistence.update(ctEntry);
	}

	@Override
	public CTEntry addCTEntry(
			long userId, long modelClassNameId, long modelClassPK,
			long modelResourcePrimKey, int changeType, long ctCollectionId,
			ServiceContext serviceContext)
		throws PortalException {

		CTCollection ctCollection = ctCollectionPersistence.findByPrimaryKey(
			ctCollectionId);

		CTEntry ctEntry = ctEntryPersistence.fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);

		boolean force = GetterUtil.getBoolean(
			serviceContext.getAttribute("force"));

		_validate(ctEntry, changeType, force);

		if (ctEntry == null) {
			long ctEntryId = counterLocalService.increment(
				CTEntry.class.getName());

			ctEntry = ctEntryPersistence.create(ctEntryId);

			ctEntry.setCompanyId(ctCollection.getCompanyId());
			ctEntry.setCtCollectionId(ctCollectionId);
			ctEntry.setModelClassNameId(modelClassNameId);
			ctEntry.setModelClassPK(modelClassPK);
			ctEntry.setModelResourcePrimKey(modelResourcePrimKey);
			ctEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		ctEntry.setUserId(userId);
		ctEntry.setChangeType(changeType);

		return ctEntryPersistence.update(ctEntry);
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

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
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
	public List<CTEntry> getCTEntries(
		long ctCollectionId, long modelClassNameId) {

		return ctEntryPersistence.findByC_MCNI(
			ctCollectionId, modelClassNameId);
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
	public boolean hasCTEntries(long ctCollectionId, long modelClassNameId) {
		int count = ctEntryPersistence.countByC_MCNI(
			ctCollectionId, modelClassNameId);

		if (count == 0) {
			return false;
		}

		return true;
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

	private void _validate(CTEntry ctEntry, int changeType, boolean force)
		throws PortalException {

		if (!force && (ctEntry != null)) {
			throw new DuplicateCTEntryException();
		}

		if ((changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) &&
			(changeType != CTConstants.CT_CHANGE_TYPE_DELETION) &&
			(changeType != CTConstants.CT_CHANGE_TYPE_MODIFICATION)) {

			throw new IllegalArgumentException("Change type value is invalid");
		}
	}

}