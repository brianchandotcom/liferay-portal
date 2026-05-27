/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.MatchAllQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.search.spi.model.permission.contributor.SearchPermissionFilterContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import org.osgi.service.component.annotations.Component;

/**
 * @author Nícolas Moura
 */
@Component(service = SearchPermissionFilterContributor.class)
public class KaleoTaskInstanceTokenSearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if (!className.equals(KaleoTaskInstanceToken.class.getName())) {
			return;
		}

		booleanFilter.add(
			new QueryFilter(new MatchAllQuery()), BooleanClauseOccur.SHOULD);
	}

}