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

package com.liferay.portal.search.test.util;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ClassNameWrapper;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.RelatedSearchResult;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.result.SearchResultTranslator;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalServiceWrapper;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;
import com.liferay.registry.collections.ServiceTrackerMapFactoryUtil;
import com.liferay.registry.collections.ServiceTrackerMapListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;

/**
 * @author André de Oliveira
 */
public abstract class BaseSearchResultUtilTestCase {

	@Before
	public void setUp() throws Exception {
		setUpRegistryUtil();
		setUpClassNameLocalService();
		setUpFastDateFormatFactoryUtil();
		setUpPropsUtil();
		setUpSearchResultTranslator();
		setUpServiceTrackerMapFactory();
	}

	protected void assertEmptyCommentRelatedSearchResults(
		SearchResult searchResult) {

		List<RelatedSearchResult<Comment>> commentRelatedSearchResults =
			searchResult.getCommentRelatedSearchResults();

		Assert.assertTrue(
			commentRelatedSearchResults.toString(),
			commentRelatedSearchResults.isEmpty());
	}

	protected void assertEmptyFileEntryRelatedSearchResults(
		SearchResult searchResult) {

		List<RelatedSearchResult<FileEntry>> fileEntryRelatedSearchResults =
			searchResult.getFileEntryRelatedSearchResults();

		Assert.assertTrue(
			fileEntryRelatedSearchResults.toString(),
			fileEntryRelatedSearchResults.isEmpty());
	}

	protected void assertEmptyVersions(SearchResult searchResult) {
		List<String> versions = searchResult.getVersions();

		Assert.assertTrue(versions.toString(), versions.isEmpty());
	}

	protected SearchResult assertOneSearchResult(Document document) {
		List<SearchResult> searchResults = SearchTestUtil.getSearchResults(
			searchResultTranslator, document);

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		return searchResults.get(0);
	}

	protected abstract SearchResultTranslator createSearchResultTranslator();

	protected void setUpClassNameLocalService() {
		classNameLocalService = new ClassNameLocalServiceWrapper(null) {

			@Override
			public ClassName getClassName(long classNameId) {
				if (classNameId ==
						SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME_ID) {

					return new ClassNameWrapper(null) {

						@Override
						public String getClassName() {
							return SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME;
						}

					};
				}

				return null;
			}

		};
	}

	protected void setUpFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			ProxyFactory.newDummyInstance(FastDateFormatFactory.class));
	}

	protected void setUpPropsUtil() {
		PropsTestUtil.setProps(Collections.emptyMap());
	}

	protected void setUpRegistryUtil() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	protected void setUpSearchResultTranslator() {
		searchResultTranslator = createSearchResultTranslator();
	}

	protected void setUpServiceTrackerMapFactory() {
		ServiceTrackerMapFactoryUtil.setServiceTrackerMapFactory(
			new MockServiceTrackerMapFactory());
	}

	protected ClassNameLocalService classNameLocalService;
	protected SearchResultTranslator searchResultTranslator;

	private class MockServiceTrackerMap<K, S>
		implements ServiceTrackerMap<K, S> {

		@Override
		public void close() {
		}

		@Override
		public boolean containsKey(K key) {
			return false;
		}

		@Override
		public S getService(K key) {
			return null;
		}

		@Override
		public Set<K> keySet() {
			return null;
		}

	}

	private class MockServiceTrackerMapFactory
		implements ServiceTrackerMapFactory {

		@Override
		public <S> ServiceTrackerMap<String, List<S>> openMultiValueMap(
			Class<S> clazz, String propertyKey) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			ServiceTrackerMapListener<K, ? super S, List<S>>
				serviceTrackerMapListener) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <K, SR, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <K, SR, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
			Comparator<ServiceReference<SR>> comparator) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <SR, S> ServiceTrackerMap<String, List<S>> openMultiValueMap(
			Class<SR> clazz, String propertyKey,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <S> ServiceTrackerMap<String, S> openSingleValueMap(
			Class<S> clazz, String propertyKey) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <K, S> ServiceTrackerMap<K, S> openSingleValueMap(
			Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <K, S> ServiceTrackerMap<K, S> openSingleValueMap(
			Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <K, SR, S> ServiceTrackerMap<K, S> openSingleValueMap(
			Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <K, SR, S> ServiceTrackerMap<K, S> openSingleValueMap(
			Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
			Comparator<ServiceReference<SR>> comparator) {

			return new MockServiceTrackerMap<>();
		}

		@Override
		public <SR, S> ServiceTrackerMap<String, S> openSingleValueMap(
			Class<SR> clazz, String propertyKey,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

			return new MockServiceTrackerMap<>();
		}

	}

}