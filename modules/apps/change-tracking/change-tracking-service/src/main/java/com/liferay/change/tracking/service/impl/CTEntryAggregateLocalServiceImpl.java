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

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.service.base.CTEntryAggregateLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(
	property = "model.name.class=com.liferay.change.tracking.model.CTEntryAggregate",
	service = AopService.class
)
public class CTEntryAggregateLocalServiceImpl
	extends CTEntryAggregateLocalServiceBaseImpl {

	@Override
	public void addCTEntry(CTEntryAggregate ctEntryAggregate, CTEntry ctEntry) {
		if ((ctEntryAggregate == null) || (ctEntry == null) ||
			hasCTEntry(ctEntryAggregate, ctEntry)) {

			return;
		}

		ctEntryAggregatePersistence.addCTEntry(
			ctEntryAggregate.getCtEntryAggregateId(), ctEntry);
	}

	@Override
	public CTEntryAggregate addCTEntryAggregate(
			long userId, long ctCollectionId, long ownerCTEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		long ctEntryAggregateId = counterLocalService.increment();

		CTEntryAggregate ctEntryAggregate = ctEntryAggregatePersistence.create(
			ctEntryAggregateId);

		User user = userLocalService.getUser(userId);

		ctEntryAggregate.setCompanyId(user.getCompanyId());
		ctEntryAggregate.setUserId(user.getUserId());
		ctEntryAggregate.setUserName(user.getFullName());

		Date now = new Date();

		ctEntryAggregate.setCreateDate(serviceContext.getCreateDate(now));
		ctEntryAggregate.setModifiedDate(serviceContext.getModifiedDate(now));

		ctEntryAggregate.setOwnerCTEntryId(ownerCTEntryId);

		int status = WorkflowConstants.STATUS_DRAFT;

		if (_isProductionCTCollectionId(ctCollectionId)) {
			status = WorkflowConstants.STATUS_APPROVED;
		}

		ctEntryAggregate.setStatus(status);

		ctEntryAggregatePersistence.update(ctEntryAggregate);

		ctEntryPersistence.addCTEntryAggregate(
			ownerCTEntryId, ctEntryAggregate);

		ctCollectionPersistence.addCTEntryAggregate(
			ctCollectionId, ctEntryAggregate);

		return ctEntryAggregate;
	}

	@Override
	public List<CTEntryAggregate> fetchCTEntryAggregates(
		long ctCollectionId, long ownerCTEntryId) {

		return ctEntryAggregateFinder.findByC_O(
			ctCollectionId, ownerCTEntryId, new QueryDefinition<>());
	}

	@Override
	public CTEntryAggregate fetchLatestCTEntryAggregate(
		long ctCollectionId, long ownerCTEntryId) {

		QueryDefinition<CTEntryAggregate> queryDefinition =
			new QueryDefinition<>();

		queryDefinition.setOrderByComparator(
			OrderByComparatorFactoryUtil.create(
				"CTEntryAggregate", "createDate", false));

		List<CTEntryAggregate> ctEntryAggregates =
			ctEntryAggregateFinder.findByC_O(
				ctCollectionId, ownerCTEntryId, queryDefinition);

		if (!ctEntryAggregates.isEmpty()) {
			return ctEntryAggregates.get(0);
		}

		return null;
	}

	@Override
	public boolean hasCTEntry(
		CTEntryAggregate ctEntryAggregate, CTEntry ctEntry) {

		LongStream ctEntryIdsStream = Arrays.stream(
			getCTEntryPrimaryKeys(ctEntryAggregate.getCtEntryAggregateId()));

		if (ctEntryIdsStream.anyMatch(
				aggregateCTEntryId ->
					aggregateCTEntryId == ctEntry.getCtEntryId())) {

			return true;
		}

		return false;
	}

	@Override
	public void removeCTEntry(
		CTEntryAggregate ctEntryAggregate, CTEntry ctEntry) {

		if (!hasCTEntry(ctEntryAggregate, ctEntry)) {
			return;
		}

		ctEntryAggregatePersistence.removeCTEntry(
			ctEntryAggregate.getCtEntryAggregateId(), ctEntry);
	}

	@Override
	public CTEntryAggregate updateStatus(long ctEntryAggregateId, int status) {
		if ((status != WorkflowConstants.STATUS_APPROVED) &&
			(status != WorkflowConstants.STATUS_DRAFT)) {

			throw new IllegalArgumentException("Invalid status: " + status);
		}

		CTEntryAggregate ctEntryAggregate =
			ctEntryAggregatePersistence.fetchByPrimaryKey(ctEntryAggregateId);

		ctEntryAggregate.setStatus(status);

		return ctEntryAggregatePersistence.update(ctEntryAggregate);
	}

	private boolean _isProductionCTCollectionId(long ctCollectionId) {
		CTCollection ctCollection = ctCollectionPersistence.fetchByPrimaryKey(
			ctCollectionId);

		if (ctCollection == null) {
			return false;
		}

		return ctCollection.isProduction();
	}

}