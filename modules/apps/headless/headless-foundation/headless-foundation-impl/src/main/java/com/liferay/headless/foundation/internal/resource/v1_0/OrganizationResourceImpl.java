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

import com.liferay.headless.foundation.dto.v1_0.HoursAvailable;
import com.liferay.headless.foundation.dto.v1_0.Location;
import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.dto.v1_0.Services;
import com.liferay.headless.foundation.resource.v1_0.OrganizationResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.OrgLaborService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

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

		return _getUserOrganizationPage(myUserAccountId);
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

		return _getUserOrganizationPage(userAccountId);
	}

	public class OpeningHour {

		public OpeningHour(String day, int open, int close) {
			this.day = day;

			if (open >= 0) {
				this.open = String.valueOf(open);
			}

			if (close >= 0) {
				this.close = String.valueOf(close);
			}
		}

		protected String close;
		protected String day;
		protected String open;

	}

	private Services _getOrgLabor(OrgLabor orgLabor) throws PortalException {
		ListType listType = orgLabor.getType();

		OpeningHour[] openingHours = {
			new OpeningHour(
				"Friday", orgLabor.getFriOpen(), orgLabor.getFriClose()),
			new OpeningHour(
				"Monday", orgLabor.getMonOpen(), orgLabor.getMonClose()),
			new OpeningHour(
				"Thursday", orgLabor.getThuOpen(), orgLabor.getThuClose()),
			new OpeningHour(
				"Tuesday", orgLabor.getTueOpen(), orgLabor.getTueClose()),
			new OpeningHour(
				"Saturday", orgLabor.getSatOpen(), orgLabor.getSatClose()),
			new OpeningHour(
				"Sunday", orgLabor.getSunOpen(), orgLabor.getSunClose()),
			new OpeningHour(
				"Wednesday", orgLabor.getWedOpen(), orgLabor.getWedClose())
		};

		return new Services() {
			{
				hoursAvailable = transform(
					openingHours,
					openingHour -> new HoursAvailable() {
						{
							closes = openingHour.close;
							dayOfWeek = openingHour.day;
							opens = openingHour.open;
						}
					},
					HoursAvailable.class);
				serviceType = listType.getName();
			}
		};
	}

	private Page<Organization> _getUserOrganizationPage(Long myUserAccountId)
		throws PortalException {

		User user = _userService.getUserById(myUserAccountId);

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
				id = organization.getOrganizationId();
				location = new Location() {
					{
						if (organization.getCountryId() != 0) {
							setAddressCountry(
								() -> {
									Country country =
										_countryService.getCountry(
											organization.getCountryId());

									return country.getName(
										contextAcceptLanguage.
											getPreferredLocale());
								});
						}

						if (organization.getRegionId() != 0) {
							setAddressRegion(
								() -> {
									Region region = _regionService.getRegion(
										organization.getRegionId());

									return region.getName();
								});
						}
					}
				};

				if (organization.getLogoId() != 0) {
					long logoId = organization.getLogoId();

					logo = StringBundler.concat(
						_portal.getPathImage(), "/organization_logo?img_id=",
						logoId, "&t=",
						WebServerServletTokenUtil.getToken(logoId));
				}

				name = organization.getName();

				parentOrganization = _toOrganization(
					organization.getParentOrganization());

				if (organization.getParentOrganizationId() <= 0) {
					parentOrganizationId =
						organization.getParentOrganizationId();
				}

				setHasOrganizations(
					() -> {
						int organizationsCount =
							_organizationService.getOrganizationsCount(
								organization.getCompanyId(),
								organization.getOrganizationId());

						return organizationsCount > 0;
					});

				setServices(
					() -> {
						List<OrgLabor> orgLabors =
							_orgLaborService.getOrgLabors(
								organization.getOrganizationId());

						return transformToArray(
							orgLabors,
							OrganizationResourceImpl.this::_getOrgLabor,
							Services.class);
					});
			}
		};
	}

	@Reference
	private CountryService _countryService;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private OrgLaborService _orgLaborService;

	@Reference
	private Portal _portal;

	@Reference
	private RegionService _regionService;

	@Reference
	private UserService _userService;

}