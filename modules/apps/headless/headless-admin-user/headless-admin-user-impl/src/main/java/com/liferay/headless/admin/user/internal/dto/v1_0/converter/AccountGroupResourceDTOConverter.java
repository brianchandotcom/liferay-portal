/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.converter;

import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.headless.admin.user.dto.v1_0.AccountGroup;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(
	property = {
		"application.name=Liferay.Headless.Admin.User",
		"dto.class.name=com.liferay.account.model.AccountGroup"
	},
	service = DTOConverter.class
)
public class AccountGroupResourceDTOConverter
	implements DTOConverter
		<com.liferay.account.model.AccountGroup, AccountGroup> {

	@Override
	public String getContentType() {
		return AccountGroup.class.getSimpleName();
	}

	@Override
	public com.liferay.account.model.AccountGroup getObject(
			String externalReferenceCode)
		throws Exception {

		com.liferay.account.model.AccountGroup accountGroup =
			_accountGroupLocalService.fetchAccountGroupByExternalReferenceCode(
				externalReferenceCode, CompanyThreadLocal.getCompanyId());

		if (accountGroup == null) {
			accountGroup = _accountGroupLocalService.getAccountGroup(
				GetterUtil.getLong(externalReferenceCode));
		}

		return accountGroup;
	}

	@Override
	public AccountGroup toDTO(
		DTOConverterContext dtoConverterContext,
		com.liferay.account.model.AccountGroup accountGroup) {

		if (accountGroup == null) {
			return null;
		}

		return new AccountGroup() {
			{
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					com.liferay.account.model.AccountGroup.class.getName(),
					accountGroup.getAccountGroupId(),
					accountGroup.getCompanyId(),
					dtoConverterContext.getLocale());
				externalReferenceCode = accountGroup.getExternalReferenceCode();
				id = accountGroup.getAccountGroupId();
				name = accountGroup.getName();
			}
		};
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

}