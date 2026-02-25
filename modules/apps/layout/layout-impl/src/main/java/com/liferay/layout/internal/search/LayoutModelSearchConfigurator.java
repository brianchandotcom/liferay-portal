/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.search;

import com.liferay.layout.internal.search.spi.model.result.contributor.LayoutModelSummaryContributor;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vagner B.C
 */
@Component(service = ModelSearchConfigurator.class)
public class LayoutModelSearchConfigurator
	implements ModelSearchConfigurator<Layout> {

	@Override
	public String getClassName() {
		return Layout.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.DEFAULT_LANGUAGE_ID, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.UID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.CONTENT, Field.TITLE};
	}

	@Override
	public ModelIndexerWriterContributor<Layout>
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
			IndexerWriterMode.UPDATE,
			_layoutLocalService::getIndexableActionableDynamicQuery);
		_modelSummaryContributor = new LayoutModelSummaryContributor(
			_htmlParser);
	}

	@Reference
	private HtmlParser _htmlParser;

	@Reference
	private LayoutLocalService _layoutLocalService;

	private ModelIndexerWriterContributor<Layout> _modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;

}