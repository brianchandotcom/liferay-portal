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

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.registry.util.StringPlus;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Raymond Augé
 */
@Component(immediate = true)
public class IndexerPostProcessorRegistry {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(indexer.class.name=*)", unbind = "removeIndexerPostProcessor"
	)
	protected void addIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor,
		Map<String, Object> properties) {

		List<String> indexerClassNames = StringPlus.asList(
			properties.get("indexer.class.name"));

		for (String indexerClassName : indexerClassNames) {
			Indexer<?> indexer = _indexerRegistry.getIndexer(indexerClassName);

			if (indexer == null) {
				_log.error("No indexer exists for " + indexerClassName);

				continue;
			}

			indexer.registerIndexerPostProcessor(indexerPostProcessor);
		}
	}

	protected void removeIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor,
		Map<String, Object> properties) {

		List<String> indexerClassNames = StringPlus.asList(
			properties.get("indexer.class.name"));

		for (String indexerClassName : indexerClassNames) {
			Indexer<?> indexer = _indexerRegistry.getIndexer(indexerClassName);

			if (indexer == null) {
				_log.error("No indexer exists for " + indexerClassName);

				continue;
			}

			indexer.unregisterIndexerPostProcessor(indexerPostProcessor);
		}
	}

	@Reference(unbind = "-")
	protected void setIndexerRegistry(IndexerRegistry indexerRegistry) {
		_indexerRegistry = indexerRegistry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerPostProcessorRegistry.class);

	private IndexerRegistry _indexerRegistry;

}