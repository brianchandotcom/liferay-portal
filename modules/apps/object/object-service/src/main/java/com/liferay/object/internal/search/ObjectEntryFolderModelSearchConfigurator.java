/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search;

import com.liferay.object.internal.search.spi.model.result.contributor.ObjectEntryFolderModelSummaryContributor;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = ModelSearchConfigurator.class)
public class ObjectEntryFolderModelSearchConfigurator
	implements ModelSearchConfigurator<ObjectEntryFolder> {

	@Override
	public String getClassName() {
		return ObjectEntryFolder.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.NAME, Field.UID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {"localized_label"};
	}

	@Override
	public ModelIndexerWriterContributor<ObjectEntryFolder>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new ModelIndexerWriterContributor<>(
			_objectEntryFolderLocalService::getIndexableActionableDynamicQuery);
	}

	private ModelIndexerWriterContributor<ObjectEntryFolder>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new ObjectEntryFolderModelSummaryContributor();

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}