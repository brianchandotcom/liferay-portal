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

package com.liferay.analytics.dxp.entities.exporter.internal.retriever;

import com.liferay.analytics.dxp.entities.exporter.dto.v1_0.DXPEntity;
import com.liferay.analytics.dxp.entities.exporter.retriever.DXPEntityRetriever;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true,
	property = "dxp.entity.retriever.class.name=com.liferay.portal.kernel.model.User",
	service = DXPEntityRetriever.class
)
public class UserDXPEntityRetriever implements DXPEntityRetriever {

	@Override
	public Page<DXPEntity> getDXPEntitiesPage(
			long companyId, Pagination pagination,
			UnsafeFunction<BaseModel<?>, DXPEntity, Exception>
				transformUnsafeFunction)
		throws Exception {

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(),
			_createBooleanFilter(companyId), User.class.getName(), null,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(companyId), null,
			document -> transformUnsafeFunction.apply(
				_userLocalService.getUser(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	private BooleanFilter _createBooleanFilter(long companyId) {
		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(
			new TermFilter(
				"screenName",
				AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN),
			BooleanClauseOccur.MUST_NOT);
		booleanFilter.add(
			new TermFilter(
				"status", String.valueOf(WorkflowConstants.STATUS_INACTIVE)),
			BooleanClauseOccur.MUST_NOT);

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		if (analyticsConfiguration.syncAllContacts()) {
			return booleanFilter;
		}

		BooleanFilter innerBooleanFilter = new BooleanFilter();

		String[] syncedOrganizationIds =
			analyticsConfiguration.syncedOrganizationIds();

		if (!ArrayUtil.isEmpty(syncedOrganizationIds)) {
			TermsFilter termsFilter = new TermsFilter("organizationIds");

			termsFilter.addValues(syncedOrganizationIds);

			innerBooleanFilter.add(termsFilter);
		}

		String[] syncedGroupIds = analyticsConfiguration.syncedUserGroupIds();

		if (!ArrayUtil.isEmpty(syncedGroupIds)) {
			TermsFilter termsFilter = new TermsFilter("userGroupIds");

			termsFilter.addValues(syncedGroupIds);

			innerBooleanFilter.add(termsFilter);
		}

		booleanFilter.add(innerBooleanFilter, BooleanClauseOccur.MUST);

		return booleanFilter;
	}

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

	@Reference
	private UserLocalService _userLocalService;

}