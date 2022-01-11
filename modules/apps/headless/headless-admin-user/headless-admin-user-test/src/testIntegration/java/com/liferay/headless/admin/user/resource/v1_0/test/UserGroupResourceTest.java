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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.UserGroup;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class UserGroupResourceTest extends BaseUserGroupResourceTestCase {

	@Override
	@Test
	public void testPostUserGroup() throws Exception {
		super.testPostUserGroup();

		UserGroup userGroup = randomUserGroup();

		UserGroup postUserGroup = userGroupResource.postUserGroup(userGroup);

		Assert.assertEquals(
			userGroup.getExternalReferenceCode(),
			postUserGroup.getExternalReferenceCode());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected UserGroup testDeleteUserGroup_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	@Override
	protected UserGroup testGetUserGroup_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	@Override
	protected UserGroup testGraphQLUserGroup_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	@Override
	protected UserGroup testPatchUserGroup_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	@Override
	protected UserGroup testPostUserGroup_addUserGroup(UserGroup userGroup)
		throws Exception {

		return _postUserGroup(userGroup);
	}

	@Override
	protected UserGroup testPutUserGroup_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	private UserGroup _postUserGroup() throws Exception {
		return _postUserGroup(randomUserGroup());
	}

	private UserGroup _postUserGroup(UserGroup userGroup) throws Exception {
		return userGroupResource.postUserGroup(userGroup);
	}

}