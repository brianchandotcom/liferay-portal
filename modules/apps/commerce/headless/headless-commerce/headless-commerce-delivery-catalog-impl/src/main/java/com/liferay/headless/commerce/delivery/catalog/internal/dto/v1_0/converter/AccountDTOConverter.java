/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Account;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webserver.WebServerServletToken;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = "dto.class.name=com.liferay.account.model.AccountEntry",
	service = DTOConverter.class
)
public class AccountDTOConverter
	implements DTOConverter<AccountEntry, Account> {

	@Override
	public String getContentType() {
		return Account.class.getSimpleName();
	}

	@Override
	public Account toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		AccountEntry accountEntry;

		if ((Long)dtoConverterContext.getId() == -1) {
			User user = dtoConverterContext.getUser();

			if (user == null) {
				user = _userLocalService.getUserById(
					PrincipalThreadLocal.getUserId());
			}

			accountEntry = _accountEntryLocalService.getGuestAccountEntry(
				user.getCompanyId());
		}
		else {
			accountEntry = _accountEntryLocalService.getAccountEntry(
				(Long)dtoConverterContext.getId());
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
				setOrganizationIds(
					() -> TransformUtil.transformToArray(
						_accountEntryOrganizationRelLocalService.
							getAccountEntryOrganizationRels(
								accountEntry.getAccountEntryId()),
						AccountEntryOrganizationRel::getOrganizationId,
						Long.class));
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
	private UserLocalService _userLocalService;

	@Reference
	private WebServerServletToken _webServerServletToken;

}