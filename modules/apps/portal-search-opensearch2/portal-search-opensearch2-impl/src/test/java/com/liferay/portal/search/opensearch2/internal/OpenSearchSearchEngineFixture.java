/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.opensearch2.configuration.OpenSearchConfiguration;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapper;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapperImpl;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.connection.TestOpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.index.CompanyIdIndexNameBuilder;
import com.liferay.portal.search.opensearch2.internal.index.CompanyIndexFactory;
import com.liferay.portal.search.opensearch2.internal.index.IndexConfigurationDynamicUpdatesExecutor;
import com.liferay.portal.search.opensearch2.internal.index.IndexHelper;
import com.liferay.portal.search.opensearch2.internal.index.IndexHelperImpl;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.OpenSearchEngineAdapterFixture;
import com.liferay.portal.search.test.util.search.engine.SearchEngineFixture;

import java.util.Collections;
import java.util.Map;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Adam Brandizzi
 * @author Petteri Karttunen
 */
public class OpenSearchSearchEngineFixture implements SearchEngineFixture {

	public OpenSearchSearchEngineFixture(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	@Override
	public IndexNameBuilder getIndexNameBuilder() {
		return _indexNameBuilder;
	}

	public OpenSearchConnectionManager getOpenSearchConnectionManager() {
		return _openSearchConnectionManager;
	}

	public OpenSearchSearchEngine getOpenSearchSearchEngine() {
		return _openSearchSearchEngine;
	}

	@Override
	public SearchEngine getSearchEngine() {
		return getOpenSearchSearchEngine();
	}

	@Override
	public void setUp() throws Exception {
		CompanyIdIndexNameBuilder indexNameBuilder = _createIndexNameBuilder();

		_frameworkUtilMockedStatic = _createFrameworkUtil();
		_indexNameBuilder = indexNameBuilder;

		_openSearchSearchEngine = _createOpenSearchSearchEngine(
			Mockito.mock(IndexConfigurationDynamicUpdatesExecutor.class),
			indexNameBuilder);
	}

	@Override
	public void tearDown() throws Exception {
		_openSearchEngineAdapterFixture.tearDown();

		if (_companyIndexFactory != null) {
			ReflectionTestUtil.invoke(
				_companyIndexFactory, "deactivate", new Class<?>[0]);

			_companyIndexFactory = null;
		}

		if (_indexHelper != null) {
			ReflectionTestUtil.invoke(
				_indexHelper, "deactivate", new Class<?>[0]);

			_indexHelper = null;
		}

		if (_frameworkUtilMockedStatic != null) {
			_frameworkUtilMockedStatic.close();

			_frameworkUtilMockedStatic = null;
		}
	}

	protected static OpenSearchConfigurationWrapper
		createOpenSearchConfigurationWrapper(
			Map<String, Object> configurationProperties) {

		return new OpenSearchConfigurationWrapperImpl() {
			{
				if (configurationProperties == null) {
					setOpenSearchConfiguration(
						ConfigurableUtil.createConfigurable(
							OpenSearchConfiguration.class,
							Collections.emptyMap()));
				}
				else {
					setOpenSearchConfiguration(
						ConfigurableUtil.createConfigurable(
							OpenSearchConfiguration.class,
							configurationProperties));
				}
			}
		};
	}

	private CompanyIndexFactory _createCompanyIndexFactory(
		IndexNameBuilder indexNameBuilder, Map<String, Object> properties,
		SearchEngineAdapter searchEngineAdapter) {

		_indexHelper = new IndexHelperImpl();

		ReflectionTestUtil.setFieldValue(
			_indexHelper, "_companyLocalService",
			Mockito.mock(CompanyLocalService.class));
		ReflectionTestUtil.setFieldValue(
			_indexHelper, "_indexNameBuilder", indexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_indexHelper, "_jsonFactory", new JSONFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_indexHelper, "_openSearchConfigurationWrapper",
			createOpenSearchConfigurationWrapper(properties));
		ReflectionTestUtil.setFieldValue(
			_indexHelper, "_openSearchConnectionManager",
			_openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			_indexHelper, "_searchEngineAdapter", searchEngineAdapter);

		ReflectionTestUtil.invoke(
			_indexHelper, "activate", new Class<?>[] {BundleContext.class},
			SystemBundleUtil.getBundleContext());

		_companyIndexFactory = new CompanyIndexFactory();

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_companyLocalService",
			Mockito.mock(CompanyLocalService.class));
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_indexHelper", _indexHelper);
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_openSearchConfigurationWrapper",
			createOpenSearchConfigurationWrapper(properties));
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_openSearchConnectionManager",
			_openSearchConnectionManager);

		ReflectionTestUtil.invoke(
			_companyIndexFactory, "activate", new Class<?>[0]);

		return _companyIndexFactory;
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

	private OpenSearchSearchEngine _createOpenSearchSearchEngine(
		IndexConfigurationDynamicUpdatesExecutor
			indexConfigurationDynamicUpdatesExecutor,
		IndexNameBuilder indexNameBuilder) {

		TestOpenSearchConnectionManager testOpenSearchConnectionManager =
			(TestOpenSearchConnectionManager)_openSearchConnectionManager;

		SearchEngineAdapter searchEngineAdapter = _createSearchEngineAdapter();

		OpenSearchSearchEngine openSearchSearchEngine =
			new OpenSearchSearchEngine();

		ReflectionTestUtil.setFieldValue(
			openSearchSearchEngine, "_indexConfigurationDynamicUpdatesExecutor",
			indexConfigurationDynamicUpdatesExecutor);
		ReflectionTestUtil.setFieldValue(
			openSearchSearchEngine, "_indexFactory",
			_createCompanyIndexFactory(
				indexNameBuilder,
				testOpenSearchConnectionManager.
					getOpenSearchConfigurationProperties(),
				searchEngineAdapter));
		ReflectionTestUtil.setFieldValue(
			openSearchSearchEngine, "_indexNameBuilder",
			(IndexNameBuilder)String::valueOf);
		ReflectionTestUtil.setFieldValue(
			openSearchSearchEngine, "_openSearchConnectionManager",
			_openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			openSearchSearchEngine, "_searchEngineAdapter",
			searchEngineAdapter);

		return openSearchSearchEngine;
	}

	private SearchEngineAdapter _createSearchEngineAdapter() {
		_openSearchEngineAdapterFixture = new OpenSearchEngineAdapterFixture() {
			{
				setOpenSearchConnectionManager(_openSearchConnectionManager);
			}
		};

		_openSearchEngineAdapterFixture.setUp();

		return _openSearchEngineAdapterFixture.getSearchEngineAdapter();
	}

	private CompanyIndexFactory _companyIndexFactory;
	private MockedStatic<FrameworkUtil> _frameworkUtilMockedStatic;
	private IndexHelper _indexHelper;
	private IndexNameBuilder _indexNameBuilder;
	private final OpenSearchConnectionManager _openSearchConnectionManager;
	private OpenSearchEngineAdapterFixture _openSearchEngineAdapterFixture;
	private OpenSearchSearchEngine _openSearchSearchEngine;

}