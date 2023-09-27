/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionNotInitializedException;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.util.HashMap;

import org.elasticsearch.client.RestHighLevelClient;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Adam Brandizzi
 */
public class CompanyIndexFactoryFixture {

	public CompanyIndexFactoryFixture(
		ElasticsearchClientResolver elasticsearchClientResolver,
		String indexName) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
		_indexName = indexName;

		_frameworkUtilMockedStatic = _createFrameworkUtil();

		_elasticsearchConnectionManager = Mockito.mock(
			ElasticsearchConnectionManager.class);

		Mockito.when(
			_elasticsearchConnectionManager.getRestHighLevelClient()
		).thenThrow(
			ElasticsearchConnectionNotInitializedException.class
		);
	}

	public void createIndices() {
		CompanyIndexFactory companyIndexFactory = getCompanyIndexFactory();

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		companyIndexFactory.createIndices(
			restHighLevelClient.indices(), RandomTestUtil.randomLong());
	}

	public void deleteIndices() {
		CompanyIndexFactory companyIndexFactory = getCompanyIndexFactory();

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		companyIndexFactory.deleteIndices(
			restHighLevelClient.indices(), RandomTestUtil.randomLong());
	}

	public CompanyIndexFactory getCompanyIndexFactory() {
		if (_companyIndexFactory != null) {
			return _companyIndexFactory;
		}

		_companyIndexFactory = new CompanyIndexFactory();

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_companyIndexFactoryHelper",
			getCompanyIndexFactoryHelper());
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_elasticsearchConfigurationWrapper",
			createElasticsearchConfigurationWrapper());

		ReflectionTestUtil.invoke(
			_companyIndexFactory, "activate",
			new Class<?>[] {BundleContext.class},
			SystemBundleUtil.getBundleContext());

		return _companyIndexFactory;
	}

	public CompanyIndexFactoryHelper getCompanyIndexFactoryHelper() {
		if (_companyIndexFactoryHelper != null) {
			return _companyIndexFactoryHelper;
		}

		_companyIndexFactoryHelper = new CompanyIndexFactoryHelper();

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryHelper, "_elasticsearchConfigurationWrapper",
			createElasticsearchConfigurationWrapper());
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryHelper, "_elasticsearchConnectionManager",
			_elasticsearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryHelper, "_indexNameBuilder",
			new TestIndexNameBuilder());
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryHelper, "_jsonFactory", new JSONFactoryImpl());

		ReflectionTestUtil.invoke(
			_companyIndexFactoryHelper, "activate",
			new Class<?>[] {BundleContext.class},
			SystemBundleUtil.getBundleContext());

		return _companyIndexFactoryHelper;
	}

	public String getIndexName() {
		IndexName indexName = new IndexName(_indexName);

		return indexName.getName();
	}

	public void tearDown() {
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

		if (_frameworkUtilMockedStatic != null) {
			_frameworkUtilMockedStatic.close();

			_frameworkUtilMockedStatic = null;
		}
	}

	protected ElasticsearchConfigurationWrapper
		createElasticsearchConfigurationWrapper() {

		return new ElasticsearchConfigurationWrapper() {
			{
				activate(new HashMap<>());
			}
		};
	}

	protected class TestIndexNameBuilder implements IndexNameBuilder {

		@Override
		public String getIndexName(long companyId) {
			return CompanyIndexFactoryFixture.this.getIndexName();
		}

	}

	private MockedStatic<FrameworkUtil> _createFrameworkUtil() {
		MockedStatic<FrameworkUtil> frameworkUtilMockedStatic =
			Mockito.mockStatic(FrameworkUtil.class);

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		frameworkUtilMockedStatic.when(
			() -> FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		return frameworkUtilMockedStatic;
	}

	private CompanyIndexFactory _companyIndexFactory;
	private CompanyIndexFactoryHelper _companyIndexFactoryHelper;
	private final ElasticsearchClientResolver _elasticsearchClientResolver;
	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager;
	private MockedStatic<FrameworkUtil> _frameworkUtilMockedStatic;
	private final String _indexName;

}