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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.indexer.BaseModelDocumentFactory;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

/**
 * @author Michael C. Han
 */
public class IndexerDocumentBuilderImpl implements IndexerDocumentBuilder {

	public IndexerDocumentBuilderImpl(
		BaseModelDocumentFactory baseModelDocumentFactory,
		Iterable<ModelDocumentContributor> modelDocumentContributors,
		Iterable<DocumentContributor> documentContributors,
		IndexerPostProcessorsHolder indexerPostProcessorsHolder) {

		_baseModelDocumentFactory = baseModelDocumentFactory;
		_modelDocumentContributors = modelDocumentContributors;
		_documentContributors = documentContributors;
		_indexerPostProcessorsHolder = indexerPostProcessorsHolder;
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

		postProcessDocument(document, baseModel);

		return document;
	}

	@Override
	public <T extends BaseModel<?>> String getDocumentUID(T baseModel) {
		Document document = _baseModelDocumentFactory.createDocument(baseModel);

		return document.get(Field.UID);
	}

	protected <T extends BaseModel<?>> void postProcessDocument(
		Document document, T baseModel) {

		_indexerPostProcessorsHolder.forEach(
			indexerPostProcessor -> {
				try {
					indexerPostProcessor.postProcessDocument(
						document, baseModel);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to post process document " + document, e);
					}
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerDocumentBuilderImpl.class);

	private final BaseModelDocumentFactory _baseModelDocumentFactory;
	private final Iterable<DocumentContributor> _documentContributors;
	private final IndexerPostProcessorsHolder _indexerPostProcessorsHolder;
	private final Iterable<ModelDocumentContributor> _modelDocumentContributors;

}