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

package com.liferay.portal.search.indexer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.search.test.util.TestIndexer;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Gregory Amerson
 * @author István Sajtos
 * @author Tibor Lipusz
 */
@RunWith(Arquillian.class)
public class IndexerPostProcessorRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testMultipleIndexerPostProcessors() throws Exception {
		Indexer<MBMessage> mbMessageIndexer = IndexerRegistryUtil.getIndexer(
			MBMessage.class.getName());

		IndexerPostProcessor[] mbMessageIndexerPostProcessors =
			mbMessageIndexer.getIndexerPostProcessors();

		Assert.assertTrue(
			Arrays.toString(mbMessageIndexerPostProcessors),
			mbMessageIndexerPostProcessors.length > 0);

		IndexerPostProcessor testMBMessageIndexerPostProcessor = null;

		for (IndexerPostProcessor mbMessageIndexerPostProcessor :
				mbMessageIndexerPostProcessors) {

			if (mbMessageIndexerPostProcessor instanceof
					TestMultipleIndexerPostProcessor) {

				testMBMessageIndexerPostProcessor =
					mbMessageIndexerPostProcessor;

				break;
			}
		}

		Assert.assertNotNull(testMBMessageIndexerPostProcessor);

		Indexer<MBThread> mbThreadIndexer = IndexerRegistryUtil.getIndexer(
			MBThread.class.getName());

		IndexerPostProcessor[] mbThreadIndexerPostProcessors =
			mbThreadIndexer.getIndexerPostProcessors();

		Assert.assertTrue(
			Arrays.toString(mbThreadIndexerPostProcessors),
			mbThreadIndexerPostProcessors.length > 0);

		IndexerPostProcessor testMBThreadIndexerPostProcessor = null;

		for (IndexerPostProcessor mbThreadIndexerPostProcessor :
				mbThreadIndexerPostProcessors) {

			if (mbThreadIndexerPostProcessor instanceof
					TestMultipleIndexerPostProcessor) {

				testMBThreadIndexerPostProcessor = mbThreadIndexerPostProcessor;

				break;
			}
		}

