/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.permission;

import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * Contributes new filters for checking permissions on search results. Matches
 * are based on the fields indexed by the corresponding {@link
 * SearchPermissionFieldContributor}.
 *
 * <p>
 * Register implementations of this interface as OSGi components using the
 * service {@code SearchPermissionFilterContributor}.
 * </p>
 *
 * @author Sergio González
 */
@FunctionalInterface
public interface SearchPermissionFilterContributor {

	/**
	 * Contributes filters to check against indexed fields.
	 *
	 * @param booleanFilter the parent search result permission checking filter
	 * @param companyId the primary key of the company in the current search
	 *        context
	 * @param groupIds the primary keys of the groups in the current search
	 *        context
	 * @param userId the primary key of the user in the current search context
	 * @param permissionChecker the permission checker in use
	 * @param className the class name of the entity being permission checked
	 */
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className);

}