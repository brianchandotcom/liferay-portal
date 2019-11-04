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

package com.liferay.depot.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class DepotEntryGroupRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeletingASiteDeletesItsGroupRels() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.getDefault(), StringUtil.randomString());
				}
			},
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.getDefault(), StringUtil.randomString());
				}
			},
			ServiceContextTestUtil.getServiceContext());

		Group group = _groupLocalService.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			null, 0, 0,
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.getDefault(), StringUtil.randomString());
				}
			},
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.getDefault(), StringUtil.randomString());
				}
			},
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, true, true,
			ServiceContextTestUtil.getServiceContext());

		int depotEntryGroupRelsCount =
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), group.getGroupId());

		Assert.assertEquals(
			depotEntryGroupRelsCount + 1,
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount());

		_groupLocalService.deleteGroup(group.getGroupId());

		Assert.assertEquals(
			depotEntryGroupRelsCount,
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount());
	}

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}