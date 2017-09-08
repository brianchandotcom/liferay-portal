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

package com.liferay.adaptive.media.blogs.internal.exportimport.content.processor;

import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adolfo Pérez
 */
@PrepareForTest(ExportImportPathUtil.class)
@RunWith(PowerMockRunner.class)
public class AMBlogsEntryExportImportContentProcessorTest {

	@Before
	public void setUp() throws Exception {
		_amBlogsEntryExportImportContentProcessor.setDLAppLocalService(
			_dlAppLocalService);
		_amBlogsEntryExportImportContentProcessor.
			setExportImportContentProcessor(_exportImportContentProcessor);
		_amBlogsEntryExportImportContentProcessor.setAMImageHTMLTagFactory(
			_amImageHTMLTagFactory);
		_amBlogsEntryExportImportContentProcessor.
			setAMEmbeddedReferenceSetFactory(_amEmbeddedReferenceSetFactory);

		Mockito.doReturn(
			_amEmbeddedReferenceSet
		).when(
			_amEmbeddedReferenceSetFactory
		).create(
			Mockito.any(PortletDataContext.class),
			Mockito.any(StagedModel.class)
		);

		Mockito.doThrow(
			NoSuchFileEntryException.class
		).when(
			_dlAppLocalService
		).getFileEntry(
			Mockito.anyLong()
		);

		PowerMockito.mockStatic(ExportImportPathUtil.class);

		_defineFileEntryToExport(1, _fileEntry1);
		_defineFileEntryToImport(1, _fileEntry1);

		_defineFileEntryToExport(2, _fileEntry2);
		_defineFileEntryToImport(2, _fileEntry2);
	}

