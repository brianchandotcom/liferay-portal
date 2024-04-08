/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.opensearch2.internal.OpenSearchSearchEngine;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.connection.TestOpenSearchConnectionManager;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import java.util.Collections;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.cluster.GetClusterSettingsResponse;
import org.opensearch.client.opensearch.cluster.OpenSearchClusterClient;
import org.opensearch.client.opensearch.cluster.PutClusterSettingsRequest;

/**
 * @author Petteri Karttunen
 */
public class OpenSearchSearchEngineAutoCreateIndexTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_indexNamePrefix = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		_disableLiferayIndexAutoCreationPattern = StringBundler.concat(
			StringPool.MINUS, _indexNamePrefix, StringPool.STAR);
		_enableLiferayIndexAutoCreationPattern = StringBundler.concat(
			StringPool.PLUS, _indexNamePrefix, StringPool.STAR);

		OpenSearchConnectionManager openSearchConnectionManager =
			new TestOpenSearchConnectionManager(
				Collections.singletonMap("indexNamePrefix", _indexNamePrefix));

		OpenSearchSearchEngineFixture openSearchSearchEngineFixture =
			new OpenSearchSearchEngineFixture(openSearchConnectionManager);

		openSearchSearchEngineFixture.setUp();

		_openSearchConnectionManager = openSearchConnectionManager;
		_openSearchSearchEngineFixture = openSearchSearchEngineFixture;
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_openSearchSearchEngineFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		_setIndexAutoCreationSetting(StringPool.BLANK);
	}

	@After
	public void tearDown() throws Exception {
		_setIndexAutoCreationSetting(StringPool.BLANK);
	}

	@Test
	public void testDisableAutoCreateIndexWithExistingValueBlank()
		throws Exception {

		OpenSearchSearchEngine openSearchSearchEngine =
			_openSearchSearchEngineFixture.getOpenSearchSearchEngine();

		_getAutoCreateIndexSetting();

		openSearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableLiferayIndexAutoCreationPattern + _COMMA_AND_SPACE_AND_STAR,
			_getAutoCreateIndexSetting());
	}

	@Test
	public void testDisableAutoCreateIndexWithExistingValueDisabled()
		throws Exception {

		OpenSearchSearchEngine openSearchSearchEngine =
			_openSearchSearchEngineFixture.getOpenSearchSearchEngine();

		_setIndexAutoCreationSetting("false");

		openSearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals("false", _getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting(_disableLiferayIndexAutoCreationPattern);

		openSearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableLiferayIndexAutoCreationPattern,
			_getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting(
			StringBundler.concat(
				"+my-index-1*, ", _disableLiferayIndexAutoCreationPattern,
				_COMMA_AND_SPACE_AND_STAR));

		openSearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			StringBundler.concat(
				"+my-index-1*, ", _disableLiferayIndexAutoCreationPattern,
				_COMMA_AND_SPACE_AND_STAR),
			_getAutoCreateIndexSetting());
	}

	@Test
	public void testDisableAutoCreateIndexWithExistingValueEnabled()
		throws Exception {

		OpenSearchSearchEngine openSearchSearchEngine =
			_openSearchSearchEngineFixture.getOpenSearchSearchEngine();

		_setIndexAutoCreationSetting(StringPool.STAR);

		openSearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableLiferayIndexAutoCreationPattern + _COMMA_AND_SPACE_AND_STAR,
			_getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting("true");

		openSearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableLiferayIndexAutoCreationPattern + _COMMA_AND_SPACE_AND_STAR,
			_getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting(_enableLiferayIndexAutoCreationPattern);

		openSearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableLiferayIndexAutoCreationPattern,
			_getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting(
			"+my-index*, " + _enableLiferayIndexAutoCreationPattern);

		openSearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			"+my-index*, " + _disableLiferayIndexAutoCreationPattern,
			_getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting("-my-index");

		openSearchSearchEngine.setAutoCreateIndex(false);

		Assert.assertEquals(
			_disableLiferayIndexAutoCreationPattern + ", -my-index",
			_getAutoCreateIndexSetting());
	}

	@Test
	public void testEnableAutoCreateIndexWithExistingValueBlank()
		throws Exception {

		OpenSearchSearchEngine openSearchSearchEngine =
			_openSearchSearchEngineFixture.getOpenSearchSearchEngine();

		openSearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(StringPool.BLANK, _getAutoCreateIndexSetting());
	}

	@Test
	public void testEnableAutoCreateIndexWithExistingValueDisabled()
		throws Exception {

		OpenSearchSearchEngine openSearchSearchEngine =
			_openSearchSearchEngineFixture.getOpenSearchSearchEngine();

		_setIndexAutoCreationSetting("false");

		Assert.assertEquals("false", _getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting(_disableLiferayIndexAutoCreationPattern);

		openSearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(
			_enableLiferayIndexAutoCreationPattern,
			_getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting(
			StringBundler.concat(
				"+my-index-1*, ", _disableLiferayIndexAutoCreationPattern,
				_COMMA_AND_SPACE_AND_STAR));

		openSearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(
			StringBundler.concat(
				"+my-index-1*, ", _enableLiferayIndexAutoCreationPattern,
				_COMMA_AND_SPACE_AND_STAR),
			_getAutoCreateIndexSetting());
	}

	@Test
	public void testEnableAutoCreateIndexWithExistingValueEnabled()
		throws Exception {

		OpenSearchSearchEngine openSearchSearchEngine =
			_openSearchSearchEngineFixture.getOpenSearchSearchEngine();

		_setIndexAutoCreationSetting("true");

		openSearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals("true", _getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting(StringPool.STAR);

		openSearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(StringPool.STAR, _getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting(_enableLiferayIndexAutoCreationPattern);

		openSearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(
			_enableLiferayIndexAutoCreationPattern,
			_getAutoCreateIndexSetting());

		_setIndexAutoCreationSetting(
			StringBundler.concat(
				"+my-index-1*, ", _enableLiferayIndexAutoCreationPattern,
				_COMMA_AND_SPACE_AND_STAR));

		openSearchSearchEngine.setAutoCreateIndex(true);

		Assert.assertEquals(
			StringBundler.concat(
				"+my-index-1*, ", _enableLiferayIndexAutoCreationPattern,
				_COMMA_AND_SPACE_AND_STAR),
			_getAutoCreateIndexSetting());
	}

	private String _getAutoCreateIndexSetting() throws Exception {
		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient();

		OpenSearchClusterClient openSearchClusterClient =
			openSearchClient.cluster();

		GetClusterSettingsResponse getClusterSettingsResponse =
			openSearchClusterClient.getSettings();

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

	private void _setIndexAutoCreationSetting(String value) throws Exception {
		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient();

		OpenSearchClusterClient openSearchClusterClient =
			openSearchClient.cluster();

		openSearchClusterClient.putSettings(
			PutClusterSettingsRequest.of(
				putClusterSettingsRequest ->
					putClusterSettingsRequest.persistent(
						"action.auto_create_index", JsonData.of(value))));
	}

	private static final String _COMMA_AND_SPACE_AND_STAR = ", *";

	private static String _disableLiferayIndexAutoCreationPattern;
	private static String _enableLiferayIndexAutoCreationPattern;
	private static String _indexNamePrefix;
	private static OpenSearchConnectionManager _openSearchConnectionManager;
	private static OpenSearchSearchEngineFixture _openSearchSearchEngineFixture;

}