/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.facet;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.solr8.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.facet.BaseAggregationFilteringTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.apache.solr.client.solrj.SolrQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author André de Oliveira
 */
public class AggregationFilteringTest extends BaseAggregationFilteringTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_serviceRegistration = _bundleContext.registerService(
			(Class<FacetProcessor<SolrQuery>>)(Class<?>)FacetProcessor.class,
			new RangeFacetProcessor() {
				{
					jsonFactory = _jsonFactory;
				}
			},
			MapUtil.singletonDictionary(
				"class.name",
				"com.liferay.portal.search.internal.facet.ModifiedFacetImpl"));
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();

			_serviceRegistration = null;
		}
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture();
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private ServiceRegistration<FacetProcessor<SolrQuery>> _serviceRegistration;

}