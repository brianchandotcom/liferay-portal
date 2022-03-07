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

package com.liferay.site.initializer.extender.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalService;
import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeEntry;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductSpecificationResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.io.InputStream;

import java.math.BigDecimal;

import java.util.List;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class BundleSiteInitializerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testInitialize() throws Exception {
		Bundle testBundle = FrameworkUtil.getBundle(
			BundleSiteInitializerTest.class);

		Bundle bundle = _installBundle(
			testBundle.getBundleContext(),
			"/com.liferay.site.initializer.extender.test.bundle.jar");

		bundle.start();

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				bundle.getSymbolicName());

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			siteInitializer.initialize(group.getGroupId());
			_assertAccounts(serviceContext);
			_assertAssetListEntries(group);
			_assertAssetVocabularies(group);
			_assertCommerceCatalogs(group);
			_assertCommerceChannel(group);
			_assertCommerceInventoryWarehouse(group);
			_assertCommerceSpecificationProducts(serviceContext);
			_assertCPDefinition(group);
			_assertCPInstanceProperties(group);
			_assertDDMStructure(group);
			_assertDDMTemplate(group);
			_assertDLFileEntry(group);
			_assertFragmentEntries(group);
			_assertJournalArticles(group);
			_assertLayoutPageTemplateEntry(group);
			_assertLayouts(group);
			_assertLayoutSets(group);
			_assertListTypeDefinitions(serviceContext);
			_assertObjectDefinitions(group, serviceContext);
			_assertWidgetTemplates(group);
			_assertPermissions(group);
			_assertRemoteApp(group);
			_assertSAPEntries(group);
			_assertSiteConfiguration(group.getGroupId());
			_assertSiteNavigationMenu(group);
			_assertStyleBookEntry(group);
			_assertUserRoles(group);
			_assertWorkflowDefinitions(group, serviceContext);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			GroupLocalServiceUtil.deleteGroup(group);

			// TODO We should not need to delete the object definition manually
			// because of DataGuardTestRule. However,
			// ObjectDefinitionLocalServiceImpl#deleteObjectDefinition checks
			// for PortalRunMode#isTestMode which is not returning true when the
			// DataGuardTestRule runs.

			ObjectDefinition objectDefinition1 =
				_objectDefinitionLocalService.fetchObjectDefinition(
					serviceContext.getCompanyId(), "C_TestObjectDefinition1");

			if (objectDefinition1 != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition1.getObjectDefinitionId());
			}

			ObjectDefinition objectDefinition2 =
				_objectDefinitionLocalService.fetchObjectDefinition(
					serviceContext.getCompanyId(), "C_TestObjectDefinition2");

			if (objectDefinition2 != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition2.getObjectDefinitionId());
			}

			ObjectDefinition objectDefinition3 =
				_objectDefinitionLocalService.fetchObjectDefinition(
					serviceContext.getCompanyId(), "C_TestObjectDefinition3");

			if (objectDefinition3 != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition3.getObjectDefinitionId());
			}

			bundle.uninstall();
		}
	}

	private void _assertAccounts(ServiceContext serviceContext)
		throws Exception {

		AccountResource.Builder accountResourceBuilder =
			_accountResourceFactory.create();

		AccountResource accountResource = accountResourceBuilder.user(
			serviceContext.fetchUser()
		).build();

		UserAccountResource.Builder userAccountResourceBuilder =
			_userAccountResourceFactory.create();

		UserAccountResource userAccountResource =
			userAccountResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		Account account1 = accountResource.getAccountByExternalReferenceCode(
			"TESTACC0001");

		Assert.assertNotNull(account1);
		Assert.assertEquals("Test Account 1", account1.getName());
		Assert.assertEquals("business", account1.getTypeAsString());

		_assertUserAccounts(account1.getId(), 1, userAccountResource);

		Account account2 = accountResource.getAccountByExternalReferenceCode(
			"TESTACC0002");

		Assert.assertNotNull(account2);
		Assert.assertEquals("Test Account 2", account2.getName());
		Assert.assertEquals("guest", account2.getTypeAsString());

		_assertUserAccounts(account2.getId(), 1, userAccountResource);

		Account account3 = accountResource.getAccountByExternalReferenceCode(
			"TESTACC0003");

		Assert.assertNotNull(account3);
		Assert.assertEquals("Test Account 3", account3.getName());
		Assert.assertEquals("person", account3.getTypeAsString());

		_assertUserAccounts(account3.getId(), 0, userAccountResource);
	}

	private void _assertAssetCategories(Group group) throws Exception {
		Group companyGroup = _groupLocalService.getCompanyGroup(
			group.getCompanyId());

		AssetCategory testAssetCategory1 =
			_assetCategoryLocalService.
				fetchAssetCategoryByExternalReferenceCode(
					companyGroup.getGroupId(), "TESTCAT0001");

		Assert.assertNotNull(testAssetCategory1);
		Assert.assertEquals(
			"Test Asset Category 1", testAssetCategory1.getName());

		AssetCategory testAssetCategory2 =
			_assetCategoryLocalService.fetchCategory(
				companyGroup.getGroupId(), testAssetCategory1.getCategoryId(),
				"Test Asset Category 2", testAssetCategory1.getVocabularyId());

		Assert.assertNotNull(testAssetCategory2);
		Assert.assertEquals(
			"TESTCAT0002", testAssetCategory2.getExternalReferenceCode());

		AssetCategory testAssetCategory3 =
			_assetCategoryLocalService.
				fetchAssetCategoryByExternalReferenceCode(
					group.getGroupId(), "TESTCAT0003");

		Assert.assertNotNull(testAssetCategory3);
		Assert.assertEquals(
			"Test Asset Category 3", testAssetCategory3.getName());

		AssetCategory testAssetCategory4 =
			_assetCategoryLocalService.fetchCategory(
				group.getGroupId(), testAssetCategory3.getCategoryId(),
				"Test Asset Category 4", testAssetCategory3.getVocabularyId());

		Assert.assertNotNull(testAssetCategory4);
		Assert.assertEquals(
			"TESTCAT0004", testAssetCategory4.getExternalReferenceCode());
	}

	private void _assertAssetListEntries(Group group) {
		List<AssetListEntry> assetListEntries =
			_assetListEntryLocalService.getAssetListEntries(group.getGroupId());

		Assert.assertTrue(assetListEntries.size() == 2);

		AssetListEntry assetListEntry1 = assetListEntries.get(0);

		Assert.assertEquals(
			"Test Asset List Entry 1", assetListEntry1.getTitle());
		Assert.assertEquals(
			"com.liferay.journal.model.JournalArticle",
			assetListEntry1.getAssetEntryType());

		AssetListEntry assetListEntry2 = assetListEntries.get(1);

		Assert.assertEquals(
			"Test Asset List Entry 2", assetListEntry2.getTitle());
		Assert.assertEquals(
			"com.liferay.journal.model.JournalArticle",
			assetListEntry2.getAssetEntryType());
	}

	private void _assertAssetVocabularies(Group group) throws Exception {
		Group companyGroup = _groupLocalService.getCompanyGroup(
			group.getCompanyId());

		AssetVocabulary testAssetVocabulary1 =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				companyGroup.getGroupId(), "Test Asset Vocabulary 1");

		Assert.assertNotNull(testAssetVocabulary1);
		Assert.assertEquals(
			"TESTVOC0001", testAssetVocabulary1.getExternalReferenceCode());

		AssetVocabulary testAssetVocabulary2 =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				group.getGroupId(), "Test Asset Vocabulary 2");

		Assert.assertNotNull(testAssetVocabulary2);
		Assert.assertEquals(
			"TESTVOC0002", testAssetVocabulary2.getExternalReferenceCode());

		_assertAssetCategories(group);
	}

	private void _assertCommerceCatalogs(Group group) throws Exception {
		CommerceCatalog commerceCatalog1 =
			_commerceCatalogLocalService.
				fetchCommerceCatalogByExternalReferenceCode(
					group.getCompanyId(), "TESTCATG0001");

		Assert.assertNotNull(commerceCatalog1);
		Assert.assertEquals(
			"Test Commerce Catalog 1", commerceCatalog1.getName());

		CommerceCatalog commerceCatalog2 =
			_commerceCatalogLocalService.
				fetchCommerceCatalogByExternalReferenceCode(
					group.getCompanyId(), "TESTCATG0002");

		Assert.assertNotNull(commerceCatalog2);
		Assert.assertEquals(
			"Test Commerce Catalog 2", commerceCatalog2.getName());

		_assertCPDefinition(group);
		_assertCPOption(group);
	}

	private void _assertCommerceChannel(Group group) throws Exception {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				group.getGroupId());

		Assert.assertNotNull(commerceChannel);
		Assert.assertEquals(
			"TESTVOC0001", commerceChannel.getExternalReferenceCode());
		Assert.assertEquals("Test Commerce Channel", commerceChannel.getName());
		Assert.assertEquals("site", commerceChannel.getType());

		_assertCommerceNotificationTemplate(commerceChannel, group);
	}

	private void _assertCommerceInventoryWarehouse(Group group) {
		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseLocalService.
				fetchCommerceInventoryWarehouseByExternalReferenceCode(
					group.getCompanyId(), "TESTWARE0001");

		Assert.assertNotNull(commerceInventoryWarehouse);
		Assert.assertEquals(
			"Test Commerce Warehouse", commerceInventoryWarehouse.getName());
	}

	private void _assertCommerceNotificationTemplate(
			CommerceChannel commerceChannel, Group group)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				group.getCompanyId(), "C_TestObjectDefinition1");

		Assert.assertNotNull(objectDefinition);

		List<CommerceNotificationTemplate> commerceNotificationTemplates =
			_commerceNotificationTemplateLocalService.
				getCommerceNotificationTemplates(
					commerceChannel.getGroupId(),
					"com.liferay.object.model.ObjectDefinition#" +
						objectDefinition.getObjectDefinitionId() + "#create",
					true);

		CommerceNotificationTemplate commerceNotificationTemplate =
			commerceNotificationTemplates.get(0);

		Assert.assertNotNull(commerceNotificationTemplate);
		Assert.assertEquals(
			"Test Commerce Notification Template",
			commerceNotificationTemplate.getName());
	}

	private void _assertCommerceSpecificationProducts(
			ServiceContext serviceContext)
		throws Exception {

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionLocalService.fetchCPSpecificationOption(
				serviceContext.getCompanyId(), "test-product-specification-1");

		Assert.assertNotNull(cpSpecificationOption);

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					"TESTPROD001", serviceContext.getCompanyId());

		Assert.assertNotNull(cpDefinition);

		ProductSpecificationResource.Builder
			productSpecificationResourceBuilder =
				_productSpecificationResourceFactory.create();

		ProductSpecificationResource productSpecificationResource =
			productSpecificationResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		Page<ProductSpecification> productSpecificationPage =
			productSpecificationResource.getProductIdProductSpecificationsPage(
				cpDefinition.getCProductId(), Pagination.of(1, 10));

		ProductSpecification productSpecification =
			productSpecificationPage.fetchFirstItem();

		Assert.assertNotNull(productSpecification);
		Assert.assertEquals(
			"test-product-specification-1",
			productSpecification.getSpecificationKey());
	}

	private void _assertCPDefinition(Group group) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					"TESTPROD001", group.getCompanyId());

		Assert.assertNotNull(cpDefinition);
		Assert.assertEquals("Test Commerce Product", cpDefinition.getName());

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpDefinitionLocalService.getDefaultImageCPAttachmentFileEntry(
				cpDefinition.getCPDefinitionId());

		Assert.assertNotNull(cpAttachmentFileEntry);

		FileEntry fileEntry = cpAttachmentFileEntry.fetchFileEntry();

		Assert.assertEquals(
			"test_commerce_product.png", fileEntry.getFileName());
	}

	private void _assertCPInstanceProperties(Group group) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					"TESTPROD001", group.getCompanyId());

		CPInstance cpInstance1 = _cpInstanceLocalService.getCPInstance(
			cpDefinition.getCPDefinitionId(), "Test Value 1");

		Assert.assertNotNull(cpInstance1);

		BigDecimal actualPrice = cpInstance1.getPrice();

		Assert.assertEquals(60.0, actualPrice.doubleValue(), 0.0001);

		BigDecimal actualPromoPrice = cpInstance1.getPromoPrice();

		Assert.assertEquals(25.0, actualPromoPrice.doubleValue(), 0.0001);

		CPInstance cpInstance2 = _cpInstanceLocalService.getCPInstance(
			cpDefinition.getCPDefinitionId(), "Test Value 2");

		Assert.assertNotNull(cpInstance2);
		Assert.assertTrue(cpInstance2.isSubscriptionEnabled());
	}

	private void _assertCPOption(Group group) throws Exception {
		CPOption cpOption1 = _cpOptionLocalService.fetchCPOption(
			group.getCompanyId(), "test-option-1");

		Assert.assertNotNull(cpOption1);
		Assert.assertEquals(
			"Test Option 1", cpOption1.getName(LocaleUtil.getSiteDefault()));

		CPOption cpOption2 = _cpOptionLocalService.fetchCPOption(
			group.getCompanyId(), "test-option-2");

		Assert.assertNotNull(cpOption2);
		Assert.assertEquals(
			"Test Option 2", cpOption2.getName(LocaleUtil.getSiteDefault()));

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					"TESTPROD001", group.getCompanyId());

		Assert.assertNotNull(cpDefinition);

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			cpDefinition.getCPDefinitionOptionRels();

		Assert.assertEquals(
			cpDefinitionOptionRels.toString(), 2,
			cpDefinitionOptionRels.size());
	}

	private void _assertDDMStructure(Group group) {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			group.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			"TEST DDM STRUCTURE NAME");

		Assert.assertNotNull(ddmStructure);
		Assert.assertTrue(ddmStructure.hasField("aField"));
	}

	private void _assertDDMTemplate(Group group) {
		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
			group.getGroupId(),
			_portal.getClassNameId(DDMStructure.class.getName()),
			"TEST DDM TEMPLATE KEY");

		Assert.assertNotNull(ddmTemplate);
		Assert.assertEquals("${aField.getData()}", ddmTemplate.getScript());
	}

	private void _assertDLFileEntry(Group group) throws Exception {
		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Table of Contents.markdown");

		String string = new String(
			StreamUtil.toByteArray(
				_dlFileEntryLocalService.getFileAsStream(
					dlFileEntry.getFileEntryId(), dlFileEntry.getVersion())));

		Assert.assertTrue(string.contains("## Old Testament"));
		Assert.assertTrue(string.contains("1. Genesis"));
		Assert.assertTrue(string.contains("## New Testament"));
		Assert.assertTrue(string.contains("1. Revelation"));
	}

	private void _assertFragmentEntries(Group group) {
		FragmentEntry testFragmentEntry1 =
			_fragmentEntryLocalService.fetchFragmentEntry(
				group.getGroupId(), "test-fragment-entry-1");

		Assert.assertNotNull(testFragmentEntry1);
		Assert.assertEquals(
			"Test Fragment Entry 1", testFragmentEntry1.getName());

		FragmentEntry testFragmentEntry2 =
			_fragmentEntryLocalService.fetchFragmentEntry(
				group.getGroupId(), "test-fragment-entry-2");

		Assert.assertNotNull(testFragmentEntry2);
		Assert.assertEquals(
			"Test Fragment Entry 2", testFragmentEntry2.getName());
	}

	private void _assertJournalArticles(Group group) throws Exception {
		JournalArticle journalArticle1 =
			_journalArticleLocalService.fetchArticle(
				group.getGroupId(), "test-journal-article-1");

		Assert.assertNotNull(journalArticle1);
		Assert.assertEquals(
			"TEST DDM TEMPLATE KEY", journalArticle1.getDDMTemplateKey());
		Assert.assertEquals(
			"Test Journal Article 1", journalArticle1.getTitle());

		JournalArticle journalArticle2 =
			_journalArticleLocalService.fetchArticle(
				group.getGroupId(), "test-journal-article-2");

		Assert.assertNotNull(journalArticle2);
		Assert.assertEquals(
			"TEST DDM TEMPLATE KEY", journalArticle2.getDDMTemplateKey());
		Assert.assertEquals(
			"Test Journal Article 2", journalArticle2.getTitle());

		List<JournalFolder> journalFolders = _journalFolderService.getFolders(
			group.getGroupId());

		Assert.assertTrue(journalFolders.size() == 2);

		JournalFolder journalFolder1 = journalFolders.get(0);

		Assert.assertEquals("Test Journal Article 1", journalFolder1.getName());

		JournalFolder journalFolder2 = journalFolders.get(1);

		Assert.assertEquals("Test Journal Article 2", journalFolder2.getName());
	}

	private void _assertLayoutPageTemplateEntry(Group group) throws Exception {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				group.getGroupId(), "Test Master Page",
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT);

		Assert.assertNotNull(layoutPageTemplateEntry);
		Assert.assertEquals(
			"Test Master Page", layoutPageTemplateEntry.getName());
	}

	private void _assertLayouts(Group group) throws Exception {
		List<Layout> privateLayouts = _layoutLocalService.getLayouts(
			group.getGroupId(), true);

		Assert.assertTrue(privateLayouts.size() == 1);

		Layout privateLayout = privateLayouts.get(0);

		Assert.assertTrue(privateLayout.isHidden());
		Assert.assertEquals(
			"Test Private Layout",
			privateLayout.getName(LocaleUtil.getSiteDefault()));
		Assert.assertEquals("content", privateLayout.getType());

		List<Layout> publicLayouts = _layoutLocalService.getLayouts(
			group.getGroupId(), false);

		Assert.assertTrue(publicLayouts.size() == 1);

		Layout publicLayout = publicLayouts.get(0);

		Assert.assertFalse(publicLayout.isHidden());
		Assert.assertEquals(
			"Test Public Layout",
			publicLayout.getName(LocaleUtil.getSiteDefault()));
		Assert.assertEquals("content", publicLayout.getType());
	}

	private void _assertLayoutSets(Group group) throws Exception {
		LayoutSet privateLayoutSet = _layoutSetLocalService.fetchLayoutSet(
			group.getGroupId(), true);

		Assert.assertNotNull(privateLayoutSet);

		Theme privateTheme = privateLayoutSet.getTheme();

		Assert.assertEquals("Dialect", privateTheme.getName());

		UnicodeProperties privateLayoutSetUnicodeProperties =
			privateLayoutSet.getSettingsProperties();

		Assert.assertTrue(
			GetterUtil.getBoolean(
				privateLayoutSetUnicodeProperties.getProperty(
					"lfr-theme:regular:show-footer")));
		Assert.assertFalse(
			GetterUtil.getBoolean(
				privateLayoutSetUnicodeProperties.getProperty(
					"lfr-theme:regular:show-header")));

		LayoutSet publicLayoutSet = _layoutSetLocalService.fetchLayoutSet(
			group.getGroupId(), false);

		Assert.assertNotNull(publicLayoutSet);

		Theme publicTheme = publicLayoutSet.getTheme();

		Assert.assertEquals("Dialect", publicTheme.getName());

		UnicodeProperties publicLayoutSetUnicodeProperties =
			publicLayoutSet.getSettingsProperties();

		Assert.assertFalse(
			GetterUtil.getBoolean(
				publicLayoutSetUnicodeProperties.getProperty(
					"lfr-theme:regular:show-footer")));
		Assert.assertTrue(
			GetterUtil.getBoolean(
				publicLayoutSetUnicodeProperties.getProperty(
					"lfr-theme:regular:show-header")));
	}

	private void _assertListTypeDefinitions(ServiceContext serviceContext)
		throws Exception {

		ListTypeDefinitionResource.Builder listTypeDefinitionResourceBuilder =
			_listTypeDefinitionResourceFactory.create();

		ListTypeDefinitionResource listTypeDefinitionResource =
			listTypeDefinitionResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		Page<ListTypeDefinition> listTypeDefinitionsPage =
			listTypeDefinitionResource.getListTypeDefinitionsPage(
				null, null,
				listTypeDefinitionResource.toFilter(
					"name eq 'Test List Type Definition'"),
				null, null);

		ListTypeDefinition listTypeDefinition =
			listTypeDefinitionsPage.fetchFirstItem();

		Assert.assertNotNull(listTypeDefinition);

		ListTypeEntry[] listTypeEntries =
			listTypeDefinition.getListTypeEntries();

		ListTypeEntry listTypeEntry1 = listTypeEntries[0];

		Assert.assertNotNull(listTypeEntry1);
		Assert.assertEquals("testlisttypeentry1", listTypeEntry1.getKey());

		ListTypeEntry listTypeEntry2 = listTypeEntries[1];

		Assert.assertNotNull(listTypeEntry2);
		Assert.assertEquals("testlisttypeentry2", listTypeEntry2.getKey());
	}

	private void _assertObjectDefinitions(
			Group group, ServiceContext serviceContext)
		throws Exception {

		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.fetchObjectDefinition(
				group.getCompanyId(), "C_TestObjectDefinition1");

		Assert.assertEquals(objectDefinition1.isSystem(), false);
		Assert.assertEquals(
			objectDefinition1.getStatus(), WorkflowConstants.STATUS_APPROVED);

		_assertObjectEntries(group.getGroupId(), objectDefinition1, 0);
		_assertObjectRelationships(objectDefinition1, serviceContext);

		ObjectDefinition objectDefinition2 =
			_objectDefinitionLocalService.fetchObjectDefinition(
				group.getCompanyId(), "C_TestObjectDefinition2");

		Assert.assertEquals(objectDefinition2.isSystem(), false);
		Assert.assertEquals(
			objectDefinition2.getStatus(), WorkflowConstants.STATUS_APPROVED);

		_assertObjectEntries(group.getGroupId(), objectDefinition2, 0);

		ObjectDefinition objectDefinition3 =
			_objectDefinitionLocalService.fetchObjectDefinition(
				group.getCompanyId(), "C_TestObjectDefinition3");

		Assert.assertEquals(objectDefinition3.isSystem(), false);
		Assert.assertEquals(
			objectDefinition3.getScope(),
			ObjectDefinitionConstants.SCOPE_COMPANY);
		Assert.assertEquals(
			objectDefinition3.getStatus(), WorkflowConstants.STATUS_APPROVED);

		_assertObjectEntries(0, objectDefinition3, 5);
	}

	private void _assertObjectEntries(
			long groupId, ObjectDefinition objectDefinition,
			int objectEntriesCount)
		throws Exception {

		Assert.assertEquals(
			objectEntriesCount,
			_objectEntryLocalService.getObjectEntriesCount(
				groupId, objectDefinition.getObjectDefinitionId()));
	}

	private void _assertObjectRelationships(
			ObjectDefinition objectDefinition, ServiceContext serviceContext)
		throws Exception {

		ObjectRelationshipResource.Builder objectRelationshipResourceBuilder =
			_objectRelationshipResourceFactory.create();

		ObjectRelationshipResource objectRelationshipResource =
			objectRelationshipResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		Page<ObjectRelationship> page1 =
			objectRelationshipResource.
				getObjectDefinitionObjectRelationshipsPage(
					objectDefinition.getObjectDefinitionId(), null,
					objectRelationshipResource.toFilter("name eq 'testOR1'"),
					null);

		Assert.assertNotNull(page1);

		ObjectRelationship existingObjectRelationship1 = page1.fetchFirstItem();

		Assert.assertEquals(
			"TestObjectDefinition1",
			existingObjectRelationship1.getObjectDefinitionName2());

		ObjectRelationship.Type objectRelationshipType1 =
			existingObjectRelationship1.getType();

		Assert.assertEquals("oneToMany", objectRelationshipType1.toString());

		Page<ObjectRelationship> page2 =
			objectRelationshipResource.
				getObjectDefinitionObjectRelationshipsPage(
					objectDefinition.getObjectDefinitionId(), null,
					objectRelationshipResource.toFilter("name eq 'testOR2'"),
					null);

		Assert.assertNotNull(page2);

		ObjectRelationship existingObjectRelationship2 = page2.fetchFirstItem();

		Assert.assertEquals(
			"TestObjectDefinition2",
			existingObjectRelationship2.getObjectDefinitionName2());

		ObjectRelationship.Type objectRelationshipType2 =
			existingObjectRelationship2.getType();

		Assert.assertEquals("manyToMany", objectRelationshipType2.toString());
	}

	private void _assertWidgetTemplates(Group group) {

		DDMTemplate ddmTemplate =
		_ddmTemplateLocalService.fetchTemplate(
			group.getGroupId(),
			_portal.getClassNameId("com.liferay.portal.kernel.theme.NavItem"),
			"TEST-WIDGET-TEMPLATE-001");

		Assert.assertNotNull(ddmTemplate);

		Assert.assertEquals("TEST WIDGET TEMPLATE 001",
			ddmTemplate.getName(LocaleUtil.getSiteDefault()));
		Assert.assertEquals("${aField.getData()}",
			ddmTemplate.getScript());
	}

	private void _assertPermissions(Group group) throws Exception {
		_assertRoles(group);

		_assertResourcePermission(group);
	}

	private void _assertRemoteApp(Group group) throws Exception {
		RemoteAppEntry remoteAppEntry =
			_remoteAppEntryLocalService.
				fetchRemoteAppEntryByExternalReferenceCode(
					group.getCompanyId(), "ERC001");

		Assert.assertNotNull(remoteAppEntry);
		Assert.assertEquals(
			"category.remote-apps", remoteAppEntry.getPortletCategoryName());
		Assert.assertEquals(
			"liferay-test-remote-app",
			remoteAppEntry.getCustomElementHTMLElementName());
	}

	private void _assertResourcePermission(Group group) throws Exception {
		Role role = _roleLocalService.fetchRole(
			group.getCompanyId(), "Test Role 1");

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				group.getCompanyId(), "com.liferay.commerce.product", 1,
				String.valueOf(group.getCompanyId()), role.getRoleId());

		Assert.assertNotNull(resourcePermission);

		role = _roleLocalService.fetchRole(group.getCompanyId(), "Test Role 2");

		resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				group.getCompanyId(), "com.liferay.commerce.product", 2,
				String.valueOf(group.getGroupId()), role.getRoleId());

		Assert.assertNotNull(resourcePermission);
	}

	private void _assertRoles(Group group) {
		Role role1 = _roleLocalService.fetchRole(
			group.getCompanyId(), "Test Role 1");

		Assert.assertNotNull(role1);
		Assert.assertEquals(1, role1.getType());

		Role role2 = _roleLocalService.fetchRole(
			group.getCompanyId(), "Test Role 2");

		Assert.assertNotNull(role2);
		Assert.assertEquals(1, role2.getType());

		Role role3 = _roleLocalService.fetchRole(
			group.getCompanyId(), "Test Role 3");

		Assert.assertNotNull(role3);
		Assert.assertEquals(1, role3.getType());

		Role role4 = _roleLocalService.fetchRole(
			group.getCompanyId(), "Test Role 4");

		Assert.assertNotNull(role4);
		Assert.assertEquals(2, role4.getType());
	}

	private void _assertSAPEntries(Group group) {
		SAPEntry sapEntry1 = _sapEntryLocalService.fetchSAPEntry(
			group.getCompanyId(), "TEST_SAP_ENTRY_1");

		Assert.assertNotNull(sapEntry1);
		Assert.assertTrue(sapEntry1.isDefaultSAPEntry());
		Assert.assertTrue(sapEntry1.isEnabled());

		List<String> allowedServiceSignaturesList1 =
			sapEntry1.getAllowedServiceSignaturesList();

		Assert.assertEquals(
			allowedServiceSignaturesList1.toString(), 3,
			allowedServiceSignaturesList1.size());

		SAPEntry sapEntry2 = _sapEntryLocalService.fetchSAPEntry(
			group.getCompanyId(), "TEST_SAP_ENTRY_2");

		Assert.assertNotNull(sapEntry2);
		Assert.assertFalse(sapEntry2.isDefaultSAPEntry());
		Assert.assertTrue(sapEntry2.isEnabled());

		List<String> allowedServiceSignaturesList2 =
			sapEntry2.getAllowedServiceSignaturesList();

		Assert.assertEquals(
			allowedServiceSignaturesList2.toString(), 5,
			allowedServiceSignaturesList2.size());
	}

	private void _assertSiteConfiguration(Long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		Assert.assertEquals(
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			group.getMembershipRestriction());
		Assert.assertEquals(GroupConstants.TYPE_SITE_OPEN, group.getType());
		Assert.assertTrue(group.isManualMembership());
	}

	private void _assertSiteNavigationMenu(Group group) {
		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchSiteNavigationMenuByName(
				group.getGroupId(), "Test Site Navigation Menu");

		Assert.assertNotNull(siteNavigationMenu);

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
				siteNavigationMenu.getSiteNavigationMenuId(), 0);

		Assert.assertEquals(
			siteNavigationMenuItems.toString(), 7,
			siteNavigationMenuItems.size());

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			siteNavigationMenuItems.get(0);

		Assert.assertEquals(
			SiteNavigationMenuItemTypeConstants.LAYOUT,
			siteNavigationMenuItem1.getType());

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			siteNavigationMenuItems.get(1);

		Assert.assertEquals("Test URL", siteNavigationMenuItem2.getName());
		Assert.assertEquals(
			SiteNavigationMenuItemTypeConstants.URL,
			siteNavigationMenuItem2.getType());

		SiteNavigationMenuItem siteNavigationMenuItem3 =
			siteNavigationMenuItems.get(2);

		Assert.assertEquals("Other Links", siteNavigationMenuItem3.getName());
		Assert.assertEquals(
			SiteNavigationMenuItemTypeConstants.NODE,
			siteNavigationMenuItem3.getType());

		SiteNavigationMenuItem siteNavigationMenuItem4 =
			siteNavigationMenuItems.get(3);

		Assert.assertEquals(
			AssetCategory.class.getName(), siteNavigationMenuItem4.getType());

		SiteNavigationMenuItem siteNavigationMenuItem5 =
			siteNavigationMenuItems.get(4);

		Assert.assertEquals(
			JournalArticle.class.getName(), siteNavigationMenuItem5.getType());

		SiteNavigationMenuItem siteNavigationMenuItem6 =
			siteNavigationMenuItems.get(5);

		Assert.assertEquals(
			FileEntry.class.getName(), siteNavigationMenuItem6.getType());

		SiteNavigationMenuItem siteNavigationMenuItem7 =
			siteNavigationMenuItems.get(6);

		String type = siteNavigationMenuItem7.getType();

		Assert.assertTrue(
			type.startsWith("com.liferay.object.model.ObjectDefinition#"));
	}

	private void _assertStyleBookEntry(Group group) {
		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.fetchStyleBookEntry(
				group.getGroupId(), "test-style-book");

		Assert.assertNotNull(styleBookEntry);

		String frontendTokensValues = styleBookEntry.getFrontendTokensValues();

		Assert.assertTrue(
			frontendTokensValues.contains("blockquote-small-color"));
	}

	private void _assertUserAccounts(
			Long accountId, int totalCount,
			UserAccountResource userAccountResource)
		throws Exception {

		Page<UserAccount> page = userAccountResource.getAccountUserAccountsPage(
			accountId, null, null, null, null);

		Assert.assertNotNull(page);
		Assert.assertEquals(totalCount, page.getTotalCount());
	}

	private void _assertUserRoles(Group group) throws Exception {
		User user = _userLocalService.fetchUserByEmailAddress(
			group.getCompanyId(), "test.user1@liferay.com");

		List<Role> roles = user.getRoles();

		Assert.assertEquals(roles.toString(), 3, roles.size());

		Role role = roles.get(0);

		Assert.assertEquals(RoleConstants.USER, role.getName());

		role = roles.get(1);

		Assert.assertEquals("Test Role 1", role.getName());

		role = roles.get(2);

		Assert.assertEquals("Test Role 2", role.getName());

		user = _userLocalService.fetchUserByEmailAddress(
			group.getCompanyId(), "test.user2@liferay.com");

		roles = user.getRoles();

		Assert.assertEquals(roles.toString(), 2, roles.size());

		role = roles.get(0);

		Assert.assertEquals(RoleConstants.USER, role.getName());

		role = roles.get(1);

		Assert.assertEquals("Test Role 3", role.getName());
	}

	private void _assertWorkflowDefinitions(
			Group group, ServiceContext serviceContext)
		throws Exception {

		WorkflowDefinitionResource.Builder workflowDefinitionResourceBuilder =
			_workflowDefinitionResourceFactory.create();

		WorkflowDefinitionResource workflowDefinitionResource =
			workflowDefinitionResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		WorkflowDefinition workflowDefinitionTest1 =
			workflowDefinitionResource.getWorkflowDefinitionByName(
				"Test Workflow Definition 1", 1);

		Assert.assertNotNull(workflowDefinitionTest1);
		Assert.assertEquals(
			"Test Workflow Definition 1", workflowDefinitionTest1.getName());
		Assert.assertEquals(
			"Test Workflow Definition 1", workflowDefinitionTest1.getTitle());
		Assert.assertEquals(
			"This is a description for Test Workflow Definition 1.",
			workflowDefinitionTest1.getDescription());

		WorkflowDefinitionLink workflowDefinitionLink1 =
			_workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(
				group.getCompanyId(), 0, "com.liferay.blogs.model.BlogsEntry",
				0, 0);

		Assert.assertNotNull(workflowDefinitionLink1);
		Assert.assertEquals(
			"Test Workflow Definition 1",
			workflowDefinitionLink1.getWorkflowDefinitionName());

		WorkflowDefinition workflowDefinitionTest2 =
			workflowDefinitionResource.getWorkflowDefinitionByName(
				"Test Workflow Definition 2", 1);

		Assert.assertNotNull(workflowDefinitionTest2);
		Assert.assertEquals(
			"Test Workflow Definition 2", workflowDefinitionTest2.getName());
		Assert.assertEquals(
			"Test Workflow Definition 2", workflowDefinitionTest2.getTitle());
		Assert.assertEquals(
			"This is a description for Test Workflow Definition 2.",
			workflowDefinitionTest2.getDescription());

		WorkflowDefinitionLink workflowDefinitionLink2 =
			_workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(
				group.getCompanyId(), group.getGroupId(),
				"com.liferay.search.experiences.model.SXPBlueprint", 0, 0);

		Assert.assertNotNull(workflowDefinitionLink2);
		Assert.assertEquals(
			"Test Workflow Definition 2",
			workflowDefinitionLink2.getWorkflowDefinitionName());
	}

	private Bundle _installBundle(BundleContext bundleContext, String location)
		throws Exception {

		try (InputStream inputStream =
				BundleSiteInitializerTest.class.getResourceAsStream(location)) {

			return bundleContext.installBundle(location, inputStream);
		}
	}

	@Inject
	private AccountResource.Factory _accountResourceFactory;

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Inject
	private CommerceNotificationTemplateLocalService
		_commerceNotificationTemplateLocalService;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@Inject
	private CPOptionLocalService _cpOptionLocalService;

	@Inject
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JournalFolderService _journalFolderService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private ListTypeDefinitionResource.Factory
		_listTypeDefinitionResourceFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectRelationshipResource.Factory
		_objectRelationshipResourceFactory;

	@Inject
	private CPFileImporter _cpFileImporter;

	@Inject
	private Portal _portal;

	@Inject
	private ProductSpecificationResource.Factory
		_productSpecificationResourceFactory;

	@Inject
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private SAPEntryLocalService _sapEntryLocalService;

	@Inject
	private ServletContext _servletContext;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Inject
	private UserAccountResource.Factory _userAccountResourceFactory;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowDefinitionResource.Factory
		_workflowDefinitionResourceFactory;

}