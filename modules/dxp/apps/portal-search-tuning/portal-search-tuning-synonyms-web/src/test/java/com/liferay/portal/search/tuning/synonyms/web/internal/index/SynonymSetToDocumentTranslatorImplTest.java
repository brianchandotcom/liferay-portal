/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetToDocumentTranslatorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_synonymSetToDocumentTranslatorImpl =
			new SynonymSetToDocumentTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			_synonymSetToDocumentTranslatorImpl, "_documentBuilderFactory",
			_documentBuilderFactory);
	}

	@Test
	public void testTranslate() {
		Document document = Mockito.mock(Document.class);

		DocumentBuilder documentBuilder = Mockito.mock(DocumentBuilder.class);

		Mockito.doReturn(
			document
		).when(
			documentBuilder
		).build();

		Mockito.doReturn(
			documentBuilder
		).when(
			documentBuilder
		).setString(
			Mockito.nullable(String.class), Mockito.nullable(String.class)
		);

		Mockito.doReturn(
			documentBuilder
		).when(
			_documentBuilderFactory
		).builder();

		_synonymSetToDocumentTranslatorImpl.translate(
			Mockito.mock(SynonymSet.class));

		Assert.assertEquals(
			document,
			_synonymSetToDocumentTranslatorImpl.translate(
				Mockito.mock(SynonymSet.class)));
	}

	private final DocumentBuilderFactory _documentBuilderFactory = Mockito.mock(
		DocumentBuilderFactory.class);
	private SynonymSetToDocumentTranslatorImpl
		_synonymSetToDocumentTranslatorImpl;

}