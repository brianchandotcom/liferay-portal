/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webserver.WebServerServletToken;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = {
		"application.name=Liferay.Headless.Admin.User",
		"dto.class.name=com.liferay.account.model.AccountEntry", "version=v1.0"
	},
	service = DTOConverter.class
)
public class AccountResourceDTOConverter
	implements DTOConverter<AccountEntry, Account> {

	@Override
	public String getContentType() {
		return Account.class.getSimpleName();
	}

	@Override
	public AccountEntry getObject(String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				externalReferenceCode, CompanyThreadLocal.getCompanyId());

		if (accountEntry == null) {
			accountEntry = _accountEntryLocalService.getAccountEntry(
				GetterUtil.getLong(externalReferenceCode));
		}

		return accountEntry;
	}

	@Override
	public Account toDTO(
		DTOConverterContext dtoConverterContext, AccountEntry accountEntry) {

		if (accountEntry == null) {
			return null;
		}

		return new Account() {
			{
				setActions(dtoConverterContext::getActions);
				setCustomFields(
					() -> CustomFieldsUtil.toCustomFields(
						dtoConverterContext.isAcceptAllLanguages(),
						AccountEntry.class.getName(),
						accountEntry.getAccountEntryId(),
						accountEntry.getCompanyId(),
						dtoConverterContext.getLocale()));
				setDateCreated(accountEntry::getCreateDate);
				setDateModified(accountEntry::getModifiedDate);
				setDefaultBillingAddressId(
					accountEntry::getDefaultBillingAddressId);
				setDefaultShippingAddressId(
					accountEntry::getDefaultShippingAddressId);
				setDescription(accountEntry::getDescription);
				setDomains(() -> StringUtil.split(accountEntry.getDomains()));
				setExternalReferenceCode(
					accountEntry::getExternalReferenceCode);
				setId(accountEntry::getAccountEntryId);
				setLogoId(accountEntry::getLogoId);
				setLogoURL(
					() -> StringBundler.concat(
						"/image/organization_logo?img_id=",
						accountEntry.getLogoId(), "&t=",
						_webServerServletToken.getToken(
							accountEntry.getLogoId())));
				setName(accountEntry::getName);
				setNumberOfUsers(
					() ->
						(int)
							_accountEntryUserRelLocalService.
								getAccountEntryUserRelsCountByAccountEntryId(
									accountEntry.getAccountEntryId()));
				setOrganizationIds(
					() -> TransformUtil.transformToArray(
						_accountEntryOrganizationRelLocalService.
							getAccountEntryOrganizationRels(
								accountEntry.getAccountEntryId()),
						AccountEntryOrganizationRel::getOrganizationId,
						Long.class));
				setParentAccountId(accountEntry::getParentAccountEntryId);
				setStatus(accountEntry::getStatus);
				setTaxId(accountEntry::getTaxIdNumber);
				setType(() -> Account.Type.create(accountEntry.getType()));
			}
		};
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private WebServerServletToken _webServerServletToken;

}