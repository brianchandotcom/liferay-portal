/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.search;

import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelRetriever;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Bryan Engler
 */
public class ConfigurationModelIndexerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_configurationModelRetriever.getConfigurationModels(
				Mockito.any(), Mockito.any())
		).thenReturn(
			_getConfigurationModels()
		);

		ReflectionTestUtil.setFieldValue(
			_configurationModelIndexer, "_configurationModelRetriever",
			_configurationModelRetriever);
		ReflectionTestUtil.setFieldValue(
			_configurationModelIndexer, "_indexWriterHelper",
			_indexWriterHelper);
	}

	@Test
	public void testDoReindex() throws Exception {
		_configurationModelIndexer.doReindex(new String[] {"0", "1"});

		Mockito.verify(
			_indexWriterHelper, Mockito.times(1)
		).updateDocuments(
			Mockito.any(Long.class), Mockito.any(Collection.class),
			Mockito.any(Boolean.class)
		);

		Mockito.verify(
			_indexWriterHelper, Mockito.times(0)
		).updateDocument(
			Mockito.any(Long.class), Mockito.any(Document.class)
		);
	}

	private Map<String, ConfigurationModel> _getConfigurationModels() {
		return HashMapBuilder.put(
			"1", Mockito.mock(ConfigurationModel.class)
		).put(
			"2", Mockito.mock(ConfigurationModel.class)
		).build();
	}

	private final ConfigurationModelIndexer _configurationModelIndexer =
		new TestConfigurationModelIndexer();
	private final ConfigurationModelRetriever _configurationModelRetriever =
		Mockito.mock(ConfigurationModelRetriever.class);
	private final IndexWriterHelper _indexWriterHelper = Mockito.mock(
		IndexWriterHelper.class);

	private class TestConfigurationModelIndexer
		extends ConfigurationModelIndexer {

		@Override
		public Document getDocument(ConfigurationModel configurationModel)
			throws SearchException {

			return Mockito.mock(Document.class);
		}

	}

}