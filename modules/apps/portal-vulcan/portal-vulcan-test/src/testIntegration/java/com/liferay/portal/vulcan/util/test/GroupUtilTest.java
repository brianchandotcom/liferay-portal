/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.GroupUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@FeatureFlags("LPD-17564")
@RunWith(Arquillian.class)
public class GroupUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws PortalException {
		_depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	@After
	public void tearDown() throws PortalException {
		_depotEntryLocalService.deleteDepotEntry(_depotEntry.getDepotEntryId());
	}

	@Test
	public void testGetAssetLibraryKey() throws Exception {

		// Depot entry

		Group depotEntryGroup = _depotEntry.getGroup();

		Assert.assertEquals(
			depotEntryGroup.getGroupKey(),
			GroupUtil.getAssetLibraryKey(depotEntryGroup));

		// Site

		Group siteGroup = _groupLocalService.getGroup(
			TestPropsValues.getGroupId());

		Assert.assertNull(GroupUtil.getAssetLibraryKey(siteGroup));
	}

	@Test
	public void testGetDepotGroupId() throws Exception {
		Group depotEntryGroup = _depotEntry.getGroup();

		// Depot entry group ID

		Assert.assertEquals(
			Long.valueOf(depotEntryGroup.getGroupId()),
			GroupUtil.getDepotGroupId(
				String.valueOf(depotEntryGroup.getGroupId()),
				depotEntryGroup.getCompanyId(), _depotEntryLocalService,
				_groupLocalService));

		// Depot entry group key

		Assert.assertEquals(
			Long.valueOf(depotEntryGroup.getGroupId()),
			GroupUtil.getDepotGroupId(
				depotEntryGroup.getGroupKey(), depotEntryGroup.getCompanyId(),
				_depotEntryLocalService, _groupLocalService));

		// Depot entry ID

		Assert.assertEquals(
			Long.valueOf(depotEntryGroup.getGroupId()),
			GroupUtil.getDepotGroupId(
				String.valueOf(_depotEntry.getDepotEntryId()),
				depotEntryGroup.getCompanyId(), _depotEntryLocalService,
				_groupLocalService));
	}

	@Test
	public void testGetGroupId() throws Exception {
		Group siteGroup = _groupLocalService.getGroup(
			TestPropsValues.getGroupId());

		// Site group key

		Assert.assertEquals(
			Long.valueOf(siteGroup.getGroupId()),
			GroupUtil.getGroupId(
				siteGroup.getCompanyId(), siteGroup.getGroupKey(),
				_groupLocalService));

		// Site group ID

		Assert.assertEquals(
			Long.valueOf(siteGroup.getGroupId()),
			GroupUtil.getGroupId(
				siteGroup.getCompanyId(),
				String.valueOf(siteGroup.getGroupId()), _groupLocalService));
	}

	@Test
	public void testGetSiteExternalReferenceCode() throws Exception {

		// Depot entry group

		Assert.assertNull(
			GroupUtil.getSiteExternalReferenceCode(_depotEntry.getGroup()));

		// Site group

		Group siteGroup = _groupLocalService.getGroup(
			TestPropsValues.getGroupId());

		Assert.assertEquals(
			siteGroup.getExternalReferenceCode(),
			GroupUtil.getSiteExternalReferenceCode(siteGroup));
	}

	@Test
	public void testGetSiteId() throws Exception {

		// Depot entry group

		Assert.assertNull(GroupUtil.getSiteId(_depotEntry.getGroup()));

		// Site group

		Group siteGroup = _groupLocalService.getGroup(
			TestPropsValues.getGroupId());

		Assert.assertEquals(
			Long.valueOf(siteGroup.getGroupId()),
			GroupUtil.getSiteId(siteGroup));
	}

	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}