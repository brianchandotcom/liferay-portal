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

package com.liferay.user.associated.data.test.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.user.associated.data.entity.UADEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Noah Sherrill
 */
public abstract class BaseUADEntityTestCase {

	@Before
	public void setUp() throws Exception {
		uadEntity = createUADEntity(_USER_ID, _UAD_ENTITY_ID);
	}

	@Test
	public void testGetUADEntityId() throws Exception {
		Assert.assertEquals(_UAD_ENTITY_ID, uadEntity.getUADEntityId());
	}

	@Test
	public void testGetUADRegistryKey() throws Exception {
		Assert.assertEquals(getUADRegistryKey(), uadEntity.getUADRegistryKey());
	}

	@Test
	public void testGetUserId() throws Exception {
		Assert.assertEquals(_USER_ID, uadEntity.getUserId());
	}

	protected abstract UADEntity createUADEntity(
		long userId, String uadEntityId);

	protected abstract String getUADRegistryKey();

	protected UADEntity uadEntity;

	private static final String _UAD_ENTITY_ID = RandomTestUtil.randomString();

	private static final long _USER_ID = RandomTestUtil.randomLong();

}