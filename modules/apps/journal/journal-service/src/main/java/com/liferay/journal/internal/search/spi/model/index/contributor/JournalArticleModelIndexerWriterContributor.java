/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.BatchIndexingHelper;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "indexer.class.name=com.liferay.journal.model.JournalArticle",
	service = ModelIndexerWriterContributor.class
)
public class JournalArticleModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<JournalArticle> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		if (_isIndexAllArticleVersions()) {
			batchIndexingActionable.setAddCriteriaMethod(
				dynamicQuery -> {
					Property property = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(
						property.ne(
							_portal.getClassNameId(DDMStructure.class)));
				});
			batchIndexingActionable.setInterval(
				_batchIndexingHelper.getBulkSize(
					JournalArticle.class.getName()));
			batchIndexingActionable.setPerformActionMethod(
				(JournalArticle journalArticle) ->
					batchIndexingActionable.addDocuments(
						modelIndexerWriterDocumentHelper.getDocument(
							journalArticle)));
		}
		else {
			batchIndexingActionable.setInterval(
				_batchIndexingHelper.getBulkSize(
					JournalArticleResource.class.getName()));
			batchIndexingActionable.setPerformActionMethod(
				(JournalArticleResource articleResource) -> {
					JournalArticle latestIndexableArticle =
						_fetchLatestIndexableArticleVersion(
							articleResource.getResourcePrimKey());

					if (latestIndexableArticle == null) {
						return;
					}

					batchIndexingActionable.addDocuments(
						modelIndexerWriterDocumentHelper.getDocument(
							latestIndexableArticle));
				});
		}
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		if (_isIndexAllArticleVersions()) {
			return _dynamicQueryBatchIndexingActionableFactory.
				getBatchIndexingActionable(
					_journalArticleLocalService.
						getIndexableActionableDynamicQuery());
		}

		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_journalArticleResourceLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(JournalArticle journalArticle) {
		return journalArticle.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		JournalArticle journalArticle) {

		if (_portal.getClassNameId(DDMStructure.class) ==
				journalArticle.getClassNameId()) {

			return IndexerWriterMode.DELETE;
		}

		if (_isIndexAllArticleVersions()) {
			if ((journalArticle.getCtCollectionId() == 0) &&
				!CTCollectionThreadLocal.isProductionMode()) {

				return IndexerWriterMode.SKIP;
			}

			return IndexerWriterMode.UPDATE;
		}

		JournalArticle latestIndexableArticle =
			_fetchLatestIndexableArticleVersion(
				journalArticle.getResourcePrimKey());

		if (journalArticle.getId() == latestIndexableArticle.getId()) {
			return IndexerWriterMode.UPDATE;
		}

		if ((journalArticle.getCtCollectionId() == 0) &&
			!CTCollectionThreadLocal.isProductionMode()) {

			return IndexerWriterMode.SKIP;
		}

		return IndexerWriterMode.DELETE;
	}

	private JournalArticle _fetchLatestIndexableArticleVersion(
		long resourcePrimKey) {

		JournalArticle latestIndexableArticle =
			_journalArticleLocalService.fetchLatestArticle(
				resourcePrimKey,
				new int[] {
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_IN_TRASH
				});

		if (latestIndexableArticle == null) {
			latestIndexableArticle =
				_journalArticleLocalService.fetchLatestArticle(resourcePrimKey);
		}

		return latestIndexableArticle;
	}

	private boolean _isIndexAllArticleVersions() {
		JournalServiceConfiguration journalServiceConfiguration = null;

		try {
			journalServiceConfiguration =
				_configurationProvider.getCompanyConfiguration(
					JournalServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			return journalServiceConfiguration.indexAllArticleVersionsEnabled();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleModelIndexerWriterContributor.class);

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

	@Reference
	private Portal _portal;

}