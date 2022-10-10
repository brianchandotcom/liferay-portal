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
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

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
		DestinationFactory destinationFactory = Mockito.mock(
			DestinationFactory.class);

		Mockito.when(
			destinationFactory.createDestination(
				Mockito.any(DestinationConfiguration.class))
		).thenReturn(
			Mockito.mock(Destination.class)
		);

		ElasticsearchEngineConfigurator elasticsearchEngineConfigurator =
			_createElasticsearchEngineConfigurator(destinationFactory);

		elasticsearchEngineConfigurator.activate(
			SystemBundleUtil.getBundleContext());

		elasticsearchEngineConfigurator.configure(
			Mockito.mock(SearchEngine.class));

		elasticsearchEngineConfigurator.unconfigure();

		Mockito.verify(
			destinationFactory, Mockito.times(2)
		).createDestination(
			Mockito.any(DestinationConfiguration.class)
		);
	}

	private ElasticsearchEngineConfigurator
		_createElasticsearchEngineConfigurator(
			DestinationFactory destinationFactory) {

		return new ElasticsearchEngineConfigurator() {
			{
				ReflectionTestUtil.setFieldValue(
					this, "_companyLocalService",
					Mockito.mock(CompanyLocalService.class));
				ReflectionTestUtil.setFieldValue(
					this, "_destinationFactory", destinationFactory);
				ReflectionTestUtil.setFieldValue(
					this, "_messageBus", Mockito.mock(MessageBus.class));
			}
		};
	}

}