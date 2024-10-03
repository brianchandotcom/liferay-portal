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
public class TextEmbeddingProvidersHolderTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpTextEmbeddingProvidersHolderImpl();
	}

	@Test
	public void testAddProvider() {
		String name = RandomTestUtil.randomString();

		_textEmbeddingProvidersHolderImpl.addTextEmbeddingProvider(
			name, Mockito.mock(TextEmbeddingProvider.class));

		List<String> availableProviderNames =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertTrue(availableProviderNames.contains(name));
	}

	@Test
	public void testDisabledProvider() {
		String name = RandomTestUtil.randomString();

		_textEmbeddingProvidersHolderImpl.addTextEmbeddingProvider(
			new String[] {name}, name,
			Mockito.mock(TextEmbeddingProvider.class));

		List<String> availableProviderNames =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertFalse(availableProviderNames.contains(name));
	}

	@Test
	public void testGetAvailableProviderNames() {
		List<String> availableProviderNames =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertEquals(
			availableProviderNames.toString(), 1,
			availableProviderNames.size());
		Assert.assertTrue(availableProviderNames.contains(_TEST_PROVIDER_NAME));
	}

	@Test
	public void testRemoveProvider() {
		String name = RandomTestUtil.randomString();

		_textEmbeddingProvidersHolderImpl.addTextEmbeddingProvider(
			name, Mockito.mock(TextEmbeddingProvider.class));

		List<String> availableProviderNames =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertTrue(availableProviderNames.contains(name));

		_textEmbeddingProvidersHolderImpl.removeTextEmbeddingProvider(
			name);

		availableProviderNames =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertFalse(availableProviderNames.contains(name));
	}

	private void _setUpTextEmbeddingProvidersHolderImpl() {
		_textEmbeddingProvidersHolderImpl =
			new TextEmbeddingProvidersHolderImpl();

		ReflectionTestUtil.setFieldValue(
			_textEmbeddingProvidersHolderImpl, "_textEmbeddingProviders",
			HashMapBuilder.put(
				_TEST_PROVIDER_NAME, _textEmbeddingProvider
			).build());
	}

	private static final String _TEST_PROVIDER_NAME =
		RandomTestUtil.randomString();

	private final TextEmbeddingProvider _textEmbeddingProvider = Mockito.mock(
		TextEmbeddingProvider.class);
	private TextEmbeddingProvidersHolderImpl _textEmbeddingProvidersHolderImpl;

}