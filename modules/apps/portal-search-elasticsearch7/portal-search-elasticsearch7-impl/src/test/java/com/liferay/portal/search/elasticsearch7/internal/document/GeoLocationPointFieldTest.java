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

package com.liferay.portal.search.elasticsearch7.internal.document;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.elasticsearch7.internal.ElasticsearchIndexingFixture;
import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.helper.IndexCreationHelper;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentType;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class GeoLocationPointFieldTest extends BaseIndexingTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCustomField() throws Exception {
		_assertGeoLocationPointField(_CUSTOM_FIELD);
	}

	@Test
	public void testDefaultField() throws Exception {
		_assertGeoLocationPointField(Field.GEO_LOCATION);
	}

	@Test
	public void testDefaultTemplate() throws Exception {
		_assertGeoLocationPointField(_CUSTOM_FIELD.concat("_geolocation"));
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		ElasticsearchIndexingFixture elasticsearchIndexingFixture =
			LiferayElasticsearchIndexingFixtureFactory.builder(
			).build();

		elasticsearchIndexingFixture.setIndexCreationHelper(
			new CustomFieldLiferayIndexCreationHelper(
				elasticsearchIndexingFixture.getElasticsearchClientResolver()));

		return elasticsearchIndexingFixture;
	}

	private void _assertGeoLocationPointField(String fieldName) {
		double latitude = 33.99772698059678;
		double longitude = -117.814457193017;

		String expected = "(33.99772698059678,-117.814457193017)";

		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				fieldName, latitude, longitude));

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> DocumentsAssert.assertValues(
						searchResponse.getRequestString(),
						searchResponse.getDocumentsStream(), fieldName,
						"[" + expected + "]"));
			});
	}

	private static final String _CUSTOM_FIELD = "customField";

	private static class CustomFieldLiferayIndexCreationHelper
		implements IndexCreationHelper {

		public CustomFieldLiferayIndexCreationHelper(
			ElasticsearchClientResolver elasticsearchClientResolver) {

			_elasticsearchClientResolver = elasticsearchClientResolver;
		}

		@Override
		public void contribute(CreateIndexRequest createIndexRequest) {
		}

		@Override
		public void contributeIndexSettings(Settings.Builder builder) {
		}

		@Override
		public void whenIndexCreated(String indexName) {
			PutMappingRequest putMappingRequest = new PutMappingRequest(
				indexName);

			String source = StringBundler.concat(
				"{ \"properties\": { \"", _CUSTOM_FIELD, "\" : { \"fields\": ",
				"{ \"geopoint\" : { \"store\": true, \"type\": \"keyword\" } ",
				"}, \"store\": true, \"type\": \"geo_point\" } } }");

			putMappingRequest.source(source, XContentType.JSON);

			RestHighLevelClient restHighLevelClient =
				_elasticsearchClientResolver.getRestHighLevelClient();

			IndicesClient indicesClient = restHighLevelClient.indices();

			try {
				indicesClient.putMapping(
					putMappingRequest, RequestOptions.DEFAULT);
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		private final ElasticsearchClientResolver _elasticsearchClientResolver;

	}

}