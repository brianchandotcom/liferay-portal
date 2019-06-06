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

package com.liferay.asset.display.page.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.display.page.util.AssetDisplayPageHelper;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class AssetDisplayPageHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAssetHasDefaultDisplayPage() throws Exception {
		long classNameId = _portal.getClassNameId(AssetEntry.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutPageTemplateEntry defaultAssetDisplayPage =
			_addLayoutPageTemplateEntry(classNameId, true, serviceContext);

		long classPK = RandomTestUtil.randomLong();

		_addAssetDisplayPageEntry(
			classNameId, classPK,
			defaultAssetDisplayPage.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_DEFAULT, serviceContext);

		Assert.assertEquals(
			true,
			AssetDisplayPageHelper.hasAssetDisplayPage(
				_group.getGroupId(), classNameId, classPK, 0));
	}

	@Test
	public void testAssetHasNoDisplayPage() throws Exception {
		long classNameId = _portal.getClassNameId(AssetEntry.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutPageTemplateEntry assetDisplayPage = _addLayoutPageTemplateEntry(
			classNameId, false, serviceContext);

		long classPK = RandomTestUtil.randomLong();

		_addAssetDisplayPageEntry(
			classNameId, classPK,
			assetDisplayPage.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_NONE, serviceContext);

		Assert.assertEquals(
			false,
			AssetDisplayPageHelper.hasAssetDisplayPage(
				_group.getGroupId(), classNameId, classPK, 0));
	}

	@Test
	public void testAssetHasSpecificDisplayPage() throws Exception {
		long classNameId = _portal.getClassNameId(AssetEntry.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutPageTemplateEntry assetDisplayPage = _addLayoutPageTemplateEntry(
			classNameId, false, serviceContext);

		long classPK = RandomTestUtil.randomLong();

		_addAssetDisplayPageEntry(
			classNameId, classPK,
			assetDisplayPage.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC, serviceContext);

		Assert.assertEquals(
			true,
			AssetDisplayPageHelper.hasAssetDisplayPage(
				_group.getGroupId(), classNameId, classPK, 0));
	}

	@Test
	public void testGetAssetDefaultDisplayPage() throws Exception {
		long classNameId = _portal.getClassNameId(AssetEntry.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutPageTemplateEntry defaultAssetDisplayPage =
			_addLayoutPageTemplateEntry(classNameId, true, serviceContext);

		long classPK = RandomTestUtil.randomLong();

		_addAssetDisplayPageEntry(
			classNameId, classPK,
			defaultAssetDisplayPage.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_DEFAULT, serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			AssetDisplayPageHelper.getAssetDisplayPageLayoutPageTemplateEntry(
				_group.getGroupId(), classNameId, classPK, 0);

		Assert.assertNotNull(layoutPageTemplateEntry);

		Assert.assertEquals(
			defaultAssetDisplayPage.getLayoutPageTemplateEntryId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
	}

	@Test
	public void testGetAssetSpecificDisplayPage() throws Exception {
		long classNameId = _portal.getClassNameId(AssetEntry.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutPageTemplateEntry assetDisplayPage = _addLayoutPageTemplateEntry(
			classNameId, false, serviceContext);

		long classPK = RandomTestUtil.randomLong();

		_addAssetDisplayPageEntry(
			classNameId, classPK,
			assetDisplayPage.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC, serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			AssetDisplayPageHelper.getAssetDisplayPageLayoutPageTemplateEntry(
				_group.getGroupId(), classNameId, classPK, 0);

		Assert.assertNotNull(layoutPageTemplateEntry);

		Assert.assertEquals(
			assetDisplayPage.getLayoutPageTemplateEntryId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
	}

	@Test
	public void testGetAssetWithNoDisplayPage() throws Exception {
		long classNameId = _portal.getClassNameId(AssetEntry.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutPageTemplateEntry assetDisplayPage = _addLayoutPageTemplateEntry(
			classNameId, false, serviceContext);

		long classPK = RandomTestUtil.randomLong();

		_addAssetDisplayPageEntry(
			classNameId, classPK,
			assetDisplayPage.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_NONE, serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			AssetDisplayPageHelper.getAssetDisplayPageLayoutPageTemplateEntry(
				_group.getGroupId(), classNameId, classPK, 0);

		Assert.assertNull(layoutPageTemplateEntry);
	}

	private AssetDisplayPageEntry _addAssetDisplayPageEntry(
			long classNameId, long classPK, long assetDisplayPageId,
			int displayPageType, ServiceContext serviceContext)
		throws PortalException {

		return _assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), classNameId,
			classPK, assetDisplayPageId, displayPageType, serviceContext);
	}

	private LayoutPageTemplateEntry _addLayoutPageTemplateEntry(
			long classNameId, boolean defaultTemplate,
			ServiceContext serviceContext)
		throws PortalException {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), 0, classNameId, 0,
			RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
			defaultTemplate, 0, 0, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

}