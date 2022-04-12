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
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Drew Brokke
 */
@ProviderType
public interface AccountUserRetriever {

	public List<User> getAccountUsers(long accountEntryId);

	public long getAccountUsersCount(long accountEntryId);

	public BaseModelSearchResult<User> searchAccountRoleUsers(
			long accountEntryId, long accountRoleId, String keywords, int start,
			int end, OrderByComparator<User> orderByComparator)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #searchAccountUsers(long[], String, LinkedHashMap, int, int, int, String, boolean)}
	 */
	@Deprecated
	public BaseModelSearchResult<User> searchAccountUsers(
			long accountEntryId, String keywords, int status, int cur,
			int delta, String sortField, boolean reverse)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #searchAccountUsers(long[], String, LinkedHashMap, int, int, int, String, boolean)}
	 */
	@Deprecated
	public BaseModelSearchResult<User> searchAccountUsers(
			long accountEntryId, String[] emailAddressDomains, String keywords,
			int status, int cur, int delta, String sortField, boolean reverse)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #searchAccountUsers(long[], String, LinkedHashMap, int, int, int, String, boolean)}
	 */
	@Deprecated
	public BaseModelSearchResult<User> searchAccountUsers(
			long[] accountEntryIds, String keywords, int status, int cur,
			int delta, String sortField, boolean reverse)
		throws PortalException;

	public BaseModelSearchResult<User> searchAccountUsers(
			long[] accountEntryIds, String keywords,
			LinkedHashMap<String, Serializable> params, int status, int cur,
			int delta, String sortField, boolean reverse)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #searchAccountUsers(long[], String, LinkedHashMap, int, int, int, String, boolean)}
	 */
	@Deprecated
	public BaseModelSearchResult<User> searchAccountUsers(
			long[] accountEntryIds, String[] emailAddressDomains,
			String keywords, int status, int cur, int delta, String sortField,
			boolean reverse)
		throws PortalException;

}