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

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.ContactUserGroup;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactUserGroupDTOConverter;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactUserGroupDTOConverterContext;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactUserGroupResource;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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

		BaseModelSearchResult<UserGroup> userGroupBaseModelSearchResult =
			_userGroupLocalService.searchUserGroups(
				contextCompany.getCompanyId(), keywords, null,
				pagination.getStartPosition(), pagination.getEndPosition(),
				sort);

		return Page.of(
			transform(
				userGroupBaseModelSearchResult.getBaseModels(),
				userGroup -> _contactUserGroupDTOConverter.toDTO(
					new ContactUserGroupDTOConverterContext(
						userGroup.getUserGroupId(),
						contextAcceptLanguage.getPreferredLocale(),
						analyticsConfiguration.syncedUserGroupIds()),
					userGroup)),
			pagination, userGroupBaseModelSearchResult.getLength());
	}

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private ContactUserGroupDTOConverter _contactUserGroupDTOConverter;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}