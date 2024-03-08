/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetToDocumentTranslatorUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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

		Assert.assertEquals(
			document,
			SynonymSetToDocumentTranslatorUtil.translate(
				_documentBuilderFactory, Mockito.mock(SynonymSet.class)));
	}

	private final DocumentBuilderFactory _documentBuilderFactory = Mockito.mock(
		DocumentBuilderFactory.class);

}