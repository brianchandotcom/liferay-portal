/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.PostalAddressUtil;
import com.liferay.headless.admin.user.resource.v1_0.PostalAddressResource;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.permission.CommonPermission;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/postal-address.properties",
	scope = ServiceScope.PROTOTYPE, service = PostalAddressResource.class
)
public class PostalAddressResourceImpl extends BasePostalAddressResourceImpl {

	@Override
	public Page<PostalAddress> getAccountPostalAddressesPage(Long accountId)
		throws Exception {

		_accountEntryService.getAccountEntry(accountId);

		return Page.of(
			transform(
				_addressLocalService.getAddresses(
					contextCompany.getCompanyId(), AccountEntry.class.getName(),
					accountId),
				address -> PostalAddressUtil.toPostalAddress(
					contextAcceptLanguage.isAcceptAllLanguages(), address,
					contextCompany.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale())));
	}

	@Override
	public Page<PostalAddress> getOrganizationPostalAddressesPage(
			String organizationId)
		throws Exception {

		Organization organization = _organizationResourceDTOConverter.getObject(
			organizationId);

		return Page.of(
			transform(
				_addressLocalService.getAddresses(
					contextCompany.getCompanyId(),
					organization.getModelClassName(),
					organization.getOrganizationId()),
				address -> PostalAddressUtil.toPostalAddress(
					contextAcceptLanguage.isAcceptAllLanguages(), address,
					contextCompany.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale())));
	}

	@Override
	public PostalAddress getPostalAddress(Long postalAddressId)
		throws Exception {

		return PostalAddressUtil.toPostalAddress(
			contextAcceptLanguage.isAcceptAllLanguages(),
			_addressService.getAddress(postalAddressId),
			contextCompany.getCompanyId(),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Override
	public Page<PostalAddress> getUserAccountPostalAddressesPage(
			Long userAccountId)
		throws Exception {

		User user = _userService.getUserById(userAccountId);

		_commonPermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			user.getModelClassName(), user.getUserId(), ActionKeys.VIEW);

		return Page.of(
			transform(
				_addressLocalService.getAddresses(
					user.getCompanyId(), Contact.class.getName(),
					user.getContactId()),
				address -> PostalAddressUtil.toPostalAddress(
					contextAcceptLanguage.isAcceptAllLanguages(), address,
					contextCompany.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale())));
	}

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private AddressService _addressService;

	@Reference
	private CommonPermission _commonPermission;

	@Reference(
		target = DTOConverterConstants.ORGANIZATION_RESOURCE_DTO_CONVERTER
	)
	private DTOConverter
		<Organization, com.liferay.headless.admin.user.dto.v1_0.Organization>
			_organizationResourceDTOConverter;

	@Reference
	private UserService _userService;

}