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

package com.liferay.portal.search.elasticsearch6.internal.synonym;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch6.internal.document.SingleFieldFixture;
import com.liferay.portal.search.elasticsearch6.internal.index.CompanyIndexFactoryFixture;
import com.liferay.portal.search.elasticsearch6.internal.index.LiferayTypeMappingsConstants;
import com.liferay.portal.search.elasticsearch6.internal.query.QueryBuilderFactories;
import com.liferay.portal.search.synonym.SynonymException;
import com.liferay.portal.search.synonym.SynonymIndexer;

/**
 * @author Adam Brandizzi
 */
public class ElasticsearchSynonymIndexerFixture {

	public ElasticsearchSynonymIndexerFixture(
		String subdirName, String indexName, String filterName) {

		_subdirName = subdirName;
		_indexName = indexName;
		_filterName = filterName;
	}

	public void assertSynonym(
			String fieldName, String fieldValue, String indexedSynonym,
			String searchedSynonym, String qureyString)
		throws Exception, SynonymException {

		indexField(fieldName, fieldValue);

		_singleFieldFixture.assertNoHits(qureyString);

		SynonymIndexer synonymIndexer = getElasticsearchSynonymIndexer();

		synonymIndexer.updateSynonymSets(
			_indexName, _filterName,
			new String[] {searchedSynonym + "," + indexedSynonym});

		_elasticsearchFixture.waitForElasticsearchToStart();

		_singleFieldFixture.assertSearch(qureyString, fieldValue);
	}

	public ElasticsearchSynonymIndexer getElasticsearchSynonymIndexer() {
		if (_elasticsearchSynonymIndexer == null) {
			_elasticsearchSynonymIndexer = new ElasticsearchSynonymIndexer() {
				{
					elasticsearchClientResolver = _elasticsearchFixture;
					jsonFactory = new JSONFactoryImpl();
				}
			};
		}

		return _elasticsearchSynonymIndexer;
	}

	public String getIndexName() {
		return _indexName;
	}

	public void indexField(String field, String content) {
		_singleFieldFixture.setField(field);

		_singleFieldFixture.indexDocument(content);
	}

	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(_subdirName);

		_elasticsearchFixture.setUp();

		_companyIndexFactoryFixture = new CompanyIndexFactoryFixture(
			_elasticsearchFixture, _indexName);

		_indexName = _companyIndexFactoryFixture.getIndexName();

		_companyIndexFactoryFixture.createIndices();

		_singleFieldFixture = new SingleFieldFixture(
			_elasticsearchFixture.getClient(),
			new IndexName(_companyIndexFactoryFixture.getIndexName()),
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);

		_singleFieldFixture.setQueryBuilderFactory(QueryBuilderFactories.MATCH);
	}

	public void tearDown() throws Exception {
		ElasticsearchSynonymIndexer elasticsearchSynonymIndexer =
			getElasticsearchSynonymIndexer();

		elasticsearchSynonymIndexer.updateSynonymSets(
			_companyIndexFactoryFixture.getIndexName(), _filterName,
			new String[0]);

		_elasticsearchFixture.waitForElasticsearchToStart();

		_elasticsearchFixture.tearDown();
	}

	public void updateSynonymSets(String[] synonymSet) throws SynonymException {
		ElasticsearchSynonymIndexer elasticsearchSynonymIndexer =
			getElasticsearchSynonymIndexer();

		elasticsearchSynonymIndexer.updateSynonymSets(
			_indexName, _filterName, synonymSet);

		_elasticsearchFixture.waitForElasticsearchToStart();
	}

	private CompanyIndexFactoryFixture _companyIndexFactoryFixture;
	private ElasticsearchFixture _elasticsearchFixture;
	private ElasticsearchSynonymIndexer _elasticsearchSynonymIndexer;
	private final String _filterName;
	private String _indexName;
	private SingleFieldFixture _singleFieldFixture;
	private final String _subdirName;

}