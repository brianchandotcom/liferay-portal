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

package com.liferay.headless.admin.user.internal.dto.v1_0.helper;

import static com.liferay.portal.vulcan.util.TransformUtil.transformToArray;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.dto.v1_0.HoursAvailable;
import com.liferay.headless.admin.user.dto.v1_0.Location;
import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.dto.v1_0.OrganizationContactInformation;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.Service;
import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.EmailAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.PhoneUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.PostalAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.WebUrlUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.OrgLaborService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PhoneService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.WebsiteService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Organization",
	service = {DTOConverter.class, OrganizationResourceDTOConverter.class}
)
public class OrganizationResourceDTOConverter
	implements DTOConverter
		<com.liferay.portal.kernel.model.Organization, Organization> {

	@Override
	public String getContentType() {
		return Organization.class.getSimpleName();
	}

	@Override
	public com.liferay.portal.kernel.model.Organization getObject(
			String externalReferenceCode)
		throws Exception {

		com.liferay.portal.kernel.model.Organization organization =
			_organizationLocalService.fetchOrganizationByReferenceCode(
				CompanyThreadLocal.getCompanyId(), externalReferenceCode);

		if (organization == null) {
			organization = _organizationService.getOrganization(
				GetterUtil.getLong(externalReferenceCode));
		}

		return organization;
	}

	@Override
	public Organization toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.portal.kernel.model.Organization organization)
		throws Exception {

		if (organization == null) {
			return null;
		}

		return new Organization() {
			{
				comment = organization.getComments();
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					com.liferay.portal.kernel.model.Organization.class.
						getName(),
					organization.getOrganizationId(),
					organization.getCompanyId(),
					dtoConverterContext.getLocale());
				dateCreated = organization.getCreateDate();
				dateModified = organization.getModifiedDate();
				id = String.valueOf(organization.getOrganizationId());
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						organization.getModelClassName(),
						organization.getOrganizationId()),
					AssetTag.NAME_ACCESSOR);
				location = new Location() {
					{
						setAddressCountry(
							() -> {
								if (organization.getCountryId() <= 0) {
									return null;
								}

								Country country = _countryService.getCountry(
									organization.getCountryId());

								return country.getName(
									dtoConverterContext.getLocale());
							});
						setAddressCountry_i18n(
							() -> {
								if (!dtoConverterContext.
										isAcceptAllLanguages()) {

									return null;
								}

								Set<Locale> locales =
									LanguageUtil.getCompanyAvailableLocales(
										organization.getCompanyId());

								Stream<Locale> localeStream = locales.stream();

								Country country = _countryService.getCountry(
									organization.getCountryId());

								return localeStream.collect(
									Collectors.toMap(
										Locale::toLanguageTag,
										country::getName));
							});
						setAddressRegion(
							() -> {
								if (organization.getRegionId() <= 0) {
									return null;
								}

								Region region = _regionService.getRegion(
									organization.getRegionId());

								return region.getName();
							});
					}
				};
				name = organization.getName();
				numberOfOrganizations =
					_organizationService.getOrganizationsCount(
						organization.getCompanyId(),
						organization.getOrganizationId());
				organizationContactInformation =
					new OrganizationContactInformation() {
						{
							emailAddresses = transformToArray(
								_emailAddressService.getEmailAddresses(
									organization.getModelClassName(),
									organization.getOrganizationId()),
								EmailAddressUtil::toEmailAddress,
								EmailAddress.class);
							postalAddresses = transformToArray(
								organization.getAddresses(),
								address -> PostalAddressUtil.toPostalAddress(
									dtoConverterContext.isAcceptAllLanguages(),
									address, organization.getCompanyId(),
									dtoConverterContext.getLocale()),
								PostalAddress.class);
							telephones = transformToArray(
								_phoneService.getPhones(
									organization.getModelClassName(),
									organization.getOrganizationId()),
								PhoneUtil::toPhone, Phone.class);
							webUrls = transformToArray(
								_websiteService.getWebsites(
									organization.getModelClassName(),
									organization.getOrganizationId()),
								WebUrlUtil::toWebUrl, WebUrl.class);
						}
					};
				parentOrganization = toDTO(
					dtoConverterContext, organization.getParentOrganization());
				services = transformToArray(
					_orgLaborService.getOrgLabors(
						organization.getOrganizationId()),
					OrganizationResourceDTOConverter.this::_toService,
					Service.class);

				setImage(
					() -> {
						if (organization.getLogoId() <= 0) {
							return null;
						}

						return StringBundler.concat(
							_portal.getPathImage(),
							"/organization_logo?img_id=",
							organization.getLogoId(), "&t=",
							WebServerServletTokenUtil.getToken(
								organization.getLogoId()));
					});
			}
		};
	}

	private HoursAvailable _createHoursAvailable(
		int closeHour, String day, int openHour) {

		return new HoursAvailable() {
			{
				closes = _formatHour(closeHour);
				dayOfWeek = day;
				opens = _formatHour(openHour);
			}
		};
	}

	private String _formatHour(int hour) {
		if (hour == -1) {
			return null;
		}

		DecimalFormat decimalFormat = new DecimalFormat("00,00") {
			{
				setDecimalFormatSymbols(
					new DecimalFormatSymbols() {
						{
							setGroupingSeparator(':');
						}
					});
				setGroupingSize(2);
			}
		};

		return decimalFormat.format(hour);
	}

	private Service _toService(OrgLabor orgLabor) throws Exception {
		ListType listType = orgLabor.getType();

		return new Service() {
			{
				hoursAvailable = new HoursAvailable[] {
					_createHoursAvailable(
						orgLabor.getSunClose(), "Sunday",
						orgLabor.getSunOpen()),
					_createHoursAvailable(
						orgLabor.getMonClose(), "Monday",
						orgLabor.getMonOpen()),
					_createHoursAvailable(
						orgLabor.getTueClose(), "Tuesday",
						orgLabor.getTueOpen()),
					_createHoursAvailable(
						orgLabor.getWedClose(), "Wednesday",
						orgLabor.getWedOpen()),
					_createHoursAvailable(
						orgLabor.getThuClose(), "Thursday",
						orgLabor.getThuOpen()),
					_createHoursAvailable(
						orgLabor.getFriClose(), "Friday",
						orgLabor.getFriOpen()),
					_createHoursAvailable(
						orgLabor.getSatClose(), "Saturday",
						orgLabor.getSatOpen())
				};
				serviceType = listType.getName();
			}
		};
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CountryService _countryService;

	@Reference
	private EmailAddressService _emailAddressService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private OrgLaborService _orgLaborService;

	@Reference
	private PhoneService _phoneService;

	@Reference
	private Portal _portal;

	@Reference
	private RegionService _regionService;

	@Reference
	private WebsiteService _websiteService;

}