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

import com.liferay.portal.test.mail.MailServiceTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jose Angel Jiménez
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