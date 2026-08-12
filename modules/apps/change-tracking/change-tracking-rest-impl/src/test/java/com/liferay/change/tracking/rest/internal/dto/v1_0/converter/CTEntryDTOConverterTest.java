/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.dto.v1_0.converter;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Kiana Suetani
 */
public class CTEntryDTOConverterTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_ctEntry = Mockito.mock(CTEntry.class);

		Mockito.when(
			_ctEntry.getCtEntryId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		_ctEntryDTOConverter = new CTEntryDTOConverter();

		_indexer = Mockito.mock(Indexer.class);

		_indexerRegistry = Mockito.mock(IndexerRegistry.class);

		Mockito.when(
			_indexerRegistry.getIndexer(CTEntry.class)
		).thenReturn(
			_indexer
		);

		ReflectionTestUtil.setFieldValue(
			_ctEntryDTOConverter, "_indexerRegistry", _indexerRegistry);

		ReflectionTestUtil.setFieldValue(
			_ctEntryDTOConverter, "_language", Mockito.mock(Language.class));
	}

	@Test
	public void testToDTOFallsBackToIndexerDocument() throws Exception {
		Document indexerDocument = new DocumentImpl();

		String ctCollectionName = RandomTestUtil.randomString();

		indexerDocument.addKeyword("ctCollectionName", ctCollectionName);

		Mockito.when(
			_indexer.getDocument(_ctEntry)
		).thenReturn(
			indexerDocument
		);

		com.liferay.change.tracking.rest.dto.v1_0.CTEntry ctEntryDTO =
			_ctEntryDTOConverter.toDTO(
				new DefaultDTOConverterContext(
					false, new HashMap<>(), new HashMap<>(), null, null,
					_ctEntry.getCtEntryId(), LocaleUtil.US, null, null),
				_ctEntry);

		Assert.assertEquals(ctCollectionName, ctEntryDTO.getCtCollectionName());
	}

	@Test
	public void testToDTOUsesContextDocument() throws Exception {
		Document contextDocument = new DocumentImpl();

		String ctCollectionName = RandomTestUtil.randomString();

		contextDocument.addKeyword("ctCollectionName", ctCollectionName);

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, new HashMap<>(), new HashMap<>(), null, null,
				_ctEntry.getCtEntryId(), LocaleUtil.US, null, null);

		defaultDTOConverterContext.setAttribute("document", contextDocument);

		com.liferay.change.tracking.rest.dto.v1_0.CTEntry ctEntryDTO =
			_ctEntryDTOConverter.toDTO(defaultDTOConverterContext, _ctEntry);

		Assert.assertEquals(ctCollectionName, ctEntryDTO.getCtCollectionName());

		Mockito.verify(
			_indexerRegistry, Mockito.never()
		).getIndexer(
			Mockito.any(Class.class)
		);
	}

	private CTEntry _ctEntry;
	private CTEntryDTOConverter _ctEntryDTOConverter;
	private Indexer<CTEntry> _indexer;
	private IndexerRegistry _indexerRegistry;

}