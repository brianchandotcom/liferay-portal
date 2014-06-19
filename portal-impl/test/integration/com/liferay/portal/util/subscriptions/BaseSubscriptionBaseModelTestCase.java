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

package com.liferay.portal.util.subscriptions;

import com.liferay.portal.util.test.MailServiceTestUtil;

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
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(containerModelId);

		addSubscriptionBaseModel(baseModelId);

		updateEntry(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionBaseModel(baseModelId);

		updateEntry(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	protected abstract void addSubscriptionBaseModel(long baseModelId)
		throws Exception;

	protected abstract long updateEntry(long baseModelId) throws Exception;

}