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

package com.liferay.adaptive.media.image.internal.exportimport.content.processor;

import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

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
public class AMImageHTMLExportImportContentProcessorTest {

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_amImageHTMLExportImportContentProcessor,
			"_amEmbeddedReferenceSetFactory", _amEmbeddedReferenceSetFactory);

		ReflectionTestUtil.setFieldValue(
			_amImageHTMLExportImportContentProcessor, "_amImageHTMLTagFactory",
			_amImageHTMLTagFactory);

		ReflectionTestUtil.setFieldValue(
			_amImageHTMLExportImportContentProcessor, "_dlAppLocalService",
			_dlAppLocalService);

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

		_setUpFileEntryToExport(_FILE_ENTRY_ID_1, _fileEntry1);
		_setUpFileEntryToImport(_FILE_ENTRY_ID_1, _fileEntry1);
		_setUpFileEntryToExport(_FILE_ENTRY_ID_2, _fileEntry2);
		_setUpFileEntryToImport(_FILE_ENTRY_ID_2, _fileEntry2);
	}

	@Test
	public void testExportContentWithNoReferencesDoesNotEscape()
		throws Exception {

		String content = StringPool.AMPERSAND;

		Assert.assertEquals(
			_amImageHTMLExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _stagedModel, content, false, false),
			_amImageHTMLExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, Mockito.mock(StagedModel.class),
					content, false, true));
	}

	@Test
	public void testExportContentWithReferenceDoesNotEscape() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("&<img data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\" src=\"");
		sb.append(RandomTestUtil.randomString());
		sb.append("\" />&");

		Assert.assertEquals(
			_amImageHTMLExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _stagedModel, sb.toString(), false,
					false),
			_amImageHTMLExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _stagedModel, sb.toString(), false,
					true));
	}

	@Test
	public void testExportContentWithStaticReferenceDoesNotEscape()
		throws Exception {

		String content =
			"&<picture data-fileentryid=\"" + _FILE_ENTRY_ID_1 +
				"\"></picture>&";

		Assert.assertEquals(
			_amImageHTMLExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _stagedModel, content, false, false),
			_amImageHTMLExportImportContentProcessor.
				replaceExportContentReferences(
					_portletDataContext, _stagedModel, content, false, true));
	}

	@Test
	public void testExportImportContentWithMultipleReferences()
		throws Exception {

		String prefix = RandomTestUtil.randomString();
		String infix = RandomTestUtil.randomString();
		String suffix = RandomTestUtil.randomString();

		String urlFileEntry1 = RandomTestUtil.randomString();
		String urlFileEntry2 = RandomTestUtil.randomString();

		StringBundler expectedSB = new StringBundler(13);

		expectedSB.append(prefix);
		expectedSB.append("<img src=\"");
		expectedSB.append(urlFileEntry1);
		expectedSB.append("\" data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_1);
		expectedSB.append("\" />");
		expectedSB.append(infix);
		expectedSB.append("<img src=\"");
		expectedSB.append(urlFileEntry2);
		expectedSB.append("\" data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_2);
		expectedSB.append("\" />");
		expectedSB.append(suffix);

		StringBundler sb = new StringBundler(13);

		sb.append(prefix);
		sb.append("<img data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\" src=\"");
		sb.append(urlFileEntry1);
		sb.append("\" />");
		sb.append(infix);
		sb.append("<img data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_2);
		sb.append("\" src=\"");
		sb.append(urlFileEntry2);
		sb.append("\" />");
		sb.append(suffix);

		Assert.assertEquals(
			expectedSB.toString(), _import(_export(sb.toString())));
	}

	@Test
	public void testExportImportContentWithMultipleStaticReferences()
		throws Exception {

		String prefix = RandomTestUtil.randomString();
		String infix = RandomTestUtil.randomString();
		String suffix = RandomTestUtil.randomString();

		String urlFileEntry1 = RandomTestUtil.randomString();
		String urlFileEntry2 = RandomTestUtil.randomString();

		StringBundler expectedSB = new StringBundler(17);

		expectedSB.append(prefix);
		expectedSB.append("<picture data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_1);
		expectedSB.append("\"><source /><img src=\"");
		expectedSB.append(urlFileEntry1);
		expectedSB.append("\" data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_1);
		expectedSB.append("\" /></picture>");
		expectedSB.append(infix);
		expectedSB.append("<picture data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_2);
		expectedSB.append("\"><source /><img src=\"");
		expectedSB.append(urlFileEntry2);
		expectedSB.append("\" data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_2);
		expectedSB.append("\" /></picture>");
		expectedSB.append(suffix);

		StringBundler sb = new StringBundler(17);

		sb.append(prefix);
		sb.append("<picture data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\"><img src=\"");
		sb.append(urlFileEntry1);
		sb.append("\" data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\" /></picture>");
		sb.append(infix);
		sb.append("<picture data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_2);
		sb.append("\"><img src=\"");
		sb.append(urlFileEntry2);
		sb.append("\" data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_2);
		sb.append("\" /></picture>");
		sb.append(suffix);

		Assert.assertEquals(
			expectedSB.toString(), _import(_export(sb.toString())));
	}

	@Test
	public void testExportImportContentWithNoReferences() throws Exception {
		String content = RandomTestUtil.randomString();

		String importedContent = _import(_export(content));

		Assert.assertEquals(content, importedContent);
	}

	@Test
	public void testExportImportContentWithReference() throws Exception {
		String prefix = RandomTestUtil.randomString();
		String suffix = RandomTestUtil.randomString();

		StringBundler expectedSB = new StringBundler(7);

		String urlFileEntry1 = RandomTestUtil.randomString();

		expectedSB.append(prefix);
		expectedSB.append("<img src=\"");
		expectedSB.append(urlFileEntry1);
		expectedSB.append("\" data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_1);
		expectedSB.append("\" />");
		expectedSB.append(suffix);

		StringBundler sb = new StringBundler(7);

		sb.append(prefix);
		sb.append("<img data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\" src=\"");
		sb.append(urlFileEntry1);
		sb.append("\" />");
		sb.append(suffix);

		Assert.assertEquals(
			expectedSB.toString(), _import(_export(sb.toString())));
	}

	@Test
	public void testExportImportContentWithReferenceContainingMoreAttributes()
		throws Exception {

		String prefix = RandomTestUtil.randomString();
		String suffix = RandomTestUtil.randomString();

		String urlFileEntry1 = RandomTestUtil.randomString();

		StringBundler expectedSB = new StringBundler(7);

		expectedSB.append(prefix);
		expectedSB.append("<img attr1=\"1\" attr2=\"2\" src=\"");
		expectedSB.append(urlFileEntry1);
		expectedSB.append("\" attr3=\"3\" data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_1);
		expectedSB.append("\" />");
		expectedSB.append(suffix);

		StringBundler sb = new StringBundler(7);

		sb.append(prefix);
		sb.append("<img attr1=\"1\" data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\" attr2=\"2\" src=\"");
		sb.append(urlFileEntry1);
		sb.append("\" attr3=\"3\"/>");
		sb.append(suffix);

		Assert.assertEquals(
			expectedSB.toString(), _import(_export(sb.toString())));
	}

	@Test
	public void testExportImportContentWithStaticReference() throws Exception {
		String prefix = RandomTestUtil.randomString();
		String suffix = RandomTestUtil.randomString();

		String urlFileEntry1 = RandomTestUtil.randomString();

		StringBundler expectedSB = new StringBundler(9);

		expectedSB.append(prefix);
		expectedSB.append("<picture data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_1);
		expectedSB.append("\"><source /><img src=\"");
		expectedSB.append(urlFileEntry1);
		expectedSB.append("\" data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_1);
		expectedSB.append("\" /></picture>");
		expectedSB.append(suffix);

		StringBundler sb = new StringBundler(9);

		sb.append(prefix);
		sb.append("<picture data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\"><img src=\"");
		sb.append(urlFileEntry1);
		sb.append("\" data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\" /></picture>");
		sb.append(suffix);

		Assert.assertEquals(
			expectedSB.toString(), _import(_export(sb.toString())));
	}

	@Test
	public void testExportImportContentWithStaticReferenceContainingImageWithAttributes()
		throws Exception {

		String urlFileEntry1 = RandomTestUtil.randomString();

		StringBundler expectedSB = new StringBundler(7);

		expectedSB.append("<picture data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_1);
		expectedSB.append("\"><source /><img src=\"");
		expectedSB.append(urlFileEntry1);
		expectedSB.append("\" class=\"pretty\" data-fileentryid=\"");
		expectedSB.append(_FILE_ENTRY_ID_1);
		expectedSB.append("\" /></picture>");

		StringBundler sb = new StringBundler(7);

		sb.append("<picture data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\"><img src=\"");
		sb.append(urlFileEntry1);
		sb.append("\" class=\"pretty\" data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\" /></picture>");

		Assert.assertEquals(
			expectedSB.toString(), _import(_export(sb.toString())));
	}

	@Test
	public void testImportContentIgnoresInvalidReferences() throws Exception {
		Mockito.doThrow(
			PortalException.class
		).when(
			_dlAppLocalService
		).getFileEntry(
			_FILE_ENTRY_ID_1
		);

		String content =
			"<img export-import-path=\"PATH_" + _FILE_ENTRY_ID_1 + "\" />";

		Assert.assertEquals(
			content,
			_amImageHTMLExportImportContentProcessor.
				replaceImportContentReferences(
					_portletDataContext, _stagedModel, content));
	}

	@Test
	public void testImportContentIgnoresInvalidStaticReferences()
		throws Exception {

		String content = "<picture export-import-path=\"#@¢∞\"></picture>";

		Assert.assertEquals(
			content,
			_amImageHTMLExportImportContentProcessor.
				replaceImportContentReferences(
					_portletDataContext, _stagedModel, content));
	}

	@Test
	public void testImportContentIgnoresReferencesWithMissingPaths()
		throws Exception {

		String content = "<img export-import-path=\"#@¢∞\" />";

		Assert.assertEquals(
			content,
			_amImageHTMLExportImportContentProcessor.
				replaceImportContentReferences(
					_portletDataContext, _stagedModel, content));
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testValidateContentFailsWithInvalidReferences()
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("<img data-fileentryid=\"");
		sb.append(RandomTestUtil.randomLong());
		sb.append("\" src=\"PATH_");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\" />");

		_amImageHTMLExportImportContentProcessor.validateContentReferences(
			RandomTestUtil.randomLong(), sb.toString());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testValidateContentFailsWithInvalidStaticReferences()
		throws Exception {

		String content =
			"<picture data-fileentryid=\"" + RandomTestUtil.randomLong() +
				"\"></picture>";

		_amImageHTMLExportImportContentProcessor.validateContentReferences(
			RandomTestUtil.randomLong(), content);
	}

	@Test
	public void testValidateContentSucceedsWhenAllReferencesAreValid()
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("<img data-fileentryid=\"");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\" src=\"PATH_");
		sb.append(_FILE_ENTRY_ID_1);
		sb.append("\" />");

		_amImageHTMLExportImportContentProcessor.validateContentReferences(
			RandomTestUtil.randomLong(), sb.toString());
	}

	@Test
	public void testValidateContentSucceedsWhenAllStaticReferencesAreValid()
		throws Exception {

		Mockito.doReturn(
			_fileEntry1
		).when(
			_dlAppLocalService
		).getFileEntry(
			_FILE_ENTRY_ID_1
		);

		String content =
			"<picture data-fileentryid=\"" + _FILE_ENTRY_ID_1 + "\"></picture>";

		_amImageHTMLExportImportContentProcessor.validateContentReferences(
			RandomTestUtil.randomLong(), content);
	}

	private String _export(String content) throws Exception {
		return _amImageHTMLExportImportContentProcessor.
			replaceExportContentReferences(
				_portletDataContext, _stagedModel, content, false, false);
	}

	private String _import(String exportedContent) throws Exception {
		return _amImageHTMLExportImportContentProcessor.
			replaceImportContentReferences(
				_portletDataContext, _stagedModel, exportedContent);
	}

	private void _setUpFileEntryToExport(long fileEntryId, FileEntry fileEntry)
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

	private void _setUpFileEntryToImport(long fileEntryId, FileEntry fileEntry)
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

	private static final long _FILE_ENTRY_ID_1 = RandomTestUtil.randomLong();

	private static final long _FILE_ENTRY_ID_2 = RandomTestUtil.randomLong();

	private final AMEmbeddedReferenceSet _amEmbeddedReferenceSet = Mockito.mock(
		AMEmbeddedReferenceSet.class);
	private final AMEmbeddedReferenceSetFactory _amEmbeddedReferenceSetFactory =
		Mockito.mock(AMEmbeddedReferenceSetFactory.class);
	private final AMImageHTMLExportImportContentProcessor
		_amImageHTMLExportImportContentProcessor =
			new AMImageHTMLExportImportContentProcessor();
	private final AMImageHTMLTagFactory _amImageHTMLTagFactory = Mockito.mock(
		AMImageHTMLTagFactory.class);
	private final DLAppLocalService _dlAppLocalService = Mockito.mock(
		DLAppLocalService.class);
	private final FileEntry _fileEntry1 = Mockito.mock(FileEntry.class);
	private final FileEntry _fileEntry2 = Mockito.mock(FileEntry.class);
	private final PortletDataContext _portletDataContext = Mockito.mock(
		PortletDataContext.class);
	private final StagedModel _stagedModel = Mockito.mock(StagedModel.class);

}