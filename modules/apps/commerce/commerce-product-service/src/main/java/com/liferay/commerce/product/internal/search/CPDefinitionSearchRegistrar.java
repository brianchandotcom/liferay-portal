/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(service = {})
public class CPDefinitionSearchRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = _modelSearchRegistrarHelper.register(
			CPDefinition.class, bundleContext,
			modelSearchDefinition -> {
				modelSearchDefinition.setDefaultSelectedFieldNames(
					CPField.DEFAULT_IMAGE_FILE_URL,
					CPField.DEFAULT_IMAGE_FILE_URL, CPField.DEPTH,
					CPField.HEIGHT, CPField.IS_IGNORE_SKU_COMBINATIONS,
					CPField.PRODUCT_TYPE_NAME, CPField.SHORT_DESCRIPTION,
					Field.COMPANY_ID, Field.DESCRIPTION, Field.ENTRY_CLASS_NAME,
					Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
					Field.NAME, Field.SCOPE_GROUP_ID, Field.UID, Field.URL);
				modelSearchDefinition.setDefaultSelectedLocalizedFieldNames(
					Field.NAME);
				modelSearchDefinition.setModelIndexWriteContributor(
					_modelIndexWriterContributor);
				modelSearchDefinition.setModelSummaryContributor(
					_modelSummaryContributor);
				modelSearchDefinition.setPermissionAware(false);
				modelSearchDefinition.setSearchResultPermissionFilterSuppressed(
					true);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.model.CPDefinition)"
	)
	private ModelIndexerWriterContributor<CPDefinition>
		_modelIndexWriterContributor;

	@Reference
	private ModelSearchRegistrarHelper _modelSearchRegistrarHelper;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.model.CPDefinition)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

	private ServiceRegistration<?> _serviceRegistration;

}