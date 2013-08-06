/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetTag;

import java.util.List;

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
	public void testDeleteTags() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		List<AssetTag> tags = AssetTagLocalServiceUtil.getGroupTags(
			group.getGroupId());

		int initialTagsCount = tags.size();

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(), null,
			serviceContext);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(), null,
			serviceContext);

		tags = AssetTagLocalServiceUtil.getGroupTags(group.getGroupId());

		Assert.assertEquals(initialTagsCount + 2, tags.size());

		AssetTagLocalServiceUtil.deleteTags(group.getGroupId());

		tags = AssetTagLocalServiceUtil.getGroupTags(group.getGroupId());

		Assert.assertEquals(initialTagsCount, tags.size());
	}

}