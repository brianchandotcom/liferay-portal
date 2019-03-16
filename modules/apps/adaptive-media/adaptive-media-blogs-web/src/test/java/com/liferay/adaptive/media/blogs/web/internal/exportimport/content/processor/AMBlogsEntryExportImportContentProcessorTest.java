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

package com.liferay.adaptive.media.blogs.web.internal.exportimport.content.processor;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
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
public class AMBlogsEntryExportImportContentProcessorTest {

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_amBlogsEntryExportImportContentProcessor,
			"_htmlExportImportContentProcessor",
			_htmlExportImportContentProcessor);
		ReflectionTestUtil.setFieldValue(
			_amBlogsEntryExportImportContentProcessor,
			"_blogsEntryExportImportContentProcessor",
			_blogsEntryExportImportContentProcessor);
	}

	@Test
	public void testExportCallsBothExportImportContentProcessors()
		throws Exception {

		String originalContent = RandomTestUtil.randomString();
		String blogsEntryReplacedContent = RandomTestUtil.randomString();

		Mockito.doReturn(
			blogsEntryReplacedContent
		).when(
			_blogsEntryExportImportContentProcessor
		).replaceExportContentReferences(
			_portletDataContext, _blogsEntry, originalContent, false, false
		);

		String adaptiveMediaReplacedContent = RandomTestUtil.randomString();

		Mockito.doReturn(
			adaptiveMediaReplacedContent
		).when(
			_htmlExportImportContentProcessor
		).replaceExportContentReferences(
			_portletDataContext, _blogsEntry, blogsEntryReplacedContent, false,
			false
		);

		Assert.assertEquals(
			adaptiveMediaReplacedContent,
			_amBlogsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _blogsEntry, originalContent, false,
					false));
	}

	@Test
	public void testImportCallsBothExportImportContentProcessors()
		throws Exception {

		String originalContent = RandomTestUtil.randomString();

		String blogsEntryReplacedContent = RandomTestUtil.randomString();

		Mockito.doReturn(
			blogsEntryReplacedContent
		).when(
			_blogsEntryExportImportContentProcessor
		).replaceImportContentReferences(
			_portletDataContext, _blogsEntry, originalContent
		);

		String adaptiveMediaReplacedContent = RandomTestUtil.randomString();

		Mockito.doReturn(
			adaptiveMediaReplacedContent
		).when(
			_htmlExportImportContentProcessor
		).replaceImportContentReferences(
			_portletDataContext, _blogsEntry, blogsEntryReplacedContent
		);

		Assert.assertEquals(
			adaptiveMediaReplacedContent,
			_amBlogsEntryExportImportContentProcessor.
				replaceImportContentReferences(
					_portletDataContext, _blogsEntry, originalContent));
	}

	@Test(expected = PortalException.class)
	public void testValidateContentFailsWhenBlogsEntryExportImportContentProcessorProcessorFails()
		throws Exception {

		String content = RandomTestUtil.randomString();

		Mockito.doThrow(
			PortalException.class
		).when(
			_blogsEntryExportImportContentProcessor
		).validateContentReferences(
			Mockito.anyLong(), Mockito.anyString()
		);

		_amBlogsEntryExportImportContentProcessor.validateContentReferences(
			RandomTestUtil.randomLong(), content);
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

		_amBlogsEntryExportImportContentProcessor.validateContentReferences(
			RandomTestUtil.randomLong(), content);
	}

	@Test
	public void testValidateContentSucceedsWhenBothExportImportContentProcessorsSucceed()
		throws Exception {

		_amBlogsEntryExportImportContentProcessor.validateContentReferences(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());
	}

	private final AMBlogsEntryExportImportContentProcessor
		_amBlogsEntryExportImportContentProcessor =
			new AMBlogsEntryExportImportContentProcessor();
	private final BlogsEntry _blogsEntry = Mockito.mock(BlogsEntry.class);
	private final ExportImportContentProcessor<String>
		_blogsEntryExportImportContentProcessor = Mockito.mock(
			ExportImportContentProcessor.class);
	private final ExportImportContentProcessor<String>
		_htmlExportImportContentProcessor = Mockito.mock(
			ExportImportContentProcessor.class);
	private final PortletDataContext _portletDataContext = Mockito.mock(
		PortletDataContext.class);

}