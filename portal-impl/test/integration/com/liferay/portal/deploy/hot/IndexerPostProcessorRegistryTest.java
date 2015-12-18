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

package com.liferay.portal.deploy.hot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.util.MBMessageIndexer;
import com.liferay.registry.ServiceTracker;

import java.lang.reflect.Field;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class IndexerPostProcessorRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.indexerpostprocessorregistry"));

	@BeforeClass
	public static void setUpClass() throws Exception {
		_indexerPostProcessorRegistry = new IndexerPostProcessorRegistry();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_indexerPostProcessorRegistry.close();
	}

	@Test
	public void testIndexerPostProcessorsSize() throws Exception {
		Field serviceTrackerField = ReflectionUtil.getDeclaredField(
			IndexerPostProcessorRegistry.class, "_serviceTracker");

		ServiceTracker<?, ?> serviceTracker =
			(ServiceTracker<?, ?>)serviceTrackerField.get(
				_indexerPostProcessorRegistry);

		Object[] services = serviceTracker.getServices();

		assertEquals(4, services.length);
	}

	@Test
	public void testMultipleIndexerPostProcessors() throws Exception {
		Indexer<MBMessage> mbMessageIndexer = IndexerRegistryUtil.getIndexer(
			MBMessageIndexer.class.getName());

		IndexerPostProcessor[] mbMessageIndexerPostProcessors =
			mbMessageIndexer.getIndexerPostProcessors();

		assertEquals(1, mbMessageIndexerPostProcessors.length);

		IndexerPostProcessor mbMessageIndexerPostProcessor =
			mbMessageIndexerPostProcessors[0];

		assertNotNull(mbMessageIndexerPostProcessor);

		Indexer<MBThread> mbThreadIndexer = IndexerRegistryUtil.getIndexer(
			"com.liferay.portlet.messageboards.util.MBThreadIndexer");

		IndexerPostProcessor[] mbThreadIndexerPostProcessors =
			mbThreadIndexer.getIndexerPostProcessors();

		assertEquals(1, mbThreadIndexerPostProcessors.length);

		IndexerPostProcessor mbThreadIndexerPostProcessor =
			mbThreadIndexerPostProcessors[0];

		assertNotNull(mbThreadIndexerPostProcessor);
		assertEquals(
			mbMessageIndexerPostProcessor, mbThreadIndexerPostProcessor);
	}

	@Test
	public void testMultipleModelIndexerPostProcessors() throws Exception {
		Indexer<User> userIndexer = IndexerRegistryUtil.getIndexer(
			User.class.getName());

		IndexerPostProcessor[] userIndexerPostProcessors =
			userIndexer.getIndexerPostProcessors();

		assertEquals(1, userIndexerPostProcessors.length);

		IndexerPostProcessor userIndexerPostProcessor =
			userIndexerPostProcessors[0];

		assertNotNull(userIndexerPostProcessor);

		Indexer<UserGroup> userGroupIndexer = IndexerRegistryUtil.getIndexer(
			UserGroup.class.getName());

		IndexerPostProcessor[] userGroupIndexerPostProcessors =
			userGroupIndexer.getIndexerPostProcessors();

		assertEquals(1, userGroupIndexerPostProcessors.length);

		IndexerPostProcessor userGroupIndexerPostProcessor =
			userGroupIndexerPostProcessors[0];

		assertNotNull(userGroupIndexerPostProcessor);
		assertEquals(userIndexerPostProcessor, userGroupIndexerPostProcessor);
	}

	@Test
	public void testSingleIndexerPostProcessor() throws Exception {
		Indexer<Organization> organizationIndexer =
			IndexerRegistryUtil.getIndexer(Organization.class.getName());

		IndexerPostProcessor[] organizationIndexerPostProcessors =
			organizationIndexer.getIndexerPostProcessors();

		assertEquals(1, organizationIndexerPostProcessors.length);

		IndexerPostProcessor organizationIndexerPostProcessor =
			organizationIndexerPostProcessors[0];

		assertNotNull(organizationIndexerPostProcessor);
	}

	@Test
	public void testSingleModelIndexerPostProcessor() throws Exception {
		Indexer<Contact> contactIndexer = IndexerRegistryUtil.getIndexer(
			Contact.class.getName());

		IndexerPostProcessor[] contactIndexerPostProcessors =
			contactIndexer.getIndexerPostProcessors();

		assertEquals(1, contactIndexerPostProcessors.length);

		IndexerPostProcessor contactIndexerPostProcessor =
			contactIndexerPostProcessors[0];

		assertNotNull(contactIndexerPostProcessor);
	}

	private static IndexerPostProcessorRegistry _indexerPostProcessorRegistry;

}