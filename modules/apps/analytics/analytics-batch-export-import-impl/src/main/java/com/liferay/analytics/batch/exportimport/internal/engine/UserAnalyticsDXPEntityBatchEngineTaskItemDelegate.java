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
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.model.Users_GroupsTable;
import com.liferay.portal.kernel.model.Users_OrgsTable;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import java.io.Serializable;

import java.util.List;
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

		return Page.of(
			TransformUtil.transform(
				_userLocalService.<List<User>>dslQuery(
					_createSelectDSLQuery(
						contextCompany.getCompanyId(), filter, pagination)),
				user -> _dxpEntityDTOConverter.toDTO(user)),
			Pagination.of(pagination.getPage(), pagination.getPageSize()),
			_userLocalService.<Integer>dslQuery(
				_createCountDSLQuery(contextCompany.getCompanyId(), filter)));
	}

	private DSLQuery _createCountDSLQuery(long companyId, Filter filter) {
		JoinStep joinStep = DSLQueryFactoryUtil.count(
		).from(
			UserTable.INSTANCE
		);

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationRegistry.getAnalyticsConfiguration(
				companyId);

		if (!analyticsConfiguration.syncAllContacts()) {
			String[] syncedOrganizationIds =
				analyticsConfiguration.syncedOrganizationIds();

			if (!ArrayUtil.isEmpty(syncedOrganizationIds)) {
				joinStep = joinStep.innerJoinON(
					Users_OrgsTable.INSTANCE,
					Users_OrgsTable.INSTANCE.userId.eq(
						UserTable.INSTANCE.userId
					).and(
						Users_OrgsTable.INSTANCE.organizationId.in(
							TransformUtil.transform(
								syncedOrganizationIds, Long::parseLong,
								Long.class))
					));
			}

			String[] syncedGroupIds =
				analyticsConfiguration.syncedUserGroupIds();

			if (!ArrayUtil.isEmpty(syncedGroupIds)) {
				joinStep = joinStep.innerJoinON(
					Users_GroupsTable.INSTANCE,
					Users_GroupsTable.INSTANCE.userId.eq(
						UserTable.INSTANCE.userId
					).and(
						Users_GroupsTable.INSTANCE.groupId.in(
							TransformUtil.transform(
								syncedGroupIds, Long::parseLong, Long.class))
					));
			}
		}

		return joinStep.where(
			buildPredicate(
				UserTable.INSTANCE, companyId,
				UserTable.INSTANCE.screenName.neq(
					AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN
				).and(
					UserTable.INSTANCE.status.neq(
						WorkflowConstants.STATUS_INACTIVE)
				),
				filter));
	}

	private DSLQuery _createSelectDSLQuery(
		long companyId, Filter filter, Pagination pagination) {

		JoinStep joinStep = DSLQueryFactoryUtil.select(
		).from(
			UserTable.INSTANCE
		);

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationRegistry.getAnalyticsConfiguration(
				companyId);

		if (!analyticsConfiguration.syncAllContacts()) {
			String[] syncedOrganizationIds =
				analyticsConfiguration.syncedOrganizationIds();

			if (!ArrayUtil.isEmpty(syncedOrganizationIds)) {
				joinStep = joinStep.innerJoinON(
					Users_OrgsTable.INSTANCE,
					Users_OrgsTable.INSTANCE.userId.eq(
						UserTable.INSTANCE.userId
					).and(
						Users_OrgsTable.INSTANCE.organizationId.in(
							TransformUtil.transform(
								syncedOrganizationIds, Long::parseLong,
								Long.class))
					));
			}

			String[] syncedGroupIds =
				analyticsConfiguration.syncedUserGroupIds();

			if (!ArrayUtil.isEmpty(syncedGroupIds)) {
				joinStep = joinStep.innerJoinON(
					Users_GroupsTable.INSTANCE,
					Users_GroupsTable.INSTANCE.userId.eq(
						UserTable.INSTANCE.userId
					).and(
						Users_GroupsTable.INSTANCE.groupId.in(
							TransformUtil.transform(
								syncedGroupIds, Long::parseLong, Long.class))
					));
			}
		}

		return joinStep.where(
			buildPredicate(
				UserTable.INSTANCE, companyId,
				UserTable.INSTANCE.screenName.neq(
					AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN
				).and(
					UserTable.INSTANCE.status.neq(
						WorkflowConstants.STATUS_INACTIVE)
				),
				filter)
		).limit(
			pagination.getPage() * pagination.getPageSize(),
			(pagination.getPage() + 1) * pagination.getPageSize()
		);
	}

	@Reference
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

	@Reference(target = DTOConverterConstants.DXP_ENTITY_DTO_CONVERTER)
	private DTOConverter<BaseModel<?>, DXPEntity> _dxpEntityDTOConverter;

	@Reference
	private UserLocalService _userLocalService;

}