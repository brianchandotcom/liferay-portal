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

package com.liferay.portlet.subscriptions.test;

import com.liferay.portal.kernel.test.util.MailServiceTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public abstract class BaseSubscriptionBaseModelTestCase
	extends BaseSubscriptionTestCase {

	@Test
	public void testSubscriptionBaseModelWhenInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(containerModelId);

		addSubscriptionBaseModel(baseModelId);

		updateBaseModel(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionBaseModelWhenInNoViewableContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(containerModelId);

		addSubscriptionBaseModel(baseModelId);

		removeContainerModelResourceViewPermission();

		updateBaseModel(baseModelId);

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionBaseModel(baseModelId);

		updateBaseModel(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	protected abstract void addSubscriptionBaseModel(long baseModelId)
		throws Exception;

	protected abstract void removeContainerModelResourceViewPermission()
		throws Exception;

}