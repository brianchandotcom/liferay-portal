/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.content.transformer.internal;

import com.liferay.adaptive.media.content.transformer.ContentTransformer;
import com.liferay.adaptive.media.exception.AMException;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleException;

/**
 * @author Alejandro Tardín
 */
public class ContentTransformerHandlerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws BundleException {
		_contentTransformerHandlerImpl.setServiceTrackerList(
			_mockServiceTrackerList);
	}

	@After
	public final void tearDown() throws Exception {
		_contentTransformerHandlerImpl.deactivate();
	}

	@Test
	public void testReturnsTheContentTransformedByAChainOfContentTransformers()
		throws Exception {

		String intermediateTransformedContent = RandomTestUtil.randomString();

		_registerContentTransformer(
			_ORIGINAL_CONTENT, intermediateTransformedContent);

		String finalTransformedContent = RandomTestUtil.randomString();

		_registerContentTransformer(
			intermediateTransformedContent, finalTransformedContent);

		Assert.assertEquals(
			finalTransformedContent,
			_contentTransformerHandlerImpl.transform(_ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheContentTransformedByAContentTransformerForAContentType()
		throws Exception {

		String transformedContent = RandomTestUtil.randomString();

		_registerContentTransformer(_ORIGINAL_CONTENT, transformedContent);

		Assert.assertEquals(
			transformedContent,
			_contentTransformerHandlerImpl.transform(_ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheSameContentIfAContentTransformerThrowsAnException()
		throws Exception {

		_registerInvalidContentTransformer(_ORIGINAL_CONTENT);

		Assert.assertSame(
			_ORIGINAL_CONTENT,
			_contentTransformerHandlerImpl.transform(_ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheSameContentIfThereAreNoContentTransformers() {
		Assert.assertSame(
			_ORIGINAL_CONTENT,
			_contentTransformerHandlerImpl.transform(_ORIGINAL_CONTENT));
	}

	@Test
	public void testRunsTheOtherContentTransformersEvenIfOneOfThemFails()
		throws Exception {

		_registerInvalidContentTransformer(_ORIGINAL_CONTENT);

		String transformedContent = RandomTestUtil.randomString();

		_registerContentTransformer(_ORIGINAL_CONTENT, transformedContent);

		Assert.assertEquals(
			transformedContent,
			_contentTransformerHandlerImpl.transform(_ORIGINAL_CONTENT));
	}

	private ContentTransformer<String> _registerContentTransformer(
			String originalContent, String transformedContent)
		throws Exception {

		ContentTransformer<String> contentTransformer = Mockito.mock(
			ContentTransformer.class);

		Mockito.when(
			contentTransformer.transform(originalContent)
		).thenReturn(
			transformedContent
		);

		_mockServiceTrackerList.register(contentTransformer);

		return contentTransformer;
	}

	private void _registerInvalidContentTransformer(String originalContent)
		throws Exception {

		ContentTransformer<String> invalidContentTransformer =
			_registerContentTransformer(originalContent, "");

		Mockito.when(
			invalidContentTransformer.transform(originalContent)
		).thenThrow(
			new AMException("This is expected")
		);
	}

	private static final String _ORIGINAL_CONTENT =
		RandomTestUtil.randomString();

	private final ContentTransformerHandlerImpl _contentTransformerHandlerImpl =
		new ContentTransformerHandlerImpl();
	private final MockServiceTrackerList _mockServiceTrackerList =
		new MockServiceTrackerList();

	private final class MockServiceTrackerList
		implements ServiceTrackerList<ContentTransformer<?>> {

		@Override
		public void close() {
			_contentTransformers.clear();
		}

		@Override
		public Iterator<ContentTransformer<?>> iterator() {
			return _contentTransformers.iterator();
		}

		public void register(ContentTransformer<?> contentTransformer) {
			_contentTransformers.add(contentTransformer);
		}

		@Override
		public int size() {
			return _contentTransformers.size();
		}

		@Override
		public <E> E[] toArray(E[] array) {
			return _contentTransformers.toArray(array);
		}

		@Override
		public List<ContentTransformer<?>> toList() {
			return new ArrayList<>(_contentTransformers);
		}

		private final List<ContentTransformer<?>> _contentTransformers =
			new ArrayList<>();

	}

}