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

package com.liferay.headless.foundation.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-foundation/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface UserAccountResource {

	public UserAccount getMyUserAccount(
				Long myUserAccountId)
			throws Exception;
	public Page<UserAccount> getOrganizationUserAccountsPage(
				Long organizationId,Pagination pagination)
			throws Exception;
	public Page<UserAccount> getUserAccountsPage(
				String fullnamequery,Pagination pagination)
			throws Exception;
	public UserAccount postUserAccount(
				UserAccount userAccount)
			throws Exception;
	public boolean deleteUserAccount(
				Long userAccountId)
			throws Exception;
	public UserAccount getUserAccount(
				Long userAccountId)
			throws Exception;
	public UserAccount putUserAccount(
				Long userAccountId,UserAccount userAccount)
			throws Exception;
	public Page<UserAccount> getWebSiteUserAccountsPage(
				Long webSiteId,Pagination pagination)
			throws Exception;

	public void setContextCompany(Company contextCompany);

}