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

package com.liferay.exportimport.resources.importer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.shrinkwrap.osgi.api.BndProjectBuilder;

import java.io.File;

import java.net.URL;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ArchiveAsset;
import org.jboss.shrinkwrap.api.asset.UrlAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class ResourcesImporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Deployment
	public static JavaArchive create() {
		BndProjectBuilder bndProjectBuilder = ShrinkWrap.create(
			BndProjectBuilder.class);

		bndProjectBuilder.setBndFile(new File("bnd.bnd"));

		bndProjectBuilder.generateManifest(true);

		JavaArchive javaArchive = bndProjectBuilder.as(JavaArchive.class);

		javaArchive.add(
			new ArchiveAsset(buildTestWebArchive(), ZipExporter.class),
			"com/liferay/exportimport/resources/importer/test/dependencies" +
				"/test.war");

		return javaArchive;
	}

	@Before
	public void setUp() throws Exception {
		Bundle testBundle = FrameworkUtil.getBundle(
			ResourcesImporterTest.class);

		_bundleContext = testBundle.getBundleContext();

		URL warURL = ResourcesImporterTest.class.getResource(
			"dependencies/test.war");

		warURL = new URL(
			warURL.toExternalForm() +
				"?Web-ContextPath=/test-resource-importer");

		URL bundleURL = new URL("webbundle", null, warURL.toString());

		_bundle = _bundleContext.installBundle(bundleURL.toString());

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("destination.name", "liferay/resources_importer");

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		ServiceRegistration<MessageListener> serviceRegistration =
			_bundleContext.registerService(
				MessageListener.class,
				new MessageListener() {

					public void receive(Message message)
						throws MessageListenerException {

						countDownLatch.countDown();
					}

				},
				properties);

		_bundle.start();

		countDownLatch.await(1, TimeUnit.MINUTES);

		serviceRegistration.unregister();
	}

	@After
	public void tearDown() throws Exception {
		if (_bundle != null) {
			_bundle.uninstall();
		}
	}

	@Test
	public void testResourcesImport() throws Exception {
		_importedGroup = GroupLocalServiceUtil.fetchGroup(
			TestPropsValues.getCompanyId(), "ResourcesImporterTest");

		Assert.assertNotNull(_importedGroup);

		_layoutPrototypes = getLayoutPrototypes();

		Assert.assertEquals(
			_layoutPrototypes.toString(), 2, _layoutPrototypes.size());

		validateLayouts(_importedGroup);

		validateJournal(_importedGroup);

		validateDocumentLibrary(_importedGroup);
	}

	protected static void addWebInfResource(
		WebArchive webArchive, String resourcePath) {

		URL resource = ResourcesImporterTest.class.getResource(
			_RESOURCES_BASE_PATH.concat(resourcePath));

		webArchive.addAsWebInfResource(new UrlAsset(resource), resourcePath);
	}

	protected static WebArchive buildTestWebArchive() {
		WebArchive webArchive = ShrinkWrap.create(WebArchive.class);

		// General

		addWebInfResource(webArchive, "liferay-plugin-package.properties");
		addWebInfResource(webArchive, "classes/resources-importer/assets.json");
		addWebInfResource(
			webArchive, "classes/resources-importer/sitemap.json");

		// Document library

		addWebInfResource(
			webArchive,
			"classes/resources-importer/document_library/documents" +
				"/company_logo.png");
		addWebInfResource(
			webArchive,
			"classes/resources-importer/document_library/documents" +
				"/Parent Folder/child_document.txt");

		// Journal

		addWebInfResource(
			webArchive,
			"classes/resources-importer/journal/articles/BASIC_WEB_CONTENT" +
				"/Basic Article.xml");

		StringBundler sb = new StringBundler(4);

		sb.append("classes/resources-importer/journal/articles");
		sb.append("/BASIC_WEB_CONTENT/Basic Web Content Parent Folder");
		sb.append("/Basic Web Content Child Folder");
		sb.append("/Basic Article in Child Folder.xml");

		addWebInfResource(webArchive, sb.toString());

		addWebInfResource(
			webArchive,
			"classes/resources-importer/journal/articles/BASIC_WEB_CONTENT" +
				"/Basic Web Content Parent Folder" +
					"/Basic Article in Parent Folder.xml");
		addWebInfResource(
			webArchive,
			"classes/resources-importer/journal/structures" +
				"/BASIC_WEB_CONTENT.json");
		addWebInfResource(
			webArchive,
			"classes/resources-importer/journal/templates/BASIC_WEB_CONTENT" +
				"/BASIC_WEB_CONTENT.ftl");
		addWebInfResource(
			webArchive,
			"classes/resources-importer/journal/articles/Child Template" +
				"/Child Template Article.xml");
		addWebInfResource(
			webArchive,
			"classes/resources-importer/journal/structures/Parent Structure" +
				"/Child Structure.json");
		addWebInfResource(
			webArchive,
			"classes/resources-importer/journal/templates/Child Structure" +
				"/Child Template.ftl");
		addWebInfResource(
			webArchive,
			"classes/resources-importer/journal/articles/Parent Template" +
				"/Parent Template Article.xml");
		addWebInfResource(
			webArchive,
			"classes/resources-importer/journal/structures/Parent " +
				"Structure.json");
		addWebInfResource(
			webArchive,
			"classes/resources-importer/journal/templates/Parent Structure" +
				"/Parent Template.ftl");

		// Page templates

		addWebInfResource(
			webArchive,
			"classes/resources-importer/templates/page/page_1.json");
		addWebInfResource(
			webArchive,
			"classes/resources-importer/templates/page/page_2.json");

		return webArchive;
	}

	protected List<LayoutPrototype> getLayoutPrototypes() {
		DynamicQuery dynamicQuery =
			LayoutPrototypeLocalServiceUtil.dynamicQuery();

		Criterion layout1Criterion = RestrictionsFactoryUtil.like(
			"name", "%Page 1%");
		Criterion layout2Criterion = RestrictionsFactoryUtil.like(
			"name", "%Page 2%");

		dynamicQuery.add(
			RestrictionsFactoryUtil.or(layout1Criterion, layout2Criterion));

		return LayoutPrototypeLocalServiceUtil.dynamicQuery(dynamicQuery);
	}

	protected void validateDocumentLibrary(Group importedGroup) {
		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getFileEntries(
				importedGroup.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(dlFileEntries.toString(), 1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			2,
			AssetTagLocalServiceUtil.getAssetEntryAssetTagsCount(
				assetEntry.getEntryId()));

		List<DLFolder> dlFolders = DLFolderLocalServiceUtil.getFolders(
			importedGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(dlFolders.toString(), 1, dlFolders.size());

		DLFolder dlFolder = dlFolders.get(0);

		int dlFileEntriesCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount(
				importedGroup.getGroupId(), dlFolder.getFolderId());

		Assert.assertEquals(1, dlFileEntriesCount);
	}

	protected void validateJournal(Group importedGroup) throws Exception {
		List<JournalArticle> journalArticles =
			JournalArticleLocalServiceUtil.getArticles(
				importedGroup.getGroupId());

		Assert.assertEquals(
			journalArticles.toString(), 5, journalArticles.size());

		int ddmStructuresCount =
			DDMStructureLocalServiceUtil.getStructuresCount(
				importedGroup.getGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class));

		Assert.assertEquals(3, ddmStructuresCount);

		int ddmTemplatesCount = DDMTemplateLocalServiceUtil.getTemplatesCount(
			importedGroup.getGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class));

		Assert.assertEquals(3, ddmTemplatesCount);

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.getArticle(
				importedGroup.getGroupId(), "BASIC-ARTICLE");

		Assert.assertTrue(journalArticle.isSmallImage());

		Map<Locale, String> descriptionMap = journalArticle.getDescriptionMap();

		Assert.assertFalse(descriptionMap.isEmpty());

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		List<AssetTag> assetTags = AssetTagLocalServiceUtil.getEntryTags(
			assetEntry.getEntryId());

		Assert.assertEquals(assetTags.toString(), 1, assetTags.size());

		JournalFolder parentJournalFolder =
			JournalFolderLocalServiceUtil.fetchFolder(
				importedGroup.getGroupId(), "Basic Web Content Parent Folder");

		JournalArticle parentJournalFolderJournalArticle =
			JournalArticleLocalServiceUtil.getArticle(
				importedGroup.getGroupId(), "BASIC-ARTICLE-IN-PARENT-FOLDER");

		Assert.assertNotNull(parentJournalFolder);

		Assert.assertEquals(
			parentJournalFolderJournalArticle.getFolder(), parentJournalFolder);

		JournalFolder childJournalFolder =
			JournalFolderLocalServiceUtil.fetchFolder(
				importedGroup.getGroupId(), "Basic Web Content Child Folder");

		Assert.assertEquals(
			parentJournalFolder, childJournalFolder.getParentFolder());

		JournalArticle childJournalFolderJournalArticle =
			JournalArticleLocalServiceUtil.getArticle(
				importedGroup.getGroupId(), "BASIC-ARTICLE-IN-CHILD-FOLDER");

		Assert.assertEquals(
			childJournalFolderJournalArticle.getFolder(), childJournalFolder);
	}

	protected void validateLayouts(Group importedGroup) throws Exception {
		long privateLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			importedGroup, true);

		Assert.assertEquals(1, privateLayoutsCount);

		long publicLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			importedGroup, false);

		Assert.assertEquals(9, publicLayoutsCount);

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			importedGroup.getGroupId(), false, "/home");

		Map<Locale, String> nameMap = layout.getNameMap();

		Assert.assertTrue(nameMap.containsValue("Bienvenue"));

		Assert.assertTrue(layout.isTypePortlet());

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

		Assert.assertEquals(portlets.toString(), 7, portlets.size());

		UnicodeProperties layoutTypeSettingsProperties =
			layout.getTypeSettingsProperties();

		String nestedColumnIds = layoutTypeSettingsProperties.get(
			LayoutTypePortletConstants.NESTED_COLUMN_IDS);

		Assert.assertTrue(
			(nestedColumnIds != null) && nestedColumnIds.contains("column-1") &&
			nestedColumnIds.contains("column-2"));

		layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			importedGroup.getGroupId(), false, "/layout-prototypes-page-1");

		Assert.assertTrue(layout.isLayoutPrototypeLinkActive());
		Assert.assertEquals(
			"371647ba-3649-4039-bfe6-ae32cf404737",
			layout.getLayoutPrototypeUuid());

		layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			importedGroup.getGroupId(), false, "/layout-prototypes-page-2");

		Assert.assertFalse(layout.isLayoutPrototypeLinkActive());
		Assert.assertEquals(
			"c98067d0-fc10-9556-7364-238d39693bc4",
			layout.getLayoutPrototypeUuid());

		layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			importedGroup.getGroupId(), false, "/url-page");

		Assert.assertTrue(layout.isTypeURL());

		layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			importedGroup.getGroupId(), false, "/hidden-page");

		Assert.assertTrue(layout.isHidden());
	}

	private static final String _RESOURCES_BASE_PATH = "dependencies/WEB-INF/";

	private Bundle _bundle;
	private BundleContext _bundleContext;

	@DeleteAfterTestRun
	private Group _importedGroup;

	@DeleteAfterTestRun
	private List<LayoutPrototype> _layoutPrototypes;

}