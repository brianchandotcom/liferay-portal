/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.search;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.segments.model.SegmentsEntry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(service = {})
public class SegmentsEntrySearchRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = modelSearchRegistrarHelper.register(
			SegmentsEntry.class, bundleContext,
			modelSearchConfigurator -> {
				modelSearchConfigurator.setDefaultSelectedFieldNames(
					Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
					Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
					Field.SCOPE_GROUP_ID, Field.UID, SegmentsEntryField.ACTIVE);
				modelSearchConfigurator.setDefaultSelectedLocalizedFieldNames(
					Field.DESCRIPTION, Field.NAME);
				modelSearchConfigurator.setModelIndexWriteContributor(
					modelIndexWriterContributor);
				modelSearchConfigurator.setModelSummaryContributor(
					modelSummaryContributor);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.segments.model.SegmentsEntry)"
	)
	protected ModelIndexerWriterContributor<DLFolder>
		modelIndexWriterContributor;

	@Reference
	protected ModelSearchRegistrarHelper modelSearchRegistrarHelper;

	@Reference(
		target = "(indexer.class.name=com.liferay.segments.model.SegmentsEntry)"
	)
	protected ModelSummaryContributor modelSummaryContributor;

	private ServiceRegistration<?> _serviceRegistration;

}