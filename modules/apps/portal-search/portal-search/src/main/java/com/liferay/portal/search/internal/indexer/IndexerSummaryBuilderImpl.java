/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.search.indexer.IndexerSummaryBuilder;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.List;
import java.util.Locale;

/**
 * @author Michael C. Han
 */
public class IndexerSummaryBuilderImpl implements IndexerSummaryBuilder {

	public IndexerSummaryBuilderImpl(
		ModelSummaryContributor modelSummaryContributor, String className) {

		_modelSummaryContributor = modelSummaryContributor;
		_className = className;
	}

	@Override
	public Summary getSummary(
		Document document, String snippet, Locale locale) {

		if (_modelSummaryContributor == null) {
			return null;
		}

		Summary summary = _modelSummaryContributor.getSummary(
			document, locale, snippet);

		List<IndexerPostProcessor> indexerPostProcessors =
			IndexerRegistryUtil.getIndexerPostProcessors(_className);

		indexerPostProcessors.forEach(
			indexerPostProcessor -> indexerPostProcessor.postProcessSummary(
				summary, document, locale, snippet));

		return summary;
	}

	private final String _className;
	private final ModelSummaryContributor _modelSummaryContributor;

}