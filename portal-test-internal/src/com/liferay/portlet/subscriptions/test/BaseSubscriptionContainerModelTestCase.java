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
public abstract class BaseSubscriptionContainerModelTestCase
	extends BaseSubscriptionTestCase {

	@Test
	public void
			testSubscriptionContainerModelWhenAddingBaseModelInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(containerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void
			testSubscriptionContainerModelWhenAddingBaseModelInRootContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void
			testSubscriptionContainerModelWhenAddingBaseModelInSubcontainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		long subcontainerModelId = addContainerModel(containerModelId);

		addBaseModel(subcontainerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void
			testSubscriptionContainerModelWhenUpdatingBaseModelInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(containerModelId);

		addSubscriptionContainerModel(containerModelId);

		updateBaseModel(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void
			testSubscriptionContainerModelWhenUpdatingBaseModelInRootContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		updateBaseModel(baseModelId);

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void
			testSubscriptionContainerModelWhenUpdatingBaseModelInSubcontainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long subcontainerModelId = addContainerModel(containerModelId);

		long baseModelId = addBaseModel(subcontainerModelId);

		addSubscriptionContainerModel(containerModelId);

		updateBaseModel(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	protected abstract void addSubscriptionContainerModel(long containerModelId)
		throws Exception;

}