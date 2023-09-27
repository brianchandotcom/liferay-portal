/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public BaseModelSearchResult<User> searchAccountUsers(
			long[] accountEntryIds, String keywords,
			LinkedHashMap<String, Serializable> params, int status, int cur,
			int delta, String sortField, boolean reverse)
		throws PortalException;

}