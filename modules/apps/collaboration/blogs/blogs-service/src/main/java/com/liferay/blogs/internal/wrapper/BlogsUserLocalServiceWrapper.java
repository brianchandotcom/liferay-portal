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

package com.liferay.blogs.internal.wrapper;

import com.liferay.blogs.service.BlogsStatsUserLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class BlogsUserLocalServiceWrapper extends UserLocalServiceWrapper {

	public BlogsUserLocalServiceWrapper() {
		super(null);
	}

	public BlogsUserLocalServiceWrapper(UserLocalService userLocalService) {
		super(userLocalService);
	}

	public User deleteUser(User user) throws PortalException {
		_blogsStatsUserLocalService.deleteStatsUserByUserId(user.getUserId());

		return super.deleteUser(user);
	}

	@Reference(unbind = "-")
	protected void setBlogsStatsUserLocalService(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {

		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	private BlogsStatsUserLocalService _blogsStatsUserLocalService;

}