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
import com.liferay.headless.admin.user.internal.odata.entity.v1_0.OrganizationEntityModel;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.OrgLaborService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PhoneService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WebsiteService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

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
public class OrganizationResourceImpl
	extends BaseOrganizationResourceImpl implements EntityModelResource {

	@Override
	public void deleteOrganization(Long organizationId) throws Exception {
		_organizationService.deleteOrganization(organizationId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Organization getOrganization(Long organizationId) throws Exception {
		return _toOrganization(
			_organizationService.getOrganization(organizationId));
	}

	@Override
	public Page<Organization> getOrganizationOrganizationsPage(
			Long parentOrganizationId, Boolean flatten, String search,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getOrganizationsPage(
			parentOrganizationId, flatten, search, filter, pagination, sorts);
	}

	@Override
	public Page<Organization> getOrganizationsPage(
			Boolean flatten, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getOrganizationsPage(
			0L, flatten, search, filter, pagination, sorts);
	}

	@Override
	public Organization postOrganization(Organization organization)
		throws Exception {

		long countryId = _getCountryId(organization);

		return _toOrganization(
			_organizationService.addOrganization(
				_getDefaultParentOrganizationId(organization),
				organization.getName(), OrganizationConstants.TYPE_ORGANIZATION,
				_getRegionId(organization, countryId), countryId,
				ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
				organization.getComment(), false, _getAddresses(organization),
				Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList(), Collections.emptyList(),
				new ServiceContext()));
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

	private List<Address> _getAddresses(Organization organization) {
		return Optional.ofNullable(
			organization.getOrganizationContactInformation()
		).map(
			OrganizationContactInformation::getPostalAddresses
		).map(
			postalAddresses -> ListUtil.filter(
				TransformUtil.transformToList(
					postalAddresses, this::_toAddress),
				Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
	}

	private long _getCountryId(Organization organization) {
		return Optional.ofNullable(
			organization.getLocation()
		).map(
			Location::getAddressCountry
		).map(
			this::_toCountryId
		).get();
	}

	private long _getDefaultParentOrganizationId(Organization organization) {
		return Optional.ofNullable(
			organization.getParentOrganization()
		).map(
			Organization::getId
		).orElse(
			(long)OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID
		);
	}

	private Page<Organization> _getOrganizationsPage(
			Long organizationId, Boolean flatten, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				if (GetterUtil.getBoolean(flatten)) {
					if (organizationId != 0) {
						booleanFilter.add(
							new QueryFilter(
								new WildcardQueryImpl(
									"treePath", "*" + organizationId + "*")));
						booleanFilter.add(
							new TermFilter(
								"organizationId",
								String.valueOf(organizationId)),
							BooleanClauseOccur.MUST_NOT);
					}
				}
				else {
					booleanFilter.add(
						new TermFilter(
							"parentOrganizationId",
							String.valueOf(organizationId)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, com.liferay.portal.kernel.model.Organization.class, search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			document -> _toOrganization(
				_organizationService.getOrganization(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	private long _getRegionId(Organization organization, long countryId) {
		return Optional.ofNullable(
			organization.getLocation()
		).map(
			Location::getAddressRegion
		).map(
			addressRegion -> _getRegionId(addressRegion, countryId)
		).orElse(
			(long)0
		);
	}

	private long _getRegionId(String addressRegion, long countryId) {
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

	private Address _toAddress(PostalAddress postalAddress) {
		String street1 = postalAddress.getStreetAddressLine1();
		String street2 = postalAddress.getStreetAddressLine2();
		String street3 = postalAddress.getStreetAddressLine3();
		String city = postalAddress.getAddressLocality();
		String zip = postalAddress.getPostalCode();
		long countryId = _toCountryId(postalAddress.getAddressCountry());

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
			_getRegionId(postalAddress.getAddressRegion(), countryId));
		address.setCountryId(countryId);
		address.setTypeId(
			_toAddressTypeId(
				Optional.ofNullable(
					postalAddress.getAddressType()
				).orElse(
					PostalAddress.AddressType.OTHER
				)));
		address.setMailing(true);
		address.setPrimary(GetterUtil.getBoolean(postalAddress.getPrimary()));

		return address;
	}

	private long _toAddressTypeId(PostalAddress.AddressType addressType) {
		ListType listType = _listTypeLocalService.getListType(
			addressType.getValue(), ListTypeConstants.ORGANIZATION_ADDRESS);

		return listType.getListTypeId();
	}

	private Country _toCountry(String addressCountry) {
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
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	private long _toCountryId(String addressCountry) {
		return Optional.ofNullable(
			addressCountry
		).map(
			this::_toCountry
		).map(
			Country::getCountryId
		).orElse(
			(long)0
		);
	}

	private Organization _toOrganization(
			com.liferay.portal.kernel.model.Organization organization)
		throws Exception {

		if (organization == null) {
			return null;
		}

		return new Organization() {
			{
				comment = organization.getComments();
				customFields = CustomFieldsUtil.toCustomFields(
					com.liferay.portal.kernel.model.Organization.class.
						getName(),
					organization.getOrganizationId(),
					organization.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale());
				dateCreated = organization.getCreateDate();
				dateModified = organization.getModifiedDate();
				id = organization.getOrganizationId();
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
				organizationContactInformation =
					new OrganizationContactInformation() {
						{
							emailAddresses = transformToArray(
								_emailAddressService.getEmailAddresses(
									organization.getModelClassName(),
									organization.getOrganizationId()),
								EmailAddressUtil::toEmail, EmailAddress.class);
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
			}
		};
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

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationResourceImpl.class);

	private static final EntityModel _entityModel =
		new OrganizationEntityModel();

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CountryService _countryService;

	@Reference
	private EmailAddressService _emailAddressService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

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