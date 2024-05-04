/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.exception.DuplicateAssetListEntryExternalReferenceCodeException;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
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
 * @author Bárbara Cabrera
 */
@RunWith(Arquillian.class)
public class AssetListEntryLocalServiceTest {

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
	public void testAddAssetListEntryByExternalReferenceCode()
		throws PortalException {

		String externalReferenceCode = RandomTestUtil.randomString();

		_addAssetListEntry(externalReferenceCode);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.
				fetchAssetListEntryByExternalReferenceCode(
					externalReferenceCode, _group.getGroupId());

		Assert.assertNotNull(assetListEntry);

		Assert.assertEquals(
			externalReferenceCode, assetListEntry.getExternalReferenceCode());
	}

	@Test(
		expected = DuplicateAssetListEntryExternalReferenceCodeException.class
	)
	public void testAddAssetListEntryWithExistingExternalReferenceCode()
		throws PortalException {

		String externalReferenceCode = RandomTestUtil.randomString();

		_addAssetListEntry(externalReferenceCode);
		_addAssetListEntry(externalReferenceCode);
	}

	@Test
	public void testDeleteAssetListEntryByExternalReferenceCode()
		throws PortalException {

		AssetListEntry assetListEntry = _addAssetListEntry(
			RandomTestUtil.randomString());

		_assetListEntryLocalService.deleteAssetListEntry(
			assetListEntry.getExternalReferenceCode(),
			assetListEntry.getGroupId());

		Assert.assertNull(
			_assetListEntryLocalService.
				fetchAssetListEntryByExternalReferenceCode(
					assetListEntry.getExternalReferenceCode(),
					assetListEntry.getGroupId()));
	}

	private AssetListEntry _addAssetListEntry(String externalReferenceCode)
		throws PortalException {

		return _assetListEntryLocalService.addAssetListEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(), RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_DYNAMIC,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

}