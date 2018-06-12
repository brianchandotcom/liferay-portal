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

package com.liferay.portal.message.boards.compat.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.test.TestIndexerRegistry;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class MBIndexerGetFullQueryTest {

	@Before
	public void setUp() {
		setUpIndexerRegistry();

		_indexer = new TestIndexer();
	}

	@Test
	public void testGetFullQueryWithAttachmentsAndDiscussions()
		throws Exception {

		_searchContext.setIncludeAttachments(true);
		_searchContext.setIncludeDiscussions(true);

		_indexer.getFullQuery(_searchContext);

		assertEntryClassNames(
			_CLASS_NAME, DLFileEntry.class.getName(),
			MBMessage.class.getName());

		Assert.assertEquals(
			Boolean.TRUE, _searchContext.getAttribute("discussion"));
		Assert.assertArrayEquals(
			new String[] {_CLASS_NAME},
			(String[])_searchContext.getAttribute("relatedEntryClassNames"));
	}

	@Test
	public void testGetFullQueryWithDiscussions() throws Exception {
		_searchContext.setIncludeDiscussions(true);

		_indexer.getFullQuery(_searchContext);

		assertEntryClassNames(_CLASS_NAME, MBMessage.class.getName());

		Assert.assertEquals(
			Boolean.TRUE, _searchContext.getAttribute("discussion"));
		Assert.assertArrayEquals(
			new String[] {_CLASS_NAME},
			(String[])_searchContext.getAttribute("relatedEntryClassNames"));
	}

	protected void assertEntryClassNames(String... expectedEntryClassNames) {
		Arrays.sort(expectedEntryClassNames);

		String[] actualEntryClassNames = _searchContext.getEntryClassNames();

		Arrays.sort(actualEntryClassNames);

		Assert.assertArrayEquals(
			expectedEntryClassNames, actualEntryClassNames);
	}

	protected void setUpIndexerRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(
			IndexerRegistry.class, new TestIndexerRegistry());
		registry.registerService(
			RelatedEntryIndexerRegistry.class,
			new TestRelatedEntryIndexerRegistry());
	}

	private static final String _CLASS_NAME = RandomTestUtil.randomString();

	private Indexer<Object> _indexer;
	private final SearchContext _searchContext = new SearchContext();

	private static class TestIndexer extends BaseIndexer<Object> {

		@Override
		public String getClassName() {
			return _CLASS_NAME;
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
				PortletRequest portletRequest, PortletResponse portletResponse)
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

	}

	private class TestRelatedEntryIndexerRegistry
		implements RelatedEntryIndexerRegistry {

		public TestRelatedEntryIndexerRegistry() {
			Registry registry = RegistryUtil.getRegistry();

			_serviceTracker = registry.trackServices(
				RelatedEntryIndexer.class,
				new RelatedEntryIndexerServiceTrackerCustomizer());

			_serviceTracker.open();
		}

		public void destroy() {
			if (_serviceTracker != null) {
				_serviceTracker.close();
			}

			_serviceTracker = null;
		}

		@Override
		public List<RelatedEntryIndexer> getRelatedEntryIndexers() {
			List<RelatedEntryIndexer> relatedEntryIndexers = new ArrayList<>();

			for (List<RelatedEntryIndexer> currentRelatedEntryIndexers :
					_relatedEntryIndexers.values()) {

				relatedEntryIndexers.addAll(currentRelatedEntryIndexers);
			}

			return relatedEntryIndexers;
		}

		@Override
		public List<RelatedEntryIndexer> getRelatedEntryIndexers(Class clazz) {
			return getRelatedEntryIndexers(clazz.getName());
		}

		@Override
		public List<RelatedEntryIndexer> getRelatedEntryIndexers(
			String className) {

			List<RelatedEntryIndexer> relatedEntryIndexers =
				_relatedEntryIndexers.get(className);

			if (relatedEntryIndexers == null) {
				relatedEntryIndexers = Collections.emptyList();
			}

			return relatedEntryIndexers;
		}

		private final Map<String, List<RelatedEntryIndexer>>
			_relatedEntryIndexers = new ConcurrentHashMap<>();
		private ServiceTracker<RelatedEntryIndexer, RelatedEntryIndexer>
			_serviceTracker;

		private class RelatedEntryIndexerServiceTrackerCustomizer
			implements ServiceTrackerCustomizer
				<RelatedEntryIndexer, RelatedEntryIndexer> {

			@Override
			public RelatedEntryIndexer addingService(
				ServiceReference<RelatedEntryIndexer> serviceReference) {

				Registry registry = RegistryUtil.getRegistry();

				RelatedEntryIndexer relatedEntryIndexer = registry.getService(
					serviceReference);

				String relatedEntryIndexerClassName =
					(String)serviceReference.getProperty(
						"related.entry.indexer.class.name");

				if (Validator.isNull(relatedEntryIndexerClassName)) {
					throw new IllegalStateException(
						"Service must contain a " +
							"related.entry.indexer.class.name");
				}

				List<RelatedEntryIndexer> relatedEntryIndexers =
					_relatedEntryIndexers.get(relatedEntryIndexerClassName);

				if (relatedEntryIndexers == null) {
					relatedEntryIndexers = new ArrayList<>();

					_relatedEntryIndexers.put(
						relatedEntryIndexerClassName, relatedEntryIndexers);
				}

				relatedEntryIndexers.add(relatedEntryIndexer);

				return relatedEntryIndexer;
			}

			@Override
			public void modifiedService(
				ServiceReference<RelatedEntryIndexer> serviceReference,
				RelatedEntryIndexer relatedEntryIndexer) {
			}

			@Override
			public void removedService(
				ServiceReference<RelatedEntryIndexer> serviceReference,
				RelatedEntryIndexer relatedEntryIndexer) {

				Registry registry = RegistryUtil.getRegistry();

				registry.ungetService(serviceReference);

				String relatedEntryIndexerClassName =
					(String)serviceReference.getProperty(
						"related.entry.indexer.class.name");

				List<RelatedEntryIndexer> relatedEntryIndexers =
					_relatedEntryIndexers.get(relatedEntryIndexerClassName);

				relatedEntryIndexers.remove(relatedEntryIndexer);
			}

		}

	}

}