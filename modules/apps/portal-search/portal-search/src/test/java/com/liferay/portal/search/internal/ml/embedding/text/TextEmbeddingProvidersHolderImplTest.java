/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.ml.embedding.text;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
@FeatureFlags("LPS-122920")
public class TextEmbeddingProvidersHolderImplTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_textEmbeddingProvidersHolderImpl =
			new TextEmbeddingProvidersHolderImpl();

		ReflectionTestUtil.setFieldValue(
			_textEmbeddingProvidersHolderImpl, "_textEmbeddingProviders",
			HashMapBuilder.put(
				_TEST_PROVIDER_NAME, Mockito.mock(TextEmbeddingProvider.class)
			).build());
	}

	@Test
	public void testAddTextEmbeddingProvider() {
		String name = RandomTestUtil.randomString();

		_textEmbeddingProvidersHolderImpl.addTextEmbeddingProvider(
			name, Mockito.mock(TextEmbeddingProvider.class));

		List<String> names =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertTrue(names.contains(name));

		name = RandomTestUtil.randomString();

		_textEmbeddingProvidersHolderImpl.addTextEmbeddingProvider(
			new String[] {name}, name,
			Mockito.mock(TextEmbeddingProvider.class));

		names =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertFalse(names.contains(name));
	}

	@Test
	public void testGetTextEmbeddingProviderNames() {
		List<String> names =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertEquals(names.toString(), 1, names.size());
		Assert.assertTrue(names.contains(_TEST_PROVIDER_NAME));
	}

	@Test
	public void testRemoveTextEmbeddingProvider() {
		String name = RandomTestUtil.randomString();

		_textEmbeddingProvidersHolderImpl.addTextEmbeddingProvider(
			name, Mockito.mock(TextEmbeddingProvider.class));

		List<String> names =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertTrue(names.contains(name));

		_textEmbeddingProvidersHolderImpl.removeTextEmbeddingProvider(name);

		names =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertFalse(names.contains(name));
	}

	private static final String _TEST_PROVIDER_NAME =
		RandomTestUtil.randomString();

	private TextEmbeddingProvidersHolderImpl _textEmbeddingProvidersHolderImpl;

}