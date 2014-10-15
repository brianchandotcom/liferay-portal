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
public abstract class BaseSubscriptionClassTypeTestCase
	extends BaseSubscriptionTestCase {

	@Test
	public void testSubscriptionClassType() throws Exception {
		long classTypeId = addClassType();

		addSubscriptionClassType(classTypeId);

		addBaseModelWithClassType(
			PARENT_CONTAINER_MODEL_ID_DEFAULT, classTypeId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionDefaultClassType() throws Exception {
		Long classTypeId = getDefaultClassTypeId();

		if (classTypeId != null) {
			addSubscriptionClassType(classTypeId);

			addBaseModelWithClassType(
				PARENT_CONTAINER_MODEL_ID_DEFAULT, classTypeId);

			Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
		}
	}

	protected abstract long addBaseModelWithClassType(
			long containerModelId, long classTypeId)
		throws Exception;

	protected abstract long addClassType() throws Exception;

	protected abstract void addSubscriptionClassType(long classTypeId)
		throws Exception;

	protected abstract Long getDefaultClassTypeId() throws Exception;

}