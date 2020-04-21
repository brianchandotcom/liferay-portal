/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = ContactInformationHelper.class)
public class ContactInformationHelper {

	public long getRegionId(String addressRegion, long countryId) {
		if (Validator.isNull(addressRegion) || (countryId <= 0)) {
			return 0;
		}

		Region region = _regionService.fetchRegion(countryId, addressRegion);

		if (region != null) {
			return region.getRegionId();
		}

		List<Region> regions = _regionService.getRegions(countryId);

		for (Region curRegion : regions) {
			if (StringUtil.equalsIgnoreCase(
					addressRegion, curRegion.getName())) {

				return curRegion.getRegionId();
			}
		}

		return 0;
	}

	public Address toAddress(PostalAddress postalAddress, String type) {
		String street1 = postalAddress.getStreetAddressLine1();
		String street2 = postalAddress.getStreetAddressLine2();
		String street3 = postalAddress.getStreetAddressLine3();
		String city = postalAddress.getAddressLocality();
		String zip = postalAddress.getPostalCode();
		long countryId = toCountryId(postalAddress.getAddressCountry());

		if (Validator.isNull(street1) && Validator.isNull(street2) &&
			Validator.isNull(street3) && Validator.isNull(city) &&
			Validator.isNull(zip) && (countryId == 0)) {

			return null;
		}

		Address address = _addressLocalService.createAddress(
			GetterUtil.getLong(postalAddress.getId()));

		address.setStreet1(street1);
		address.setStreet2(street2);
		address.setStreet3(street3);
		address.setCity(city);
		address.setZip(zip);
		address.setRegionId(
			getRegionId(postalAddress.getAddressRegion(), countryId));
		address.setCountryId(countryId);
		address.setTypeId(
			_toListTypeId("other", postalAddress.getAddressType(), type));
		address.setMailing(true);
		address.setPrimary(GetterUtil.getBoolean(postalAddress.getPrimary()));

		return address;
	}

	public Country toCountry(String addressCountry) {
		try {
			Country country = _countryService.fetchCountryByA2(addressCountry);

			if (country != null) {
				return country;
			}

			country = _countryService.fetchCountryByA3(addressCountry);

			if (country != null) {
				return country;
			}

			return _countryService.getCountryByName(addressCountry);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return null;
	}

	public long toCountryId(String addressCountry) {
		return Optional.ofNullable(
			addressCountry
		).map(
			this::toCountry
		).map(
			Country::getCountryId
		).orElse(
			(long)0
		);
	}

	public com.liferay.portal.kernel.model.EmailAddress
		toServiceBuilderEmailAddress(EmailAddress emailAddress, String type) {

		String address = emailAddress.getEmailAddress();

		if (Validator.isNull(address)) {
			return null;
		}

		com.liferay.portal.kernel.model.EmailAddress
			serviceBuilderEmailAddress =
				_emailAddressLocalService.createEmailAddress(
					GetterUtil.getLong(emailAddress.getId()));

		serviceBuilderEmailAddress.setAddress(address);
		serviceBuilderEmailAddress.setTypeId(
			_toListTypeId("email-address", emailAddress.getType(), type));
		serviceBuilderEmailAddress.setPrimary(
			GetterUtil.getBoolean(emailAddress.getPrimary()));

		return serviceBuilderEmailAddress;
	}

	public com.liferay.portal.kernel.model.Phone toServiceBuilderPhone(
		Phone phone, String type) {

		String number = phone.getPhoneNumber();
		String extension = phone.getExtension();

		if (Validator.isNull(number) && Validator.isNull(extension)) {
			return null;
		}

		com.liferay.portal.kernel.model.Phone serviceBuilderPhone =
			_phoneLocalService.createPhone(GetterUtil.getLong(phone.getId()));

		serviceBuilderPhone.setNumber(number);
		serviceBuilderPhone.setExtension(extension);
		serviceBuilderPhone.setTypeId(
			_toListTypeId("other", phone.getPhoneType(), type));
		serviceBuilderPhone.setPrimary(
			GetterUtil.getBoolean(phone.getPrimary()));

		return serviceBuilderPhone;
	}

	public Website toWebsite(WebUrl webUrl, String type) {
		String url = webUrl.getUrl();

		if (Validator.isNull(url)) {
			return null;
		}

		Website website = _websiteLocalService.createWebsite(
			GetterUtil.getLong(webUrl.getId()));

		website.setUrl(url);
		website.setTypeId(_toListTypeId("public", webUrl.getUrlType(), type));
		website.setPrimary(GetterUtil.getBoolean(webUrl.getPrimary()));

		return website;
	}

	private long _toListTypeId(String defaultName, String name, String type) {
		ListType listType = _listTypeLocalService.getListType(name, type);

		if (listType == null) {
			listType = _listTypeLocalService.getListType(defaultName, type);
		}

		if (listType != null) {
			return listType.getListTypeId();
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContactInformationHelper.class);

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private CountryService _countryService;

	@Reference
	private EmailAddressLocalService _emailAddressLocalService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private PhoneLocalService _phoneLocalService;

	@Reference
	private RegionService _regionService;

	@Reference
	private WebsiteLocalService _websiteLocalService;

}