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
		String providerName = RandomTestUtil.randomString();

		_textEmbeddingProvidersHolderImpl.addTextEmbeddingProvider(
			providerName, Mockito.mock(TextEmbeddingProvider.class));

		List<String> availableProviderNames =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertTrue(availableProviderNames.contains(providerName));
	}

	@Test
	public void testDisabledProvider() {
		String providerName = RandomTestUtil.randomString();

		_textEmbeddingProvidersHolderImpl.addTextEmbeddingProvider(
			new String[] {providerName}, providerName,
			Mockito.mock(TextEmbeddingProvider.class));

		List<String> availableProviderNames =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertFalse(availableProviderNames.contains(providerName));
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
		String providerName = RandomTestUtil.randomString();

		_textEmbeddingProvidersHolderImpl.addTextEmbeddingProvider(
			providerName, Mockito.mock(TextEmbeddingProvider.class));

		List<String> availableProviderNames =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertTrue(availableProviderNames.contains(providerName));

		_textEmbeddingProvidersHolderImpl.removeTextEmbeddingProvider(
			providerName);

		availableProviderNames =
			_textEmbeddingProvidersHolderImpl.getTextEmbeddingProviderNames();

		Assert.assertFalse(availableProviderNames.contains(providerName));
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