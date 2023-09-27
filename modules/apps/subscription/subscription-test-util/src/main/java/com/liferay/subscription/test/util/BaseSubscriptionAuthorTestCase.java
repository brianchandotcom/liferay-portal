/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.subscription.test.util;

import com.liferay.portal.test.mail.MailServiceTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author José Ángel Jiménez
 */
public abstract class BaseSubscriptionAuthorTestCase
	extends BaseSubscriptionTestCase {

	@Test
	public void testSubscriptionForAuthorWhenAddingBaseModel()
		throws Exception {

		long containerModelId = addContainerModel(
			creatorUser.getUserId(),
			BaseSubscriptionTestCase.PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscription(creatorUser.getUserId(), containerModelId);

		addBaseModel(creatorUser.getUserId(), containerModelId);

		if (isSubscriptionForAuthorEnabled()) {
			Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
		}
		else {
			Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
		}
	}

	@Test
	public void testSubscriptionForAuthorWhenUpdatingBaseModel()
		throws Exception {

		long containerModelId = addContainerModel(
			creatorUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(
			creatorUser.getUserId(), containerModelId);

		addSubscription(creatorUser.getUserId(), containerModelId);

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		if (isSubscriptionForAuthorEnabled()) {
			Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
		}
		else {
			Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
		}
	}

	protected abstract void addSubscription(long userId, long containerModelId)
		throws Exception;

	protected boolean isSubscriptionForAuthorEnabled() {
		return false;
	}

}