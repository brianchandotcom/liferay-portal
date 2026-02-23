/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.search.experiences.internal.search.spi.model.index.contributor.SXPBlueprintModelIndexerWriterContributor;
import com.liferay.search.experiences.internal.search.spi.model.result.contributor.SXPBlueprintModelSummaryContributor;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, service = ModelSearchConfigurator.class)
public class SXPBlueprintModelSearchConfigurator
	implements ModelSearchConfigurator<SXPBlueprint> {

	@Override
	public String getClassName() {
		return SXPBlueprint.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.STATUS, Field.UID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.DESCRIPTION, Field.TITLE};
	}

	@Override
	public ModelIndexerWriterContributor<SXPBlueprint>
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
			new SXPBlueprintModelIndexerWriterContributor(
				_sxpBlueprintLocalService);
		_modelSummaryContributor = new SXPBlueprintModelSummaryContributor(
			_localization);
	}

	@Reference
	private Localization _localization;

	private ModelIndexerWriterContributor<SXPBlueprint>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

}