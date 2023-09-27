/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.engine;

import com.liferay.analytics.batch.exportimport.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = "batch.engine.task.item.delegate.name=user-analytics-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class UserAnalyticsDXPEntityBatchEngineTaskItemDelegate
	extends BaseAnalyticsDXPEntityBatchEngineTaskItemDelegate<DXPEntity> {

	@Override
	public Page<DXPEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		com.liferay.portal.vulcan.pagination.Pagination vulcanPagination =
			com.liferay.portal.vulcan.pagination.Pagination.of(
				pagination.getPage(), pagination.getPageSize());

		com.liferay.portal.vulcan.pagination.Page<DXPEntity> page =
			SearchUtil.search(
				null, booleanQuery -> booleanQuery.getPreBooleanFilter(),
				_createBooleanFilter(contextCompany.getCompanyId(), filter),
				User.class.getName(), null, vulcanPagination,
				queryConfig -> queryConfig.setSelectedFieldNames(
					Field.ENTRY_CLASS_PK),
				this::getSearchContext, sorts,
				document -> _dxpEntityDTOConverter.toDTO(
					_userLocalService.getUser(
						GetterUtil.getLong(
							document.get(Field.ENTRY_CLASS_PK)))));

		return Page.of(
			page.getItems(),
			Pagination.of(pagination.getPage(), pagination.getPageSize()),
			page.getTotalCount());
	}

	private BooleanFilter _createBooleanFilter(long companyId, Filter filter) {
		BooleanFilter booleanFilter = new BooleanFilter();

		if (filter != null) {
			booleanFilter.add(filter, BooleanClauseOccur.MUST);
		}

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
			_analyticsConfigurationRegistry.getAnalyticsConfiguration(
				companyId);

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
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

	@Reference(target = DTOConverterConstants.DXP_ENTITY_DTO_CONVERTER)
	private DTOConverter<BaseModel<?>, DXPEntity> _dxpEntityDTOConverter;

	@Reference
	private UserLocalService _userLocalService;

}