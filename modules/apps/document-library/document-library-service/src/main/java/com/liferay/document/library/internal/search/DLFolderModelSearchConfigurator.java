/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search;

import com.liferay.document.library.internal.search.spi.model.index.contributor.DLFolderModelIndexerWriterContributor;
import com.liferay.document.library.internal.search.spi.model.result.contributor.DLFolderModelSummaryContributor;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.search.Field;
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
public class DLFolderModelSearchConfigurator
	implements ModelSearchConfigurator<DLFolder> {

	@Override
	public String getClassName() {
		return DLFolder.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.DESCRIPTION, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.TITLE, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<DLFolder>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new DLFolderModelIndexerWriterContributor(_dlFolderLocalService);
	}

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	private ModelIndexerWriterContributor<DLFolder>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new DLFolderModelSummaryContributor();

}