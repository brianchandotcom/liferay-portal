/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupTable;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.ContactAccountGroup;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactAccountGroupDTOConverterContext;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactAccountGroupResource;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/contact-account-group.properties",
	scope = ServiceScope.PROTOTYPE, service = ContactAccountGroupResource.class
)
public class ContactAccountGroupResourceImpl
	extends BaseContactAccountGroupResourceImpl {

	@Override
	public Page<ContactAccountGroup> getContactAccountGroupsPage(
			String keywords, Pagination pagination, Sort[] sorts)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId());

		if (sorts == null) {
			sorts = new Sort[] {new Sort("name", Sort.STRING_TYPE, false)};
		}

		Sort sort = sorts[0];

		BaseModelSearchResult<AccountGroup> accountGroupBaseModelSearchResult =
			_accountGroupLocalService.searchAccountGroups(
				contextCompany.getCompanyId(), keywords, null,
				pagination.getStartPosition(), pagination.getEndPosition(),
				OrderByComparatorFactoryUtil.create(
					AccountGroupTable.INSTANCE.getTableName(),
					sort.getFieldName(), !sort.isReverse()));

		return Page.of(
			transform(
				accountGroupBaseModelSearchResult.getBaseModels(),
				accountGroup -> _contactAccountGroupDTOConverter.toDTO(
					new ContactAccountGroupDTOConverterContext(
						accountGroup.getAccountGroupId(),
						contextAcceptLanguage.getPreferredLocale(),
						analyticsConfiguration.syncedAccountGroupIds()),
					accountGroup)),
			pagination, accountGroupBaseModelSearchResult.getLength());
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference(
		target = "(component.name=com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactAccountGroupDTOConverter)"
	)
	private DTOConverter<AccountGroup, ContactAccountGroup>
		_contactAccountGroupDTOConverter;

}