/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search;

import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = ModelSearchConfigurator.class)
public class KaleoDefinitionVersionModelSearchConfigurator
	implements ModelSearchConfigurator<KaleoDefinitionVersion> {

	@Override
	public String getClassName() {
		return KaleoDefinitionVersion.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<KaleoDefinitionVersion>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new ModelIndexerWriterContributor<>(
			_kaleoDefinitionVersionLocalService::
				getIndexableActionableDynamicQuery);
	}

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	private ModelIndexerWriterContributor<KaleoDefinitionVersion>
		_modelIndexWriterContributor;

}