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

package com.liferay.account.retriever;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;

import java.util.List;

/**
 * @author Drew Brokke
 */
public interface AccountUserRetriever {

	public List<User> getAccountUsers(long accountEntryId);

	public long getAccountUsersCount(long accountEntryId);

	public BaseModelSearchResult<User> searchAccountUsers(
			long accountEntryId, String keywords, int status, int cur,
			int delta, String sortField, boolean reverse)
		throws PortalException;

	public BaseModelSearchResult<User> searchAccountUsers(
			long accountEntryId, String[] emailAddressDomains, String keywords,
			int status, int cur, int delta, String sortField, boolean reverse)
		throws PortalException;

	public BaseModelSearchResult<User> searchAccountUsers(
			long[] accountEntryIds, String keywords, int status, int cur,
			int delta, String sortField, boolean reverse)
		throws PortalException;

}