/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.MissingReference;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class ExportImportHelperUtilTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_liveGroup = GroupTestUtil.addGroup();
		_stagingGroup = GroupTestUtil.addGroup();

		_fileEntry = DLAppTestUtil.addFileEntry(
			_stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString() + ".txt",
			ServiceTestUtil.randomString(), true);

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

		Element rootElement = SAXReaderUtil.createElement("root");

		_portletDataContextExport.setExportDataRootElement(rootElement);

		_stagingPrivateLayout = LayoutTestUtil.addLayout(
			_stagingGroup.getGroupId(), ServiceTestUtil.randomString(), true);
		_stagingPublicLayout = LayoutTestUtil.addLayout(
			_stagingGroup.getGroupId(), ServiceTestUtil.randomString(), false);

		_portletDataContextExport.setPlid(_stagingPublicLayout.getPlid());

		_portletDataContextImport =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				_stagingGroup.getCompanyId(), _stagingGroup.getGroupId(),
				new HashMap<String, String[]>(),
				new CurrentUserIdStrategy(TestPropsValues.getUser()),
				testReaderWriter);

		_portletDataContextImport.setImportDataRootElement(rootElement);

		_livePublicLayout = LayoutTestUtil.addLayout(
			_liveGroup.getGroupId(), ServiceTestUtil.randomString(), false);

		_portletDataContextImport.setPlid(_livePublicLayout.getPlid());

		_portletDataContextImport.setSourceGroupId(_stagingGroup.getGroupId());

		rootElement.addElement("entry");

		_referrerStagedModel = JournalTestUtil.addArticle(
			_stagingGroup.getGroupId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString());
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_liveGroup);
		GroupLocalServiceUtil.deleteGroup(_stagingGroup);
	}

	@Test
	public void testDeleteTimestampFromDLReferenceUrls() throws Exception {
		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		String content = replaceParameters(
			getContent("dl_references_timestamp.txt"), _fileEntry);

		List<String> urls = replaceTimestampParameters(content);

		content = StringUtil.merge(urls,"\n");

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel,
			rootElement.element("entry"), content, true);

		String[] exportedUrls = content.split("\n");

		Assert.assertEquals(urls.size(), exportedUrls.length);

		for (int i = 0; i < urls.size(); i++) {
			String exportedUrl = exportedUrls[i];
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
		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		String content = replaceParameters(
			getContent("dl_references.txt"), _fileEntry);

		List<String> urls = getURLs(content);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel,
			rootElement.element("entry"), content, true);

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

		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel,
			rootElement.element("entry"), content, true);

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
				"_PRIVATE_USER_SERVLET_MAPPING"), "/en/");

		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel,
			rootElement.element("entry"), content, true);

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
		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		String content = replaceParameters(
			getContent("layout_links.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel,
			rootElement.element("entry"), content, true);

		StringBundler sb = new StringBundler(5);

		sb.append("[1@private-group@");
		sb.append(_stagingPrivateLayout.getUuid());
		sb.append(StringPool.AT);
		sb.append(_stagingPrivateLayout.getFriendlyURL());
		sb.append(StringPool.CLOSE_BRACKET);

		Assert.assertTrue(content.contains(sb.toString()));

		sb.setIndex(0);

		sb.append("[1@public@");
		sb.append(_stagingPublicLayout.getUuid());
		sb.append(StringPool.AT);
		sb.append(_stagingPublicLayout.getFriendlyURL());
		sb.append(StringPool.CLOSE_BRACKET);

		Assert.assertTrue(content.contains(sb.toString()));
	}

	@Test
	public void testImportDLReferences() throws Exception {
		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		Element entryElement = rootElement.element("entry");

		String content = replaceParameters(
			getContent("dl_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, entryElement,
			content, true);
		content = ExportImportHelperUtil.replaceImportContentReferences(
			_portletDataContextImport, entryElement, content, true);

		Assert.assertFalse(content.contains("[$dl-reference="));
	}

	@Test
	public void testImportLayoutReferences() throws Exception {
		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		Element entryElement = rootElement.element("entry");

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, entryElement,
			content, true);
		content = ExportImportHelperUtil.replaceImportContentReferences(
			_portletDataContextExport, entryElement, content, true);

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
		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		Element entryElement = rootElement.element("entry");

		String content = replaceParameters(
			getContent("layout_links.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, entryElement,
			content, true);

		String importedContent =
			ExportImportHelperUtil.replaceExportContentReferences(
				_portletDataContextExport, _referrerStagedModel, entryElement,
				content, true);

		Assert.assertEquals(importedContent, content);
	}

	@Test
	public void testValidateMissingReferences() throws Exception {
		String xml = replaceParameters(
			getContent("missing_references.txt"), _fileEntry);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		zipWriter.addEntry("/manifest.xml", xml);

		MissingReferences missingReferences =
			ExportImportHelperUtil.validateMissingReferences(
				TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
				new HashMap<String, String[]>(), zipWriter.getFile());

		Map<String, MissingReference> dependencyMissingReferences =
			missingReferences.getDependencyMissingReferences();

		Map<String, MissingReference> weakMissingReferences =
			missingReferences.getWeakMissingReferences();

		Assert.assertEquals(2, dependencyMissingReferences.size());
		Assert.assertEquals(1, weakMissingReferences.size());

		FileUtil.delete(zipWriter.getFile());
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
		Pattern pattern = Pattern.compile(
			"(?:href=|\\{|\\[[^$])(.*?)(?:>|\\}|[^$]\\]|Link\\]\\])");

		Matcher matcher = pattern.matcher(content);

		List<String> urls = new ArrayList<String>();

		while (matcher.find()) {
			String url = matcher.group(1);

			urls.add(url);
		}

		return urls;
	}

	protected String replaceParameters(String content, FileEntry fileEntry) {
		return StringUtil.replace(
			content,
			new String[] {
				"[$GROUP_FRIENDLY_URL$]", "[$GROUP_ID$]", "[$IMAGE_ID$]",
				"[$PATH_CONTEXT$]", "[$PATH_FRIENDLY_URL_PRIVATE_GROUP$]",
				"[$PATH_FRIENDLY_URL_PRIVATE_USER$]",
				"[$PATH_FRIENDLY_URL_PUBLIC$]", "[$TITLE$]", "[$UUID$]"
			},
			new String[] {
				_stagingGroup.getFriendlyURL(),
				String.valueOf(fileEntry.getGroupId()),
				String.valueOf(fileEntry.getFileEntryId()),
				PortalUtil.getPathContext(),
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
				fileEntry.getTitle(), fileEntry.getUuid()
			});
	}

	protected List<String> replaceTimestampParameters(String content)
		throws Exception {

		List<String> urls = new ArrayList<String>();

		for (String line : content.split("\n")) {
			if (line.contains("TIMESTAMP$]")) {
				urls.add(line);
			}
		}

		String timeStampParam = "t=" + ServiceTestUtil.randomLong();

		String params1 = timeStampParam + "&width=100&height=100";
		String params2 = "width=100&" + timeStampParam + "&height=100";
		String params3 = "width=100&height=100" + timeStampParam;

		List<String> outUrls = new ArrayList<String>();

		for (String url : urls) {
			outUrls.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$ONLYTIMESTAMP$]"},
					new String[] {"&" + params1, "?" + params1}));

			outUrls.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$ONLYTIMESTAMP$]"},
					new String[] {"&" + params2, "?" + params2}));

			outUrls.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$ONLYTIMESTAMP$]"},
					new String[] {"&" + params3, "?" + params3}));
		}

		return outUrls;
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
	private Group _liveGroup;
	private Layout _livePublicLayout;
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;
	private StagedModel _referrerStagedModel;
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