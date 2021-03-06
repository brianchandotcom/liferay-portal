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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.search.elasticsearch7.configuration.RESTClientLoggerLevel;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.configuration.OperationModeResolver;
import com.liferay.portal.search.elasticsearch7.internal.util.SearchLogHelper;
import com.liferay.portal.search.elasticsearch7.internal.util.SearchLogHelperImpl;
import com.liferay.portal.search.elasticsearch7.internal.util.SearchLogHelperUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class ElasticsearchConnectionLogTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_elasticsearchConfigurationWrapper = Mockito.mock(
			ElasticsearchConfigurationWrapper.class);

		Mockito.when(
			_elasticsearchConfigurationWrapper.restClientLoggerLevel()
		).thenReturn(
			RESTClientLoggerLevel.DEBUG
		);

		_searchLogHelper = Mockito.spy(new SearchLogHelperImpl());

		SearchLogHelperUtil.setSearchLogHelper(_searchLogHelper);
	}

	@Test
	public void testLogLevel() throws Exception {
		ElasticsearchConnectionManager elasticsearchConnectionManager =
			new ElasticsearchConnectionManager() {
				{
					elasticsearchConfigurationWrapper =
						_elasticsearchConfigurationWrapper;
					operationModeResolver = Mockito.mock(
						OperationModeResolver.class);
				}
			};

		elasticsearchConnectionManager.applyConfigurations();

		Mockito.verify(
			_searchLogHelper
		).setRESTClientLoggerLevel(
			RESTClientLoggerLevel.DEBUG
		);
	}

	private ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;
	private SearchLogHelper _searchLogHelper;

}