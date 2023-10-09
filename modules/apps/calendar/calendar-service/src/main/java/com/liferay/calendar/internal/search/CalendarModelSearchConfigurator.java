/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.search;

import com.liferay.calendar.model.Calendar;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

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

	@Reference(
		target = "(indexer.class.name=com.liferay.calendar.model.Calendar)"
	)
	private ModelIndexerWriterContributor<Calendar>
		_modelIndexWriterContributor;

	@Reference(
		target = "(indexer.class.name=com.liferay.calendar.model.Calendar)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

}