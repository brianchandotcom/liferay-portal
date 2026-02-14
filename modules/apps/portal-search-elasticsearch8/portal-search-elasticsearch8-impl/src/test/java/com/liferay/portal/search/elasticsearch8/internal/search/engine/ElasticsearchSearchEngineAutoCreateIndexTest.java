/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cluster.ElasticsearchClusterClient;
import co.elastic.clients.elasticsearch.cluster.GetClusterSettingsResponse;
import co.elastic.clients.elasticsearch.cluster.PutClusterSettingsRequest;
import co.elastic.clients.json.JsonData;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch8.internal.ElasticsearchSearchEngine;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class ElasticsearchSearchEngineAutoCreateIndexTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_indexNamePrefix = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		_disableAutoCreateLiferayIndexPattern = StringBundler.concat(
			StringPool.MINUS, _indexNamePrefix, StringPool.STAR);
		_enableAutoCreateLiferayIndexPattern = StringBundler.concat(
			StringPool.PLUS, _indexNamePrefix, StringPool.STAR);

		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				ElasticsearchSearchEngineAutoCreateIndexTest.class.
					getSimpleName()
			).elasticsearchConfigurationProperties(
				HashMapBuilder.<String, Object>put(
					"indexNamePrefix", _indexNamePrefix
				).build()
			).build();

		ElasticsearchSearchEngineFixture elasticsearchSearchEngineFixture =
			new ElasticsearchSearchEngineFixture(
				elasticsearchConnectionFixture);

		elasticsearchSearchEngineFixture.setUp();

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;
		_elasticsearchSearchEngineFixture = elasticsearchSearchEngineFixture;
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchSearchEngineFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		_setAutoCreateIndexSetting(StringPool.BLANK);
	}

	@After
	public void tearDown() throws Exception {
		_setAutoCreateIndexSetting(StringPool.BLANK);
	}

	@Test
	public void testDisableAutoCreateIndexWithExistingValueBlank()
		throws Exception {

		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		elasticsearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableAutoCreateLiferayIndexPattern + _COMMA_AND_SPACE_AND_STAR,
			_getAutoCreateIndexSetting());
	}

	@Test
	public void testDisableAutoCreateIndexWithExistingValueDisabled()
		throws Exception {

		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		_setAutoCreateIndexSetting("false");

		elasticsearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals("false", _getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting(_disableAutoCreateLiferayIndexPattern);

		elasticsearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableAutoCreateLiferayIndexPattern,
			_getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting(
			StringBundler.concat(
				"+my-index-1*, ", _disableAutoCreateLiferayIndexPattern,
				_COMMA_AND_SPACE_AND_STAR));

		elasticsearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			StringBundler.concat(
				"+my-index-1*, ", _disableAutoCreateLiferayIndexPattern,
				_COMMA_AND_SPACE_AND_STAR),
			_getAutoCreateIndexSetting());
	}

	@Test
	public void testDisableAutoCreateIndexWithExistingValueEnabled()
		throws Exception {

		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		_setAutoCreateIndexSetting(StringPool.STAR);

		elasticsearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableAutoCreateLiferayIndexPattern + _COMMA_AND_SPACE_AND_STAR,
			_getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting("true");

		elasticsearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableAutoCreateLiferayIndexPattern + _COMMA_AND_SPACE_AND_STAR,
			_getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting(_enableAutoCreateLiferayIndexPattern);

		elasticsearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableAutoCreateLiferayIndexPattern,
			_getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting(
			"+my-index*, " + _enableAutoCreateLiferayIndexPattern);

		elasticsearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			"+my-index*, " + _disableAutoCreateLiferayIndexPattern,
			_getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting("-my-index");

		elasticsearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableAutoCreateLiferayIndexPattern + ", -my-index",
			_getAutoCreateIndexSetting());
	}

	@Test
	public void testEnableAutoCreateIndexWithExistingValueBlank()
		throws Exception {

		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		elasticsearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(StringPool.BLANK, _getAutoCreateIndexSetting());
	}

	@Test
	public void testEnableAutoCreateIndexWithExistingValueDisabled()
		throws Exception {

		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		_setAutoCreateIndexSetting("false");

		Assert.assertEquals("false", _getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting(_disableAutoCreateLiferayIndexPattern);

		elasticsearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(
			_enableAutoCreateLiferayIndexPattern, _getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting(
			StringBundler.concat(
				"+my-index-1*, ", _disableAutoCreateLiferayIndexPattern,
				_COMMA_AND_SPACE_AND_STAR));

		elasticsearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(
			StringBundler.concat(
				"+my-index-1*, ", _enableAutoCreateLiferayIndexPattern,
				_COMMA_AND_SPACE_AND_STAR),
			_getAutoCreateIndexSetting());
	}

	@Test
	public void testEnableAutoCreateIndexWithExistingValueEnabled()
		throws Exception {

		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		_setAutoCreateIndexSetting("true");

		elasticsearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals("true", _getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting(StringPool.STAR);

		elasticsearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(StringPool.STAR, _getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting(_enableAutoCreateLiferayIndexPattern);

		elasticsearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(
			_enableAutoCreateLiferayIndexPattern, _getAutoCreateIndexSetting());

		_setAutoCreateIndexSetting(
			StringBundler.concat(
				"+my-index-1*, ", _enableAutoCreateLiferayIndexPattern,
				_COMMA_AND_SPACE_AND_STAR));

		elasticsearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(
			StringBundler.concat(
				"+my-index-1*, ", _enableAutoCreateLiferayIndexPattern,
				_COMMA_AND_SPACE_AND_STAR),
			_getAutoCreateIndexSetting());
	}

	private String _getAutoCreateIndexSetting() throws Exception {
		ElasticsearchClient elasticsearchClient =
			_elasticsearchConnectionFixture.getElasticsearchClient();

		ElasticsearchClusterClient elasticsearchClusterClient =
			elasticsearchClient.cluster();

		GetClusterSettingsResponse getClusterSettingsResponse =
			elasticsearchClusterClient.getSettings();

		Map<String, JsonData> persistentSettings =
			getClusterSettingsResponse.persistent();

		JsonData jsonData = persistentSettings.get("action");

		if (jsonData == null) {
			return null;
		}

		JsonValue jsonValue = jsonData.toJson();

		JsonObject jsonObject = jsonValue.asJsonObject();

		return jsonObject.getString("auto_create_index");
	}

	private void _setAutoCreateIndexSetting(String value) throws Exception {
		ElasticsearchClient elasticsearchClient =
			_elasticsearchConnectionFixture.getElasticsearchClient();

		ElasticsearchClusterClient elasticsearchClusterClient =
			elasticsearchClient.cluster();

		elasticsearchClusterClient.putSettings(
			PutClusterSettingsRequest.of(
				putClusterSettingsRequest ->
					putClusterSettingsRequest.persistent(
						"action.auto_create_index", JsonData.of(value))));
	}

	private static final String _COMMA_AND_SPACE_AND_STAR = ", *";

	private static String _disableAutoCreateLiferayIndexPattern;
	private static ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;
	private static ElasticsearchSearchEngineFixture
		_elasticsearchSearchEngineFixture;
	private static String _enableAutoCreateLiferayIndexPattern;
	private static String _indexNamePrefix;

}