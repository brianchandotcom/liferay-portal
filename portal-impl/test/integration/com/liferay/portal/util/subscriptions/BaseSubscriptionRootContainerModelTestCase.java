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
public abstract class BaseSubscriptionRootContainerModelTestCase
	extends BaseSubscriptionTestCase {

	@Test
	public void testSubscriptionRootContainerModelWhenAddingBaseModelInContainerModel()
		throws Exception {

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long containerModelId = addContainerModel(
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(containerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenAddingBaseModelInRootContainerModel()
		throws Exception {

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenAddingBaseModelInSubcontainerModel()
		throws Exception {

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long containerModelId = addContainerModel(
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long subcontainerModelId = addContainerModel(containerModelId);

		addBaseModel(subcontainerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenUpdatingBaseModelInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(containerModelId);

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		updateBaseModel(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenUpdatingBaseModelInRootContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		updateBaseModel(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenUpdatingBaseModelInSubcontainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long subcontainerModelId = addContainerModel(containerModelId);

		long baseModelId = addBaseModel(subcontainerModelId);

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		updateBaseModel(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	protected abstract void addSubscriptionContainerModel(long containerModelId)
		throws Exception;

}