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
	public void testScopeGroupFilterCountByG_C_N() throws Exception {
		Group scopeGroup = addScopeGroup();
		Group siteGroup = scopeGroup.getParentGroup();

		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);

		String tagName = ServiceTestUtil.randomString();

		int initialTagsCountScopeGroup =
			AssetTagFinderUtil.filterCountByG_C_N(
				scopeGroup.getGroupId(), classNameId, tagName);
		int initialTagsCountSiteGroup =
			AssetTagFinderUtil.filterCountByG_C_N(
				siteGroup.getGroupId(), classNameId, tagName);

		addBlogEntry(scopeGroup.getGroupId(), tagName);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			int finalTagsCountScopeGroup =
				AssetTagFinderUtil.filterCountByG_C_N(
					scopeGroup.getGroupId(), classNameId, tagName);
			int finalTagsCountSiteGroup = AssetTagFinderUtil.filterCountByG_C_N(
				siteGroup.getGroupId(), classNameId, tagName);

			Assert.assertEquals(
				initialTagsCountScopeGroup + 1, finalTagsCountScopeGroup);
			Assert.assertEquals(
				initialTagsCountSiteGroup, finalTagsCountSiteGroup);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testScopeGroupFilterCountByG_N() throws Exception {
		Group scopeGroup = addScopeGroup();
		Group siteGroup = scopeGroup.getParentGroup();

		String tagName = ServiceTestUtil.randomString();

		int initialTagsCountScopeGroup = AssetTagFinderUtil.filterCountByG_N(
			scopeGroup.getGroupId(), tagName);
		int initialTagsCountSiteGroup = AssetTagFinderUtil.filterCountByG_N(
			siteGroup.getGroupId(), tagName);

		addBlogEntry(scopeGroup.getGroupId(), tagName);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			int finalTagsCountScopeGroup = AssetTagFinderUtil.filterCountByG_N(
				scopeGroup.getGroupId(), tagName);
			int finalTagsCountSiteGroup = AssetTagFinderUtil.filterCountByG_N(
				siteGroup.getGroupId(), tagName);

			Assert.assertEquals(
				initialTagsCountScopeGroup + 1, finalTagsCountScopeGroup);
			Assert.assertEquals(
				initialTagsCountSiteGroup, finalTagsCountSiteGroup);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testScopeGroupFilterCountByG_N_P() throws Exception {
		Group scopeGroup = addScopeGroup();
		Group siteGroup = scopeGroup.getParentGroup();

		String tagName = ServiceTestUtil.randomString();

		String[] tagProperties = new String[] {"key:value"};

		int initialTagsCountScopeGroup = AssetTagFinderUtil.filterCountByG_N_P(
			scopeGroup.getGroupId(), tagName, tagProperties);
		int initialTagsCountSiteGroup = AssetTagFinderUtil.filterCountByG_N_P(
			siteGroup.getGroupId(), tagName, tagProperties);

		addAssetTag(siteGroup.getGroupId(), tagName, tagProperties);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			int finalTagsCountScopeGroup =
				AssetTagFinderUtil.filterCountByG_N_P(
					scopeGroup.getGroupId(), tagName, tagProperties);
			int finalTagsCountSiteGroup = AssetTagFinderUtil.filterCountByG_N_P(
				siteGroup.getGroupId(), tagName, tagProperties);

			Assert.assertEquals(
				initialTagsCountScopeGroup, finalTagsCountScopeGroup);
			Assert.assertEquals(
				initialTagsCountSiteGroup + 1, finalTagsCountSiteGroup);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testScopeGroupFilterFindByG_C_N() throws Exception {
		Group scopeGroup = addScopeGroup();
		Group siteGroup = scopeGroup.getParentGroup();

		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);

		String tagName = ServiceTestUtil.randomString();

		List<AssetTag> initialTagsScopeGroup =
			AssetTagFinderUtil.filterFindByG_C_N(
				scopeGroup.getGroupId(), classNameId, tagName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		List<AssetTag> initialTagsSiteGroup =
			AssetTagFinderUtil.filterFindByG_C_N(
				siteGroup.getGroupId(), classNameId, tagName, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		addBlogEntry(scopeGroup.getGroupId(), tagName);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			List<AssetTag> finalTagsScopeGroup =
				AssetTagFinderUtil.filterFindByG_C_N(
					scopeGroup.getGroupId(), classNameId, tagName,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
			List<AssetTag> finalTagsSiteGroup =
				AssetTagFinderUtil.filterFindByG_C_N(
					siteGroup.getGroupId(), classNameId, tagName,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				initialTagsScopeGroup.size() + 1, finalTagsScopeGroup.size());
			Assert.assertEquals(
				initialTagsSiteGroup.size(), finalTagsSiteGroup.size());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testScopeGroupFilterFindByG_N() throws Exception {
		Group scopeGroup = addScopeGroup();
		Group siteGroup = scopeGroup.getParentGroup();

		String tagName = ServiceTestUtil.randomString();

		addAssetTag(siteGroup.getGroupId(), tagName, null);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			try {
				AssetTagFinderUtil.filterFindByG_N(
					scopeGroup.getGroupId(), tagName);

				Assert.fail();
			}
			catch (NoSuchTagException nste) {
			}

			AssetTag finalTagsSiteGroup = AssetTagFinderUtil.filterFindByG_N(
				siteGroup.getGroupId(), tagName);

			Assert.assertEquals(
				tagName.toLowerCase(), finalTagsSiteGroup.getName());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testScopeGroupFilterFindByG_N_P() throws Exception {
		Group scopeGroup = addScopeGroup();
		Group siteGroup = scopeGroup.getParentGroup();

		String tagName = ServiceTestUtil.randomString();

		String[] tagProperties = new String[] {"key:value"};

		List<AssetTag> initialTagsScopeGroup =
			AssetTagFinderUtil.filterFindByG_N_P(
				new long[] {scopeGroup.getGroupId()}, tagName, tagProperties,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		List<AssetTag> initialTagsSiteGroup =
			AssetTagFinderUtil.filterFindByG_N_P(
				new long[]{siteGroup.getGroupId()}, tagName, tagProperties,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		addAssetTag(siteGroup.getGroupId(), tagName, tagProperties);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			List<AssetTag> finalTagsScopeGroup =
				AssetTagFinderUtil.filterFindByG_N_P(
					new long[]{scopeGroup.getGroupId()}, tagName, tagProperties,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
			List<AssetTag> finalTagsSiteGroup =
				AssetTagFinderUtil.filterFindByG_N_P(
					new long[]{siteGroup.getGroupId()}, tagName, tagProperties,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				initialTagsScopeGroup.size(), finalTagsScopeGroup.size());
			Assert.assertEquals(
				initialTagsSiteGroup.size() + 1, finalTagsSiteGroup.size());
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

	protected void addBlogEntry(long groupId, String tagName) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		serviceContext.setAssetTagNames(new String[] {tagName});

		BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(), true,
			serviceContext);
	}

	protected Group addScopeGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		String name = ServiceTestUtil.randomString();
		String description = ServiceTestUtil.randomString();
		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Group scopeGroup = GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), group.getParentGroupId(),
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, description,
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL, false,
			true, serviceContext);

		return scopeGroup;
	}

}