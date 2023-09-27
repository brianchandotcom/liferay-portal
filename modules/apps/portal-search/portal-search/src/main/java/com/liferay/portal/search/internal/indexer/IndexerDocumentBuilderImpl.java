/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.indexer.BaseModelDocumentFactory;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.permission.SearchPermissionDocumentContributor;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class IndexerDocumentBuilderImpl implements IndexerDocumentBuilder {

	public IndexerDocumentBuilderImpl(
		BaseModelDocumentFactory baseModelDocumentFactory,
		Iterable<ModelDocumentContributor<?>> modelDocumentContributors,
		Iterable<DocumentContributor<?>> documentContributors, String className,
		SearchPermissionDocumentContributor
			searchPermissionDocumentContributor) {

		_baseModelDocumentFactory = baseModelDocumentFactory;
		_modelDocumentContributors = modelDocumentContributors;
		_documentContributors = documentContributors;
		_className = className;
		_searchPermissionDocumentContributor =
			searchPermissionDocumentContributor;
	}

	@Override
	public <T extends BaseModel<?>> Document getDocument(T baseModel) {
		Document document = _baseModelDocumentFactory.createDocument(baseModel);

		_documentContributors.forEach(
			(DocumentContributor documentContributor) ->
				documentContributor.contribute(document, baseModel));

		_modelDocumentContributors.forEach(
			(ModelDocumentContributor modelDocumentContributor) ->
				modelDocumentContributor.contribute(document, baseModel));

		_searchPermissionDocumentContributor.addPermissionFields(
			GetterUtil.getLong(document.get(Field.COMPANY_ID)), document);

		_postProcessDocument(document, baseModel);

		return document;
	}

	@Override
	public <T extends BaseModel<?>> String getDocumentUID(T baseModel) {
		Document document = _baseModelDocumentFactory.createDocument(baseModel);

		return document.get(Field.UID);
	}

	private <T extends BaseModel<?>> void _postProcessDocument(
		Document document, T baseModel) {

		List<IndexerPostProcessor> indexerPostProcessors =
			IndexerRegistryUtil.getIndexerPostProcessors(_className);

		indexerPostProcessors.forEach(
			indexerPostProcessor -> {
				try {
					indexerPostProcessor.postProcessDocument(
						document, baseModel);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to post process document " + document,
							exception);
					}
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerDocumentBuilderImpl.class);

	private final BaseModelDocumentFactory _baseModelDocumentFactory;
	private final String _className;
	private final Iterable<DocumentContributor<?>> _documentContributors;
	private final Iterable<ModelDocumentContributor<?>>
		_modelDocumentContributors;
	private final SearchPermissionDocumentContributor
		_searchPermissionDocumentContributor;

}