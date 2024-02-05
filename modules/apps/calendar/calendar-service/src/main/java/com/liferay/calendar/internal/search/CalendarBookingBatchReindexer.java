/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.search;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.workflow.constants.CalendarBookingWorkflowConstants;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

/**
 * @author André de Oliveira
 */
public class CalendarBookingBatchReindexer {

	public CalendarBookingBatchReindexer(
		IndexerDocumentBuilder indexerDocumentBuilder,
		IndexerWriter<CalendarBooking> indexerWriter) {

		_indexerDocumentBuilder = indexerDocumentBuilder;
		_indexerWriter = indexerWriter;
	}

	public void reindex(long calendarId, long companyId) {
		BatchIndexingActionable batchIndexingActionable =
			_indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property calendarIdProperty = PropertyFactoryUtil.forName(
					"calendarId");

				dynamicQuery.add(calendarIdProperty.eq(calendarId));

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.in(
						new int[] {
							WorkflowConstants.STATUS_APPROVED,
							CalendarBookingWorkflowConstants.STATUS_MAYBE
						}));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(CalendarBooking calendarBooking) ->
				batchIndexingActionable.addDocuments(
					_indexerDocumentBuilder.getDocument(calendarBooking)));

		batchIndexingActionable.performActions();
	}

	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final IndexerWriter<CalendarBooking> _indexerWriter;

}