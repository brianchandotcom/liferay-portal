/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.search;

import com.liferay.calendar.internal.search.spi.model.index.contributor.CalendarModelIndexerWriterContributor;
import com.liferay.calendar.internal.search.spi.model.result.contributor.CalendarModelSummaryContributor;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = ModelSearchConfigurator.class)
public class CalendarModelSearchConfigurator
	implements ModelSearchConfigurator<Calendar> {

	@Override
	public String getClassName() {
		return Calendar.class.getName();
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
		return new String[] {
			Field.DESCRIPTION, Field.NAME, CalendarField.RESOURCE_NAME
		};
	}

	@Override
	public ModelIndexerWriterContributor<Calendar>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public boolean isSelectAllLocales() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new CalendarModelIndexerWriterContributor(
				new CalendarBookingBatchReindexer(
					_indexerDocumentBuilder, _indexerWriter),
				_calendarLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private CalendarLocalService _calendarLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference(
		target = "(indexer.class.name=com.liferay.calendar.model.CalendarBooking)"
	)
	private IndexerDocumentBuilder _indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.calendar.model.CalendarBooking)"
	)
	private IndexerWriter<CalendarBooking> _indexerWriter;

	private ModelIndexerWriterContributor<Calendar>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new CalendarModelSummaryContributor();

}