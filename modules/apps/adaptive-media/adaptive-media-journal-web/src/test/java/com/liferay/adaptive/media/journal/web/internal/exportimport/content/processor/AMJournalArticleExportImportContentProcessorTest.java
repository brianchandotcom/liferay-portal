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

package com.liferay.adaptive.media.journal.web.internal.exportimport.content.processor;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AMJournalArticleExportImportContentProcessorTest {

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_amJournalArticleExportImportContentProcessor,
			"_amJournalArticleContentHTMLReplacer",
			_amJournalArticleContentHTMLReplacer);
		ReflectionTestUtil.setFieldValue(
			_amJournalArticleExportImportContentProcessor,
			"_htmlExportImportContentProcessor",
			_htmlExportImportContentProcessor);
		ReflectionTestUtil.setFieldValue(
			_amJournalArticleExportImportContentProcessor,
			"_journalArticleExportImportContentProcessor",
			_journalArticleExportImportContentProcessor);

		Mockito.when(
			_amJournalArticleContentHTMLReplacer.replace(
				Mockito.anyString(),
				Mockito.any(AMJournalArticleContentHTMLReplacer.Replace.class))
		).then(
			answer -> {
				AMJournalArticleContentHTMLReplacer.Replace replace =
					answer.getArgumentAt(
						1, AMJournalArticleContentHTMLReplacer.Replace.class);
				String content = answer.getArgumentAt(0, String.class);

				return replace.apply(content);
			}
		);
	}

	@Test
	public void testExportCallsBothExportImportContentProcessors()
		throws Exception {

		String originalContent = RandomTestUtil.randomString();
		String journalArticleReplacedContent = RandomTestUtil.randomString();

		Mockito.doReturn(
			journalArticleReplacedContent
		).when(
			_journalArticleExportImportContentProcessor
		).replaceExportContentReferences(
			_portletDataContext, _journalArticle, originalContent, false, false
		);

		String adaptiveMediaReplacedContent = RandomTestUtil.randomString();

		Mockito.doReturn(
			adaptiveMediaReplacedContent
		).when(
			_htmlExportImportContentProcessor
		).replaceExportContentReferences(
			_portletDataContext, _journalArticle, journalArticleReplacedContent,
			false, false
		);

		Assert.assertEquals(
			adaptiveMediaReplacedContent,
			_amJournalArticleExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _journalArticle, originalContent,
					false, false));
	}

	@Test
	public void testImportCallsBothExportImportContentProcessors()
		throws Exception {

		String originalContent = RandomTestUtil.randomString();
		String journalArticleReplacedContent = RandomTestUtil.randomString();

		Mockito.doReturn(
			journalArticleReplacedContent
		).when(
			_journalArticleExportImportContentProcessor
		).replaceImportContentReferences(
			_portletDataContext, _journalArticle, originalContent
		);

		String adaptiveMediaReplacedContent = RandomTestUtil.randomString();

		Mockito.doReturn(
			adaptiveMediaReplacedContent
		).when(
			_htmlExportImportContentProcessor
		).replaceImportContentReferences(
			_portletDataContext, _journalArticle, journalArticleReplacedContent
		);

		Assert.assertEquals(
			adaptiveMediaReplacedContent,
			_amJournalArticleExportImportContentProcessor.
				replaceImportContentReferences(
					_portletDataContext, _journalArticle, originalContent));
	}

	@Test(expected = PortalException.class)
	public void testValidateContentFailsWhenHTMLExportImportContentProcessorFails()
		throws Exception {

		String content = RandomTestUtil.randomString();

		Mockito.doThrow(
			PortalException.class
		).when(
			_htmlExportImportContentProcessor
		).validateContentReferences(
			Mockito.anyLong(), Mockito.anyString()
		);

		_amJournalArticleExportImportContentProcessor.validateContentReferences(
			RandomTestUtil.randomLong(), content);
	}

	@Test(expected = PortalException.class)
	public void testValidateContentFailsWhenJournalArticleExportImportContentProcessorFails()
		throws Exception {

		String content = RandomTestUtil.randomString();

		Mockito.doThrow(
			PortalException.class
		).when(
			_journalArticleExportImportContentProcessor
		).validateContentReferences(
			Mockito.anyLong(), Mockito.anyString()
		);

		_amJournalArticleExportImportContentProcessor.validateContentReferences(
			RandomTestUtil.randomLong(), content);
	}

	@Test
	public void testValidateContentSucceedsWhenBothExportImportContentProcessorsSucceed()
		throws Exception {

		_amJournalArticleExportImportContentProcessor.validateContentReferences(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());
	}

	private final AMJournalArticleContentHTMLReplacer
		_amJournalArticleContentHTMLReplacer = Mockito.mock(
			AMJournalArticleContentHTMLReplacer.class);
	private final AMJournalArticleExportImportContentProcessor
		_amJournalArticleExportImportContentProcessor =
			new AMJournalArticleExportImportContentProcessor();
	private final ExportImportContentProcessor<String>
		_htmlExportImportContentProcessor = Mockito.mock(
			ExportImportContentProcessor.class);
	private final JournalArticle _journalArticle = Mockito.mock(
		JournalArticle.class);
	private final ExportImportContentProcessor<String>
		_journalArticleExportImportContentProcessor = Mockito.mock(
			ExportImportContentProcessor.class);
	private final PortletDataContext _portletDataContext = Mockito.mock(
		PortletDataContext.class);

}