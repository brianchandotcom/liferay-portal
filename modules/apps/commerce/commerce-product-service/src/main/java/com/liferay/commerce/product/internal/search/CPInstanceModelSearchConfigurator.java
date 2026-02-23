/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.internal.search.spi.model.index.contributor.CPInstanceModelIndexerWriterContributor;
import com.liferay.commerce.product.internal.search.spi.model.result.contributor.CPInstanceModelSummaryContributor;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(service = ModelSearchConfigurator.class)
public class CPInstanceModelSearchConfigurator
	implements ModelSearchConfigurator<CPInstance> {

	@Override
	public String getClassName() {
		return CPInstance.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			CPField.SKU, Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<CPInstance>
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
			new CPInstanceModelIndexerWriterContributor(
				_cpInstanceLocalService);
	}

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	private ModelIndexerWriterContributor<CPInstance>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new CPInstanceModelSummaryContributor();

}