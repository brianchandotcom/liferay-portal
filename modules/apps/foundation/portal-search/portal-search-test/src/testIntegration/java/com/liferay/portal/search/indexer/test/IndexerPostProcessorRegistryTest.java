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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

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

		assertEquals(1, mbMessageIndexerPostProcessors.length);

		IndexerPostProcessor mbMessageIndexerPostProcessor =
			mbMessageIndexerPostProcessors[0];

		assertNotNull(mbMessageIndexerPostProcessor);

		Indexer<MBThread> mbThreadIndexer = IndexerRegistryUtil.getIndexer(
			MBThread.class.getName());

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
	public void testNullIndexerIndexerPostProcessor() throws Exception {
		Indexer indexer = IndexerRegistryUtil.getIndexer(
			"com.liferay.portal.test.SampleModel");

		assertNull(indexer);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		Indexer<?> sampleIndexer = new BaseIndexer() {

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

		ServiceRegistration<Indexer> serviceRegistration = null;

		try {
			serviceRegistration = bundleContext.registerService(
				Indexer.class, sampleIndexer, new HashMapDictionary<>());

			indexer = IndexerRegistryUtil.getIndexer(
				"com.liferay.portal.test.SampleModel");

			assertNotNull(indexer);

			IndexerPostProcessor[] indexerPostProcessors =
				indexer.getIndexerPostProcessors();

			assertEquals(1, indexerPostProcessors.length);

			assertEquals(
				TestSampleModelIndexerPostProcessor.class.getName(),
				indexerPostProcessors[0].getClass().getName());
		}
		finally {
			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
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

}