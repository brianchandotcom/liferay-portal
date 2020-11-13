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

package com.liferay.layout.page.template.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
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
 * @author Yang Cao
 */
@RunWith(Arquillian.class)
@Sync
public class UpdateAssetDisplayPageEntryMVCActionCommandTest {

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
	public void testUpdateAssetDisplayPageEntryMVCActionCommand()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_portal.getClassNameId(AssetEntry.class.getName()),
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
				AssetDisplayPageConstants.TYPE_SPECIFIC, serviceContext);

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(assetDisplayPageEntry),
			new MockLiferayPortletActionResponse());

		AssetDisplayPageEntry updateAssetDisplayEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				assetDisplayPageEntry.getAssetDisplayPageEntryId());

		Assert.assertEquals(
			0, updateAssetDisplayEntry.getLayoutPageTemplateEntryId());

		Assert.assertEquals(
			AssetDisplayPageConstants.TYPE_NONE,
			updateAssetDisplayEntry.getType());
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"assetDisplayPageEntryId",
			String.valueOf(assetDisplayPageEntry.getAssetDisplayPageEntryId()));

		return mockLiferayPortletActionRequest;
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/layout_page_template_admin/update_asset_display_page_entry"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

}