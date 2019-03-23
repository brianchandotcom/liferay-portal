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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.ContactInformation;
import com.liferay.headless.foundation.dto.v1_0.Email;
import com.liferay.headless.foundation.dto.v1_0.HoursAvailable;
import com.liferay.headless.foundation.dto.v1_0.Location;
import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.dto.v1_0.Phone;
import com.liferay.headless.foundation.dto.v1_0.PostalAddress;
import com.liferay.headless.foundation.dto.v1_0.Service;
import com.liferay.headless.foundation.dto.v1_0.WebUrl;
import com.liferay.headless.foundation.internal.dto.v1_0.util.EmailUtil;
import com.liferay.headless.foundation.internal.dto.v1_0.util.PhoneUtil;
import com.liferay.headless.foundation.internal.dto.v1_0.util.PostalAddressUtil;
import com.liferay.headless.foundation.internal.dto.v1_0.util.WebUrlUtil;
import com.liferay.headless.foundation.resource.v1_0.OrganizationResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.OrgLaborService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PhoneService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.WebsiteService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/organization.properties",
	scope = ServiceScope.PROTOTYPE, service = OrganizationResource.class
)
public class OrganizationResourceImpl extends BaseOrganizationResourceImpl {

	@Override
	public Page<Organization> getMyUserAccountOrganizationsPage(
			Long myUserAccountId, Pagination pagination)
		throws Exception {

		return _getOrganizationsPage(myUserAccountId);
	}

	@Override
	public Organization getOrganization(Long organizationId) throws Exception {
		return _toOrganization(
			_organizationService.getOrganization(organizationId));
	}

	@Override
	public Page<Organization> getOrganizationOrganizationsPage(
			Long organizationId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_organizationService.getOrganizations(
					contextCompany.getCompanyId(), organizationId,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toOrganization),
			pagination,
			_organizationService.getOrganizationsCount(
				contextCompany.getCompanyId(), organizationId));
	}

	@Override
	public Page<Organization> getOrganizationsPage(Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_organizationService.getOrganizations(
					contextCompany.getCompanyId(), 0,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toOrganization),
			pagination,
			_organizationService.getOrganizationsCount(
				contextCompany.getCompanyId(), 0));
	}

	@Override
	public Page<Organization> getUserAccountOrganizationsPage(
			Long userAccountId, Pagination pagination)
		throws Exception {

		return _getOrganizationsPage(userAccountId);
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

	private Page<Organization> _getOrganizationsPage(Long userAccountId)
		throws PortalException {

		User user = _userService.getUserById(userAccountId);

		return Page.of(
			transform(user.getOrganizations(), this::_toOrganization));
	}

	private Organization _toOrganization(
			com.liferay.portal.kernel.model.Organization organization)
		throws PortalException {

		if (organization == null) {
			return null;
		}

		return new Organization() {
			{
				comment = organization.getComments();
				contactInformation = new ContactInformation() {
					{
						emails = transformToArray(
							_emailAddressService.getEmailAddresses(
								organization.getModelClassName(),
								organization.getOrganizationId()),
							EmailUtil::toEmail, Email.class);
						postalAddresses = transformToArray(
							organization.getAddresses(),
							address -> PostalAddressUtil.toPostalAddress(
								address,
								contextAcceptLanguage.getPreferredLocale()),
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
				id = organization.getOrganizationId();
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
									contextAcceptLanguage.getPreferredLocale());
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
				parentOrganization = _toOrganization(
					organization.getParentOrganization());
				services = transformToArray(
					_orgLaborService.getOrgLabors(
						organization.getOrganizationId()),
					OrganizationResourceImpl.this::_toService, Service.class);

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
				setParentOrganizationId(
					() -> {
						if (organization.getParentOrganizationId() <= 0) {
							return null;
						}

						return organization.getParentOrganizationId();
					});
			}
		};
	}

	private Service _toService(OrgLabor orgLabor) throws PortalException {
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
	private CountryService _countryService;

	@Reference
	private EmailAddressService _emailAddressService;

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
	private UserService _userService;

	@Reference
	private WebsiteService _websiteService;

}