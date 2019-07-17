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

package com.liferay.calendar.internal.search;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.workflow.CalendarBookingWorkflowConstants;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = CalendarBookingBatchReindexer.class)
public class CalendarBookingBatchReindexerImpl
	implements CalendarBookingBatchReindexer {

	@Override
	public void reindex(long calendarId, long companyId) {
		BatchIndexingActionable batchIndexingActionable =
			indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property calendarIdPropery = PropertyFactoryUtil.forName(
					"calendarId");

				dynamicQuery.add(calendarIdPropery.eq(calendarId));

				Property statusProperty = PropertyFactoryUtil.forName("status");

				int[] statuses = {
					WorkflowConstants.STATUS_APPROVED,
					CalendarBookingWorkflowConstants.STATUS_MAYBE
				};

				dynamicQuery.add(statusProperty.in(statuses));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(CalendarBooking calendarBooking) ->
				batchIndexingActionable.addDocuments(
					indexerDocumentBuilder.getDocument(calendarBooking)));

		batchIndexingActionable.performActions();
	}

	@Reference
	protected CalendarBookingLocalService calendarBookingLocalService;

	@Reference(
		target = "(indexer.class.name=com.liferay.calendar.model.CalendarBooking)"
	)
	protected IndexerDocumentBuilder indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.calendar.model.CalendarBooking)"
	)
	protected IndexerWriter<CalendarBooking> indexerWriter;

}