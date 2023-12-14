/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.search.spi.model.index.contributor;

import com.liferay.calendar.internal.search.CalendarBookingBatchReindexer;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Michael C. Han
 */
public class CalendarModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<Calendar> {

	public CalendarModelIndexerWriterContributor(
		CalendarBookingBatchReindexer calendarBookingBatchReindexer,
		CalendarLocalService calendarLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_calendarBookingBatchReindexer = calendarBookingBatchReindexer;
		_calendarLocalService = calendarLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

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
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_calendarLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(Calendar calendar) {
		return calendar.getCompanyId();
	}

	@Override
	public void modelIndexed(Calendar calendar) {
		_calendarBookingBatchReindexer.reindex(
			calendar.getCalendarId(), calendar.getCompanyId());
	}

	private final CalendarBookingBatchReindexer _calendarBookingBatchReindexer;
	private final CalendarLocalService _calendarLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}