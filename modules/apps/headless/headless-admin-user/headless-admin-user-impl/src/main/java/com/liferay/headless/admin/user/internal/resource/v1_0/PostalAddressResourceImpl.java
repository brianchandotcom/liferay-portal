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
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.service.permission.CommonPermissionUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.BadRequestException;

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
	public void deleteAccountPostalAddresses(Long accountId, Long[] longs)
		throws Exception {

		for (long postalAddressesId : longs) {
			_validateAccountPostalAddres(accountId, postalAddressesId);
			_addressLocalService.deleteAddress(postalAddressesId);
		}
	}

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

		CommonPermissionUtil.check(
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

	@Override
	public PostalAddress postAccountPostalAddress(
			Long accountId, PostalAddress postalAddress)
		throws Exception {

		Country country = _getCountryByTitle(postalAddress);

		long regionId = _getRegionByTitleAndCountry(postalAddress, country);

		ListType type = _getType(postalAddress);

		Address address = _addressLocalService.addAddress(
			null, contextUser.getUserId(), AccountEntry.class.getName(),
			accountId, postalAddress.getName(), null,
			postalAddress.getStreetAddressLine1(),
			postalAddress.getStreetAddressLine2(),
			postalAddress.getStreetAddressLine3(),
			postalAddress.getAddressLocality(), postalAddress.getPostalCode(),
			regionId, country.getCountryId(), type.getListTypeId(), false,
			postalAddress.getPrimary(), null,
			ServiceContextFactory.getInstance(contextHttpServletRequest));

		return PostalAddressUtil.toPostalAddress(
			contextAcceptLanguage.isAcceptAllLanguages(), address,
			contextCompany.getCompanyId(),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Override
	public PostalAddress putAccountPostalAddress(
			Long accountId, Long postalAddressId, PostalAddress postalAddress)
		throws Exception {

		Address address = _validateAccountPostalAddres(
			accountId, postalAddressId);

		Country country = _getCountryByTitle(postalAddress);

		long regionId = _getRegionByTitleAndCountry(postalAddress, country);

		ListType type = _getType(postalAddress);

		address = _addressLocalService.updateAddress(
			address.getAddressId(), postalAddress.getName(),
			address.getDescription(), postalAddress.getStreetAddressLine1(),
			postalAddress.getStreetAddressLine2(),
			postalAddress.getStreetAddressLine3(),
			postalAddress.getAddressLocality(), postalAddress.getPostalCode(),
			regionId, country.getCountryId(), type.getListTypeId(),
			address.isMailing(), postalAddress.getPrimary(),
			address.getPhoneNumber());

		return PostalAddressUtil.toPostalAddress(
			contextAcceptLanguage.isAcceptAllLanguages(), address,
			contextCompany.getCompanyId(),
			contextAcceptLanguage.getPreferredLocale());
	}

	private Country _getCountryByTitle(PostalAddress postalAddress) {
		List<Country> countries = _countryService.getCompanyCountries(
			contextCompany.getCompanyId());

		Iterator<Country> countryIterator = countries.iterator();

		Boolean found = false;

		String title = null;

		Country country = null;

		while (countryIterator.hasNext() && !found) {
			country = countryIterator.next();

			title = country.getTitle(
				contextAcceptLanguage.getPreferredLocale());

			if (title.equals(postalAddress.getAddressCountry())) {
				found = true;
			}
		}

		if (!found) {
			throw new BadRequestException("Country not found");
		}

		return country;
	}

	private long _getRegionByTitleAndCountry(
		PostalAddress postalAddress, Country country) {

		if (postalAddress.getAddressType() == null) {
			return 0;
		}

		List<Region> regions = _regionService.getRegions(
			country.getCountryId());

		if (regions.isEmpty()) {
			return 0;
		}

		Iterator<Region> regionIterator = regions.iterator();

		Region region = null;
		String title = null;

		Boolean found = false;

		while (regionIterator.hasNext() && !found) {
			region = regionIterator.next();

			title = region.getTitle(
				contextAcceptLanguage.getPreferredLanguageId());

			if (title.equals(postalAddress.getAddressRegion())) {
				found = true;
			}
		}

		if (!found) {
			throw new BadRequestException("Region not found");
		}

		return region.getRegionId();
	}

	private ListType _getType(PostalAddress postalAddress) {
		ListType type = _listTypeLocalService.getListType(
			postalAddress.getAddressType(),
			"com.liferay.account.model.AccountEntry.address");

		if (type == null) {
			throw new BadRequestException("Type not found");
		}

		return type;
	}

	private Address _validateAccountPostalAddres(
			Long accountId, long postalAddressId)
		throws Exception {

		Address address = _addressLocalService.getAddress(postalAddressId);

		if (!accountId.equals(address.getClassPK())) {
			throw new BadRequestException(
				_language.format(
					contextAcceptLanguage.getPreferredLocale(),
					"account-entry-x-not-has-postal-address-y",
					new String[] {
						accountId.toString(), String.valueOf(postalAddressId)
					}));
		}

		return address;
	}

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private AddressService _addressService;

	@Reference
	private CommonPermission _commonPermission;

	@Reference
	private CountryService _countryService;

	@Reference
	private Language _language;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference(
		target = DTOConverterConstants.ORGANIZATION_RESOURCE_DTO_CONVERTER
	)
	private DTOConverter
		<Organization, com.liferay.headless.admin.user.dto.v1_0.Organization>
			_organizationResourceDTOConverter;

	@Reference
	private RegionService _regionService;

	@Reference
	private UserService _userService;

}