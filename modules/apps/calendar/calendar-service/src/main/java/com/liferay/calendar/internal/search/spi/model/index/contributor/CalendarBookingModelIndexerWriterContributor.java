/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.search.spi.model.index.contributor;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.workflow.constants.CalendarBookingWorkflowConstants;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Michael C. Han
 */
public class CalendarBookingModelIndexerWriterContributor
	extends ModelIndexerWriterContributor<CalendarBooking> {

	public CalendarBookingModelIndexerWriterContributor(
		CalendarBookingLocalService calendarBookingLocalService) {

		super(calendarBookingLocalService::getIndexableActionableDynamicQuery);
	}

	@Override
	public void customize(
		IndexableActionableDynamicQuery indexableActionableDynamicQuery,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.in(
						new int[] {
							WorkflowConstants.STATUS_APPROVED,
							CalendarBookingWorkflowConstants.STATUS_MAYBE
						}));
			});
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CalendarBooking calendarBooking) ->
				indexableActionableDynamicQuery.addDocument(
					modelIndexerWriterDocumentHelper.getDocument(
						calendarBooking)));
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

}