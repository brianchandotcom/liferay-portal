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

package com.liferay.calendar.internal.search.spi.model.index.contributor;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.workflow.CalendarBookingWorkflowConstants;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.calendar.model.CalendarBooking",
	service = ModelIndexerWriterContributor.class
)
public class CalendarBookingModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CalendarBooking> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property statusProperty = PropertyFactoryUtil.forName("status");

				int[] statuses = {
					WorkflowConstants.STATUS_APPROVED,
					CalendarBookingWorkflowConstants.STATUS_MAYBE
				};

				dynamicQuery.add(statusProperty.in(statuses));
			});
		batchIndexingActionable.setPerformActionMethod(
			(CalendarBooking calendarBooking) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						calendarBooking)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				calendarBookingLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(CalendarBooking calendarBooking) {
		return calendarBooking.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CalendarBooking calendarBooking) {

		int status = calendarBooking.getStatus();

		if ((status == WorkflowConstants.STATUS_APPROVED) ||
			(status == CalendarBookingWorkflowConstants.STATUS_MAYBE) ||
			(status == WorkflowConstants.STATUS_IN_TRASH)) {

			return IndexerWriterMode.UPDATE;
		}

		return IndexerWriterMode.DELETE;
	}

	@Reference
	protected CalendarBookingLocalService calendarBookingLocalService;

	@Reference
	protected DynamicQueryBatchIndexingActionableFactory
		dynamicQueryBatchIndexingActionableFactory;

}