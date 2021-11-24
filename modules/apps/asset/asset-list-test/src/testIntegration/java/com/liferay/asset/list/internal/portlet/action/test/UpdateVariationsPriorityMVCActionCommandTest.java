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

package com.liferay.asset.list.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalServiceUtil;
import com.liferay.asset.list.util.AssetListTestUtil;
import com.liferay.asset.list.util.MockPortletURL;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.lang.StringUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yurena Cabrera
 */
@RunWith(Arquillian.class)
@Sync
public class UpdateVariationsPriorityMVCActionCommandTest {

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
	public void testUpdateVariationsPriority() throws PortalException {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel1 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel2 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel3 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		String[] priorities = {
			String.valueOf(
				assetListEntrySegmentsEntryRel3.
					getAssetListEntrySegmentsEntryRelId()),
			String.valueOf(
				assetListEntrySegmentsEntryRel2.
					getAssetListEntrySegmentsEntryRelId()),
			String.valueOf(
				assetListEntrySegmentsEntryRel1.
					getAssetListEntrySegmentsEntryRelId())
		};

		mockLiferayPortletActionRequest.addParameter(
			"variations_priority", StringUtils.join(priorities, ","));

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "doProcessAction",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockLiferayPortletActionRequest, new MockActionResponse());

		assetListEntrySegmentsEntryRel3 =
			AssetListEntrySegmentsEntryRelLocalServiceUtil.
				getAssetListEntrySegmentsEntryRel(
					assetListEntrySegmentsEntryRel3.
						getAssetListEntrySegmentsEntryRelId());
		assetListEntrySegmentsEntryRel2 =
			AssetListEntrySegmentsEntryRelLocalServiceUtil.
				getAssetListEntrySegmentsEntryRel(
					assetListEntrySegmentsEntryRel2.
						getAssetListEntrySegmentsEntryRelId());
		assetListEntrySegmentsEntryRel1 =
			AssetListEntrySegmentsEntryRelLocalServiceUtil.
				getAssetListEntrySegmentsEntryRel(
					assetListEntrySegmentsEntryRel1.
						getAssetListEntrySegmentsEntryRelId());

		Assert.assertEquals(0, assetListEntrySegmentsEntryRel3.getPriority());
		Assert.assertEquals(1, assetListEntrySegmentsEntryRel2.getPriority());
		Assert.assertEquals(2, assetListEntrySegmentsEntryRel1.getPriority());
	}

	@Inject
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "mvc.command.name=/asset_list/update_variations_priority")
	private MVCActionCommand _mvcActionCommand;

	private static class MockActionResponse
		extends MockLiferayPortletActionResponse {

		@Override
		public MockPortletURL createRenderURL() {
			return new MockPortletURL();
		}

	}

}