		Assert.assertNotNull(testMBThreadIndexerPostProcessor);
		Assert.assertEquals(
			testMBMessageIndexerPostProcessor,
			testMBThreadIndexerPostProcessor);
	}

	@Test
	public void testMultipleModelIndexerPostProcessors() throws Exception {
		Indexer<User> userIndexer = IndexerRegistryUtil.getIndexer(
			User.class.getName());

		IndexerPostProcessor[] userIndexerPostProcessors =
			userIndexer.getIndexerPostProcessors();

		Assert.assertTrue(
			Arrays.toString(userIndexerPostProcessors),
			userIndexerPostProcessors.length > 0);

		IndexerPostProcessor testUserIndexerPostProcessor = null;

		for (IndexerPostProcessor userIndexerPostProcessor :
				userIndexerPostProcessors) {

			if (userIndexerPostProcessor instanceof
					TestMultipleEntityIndexerPostProcessor) {

				testUserIndexerPostProcessor = userIndexerPostProcessor;

				break;
			}
		}

		Assert.assertNotNull(testUserIndexerPostProcessor);

		Indexer<UserGroup> userGroupIndexer = IndexerRegistryUtil.getIndexer(
			UserGroup.class.getName());

		IndexerPostProcessor[] userGroupIndexerPostProcessors =
			userGroupIndexer.getIndexerPostProcessors();

		Assert.assertTrue(
			Arrays.toString(userGroupIndexerPostProcessors),
			userGroupIndexerPostProcessors.length > 0);

		IndexerPostProcessor testUserGroupIndexerPostProcessor = null;

		for (IndexerPostProcessor userGroupIndexerPostProcessor :
				userGroupIndexerPostProcessors) {

			if (userGroupIndexerPostProcessor instanceof
					TestMultipleEntityIndexerPostProcessor) {

				testUserGroupIndexerPostProcessor =
					userGroupIndexerPostProcessor;

				break;
			}
		}

		Assert.assertNotNull(testUserGroupIndexerPostProcessor);
		Assert.assertEquals(
			testUserIndexerPostProcessor, testUserGroupIndexerPostProcessor);
	}

	@Test
	public void testNullIndexerIndexerPostProcessor() throws Exception {
		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
			"com.liferay.portal.test.SampleModel");

		Assert.assertNull(indexer);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		Indexer<?> sampleIndexer = new TestIndexer(
			"com.liferay.portal.test.SampleModel");

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				Indexer.class, sampleIndexer, new HashMapDictionary<>());

		try {
			indexer = IndexerRegistryUtil.getIndexer(
				"com.liferay.portal.test.SampleModel");

			Assert.assertNotNull(indexer);

			List<String> expectedClassNames = Arrays.asList(
				TestSampleModelIndexerPostProcessor.class.getName());

			List<String> actualClassNames = Stream.of(
				indexer.getIndexerPostProcessors()
			).map(
				IndexerPostProcessor::getClass
			).map(
				Class::getName
			).collect(
				Collectors.toList()
			);

			Assert.assertEquals(
				expectedClassNames.toString(), actualClassNames.toString());
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testQueuedIndexerPostProcessorWithIndexerClassName()
		throws Exception {

		Indexer<BlogsEntry> blogsEntryIndexer = IndexerRegistryUtil.getIndexer(
			BlogsEntry.class.getName());

		testQueuedIndexerPostProcessor(
			blogsEntryIndexer, blogsEntryIndexer.getClassName());
	}

	@Test
	public void testQueuedIndexerPostProcessorWithModelClassName()
		throws Exception {

		Indexer<BlogsEntry> blogsEntryIndexer = IndexerRegistryUtil.getIndexer(
			BlogsEntry.class.getName());

		testQueuedIndexerPostProcessor(
			blogsEntryIndexer, BlogsEntry.class.getName());
	}

	@Test
	public void testSingleIndexerPostProcessor() throws Exception {
		Indexer<Organization> organizationIndexer =
			IndexerRegistryUtil.getIndexer(Organization.class.getName());

		IndexerPostProcessor[] organizationIndexerPostProcessors =
			organizationIndexer.getIndexerPostProcessors();

		Assert.assertEquals(
			Arrays.toString(organizationIndexerPostProcessors), 1,
			organizationIndexerPostProcessors.length);

		IndexerPostProcessor organizationIndexerPostProcessor =
			organizationIndexerPostProcessors[0];

		Assert.assertNotNull(organizationIndexerPostProcessor);
	}

	@Test
	public void testSingleModelIndexerPostProcessor() throws Exception {
		Indexer<Contact> contactIndexer = IndexerRegistryUtil.getIndexer(
			Contact.class.getName());

		IndexerPostProcessor[] contactIndexerPostProcessors =
			contactIndexer.getIndexerPostProcessors();

		Assert.assertEquals(
			Arrays.toString(contactIndexerPostProcessors), 1,
			contactIndexerPostProcessors.length);

		IndexerPostProcessor contactIndexerPostProcessor =
			contactIndexerPostProcessors[0];

		Assert.assertNotNull(contactIndexerPostProcessor);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void testQueuedIndexerPostProcessor(
			Indexer<?> indexer, String indexerClassName)
		throws Exception {

		IndexerPostProcessor[] indexerPostProcessors =
			indexer.getIndexerPostProcessors();

		Assert.assertEquals(
			Arrays.toString(indexerPostProcessors), 0,
			indexerPostProcessors.length);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		IndexerPostProcessor indexerPostProcessor =
			new BaseIndexerPostProcessor();

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				IndexerPostProcessor.class, indexerPostProcessor,
				HashMapDictionaryBuilder.<String, Object>put(
					"indexer.class.name", indexerClassName
				).build());

		try {
			indexerPostProcessors = indexer.getIndexerPostProcessors();

			Assert.assertEquals(
				Arrays.toString(indexerPostProcessors), 1,
				indexerPostProcessors.length);
			Assert.assertEquals(indexerPostProcessor, indexerPostProcessors[0]);
		}
		finally {
			serviceRegistration.unregister();
		}
	}

}