/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author Adam Brandizzi
 */
public class ElasticsearchIndexInformationTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				ElasticsearchIndexInformationTest.class.getSimpleName()
			).build();

		elasticsearchConnectionFixture.createNode();

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchConnectionFixture.destroyNode();
	}

	@Before
	public void setUp() throws Exception {
		_companyIndexFactoryFixture = _createCompanyIndexFactoryFixture(
			_elasticsearchConnectionFixture);

		_elasticsearchIndexInformation = _createElasticsearchIndexInformation(
			_elasticsearchConnectionFixture);
	}

	@After
	public void tearDown() {
		_companyIndexFactoryFixture.tearDown();
	}

	@Test
	public void testGetCompanyIndexName() throws Exception {
		_companyIndexFactoryFixture.createIndices();

		long companyId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			_getIndexNameBuilder(companyId),
			_elasticsearchIndexInformation.getCompanyIndexName(companyId));
	}

	@Test
	public void testGetFieldMappings() throws Exception {
		_companyIndexFactoryFixture.createIndices();

		AssertUtils.assertEquals(
			"", _loadJSONObject(testName.getMethodName()),
			_jsonFactory.createJSONObject(
				_elasticsearchIndexInformation.getFieldMappings(
					_companyIndexFactoryFixture.getIndexName())));
	}

	@Test
	public void testGetIndexNames() throws Exception {
		_companyIndexFactoryFixture.createIndices();

		AssertUtils.assertEquals(
			"", Arrays.asList(_companyIndexFactoryFixture.getIndexName()),
			Arrays.asList(_elasticsearchIndexInformation.getIndexNames()));
	}

	@Rule
	public TestName testName = new TestName();

	private CompanyIndexFactoryFixture _createCompanyIndexFactoryFixture(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new CompanyIndexFactoryFixture(
			elasticsearchClientResolver, testName.getMethodName());
	}

	private ElasticsearchIndexInformation _createElasticsearchIndexInformation(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ElasticsearchIndexInformation elasticsearchIndexInformation =
			new ElasticsearchIndexInformation();

		ReflectionTestUtil.setFieldValue(
			elasticsearchIndexInformation, "_elasticsearchClientResolver",
			elasticsearchClientResolver);
		ReflectionTestUtil.setFieldValue(
			elasticsearchIndexInformation, "_indexNameBuilder",
			(IndexNameBuilder)companyId -> _getIndexNameBuilder(companyId));

		return elasticsearchIndexInformation;
	}

	private String _getIndexNameBuilder(long companyId) {
		return "test-" + companyId;
	}

	private JSONObject _loadJSONObject(String suffix) throws Exception {
		String json = ResourceUtil.getResourceAsString(
			getClass(),
			"ElasticsearchIndexInformationTest-" + suffix + ".json");

		return _jsonFactory.createJSONObject(json);
	}

	private static ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;

	private CompanyIndexFactoryFixture _companyIndexFactoryFixture;
	private ElasticsearchIndexInformation _elasticsearchIndexInformation;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}