	@Test
	public void testExportContentWithNoReferencesDoesNotEscape()
		throws Exception {

		String content = StringPool.AMPERSAND;

		_makeOverridenProcessorReturn(content);

		Assert.assertEquals(
			_amBlogsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _blogsEntry, content, false, false),
			_amBlogsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, Mockito.mock(BlogsEntry.class),
					content, false, true));
	}

	@Test
	public void testExportContentWithReferenceDoesNotEscape() throws Exception {
		String content = "&<img data-fileentryid=\"1\" src=\"url\" />&";

		_makeOverridenProcessorReturn(content);

		Assert.assertEquals(
			_amBlogsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _blogsEntry, content, false, false),
			_amBlogsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _blogsEntry, content, false, true));
	}

	@Test
	public void testExportContentWithStaticReferenceDoesNotEscape()
		throws Exception {

		String content = "&<picture data-fileentryid=\"1\"></picture>&";

		_makeOverridenProcessorReturn(content);

		Assert.assertEquals(
			_amBlogsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _blogsEntry, content, false, false),
			_amBlogsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _blogsEntry, content, false, true));
	}

	@Test
	public void testExportImportContentWithMultipleReferences()
		throws Exception {

		String prefix = StringUtil.randomString();
		String infix = StringUtil.randomString();
		String suffix = StringUtil.randomString();

		String content =
			prefix + "<img data-fileentryid=\"1\" src=\"url1\" />" + infix +
				"<img data-fileentryid=\"2\" src=\"url2\" />" + suffix;

		String importedContent = _import(_export(content));

		StringBundler sb = new StringBundler(4);

		sb.append(prefix);
		sb.append("<img src=\"url1\" data-fileEntryId=\"1\" />");
		sb.append(infix);
		sb.append("<img src=\"url2\" data-fileEntryId=\"2\" />");
		sb.append(suffix);

		Assert.assertEquals(sb.toString(), importedContent);
	}

	@Test
	public void testExportImportContentWithMultipleStaticReferences()
		throws Exception {

		String prefix = StringUtil.randomString();
		String infix = StringUtil.randomString();
		String suffix = StringUtil.randomString();

		String content =
			prefix + "<picture data-fileentryid=\"1\"><img src=\"url1\" />" +
				"</picture>" + infix + "<picture data-fileentryid=\"2\">" +
					"<img src=\"url2\" /></picture>" + suffix;

		String importedContent = _import(_export(content));

		StringBundler sb = new StringBundler();

		sb.append(prefix);
		sb.append("<picture data-fileEntryId=\"1\">");
		sb.append("<source /><img src=\"url1\" />");
		sb.append("</picture>");
		sb.append(infix);
		sb.append("<picture data-fileEntryId=\"2\">");
		sb.append("<source /><img src=\"url2\" />");
		sb.append("</picture>");
		sb.append(suffix);

		Assert.assertEquals(sb.toString(), importedContent);
	}

	@Test
	public void testExportImportContentWithNoReferences() throws Exception {
		String content = StringUtil.randomString();

		String importedContent = _import(_export(content));

		Assert.assertEquals(content, importedContent);
	}

	@Test
	public void testExportImportContentWithReference() throws Exception {
		String prefix = StringUtil.randomString();
		String suffix = StringUtil.randomString();

		String content =
			prefix + "<img data-fileentryid=\"1\" src=\"url\" />" + suffix;

		String importedContent = _import(_export(content));

		Assert.assertEquals(
			prefix + "<img src=\"url\" data-fileEntryId=\"1\" />" +
				suffix,
			importedContent);
	}

	@Test
	public void testExportImportContentWithReferenceContainingMoreAttributes()
		throws Exception {

		String prefix = StringUtil.randomString();
		String suffix = StringUtil.randomString();

		String content =
			prefix + "<img attr1=\"1\" data-fileentryid=\"1\" attr2=\"2\" " +
				"src=\"url\" attr3=\"3\"/>" + suffix;

		String importedContent = _import(_export(content));

		Assert.assertEquals(
			prefix + "<img attr1=\"1\" attr2=\"2\" src=\"url\" attr3=\"3\" " +
				"data-fileEntryId=\"1\" />" + suffix,
			importedContent);
	}

	@Test
	public void testExportImportContentWithStaticReference() throws Exception {
		String prefix = StringUtil.randomString();
		String suffix = StringUtil.randomString();

		String content =
			prefix + "<picture data-fileentryid=\"1\">" +
				"<img src=\"url\" /></picture>" + suffix;

		String importedContent = _import(_export(content));

		Assert.assertEquals(
			prefix + "<picture data-fileEntryId=\"1\"><source />" +
				"<img src=\"url\" /></picture>" + suffix,
			importedContent);
	}

	@Test
	public void testExportImportContentWithStaticReferenceContainingImageWithAttributes()
		throws Exception {

		String content =
			"<picture data-fileentryid=\"1\"><img src=\"url\" " +
				"class=\"pretty\" /></picture>";

		String importedContent = _import(_export(content));

		Assert.assertEquals(
			"<picture data-fileEntryId=\"1\"><source /><img src=\"url\" " +
				"class=\"pretty\" /></picture>",
			importedContent);
	}

	@Test
	public void testImportContentIgnoresInvalidReferences() throws Exception {
		String content = "<img export-import-path=\"PATH_1\" />";

		_makeOverridenProcessorReturn(content);

		Mockito.doThrow(
			PortalException.class
		).when(
			_dlAppLocalService
		).getFileEntry(
			1
		);

		String replacedContent =
			_amBlogsEntryExportImportContentProcessor.
				replaceImportContentReferences(
					_portletDataContext, _blogsEntry, content);

		Assert.assertEquals(content, replacedContent);
	}

	@Test
	public void testImportContentIgnoresInvalidStaticReferences()
		throws Exception {

		String content = "<picture export-import-path=\"#@¢∞\"></picture>";

		_makeOverridenProcessorReturn(content);

		String replacedContent =
			_amBlogsEntryExportImportContentProcessor.
				replaceImportContentReferences(
					_portletDataContext, _blogsEntry, content);

		Assert.assertEquals(content, replacedContent);
	}

	@Test
	public void testImportContentIgnoresReferencesWithMissingPaths()
		throws Exception {

		String content = "<img export-import-path=\"#@¢∞\" />";

		_makeOverridenProcessorReturn(content);

		String replacedContent =
			_amBlogsEntryExportImportContentProcessor.
				replaceImportContentReferences(
					_portletDataContext, _blogsEntry, content);

		Assert.assertEquals(content, replacedContent);
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testValidateContentFailsWhenInvalidReferences()
		throws Exception {

		String content = "<img data-fileentryid=\"0\" src=\"PATH_1\" />";

		_amBlogsEntryExportImportContentProcessor.validateContentReferences(
			1, content);
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testValidateContentFailsWhenInvalidStaticReferences()
		throws Exception {

		String content = "<picture data-fileentryid=\"0\"></picture>";

		_amBlogsEntryExportImportContentProcessor.validateContentReferences(
			1, content);
	}

	@Test(expected = PortalException.class)
	public void testValidateContentFailsWhenOverridenProcessorFails()
		throws Exception {

		String content = StringUtil.randomString();

		Mockito.doThrow(
			PortalException.class
		).when(
			_exportImportContentProcessor
		).validateContentReferences(
			Mockito.anyLong(), Mockito.anyString()
		);

		_amBlogsEntryExportImportContentProcessor.validateContentReferences(
			1, content);
	}

	@Test
	public void testValidateContentSucceedsWhenAllReferencesAreValid()
		throws Exception {

		String content = "<img data-fileentryid=\"1\" src=\"PATH_1\" />";

		_amBlogsEntryExportImportContentProcessor.validateContentReferences(
			1, content);
	}

	@Test
	public void testValidateContentSucceedsWhenAllStaticReferencesAreValid()
		throws Exception {

		String content = "<picture data-fileentryid=\"1\"></picture>";

		Mockito.doReturn(
			_fileEntry1
		).when(
			_dlAppLocalService
		).getFileEntry(
			Mockito.anyLong()
		);

		_amBlogsEntryExportImportContentProcessor.validateContentReferences(
			1, content);
	}

	private void _defineFileEntryToExport(long fileEntryId, FileEntry fileEntry)
		throws PortalException {

		Mockito.doReturn(
			fileEntry
		).when(
			_dlAppLocalService
		).getFileEntry(
			fileEntryId
		);

		Mockito.when(
			ExportImportPathUtil.getModelPath(fileEntry)
		).thenReturn(
			"PATH_" + fileEntryId
		);
	}

	private void _defineFileEntryToImport(long fileEntryId, FileEntry fileEntry)
		throws Exception {

		Mockito.doReturn(
			true
		).when(
			_amEmbeddedReferenceSet
		).containsReference(
			"PATH_" + fileEntryId
		);

		Mockito.doReturn(
			fileEntryId
		).when(
			_amEmbeddedReferenceSet
		).importReference(
			"PATH_" + fileEntryId
		);

		Mockito.doReturn(
			fileEntry
		).when(
			_dlAppLocalService
		).getFileEntry(
			fileEntryId
		);

		Mockito.when(
			_amImageHTMLTagFactory.create(
				Mockito.anyString(), Mockito.eq(fileEntry))
		).thenAnswer(
			invocation -> {
				String imgTag = invocation.getArgumentAt(0, String.class);

				return "<picture><source/>" + imgTag + "</picture>";
			}
		);
	}

	private String _export(String content) throws Exception {
		_makeOverridenProcessorReturn(content);

		return _amBlogsEntryExportImportContentProcessor.
			replaceExportContentReferences(
				_portletDataContext, _blogsEntry, content, false, false);
	}

	private String _import(String exportedContent) throws Exception {
		_makeOverridenProcessorReturn(exportedContent);

		return _amBlogsEntryExportImportContentProcessor.
			replaceImportContentReferences(
				_portletDataContext, _blogsEntry, exportedContent);
	}

	private void _makeOverridenProcessorReturn(String content)
		throws Exception {

		Mockito.doReturn(
			content
		).when(
			_exportImportContentProcessor
		).replaceExportContentReferences(
			Mockito.any(PortletDataContext.class),
			Mockito.any(StagedModel.class), Mockito.anyString(),
			Mockito.anyBoolean(), Mockito.anyBoolean()
		);

		Mockito.doReturn(
			content
		).when(
			_exportImportContentProcessor
		).replaceImportContentReferences(
			Mockito.any(PortletDataContext.class),
			Mockito.any(StagedModel.class), Mockito.anyString()
		);

		Mockito.doReturn(
			_amEmbeddedReferenceSet
		).when(
			_amEmbeddedReferenceSetFactory
		).create(
			Mockito.any(PortletDataContext.class),
			Mockito.any(StagedModel.class)
		);
	}

	private final AMBlogsEntryExportImportContentProcessor
		_amBlogsEntryExportImportContentProcessor =
			new AMBlogsEntryExportImportContentProcessor();
	private final AMEmbeddedReferenceSet _amEmbeddedReferenceSet = Mockito.mock(
		AMEmbeddedReferenceSet.class);
	private final AMEmbeddedReferenceSetFactory _amEmbeddedReferenceSetFactory =
		Mockito.mock(AMEmbeddedReferenceSetFactory.class);
	private final AMImageHTMLTagFactory _amImageHTMLTagFactory = Mockito.mock(
		AMImageHTMLTagFactory.class);
	private final BlogsEntry _blogsEntry = Mockito.mock(BlogsEntry.class);
	private final DLAppLocalService _dlAppLocalService = Mockito.mock(
		DLAppLocalService.class);
	private final ExportImportContentProcessor<String>
		_exportImportContentProcessor = Mockito.mock(
			ExportImportContentProcessor.class);
	private final FileEntry _fileEntry1 = Mockito.mock(FileEntry.class);
	private final FileEntry _fileEntry2 = Mockito.mock(FileEntry.class);
	private final PortletDataContext _portletDataContext = Mockito.mock(
		PortletDataContext.class);

}