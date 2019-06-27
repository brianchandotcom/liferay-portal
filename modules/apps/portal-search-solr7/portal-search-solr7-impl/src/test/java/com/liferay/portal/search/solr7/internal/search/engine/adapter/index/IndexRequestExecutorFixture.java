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

package com.liferay.portal.search.solr7.internal.search.engine.adapter.index;

import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;
import com.liferay.portal.search.solr7.internal.connection.SolrClientManager;

/**
 * @author Bryan Engler
 */
public class IndexRequestExecutorFixture {

	public IndexRequestExecutor getIndexRequestExecutor() {
		return _indexRequestExecutor;
	}

	public void setUp() {
		_indexRequestExecutor = createIndexRequestExecutor(_solrClientManager);
	}

	protected IndexRequestExecutor createIndexRequestExecutor(
		SolrClientManager solrClientManager) {

		return new SolrIndexRequestExecutor() {
			{
				setRefreshIndexRequestExecutor(
					createRefreshIndexRequestExecutor(solrClientManager));
			}
		};
	}

	protected RefreshIndexRequestExecutor createRefreshIndexRequestExecutor(
		SolrClientManager solrClientManager) {

		return new RefreshIndexRequestExecutorImpl() {
			{
				setSolrClientManager(solrClientManager);
			}
		};
	}

	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	private IndexRequestExecutor _indexRequestExecutor;
	private SolrClientManager _solrClientManager;

}