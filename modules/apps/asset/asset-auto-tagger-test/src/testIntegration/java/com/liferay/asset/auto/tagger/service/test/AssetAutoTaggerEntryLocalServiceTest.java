/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class AssetAutoTaggerEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());
	}

	@Test
	public void testAddAssetAutoTaggerEntryWithExistingAssetTag()
		throws PortalException {

		AssetTag assetTag = _assetTagLocalService.addTag(
			null, TestPropsValues.getUserId(), _group.getGroupId(), "Tag",
			_serviceContext);

		_assetTagLocalService.addTag(
			null, TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			_serviceContext);

		AssetAutoTaggerEntry assetAutoTaggerEntry =
			_assetAutoTaggerEntryLocalService.addAssetAutoTaggerEntry(
				AssetTestUtil.addAssetEntry(_group.getGroupId()),
				assetTag.getName());

		Assert.assertNotNull(assetAutoTaggerEntry);
		Assert.assertEquals(
			assetTag.getTagId(), assetAutoTaggerEntry.getAssetTagId());
	}

	@Test
	public void testAddAssetAutoTaggerEntryWithoutExistingAssetTag()
		throws PortalException {

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId());

		assetEntry.setUserId(TestPropsValues.getUserId());

		assetEntry = _assetEntryLocalService.updateAssetEntry(assetEntry);

		AssetAutoTaggerEntry assetAutoTaggerEntry =
			_assetAutoTaggerEntryLocalService.addAssetAutoTaggerEntry(
				assetEntry, "Tag");

		Assert.assertNotNull(assetAutoTaggerEntry);

		AssetTag assetTag = _assetTagLocalService.fetchAssetTag(
			assetAutoTaggerEntry.getAssetTagId());

		Assert.assertNotNull(assetTag);
		Assert.assertEquals("Tag", assetTag.getName());
	}

	@Inject
	private AssetAutoTaggerEntryLocalService _assetAutoTaggerEntryLocalService;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}