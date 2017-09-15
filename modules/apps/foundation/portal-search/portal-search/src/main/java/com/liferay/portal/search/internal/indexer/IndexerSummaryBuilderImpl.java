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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.search.indexer.IndexerSummaryBuilder;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;
import java.util.stream.Stream;

/**
 * @author Michael C. Han
 */
public class IndexerSummaryBuilderImpl implements IndexerSummaryBuilder {

	public IndexerSummaryBuilderImpl(
		ModelSummaryContributor modelSummaryContributor,
		IndexerPostProcessorsHolder indexerPostProcessorsHolder) {

		_modelSummaryContributor = modelSummaryContributor;
		_indexerPostProcessorsHolder = indexerPostProcessorsHolder;
	}

	@Override
	public Summary getSummary(
		Document document, String snippet, Locale locale) {

		if (_modelSummaryContributor == null) {
			return null;
		}

		Summary summary = _modelSummaryContributor.getSummary(
			document, locale, snippet);

		Stream<IndexerPostProcessor> stream =
			_indexerPostProcessorsHolder.stream();

		stream.forEach(
			indexerPostProcessor -> indexerPostProcessor.postProcessSummary(
				summary, document, locale, snippet));

		return summary;
	}

	private final IndexerPostProcessorsHolder _indexerPostProcessorsHolder;
	private final ModelSummaryContributor _modelSummaryContributor;

}