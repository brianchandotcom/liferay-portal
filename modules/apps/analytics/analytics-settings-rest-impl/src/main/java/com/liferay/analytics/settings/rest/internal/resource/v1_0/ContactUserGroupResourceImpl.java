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

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.ContactUserGroup;
import com.liferay.analytics.settings.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactAccountGroupDTOConverter;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactOrganizationDTOConverter;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactUserGroupDTOConverter;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactUserGroupDTOConverterContext;
import com.liferay.analytics.settings.rest.internal.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactUserGroupResource;
import com.liferay.portal.kernel.model.UserGroupTable;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.LinkedHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/contact-user-group.properties",
	scope = ServiceScope.PROTOTYPE, service = ContactUserGroupResource.class
)
public class ContactUserGroupResourceImpl
	extends BaseContactUserGroupResourceImpl {

	@Override
	public Page<ContactUserGroup> getContactUserGroupsPage(
			String keywords, Pagination pagination, Sort[] sorts)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId());

		if (sorts == null) {
			sorts = new Sort[] {new Sort("name", Sort.STRING_TYPE, false)};
		}

		Sort sort = sorts[0];

		return Page.of(
			transform(
				_userGroupLocalService.search(
					contextCompany.getCompanyId(), keywords, _getParams(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					OrderByComparatorFactoryUtil.create(
						UserGroupTable.INSTANCE.getTableName(),
						sort.getFieldName(), !sort.isReverse())),
				userGroup -> _contactUserGroupDTOConverter.toDTO(
					new ContactUserGroupDTOConverterContext(
						userGroup.getUserGroupId(),
						contextAcceptLanguage.getPreferredLocale(),
						analyticsConfiguration.syncedUserGroupIds()),
					userGroup)),
			pagination,
			_userGroupLocalService.searchCount(
				contextCompany.getCompanyId(), keywords, _getParams()));
	}

	private LinkedHashMap<String, Object> _getParams() {
		return LinkedHashMapBuilder.<String, Object>put(
			"active", Boolean.TRUE
		).build();
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private ContactAccountGroupDTOConverter _contactAccountGroupDTOConverter;

	@Reference
	private ContactOrganizationDTOConverter _contactOrganizationDTOConverter;

	@Reference
	private ContactUserGroupDTOConverter _contactUserGroupDTOConverter;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}