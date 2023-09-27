/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis;

import java.util.Set;

/**
 * @author Alexander Chow
 */
public interface Session {

	public void setDefaultContext(
		Set<String> filter, boolean includeAcls,
		boolean includeAllowableActions, boolean includePolicies,
		String includeRelationships, Set<String> renditionFilter,
		boolean includePathSegments, String orderBy, boolean cacheEnabled,
		int maxItemsPerPage);

}