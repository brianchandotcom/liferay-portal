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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.test.portal.util.GroupTestUtil;
import com.liferay.test.portal.util.RandomTestUtil;
import com.liferay.test.portal.util.ServiceContextTestUtil;
import com.liferay.test.portal.util.TestPropsValues;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetTagServiceTest {

	@Test
	public void testDeleteGroupTags() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTagsCount = AssetTagLocalServiceUtil.getGroupTagsCount(
			group.getGroupId());

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			serviceContext);
		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			serviceContext);

		Assert.assertEquals(
			initialTagsCount + 2,
			AssetTagLocalServiceUtil.getGroupTagsCount(group.getGroupId()));

		AssetTagLocalServiceUtil.deleteGroupTags(group.getGroupId());

		Assert.assertEquals(
			initialTagsCount,
			AssetTagLocalServiceUtil.getGroupTagsCount(group.getGroupId()));
	}

}