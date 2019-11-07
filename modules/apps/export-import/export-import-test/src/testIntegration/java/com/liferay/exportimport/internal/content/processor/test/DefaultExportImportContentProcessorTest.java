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
import com.liferay.document.library.kernel.service.DLAppHelperLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.ExportImportContentProcessorRegistryUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
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
import com.liferay.layout.test.util.LayoutFriendlyURLRandomizerBumper;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.VirtualLayoutConstants;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestDataConstants;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

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
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Bowerman
 * @author Gergely Mathe
 */
@RunWith(Arquillian.class)
public class DefaultExportImportContentProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		StringBundler sb = new StringBundler(3);

		sb.append("(&(content.processor.type=LayoutReferences)(objectClass=");
		sb.append(ExportImportContentProcessor.class.getName());
		sb.append("))");

		Filter filter = registry.getFilter(sb.toString());

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@Before
	public void setUp() throws Exception {
		_defaultLocale = LocaleUtil.getDefault();
		_nondefaultLocale = getNondefaultLocale();

		_externalGroup = GroupTestUtil.addGroup();

		_liveGroup = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(_liveGroup);

		_stagingGroup = _liveGroup.getStagingGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_stagingGroup.getGroupId(), TestPropsValues.getUserId());

		_fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			TestDataConstants.TEST_BYTE_ARRAY, serviceContext);

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

		_stagingPrivateLayout = addMultiLocaleLayout(_stagingGroup, true);

		_stagingPublicLayout = addMultiLocaleLayout(_stagingGroup, false);

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

		_livePrivateLayout = addMultiLocaleLayout(_liveGroup, true);
		_livePublicLayout = addMultiLocaleLayout(_liveGroup, false);

		_externalPrivateLayout = addMultiLocaleLayout(_externalGroup, true);
		_externalPublicLayout = addMultiLocaleLayout(_externalGroup, false);

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

		_layoutReferencesExportImportContentProcessor =
			_serviceTracker.getService();
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

			Assert.assertFalse(exportedUrl, exportedUrl.matches("[?&]t="));

			if (url.contains("/documents/") && url.contains("?")) {
				Assert.assertTrue(
					exportedUrl, exportedUrl.contains("width=100&height=100"));
			}

			if (url.contains("/documents/") && url.contains("mustkeep")) {
				Assert.assertTrue(
					exportedUrl, exportedUrl.contains("mustkeep"));
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
			Assert.assertFalse(content, content.contains(url));
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

		int count = 0;

		for (String entry : testReaderWriter.getEntries()) {
			if (entry.contains(DLFileEntryConstants.getClassName())) {
				Assert.assertTrue(
					content,
					content.contains("[$dl-reference=" + entry + "$]"));

				count++;
			}
		}

		Assert.assertTrue(
			"There should be at least one file entry reference", count > 0);
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
			_layoutReferencesExportImportContentProcessor, "_portal",
			portalImpl);

		_oldLayoutFriendlyURLPrivateUserServletMapping =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

		setFinalStaticField(
			PropsValues.class.getField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			"/en");

		Class<?> clazz =
			_layoutReferencesExportImportContentProcessor.getClass();

		setFinalStaticField(
			clazz.getDeclaredField("_PRIVATE_USER_SERVLET_MAPPING"), "/en/");

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		_exportImportContentProcessor.validateContentReferences(
			_stagingGroup.getGroupId(), content);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			true);

		Assert.assertFalse(
			content,
			content.contains(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR));
		Assert.assertFalse(
			content,
			content.contains(GroupConstants.CONTROL_PANEL_FRIENDLY_URL));
		Assert.assertFalse(
			content,
			content.contains(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL));
		Assert.assertFalse(
			content,
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING));
		Assert.assertFalse(
			content,
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING));
		Assert.assertTrue(
			content,
			content.contains(
				"@data_handler_group_friendly_url@@" + _liveGroup.getUuid() +
					"@"));
		Assert.assertFalse(content, content.contains(_stagingGroup.getUuid()));
		Assert.assertFalse(
			content, content.contains(_stagingGroup.getFriendlyURL()));
		Assert.assertTrue(
			content, content.contains("@data_handler_path_context@/en@"));
		Assert.assertFalse(
			content, content.contains("@data_handler_path_context@/de@"));

		setFinalStaticField(
			PropsValues.class.getDeclaredField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			_oldLayoutFriendlyURLPrivateUserServletMapping);

		setFinalStaticField(
			clazz.getDeclaredField("_PRIVATE_USER_SERVLET_MAPPING"),
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING +
				StringPool.SLASH);

		portalUtil.setPortal(new PortalImpl());

		ReflectionTestUtil.setFieldValue(
			_layoutReferencesExportImportContentProcessor, "_portal",
			originalPortal);
	}

	@Test
	public void testExportLayoutReferencesWithoutContext() throws Exception {
		_oldLayoutFriendlyURLPrivateUserServletMapping =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

		setFinalStaticField(
			PropsValues.class.getField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			"/en");

		Class<?> clazz =
			_layoutReferencesExportImportContentProcessor.getClass();

		setFinalStaticField(
			clazz.getDeclaredField("_PRIVATE_USER_SERVLET_MAPPING"), "/en/");

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		_exportImportContentProcessor.validateContentReferences(
			_stagingGroup.getGroupId(), content);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			true);

		Assert.assertFalse(
			content,
			content.contains(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR));
		Assert.assertFalse(
			content,
			content.contains(GroupConstants.CONTROL_PANEL_FRIENDLY_URL));
		Assert.assertFalse(
			content,
			content.contains(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL));
		Assert.assertFalse(
			content,
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING));
		Assert.assertFalse(
			content,
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING));
		Assert.assertTrue(
			content,
			content.contains(
				"@data_handler_group_friendly_url@@" + _liveGroup.getUuid() +
					"@"));
		Assert.assertFalse(content, content.contains(_stagingGroup.getUuid()));
		Assert.assertFalse(
			content, content.contains(_stagingGroup.getFriendlyURL()));
		Assert.assertFalse(content, content.contains("/en/en"));

		setFinalStaticField(
			PropsValues.class.getDeclaredField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			_oldLayoutFriendlyURLPrivateUserServletMapping);

		setFinalStaticField(
			clazz.getDeclaredField("_PRIVATE_USER_SERVLET_MAPPING"),
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
	public void testExportLinksToURLsWithStopCharacters() throws Exception {
		String path = RandomTestUtil.randomString();

		String content = getContent("url_links.txt");

		content = content.replaceAll("PATH", path);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			true);

		_assertContainsPathWithStopCharacters(content, path);
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
	public void testImportDLReferences1() throws Exception {
		doTestImportDLReferences(false);
	}

	@Test
	public void testImportDLReferences2() throws Exception {
		doTestImportDLReferences(true);
	}

	@Test
	public void testImportDLReferencesFileEntryDeleted() throws Exception {
		DLAppHelperLocalServiceUtil.deleteFileEntry(_fileEntry);

		doTestImportDLReferences(false);
	}

	@Test
	public void testImportDLReferencesFileEntryInTrash1() throws Exception {
		DLAppHelperLocalServiceUtil.moveFileEntryToTrash(
			TestPropsValues.getUserId(), _fileEntry);

		doTestImportDLReferences(false);
	}

	@Test
	public void testImportDLReferencesFileEntryInTrash2() throws Exception {
		DLAppHelperLocalServiceUtil.moveFileEntryToTrash(
			TestPropsValues.getUserId(), _fileEntry);

		doTestImportDLReferences(true);
	}

	@Test
	public void testImportLayoutReferences() throws Exception {
		doTestImportLayoutReferences();
	}

	@Test
	public void testImportLayoutReferencesOnExternalGroupWithDifferentUUID()
		throws Exception {

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		_exportImportContentProcessor.validateContentReferences(
			_stagingGroup.getGroupId(), content);

		content = _exportImportContentProcessor.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, content, true,
			false);

		UUID randomUUID = UUID.randomUUID();

		String uuidString = randomUUID.toString();

		content = StringUtil.replace(
			content, _externalGroup.getUuid(), uuidString);

		content = _exportImportContentProcessor.replaceImportContentReferences(
			_portletDataContextImport, _referrerStagedModel, content);

		Assert.assertTrue(
			StringBundler.concat(
				"The imported content should contain the friendly URL of the ",
				"external group (\"", _externalGroup.getFriendlyURL(),
				"\"), but it does not:\n", content),
			content.contains(_externalGroup.getFriendlyURL()));

		Assert.assertFalse(
			"The imported content should not contain any @ variables, but it " +
				"does:\n" + content,
			content.contains(StringPool.AT));

		Assert.assertFalse(
			content, content.contains("data_handler_group_friendly_url"));
		Assert.assertFalse(
			content, content.contains("data_handler_path_context"));
		Assert.assertFalse(
			content,
			content.contains("data_handler_private_group_servlet_mapping"));
		Assert.assertFalse(
			content,
			content.contains("data_handler_private_user_servlet_mapping"));
		Assert.assertFalse(
			content, content.contains("data_handler_public_servlet_mapping"));
		Assert.assertFalse(
			content, content.contains("data_handler_site_admin_url"));
	}

	@Test
	public void testImportLayoutReferencesOnSameGroup() throws Exception {
		_portletDataContextImport.setGroupId(_stagingGroup.getGroupId());
		_portletDataContextImport.setScopeGroupId(_stagingGroup.getGroupId());

		doTestImportLayoutReferences();
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
	public void testImportLinksToLayoutsInLayoutSetPrototype()
		throws Exception {

		LayoutTestUtil.addLayout(_liveGroup, true);

		exportImportLayouts(true);

		Layout importedPrivateLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				_stagingPrivateLayout.getUuid(), _liveGroup.getGroupId(), true);

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)_portletDataContextImport.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		layouts.put(3L, importedPrivateLayout);

		String contentInFile = getContent(
			"layout_links_in_layoutset_prototype.txt");

		String content = replaceLinksToLayoutsParametersInLayoutSetPrototype(
			contentInFile);

		String importedContent =
			_exportImportContentProcessor.replaceImportContentReferences(
				_portletDataContextImport, _referrerStagedModel, content);

		Assert.assertTrue(
			"Template ID should have been replaced in the imported content",
			!importedContent.contains("template"));
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
			catch (ExportImportContentValidationException eicve) {
				Throwable cause = eicve.getCause();

				if ((cause instanceof NoSuchLayoutException) ||
					(eicve.getType() ==
						ExportImportContentValidationException.
							LAYOUT_GROUP_NOT_FOUND)) {

					noSuchLayoutExceptionThrown = true;
				}
			}

			Assert.assertTrue(
				layoutReference + " was not flagged as invalid",
				noSuchLayoutExceptionThrown);
		}

		portalUtil.setPortal(new PortalImpl());
	}

	protected Layout addMultiLocaleLayout(Group group, boolean privateLayout)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();
		Map<Locale, String> firendlyURLMap = new HashMap<>();

		for (Locale locale : new Locale[] {_defaultLocale, _nondefaultLocale}) {
			String name = RandomTestUtil.randomString(
				LayoutFriendlyURLRandomizerBumper.INSTANCE,
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE);

			String friendlyURL =
				StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

			nameMap.put(locale, name);

			firendlyURLMap.put(locale, friendlyURL);
		}

		return LayoutTestUtil.addLayout(
			group.getGroupId(), privateLayout, nameMap, firendlyURLMap);
	}

	protected void assertLinksToLayouts(
		String content, Layout layout, long groupId) {

		StringBundler sb = new StringBundler(9);

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

		Assert.assertTrue(content, content.contains(sb.toString()));
	}

	protected void doTestImportDLReferences(boolean deleteFileEntryBeforeImport)
		throws Exception {

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

		if (deleteFileEntryBeforeImport) {
			DLAppLocalServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());
		}

		content = _exportImportContentProcessor.replaceImportContentReferences(
			_portletDataContextImport, _referrerStagedModel, content);

		Assert.assertFalse(content, content.contains("[$dl-reference="));
	}

	protected void doTestImportLayoutReferences() throws Exception {
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
			content, content.contains("data_handler_group_friendly_url"));
		Assert.assertFalse(
			content, content.contains("data_handler_path_context"));
		Assert.assertFalse(
			content,
			content.contains("data_handler_private_group_servlet_mapping"));
		Assert.assertFalse(
			content,
			content.contains("data_handler_private_user_servlet_mapping"));
		Assert.assertFalse(
			content, content.contains("data_handler_public_servlet_mapping"));
		Assert.assertFalse(
			content, content.contains("data_handler_site_admin_url"));
	}

	protected String duplicateLinesWithParamNames(
		String content, String[] findParams, String[] addParams) {

		if (StringUtil.indexOfAny(content, findParams) <= -1) {
			return content;
		}

		List<String> urls = ListUtil.fromArray(StringUtil.splitLines(content));

		List<String> outURLs = new ArrayList<>();

		for (String url : urls) {
			outURLs.add(url);

			if (StringUtil.indexOfAny(url, findParams) > -1) {
				outURLs.add(StringUtil.replace(url, findParams, addParams));
			}
		}

		return StringUtil.merge(outURLs, StringPool.NEW_LINE);
	}

	protected void exportImportLayouts(boolean privateLayout) throws Exception {
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			_stagingGroup.getGroupId(), privateLayout);

		User user = TestPropsValues.getUser();

		Map<String, Serializable> publishLayoutLocalSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
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

	protected Locale getNondefaultLocale() throws Exception {
		for (Locale locale : _locales) {
			if (!locale.equals(_defaultLocale)) {
				return locale;
			}
		}

		throw new Exception("Could not find a non-default locale");
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

	protected String replaceExternalGroupFriendlyURLs(String content) {
		return duplicateLinesWithParamNames(
			content, _GROUP_FRIENDLY_URL_VARIABLES,
			_EXTERNAL_GROUP_FRIENDLY_URL_VARIABLES);
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

	protected String replaceLinksToLayoutsParametersInLayoutSetPrototype(
		String content) {

		String portalURL = TestPropsValues.PORTAL_URL;

		String portalURLPlaceholderToReplace = "[$PORTAL_URL$]";

		String templateIdPlaceholderToReplace = "[$ID$]";

		return StringUtil.replace(
			content,
			new String[] {
				portalURLPlaceholderToReplace, templateIdPlaceholderToReplace
			},
			new String[] {
				portalURL, String.valueOf(_stagingGroup.getGroupId())
			});
	}

	protected String replaceMultiLocaleLayoutFriendlyURLs(String content) {
		return duplicateLinesWithParamNames(
			content, _MULTI_LOCALE_LAYOUT_VARIABLES,
			_NON_DEFAULT_MULTI_LOCALE_LAYOUT_VARIABLES);
	}

	protected String replaceParameters(String content, FileEntry fileEntry) {
		Company company = CompanyLocalServiceUtil.fetchCompany(
			fileEntry.getCompanyId());

		content = replaceExternalGroupFriendlyURLs(content);
		content = replaceMultiLocaleLayoutFriendlyURLs(content);

		Map<Locale, String> livePublicLayoutFriendlyURLMap =
			_livePublicLayout.getFriendlyURLMap();
		Map<Locale, String> stagingPrivateLayoutFriendlyURLMap =
			_stagingPrivateLayout.getFriendlyURLMap();
		Map<Locale, String> stagingPublicLayoutFriendlyURLMap =
			_stagingPublicLayout.getFriendlyURLMap();

		content = StringUtil.replace(
			content,
			new String[] {
				"[$CANONICAL_URL_SEPARATOR$]", "[$CONTROL_PANEL_FRIENDLY_URL$]",
				"[$CONTROL_PANEL_LAYOUT_FRIENDLY_URL$]",
				"[$EXTERNAL_GROUP_FRIENDLY_URL$]",
				"[$EXTERNAL_PRIVATE_LAYOUT_FRIENDLY_URL$]",
				"[$EXTERNAL_PUBLIC_LAYOUT_FRIENDLY_URL$]",
				"[$GROUP_FRIENDLY_URL$]", "[$GROUP_ID$]", "[$IMAGE_ID$]",
				"[$LIVE_GROUP_FRIENDLY_URL$]", "[$LIVE_GROUP_ID$]",
				"[$LIVE_PUBLIC_LAYOUT_FRIENDLY_URL$]",
				"[$NON_DEFAULT_LIVE_PUBLIC_LAYOUT_FRIENDLY_URL$]",
				"[$NON_DEFAULT_PRIVATE_LAYOUT_FRIENDLY_URL$]",
				"[$NON_DEFAULT_PUBLIC_LAYOUT_FRIENDLY_URL$]",
				"[$PATH_CONTEXT$]", "[$PATH_FRIENDLY_URL_PRIVATE_GROUP$]",
				"[$PATH_FRIENDLY_URL_PRIVATE_USER$]",
				"[$PATH_FRIENDLY_URL_PUBLIC$]",
				"[$PRIVATE_LAYOUT_FRIENDLY_URL$]",
				"[$PUBLIC_LAYOUT_FRIENDLY_URL$]", "[$TITLE$]", "[$UUID$]",
				"[$WEB_ID$]"
			},
			new String[] {
				VirtualLayoutConstants.CANONICAL_URL_SEPARATOR,
				GroupConstants.CONTROL_PANEL_FRIENDLY_URL,
				PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL,
				_externalGroup.getFriendlyURL(),
				_externalPrivateLayout.getFriendlyURL(),
				_externalPublicLayout.getFriendlyURL(),
				_stagingGroup.getFriendlyURL(),
				String.valueOf(fileEntry.getGroupId()),
				String.valueOf(fileEntry.getFileEntryId()),
				_liveGroup.getFriendlyURL(),
				String.valueOf(_liveGroup.getGroupId()),
				_livePublicLayout.getFriendlyURL(),
				livePublicLayoutFriendlyURLMap.get(_nondefaultLocale),
				stagingPrivateLayoutFriendlyURLMap.get(_nondefaultLocale),
				stagingPublicLayoutFriendlyURLMap.get(_nondefaultLocale),
				PortalUtil.getPathContext(),
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
		List<String> urls = ListUtil.fromArray(StringUtil.splitLines(content));

		String timestampParameter = "t=123456789";

		String parameters1 = timestampParameter + "&width=100&height=100";
		String parameters2 = "width=100&" + timestampParameter + "&height=100";
		String parameters3 = "width=100&height=100&" + timestampParameter;
		String parameters4 = StringBundler.concat(
			timestampParameter, "?", timestampParameter,
			"&width=100&height=100");

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

	private void _assertContainsPathWithStopCharacters(
		String content, String path) {

		for (char stopChar : _LAYOUT_REFERENCE_STOP_CHARS) {
			StringBundler sb = new StringBundler(4);

			sb.append(path);
			sb.append(StringPool.SLASH);
			sb.append(stopChar);
			sb.append(StringPool.SLASH);

			Assert.assertTrue(
				String.format(
					"%s does not contain the path %s", content, sb.toString()),
				content.contains(sb.toString()));
		}
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

	private static final String[] _EXTERNAL_GROUP_FRIENDLY_URL_VARIABLES = {
		"[$EXTERNAL_GROUP_FRIENDLY_URL$]",
		"[$EXTERNAL_PRIVATE_LAYOUT_FRIENDLY_URL$]",
		"[$EXTERNAL_PUBLIC_LAYOUT_FRIENDLY_URL$]"
	};

	private static final String[] _GROUP_FRIENDLY_URL_VARIABLES = {
		"[$GROUP_FRIENDLY_URL$]", "[$PRIVATE_LAYOUT_FRIENDLY_URL$]",
		"[$PUBLIC_LAYOUT_FRIENDLY_URL$]"
	};

	private static final char[] _LAYOUT_REFERENCE_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.POUND, CharPool.QUESTION, CharPool.QUOTE,
		CharPool.SPACE
	};

	private static final String[] _MULTI_LOCALE_LAYOUT_VARIABLES = {
		"[$LIVE_PUBLIC_LAYOUT_FRIENDLY_URL$]",
		"[$PRIVATE_LAYOUT_FRIENDLY_URL$]", "[$PUBLIC_LAYOUT_FRIENDLY_URL$]"
	};

	private static final String[] _NON_DEFAULT_MULTI_LOCALE_LAYOUT_VARIABLES = {
		"[$NON_DEFAULT_LIVE_PUBLIC_LAYOUT_FRIENDLY_URL$]",
		"[$NON_DEFAULT_PRIVATE_LAYOUT_FRIENDLY_URL$]",
		"[$NON_DEFAULT_PUBLIC_LAYOUT_FRIENDLY_URL$]"
	};

	private static final Locale[] _locales = {
		LocaleUtil.US, LocaleUtil.GERMANY, LocaleUtil.SPAIN
	};
	private static String _oldLayoutFriendlyURLPrivateUserServletMapping;
	private static final Pattern _pattern = Pattern.compile("href=|\\{|\\[");
	private static ServiceTracker
		<ExportImportContentProcessor, ExportImportContentProcessor>
			_serviceTracker;

	private Locale _defaultLocale;
	private ExportImportContentProcessor<String> _exportImportContentProcessor;

	@DeleteAfterTestRun
	private Group _externalGroup;

	private Layout _externalPrivateLayout;
	private Layout _externalPublicLayout;
	private FileEntry _fileEntry;
	private ExportImportContentProcessor<String>
		_layoutReferencesExportImportContentProcessor;

	@DeleteAfterTestRun
	private Group _liveGroup;

	private Layout _livePrivateLayout;
	private Layout _livePublicLayout;
	private Locale _nondefaultLocale;
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;
	private StagedModel _referrerStagedModel;
	private Group _stagingGroup;
	private Layout _stagingPrivateLayout;
	private Layout _stagingPublicLayout;

}