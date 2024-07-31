/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.PostalAddressUtil;
import com.liferay.headless.admin.user.resource.v1_0.PostalAddressResource;
import com.liferay.portal.kernel.exception.NoSuchAddressException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.service.permission.CommonPermissionUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.util.DTOConverterUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
	public void deletePostalAddress(Long postalAddressId) throws Exception {
		_addressService.deleteAddress(postalAddressId);
	}

	@Override
	public void deletePostalAddressByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		Address address =
			_addressLocalService.fetchAddressByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (address == null) {
			throw new NoSuchAddressException(
				"No address found with external reference code " +
					externalReferenceCode);
		}

		_addressService.deleteAddress(address.getAddressId());
	}

	@Override
	public Page<PostalAddress>
			getAccountByExternalReferenceCodePostalAddressesPage(
				String externalReferenceCode)
		throws Exception {

		return getAccountPostalAddressesPage(
			DTOConverterUtil.getModelPrimaryKey(
				_accountResourceDTOConverter, externalReferenceCode));
	}

	@Override
	public Page<PostalAddress> getAccountPostalAddressesPage(Long accountId)
		throws Exception {

		_accountEntryService.getAccountEntry(accountId);

		return Page.of(
			transform(
				_addressService.getAddresses(
					AccountEntry.class.getName(), accountId),
				address -> PostalAddressUtil.toPostalAddress(
					contextAcceptLanguage.isAcceptAllLanguages(), address,
					contextCompany.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale())));
	}

	@Override
	public Page<PostalAddress>
			getOrganizationByExternalReferenceCodePostalAddressesPage(
				String externalReferenceCode)
		throws Exception {

		return getOrganizationPostalAddressesPage(
			String.valueOf(
				DTOConverterUtil.getModelPrimaryKey(
					_organizationResourceDTOConverter, externalReferenceCode)));
	}

	@Override
	public Page<PostalAddress> getOrganizationPostalAddressesPage(
			String organizationId)
		throws Exception {

		Organization organization = _organizationResourceDTOConverter.getObject(
			organizationId);

		return Page.of(
			transform(
				_addressService.getAddresses(
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
	public PostalAddress getPostalAddressByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		Address address =
			_addressLocalService.fetchAddressByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (address == null) {
			throw new NoSuchAddressException(
				"No address found with external reference code " +
					externalReferenceCode);
		}

		return getPostalAddress(address.getAddressId());
	}

	@Override
	public Page<PostalAddress>
			getUserAccountByExternalReferenceCodePostalAddressesPage(
				String externalReferenceCode)
		throws Exception {

		return getUserAccountPostalAddressesPage(
			DTOConverterUtil.getModelPrimaryKey(
				_userResourceDTOConverter, externalReferenceCode));
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
				_addressService.getAddresses(
					Contact.class.getName(), user.getContactId()),
				address -> PostalAddressUtil.toPostalAddress(
					contextAcceptLanguage.isAcceptAllLanguages(), address,
					contextCompany.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale())));
	}

	@Override
	public PostalAddress patchPostalAddress(
			Long postalAddressId, PostalAddress postalAddress)
		throws Exception {

		Address address = _addressService.getAddress(postalAddressId);

		Country country = null;

		if (postalAddress.getAddressCountry() != null) {
			country = _getCountryByTitle(postalAddress);

			address.setCountryId(country.getCountryId());
			address.setRegionId(_getRegionId(postalAddress, country));
		}

		if (postalAddress.getAddressLocality() != null) {
			address.setCity(postalAddress.getAddressLocality());
		}

		if ((postalAddress.getAddressRegion() != null) && (country == null)) {
			country = _countryService.getCountry(address.getCountryId());

			address.setRegionId(_getRegionId(postalAddress, country));
		}

		if (postalAddress.getAddressType() != null) {
			ListType listType = _getListType(address, postalAddress);

			address.setListTypeId(listType.getListTypeId());
		}

		if (postalAddress.getName() != null) {
			address.setName(postalAddress.getName());
		}

		String phoneNumber = address.getPhoneNumber();

		if (postalAddress.getPhoneNumber() != null) {
			phoneNumber = postalAddress.getPhoneNumber();
		}

		if (postalAddress.getPostalCode() != null) {
			address.setZip(postalAddress.getPostalCode());
		}

		if (postalAddress.getPrimary() != null) {
			address.setPrimary(postalAddress.getPrimary());
		}

		if (postalAddress.getStreetAddressLine1() != null) {
			address.setStreet1(postalAddress.getStreetAddressLine1());
		}

		if (postalAddress.getStreetAddressLine2() != null) {
			address.setStreet2(postalAddress.getStreetAddressLine2());
		}

		if (postalAddress.getStreetAddressLine3() != null) {
			address.setStreet3(postalAddress.getStreetAddressLine3());
		}

		address = _addressService.updateAddress(
			address.getAddressId(), address.getName(), address.getDescription(),
			address.getStreet1(), address.getStreet2(), address.getStreet3(),
			address.getCity(), address.getZip(), address.getRegionId(),
			address.getCountryId(), address.getListTypeId(),
			address.isMailing(), address.isPrimary(), phoneNumber);

		address = _addressService.updateExternalReferenceCode(
			address,
			GetterUtil.getString(
				postalAddress.getExternalReferenceCode(),
				address.getExternalReferenceCode()));

		return PostalAddressUtil.toPostalAddress(
			contextAcceptLanguage.isAcceptAllLanguages(), address,
			contextCompany.getCompanyId(),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Override
	public PostalAddress patchPostalAddressByExternalReferenceCode(
			String externalReferenceCode, PostalAddress postalAddress)
		throws Exception {

		Address address =
			_addressLocalService.fetchAddressByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (address == null) {
			throw new NoSuchAddressException(
				"No address found with external reference code " +
					externalReferenceCode);
		}

		return patchPostalAddress(address.getAddressId(), postalAddress);
	}

	@Override
	public PostalAddress postAccountPostalAddress(
			Long accountId, PostalAddress postalAddress)
		throws Exception {

		Country country = _getCountryByTitle(postalAddress);

		long regionId = _getRegionId(postalAddress, country);

		ListType listType = _getListType(null, postalAddress);

		Address address = _addressService.addAddress(
			postalAddress.getExternalReferenceCode(),
			AccountEntry.class.getName(), accountId, postalAddress.getName(),
			null, postalAddress.getStreetAddressLine1(),
			postalAddress.getStreetAddressLine2(),
			postalAddress.getStreetAddressLine3(),
			postalAddress.getAddressLocality(), postalAddress.getPostalCode(),
			regionId, country.getCountryId(), listType.getListTypeId(), false,
			postalAddress.getPrimary(), postalAddress.getPhoneNumber(),
			ServiceContextFactory.getInstance(contextHttpServletRequest));

		return PostalAddressUtil.toPostalAddress(
			contextAcceptLanguage.isAcceptAllLanguages(), address,
			contextCompany.getCompanyId(),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Override
	public PostalAddress putPostalAddress(
			Long postalAddressId, PostalAddress postalAddress)
		throws Exception {

		Address address = _addressService.getAddress(postalAddressId);

		Country country = _getCountryByTitle(postalAddress);

		long regionId = _getRegionId(postalAddress, country);

		ListType listType = _getListType(address, postalAddress);

		address = _addressService.updateAddress(
			address.getAddressId(), postalAddress.getName(),
			address.getDescription(), postalAddress.getStreetAddressLine1(),
			postalAddress.getStreetAddressLine2(),
			postalAddress.getStreetAddressLine3(),
			postalAddress.getAddressLocality(), postalAddress.getPostalCode(),
			regionId, country.getCountryId(), listType.getListTypeId(),
			address.isMailing(), postalAddress.getPrimary(),
			postalAddress.getPhoneNumber());

		address = _addressService.updateExternalReferenceCode(
			address,
			GetterUtil.getString(
				postalAddress.getExternalReferenceCode(),
				address.getExternalReferenceCode()));

		return PostalAddressUtil.toPostalAddress(
			contextAcceptLanguage.isAcceptAllLanguages(), address,
			contextCompany.getCompanyId(),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Override
	public PostalAddress putPostalAddressByExternalReferenceCode(
			String externalReferenceCode, PostalAddress postalAddress)
		throws Exception {

		Address address =
			_addressLocalService.fetchAddressByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (address == null) {
			throw new NoSuchAddressException(
				"No address found with external reference code " +
					externalReferenceCode);
		}

		return putPostalAddress(address.getAddressId(), postalAddress);
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

	private ListType _getListType(Address address, PostalAddress postalAddress)
		throws Exception {

		String type = AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS;

		if (address != null) {
			ClassName className = _classNameLocalService.getClassName(
				address.getClassNameId());

			type = className.getClassName() + ListTypeConstants.ADDRESS;
		}

		ListType listType = _listTypeLocalService.getListType(
			contextCompany.getCompanyId(), postalAddress.getAddressType(),
			type);

		if (listType == null) {
			throw new BadRequestException("Type not found");
		}

		return listType;
	}

	private long _getRegionId(PostalAddress postalAddress, Country country) {
		List<Region> regions = _regionService.getRegions(
			country.getCountryId());

		if ((postalAddress.getAddressRegion() == null) ||
			Objects.equals(postalAddress.getAddressRegion(), "")) {

			if (regions.isEmpty()) {
				return 0;
			}

			throw new BadRequestException("Region not found");
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

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference(target = DTOConverterConstants.ACCOUNT_RESOURCE_DTO_CONVERTER)
	private DTOConverter<AccountEntry, Account> _accountResourceDTOConverter;

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private AddressService _addressService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CountryService _countryService;

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

	@Reference(target = DTOConverterConstants.USER_RESOURCE_DTO_CONVERTER)
	private DTOConverter<User, UserAccount> _userResourceDTOConverter;

	@Reference
	private UserService _userService;

}