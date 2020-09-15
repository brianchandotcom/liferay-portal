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

package com.liferay.portal.search.internal.buffer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchException;

import java.util.Set;

/**
 * @author Michael C. Han
 */
public abstract class BaseIndexerRequestBufferExecutor
	implements IndexerRequestBufferExecutor {

	@Override
	public void execute(IndexerRequestBuffer indexerRequestBuffer) {
		execute(indexerRequestBuffer, indexerRequestBuffer.size());
	}

	protected void commit(Set<String> searchEngineIds) {
		IndexWriterHelper indexWriterHelper = getIndexWriterHelper();

		if (indexWriterHelper == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Index writer helper is null");
			}

			return;
		}

		for (String searchEngineId : searchEngineIds) {
			try {
				indexWriterHelper.commit(searchEngineId);
			}
			catch (SearchException searchException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to commit search engine", searchException);
				}
			}
		}
	}

	protected void executeIndexerRequest(
		Set<String> searchEngineIds, IndexerRequest indexerRequest) {

		try {
			indexerRequest.execute();

			searchEngineIds.add(indexerRequest.getSearchEngineId());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to execute index request " + indexerRequest,
					exception);
			}
		}
	}

	protected abstract IndexWriterHelper getIndexWriterHelper();

	private static final Log _log = LogFactoryUtil.getLog(
		BaseIndexerRequestBufferExecutor.class);

}