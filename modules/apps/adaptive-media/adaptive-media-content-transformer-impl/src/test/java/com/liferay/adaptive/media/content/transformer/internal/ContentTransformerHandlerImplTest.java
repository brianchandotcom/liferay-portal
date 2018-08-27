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

package com.liferay.adaptive.media.content.transformer.internal;

import com.liferay.adaptive.media.content.transformer.ContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformerContentType;
import com.liferay.adaptive.media.exception.AMException;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleException;

/**
 * @author Alejandro Tardín
 */
public class ContentTransformerHandlerImplTest {

	@Before
	public void setUp() throws BundleException {
		_contentTransformerHandlerImpl.setServiceTrackerMap(
			_mockServiceTrackerMap);
	}

	@After
	public final void tearDown() throws Exception {
		_contentTransformerHandlerImpl.deactivate();
	}

	@Test
	public void testIgnoresTheContentTransformersForDifferentContentTypes()
		throws Exception {

		ContentTransformerContentType<String> contentTransformerContentTypeA =
			new TestContentTransformerContentType<>();

		String transformedContentA = RandomTestUtil.randomString();

		_registerContentTransformer(
			contentTransformerContentTypeA, _ORIGINAL_CONTENT,
			transformedContentA);

		ContentTransformerContentType<String> contentTransformerContentTypeB =
			new TestContentTransformerContentType<>();

		String transformedContentB = RandomTestUtil.randomString();

		_registerContentTransformer(
			contentTransformerContentTypeB, _ORIGINAL_CONTENT,
			transformedContentB);

		Assert.assertEquals(
			transformedContentA,
			_contentTransformerHandlerImpl.transform(
				contentTransformerContentTypeA, _ORIGINAL_CONTENT));
		Assert.assertEquals(
			transformedContentB,
			_contentTransformerHandlerImpl.transform(
				contentTransformerContentTypeB, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheContentTransformedByAChainOfContentTransformers()
		throws Exception {

		String intermediateTransformedContent = RandomTestUtil.randomString();

		_registerContentTransformer(
			_contentTransformerContentType, _ORIGINAL_CONTENT,
			intermediateTransformedContent);

		String finalTransformedContent = RandomTestUtil.randomString();

		_registerContentTransformer(
			_contentTransformerContentType, intermediateTransformedContent,
			finalTransformedContent);

		Assert.assertEquals(
			finalTransformedContent,
			_contentTransformerHandlerImpl.transform(
				_contentTransformerContentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheContentTransformedByAContentTransformerForAContentType()
		throws Exception {

		String transformedContent = RandomTestUtil.randomString();

		_registerContentTransformer(
			_contentTransformerContentType, _ORIGINAL_CONTENT,
			transformedContent);

		Assert.assertEquals(
			transformedContent,
			_contentTransformerHandlerImpl.transform(
				_contentTransformerContentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheSameContentIfAContentTransformerThrowsAnException()
		throws Exception {

		_registerInvalidContentTransformer(
			_contentTransformerContentType, _ORIGINAL_CONTENT);

		Assert.assertSame(
			_ORIGINAL_CONTENT,
			_contentTransformerHandlerImpl.transform(
				_contentTransformerContentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheSameContentIfThereAreNoContentTransformers() {
		Assert.assertSame(
			_ORIGINAL_CONTENT,
			_contentTransformerHandlerImpl.transform(
				_contentTransformerContentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testRunsTheOtherContentTransformersEvenIfOneOfThemFails()
		throws Exception {

		_registerInvalidContentTransformer(
			_contentTransformerContentType, _ORIGINAL_CONTENT);

		String transformedContent = RandomTestUtil.randomString();

		_registerContentTransformer(
			_contentTransformerContentType, _ORIGINAL_CONTENT,
			transformedContent);

		Assert.assertEquals(
			transformedContent,
			_contentTransformerHandlerImpl.transform(
				_contentTransformerContentType, _ORIGINAL_CONTENT));
	}

	private ContentTransformer<String> _registerContentTransformer(
			ContentTransformerContentType<String> contentTransformerContentType,
			String originalContent, String transformedContent)
		throws Exception {

		ContentTransformer<String> contentTransformer = Mockito.mock(
			ContentTransformer.class);

		Mockito.when(
			contentTransformer.getContentTransformerContentType()
		).thenReturn(
			contentTransformerContentType
		);

		Mockito.when(
			contentTransformer.transform(originalContent)
		).thenReturn(
			transformedContent
		);

		_mockServiceTrackerMap.register(contentTransformer);

		return contentTransformer;
	}

	private void _registerInvalidContentTransformer(
			ContentTransformerContentType<String> contentTransformerContentType,
			String originalContent)
		throws Exception {

		ContentTransformer<String> invalidContentTransformer =
			_registerContentTransformer(
				contentTransformerContentType, originalContent, "");

		Mockito.when(
			invalidContentTransformer.transform(originalContent)
		).thenThrow(
			new AMException("This is expected")
		);
	}

	private static final String _ORIGINAL_CONTENT =
		RandomTestUtil.randomString();

	private final ContentTransformerContentType<String>
		_contentTransformerContentType =
			new TestContentTransformerContentType<>();
	private final ContentTransformerHandlerImpl _contentTransformerHandlerImpl =
		new ContentTransformerHandlerImpl();
	private final MockServiceTrackerMap _mockServiceTrackerMap =
		new MockServiceTrackerMap();

	private static class TestContentTransformerContentType<T>
		implements ContentTransformerContentType<T> {

		@Override
		public String getKey() {
			return "test";
		}

	}

	private final class MockServiceTrackerMap
		implements ServiceTrackerMap
			<ContentTransformerContentType, List<ContentTransformer>> {

		@Override
		public void close() {
			_contentTransformers.clear();
		}

		@Override
		public boolean containsKey(
			ContentTransformerContentType contentTransformerContentType) {

			return _contentTransformers.containsKey(
				contentTransformerContentType);
		}

		@Override
		public List<ContentTransformer> getService(
			ContentTransformerContentType contentTransformerContentType) {

			return _contentTransformers.get(contentTransformerContentType);
		}

		@Override
		public Set<ContentTransformerContentType> keySet() {
			return _contentTransformers.keySet();
		}

		public void register(ContentTransformer contentTransformer) {
			List<ContentTransformer> contentTransformers =
				_contentTransformers.computeIfAbsent(
					contentTransformer.getContentTransformerContentType(),
					key -> new ArrayList<>());

			contentTransformers.add(contentTransformer);
		}

		@Override
		public Collection<List<ContentTransformer>> values() {
			return _contentTransformers.values();
		}

		private final Map
			<ContentTransformerContentType, List<ContentTransformer>>
				_contentTransformers = new HashMap<>();

	}

}