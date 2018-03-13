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

package com.liferay.blogs.uad.entity;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.uad.constants.BlogsUADConstants;

import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.test.util.BaseUADEntityTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BlogsEntryUADEntityTest extends BaseUADEntityTestCase {
	@Before
	@Override
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		super.setUp();
	}

	@Test
	public void testGetBlogsEntry() throws Exception {
		BlogsEntryUADEntity blogsEntryUADEntity = (BlogsEntryUADEntity)uadEntity;

		Assert.assertEquals(_blogsEntry, blogsEntryUADEntity.getBlogsEntry());
	}

	@Override
	protected UADEntity createUADEntity(long userId, String uadEntityId) {
		return new BlogsEntryUADEntity(userId, uadEntityId, _blogsEntry);
	}

	@Override
	protected String getUADRegistryKey() {
		return BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY;
	}

	@Mock
	private BlogsEntry _blogsEntry;
}