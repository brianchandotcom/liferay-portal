/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexResponse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author André de Oliveira
 */
public class CompanyIdIndexNameBuilderTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_frameworkUtilMockedStatic.when(
			() -> FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_frameworkUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				CompanyIdIndexNameBuilderTest.class.getSimpleName()
			).build();

		_elasticsearchFixture = new ElasticsearchFixture(
			elasticsearchConnectionFixture);

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();

		if (_companyIndexFactory != null) {
			ReflectionTestUtil.invoke(
				_companyIndexFactory, "deactivate", new Class<?>[0]);

			_companyIndexFactory = null;
		}

		if (_companyIndexFactoryHelper != null) {
			ReflectionTestUtil.invoke(
				_companyIndexFactoryHelper, "deactivate", new Class<?>[0]);

			_companyIndexFactoryHelper = null;
		}
	}

	@Test
	public void testActivate() throws Exception {
		ElasticsearchConfigurationWrapper
			elasticsearchConfigurationWrapperMock = Mockito.mock(
				ElasticsearchConfigurationWrapper.class);

		Mockito.when(
			elasticsearchConfigurationWrapperMock.indexNamePrefix()
		).thenReturn(
			"UPPERCASE"
		);

		CompanyIdIndexNameBuilder companyIdIndexNameBuilder =
			new CompanyIdIndexNameBuilder() {
				{
					elasticsearchConfigurationWrapper =
						elasticsearchConfigurationWrapperMock;
				}
			};

		companyIdIndexNameBuilder.activate();

		Assert.assertEquals(
			"uppercase0", companyIdIndexNameBuilder.getIndexName(0));
	}

	@Test
	public void testIndexNamePrefixBlank() throws Exception {
		_assertIndexNamePrefix(StringPool.BLANK, StringPool.BLANK);
	}

	@Test(expected = ElasticsearchStatusException.class)
	public void testIndexNamePrefixInvalidIndexName() throws Exception {
		createIndices(StringPool.SLASH, 0);
	}

	@Test
	public void testIndexNamePrefixNull() throws Exception {
		_assertIndexNamePrefix(null, StringPool.BLANK);
	}

	@Test
	public void testIndexNamePrefixTrim() throws Exception {
		String string = RandomTestUtil.randomString();

		_assertIndexNamePrefix(
			StringPool.TAB + string + StringPool.SPACE,
			StringUtil.toLowerCase(string));
	}

	@Test
	public void testIndexNamePrefixUppercase() throws Exception {
		_assertIndexNamePrefix("UPPERCASE", "uppercase");
	}

	protected ElasticsearchConfigurationWrapper
		createElasticsearchConfigurationWrapper() {

		return new ElasticsearchConfigurationWrapper() {
			{
				setElasticsearchConfiguration(
					ConfigurableUtil.createConfigurable(
						ElasticsearchConfiguration.class,
						Collections.emptyMap()));
			}
		};
	}

	protected void createIndices(String indexNamePrefix, long companyId)
		throws Exception {

		CompanyIdIndexNameBuilder companyIdIndexNameBuilder =
			new CompanyIdIndexNameBuilder();

		companyIdIndexNameBuilder.setIndexNamePrefix(indexNamePrefix);

		_companyIndexFactoryHelper = new CompanyIndexFactoryHelper();

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryHelper, "_elasticsearchConfigurationWrapper",
			createElasticsearchConfigurationWrapper());
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryHelper, "_indexNameBuilder",
			companyIdIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryHelper, "_jsonFactory", new JSONFactoryImpl());

		ReflectionTestUtil.invoke(
			_companyIndexFactoryHelper, "activate",
			new Class<?>[] {BundleContext.class},
			SystemBundleUtil.getBundleContext());

		_companyIndexFactory = new CompanyIndexFactory();

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_companyIndexFactoryHelper",
			_companyIndexFactoryHelper);
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_elasticsearchConfigurationWrapper",
			createElasticsearchConfigurationWrapper());

		ReflectionTestUtil.invoke(
			_companyIndexFactory, "activate",
			new Class<?>[] {BundleContext.class},
			SystemBundleUtil.getBundleContext());

		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		_companyIndexFactory.createIndices(
			restHighLevelClient.indices(), companyId);
	}

	private void _assertIndexNamePrefix(
			String indexNamePrefix, String expectedIndexNamePrefix)
		throws Exception {

		long companyId = RandomTestUtil.randomLong();

		createIndices(indexNamePrefix, companyId);

		String expectedIndexName = expectedIndexNamePrefix + companyId;

		GetIndexResponse getIndexResponse = _elasticsearchFixture.getIndex(
			expectedIndexName);

		Assert.assertArrayEquals(
			new String[] {expectedIndexName}, getIndexResponse.getIndices());
	}

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

	private CompanyIndexFactory _companyIndexFactory;
	private CompanyIndexFactoryHelper _companyIndexFactoryHelper;
	private ElasticsearchFixture _elasticsearchFixture;

}