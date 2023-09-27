/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.blogs.web.internal.exportimport.content.processor;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AMBlogsEntryExportImportContentProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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