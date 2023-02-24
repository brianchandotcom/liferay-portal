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
			new ModifiedFacetProcessor() {
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