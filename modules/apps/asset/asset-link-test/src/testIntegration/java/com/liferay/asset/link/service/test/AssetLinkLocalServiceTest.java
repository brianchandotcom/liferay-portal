/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.link.model.AssetLink;
import com.liferay.asset.link.service.AssetLinkLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class AssetLinkLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteLinksByAssetEntryGroupId() throws Exception {

		// Add link between entries in group 1

		AssetEntry assetEntry1 = AssetTestUtil.addAssetEntry(
			_group1.getGroupId());
		AssetEntry assetEntry2 = AssetTestUtil.addAssetEntry(
			_group1.getGroupId());

		AssetLinkLocalServiceUtil.addLink(
			TestPropsValues.getUserId(), assetEntry1.getEntryId(),
			assetEntry2.getEntryId(), 0, 0);

		// Add link between entries in different groups

		assetEntry1 = AssetTestUtil.addAssetEntry(_group1.getGroupId());
		assetEntry2 = AssetTestUtil.addAssetEntry(_group2.getGroupId());

		AssetLinkLocalServiceUtil.addLink(
			TestPropsValues.getUserId(), assetEntry1.getEntryId(),
			assetEntry2.getEntryId(), 0, 0);

		// Add link between entries in group 2

		assetEntry1 = AssetTestUtil.addAssetEntry(_group2.getGroupId());
		assetEntry2 = AssetTestUtil.addAssetEntry(_group2.getGroupId());

		AssetLinkLocalServiceUtil.addLink(
			TestPropsValues.getUserId(), assetEntry1.getEntryId(),
			assetEntry2.getEntryId(), 0, 0);

		AssetLinkLocalServiceUtil.deleteGroupLinks(_group1.getGroupId());

		List<AssetLink> assetLinks = AssetLinkLocalServiceUtil.getAssetLinks(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertNotNull(assetLinks);
		Assert.assertTrue(!assetLinks.isEmpty());

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