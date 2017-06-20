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

package com.liferay.exportimport.internal.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.ExportImportContentProcessorRegistryUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.test.util.TestReaderWriter;
import com.liferay.exportimport.test.util.TestUserIdStrategy;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Bowerman
 */
@RunWith(Arquillian.class)
@Sync
public class DefaultExportImportContentProcessorTest {

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
				new HashMap<>(),
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
				new HashMap<>(), new TestUserIdStrategy(), testReaderWriter);

		_portletDataContextImport.setImportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		_portletDataContextExport.setMissingReferencesElement(
			missingReferencesElement);

		_portletDataContextImport.setMissingReferencesElement(
			missingReferencesElement);

		_livePrivateLayout = LayoutTestUtil.addLayout(_liveGroup, true);
		_livePublicLayout = LayoutTestUtil.addLayout(_liveGroup, false);

		Map<Long, Long> layoutPlids =
			(Map<Long, Long>)_portletDataContextImport.getNewPrimaryKeysMap(
				Layout.class);

		layoutPlids.put(
			_stagingPrivateLayout.getPlid(), _livePrivateLayout.getPlid());
		layoutPlids.put(
			_stagingPublicLayout.getPlid(), _livePublicLayout.getPlid());

		_portletDataContextImport.setPlid(_livePublicLayout.getPlid());

		_portletDataContextImport.setSourceGroupId(_stagingGroup.getGroupId());

		rootElement.addElement("entry");

		_referrerStagedModel = JournalTestUtil.addArticle(
			_stagingGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_exportImportContentProcessor =
			ExportImportContentProcessorRegistryUtil.
				getExportImportContentProcessor(String.class.getName());
	}

	@Test
	public void testDeleteTimestampFromDLReferenceURLs() throws Exception {
		String content = replaceParameters(
			getContent("dl_references.txt"), _fileEntry);

		List<String> urls = getURLs(content);

		String urlContent = StringUtil.merge(urls, StringPool.NEW_LINE);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, urlContent, true,
			true);

		String[] exportedURLs = content.split(StringPool.NEW_LINE);

		Assert.assertEquals(
			Arrays.toString(exportedURLs), urls.size(), exportedURLs.length);

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

		_exportImportContentProcessor.validateContentReferences(
			_stagingGroup.getGroupId(), content);

		List<String> urls = getURLs(content);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			true);

		for (String url : urls) {
			Assert.assertFalse(content.contains(url));
		}

		TestReaderWriter testReaderWriter =
			(TestReaderWriter)_portletDataContextExport.getZipWriter();

		List<String> entries = testReaderWriter.getEntries();

		_assertContainsReference(
			entries, DLFileEntryConstants.getClassName(),
			_fileEntry.getFileEntryId());

		List<String> binaryEntries = testReaderWriter.getBinaryEntries();

		_assertContainsBinary(
			binaryEntries, DLFileEntryConstants.getClassName(),
			_fileEntry.getFileEntryId());

		for (String entry : testReaderWriter.getEntries()) {
			if (entry.contains(DLFileEntryConstants.getClassName())) {
				Assert.assertTrue(
					content.contains("[$dl-reference=" + entry + "$]"));
			}
		}
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

		_exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, sb.toString(),
			true, true);
	}

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

		Portal originalPortal = ReflectionTestUtil.getAndSetFieldValue(
			_exportImportContentProcessor, "_portal", portalImpl);

		_oldLayoutFriendlyURLPrivateUserServletMapping =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

		setFinalStaticField(
			PropsValues.class.getField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			"/en");

		setFinalStaticField(
			_exportImportContentProcessor.getClass().getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			"/en/");

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		_exportImportContentProcessor.validateContentReferences(
			_stagingGroup.getGroupId(), content);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			true);

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
			_exportImportContentProcessor.getClass().getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING +
				StringPool.SLASH);

		portalUtil.setPortal(new PortalImpl());

		ReflectionTestUtil.setFieldValue(
			_exportImportContentProcessor, "_portal", originalPortal);
	}

	@Test
	public void testExportLayoutReferencesWithoutContext() throws Exception {
		_oldLayoutFriendlyURLPrivateUserServletMapping =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

		setFinalStaticField(
			PropsValues.class.getField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			"/en");

		setFinalStaticField(
			_exportImportContentProcessor.getClass().getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			"/en/");

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		_exportImportContentProcessor.validateContentReferences(
			_stagingGroup.getGroupId(), content);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			true);

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
			_exportImportContentProcessor.getClass().getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING +
				StringPool.SLASH);
	}

	@Test
	public void testExportLinksToLayouts() throws Exception {
		String content = replaceLinksToLayoutsParameters(
			getContent("layout_links.txt"), _stagingPrivateLayout,
			_stagingPublicLayout);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			true);

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

		Layout privateLayout = LayoutTestUtil.addLayout(group, true);
		Layout publicLayout = LayoutTestUtil.addLayout(group, false);

		PortletDataContext portletDataContextExport =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				group.getCompanyId(), group.getGroupId(), new HashMap<>(),
				new Date(System.currentTimeMillis() - Time.HOUR), new Date(),
				new TestReaderWriter());

		Element rootElement = SAXReaderUtil.createElement("root");

		portletDataContextExport.setExportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		portletDataContextExport.setMissingReferencesElement(
			missingReferencesElement);

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		String content = replaceLinksToLayoutsParameters(
			getContent("layout_links_user_group.txt"), privateLayout,
			publicLayout);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			portletDataContextExport, journalArticle, content, true, true);

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

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			true);

		_portletDataContextImport.setScopeGroupId(_fileEntry.getGroupId());

		content = _exportImportContentProcessor.replaceImportContentReferences(
			_portletDataContextImport, _referrerStagedModel, content);

		Assert.assertFalse(content.contains("[$dl-reference="));
	}

	@Test
	public void testImportLayoutReferences() throws Exception {
		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		_exportImportContentProcessor.validateContentReferences(
			_stagingGroup.getGroupId(), content);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			false);
		content = _exportImportContentProcessor.replaceImportContentReferences(
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

		String liveContent = replaceLinksToLayoutsParameters(
			getContent("layout_links.txt"), _livePrivateLayout,
			_livePublicLayout);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			true);

		String importedContent =
			_exportImportContentProcessor.replaceImportContentReferences(
				_portletDataContextImport, _referrerStagedModel, content);

		Assert.assertEquals(liveContent, importedContent);
	}

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

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			true);

		String importedContent =
			_exportImportContentProcessor.replaceImportContentReferences(
				_portletDataContextImport, _referrerStagedModel, content);

		Assert.assertEquals(expectedContent, importedContent);
	}

	@Test
	public void testInvalidLayoutReferencesCauseNoSuchLayoutException()
		throws Exception {

		PortalImpl portalImpl = new PortalImpl() {

			@Override
			public String getPathContext() {
				return "/de";
			}

		};

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portalImpl);

		String content = replaceParameters(
			getContent("invalid_layout_references.txt"), _fileEntry);

		String[] layoutReferences = StringUtil.split(
			content, StringPool.NEW_LINE);

		for (String layoutReference : layoutReferences) {
			if (!layoutReference.contains(PortalUtil.getPathContext())) {
				continue;
			}

			boolean noSuchLayoutExceptionThrown = false;

			try {
				_exportImportContentProcessor.validateContentReferences(
					_stagingGroup.getGroupId(), layoutReference);
			}
			catch (NoSuchLayoutException nsle) {
				noSuchLayoutExceptionThrown = true;
			}

			Assert.assertTrue(
				layoutReference + " was not flagged as invalid",
				noSuchLayoutExceptionThrown);
		}

		portalUtil.setPortal(new PortalImpl());
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
					new HashMap<>());

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
		Company company = CompanyLocalServiceUtil.fetchCompany(
			fileEntry.getCompanyId());

		content = StringUtil.replace(
			content,
			new String[] {
				"[$GROUP_FRIENDLY_URL$]", "[$GROUP_ID$]", "[$IMAGE_ID$]",
				"[$LIVE_GROUP_FRIENDLY_URL$]", "[$LIVE_GROUP_ID$]",
				"[$LIVE_PUBLIC_LAYOUT_FRIENDLY_URL$]", "[$PATH_CONTEXT$]",
				"[$PATH_FRIENDLY_URL_PRIVATE_GROUP$]",
				"[$PATH_FRIENDLY_URL_PRIVATE_USER$]",
				"[$PATH_FRIENDLY_URL_PUBLIC$]",
				"[$PRIVATE_LAYOUT_FRIENDLY_URL$]",
				"[$PUBLIC_LAYOUT_FRIENDLY_URL$]", "[$TITLE$]", "[$UUID$]",
				"[$WEB_ID$]"
			},
			new String[] {
				_stagingGroup.getFriendlyURL(),
				String.valueOf(fileEntry.getGroupId()),
				String.valueOf(fileEntry.getFileEntryId()),
				_liveGroup.getFriendlyURL(),
				String.valueOf(_liveGroup.getGroupId()),
				_livePublicLayout.getFriendlyURL(), PortalUtil.getPathContext(),
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
				_stagingPrivateLayout.getFriendlyURL(),
				_stagingPublicLayout.getFriendlyURL(), fileEntry.getTitle(),
				fileEntry.getUuid(), company.getWebId()
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

	private void _assertContainsBinary(
		List<String> entries, String className, long classPK) {

		Pattern pattern = Pattern.compile(
			String.format("/%s/%d/\\d+\\.\\d+$", className, classPK));

		Stream<String> entriesStream = entries.stream();

		Assert.assertTrue(
			String.format(
				"%s does not contain a binary entry for %s with primary key %s",
				entries.toString(), className, classPK),
			entriesStream.anyMatch(pattern.asPredicate()));
	}

	private void _assertContainsReference(
		List<String> entries, String className, long classPK) {

		String expected = String.format("/%s/%d.xml", className, classPK);

		Stream<String> entriesStream = entries.stream();

		Assert.assertTrue(
			String.format(
				"%s does not contain an entry for %s with primary key %s",
				entries.toString(), className, classPK),
			entriesStream.anyMatch(entry -> entry.endsWith(expected)));
	}

	private static String _oldLayoutFriendlyURLPrivateUserServletMapping;

	private ExportImportContentProcessor<String> _exportImportContentProcessor;
	private FileEntry _fileEntry;

	@DeleteAfterTestRun
	private Group _liveGroup;

	private Layout _livePrivateLayout;
	private Layout _livePublicLayout;
	private final Pattern _pattern = Pattern.compile("href=|\\{|\\[");
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;
	private StagedModel _referrerStagedModel;

	@DeleteAfterTestRun
	private Group _stagingGroup;

	private Layout _stagingPrivateLayout;
	private Layout _stagingPublicLayout;

}