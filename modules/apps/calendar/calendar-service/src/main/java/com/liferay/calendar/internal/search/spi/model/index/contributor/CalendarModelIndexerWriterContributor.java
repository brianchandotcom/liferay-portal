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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
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