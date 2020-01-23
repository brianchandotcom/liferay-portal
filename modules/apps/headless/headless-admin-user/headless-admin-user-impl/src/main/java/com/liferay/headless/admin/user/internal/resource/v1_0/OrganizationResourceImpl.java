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
import com.liferay.headless.admin.user.dto.v1_0.HoursAvailable;
import com.liferay.headless.admin.user.dto.v1_0.Location;
import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.dto.v1_0.OrganizationContactInformation;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.Service;
import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.internal.dto.v1_0.helper.OrganizationResourceDTOConverter;
import com.liferay.headless.admin.user.internal.odata.entity.v1_0.OrganizationEntityModel;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.OrgLaborLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;

import java.util.Collections;
import java.util.Date;
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
	public void deleteOrganization(String organizationId) throws Exception {
		long id = _getOrganizationId(organizationId);

		_organizationService.deleteOrganization(id);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Organization getOrganization(String organizationId)
		throws Exception {

		return _toOrganization(organizationId);
	}

	@Override
	public Page<Organization> getOrganizationOrganizationsPage(
			String parentOrganizationId, Boolean flatten, String search,
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
			null, flatten, search, filter, pagination, sorts);
	}

	@Override
	public Organization postOrganization(Organization organization)
		throws Exception {

		long countryId = _getCountryId(organization);

		com.liferay.portal.kernel.model.Organization
			serviceBuilderOrganization = _organizationService.addOrganization(
				_getDefaultParentOrganizationId(organization),
				organization.getName(), OrganizationConstants.TYPE_ORGANIZATION,
				_getRegionId(organization, countryId), countryId,
				ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
				organization.getComment(), false, _getAddresses(organization),
				_getEmailAddresses(organization), _getOrgLabors(organization),
				_getPhones(organization), _getWebsites(organization),
				ServiceContextFactory.getInstance(contextHttpServletRequest));

		return _organizationResourceDTOConverter.toDTO(
			_getDTOConverterContext(String.valueOf(serviceBuilderOrganization)),
			serviceBuilderOrganization);
	}

	@Override
	public Organization putOrganization(
			String organizationId, Organization organization)
		throws Exception {

		com.liferay.portal.kernel.model.Organization
			serviceBuilderOrganization =
				_organizationResourceDTOConverter.getObject(organizationId);
		long countryId = _getCountryId(organization);
		Group group = serviceBuilderOrganization.getGroup();

		return _organizationResourceDTOConverter.toDTO(
			_getDTOConverterContext(organizationId),
			_organizationService.updateOrganization(
				serviceBuilderOrganization.getOrganizationId(),
				_getDefaultParentOrganizationId(organization),
				organization.getName(), serviceBuilderOrganization.getType(),
				_getRegionId(organization, countryId), countryId,
				serviceBuilderOrganization.getStatusId(),
				organization.getComment(), false, null, group.isSite(),
				_getAddresses(organization), _getEmailAddresses(organization),
				_getOrgLabors(organization), _getPhones(organization),
				_getWebsites(organization),
				ServiceContextFactory.getInstance(contextHttpServletRequest)));
	}

	@Override
	protected void preparePatch(
		Organization organization, Organization existingOrganization) {

		OrganizationContactInformation organizationContactInformation =
			organization.getOrganizationContactInformation();

		if (organizationContactInformation != null) {
			OrganizationContactInformation
				existingOrganizationContactInformation =
					existingOrganization.getOrganizationContactInformation();

			if (organizationContactInformation.getEmailAddresses() != null) {
				existingOrganizationContactInformation.setEmailAddresses(
					organizationContactInformation.getEmailAddresses());
			}

			if (organizationContactInformation.getPostalAddresses() != null) {
				existingOrganizationContactInformation.setPostalAddresses(
					organizationContactInformation.getPostalAddresses());
			}

			if (organizationContactInformation.getTelephones() != null) {
				existingOrganizationContactInformation.setTelephones(
					organizationContactInformation.getTelephones());
			}

			if (organizationContactInformation.getWebUrls() != null) {
				existingOrganizationContactInformation.setWebUrls(
					organizationContactInformation.getWebUrls());
			}
		}

		if (organization.getServices() != null) {
			existingOrganization.setServices(organization.getServices());
		}
	}

	private List<Address> _getAddresses(Organization organization) {
		return Optional.ofNullable(
			organization.getOrganizationContactInformation()
		).map(
			OrganizationContactInformation::getPostalAddresses
		).map(
			postalAddresses -> ListUtil.filter(
				transformToList(postalAddresses, this::_toAddress),
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
		).orElse(
			0L
		);
	}

	private long _getDefaultParentOrganizationId(Organization organization) {
		return Optional.ofNullable(
			organization.getParentOrganization()
		).map(
			Organization::getId
		).map(
			Long::valueOf
		).orElse(
			(long)OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID
		);
	}

	private DefaultDTOConverterContext _getDTOConverterContext(
		String organizationId) {

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(), null, organizationId,
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private List<com.liferay.portal.kernel.model.EmailAddress>
		_getEmailAddresses(Organization organization) {

		return Optional.ofNullable(
			organization.getOrganizationContactInformation()
		).map(
			OrganizationContactInformation::getEmailAddresses
		).map(
			emailAddresses -> ListUtil.filter(
				transformToList(
					emailAddresses, this::_toServiceBuilderEmailAddress),
				Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
	}

	private long _getOrganizationId(String organizationId) {
		if (organizationId == null) {
			return 0;
		}

		com.liferay.portal.kernel.model.Organization
			serviceBuilderOrganization =
				_organizationLocalService.fetchOrganizationByReferenceCode(
					CompanyThreadLocal.getCompanyId(), organizationId);

		if (serviceBuilderOrganization == null) {
			return GetterUtil.getLong(organizationId);
		}

		return GetterUtil.getLong(
			serviceBuilderOrganization.getOrganizationId());
	}

	private Page<Organization> _getOrganizationsPage(
			String organizationId, Boolean flatten, String search,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		long id = _getOrganizationId(organizationId);

		return SearchUtil.search(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				if (GetterUtil.getBoolean(flatten)) {
					if (id != 0L) {
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
							"parentOrganizationId", String.valueOf(id)),
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
				GetterUtil.getString(document.get(Field.ENTRY_CLASS_PK))),
			sorts);
	}

	private List<OrgLabor> _getOrgLabors(Organization organization) {
		return Optional.ofNullable(
			organization.getServices()
		).map(
			services -> ListUtil.filter(
				transformToList(services, this::_toOrgLabor), Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
	}

	private List<com.liferay.portal.kernel.model.Phone> _getPhones(
		Organization organization) {

		return Optional.ofNullable(
			organization.getOrganizationContactInformation()
		).map(
			OrganizationContactInformation::getTelephones
		).map(
			telephones -> ListUtil.filter(
				transformToList(telephones, this::_toServiceBuilderPhone),
				Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
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

	private List<Website> _getWebsites(Organization organization) {
		return Optional.ofNullable(
			organization.getOrganizationContactInformation()
		).map(
			OrganizationContactInformation::getWebUrls
		).map(
			webUrls -> ListUtil.filter(
				transformToList(webUrls, this::_toWebsite), Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
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
			_toListTypeId(
				"other", postalAddress.getAddressType(),
				ListTypeConstants.ORGANIZATION_ADDRESS));
		address.setMailing(true);
		address.setPrimary(GetterUtil.getBoolean(postalAddress.getPrimary()));

		return address;
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
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
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

	private Organization _toOrganization(String organizationId)
		throws Exception {

		return _organizationResourceDTOConverter.toDTO(
			_getDTOConverterContext(organizationId));
	}

	private OrgLabor _toOrgLabor(Service service) {
		long typeId = _toListTypeId(
			"administrative", service.getServiceType(),
			ListTypeConstants.ORGANIZATION_SERVICE);

		if (typeId == -1) {
			return null;
		}

		OrgLabor orgLabor = _orgLaborLocalService.createOrgLabor(0);

		orgLabor.setTypeId(typeId);

		HoursAvailable[] hoursAvailableArray = service.getHoursAvailable();

		if (ArrayUtil.isEmpty(hoursAvailableArray)) {
			return null;
		}

		orgLabor.setSunOpen(-1);
		orgLabor.setSunClose(-1);
		orgLabor.setMonOpen(-1);
		orgLabor.setMonClose(-1);
		orgLabor.setTueOpen(-1);
		orgLabor.setTueClose(-1);
		orgLabor.setWedOpen(-1);
		orgLabor.setWedClose(-1);
		orgLabor.setThuOpen(-1);
		orgLabor.setThuClose(-1);
		orgLabor.setFriOpen(-1);
		orgLabor.setFriClose(-1);
		orgLabor.setSatOpen(-1);
		orgLabor.setSatClose(-1);

		for (HoursAvailable hoursAvailable : hoursAvailableArray) {
			String dayOfWeek = hoursAvailable.getDayOfWeek();

			if (Validator.isNull(dayOfWeek)) {
				continue;
			}

			dayOfWeek = StringUtil.toLowerCase(dayOfWeek);

			int opens = _toTime(hoursAvailable.getOpens());
			int closes = _toTime(hoursAvailable.getCloses());

			if (dayOfWeek.startsWith("sun")) {
				orgLabor.setSunOpen(opens);
				orgLabor.setSunClose(closes);
			}
			else if (dayOfWeek.startsWith("mon")) {
				orgLabor.setMonOpen(opens);
				orgLabor.setMonClose(closes);
			}
			else if (dayOfWeek.startsWith("tue")) {
				orgLabor.setTueOpen(opens);
				orgLabor.setTueClose(closes);
			}
			else if (dayOfWeek.startsWith("wed")) {
				orgLabor.setWedOpen(opens);
				orgLabor.setWedClose(closes);
			}
			else if (dayOfWeek.startsWith("thu")) {
				orgLabor.setThuOpen(opens);
				orgLabor.setThuClose(closes);
			}
			else if (dayOfWeek.startsWith("fri")) {
				orgLabor.setFriOpen(opens);
				orgLabor.setFriClose(closes);
			}
			else if (dayOfWeek.startsWith("sat")) {
				orgLabor.setSatOpen(opens);
				orgLabor.setSatClose(closes);
			}
		}

		return orgLabor;
	}

	private com.liferay.portal.kernel.model.EmailAddress
		_toServiceBuilderEmailAddress(EmailAddress emailAddress) {

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
			_toListTypeId(
				"email-address", emailAddress.getType(),
				ListTypeConstants.ORGANIZATION_EMAIL_ADDRESS));
		serviceBuilderEmailAddress.setPrimary(
			GetterUtil.getBoolean(emailAddress.getPrimary()));

		return serviceBuilderEmailAddress;
	}

	private com.liferay.portal.kernel.model.Phone _toServiceBuilderPhone(
		Phone phone) {

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
			_toListTypeId(
				"other", phone.getPhoneType(),
				ListTypeConstants.ORGANIZATION_PHONE));
		serviceBuilderPhone.setPrimary(
			GetterUtil.getBoolean(phone.getPrimary()));

		return serviceBuilderPhone;
	}

	private int _toTime(String timeString) {
		if (Validator.isNull(timeString)) {
			return -1;
		}

		Date date = null;

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"HH:mm");

		try {
			date = dateFormat.parse(timeString);
		}
		catch (ParseException parseException) {
			if (_log.isWarnEnabled()) {
				_log.warn(parseException, parseException);
			}

			return -1;
		}

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat("HHmm");

		return GetterUtil.getInteger(format.format(date));
	}

	private Website _toWebsite(WebUrl webUrl) {
		String url = webUrl.getUrl();

		if (Validator.isNull(url)) {
			return null;
		}

		Website website = _websiteLocalService.createWebsite(
			GetterUtil.getLong(webUrl.getId()));

		website.setUrl(url);
		website.setTypeId(
			_toListTypeId(
				"public", webUrl.getUrlType(),
				ListTypeConstants.ORGANIZATION_WEBSITE));
		website.setPrimary(GetterUtil.getBoolean(webUrl.getPrimary()));

		return website;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationResourceImpl.class);

	private static final EntityModel _entityModel =
		new OrganizationEntityModel();

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private CountryService _countryService;

	@Reference
	private EmailAddressLocalService _emailAddressLocalService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationResourceDTOConverter _organizationResourceDTOConverter;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private OrgLaborLocalService _orgLaborLocalService;

	@Reference
	private PhoneLocalService _phoneLocalService;

	@Reference
	private RegionService _regionService;

	@Reference
	private WebsiteLocalService _websiteLocalService;

}