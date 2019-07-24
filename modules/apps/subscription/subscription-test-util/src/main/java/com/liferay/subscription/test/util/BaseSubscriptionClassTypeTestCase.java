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

package com.liferay.subscription.test.util;

import com.liferay.portal.test.mail.MailServiceTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public abstract class BaseSubscriptionClassTypeTestCase
	extends BaseSubscriptionTestCase {

	@Test
	public void testSubscriptionClassTypeWhenAddingBaseModel()
		throws Exception {

		long classTypeId = addClassType();

		addSubscriptionClassType(classTypeId);

		addBaseModelWithClassType(
			PARENT_CONTAINER_MODEL_ID_DEFAULT, classTypeId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionClassTypeWhenUpdatingBaseModel()
		throws Exception {

		long classTypeId = addClassType();

		long baseModelId = addBaseModelWithClassType(
			PARENT_CONTAINER_MODEL_ID_DEFAULT, classTypeId);

		addSubscriptionClassType(classTypeId);

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionDefaultClassTypeWhenAddingBaseModel()
		throws Exception {

		Long classTypeId = getDefaultClassTypeId();

		addSubscriptionClassType(classTypeId);

		addBaseModelWithClassType(
			PARENT_CONTAINER_MODEL_ID_DEFAULT, classTypeId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());

		deleteSubscriptionClassType(classTypeId);
	}

	@Test
	public void testSubscriptionDefaultClassTypeWhenUpdatingBaseModel()
		throws Exception {

		Long classTypeId = getDefaultClassTypeId();

		long baseModelId = addBaseModelWithClassType(
			PARENT_CONTAINER_MODEL_ID_DEFAULT, classTypeId);

		addSubscriptionClassType(classTypeId);

		updateBaseModel(creatorUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());

		deleteSubscriptionClassType(classTypeId);
	}

	protected abstract long addBaseModelWithClassType(
			long containerModelId, long classTypeId)
		throws Exception;

	protected abstract long addClassType() throws Exception;

	protected abstract void addSubscriptionClassType(long classTypeId)
		throws Exception;

	protected abstract void deleteSubscriptionClassType(long classTypeId)
		throws Exception;

	protected abstract Long getDefaultClassTypeId() throws Exception;

}