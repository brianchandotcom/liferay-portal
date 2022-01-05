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

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.elasticsearch7.internal.BaseSearchEngineConfigurator.DestinationServiceRegistrarHelper;
import com.liferay.portal.search.elasticsearch7.internal.BaseSearchEngineConfigurator.SearchDestinationHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

/**
 * @author André de Oliveira
 */
public class ElasticsearchEngineConfiguratorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		PropsTestUtil.setProps(
			PropsKeys.INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE, "2");
	}

	@Test
	public void testDestroyMustNotCreateDestinationsAgain() {
		SearchDestinationHelper searchDestinationHelper =
			_createSearchDestinationHelper();

		ElasticsearchEngineConfigurator elasticsearchEngineConfigurator =
			_createElasticsearchEngineConfigurator(searchDestinationHelper);

		elasticsearchEngineConfigurator.activate(
			Mockito.mock(ComponentContext.class));

		elasticsearchEngineConfigurator.destroy();

		Mockito.verify(
			searchDestinationHelper, Mockito.times(1)
		).createSearchReaderDestination(
			Mockito.anyString()
		);

		Mockito.verify(
			searchDestinationHelper, Mockito.times(1)
		).createSearchWriterDestination(
			Mockito.anyString()
		);
	}

	private DestinationServiceRegistrarHelper
		_createDestinationServiceRegistrarHelper() {

		DestinationServiceRegistrarHelper destinationServiceRegistrarHelper =
			Mockito.mock(DestinationServiceRegistrarHelper.class);

		Mockito.doReturn(
			Mockito.mock(ServiceRegistration.class)
		).when(
			destinationServiceRegistrarHelper
		).registerDestination(
			Mockito.any()
		);

		Mockito.doReturn(
			Mockito.mock(Destination.class)
		).when(
			destinationServiceRegistrarHelper
		).getDestination(
			Mockito.any()
		);

		return destinationServiceRegistrarHelper;
	}

	private ElasticsearchEngineConfigurator
		_createElasticsearchEngineConfigurator(
			SearchDestinationHelper searchDestinationHelper) {

		return new ElasticsearchEngineConfigurator() {
			{
				setDestinationServiceRegistrarHelper(
					_createDestinationServiceRegistrarHelper());
				setMessageBus(Mockito.mock(MessageBus.class));
				setSearchDestinationHelper(searchDestinationHelper);
				setSearchEngine(
					Mockito.mock(SearchEngine.class),
					Collections.singletonMap(
						"search.engine.id", "SYSTEM_ENGINE"));
				setSearchEngineHelper(Mockito.mock(SearchEngineHelper.class));
			}
		};
	}

	private SearchDestinationHelper _createSearchDestinationHelper() {
		SearchDestinationHelper searchDestinationHelper = Mockito.mock(
			SearchDestinationHelper.class);

		Mockito.doReturn(
			Mockito.mock(Destination.class)
		).when(
			searchDestinationHelper
		).createSearchReaderDestination(
			Mockito.anyString()
		);

		Mockito.doReturn(
			Mockito.mock(Destination.class)
		).when(
			searchDestinationHelper
		).createSearchWriterDestination(
			Mockito.anyString()
		);

		return searchDestinationHelper;
	}

}