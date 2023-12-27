/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.search;

import com.liferay.journal.internal.search.spi.model.index.contributor.JournalArticleModelIndexerWriterContributor;
import com.liferay.journal.internal.search.spi.model.result.contributor.JournalArticleModelSummaryContributor;
import com.liferay.journal.internal.search.spi.model.result.contributor.JournalArticleModelVisibilityContributor;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.BatchIndexingHelper;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = ModelSearchConfigurator.class)
public class JournalArticleModelSearchConfigurator
	implements ModelSearchConfigurator<JournalArticle> {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.ASSET_TAG_NAMES, Field.ARTICLE_ID, Field.COMPANY_ID,
			Field.DEFAULT_LANGUAGE_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.VERSION, Field.UID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.CONTENT, Field.DESCRIPTION, Field.TITLE};
	}

	@Override
	public ModelIndexerWriterContributor<JournalArticle>
		getModelIndexerWriterContributor() {

		return _modelIndexerWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public ModelVisibilityContributor getModelVisibilityContributor() {
		return _modelVisibilityContributor;
	}

	@Override
	public boolean isSelectAllLocales() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexerWriterContributor =
			new JournalArticleModelIndexerWriterContributor(
				_batchIndexingHelper, _configurationProvider,
				_dynamicQueryBatchIndexingActionableFactory,
				_journalArticleLocalService,
				_journalArticleResourceLocalService);
		_modelVisibilityContributor =
			new JournalArticleModelVisibilityContributor(
				_journalArticleLocalService);
	}

	@Reference
	private BatchIndexingHelper _batchIndexingHelper;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	private ModelIndexerWriterContributor<JournalArticle>
		_modelIndexerWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new JournalArticleModelSummaryContributor();
	private ModelVisibilityContributor _modelVisibilityContributor;

}