/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.internal.search;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = {})
public class CPDefinitionDiagramEntrySearchRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = _modelSearchRegistrarHelper.register(
			CPDefinitionDiagramEntry.class, bundleContext,
			modelSearchDefinition -> {
				modelSearchDefinition.setDefaultSelectedFieldNames(
					Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
					Field.ENTRY_CLASS_PK, Field.UID);
				modelSearchDefinition.setModelIndexWriteContributor(
					_modelIndexWriterContributor);
				modelSearchDefinition.setModelSummaryContributor(
					_modelSummaryContributor);
				modelSearchDefinition.setModelVisibilityContributor(
					_modelVisibilityContributor);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry)"
	)
	private ModelIndexerWriterContributor<CPDefinitionDiagramEntry>
		_modelIndexWriterContributor;

	@Reference
	private ModelSearchRegistrarHelper _modelSearchRegistrarHelper;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry)"
	)
	private ModelVisibilityContributor _modelVisibilityContributor;

	private ServiceRegistration<?> _serviceRegistration;

}