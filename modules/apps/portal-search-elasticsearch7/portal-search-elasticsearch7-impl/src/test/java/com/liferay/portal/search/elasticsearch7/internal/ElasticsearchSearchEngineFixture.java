/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.configuration.OperationModeResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.index.CompanyIdIndexNameBuilder;
import com.liferay.portal.search.elasticsearch7.internal.index.CompanyIndexFactory;
import com.liferay.portal.search.elasticsearch7.internal.index.CompanyIndexFactoryHelper;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexConfigurationDynamicUpdatesExecutor;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.ElasticsearchEngineAdapterFixture;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.test.util.search.engine.SearchEngineFixture;

import java.util.Map;
import java.util.Objects;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Adam Brandizzi
 */
public class ElasticsearchSearchEngineFixture implements SearchEngineFixture {

	public ElasticsearchSearchEngineFixture(
		ElasticsearchConnectionFixture elasticsearchConnectionFixture) {

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;
	}

	public ElasticsearchConnectionManager getElasticsearchConnectionManager() {
		return _elasticsearchConnectionManager;
	}

	public ElasticsearchSearchEngine getElasticsearchSearchEngine() {
		return _elasticsearchSearchEngine;
	}

	@Override
	public IndexNameBuilder getIndexNameBuilder() {
		return _indexNameBuilder;
	}

	@Override
	public SearchEngine getSearchEngine() {
		return getElasticsearchSearchEngine();
	}

	@Override
	public void setUp() throws Exception {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			Objects.requireNonNull(_elasticsearchConnectionFixture);

		CompanyIdIndexNameBuilder indexNameBuilder = _createIndexNameBuilder();

		_frameworkUtilMockedStatic = _createFrameworkUtil();

		ElasticsearchConnectionManager elasticsearchConnectionManager =
			_createElasticsearchConnectionManager(
				elasticsearchConnectionFixture);

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
		_elasticsearchSearchEngine = _createElasticsearchSearchEngine(
			elasticsearchConnectionFixture, elasticsearchConnectionManager,
			Mockito.mock(IndexConfigurationDynamicUpdatesExecutor.class),
			indexNameBuilder,
			elasticsearchConnectionFixture.
				getElasticsearchConfigurationProperties());

		_indexNameBuilder = indexNameBuilder;
	}

	@Override
	public void tearDown() throws Exception {
		_elasticsearchConnectionFixture.destroyNode();

		_elasticsearchEngineAdapterFixture.tearDown();

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

	protected static ElasticsearchConfigurationWrapper
		createElasticsearchConfigurationWrapper(
			Map<String, Object> properties) {

		return new ElasticsearchConfigurationWrapper() {
			{
				setElasticsearchConfiguration(
					ConfigurableUtil.createConfigurable(
						ElasticsearchConfiguration.class, properties));
			}
		};
	}

	private CompanyIndexFactory _createCompanyIndexFactory(
		IndexNameBuilder indexNameBuilder, Map<String, Object> properites) {

		_companyIndexFactory = new CompanyIndexFactory();

		_companyIndexFactoryHelper = new CompanyIndexFactoryHelper();

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryHelper, "_elasticsearchConfigurationWrapper",
			createElasticsearchConfigurationWrapper(properites));
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryHelper, "_indexNameBuilder", indexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryHelper, "_jsonFactory", new JSONFactoryImpl());

		ReflectionTestUtil.invoke(
			_companyIndexFactoryHelper, "activate",
			new Class<?>[] {BundleContext.class},
			SystemBundleUtil.getBundleContext());

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_companyIndexFactoryHelper",
			_companyIndexFactoryHelper);

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_elasticsearchConfigurationWrapper",
			createElasticsearchConfigurationWrapper(properites));

		ReflectionTestUtil.invoke(
			_companyIndexFactory, "activate",
			new Class<?>[] {BundleContext.class},
			SystemBundleUtil.getBundleContext());

		return _companyIndexFactory;
	}

	private ElasticsearchConnectionManager
		_createElasticsearchConnectionManager(
			ElasticsearchConnectionFixture elasticsearchConnectionFixture) {

		return new ElasticsearchConnectionManager() {
			{
				elasticsearchConfigurationWrapper =
					createElasticsearchConfigurationWrapper(
						elasticsearchConnectionFixture.
							getElasticsearchConfigurationProperties());

				operationModeResolver = _createOperationModeResolver(
					elasticsearchConfigurationWrapper);

				addElasticsearchConnection(
					elasticsearchConnectionFixture.
						createElasticsearchConnection());
			}
		};
	}

	private ElasticsearchSearchEngine _createElasticsearchSearchEngine(
		ElasticsearchClientResolver elasticsearchClientResolver,
		ElasticsearchConnectionManager elasticsearchConnectionManager,
		IndexConfigurationDynamicUpdatesExecutor
			indexConfigurationDynamicUpdatesExecutor,
		IndexNameBuilder indexNameBuilder, Map<String, Object> properites) {

		ElasticsearchSearchEngine elasticsearchSearchEngine =
			new ElasticsearchSearchEngine();

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngine, "_elasticsearchConnectionManager",
			elasticsearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngine,
			"_indexConfigurationDynamicUpdatesExecutor",
			indexConfigurationDynamicUpdatesExecutor);
		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngine, "_indexFactory",
			_createCompanyIndexFactory(indexNameBuilder, properites));
		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngine, "_indexNameBuilder",
			(IndexNameBuilder)String::valueOf);
		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngine, "_searchEngineAdapter",
			_createSearchEngineAdapter(elasticsearchClientResolver));

		return elasticsearchSearchEngine;
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

	private CompanyIdIndexNameBuilder _createIndexNameBuilder() {
		return new CompanyIdIndexNameBuilder() {
			{
				setIndexNamePrefix(null);
			}
		};
	}

	private OperationModeResolver _createOperationModeResolver(
		ElasticsearchConfigurationWrapper elasticsearchConfigurationWrapper1) {

		return new OperationModeResolver() {
			{
				elasticsearchConfigurationWrapper =
					elasticsearchConfigurationWrapper1;
			}
		};
	}

	private SearchEngineAdapter _createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchEngineAdapterFixture =
			new ElasticsearchEngineAdapterFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
				}
			};

		_elasticsearchEngineAdapterFixture.setUp();

		return _elasticsearchEngineAdapterFixture.getSearchEngineAdapter();
	}

	private CompanyIndexFactory _companyIndexFactory;
	private CompanyIndexFactoryHelper _companyIndexFactoryHelper;
	private final ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private ElasticsearchEngineAdapterFixture
		_elasticsearchEngineAdapterFixture;
	private ElasticsearchSearchEngine _elasticsearchSearchEngine;
	private MockedStatic<FrameworkUtil> _frameworkUtilMockedStatic;
	private IndexNameBuilder _indexNameBuilder;

}