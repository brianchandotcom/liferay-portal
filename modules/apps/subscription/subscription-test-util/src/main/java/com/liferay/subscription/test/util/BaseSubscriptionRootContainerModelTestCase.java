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
public abstract class BaseSubscriptionRootContainerModelTestCase
	extends BaseSubscriptionTestCase {

	@Test
	public void testSubscriptionRootContainerModelWhenAddingBaseModelInContainerModel()
		throws Exception {

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(
			creatorUser.getUserId(),
			addContainerModel(
				creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT));

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenAddingBaseModelInRootContainerModel()
		throws Exception {

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(
			creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenAddingBaseModelInSubcontainerModel()
		throws Exception {

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long subcontainerModelId = addContainerModel(
			creatorUser.getUserId(),
			addContainerModel(
				creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT));

		addBaseModel(creatorUser.getUserId(), subcontainerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenUpdatingBaseModelInContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(
			creatorUser.getUserId(),
			addContainerModel(
				creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT));

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenUpdatingBaseModelInRootContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(
			creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenUpdatingBaseModelInSubcontainerModel()
		throws Exception {

		long subcontainerModelId = addContainerModel(
			creatorUser.getUserId(),
			addContainerModel(
				creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT));

		long baseModelId = addBaseModel(
			creatorUser.getUserId(), subcontainerModelId);

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	protected abstract void addSubscriptionContainerModel(long containerModelId)
		throws Exception;

}