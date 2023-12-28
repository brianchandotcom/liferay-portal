/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Account;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = {
		"application.name=Liferay.Headless.Commerce.Admin.Order",
		"dto.class.name=com.liferay.account.model.AccountEntry", "version=v1.0"
	},
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

		if ((Long)dtoConverterContext.getId() ==
				AccountConstants.ACCOUNT_ENTRY_ID_GUEST) {

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
				setCustomFields(
					() -> {
						ExpandoBridge expandoBridge =
							accountEntry.getExpandoBridge();

						return expandoBridge.getAttributes();
					});
				setEmailAddress(accountEntry::getEmailAddress);
				setExternalReferenceCode(
					accountEntry::getExternalReferenceCode);
				setId(accountEntry::getAccountEntryId);
				setLogoId(accountEntry::getLogoId);
				setName(accountEntry::getName);
				setRoot(
					() ->
						accountEntry.getParentAccountEntryId() ==
							AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);
				setTaxId(accountEntry::getTaxIdNumber);
				setType(() -> _getCommerceAccountType(accountEntry.getType()));
			}
		};
	}

	private Integer _getCommerceAccountType(String accountEntryType) {
		if (Objects.equals(
				accountEntryType,
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS)) {

			return 2;
		}
		else if (Objects.equals(
					accountEntryType,
					AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST)) {

			return 0;
		}
		else if (Objects.equals(
					accountEntryType,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON)) {

			return 1;
		}

		return 0;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}