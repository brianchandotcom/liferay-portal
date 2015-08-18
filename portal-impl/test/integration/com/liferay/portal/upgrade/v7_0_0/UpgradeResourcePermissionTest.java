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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sampsa Sohlman
 */
@Sync(cleanTransaction = true)
public class UpgradeResourcePermissionTest extends UpgradeResourcePermission {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() throws Exception {
		for (long resourcePermissionId : _resourcePermissionIds) {
			ResourcePermissionLocalServiceUtil.deleteResourcePermission(
				resourcePermissionId);
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		String primKey1 = "123";
		long actionIds1 = 4;

		long resourcePermissionId1 = addResourcePermission(
			primKey1, actionIds1);

		String primKey2 = "987";
		long actionIds2 = 3;

		long resourcePermissionId2 = addResourcePermission(
			primKey2, actionIds2);

		upgrade();

		CacheRegistryUtil.clear();

		ResourcePermission resourcePermission1 =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				resourcePermissionId1);

		Assert.assertEquals(
			(actionIds1 % 2 == 1), resourcePermission1.getViewActionId());
		Assert.assertEquals(
			resourcePermission1.getPrimKeyId(), GetterUtil.getLong(primKey1));

		ResourcePermission resourcePermission2 =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				resourcePermissionId2);

		Assert.assertEquals(
			(actionIds2 % 2 == 1), resourcePermission2.getViewActionId());
		Assert.assertEquals(
			resourcePermission2.getPrimKeyId(), GetterUtil.getLong(primKey2));
	}

	protected long addResourcePermission(String primKey, long actionIds) {
		ResourcePermission resourcePermission = new ResourcePermissionImpl();

		long resourcePermissionId = CounterLocalServiceUtil.increment(
			ResourcePermission.class.getName());

		resourcePermission.setResourcePermissionId(resourcePermissionId);
		resourcePermission.setCompanyId(_user.getCompanyId());
		resourcePermission.setName("com.lifeary.rocks");
		resourcePermission.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
		resourcePermission.setOwnerId(_user.getUserId());
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setActionIds(actionIds);
		resourcePermission.setPrimKeyId(-1L);
		resourcePermission.setViewActionId(actionIds % 2!= 1);

		resourcePermission =
			ResourcePermissionLocalServiceUtil.addResourcePermission(
				resourcePermission);

		return resourcePermission.getResourcePermissionId();
	}

	private List<Long> _resourcePermissionIds = new ArrayList<>();
	
	@DeleteAfterTestRun
	private User _user;

}