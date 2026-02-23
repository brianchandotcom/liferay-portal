/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.search;

import com.liferay.calendar.internal.search.spi.model.index.contributor.CalendarBookingModelIndexerWriterContributor;
import com.liferay.calendar.internal.search.spi.model.result.contributor.CalendarBookingModelSummaryContributor;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.summary.SummaryHelper;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = ModelSearchConfigurator.class)
public class CalendarBookingModelSearchConfigurator
	implements ModelSearchConfigurator<CalendarBooking> {

	@Override
	public String getClassName() {
		return CalendarBooking.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.DESCRIPTION, Field.TITLE};
	}

	@Override
	public ModelIndexerWriterContributor<CalendarBooking>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new CalendarBookingModelIndexerWriterContributor(
				_calendarBookingLocalService);
		_modelSummaryContributor = new CalendarBookingModelSummaryContributor(
			_summaryHelper, _htmlParser);
	}

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Reference
	private HtmlParser _htmlParser;

	private ModelIndexerWriterContributor<CalendarBooking>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;

	@Reference
	private SummaryHelper _summaryHelper;

}