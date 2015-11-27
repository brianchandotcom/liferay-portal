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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.util.test.AssetTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Akos Thurzo
 */
public class AssetLinkLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteLinksByGroupId() throws Exception {

		// Add Link between entities in Group 1

		AssetEntry assetEntry1 = AssetTestUtil.addAssetEntry(
			_group1.getGroupId());
		AssetEntry assetEntry2 = AssetTestUtil.addAssetEntry(
			_group1.getGroupId());

		AssetLinkLocalServiceUtil.addLink(
			TestPropsValues.getUserId(), assetEntry1.getEntryId(),
			assetEntry2.getEntryId(), 0, 0);

		// Add Link between entities in different Groups

		assetEntry1 = AssetTestUtil.addAssetEntry(_group1.getGroupId());
		assetEntry2 = AssetTestUtil.addAssetEntry(_group2.getGroupId());

		AssetLinkLocalServiceUtil.addLink(
			TestPropsValues.getUserId(), assetEntry1.getEntryId(),
			assetEntry2.getEntryId(), 0, 0);

		// Add Link between entities in Group 2

		assetEntry1 = AssetTestUtil.addAssetEntry(_group2.getGroupId());
		assetEntry2 = AssetTestUtil.addAssetEntry(_group2.getGroupId());

		AssetLinkLocalServiceUtil.addLink(
			TestPropsValues.getUserId(), assetEntry1.getEntryId(),
			assetEntry2.getEntryId(), 0, 0);

		AssetLinkLocalServiceUtil.deleteLinksByGroupId(_group1.getGroupId());

		List<AssetLink> assetLinks = AssetLinkLocalServiceUtil.getAssetLinks(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertNotNull(assetLinks);
		Assert.assertTrue(assetLinks.size() > 0);

		for (AssetLink assetLink : assetLinks) {
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				assetLink.getEntryId1());

			Assert.assertTrue(assetEntry.getGroupId() != _group1.getGroupId());

			assetEntry = AssetEntryLocalServiceUtil.getEntry(
				assetLink.getEntryId2());

			Assert.assertTrue(assetEntry.getGroupId() != _group1.getGroupId());
		}
	}

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

}