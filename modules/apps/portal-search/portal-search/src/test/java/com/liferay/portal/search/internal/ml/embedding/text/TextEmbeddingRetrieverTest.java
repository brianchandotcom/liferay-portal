/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.ml.embedding.text;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.configuration.SemanticSearchConfiguration;
import com.liferay.portal.search.configuration.SemanticSearchConfigurationProvider;
import com.liferay.portal.search.ml.embedding.EmbeddingProviderStatus;
import com.liferay.portal.search.rest.dto.v1_0.EmbeddingProviderConfiguration;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
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
public class TextEmbeddingRetrieverTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setSemanticSearchConfiguration(
			new String[] {LocaleUtil.toLanguageId(LocaleUtil.US)},
			new String[] {BlogsEntry.class.getName()});
		_setUpTextEmbeddingProvider();
		_setUpTextEmbeddingRetrieverImpl();
	}

	@Test
	public void testDisabledProvider() {
		_textEmbeddingRetrieverImpl.addProvider(
			new String[] {"disabledProvider"}, "disabledProvider",
			Mockito.mock(TextEmbeddingProvider.class));

		List<String> availableProviderNames =
			_textEmbeddingRetrieverImpl.getAvailableProviderNames();

		Assert.assertFalse(availableProviderNames.contains("disabledProvider"));
	}

	@Test
	public void testGetAvailableProviderNames() {
		List<String> availableProviderNames =
			_textEmbeddingRetrieverImpl.getAvailableProviderNames();

		Assert.assertEquals(
			availableProviderNames.toString(), 1,
			availableProviderNames.size());
		Assert.assertTrue(availableProviderNames.contains("testProvider"));
	}

	@Test
	public void testGetEmbeddingProviderConfiguration() {
		EmbeddingProviderConfiguration embeddingProviderConfiguration =
			_textEmbeddingRetrieverImpl.getEmbeddingProviderConfiguration(
				"testProvider");

		Assert.assertNotNull(embeddingProviderConfiguration);
		Assert.assertEquals(
			"testProvider", embeddingProviderConfiguration.getProviderName());
	}

	@Test
	public void testGetEmbeddingProviderConfigurationNotFound() {
		Assert.assertNull(
			_textEmbeddingRetrieverImpl.getEmbeddingProviderConfiguration(
				"notFoundProvider"));
	}

	@Test
	public void testGetEmbeddingProviderStatus() {
		EmbeddingProviderStatus embeddingProviderStatus =
			_textEmbeddingRetrieverImpl.getEmbeddingProviderStatus(
				new EmbeddingProviderConfiguration(
				) {

					{
						providerName = "testProvider";
					}
				}.toString());

		Assert.assertNotNull(embeddingProviderStatus);
		Assert.assertEquals(
			"testProvider", embeddingProviderStatus.getProviderName());
	}

	@Test
	public void testGetEmbeddingProviderStatuses() {
		EmbeddingProviderStatus[] embeddingProviderStatuses =
			_textEmbeddingRetrieverImpl.getEmbeddingProviderStatuses();

		Assert.assertNotNull(embeddingProviderStatuses);
		Assert.assertEquals(
			Arrays.toString(embeddingProviderStatuses), 1,
			embeddingProviderStatuses.length);
		Assert.assertEquals(
			Arrays.toString(embeddingProviderStatuses), "testProvider",
			embeddingProviderStatuses[0].getProviderName());
	}

	@Test
	public void testGetEmbeddingProviderStatusWithException() {
		Mockito.when(
			_textEmbeddingProvider.getEmbedding(
				Mockito.any(), Mockito.anyString())
		).thenThrow(
			new RuntimeException("Test exception")
		);

		EmbeddingProviderStatus embeddingProviderStatus =
			_textEmbeddingRetrieverImpl.getEmbeddingProviderStatus(
				new EmbeddingProviderConfiguration(
				) {

					{
						providerName = "testProvider";
					}
				}.toString());

		Assert.assertNotNull(embeddingProviderStatus);
		Assert.assertEquals(
			"Test exception", embeddingProviderStatus.getErrorMessage());
		Assert.assertEquals(
			"testProvider", embeddingProviderStatus.getProviderName());
	}

	@Test
	public void testGetEmbeddingProviderStatusWithProviderNotFound() {
		EmbeddingProviderStatus embeddingProviderStatus =
			_textEmbeddingRetrieverImpl.getEmbeddingProviderStatus(
				new EmbeddingProviderConfiguration(
				) {

					{
						providerName = "notFoundProvider";
					}
				}.toString());

		Assert.assertNotNull(embeddingProviderStatus);
		Assert.assertEquals(
			"Embedding provider notFoundProvider was not found",
			embeddingProviderStatus.getErrorMessage());
		Assert.assertEquals(
			"notFoundProvider", embeddingProviderStatus.getProviderName());
	}

	@Test
	public void testGetTextEmbedding() {
		Double[] textEmbedding = _textEmbeddingRetrieverImpl.getTextEmbedding(
			"testProvider", "testText");

		Assert.assertNotNull(textEmbedding);
		Assert.assertArrayEquals(new Double[] {1.0, 2.0, 3.0}, textEmbedding);
	}

	@Test
	public void testGetTextEmbeddingProviderConfigurationJSONs() {
		String[] textEmbeddingProviderConfigurationJSONs =
			_textEmbeddingRetrieverImpl.
				getTextEmbeddingProviderConfigurationJSONs();

		Assert.assertNotNull(textEmbeddingProviderConfigurationJSONs);
		Assert.assertEquals(
			Arrays.toString(textEmbeddingProviderConfigurationJSONs), 1,
			textEmbeddingProviderConfigurationJSONs.length);
		Assert.assertTrue(
			textEmbeddingProviderConfigurationJSONs[0].contains(
				"testProvider"));
	}

	@Test
	public void testGetTextEmbeddingWithProviderNotFound() {
		Double[] textEmbedding = _textEmbeddingRetrieverImpl.getTextEmbedding(
			"notFoundProvider", "testText");

		Assert.assertNotNull(textEmbedding);
		Assert.assertEquals(
			Arrays.toString(textEmbedding), 0, textEmbedding.length);
	}

	private SemanticSearchConfiguration _createSemanticSearchConfiguration(
		String[] embeddingProviderLanguageIds,
		String[] embeddingProviderModelClassNames) {

		SemanticSearchConfiguration semanticSearchConfiguration = Mockito.mock(
			SemanticSearchConfiguration.class);

		Mockito.when(
			semanticSearchConfiguration.
				textEmbeddingProviderConfigurationJSONs()
		).thenReturn(
			new String[] {
				new EmbeddingProviderConfiguration(
				) {

					{
						languageIds = embeddingProviderLanguageIds;
						modelClassNames = embeddingProviderModelClassNames;
						providerName = "testProvider";
					}
				}.toString()
			}
		);
		Mockito.when(
			semanticSearchConfiguration.textEmbeddingsEnabled()
		).thenReturn(
			true
		);

		return semanticSearchConfiguration;
	}

	private void _setSemanticSearchConfiguration(
		String[] embeddingProviderLanguageIds,
		String[] embeddingProviderModelClassNames) {

		SemanticSearchConfiguration semanticSearchConfiguration =
			_createSemanticSearchConfiguration(
				embeddingProviderLanguageIds, embeddingProviderModelClassNames);

		Mockito.when(
			_semanticSearchConfigurationProvider.getCompanyConfiguration(
				Mockito.anyLong())
		).thenReturn(
			semanticSearchConfiguration
		);
	}

	private void _setUpTextEmbeddingProvider() {
		Mockito.when(
			_textEmbeddingProvider.getEmbedding(
				Mockito.any(), Mockito.anyString())
		).thenReturn(
			new Double[] {1.0, 2.0, 3.0}
		);
	}

	private void _setUpTextEmbeddingRetrieverImpl() {
		_textEmbeddingRetrieverImpl = new TextEmbeddingRetrieverImpl();

		ReflectionTestUtil.setFieldValue(
			_textEmbeddingRetrieverImpl, "_semanticSearchConfigurationProvider",
			_semanticSearchConfigurationProvider);
		ReflectionTestUtil.setFieldValue(
			_textEmbeddingRetrieverImpl, "_textEmbeddingProviders",
			HashMapBuilder.put(
				"testProvider", _textEmbeddingProvider
			).build());
	}

	private final SemanticSearchConfigurationProvider
		_semanticSearchConfigurationProvider = Mockito.mock(
			SemanticSearchConfigurationProvider.class);
	private final TextEmbeddingProvider _textEmbeddingProvider = Mockito.mock(
		TextEmbeddingProvider.class);
	private TextEmbeddingRetrieverImpl _textEmbeddingRetrieverImpl;

}