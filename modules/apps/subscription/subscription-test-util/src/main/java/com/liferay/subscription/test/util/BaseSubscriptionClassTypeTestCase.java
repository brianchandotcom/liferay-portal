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