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
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

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

		Assert.assertEquals(
			Arrays.toString(mbMessageIndexerPostProcessors), 1,
			mbMessageIndexerPostProcessors.length);

		IndexerPostProcessor mbMessageIndexerPostProcessor =
			mbMessageIndexerPostProcessors[0];

		Assert.assertNotNull(mbMessageIndexerPostProcessor);

		Indexer<MBThread> mbThreadIndexer = IndexerRegistryUtil.getIndexer(
			MBThread.class.getName());

		IndexerPostProcessor[] mbThreadIndexerPostProcessors =
			mbThreadIndexer.getIndexerPostProcessors();

		Assert.assertEquals(
			Arrays.toString(mbThreadIndexerPostProcessors), 1,
			mbThreadIndexerPostProcessors.length);

		IndexerPostProcessor mbThreadIndexerPostProcessor =
			mbThreadIndexerPostProcessors[0];

		Assert.assertNotNull(mbThreadIndexerPostProcessor);
		Assert.assertEquals(
			mbMessageIndexerPostProcessor, mbThreadIndexerPostProcessor);
	}

	@Test
	public void testMultipleModelIndexerPostProcessors() throws Exception {
		Indexer<User> userIndexer = IndexerRegistryUtil.getIndexer(
			User.class.getName());

		IndexerPostProcessor[] userIndexerPostProcessors =
			userIndexer.getIndexerPostProcessors();

		Assert.assertEquals(
			Arrays.toString(userIndexerPostProcessors), 1,
			userIndexerPostProcessors.length);

		IndexerPostProcessor userIndexerPostProcessor =
			userIndexerPostProcessors[0];

		Assert.assertNotNull(userIndexerPostProcessor);

		Indexer<UserGroup> userGroupIndexer = IndexerRegistryUtil.getIndexer(
			UserGroup.class.getName());

		IndexerPostProcessor[] userGroupIndexerPostProcessors =
			userGroupIndexer.getIndexerPostProcessors();

		Assert.assertEquals(
			Arrays.toString(userGroupIndexerPostProcessors), 1,
			userGroupIndexerPostProcessors.length);

		IndexerPostProcessor userGroupIndexerPostProcessor =
			userGroupIndexerPostProcessors[0];

		Assert.assertNotNull(userGroupIndexerPostProcessor);
		Assert.assertEquals(
			userIndexerPostProcessor, userGroupIndexerPostProcessor);
	}

	@Test
	public void testNullIndexerIndexerPostProcessor() throws Exception {
		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
			"com.liferay.portal.test.SampleModel");

		Assert.assertNull(indexer);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		Indexer<?> sampleIndexer = new BaseIndexer<Object>() {

			@Override
			public String getClassName() {
				return "com.liferay.portal.test.SampleModel";
			}

			@Override
			protected void doDelete(Object object) throws Exception {
			}

			@Override
			protected Document doGetDocument(Object object) throws Exception {
				return null;
			}

			@Override
			protected Summary doGetSummary(
					Document document, Locale locale, String snippet,
					PortletRequest portletRequest,
					PortletResponse portletResponse)
				throws Exception {

				return null;
			}

			@Override
			protected void doReindex(Object object) throws Exception {
			}

			@Override
			protected void doReindex(String className, long classPK)
				throws Exception {
			}

			@Override
			protected void doReindex(String[] ids) throws Exception {
			}

		};

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				Indexer.class, sampleIndexer, new HashMapDictionary<>());

		try {
			indexer = IndexerRegistryUtil.getIndexer(
				"com.liferay.portal.test.SampleModel");

			Assert.assertNotNull(indexer);

			List<String> expectedClassNames = Arrays.asList(
				TestSampleModelIndexerPostProcessor.class.getName());

			Stream<IndexerPostProcessor> indexerPostProcessorsStream =
				Stream.of(indexer.getIndexerPostProcessors());

			List<String> actualClassNames = indexerPostProcessorsStream.map(
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

}