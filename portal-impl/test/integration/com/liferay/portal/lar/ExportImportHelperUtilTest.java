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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.MissingReference;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
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
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Zsolt Berentey
 * @author Peter Borkuti
 */
@Sync
public class ExportImportHelperUtilTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();
		_stagingGroup = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_stagingGroup.getGroupId(), TestPropsValues.getUserId());

		_fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(), serviceContext);

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

		_stagingLayout = LayoutTestUtil.addLayout(_stagingGroup);

		_portletDataContextExport.setPlid(_stagingLayout.getPlid());

		_portletDataContextImport =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				_liveGroup.getCompanyId(), _liveGroup.getGroupId(),
				new HashMap<String, String[]>(),
				new CurrentUserIdStrategy(TestPropsValues.getUser()),
				testReaderWriter);

		_portletDataContextImport.setImportDataRootElement(rootElement);

		_liveLayout = LayoutTestUtil.addLayout(_liveGroup);

		_portletDataContextImport.setPlid(_liveLayout.getPlid());

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
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING));
		Assert.assertFalse(content.contains(_stagingGroup.getFriendlyURL()));
		Assert.assertFalse(content.contains(PortalUtil.getPathContext()));
		Assert.assertFalse(content.contains("/en/en"));

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testExportLayoutReferencesWithoutContext() throws Exception {
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
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING));
		Assert.assertFalse(content.contains(_stagingGroup.getFriendlyURL()));
		Assert.assertFalse(content.contains("/en/en"));
	}

	@Test
	public void testExportLinksToLayouts() throws Exception {
		String content = replaceLinksToLayoutsParameters(
			getContent("layout_links.txt"), _stagingLayout);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true);

		assertLinksToLayouts(content, _stagingLayout, 0);
		assertLinksToLayouts(
			content, _stagingLayout, _stagingLayout.getGroupId());
	}

	@Test
	public void testExportLinksToUserLayouts() throws Exception {
		User user = TestPropsValues.getUser();

		Group group = user.getGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

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
			getContent("layout_links_user_group.txt"), layout);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			portletDataContextExport, journalArticle, content, true);

		assertLinksToLayouts(content, layout, 0);
		assertLinksToLayouts(content, layout, layout.getGroupId());
	}

	@Test
	public void testGetSelectedLayoutsJSONSelectAllLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_stagingGroup);

		Layout childLayout = LayoutTestUtil.addLayout(
			_stagingGroup, layout.getPlid());

		long[] selectedLayoutIds = new long[] {
			layout.getLayoutId(), childLayout.getLayoutId()};

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(),
				StringUtil.merge(selectedLayoutIds));

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			selectedLayoutsJSON);

		Assert.assertEquals(1, selectedLayoutsJSONArray.length());

		JSONObject layoutJSONObject = selectedLayoutsJSONArray.getJSONObject(0);

		Assert.assertTrue(layoutJSONObject.getBoolean("includeChildren"));
		Assert.assertEquals(layout.getPlid(), layoutJSONObject.getLong("plid"));
	}

	@Test
	public void testGetSelectedLayoutsJSONSelectChildLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_stagingGroup);

		Layout childLayout = LayoutTestUtil.addLayout(
			_stagingGroup, layout.getPlid());

		long[] selectedLayoutIds = new long[] {childLayout.getLayoutId()};

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(),
				StringUtil.merge(selectedLayoutIds));

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			selectedLayoutsJSON);

		Assert.assertEquals(1, selectedLayoutsJSONArray.length());

		JSONObject layoutJSONObject = selectedLayoutsJSONArray.getJSONObject(0);

		Assert.assertTrue(layoutJSONObject.getBoolean("includeChildren"));
		Assert.assertEquals(
			childLayout.getPlid(), layoutJSONObject.getLong("plid"));
	}

	@Test
	public void testGetSelectedLayoutsJSONSelectNoLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_stagingGroup);

		LayoutTestUtil.addLayout(_stagingGroup, layout.getPlid());

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(), StringUtil.merge(new long[0]));

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			selectedLayoutsJSON);

		Assert.assertEquals(0, selectedLayoutsJSONArray.length());
	}

	@Test
	public void testGetSelectedLayoutsJSONSelectParentLayout()
		throws Exception {

		Layout layout = LayoutTestUtil.addLayout(_stagingGroup);

		LayoutTestUtil.addLayout(
			_stagingGroup.getGroupId(), "Child Layout", layout.getPlid());

		long[] selectedLayoutIds = new long[] {layout.getLayoutId()};

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(),
				StringUtil.merge(selectedLayoutIds));

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			selectedLayoutsJSON);

		Assert.assertEquals(1, selectedLayoutsJSONArray.length());

		JSONObject layoutJSONObject = selectedLayoutsJSONArray.getJSONObject(0);

		Assert.assertFalse(layoutJSONObject.getBoolean("includeChildren"));
		Assert.assertEquals(layout.getPlid(), layoutJSONObject.getLong("plid"));
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
			getContent("layout_links.txt"), _stagingLayout);

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
		LayoutTestUtil.addLayout(_liveGroup);

		exportImportLayouts();

		Layout importedLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				_stagingLayout.getUuid(), _liveGroup.getGroupId());

		Map<Long, Long> layoutPlids =
			(Map<Long, Long>)_portletDataContextImport.getNewPrimaryKeysMap(
				Layout.class);

		layoutPlids.put(_stagingLayout.getPlid(), importedLayout.getPlid());

		String content = getContent("layout_links_ids_replacement.txt");

		String expectedContent = replaceLinksToLayoutsParameters(
			content, importedLayout);

		content = replaceLinksToLayoutsParameters(content, _stagingLayout);

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

		Assert.assertEquals(2, dependencyMissingReferences.size());
		Assert.assertEquals(1, weakMissingReferences.size());

		FileUtil.delete(zipWriter.getFile());

		zipReader.close();
	}

	protected void assertLinksToLayouts(
		String content, Layout layout, long groupId) {

		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);
		sb.append(layout.getLayoutId());
		sb.append(CharPool.AT);

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		sb.append(CharPool.AT);
		sb.append(layout.getPlid());

		if (group != null) {
			sb.append(CharPool.AT);
			sb.append(String.valueOf(groupId));
		}

		sb.append(StringPool.CLOSE_BRACKET);

		Assert.assertTrue(content.contains(sb.toString()));
	}

	protected void exportImportLayouts() throws Exception {
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			_stagingGroup.getGroupId());

		File larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(
			_stagingGroup.getGroupId(),
			ExportImportHelperUtil.getLayoutIds(layouts),
			new HashMap<String, String[]>(), null, null);

		LayoutLocalServiceUtil.importLayouts(
			TestPropsValues.getUserId(), _liveGroup.getGroupId(),
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

		List<String> urls = new ArrayList<>();

		for (String line : lines) {
			matcher.reset(line);

			if (matcher.find()) {
				urls.add(line);
			}
		}

		return urls;
	}

	protected String replaceLinksToLayoutsParameters(
		String content, Layout layout) {

		return StringUtil.replace(
			content,
			new String[] {
				"[$GROUP_ID$]", "[$LAYOUT_ID$]"
			},
			new String[] {
				String.valueOf(layout.getGroupId()),
				String.valueOf(layout.getLayoutId())
			});
	}

	protected String replaceParameters(String content, FileEntry fileEntry) {
		content = StringUtil.replace(
			content,
			new String[] {
				"[$GROUP_FRIENDLY_URL$]", "[$GROUP_ID$]", "[$IMAGE_ID$]",
				"[$LIVE_GROUP_ID$]", "[$PATH_CONTEXT$]",
				"[$PATH_FRIENDLY_URL_PUBLIC$]", "[$TITLE$]", "[$UUID$]"
			},
			new String[] {
				_stagingGroup.getFriendlyURL(),
				String.valueOf(fileEntry.getGroupId()),
				String.valueOf(fileEntry.getFileEntryId()),
				String.valueOf(fileEntry.getGroupId()),
				PortalUtil.getPathContext(),
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

		List<String> outURLs = new ArrayList<>();

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

	private FileEntry _fileEntry;

	@DeleteAfterTestRun
	private Group _liveGroup;

	private Layout _liveLayout;
	private final Pattern _pattern = Pattern.compile("href=|\\{|\\[");
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;
	private StagedModel _referrerStagedModel;

	@DeleteAfterTestRun
	private Group _stagingGroup;

	private Layout _stagingLayout;

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
			return new ArrayList<>(_entries.keySet());
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
		public File getFile() {
			return null;
		}

		@Override
		public List<String> getFolderEntries(String path) {
			return null;
		}

		@Override
		public String getPath() {
			return StringPool.BLANK;
		}

		private final List<String> _binaryEntries = new ArrayList<>();
		private final Map<String, String> _entries = new HashMap<>();

	}

}