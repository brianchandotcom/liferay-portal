/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.converter;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.dto.v1_0.AccountContactInformation;
import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.EmailAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.PhoneUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.PostalAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.WebUrlUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
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
			DTOConverterContext dtoConverterContext, AccountEntry accountEntry)
		throws Exception {

		if (accountEntry == null) {
			return null;
		}

		return new Account() {
			{
				setAccountContactInformation(
					() -> _toAccountContactInformation(
						accountEntry, dtoConverterContext));
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
				setDefaultBillingAddressExternalReferenceCode(
					() -> {
						Address address = _addressLocalService.fetchAddress(
							accountEntry.getDefaultBillingAddressId());

						if (address == null) {
							return null;
						}

						return address.getExternalReferenceCode();
					});
				setDefaultBillingAddressId(
					accountEntry::getDefaultBillingAddressId);
				setDefaultShippingAddressExternalReferenceCode(
					() -> {
						Address address = _addressLocalService.fetchAddress(
							accountEntry.getDefaultShippingAddressId());

						if (address == null) {
							return null;
						}

						return address.getExternalReferenceCode();
					});
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
				setOrganizationExternalReferenceCodes(
					() -> TransformUtil.transformToArray(
						_accountEntryOrganizationRelLocalService.
							getAccountEntryOrganizationRels(
								accountEntry.getAccountEntryId()),
						accountEntryOrganizationRel -> {
							Organization organization =
								_organizationLocalService.fetchOrganization(
									accountEntryOrganizationRel.
										getOrganizationId());

							if (organization == null) {
								return null;
							}

							return organization.getExternalReferenceCode();
						},
						String.class));
				setOrganizationIds(
					() -> TransformUtil.transformToArray(
						_accountEntryOrganizationRelLocalService.
							getAccountEntryOrganizationRels(
								accountEntry.getAccountEntryId()),
						AccountEntryOrganizationRel::getOrganizationId,
						Long.class));
				setParentAccountExternalReferenceCode(
					() -> {
						AccountEntry parentAccountEntry =
							_accountEntryLocalService.fetchAccountEntry(
								accountEntry.getParentAccountEntryId());

						if (parentAccountEntry == null) {
							return null;
						}

						return parentAccountEntry.getExternalReferenceCode();
					});
				setParentAccountId(accountEntry::getParentAccountEntryId);
				setStatus(accountEntry::getStatus);
				setTaxId(accountEntry::getTaxIdNumber);
				setType(() -> Account.Type.create(accountEntry.getType()));
			}
		};
	}

	private AccountContactInformation _toAccountContactInformation(
			AccountEntry accountEntry, DTOConverterContext dtoConverterContext)
		throws Exception {

		if (!_accountEntryModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				accountEntry.getAccountEntryId(),
				AccountActionKeys.MANAGE_ADDRESSES) &&
			!_accountEntryModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				accountEntry.getAccountEntryId(),
				AccountActionKeys.VIEW_ADDRESSES)) {

			return null;
		}

		Contact contact = accountEntry.fetchContact();

		return new AccountContactInformation() {
			{
				setEmailAddresses(
					() -> TransformUtil.transformToArray(
						accountEntry.getEmailAddresses(),
						EmailAddressUtil::toEmailAddress, EmailAddress.class));
				setFacebook(
					() -> {
						if (contact == null) {
							return null;
						}

						return contact.getFacebookSn();
					});
				setJabber(
					() -> {
						if (contact == null) {
							return null;
						}

						return contact.getJabberSn();
					});
				setPostalAddresses(
					() -> TransformUtil.transformToArray(
						accountEntry.getListTypeAddresses(
							PostalAddressUtil.
								getAccountEntryContactAddressListTypeIds(
									accountEntry.getCompanyId(),
									_listTypeLocalService)),
						address -> PostalAddressUtil.toPostalAddress(
							dtoConverterContext.isAcceptAllLanguages(), address,
							accountEntry.getCompanyId(),
							dtoConverterContext.getLocale()),
						PostalAddress.class));
				setSkype(
					() -> {
						if (contact == null) {
							return null;
						}

						return contact.getSkypeSn();
					});
				setSms(
					() -> {
						if (contact == null) {
							return null;
						}

						return contact.getSmsSn();
					});
				setTelephones(
					() -> TransformUtil.transformToArray(
						accountEntry.getPhones(), PhoneUtil::toPhone,
						Phone.class));
				setTwitter(
					() -> {
						if (contact == null) {
							return null;
						}

						return contact.getTwitterSn();
					});
				setWebUrls(
					() -> TransformUtil.transformToArray(
						accountEntry.getWebsites(), WebUrlUtil::toWebUrl,
						WebUrl.class));
			}
		};
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private WebServerServletToken _webServerServletToken;

}