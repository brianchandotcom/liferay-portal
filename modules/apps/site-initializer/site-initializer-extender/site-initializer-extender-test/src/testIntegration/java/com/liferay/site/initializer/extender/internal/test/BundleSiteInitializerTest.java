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
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.io.InputStream;

import java.util.List;

import javax.servlet.ServletContext;

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

	@Before
	public void setUp() throws Exception {
		_user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_user.getGroupId(), _user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.popServiceContext();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				serviceContext.getCompanyId(), "C_TestBundleSiteInitializer");

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

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

		siteInitializer.initialize(group.getGroupId());

		_assertCommerceCatalogs(group);
		_assertCommerceChannel(group);
		_assertCommerceInventoryWarehouse(group);
		_assertDDMStructure(group);
		_assertDDMTemplate(group);
		_assertDocuments(group);
		_assertFragments(group);
		_assertLayoutPageTemplates(group);
		_assertLayouts(group);
		_assertObjectDefinitions(group);
		_assertStyleBookEntry(group);

		GroupLocalServiceUtil.deleteGroup(group);

		bundle.uninstall();
	}

	private void _assertCommerceCatalogs(Group group) throws Exception {
		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.
				fetchCommerceCatalogByExternalReferenceCode(
					group.getCompanyId(), "TESTCATG0001");

		Assert.assertNotNull(commerceCatalog);
		Assert.assertEquals("Test Catalog 01", commerceCatalog.getName());

		commerceCatalog =
			_commerceCatalogLocalService.
				fetchCommerceCatalogByExternalReferenceCode(
					group.getCompanyId(), "TESTCATG0002");

		Assert.assertNotNull(commerceCatalog);
		Assert.assertEquals("Test Catalog 02", commerceCatalog.getName());
	}

	private void _assertCommerceChannel(Group group) throws Exception {
		CommerceChannel channel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				group.getGroupId());

		Assert.assertNotNull(channel);
		Assert.assertEquals("Test Channel", channel.getName());
		Assert.assertEquals("site", channel.getType());
		Assert.assertEquals("TESTVOC0001", channel.getExternalReferenceCode());
	}

	private void _assertCommerceInventoryWarehouse(Group group) {
		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseLocalService.
				fetchCommerceInventoryWarehouseByExternalReferenceCode(
					group.getCompanyId(), "TESTWARE0001");

		Assert.assertNotNull(commerceInventoryWarehouse);
		Assert.assertEquals(
			"Test Warehouse", commerceInventoryWarehouse.getName());
	}

	private void _assertDDMStructure(Group group) {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			group.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			"TEST STRUCTURE NAME");

		Assert.assertNotNull(ddmStructure);
		Assert.assertTrue(ddmStructure.hasField("aField"));
	}

	private void _assertDDMTemplate(Group group) {
		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
			group.getGroupId(),
			_portal.getClassNameId(DDMStructure.class.getName()),
			"TEST TEMPLATE KEY");

		Assert.assertNotNull(ddmTemplate);
		Assert.assertEquals("${aField.getData()}", ddmTemplate.getScript());
	}

	private void _assertDocuments(Group group) throws Exception {
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

	private void _assertFragments(Group group) {
		FragmentEntry fragment1 = _fragmentEntryLocalService.fetchFragmentEntry(
			group.getGroupId(), "fragment1");

		FragmentEntry fragment2 = _fragmentEntryLocalService.fetchFragmentEntry(
			group.getGroupId(), "fragment2");

		Assert.assertNotNull(fragment1);
		Assert.assertEquals("fragment1", fragment1.getName());
		Assert.assertNotNull(fragment2);
		Assert.assertEquals("fragment2", fragment2.getName());
	}

	private void _assertLayoutPageTemplates(Group group) throws Exception {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				group.getGroupId(), "Test",
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT);

		Assert.assertNotNull(layoutPageTemplateEntry);
		Assert.assertEquals("Test", layoutPageTemplateEntry.getName());
	}

	private void _assertLayouts(Group group) throws Exception {
		List<Layout> layouts = _layoutLocalService.getLayouts(
			group.getGroupId(), true);

		Assert.assertTrue(layouts.size() == 1);

		Layout layout = layouts.get(0);

		Assert.assertTrue(layout.isHidden());
		Assert.assertEquals(
			"Test Private Layout", layout.getName(LocaleUtil.getSiteDefault()));
		Assert.assertEquals("content", layout.getType());

		layouts = _layoutLocalService.getLayouts(group.getGroupId(), false);

		Assert.assertTrue(layouts.size() == 1);

		layout = layouts.get(0);

		Assert.assertFalse(layout.isHidden());
		Assert.assertEquals(
			"Test Public Layout", layout.getName(LocaleUtil.getSiteDefault()));
		Assert.assertEquals("content", layout.getType());
	}

	private void _assertObjectDefinitions(Group group) throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				group.getCompanyId(), "C_TestBundleSiteInitializer");

		Assert.assertEquals(
			objectDefinition.getStatus(), WorkflowConstants.STATUS_APPROVED);
		Assert.assertEquals(objectDefinition.isSystem(), false);
	}

	private void _assertStyleBookEntry(Group group) {
		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.fetchStyleBookEntry(
				group.getGroupId(), "Test");

		Assert.assertNotNull(styleBookEntry);

		String frontendTokensValues = styleBookEntry.getFrontendTokensValues();

		Assert.assertTrue(
			frontendTokensValues.contains("blockquote-small-color"));
	}

	private Bundle _installBundle(BundleContext bundleContext, String location)
		throws Exception {

		try (InputStream inputStream =
				BundleSiteInitializerTest.class.getResourceAsStream(location)) {

			return bundleContext.installBundle(location, inputStream);
		}
	}

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private ServletContext _servletContext;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}