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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.lar.ExportImportHelperImpl;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Peter Borkuti
 */
@RunWith(Arquillian.class)
@Sync
public class ExportImportHelperUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

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
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);

		ThumbnailCapability thumbnailCapability =
			_fileEntry.getRepositoryCapability(ThumbnailCapability.class);

		_fileEntry = thumbnailCapability.setLargeImageId(
			_fileEntry, _fileEntry.getFileEntryId());

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

		_stagingPrivateLayout = LayoutTestUtil.addLayout(_stagingGroup, true);
		_stagingPublicLayout = LayoutTestUtil.addLayout(_stagingGroup, false);

		_portletDataContextExport.setPlid(_stagingPublicLayout.getPlid());

		_portletDataContextImport =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				_liveGroup.getCompanyId(), _liveGroup.getGroupId(),
				new HashMap<String, String[]>(), new TestUserIdStrategy(),
				testReaderWriter);

		_portletDataContextImport.setImportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		_portletDataContextImport.setMissingReferencesElement(
			missingReferencesElement);

		_livePublicLayout = LayoutTestUtil.addLayout(_liveGroup, false);

		_portletDataContextImport.setPlid(_livePublicLayout.getPlid());

		_portletDataContextImport.setSourceGroupId(_stagingGroup.getGroupId());

		rootElement.addElement("entry");

		_referrerStagedModel = JournalTestUtil.addArticle(
			_stagingGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testDataSiteLevelPortletsRank() throws Exception {
		List<Portlet> portlets =
			ExportImportHelperUtil.getDataSiteLevelPortlets(
				TestPropsValues.getCompanyId());

		Integer previousRank = null;

		for (Portlet portlet : portlets) {
			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			int actualRank = portletDataHandler.getRank();

			if (previousRank != null) {
				Assert.assertTrue(
					"Portlets should be in ascending order by rank",
					previousRank <= actualRank);
			}

			previousRank = actualRank;
		}
	}

	@Ignore
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

	@Ignore
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

		Assert.assertEquals(1, entries.size());

		List<String> binaryEntries = testReaderWriter.getBinaryEntries();

		Assert.assertEquals(binaryEntries.size(), entries.size());

		for (String entry : testReaderWriter.getEntries()) {
			Assert.assertTrue(
				content.contains("[$dl-reference=" + entry + "$]"));
		};
	}

	@Ignore
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

	@Ignore
	@Test
	public void testExportLayoutReferencesWithContext() throws Exception {
		PortalImpl portalImpl = new PortalImpl() {

			@Override
			public String getPathContext() {
				return "/de";
			}

		};

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portalImpl);

		_oldLayoutFriendlyURLPrivateUserServletMapping =
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
			_oldLayoutFriendlyURLPrivateUserServletMapping);

		setFinalStaticField(
			ExportImportHelperImpl.class.getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING +
				StringPool.SLASH);

		portalUtil.setPortal(new PortalImpl());
	}

	@Ignore
	@Test
	public void testExportLayoutReferencesWithoutContext() throws Exception {
		_oldLayoutFriendlyURLPrivateUserServletMapping =
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
			_oldLayoutFriendlyURLPrivateUserServletMapping);

		setFinalStaticField(
			ExportImportHelperImpl.class.getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING +
				StringPool.SLASH);
	}

	@Ignore
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

	@Ignore
	@Test
	public void testExportLinksToUserLayouts() throws Exception {
		User user = TestPropsValues.getUser();

		Group group = user.getGroup();

		Layout privateLayout = LayoutTestUtil.addLayout(group, true);
		Layout publicLayout = LayoutTestUtil.addLayout(group, false);

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
	public void testGetSelectedLayoutsJSONSelectAllLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_stagingGroup);

		Layout childLayout = LayoutTestUtil.addLayout(
			_stagingGroup, layout.getPlid());

		long[] selectedLayoutIds =
			new long[] {layout.getLayoutId(), childLayout.getLayoutId()};

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(), false,
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
				_stagingGroup.getGroupId(), false,
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
				_stagingGroup.getGroupId(), false,
				StringUtil.merge(new long[0]));

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
				_stagingGroup.getGroupId(), false,
				StringUtil.merge(selectedLayoutIds));

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			selectedLayoutsJSON);

		Assert.assertEquals(1, selectedLayoutsJSONArray.length());

		JSONObject layoutJSONObject = selectedLayoutsJSONArray.getJSONObject(0);

		Assert.assertFalse(layoutJSONObject.getBoolean("includeChildren"));
		Assert.assertEquals(layout.getPlid(), layoutJSONObject.getLong("plid"));
	}

	@Ignore
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

	@Ignore
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

	@Ignore
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

	@Ignore
	@Test
	public void testImportLinksToLayoutsIdsReplacement() throws Exception {
		LayoutTestUtil.addLayout(_liveGroup, true);
		LayoutTestUtil.addLayout(_liveGroup, false);

		exportImportLayouts(true);
		exportImportLayouts(false);

		Layout importedPrivateLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				_stagingPrivateLayout.getUuid(), _liveGroup.getGroupId(), true);
		Layout importedPublicLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				_stagingPublicLayout.getUuid(), _liveGroup.getGroupId(), false);

		Map<Long, Long> layoutPlids =
			(Map<Long, Long>)_portletDataContextImport.getNewPrimaryKeysMap(
				Layout.class);

		layoutPlids.put(
			_stagingPrivateLayout.getPlid(), importedPrivateLayout.getPlid());
		layoutPlids.put(
			_stagingPublicLayout.getPlid(), importedPublicLayout.getPlid());

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
		sb.append(layout.getPlid());

		if (group != null) {
			sb.append(CharPool.AT);
			sb.append(String.valueOf(groupId));
		}

		sb.append(StringPool.CLOSE_BRACKET);

		Assert.assertTrue(content.contains(sb.toString()));
	}

	protected void exportImportLayouts(boolean privateLayout) throws Exception {
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			_stagingGroup.getGroupId(), privateLayout);

		User user = TestPropsValues.getUser();

		Map<String, Serializable> publishLayoutLocalSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildPublishLayoutLocalSettingsMap(
					user, _stagingGroup.getGroupId(), _liveGroup.getGroupId(),
					privateLayout, ExportImportHelperUtil.getLayoutIds(layouts),
					new HashMap<String, String[]>());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_LOCAL,
					publishLayoutLocalSettingsMap);

		File larFile = ExportImportLocalServiceUtil.exportLayoutsAsFile(
			exportImportConfiguration);

		ExportImportLocalServiceUtil.importLayouts(
			exportImportConfiguration, larFile);
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

	private static String _oldLayoutFriendlyURLPrivateUserServletMapping;

	private FileEntry _fileEntry;

	@DeleteAfterTestRun
	private Group _liveGroup;

	private Layout _livePublicLayout;
	private final Pattern _pattern = Pattern.compile("href=|\\{|\\[");
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;
	private StagedModel _referrerStagedModel;

	@DeleteAfterTestRun
	private Group _stagingGroup;

	private Layout _stagingPrivateLayout;
	private Layout _stagingPublicLayout;

	private static class TestReaderWriter implements ZipReader, ZipWriter {

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
			return new InputStream() {

				@Override
				public int read() {
					return -1;
				}

			};
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

	private static class TestUserIdStrategy implements UserIdStrategy {

		@Override
		public long getUserId(String userUuid) {
			try {
				return TestPropsValues.getUserId();
			}
			catch (Exception e) {
				return 0;
			}
		}

	}

}