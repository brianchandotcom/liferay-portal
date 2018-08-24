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

package com.liferay.organization.apio.internal.architect.resource;

import com.liferay.address.apio.architect.identifier.AddressIdentifier;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.email.apio.architect.identifier.EmailIdentifier;
import com.liferay.organization.apio.architect.identifier.OrganizationIdentifier;
import com.liferay.organization.apio.internal.model.OpeningHours;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.petra.string.StringBundler;
import com.liferay.phone.apio.architect.identifier.PhoneIdentifier;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.OrgLaborService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import com.liferay.web.url.apio.architect.identifier.WebUrlIdentifier;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/Organization">Organization </a> resources through a
 * web API. The resources are mapped from the internal model {@code
 * Organization}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class OrganizationCollectionResource
	implements CollectionResource<Organization, Long, OrganizationIdentifier> {

	@Override
	public CollectionRoutes<Organization, Long> collectionRoutes(
		CollectionRoutes.Builder<Organization, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "organization";
	}

	@Override
	public ItemRoutes<Organization, Long> itemRoutes(
		ItemRoutes.Builder<Organization, Long> builder) {

		return builder.addGetter(
			_organizationService::getOrganization
		).build();
	}

	@Override
	public Representor<Organization> representor(
		Representor.Builder<Organization, Long> builder) {

		return builder.types(
			"Organization"
		).identifier(
			Organization::getOrganizationId
		).addBidirectionalModel(
			"parentOrganization", "subOrganization",
			OrganizationIdentifier.class,
			OrganizationCollectionResource::_getParentOrganizationId
		).addLinkedModel(
			"website", WebSiteIdentifier.class, this::_getSiteId
		).addNested(
			"location", organization -> organization,
			nestedBuilder -> nestedBuilder.types(
				"PostalAddress"
			).addLocalizedStringByLocale(
				"addressCountry", this::_getCountry
			).addString(
				"addressRegion", this::_getRegion
			).build()
		).addNestedList(
			"services", this::_getOrgLabors,
			this::_getServiceNestedRepresentorFunction
		).addRelatedCollection(
			"address", AddressIdentifier.class
		).addRelatedCollection(
			"email", EmailIdentifier.class
		).addRelatedCollection(
			"phones", PhoneIdentifier.class
		).addRelatedCollection(
			"members", PersonIdentifier.class
		).addRelatedCollection(
			"telephone", PhoneIdentifier.class
		).addRelatedCollection(
			"webUrl", WebUrlIdentifier.class
		).addRelativeURL(
			"logo", this::_getLogoURL
		).addString(
			"comment", Organization::getComments
		).addString(
			"name", Organization::getName
		).build();
	}

	private static Long _getParentOrganizationId(Organization organization) {
		long parentOrganizationId = organization.getParentOrganizationId();

		if (parentOrganizationId <= 0) {
			return null;
		}

		return parentOrganizationId;
	}

	private String _getCountry(Organization organization, Locale locale) {
		return Try.success(
			organization.getCountryId()
		).map(
			_countryService::getCountry
		).map(
			country -> country.getName(locale)
		).orElse(
			null
		);
	}

	private List<OpeningHours> _getDay(OrgLabor orgLabor) {
		return Arrays.asList(
			new OpeningHours(
				"Friday", orgLabor::getFriOpen, orgLabor::getFriClose),
			new OpeningHours(
				"Monday", orgLabor::getMonOpen, orgLabor::getMonClose),
			new OpeningHours(
				"Thursday", orgLabor::getThuOpen, orgLabor::getThuClose),
			new OpeningHours(
				"Tuesday", orgLabor::getTueOpen, orgLabor::getTueClose),
			new OpeningHours(
				"Saturday", orgLabor::getSatOpen, orgLabor::getSatClose),
			new OpeningHours(
				"Sunday", orgLabor::getSunOpen, orgLabor::getSunClose),
			new OpeningHours(
				"Wednesday", orgLabor::getWedOpen, orgLabor::getWedClose));
	}

	private String _getLogoURL(Organization organization) {
		return Try.success(
			organization.getLogoId()
		).filter(
			logoId -> logoId != 0
		).map(
			logoId -> StringBundler.concat(
				_portal.getPathImage(), "/organization_logo?img_id=", logoId,
				"&t=", WebServerServletTokenUtil.getToken(logoId))
		).orElse(
			null
		);
	}

	private List<OrgLabor> _getOrgLabors(Organization organization) {
		return Try.fromFallible(
			() -> _orgLaborService.getOrgLabors(
				organization.getOrganizationId())
		).orElse(
			null
		);
	}

	private String _getOrgLaborType(OrgLabor orgLabor) {
		return Try.fromFallible(
			orgLabor::getType
		).map(
			ListType::getName
		).orElse(
			null
		);
	}

	private PageItems<Organization> _getPageItems(
		Pagination pagination, Company company) {

		List<Organization> organizations =
			_organizationService.getOrganizations(
				company.getCompanyId(), 0, pagination.getStartPosition(),
				pagination.getEndPosition());
		int count = _organizationService.getOrganizationsCount(
			company.getCompanyId(), 0);

		return new PageItems<>(organizations, count);
	}

	private String _getRegion(Organization organization) {
		return Try.success(
			organization.getRegionId()
		).map(
			_regionService::getRegion
		).map(
			Region::getName
		).orElse(
			null
		);
	}

	private NestedRepresentor<OrgLabor> _getServiceNestedRepresentorFunction(
		NestedRepresentor.Builder<OrgLabor> orgLaborBuilder) {

		return orgLaborBuilder.types(
			"OrgLabor", "OpeningHoursSpecification"
		).addString(
			"serviceType", this::_getOrgLaborType
		).addNestedList(
			"hoursAvailable", this::_getDay,
			nestedBuilder -> nestedBuilder.types(
				"OpeningHoursSpecification"
			).addString(
				"closes", OpeningHours::getCloses
			).addString(
				"dayOfWeek", OpeningHours::getDay
			).addString(
				"opens", OpeningHours::getOpens
			).build()
		).build();
	}

	private Long _getSiteId(Organization organization) {
		return Try.success(
			organization.getGroupId()
		).map(
			_groupService::getGroup
		).filter(
			Group::isSite
		).map(
			Group::getGroupId
		).orElse(
			null
		);
	}

	@Reference
	private CountryService _countryService;

	@Reference
	private GroupService _groupService;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private OrgLaborService _orgLaborService;

	@Reference
	private Portal _portal;

	@Reference
	private RegionService _regionService;

}