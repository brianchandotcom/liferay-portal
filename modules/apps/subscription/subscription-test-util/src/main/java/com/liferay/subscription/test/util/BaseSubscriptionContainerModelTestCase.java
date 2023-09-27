/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.subscription.test.util;

import com.liferay.portal.test.mail.MailServiceTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public abstract class BaseSubscriptionContainerModelTestCase
	extends BaseSubscriptionTestCase {

	@Test
	public void testSubscriptionContainerModelWhenAddingBaseModelInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			creatorUser.getUserId(),
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(creatorUser.getUserId(), containerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenAddingBaseModelInRootContainerModel()
		throws Exception {

		addSubscriptionContainerModel(
			addContainerModel(
				creatorUser.getUserId(),
				BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT));

		addBaseModel(
			creatorUser.getUserId(),
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenAddingBaseModelInSubcontainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			creatorUser.getUserId(),
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(
			creatorUser.getUserId(),
			addContainerModel(creatorUser.getUserId(), containerModelId));

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenUpdatingBaseModelInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(
			creatorUser.getUserId(), containerModelId);

		addSubscriptionContainerModel(containerModelId);

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenUpdatingBaseModelInRootContainerModel()
		throws Exception {

		addSubscriptionContainerModel(
			addContainerModel(
				creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT));

		updateBaseModel(
			creatorUser.getUserId(),
			addBaseModel(
				creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT));

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenUpdatingBaseModelInSubcontainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long subcontainerModelId = addContainerModel(
			creatorUser.getUserId(), containerModelId);

		long baseModelId = addBaseModel(
			creatorUser.getUserId(), subcontainerModelId);

		addSubscriptionContainerModel(containerModelId);

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	protected abstract void addSubscriptionContainerModel(long containerModelId)
		throws Exception;

}