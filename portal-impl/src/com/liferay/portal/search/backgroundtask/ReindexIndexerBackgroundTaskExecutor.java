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

package com.liferay.portal.search.backgroundtask;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;

/**
 * @author Andrew Betts
 */
public class ReindexIndexerBackgroundTaskExecutor
	extends ReindexBackgroundTaskExecutor {

	@Override
	protected void reindex(String className, long[] companyIds)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(className);

		if (indexer == null) {
			return;
		}

		for (long companyId : companyIds) {
			SearchEngineUtil.deleteEntityDocuments(
				indexer.getSearchEngineId(), companyId, className, true);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexIndexerBackgroundTaskExecutor.class);

}