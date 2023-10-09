/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.search.experiences.model.SXPElement;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, service = ModelSearchConfigurator.class)
public class SXPElementModelSearchConfigurator
	implements ModelSearchConfigurator<SXPElement> {

	@Override
	public String getClassName() {
		return SXPElement.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.HIDDEN, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.UID, "readOnly"
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.DESCRIPTION, Field.TITLE};
	}

	@Override
	public ModelIndexerWriterContributor<SXPElement>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.search.experiences.model.SXPElement)"
	)
	private ModelIndexerWriterContributor<SXPElement>
		_modelIndexWriterContributor;

	@Reference(
		target = "(indexer.class.name=com.liferay.search.experiences.model.SXPElement)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

}