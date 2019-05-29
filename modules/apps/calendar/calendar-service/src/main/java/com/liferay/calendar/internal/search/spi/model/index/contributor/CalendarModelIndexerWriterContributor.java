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

import com.liferay.calendar.internal.search.CalendarBookingBatchReindexer;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.calendar.model.Calendar",
	service = ModelIndexerWriterContributor.class
)
public class CalendarModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<Calendar> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(Calendar calendar) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(calendar)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				calendarLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(Calendar calendar) {
		return calendar.getCompanyId();
	}

	@Override
	public void modelIndexed(Calendar calendar) {
		calendarBookingBatchReindexer.reindex(
			calendar.getCalendarId(), calendar.getCompanyId());
	}

	@Reference
	protected CalendarBookingBatchReindexer calendarBookingBatchReindexer;

	@Reference
	protected CalendarLocalService calendarLocalService;

	@Reference
	protected DynamicQueryBatchIndexingActionableFactory
		dynamicQueryBatchIndexingActionableFactory;

}