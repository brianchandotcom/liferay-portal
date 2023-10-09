/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.segments.model.SegmentsEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(service = ModelSearchConfigurator.class)
public class SegmentsEntryModelSearchConfigurator
	implements ModelSearchConfigurator<SegmentsEntry> {

	@Override
	public String getClassName() {
		return SegmentsEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.UID, SegmentsEntryField.ACTIVE
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.DESCRIPTION, Field.NAME};
	}

	@Override
	public ModelIndexerWriterContributor<SegmentsEntry>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.segments.model.SegmentsEntry)"
	)
	private ModelIndexerWriterContributor<SegmentsEntry>
		_modelIndexWriterContributor;

	@Reference(
		target = "(indexer.class.name=com.liferay.segments.model.SegmentsEntry)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

}