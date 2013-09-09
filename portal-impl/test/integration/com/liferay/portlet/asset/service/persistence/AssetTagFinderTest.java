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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.BlogsTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetTagFinderTest {

	@Test
	public void testFilterCountByG_C_N() throws Exception {
		Group scopeGroup = addScopeGroup();

		Group siteGroup = scopeGroup.getParentGroup();

		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);
		String assetTagName = ServiceTestUtil.randomString();

		int initialScopeGroupAssetTagsCount =
			AssetTagFinderUtil.filterCountByG_C_N(
				scopeGroup.getGroupId(), classNameId, assetTagName);
		int initialSiteGroupAssetTagsCount =
			AssetTagFinderUtil.filterCountByG_C_N(
				siteGroup.getGroupId(), classNameId, assetTagName);

		addBlogsEntry(scopeGroup.getGroupId(), assetTagName);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			int scopeGroupAssetTagsCount =
				AssetTagFinderUtil.filterCountByG_C_N(
					scopeGroup.getGroupId(), classNameId, assetTagName);

			Assert.assertEquals(
				initialScopeGroupAssetTagsCount + 1, scopeGroupAssetTagsCount);

			int siteGroupAssetTagsCount = AssetTagFinderUtil.filterCountByG_C_N(
					siteGroup.getGroupId(), classNameId, assetTagName);

			Assert.assertEquals(
				initialSiteGroupAssetTagsCount, siteGroupAssetTagsCount);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testFilterCountByG_N() throws Exception {
		Group scopeGroup = addScopeGroup();

		Group siteGroup = scopeGroup.getParentGroup();

		String assetTagName = ServiceTestUtil.randomString();

		int initialScopeGroupAssetTagsCount =
			AssetTagFinderUtil.filterCountByG_N(
				scopeGroup.getGroupId(), assetTagName);
		int initialTagsCountSiteGroup = AssetTagFinderUtil.filterCountByG_N(
			siteGroup.getGroupId(), assetTagName);

		addBlogsEntry(scopeGroup.getGroupId(), assetTagName);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			int scopeGroupAssetTagsCount = AssetTagFinderUtil.filterCountByG_N(
				scopeGroup.getGroupId(), assetTagName);

			Assert.assertEquals(
				initialScopeGroupAssetTagsCount + 1, scopeGroupAssetTagsCount);

			int siteGroupAssetTagsCount = AssetTagFinderUtil.filterCountByG_N(
				siteGroup.getGroupId(), assetTagName);

			Assert.assertEquals(
				initialTagsCountSiteGroup, siteGroupAssetTagsCount);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testFilterCountByG_N_P() throws Exception {
		Group scopeGroup = addScopeGroup();

		Group siteGroup = scopeGroup.getParentGroup();

		String assetTagName = ServiceTestUtil.randomString();
		String[] assetTagProperties = new String[] {"key:value"};

		int initialScopeGroupAssetTagsCount =
			AssetTagFinderUtil.filterCountByG_N_P(
				scopeGroup.getGroupId(), assetTagName, assetTagProperties);
		int initialTagsCountSiteGroup = AssetTagFinderUtil.filterCountByG_N_P(
			siteGroup.getGroupId(), assetTagName, assetTagProperties);

		addAssetTag(siteGroup.getGroupId(), assetTagName, assetTagProperties);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			int scopeGroupAssetTagsCount =
				AssetTagFinderUtil.filterCountByG_N_P(
					scopeGroup.getGroupId(), assetTagName, assetTagProperties);

			Assert.assertEquals(
				initialScopeGroupAssetTagsCount, scopeGroupAssetTagsCount);

			int siteGroupAssetTagsCount = AssetTagFinderUtil.filterCountByG_N_P(
				siteGroup.getGroupId(), assetTagName, assetTagProperties);

			Assert.assertEquals(
				initialTagsCountSiteGroup + 1, siteGroupAssetTagsCount);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testFilterFindByG_C_N() throws Exception {
		Group scopeGroup = addScopeGroup();

		Group siteGroup = scopeGroup.getParentGroup();

		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);
		String assetTagName = ServiceTestUtil.randomString();

		List<AssetTag> initialScopeGroupAssetTags =
			AssetTagFinderUtil.filterFindByG_C_N(
				scopeGroup.getGroupId(), classNameId, assetTagName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		List<AssetTag> initialSiteGroupAssetTags =
			AssetTagFinderUtil.filterFindByG_C_N(
				siteGroup.getGroupId(), classNameId, assetTagName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		addBlogsEntry(scopeGroup.getGroupId(), assetTagName);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			List<AssetTag> scopeGroupAssetTags =
				AssetTagFinderUtil.filterFindByG_C_N(
					scopeGroup.getGroupId(), classNameId, assetTagName,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				initialScopeGroupAssetTags.size() + 1,
				scopeGroupAssetTags.size());

			List<AssetTag> siteGroupAssetTags =
				AssetTagFinderUtil.filterFindByG_C_N(
					siteGroup.getGroupId(), classNameId, assetTagName,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				initialSiteGroupAssetTags.size(), siteGroupAssetTags.size());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testFilterFindByG_N() throws Exception {
		Group scopeGroup = addScopeGroup();

		Group siteGroup = scopeGroup.getParentGroup();

		String assetTagName = ServiceTestUtil.randomString();

		addAssetTag(siteGroup.getGroupId(), assetTagName, null);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			try {
				AssetTagFinderUtil.filterFindByG_N(
					scopeGroup.getGroupId(), assetTagName);

				Assert.fail();
			}
			catch (NoSuchTagException nste) {
			}

			AssetTag siteGroupAssetTag = AssetTagFinderUtil.filterFindByG_N(
				siteGroup.getGroupId(), assetTagName);

			Assert.assertEquals(
				StringUtil.toLowerCase(
					assetTagName), siteGroupAssetTag.getName());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testFilterFindByG_N_P() throws Exception {
		Group scopeGroup = addScopeGroup();

		Group siteGroup = scopeGroup.getParentGroup();

		String assetTagName = ServiceTestUtil.randomString();
		String[] assetTagProperties = new String[] {"key:value"};

		List<AssetTag> initialScopeGroupAssetTags =
			AssetTagFinderUtil.filterFindByG_N_P(
				new long[] {scopeGroup.getGroupId()}, assetTagName,
				assetTagProperties, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		List<AssetTag> initialSiteGroupAssetTags =
			AssetTagFinderUtil.filterFindByG_N_P(
				new long[] {siteGroup.getGroupId()}, assetTagName,
				assetTagProperties, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		addAssetTag(siteGroup.getGroupId(), assetTagName, assetTagProperties);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			List<AssetTag> scopeGroupAssetTags =
				AssetTagFinderUtil.filterFindByG_N_P(
					new long[] {scopeGroup.getGroupId()}, assetTagName,
					assetTagProperties, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

			Assert.assertEquals(
				initialScopeGroupAssetTags.size(), scopeGroupAssetTags.size());

			List<AssetTag> siteGroupAssetTags =
				AssetTagFinderUtil.filterFindByG_N_P(
					new long[] {siteGroup.getGroupId()}, assetTagName,
					assetTagProperties, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

			Assert.assertEquals(
				initialSiteGroupAssetTags.size() + 1,
				siteGroupAssetTags.size());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	protected void addAssetTag(long groupId, String name, String[] properties)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), name, properties, serviceContext);
	}

	protected void addBlogsEntry(long groupId, String assetTagName)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(), true,
			serviceContext);
	}

	protected Group addScopeGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		String name = ServiceTestUtil.randomString();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Group scopeGroup = GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), group.getParentGroupId(),
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name,
			ServiceTestUtil.randomString(), GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name), false,
			true, serviceContext);

		return scopeGroup;
	}

}