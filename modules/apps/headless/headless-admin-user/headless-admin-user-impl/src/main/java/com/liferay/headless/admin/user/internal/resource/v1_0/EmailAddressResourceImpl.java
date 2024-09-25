/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.EmailAddressUtil;
import com.liferay.headless.admin.user.resource.v1_0.EmailAddressResource;
import com.liferay.portal.kernel.exception.NoSuchEmailAddressException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.ListTypeService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.util.DTOConverterUtil;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/email-address.properties",
	scope = ServiceScope.PROTOTYPE, service = EmailAddressResource.class
)
public class EmailAddressResourceImpl extends BaseEmailAddressResourceImpl {

	@Override
	public void deleteEmailAddress(Long emailAddressId) throws Exception {
		_emailAddressService.deleteEmailAddress(emailAddressId);
	}

	@Override
	public void deleteEmailAddressByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		com.liferay.portal.kernel.model.EmailAddress serviceEmailAddress =
			_emailAddressService.fetchEmailAddressByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (serviceEmailAddress == null) {
			throw new NoSuchEmailAddressException();
		}

		deleteEmailAddress(serviceEmailAddress.getEmailAddressId());
	}

	@Override
	public Page<EmailAddress>
			getAccountByExternalReferenceCodeEmailAddressesPage(
				String externalReferenceCode)
		throws Exception {

		return getAccountEmailAddressesPage(
			DTOConverterUtil.getModelPrimaryKey(
				_accountResourceDTOConverter, externalReferenceCode));
	}

	@Override
	public Page<EmailAddress> getAccountEmailAddressesPage(Long accountId)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.getAccountEntry(
			accountId);

		return Page.of(
			transform(
				_emailAddressService.getEmailAddresses(
					accountEntry.getModelClassName(),
					accountEntry.getAccountEntryId()),
				EmailAddressUtil::toEmailAddress));
	}

	@Override
	public EmailAddress getEmailAddress(Long emailAddressId) throws Exception {
		return EmailAddressUtil.toEmailAddress(
			_emailAddressService.getEmailAddress(emailAddressId));
	}

	@Override
	public EmailAddress getEmailAddressByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		com.liferay.portal.kernel.model.EmailAddress serviceEmailAddress =
			_emailAddressService.fetchEmailAddressByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (serviceEmailAddress == null) {
			throw new NoSuchEmailAddressException();
		}

		return getEmailAddress(serviceEmailAddress.getEmailAddressId());
	}

	@Override
	public Page<EmailAddress>
			getOrganizationByExternalReferenceCodeEmailAddressesPage(
				String externalReferenceCode)
		throws Exception {

		return getOrganizationEmailAddressesPage(
			String.valueOf(
				DTOConverterUtil.getModelPrimaryKey(
					_organizationResourceDTOConverter, externalReferenceCode)));
	}

	@Override
	public Page<EmailAddress> getOrganizationEmailAddressesPage(
			String organizationId)
		throws Exception {

		Organization organization = _organizationResourceDTOConverter.getObject(
			organizationId);

		return Page.of(
			transform(
				_emailAddressService.getEmailAddresses(
					organization.getModelClassName(),
					organization.getOrganizationId()),
				EmailAddressUtil::toEmailAddress));
	}

	@Override
	public Page<EmailAddress>
			getUserAccountByExternalReferenceCodeEmailAddressesPage(
				String externalReferenceCode)
		throws Exception {

		return getUserAccountEmailAddressesPage(
			DTOConverterUtil.getModelPrimaryKey(
				_userResourceDTOConverter, externalReferenceCode));
	}

	@Override
	public Page<EmailAddress> getUserAccountEmailAddressesPage(
			Long userAccountId)
		throws Exception {

		User user = _userService.getUserById(userAccountId);

		return Page.of(
			transform(
				_emailAddressService.getEmailAddresses(
					Contact.class.getName(), user.getContactId()),
				EmailAddressUtil::toEmailAddress));
	}

	@Override
	public EmailAddress patchEmailAddress(
			Long emailAddressId, EmailAddress emailAddress)
		throws Exception {

		com.liferay.portal.kernel.model.EmailAddress serviceEmailAddress =
			_emailAddressService.getEmailAddress(emailAddressId);

		return _updateEmailAddress(emailAddress, serviceEmailAddress);
	}

	@Override
	public EmailAddress patchEmailAddressByExternalReferenceCode(
			String externalReferenceCode, EmailAddress emailAddress)
		throws Exception {

		com.liferay.portal.kernel.model.EmailAddress serviceEmailAddress =
			_emailAddressService.fetchEmailAddressByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (serviceEmailAddress == null) {
			throw new NoSuchEmailAddressException();
		}

		return _updateEmailAddress(emailAddress, serviceEmailAddress);
	}

	private Long _getListTypeId(String className, String name) {
		ListType listType = _listTypeService.getListType(
			contextCompany.getCompanyId(), name,
			className + ListTypeConstants.EMAIL_ADDRESS);

		if (listType == null) {
			return null;
		}

		return listType.getListTypeId();
	}

	private EmailAddress _updateEmailAddress(
			EmailAddress emailAddress,
			com.liferay.portal.kernel.model.EmailAddress serviceEmailAddress)
		throws Exception {

		serviceEmailAddress = _emailAddressService.updateEmailAddress(
			GetterUtil.getString(
				emailAddress.getExternalReferenceCode(),
				serviceEmailAddress.getExternalReferenceCode()),
			serviceEmailAddress.getEmailAddressId(),
			GetterUtil.getString(
				emailAddress.getEmailAddress(),
				serviceEmailAddress.getAddress()),
			GetterUtil.getLong(
				_getListTypeId(
					serviceEmailAddress.getClassName(), emailAddress.getType()),
				serviceEmailAddress.getListTypeId()),
			GetterUtil.getBoolean(
				emailAddress.getPrimary(), serviceEmailAddress.isPrimary()));

		return EmailAddressUtil.toEmailAddress(serviceEmailAddress);
	}

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference(target = DTOConverterConstants.ACCOUNT_RESOURCE_DTO_CONVERTER)
	private DTOConverter<AccountEntry, Account> _accountResourceDTOConverter;

	@Reference
	private EmailAddressService _emailAddressService;

	@Reference
	private ListTypeService _listTypeService;

	@Reference(
		target = DTOConverterConstants.ORGANIZATION_RESOURCE_DTO_CONVERTER
	)
	private DTOConverter
		<Organization, com.liferay.headless.admin.user.dto.v1_0.Organization>
			_organizationResourceDTOConverter;

	@Reference(target = DTOConverterConstants.USER_RESOURCE_DTO_CONVERTER)
	private DTOConverter<User, UserAccount> _userResourceDTOConverter;

	@Reference
	private UserService _userService;

}