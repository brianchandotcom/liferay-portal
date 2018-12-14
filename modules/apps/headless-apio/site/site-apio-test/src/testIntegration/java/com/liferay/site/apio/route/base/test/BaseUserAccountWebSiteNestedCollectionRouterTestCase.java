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

package com.liferay.site.apio.route.base.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.lang.reflect.Method;

/**
 * @author Cristina González
 */
public abstract class BaseUserAccountWebSiteNestedCollectionRouterTestCase {

	protected abstract NestedCollectionRouter getNestedCollectionResource();

	protected PageItems<Group> getPageItems(Pagination pagination, long userId)
		throws Exception {

		NestedCollectionRouter nestedCollectionResource =
			getNestedCollectionResource();

		Class<? extends NestedCollectionRouter> clazz =
			nestedCollectionResource.getClass();

		Class<?> superclass = clazz.getSuperclass();

		Method method = superclass.getDeclaredMethod(
			"_getPageItems", Pagination.class, long.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			nestedCollectionResource, pagination, userId);
	}

	protected User getUser() {
		return _user;
	}

	protected void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();

		_group2 = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser(
			_group1.getGroupId(), _group2.getGroupId());
	}

	protected void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(_group1);
		GroupTestUtil.deleteGroup(_group2);
		UserLocalServiceUtil.deleteUser(_user);
	}

	private Group _group1;
	private Group _group2;
	private User _user;

}