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

package com.liferay.headless.foundation.internal.resource;

import com.liferay.headless.foundation.dto.UserAccount;
import com.liferay.headless.foundation.resource.UserAccountResource;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseUserAccountResourceImpl
	implements UserAccountResource {

	@Override
	public Response deleteUserAccount(Long id) throws Exception {
		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public UserAccount getMyUserAccount(Long id) throws Exception {
		return new UserAccount();
	}

	@Override
	public Page<UserAccount> getMyUserAccountPage(Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Page<UserAccount> getOrganizationUserAccountPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public UserAccount getUserAccount(Long id) throws Exception {
		return new UserAccount();
	}

	@Override
	public Page<UserAccount> getUserAccountPage(
			String fullnamequery, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Page<UserAccount> getWebSiteUserAccountPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public UserAccount postUserAccount() throws Exception {
		return new UserAccount();
	}

	@Override
	public UserAccount postUserAccountBatchCreate() throws Exception {
		return new UserAccount();
	}

	@Override
	public UserAccount putUserAccount(Long id) throws Exception {
		return new UserAccount();
	}

	protected <T, R> List<R> transform(
		List<T> list, Function<T, R> transformFunction) {

		return TransformUtil.transform(list, transformFunction);
	}

}