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

package com.liferay.journal.asset.auto.tagger.google.cloud.natural.language.internal;

import com.liferay.journal.asset.auto.tagger.google.cloud.natural.language.internal.configuration.GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Cristina González
 */
public class GCloudNaturalLanguageDocumentAssetAutoTagProviderTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetTagNames() throws ConfigurationException {
		GCloudNaturalLanguageDocumentAssetAutoTagProvider
			gCloudNaturalLanguageDocumentAssetAutoTagProvider =
				new GCloudNaturalLanguageDocumentAssetAutoTagProvider();

		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				Mockito.anyObject(), Mockito.anyLong())
		).thenReturn(
			(GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration)
				() -> false
		);

		ReflectionTestUtils.setField(
			gCloudNaturalLanguageDocumentAssetAutoTagProvider,
			"_configurationProvider", _configurationProvider);

		Assert.assertEquals(
			Collections.emptySet(),
			gCloudNaturalLanguageDocumentAssetAutoTagProvider.getTagNames(
				_journalArticle));
	}

	@Mock
	private ConfigurationProvider _configurationProvider;

	@Mock
	private JournalArticle _journalArticle;

}