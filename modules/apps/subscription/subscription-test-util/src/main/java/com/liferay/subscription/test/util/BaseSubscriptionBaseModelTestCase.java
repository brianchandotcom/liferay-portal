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
public abstract class BaseSubscriptionBaseModelTestCase
	extends BaseSubscriptionTestCase {

	@Test
	public void testSubscriptionBaseModelWhenInContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(
			creatorUser.getUserId(),
			addContainerModel(
				creatorUser.getUserId(),
				BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT));

		addSubscriptionBaseModel(baseModelId);

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionBaseModelWhenInNoViewableContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(
			creatorUser.getUserId(),
			addContainerModel(
				creatorUser.getUserId(),
				BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT));

		addSubscriptionBaseModel(baseModelId);

		removeContainerModelResourceViewPermission();

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(
			creatorUser.getUserId(),
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionBaseModel(baseModelId);

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	protected abstract void addSubscriptionBaseModel(long baseModelId)
		throws Exception;

	protected abstract void removeContainerModelResourceViewPermission()
		throws Exception;

}