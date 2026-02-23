/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.internal.search.spi.model.index.contributor.CPSpecificationOptionModelIndexerWriterContributor;
import com.liferay.commerce.product.internal.search.spi.model.result.contributor.CPSpecificationOptionModelSummaryContributor;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelSearchConfigurator.class)
public class CPSpecificationOptionModelSearchConfigurator
	implements ModelSearchConfigurator<CPSpecificationOption> {

	@Override
	public String getClassName() {
		return CPSpecificationOption.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.MODIFIED_DATE, Field.TITLE, Field.UID, CPField.KEY
		};
	}

	@Override
	public ModelIndexerWriterContributor<CPSpecificationOption>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new CPSpecificationOptionModelIndexerWriterContributor(
				_cpSpecificationOptionLocalService);
	}

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	private ModelIndexerWriterContributor<CPSpecificationOption>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new CPSpecificationOptionModelSummaryContributor();

}