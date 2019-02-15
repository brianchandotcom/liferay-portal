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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.resource.v1_0.UserAccountResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseUserAccountResourceImpl
	implements UserAccountResource {

	@Override
	public Response deleteUserAccount(Long userAccountId) throws Exception {
		Response.ResponseBuilder responseBuilder = Response.ok();

			return responseBuilder.build();
	}

	@Override
	public UserAccount getMyUserAccount(Long myUserAccountId) throws Exception {
		return new UserAccount();
	}

	@Override
	public Page<UserAccount> getOrganizationUserAccountsPage(
			Long organizationId, Pagination pagination)
		throws Exception {

			return Page.of(Collections.emptyList());
	}

	@Override
	public UserAccount getUserAccount(Long userAccountId) throws Exception {
		return new UserAccount();
	}

	@Override
	public Page<UserAccount> getUserAccountsPage(
			String fullnamequery, Pagination pagination)
		throws Exception {

			return Page.of(Collections.emptyList());
	}

	@Override
	public Page<UserAccount> getWebSiteUserAccountsPage(
			Long webSiteId, Pagination pagination)
		throws Exception {

			return Page.of(Collections.emptyList());
	}

	@Override
	public UserAccount postUserAccount(UserAccount userAccount)
		throws Exception {

			return new UserAccount();
	}

	@Override
	public UserAccount postUserAccountBatchCreate(UserAccount userAccount)
		throws Exception {

			return new UserAccount();
	}

	@Override
	public UserAccount putUserAccount(
			Long userAccountId, UserAccount userAccount)
		throws Exception {

			return new UserAccount();
	}

	protected Response buildNoContentResponse() {
		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Throwable> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage acceptLanguage;

	@Context
	protected Company company;

}