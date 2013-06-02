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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.util.SocialActivityHierarchyEntryThreadLocal;
import com.liferay.portlet.social.util.SocialActivityTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class SocialActivityLocalServiceTest extends BaseSocialActivityTestCase {

	@Test
	public void testActivityHierarchy() throws Exception {
		AssetEntry parentAssetEntry = SocialActivityTestUtil.addAssetEntry(
			_creatorUser, _group);

		SocialActivityHierarchyEntryThreadLocal.push(
			parentAssetEntry.getClassNameId(), parentAssetEntry.getClassPK());

		SocialActivityTestUtil.addActivity(
			_creatorUser, _group, _assetEntry, 1);

		List<SocialActivity> activities =
			SocialActivityLocalServiceUtil.getGroupActivities(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(1, activities.size());

		SocialActivity activity = activities.get(0);

		Assert.assertEquals(
			parentAssetEntry.getClassNameId(), activity.getParentClassNameId());
		Assert.assertEquals(
			parentAssetEntry.getClassPK(), activity.getParentClassPK());

		SocialActivityTestUtil.addActivity(
			_creatorUser, _group, _assetEntry,
			SocialActivityConstants.TYPE_DELETE);

		Assert.assertEquals(
			1,
			SocialActivityLocalServiceUtil.getGroupActivitiesCount(
				_group.getGroupId()));
	}

	@Test
	public void testAssetActivitySet() throws Exception {
		List<String> actionIds = new ArrayList<String>();

		actionIds.add(ActionKeys.VIEW);

		ResourceActionLocalServiceUtil.checkResourceActions(
			TEST_MODEL, actionIds);

		Role role = RoleLocalServiceUtil.getRole(_group.getCompanyId(), "User");

		ResourcePermissionLocalServiceUtil.setOwnerResourcePermissions(
			_group.getCompanyId(), _assetEntry.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_assetEntry.getClassPK()), role.getRoleId(), 0,
			new String[] {ActionKeys.VIEW});

		SocialActivityTestUtil.addActivity(
			_creatorUser, _group, _assetEntry, 1,
			SocialActivityTestUtil.createExtraDataJSON("title", "title1"));

		Assert.assertEquals(
			1,
			SocialActivityLocalServiceUtil.getGroupActivitiesCount(
				_group.getGroupId()));

		SocialActivitySet activitySet =
			SocialActivitySetLocalServiceUtil.fetchAssetActivitySet(
				_assetEntry.getClassNameId(), _assetEntry.getClassPK());

		Assert.assertNotNull(activitySet);

		Assert.assertFalse(
			ResourcePermissionLocalServiceUtil.hasResourcePermission(
				_group.getCompanyId(), SocialActivitySet.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(activitySet.getActivitySetId()),
				role.getRoleId(), ActionKeys.VIEW));

		ResourcePermissionLocalServiceUtil.deleteResourcePermissions(
			_group.getCompanyId(), _assetEntry.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL, _assetEntry.getClassPK());

		Assert.assertTrue(
			ResourcePermissionLocalServiceUtil.hasResourcePermission(
				_group.getCompanyId(), SocialActivitySet.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(activitySet.getActivitySetId()),
				role.getRoleId(), ActionKeys.VIEW));
	}

}