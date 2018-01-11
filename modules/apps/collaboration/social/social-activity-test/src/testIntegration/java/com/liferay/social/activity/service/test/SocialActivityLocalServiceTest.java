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

package com.liferay.social.activity.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.social.util.SocialActivityHierarchyEntryThreadLocal;
import com.liferay.social.activity.service.test.util.SocialActivityTestUtil;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.service.SocialActivityLocalServiceUtil;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
public class SocialActivityLocalServiceTest extends BaseSocialActivityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testActivityHierarchy() throws Exception {
		_parentAssetEntry = SocialActivityTestUtil.addAssetEntry(
			creatorUser, group);

		SocialActivityHierarchyEntryThreadLocal.push(
			_parentAssetEntry.getClassNameId(), _parentAssetEntry.getClassPK());

		SocialActivityTestUtil.addActivity(creatorUser, group, assetEntry, 1);

		List<SocialActivity> activities =
			SocialActivityLocalServiceUtil.getGroupActivities(
				group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(activities.toString(), 1, activities.size());

		SocialActivity activity = activities.get(0);

		Assert.assertEquals(
			_parentAssetEntry.getClassNameId(),
			activity.getParentClassNameId());
		Assert.assertEquals(
			_parentAssetEntry.getClassPK(), activity.getParentClassPK());

		SocialActivityTestUtil.addActivity(
			creatorUser, group, assetEntry,
			SocialActivityConstants.TYPE_DELETE);

		Assert.assertEquals(
			1,
			SocialActivityLocalServiceUtil.getGroupActivitiesCount(
				group.getGroupId()));
	}

	@Test
	public void testAddActivityTimeDoesNotRound() throws PortalException {
		long time = (System.currentTimeMillis() % 1000) + 1;

		SocialActivityLocalServiceUtil.addActivity(
			creatorUser.getUserId(), group.getGroupId(), new Date(time),
			assetEntry.getClassName(), assetEntry.getClassPK(), 1,
			StringPool.BLANK, creatorUser.getUserId());

		List<SocialActivity> activities =
			SocialActivityLocalServiceUtil.getGroupActivities(
				group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(activities.toString(), 1, activities.size());

		SocialActivity activity = activities.get(0);

		Assert.assertEquals(time, activity.getCreateDate());
	}

	@DeleteAfterTestRun
	private AssetEntry _parentAssetEntry;

}