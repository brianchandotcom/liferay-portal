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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.MissingReference;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.io.File;
import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Zsolt Berentey
 * @author Peter Borkuti
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class ExportImportHelperUtilTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();
		_stagingGroup = GroupTestUtil.addGroup();

		_fileEntry = DLAppTestUtil.addFileEntry(
			_stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true);

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)_fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		dlFileEntry.setLargeImageId(dlFileEntry.getFileEntryId());

		DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		_portletDataContextExport =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				_stagingGroup.getCompanyId(), _stagingGroup.getGroupId(),
				new HashMap<String, String[]>(),
				new Date(System.currentTimeMillis() - Time.HOUR), new Date(),
				testReaderWriter);

		Document document = SAXReaderUtil.createDocument();

		Element manifestRootElement = document.addElement("root");

		manifestRootElement.addElement("header");

		testReaderWriter.addEntry("/manifest.xml", document.asXML());

		Element rootElement = SAXReaderUtil.createElement("root");

		_portletDataContextExport.setExportDataRootElement(rootElement);

		_stagingPrivateLayout = LayoutTestUtil.addLayout(
			_stagingGroup.getGroupId(), RandomTestUtil.randomString(), true);
		_stagingPublicLayout = LayoutTestUtil.addLayout(
			_stagingGroup.getGroupId(), RandomTestUtil.randomString(), false);

		_portletDataContextExport.setPlid(_stagingPublicLayout.getPlid());

		_portletDataContextImport =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				_liveGroup.getCompanyId(), _liveGroup.getGroupId(),
				new HashMap<String, String[]>(),
				new CurrentUserIdStrategy(TestPropsValues.getUser()),
				testReaderWriter);

		_portletDataContextImport.setImportDataRootElement(rootElement);

		_livePublicLayout = LayoutTestUtil.addLayout(
			_liveGroup.getGroupId(), RandomTestUtil.randomString(), false);

		_portletDataContextImport.setPlid(_livePublicLayout.getPlid());

		_portletDataContextImport.setSourceGroupId(_stagingGroup.getGroupId());

		rootElement.addElement("entry");

		_referrerStagedModel = JournalTestUtil.addArticle(
			_stagingGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testDeleteTimestampFromDLReferenceURLs() throws Exception {
		String content = replaceParameters(
			getContent("dl_references.txt"), _fileEntry);

		List<String> urls = getURLs(content);

		String urlContent = StringUtil.merge(urls, StringPool.NEW_LINE);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, urlContent, true);

		String[] exportedURLs = content.split(StringPool.NEW_LINE);

		Assert.assertEquals(urls.size(), exportedURLs.length);

		for (int i = 0; i < urls.size(); i++) {
			String exportedUrl = exportedURLs[i];
			String url = urls.get(i);

			Assert.assertFalse(exportedUrl.matches("[?&]t="));

			if (url.contains("/documents/") && url.contains("?")) {
				Assert.assertTrue(exportedUrl.contains("width=100&height=100"));
			}

			if (url.contains("/documents/") && url.contains("mustkeep")) {
				Assert.assertTrue(exportedUrl.contains("mustkeep"));
			}
		}
	}

	@Test
	public void testExportDLReferences() throws Exception {
		_portletDataContextExport.setZipWriter(new TestReaderWriter());

		String content = replaceParameters(
			getContent("dl_references.txt"), _fileEntry);

		List<String> urls = getURLs(content);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true);

		for (String url : urls) {
			Assert.assertFalse(content.contains(url));
		}

		TestReaderWriter testReaderWriter =
			(TestReaderWriter)_portletDataContextExport.getZipWriter();

		List<String> entries = testReaderWriter.getEntries();

		Assert.assertEquals(entries.size(), 1);

		List<String> binaryEntries = testReaderWriter.getBinaryEntries();

		Assert.assertEquals(binaryEntries.size(), entries.size());

		for (String entry : testReaderWriter.getEntries()) {
			Assert.assertTrue(
				content.contains("[$dl-reference=" + entry + "$]"));
		};
	}

	@Test
	public void testExportDLReferencesInvalidReference() throws Exception {
		_portletDataContextExport.setZipWriter(new TestReaderWriter());

		StringBundler sb = new StringBundler(9);

		sb.append("{{/documents/}}");
		sb.append(StringPool.NEW_LINE);
		sb.append("[[/documents/]]");
		sb.append(StringPool.NEW_LINE);
		sb.append("<a href=/documents/>Link</a>");
		sb.append(StringPool.NEW_LINE);
		sb.append("<a href=\"/documents/\">Link</a>");
		sb.append(StringPool.NEW_LINE);
		sb.append("<a href='/documents/'>Link</a>");

		ExportImportHelperUtil.replaceExportDLReferences(
			_portletDataContextExport, _referrerStagedModel, sb.toString(),
			true);
	}

	@Test
	public void testExportLayoutReferencesWithContext() throws Exception {
		PortalImpl portalImpl = spy(new PortalImpl());

		when(
			portalImpl.getPathContext()
		).thenReturn(
			"/de"
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portalImpl);

		_OLD_LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

		setFinalStaticField(
			PropsValues.class.getField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			"/en");

		setFinalStaticField(
			ExportImportHelperImpl.class.getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			"/en/");

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true);

		Assert.assertFalse(
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING));
		Assert.assertFalse(
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING));
		Assert.assertFalse(content.contains(_stagingGroup.getFriendlyURL()));
		Assert.assertFalse(content.contains(PortalUtil.getPathContext()));
		Assert.assertFalse(content.contains("/en/en"));

		setFinalStaticField(
			PropsValues.class.getDeclaredField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			_OLD_LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);

		setFinalStaticField(
			ExportImportHelperImpl.class.getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING +
				StringPool.SLASH);

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testExportLayoutReferencesWithoutContext() throws Exception {
		_OLD_LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

		setFinalStaticField(
			PropsValues.class.getField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			"/en");

		setFinalStaticField(
			ExportImportHelperImpl.class.getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			"/en/");

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true);

		Assert.assertFalse(
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING));
		Assert.assertFalse(
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING));
		Assert.assertFalse(content.contains(_stagingGroup.getFriendlyURL()));
		Assert.assertFalse(content.contains("/en/en"));

		setFinalStaticField(
			PropsValues.class.getDeclaredField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			_OLD_LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);

		setFinalStaticField(
			ExportImportHelperImpl.class.getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING +
				StringPool.SLASH);
	}

	@Test
	public void testExportLinksToLayouts() throws Exception {
		String content = replaceLinksToLayoutsParameters(
			getContent("layout_links.txt"), _stagingPrivateLayout,
			_stagingPublicLayout);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true);

		assertLinksToLayouts(content, _stagingPrivateLayout, 0);
		assertLinksToLayouts(
			content, _stagingPrivateLayout, _stagingPrivateLayout.getGroupId());
		assertLinksToLayouts(content, _stagingPublicLayout, 0);
		assertLinksToLayouts(
			content, _stagingPublicLayout, _stagingPublicLayout.getGroupId());
	}

	@Test
	public void testExportLinksToUserLayouts() throws Exception {
		User user = TestPropsValues.getUser();

		Group group = user.getGroup();

		Layout privateLayout = LayoutTestUtil.addLayout(
			group.getGroupId(), RandomTestUtil.randomString(), true);
		Layout publicLayout = LayoutTestUtil.addLayout(
			group.getGroupId(), RandomTestUtil.randomString(), false);

		PortletDataContext portletDataContextExport =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				group.getCompanyId(), group.getGroupId(),
				new HashMap<String, String[]>(),
				new Date(System.currentTimeMillis() - Time.HOUR), new Date(),
				new TestReaderWriter());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		Element rootElement = SAXReaderUtil.createElement("root");

		rootElement.addElement("entry");

		String content = replaceLinksToLayoutsParameters(
			getContent("layout_links_user_group.txt"), privateLayout,
			publicLayout);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			portletDataContextExport, journalArticle, content, true);

		assertLinksToLayouts(content, privateLayout, 0);
		assertLinksToLayouts(
			content, privateLayout, privateLayout.getGroupId());
		assertLinksToLayouts(content, publicLayout, 0);
		assertLinksToLayouts(content, publicLayout, publicLayout.getGroupId());
	}

	@Test
	public void testImportDLReferences() throws Exception {
		Element referrerStagedModelElement =
			_portletDataContextExport.getExportDataElement(
				_referrerStagedModel);

		String referrerStagedModelPath = ExportImportPathUtil.getModelPath(
			_referrerStagedModel);

		referrerStagedModelElement.addAttribute(
			"path", referrerStagedModelPath);

		String content = replaceParameters(
			getContent("dl_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true);

		_portletDataContextImport.setScopeGroupId(_fileEntry.getGroupId());

		content = ExportImportHelperUtil.replaceImportContentReferences(
			_portletDataContextImport, _referrerStagedModel, content);

		Assert.assertFalse(content.contains("[$dl-reference="));
	}

	@Test
	public void testImportLayoutReferences() throws Exception {
		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true);
		content = ExportImportHelperUtil.replaceImportContentReferences(
			_portletDataContextImport, _referrerStagedModel, content);

		Assert.assertFalse(
			content.contains("@data_handler_group_friendly_url@"));
		Assert.assertFalse(content.contains("@data_handler_path_context@"));
		Assert.assertFalse(
			content.contains("@data_handler_private_group_servlet_mapping@"));
		Assert.assertFalse(
			content.contains("@data_handler_private_user_servlet_mapping@"));
		Assert.assertFalse(
			content.contains("@data_handler_public_servlet_mapping@"));
	}

	@Test
	public void testImportLinksToLayouts() throws Exception {
		String content = replaceLinksToLayoutsParameters(
			getContent("layout_links.txt"), _stagingPrivateLayout,
			_stagingPublicLayout);

		String originalContent = content;

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true);

		String importedContent =
			ExportImportHelperUtil.replaceImportContentReferences(
				_portletDataContextImport, _referrerStagedModel, content);

		Assert.assertEquals(originalContent, importedContent);
	}

	@Test
	public void testImportLinksToLayoutsIdsReplacement() throws Exception {
		LayoutTestUtil.addLayout(
			_liveGroup.getGroupId(), RandomTestUtil.randomString(), true);
		LayoutTestUtil.addLayout(
			_liveGroup.getGroupId(), RandomTestUtil.randomString(), false);

		exportImportLayouts(true);
		exportImportLayouts(false);

		Layout importedPrivateLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				_stagingPrivateLayout.getUuid(), _liveGroup.getGroupId(), true);
		Layout importedPublicLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				_stagingPublicLayout.getUuid(), _liveGroup.getGroupId(), false);

		String content = getContent("layout_links_ids_replacement.txt");

		String expectedContent = replaceLinksToLayoutsParameters(
			content, importedPrivateLayout, importedPublicLayout);

		content = replaceLinksToLayoutsParameters(
			content, _stagingPrivateLayout, _stagingPublicLayout);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true);

		String importedContent =
			ExportImportHelperUtil.replaceImportContentReferences(
				_portletDataContextImport, _referrerStagedModel, content);

		Assert.assertEquals(expectedContent, importedContent);
	}

	@Test
	public void testValidateMissingReferences() throws Exception {
		String xml = replaceParameters(
			getContent("missing_references.txt"), _fileEntry);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		zipWriter.addEntry("/manifest.xml", xml);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
			zipWriter.getFile());

		_portletDataContextImport.setZipReader(zipReader);

		MissingReferences missingReferences =
			ExportImportHelperUtil.validateMissingReferences(
				_portletDataContextImport);

		Map<String, MissingReference> dependencyMissingReferences =
			missingReferences.getDependencyMissingReferences();

		Map<String, MissingReference> weakMissingReferences =
			missingReferences.getWeakMissingReferences();

		Assert.assertEquals(3, dependencyMissingReferences.size());
		Assert.assertEquals(1, weakMissingReferences.size());

		FileUtil.delete(zipWriter.getFile());
	}

	protected void assertLinksToLayouts(
		String content, Layout layout, long groupId) {

		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);
		sb.append(layout.getLayoutId());
		sb.append(CharPool.AT);

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		if (layout.isPrivateLayout()) {
			if (group == null) {
				sb.append("private");
			}
			else if (group.isUser()) {
				sb.append("private-user");
			}
			else {
				sb.append("private-group");
			}
		}
		else {
			sb.append("public");
		}

		sb.append(CharPool.AT);
		sb.append(layout.getUuid());
		sb.append(StringPool.AT);
		sb.append(layout.getFriendlyURL());

		if (group != null) {
			sb.append(CharPool.AT);
			sb.append(String.valueOf(groupId));
		}

		sb.append(StringPool.CLOSE_BRACKET);

		Assert.assertTrue(content.contains(sb.toString()));
	}

	protected void exportImportLayouts(boolean privateLayout) throws Exception {
		File larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(
			_stagingGroup.getGroupId(), privateLayout, null,
			new HashMap<String, String[]>(), null, null);

		LayoutLocalServiceUtil.importLayouts(
			TestPropsValues.getUserId(), _liveGroup.getGroupId(), privateLayout,
			new HashMap<String, String[]>(), larFile);
	}

	protected String getContent(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		Scanner scanner = new Scanner(inputStream);

		scanner.useDelimiter("\\Z");

		return scanner.next();
	}

	protected List<String> getURLs(String content) {
		Matcher matcher = _pattern.matcher(StringPool.BLANK);

		String[] lines = StringUtil.split(content, StringPool.NEW_LINE);

		List<String> urls = new ArrayList<String>();

		for (String line : lines) {
			matcher.reset(line);

			if (matcher.find()) {
				urls.add(line);
			}
		}

		return urls;
	}

	protected String replaceLinksToLayoutsParameters(
		String content, Layout privateLayout, Layout publicLayout) {

		return StringUtil.replace(
			content,
			new String[] {
				"[$GROUP_ID_PRIVATE$]", "[$GROUP_ID_PUBLIC$]",
				"[$LAYOUT_ID_PRIVATE$]", "[$LAYOUT_ID_PUBLIC$]"
			},
			new String[] {
				String.valueOf(privateLayout.getGroupId()),
				String.valueOf(publicLayout.getGroupId()),
				String.valueOf(privateLayout.getLayoutId()),
				String.valueOf(publicLayout.getLayoutId())
			});
	}

	protected String replaceParameters(String content, FileEntry fileEntry) {
		content = StringUtil.replace(
			content,
			new String[] {
				"[$GROUP_FRIENDLY_URL$]", "[$GROUP_ID$]", "[$IMAGE_ID$]",
				"[$LIVE_GROUP_ID$]", "[$PATH_CONTEXT$]",
				"[$PATH_FRIENDLY_URL_PRIVATE_GROUP$]",
				"[$PATH_FRIENDLY_URL_PRIVATE_USER$]",
				"[$PATH_FRIENDLY_URL_PUBLIC$]", "[$TITLE$]", "[$UUID$]"
			},
			new String[] {
				_stagingGroup.getFriendlyURL(),
				String.valueOf(fileEntry.getGroupId()),
				String.valueOf(fileEntry.getFileEntryId()),
				String.valueOf(fileEntry.getGroupId()),
				PortalUtil.getPathContext(),
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
				fileEntry.getTitle(), fileEntry.getUuid()
			});

		if (!content.contains("[$TIMESTAMP")) {
			return content;
		}

		return replaceTimestampParameters(content);
	}

	protected String replaceTimestampParameters(String content) {
		List<String> urls = ListUtil.toList(StringUtil.splitLines(content));

		String timestampParameter = "t=123456789";

		String parameters1 = timestampParameter + "&width=100&height=100";
		String parameters2 = "width=100&" + timestampParameter + "&height=100";
		String parameters3 = "width=100&height=100&" + timestampParameter;
		String parameters4 =
			timestampParameter + "?" + timestampParameter +
				"&width=100&height=100";

		List<String> outURLs = new ArrayList<String>();

		for (String url : urls) {
			if (!url.contains("[$TIMESTAMP")) {
				continue;
			}

			outURLs.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$TIMESTAMP_ONLY$]"},
					new String[] {"&" + parameters1, "?" + parameters1}));
			outURLs.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$TIMESTAMP_ONLY$]"},
					new String[] {"&" + parameters2, "?" + parameters2}));
			outURLs.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$TIMESTAMP_ONLY$]"},
					new String[] {"&" + parameters3, "?" + parameters3}));
			outURLs.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$TIMESTAMP_ONLY$]"},
					new String[] {StringPool.BLANK, "?" + parameters4}));
		}

		return StringUtil.merge(outURLs, StringPool.NEW_LINE);
	}

	protected void setFinalStaticField(Field field, Object newValue)
		throws Exception {

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, newValue);
	}

	private static String _OLD_LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private FileEntry _fileEntry;

	@DeleteAfterTestRun
	private Group _liveGroup;

	private Layout _livePublicLayout;
	private Pattern _pattern = Pattern.compile("href=|\\{|\\[");
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;
	private StagedModel _referrerStagedModel;

	@DeleteAfterTestRun
	private Group _stagingGroup;

	private Layout _stagingPrivateLayout;
	private Layout _stagingPublicLayout;

	private class TestReaderWriter implements ZipReader, ZipWriter {

		@Override
		public void addEntry(String name, byte[] bytes) {
			_binaryEntries.add(name);
		}

		@Override
		public void addEntry(String name, InputStream inputStream) {
			_binaryEntries.add(name);
		}

		@Override
		public void addEntry(String name, String s) {
			_entries.put(name, s);
		}

		@Override
		public void addEntry(String name, StringBuilder sb) {
			_entries.put(name, sb.toString());
		}

		@Override
		public void close() {
		}

		@Override
		public byte[] finish() {
			return new byte[0];
		}

		public List<String> getBinaryEntries() {
			return _binaryEntries;
		}

		@Override
		public List<String> getEntries() {
			return new ArrayList<String>(_entries.keySet());
		}

		@Override
		public byte[] getEntryAsByteArray(String name) {
			return null;
		}

		@Override
		public InputStream getEntryAsInputStream(String name) {
			return null;
		}

		@Override
		public String getEntryAsString(String name) {
			return _entries.get(name);
		}

		@Override
		public List<String> getFolderEntries(String path) {
			return null;
		}

		@Override
		public File getFile() {
			return null;
		}

		@Override
		public String getPath() {
			return StringPool.BLANK;
		}

		private List<String> _binaryEntries = new ArrayList<String>();
		private Map<String, String> _entries = new HashMap<String, String>();

	}

}