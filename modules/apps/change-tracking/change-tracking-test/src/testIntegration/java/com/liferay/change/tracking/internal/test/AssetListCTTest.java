/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class AssetListCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, LayoutCTTest.class.getName(), null);
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddAssetList() throws Exception {
		AssetListEntry manualAssetListEntry;
		AssetListEntry dynamicAssetListEntry;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			manualAssetListEntry =
				_assetListEntryLocalService.addAssetListEntry(
					RandomTestUtil.randomString(), TestPropsValues.getUserId(),
					_group.getGroupId(), RandomTestUtil.randomString(),
					AssetListEntryTypeConstants.TYPE_MANUAL, serviceContext);

			dynamicAssetListEntry =
				_assetListEntryLocalService.addAssetListEntry(
					RandomTestUtil.randomString(), TestPropsValues.getUserId(),
					_group.getGroupId(), RandomTestUtil.randomString(),
					AssetListEntryTypeConstants.TYPE_DYNAMIC, serviceContext);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Assert.assertNotNull(
			_assetListEntryLocalService.fetchAssetListEntry(
				manualAssetListEntry.getAssetListEntryId()));
		Assert.assertNotNull(
			_assetListEntryLocalService.fetchAssetListEntry(
				dynamicAssetListEntry.getAssetListEntryId()));
	}

	@Test
	public void testDeleteAssetList() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetListEntry manualAssetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, serviceContext);

		AssetListEntry dynamicAssetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, serviceContext);

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_assetListEntryLocalService.deleteAssetListEntry(
				manualAssetListEntry);
			_assetListEntryLocalService.deleteAssetListEntry(
				dynamicAssetListEntry);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Assert.assertNull(
			_assetListEntryLocalService.fetchAssetListEntry(
				manualAssetListEntry.getAssetListEntryId()));
		Assert.assertNull(
			_assetListEntryLocalService.fetchAssetListEntry(
				dynamicAssetListEntry.getAssetListEntryId()));
	}

	@Test
	public void testEditAssetList() throws Exception {
		AssetListEntry manualAssetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		String editedTitle = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			manualAssetListEntry.setTitle(editedTitle);

			manualAssetListEntry =
				_assetListEntryLocalService.updateAssetListEntry(
					manualAssetListEntry);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				manualAssetListEntry.getAssetListEntryId());

		Assert.assertNotNull(assetListEntry);
		Assert.assertEquals(editedTitle, assetListEntry.getTitle());
	}

	@Inject
	private static AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTProcessLocalService _ctProcessLocalService;

	private CTCollection _ctCollection;
	private Group _group;

}