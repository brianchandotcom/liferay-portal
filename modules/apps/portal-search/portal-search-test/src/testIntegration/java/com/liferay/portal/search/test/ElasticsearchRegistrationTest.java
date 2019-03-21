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

package com.liferay.portal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class ElasticsearchRegistrationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetSearchEngineService() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		Collection<ServiceReference<SearchEngine>> searchEngineReferences =
			bundleContext.getServiceReferences(
				SearchEngine.class, "(search.engine.id=SYSTEM_ENGINE)");

		Assert.assertEquals(
			searchEngineReferences.toString(), 1,
			searchEngineReferences.size());

		for (ServiceReference<SearchEngine> searchEngineReference :
				searchEngineReferences) {

			SearchEngine searchEngine = bundleContext.getService(
				searchEngineReference);

			Class<? extends SearchEngine> searchEngineClass =
				searchEngine.getClass();

			String searchEngineClassName = searchEngineClass.getName();

			Assert.assertTrue(
				"The registered search engine is " + searchEngineClassName,
				searchEngineClassName.endsWith("ElasticsearchSearchEngine"));
		}
	}

}