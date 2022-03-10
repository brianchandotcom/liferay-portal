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

import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.headless.admin.user.dto.v1_0.HoursAvailable;
import com.liferay.headless.admin.user.dto.v1_0.Location;
import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.dto.v1_0.OrganizationContactInformation;
import com.liferay.headless.admin.user.dto.v1_0.Service;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.AccountResourceDTOConverter;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.OrganizationResourceDTOConverter;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.UserResourceDTOConverter;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderCountryUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderEmailAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderListTypeUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderPhoneUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderRegionUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderWebsiteUtil;
import com.liferay.headless.admin.user.internal.odata.entity.v1_0.OrganizationEntityModel;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.headless.admin.user.resource.v1_0.RoleResource;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.service.OrgLaborLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, OrganizationResource.class}
)
public class OrganizationResourceImpl
	extends BaseOrganizationResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteAccountByExternalReferenceCodeOrganization(
			String externalReferenceCode, String organizationId)
		throws Exception {

		long accountId = _accountResourceDTOConverter.getAccountEntryId(
			externalReferenceCode);

		deleteAccountOrganization(accountId, organizationId);
	}

	@Override
	public void deleteAccountOrganization(Long accountId, String organizationId)
		throws Exception {

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				accountId, GetterUtil.getLong(organizationId));
	}

	@Override
	public void deleteOrganization(String organizationId) throws Exception {
		long serviceBuilderOrganizationId = _getServiceBuilderOrganizationId(
			organizationId);

		_organizationService.deleteOrganization(serviceBuilderOrganizationId);
	}

	@Override
	public void deleteOrganizationByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		com.liferay.portal.kernel.model.Organization organization =
			_organizationService.getOrganizationByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		_organizationService.deleteOrganization(
			organization.getOrganizationId());
	}

	@Override
	public void deleteUserAccountByEmailAddress(
			String organizationId, String emailAddress)
		throws Exception {

		_organizationService.deleteUserOrganizationByEmailAddress(
			emailAddress, _getServiceBuilderOrganizationId(organizationId));
	}

	@Override
	public void deleteUserAccountsByEmailAddress(
			String organizationId, String[] emailAddresses)
		throws Exception {

		for (String emailAddress : emailAddresses) {
			deleteUserAccountByEmailAddress(organizationId, emailAddress);
		}
	}

	@Override
	public Page<Organization>
			getAccountByExternalReferenceCodeOrganizationsPage(
				String externalReferenceCode, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		long accountId = _accountResourceDTOConverter.getAccountEntryId(
			externalReferenceCode);

		return getAccountOrganizationsPage(
			accountId, search, filter, pagination, sorts);
	}

	@Override
	public Page<Organization> getAccountOrganizationsPage(
			Long accountId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"accountEntryIds", String.valueOf(accountId)),
					BooleanClauseOccur.MUST);
			},
			filter,
			com.liferay.portal.kernel.model.Organization.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toOrganization(
				GetterUtil.getString(document.get(Field.ENTRY_CLASS_PK))));
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
	public Organization getOrganizationByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		com.liferay.portal.kernel.model.Organization
			serviceBuilderOrganization =
				_organizationService.getOrganizationByExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		return _organizationResourceDTOConverter.toDTO(
			_getDTOConverterContext(
				String.valueOf(serviceBuilderOrganization.getOrganizationId())),
			serviceBuilderOrganization);
	}

	@NestedField(parentClass = Organization.class, value = "childOrganizations")
	@Override
	public Page<Organization> getOrganizationChildOrganizationsPage(
			String organizationId, Boolean flatten, String search,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getOrganizationsPage(
			HashMapBuilder.put(
				"get",
				addAction(
					"VIEW", "getOrganizationChildOrganizationsPage",
					com.liferay.portal.kernel.model.Organization.class.
						getName(),
					_getServiceBuilderOrganizationId(organizationId))
			).build(),
			organizationId, flatten, filter, search, pagination, sorts);
	}

	@Override
	public Page<Organization> getOrganizationOrganizationsPage(
			String parentOrganizationId, Boolean flatten, String search,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getOrganizationsPage(
			HashMapBuilder.put(
				"get",
				addAction(
					"VIEW", "getOrganizationOrganizationsPage",
					com.liferay.portal.kernel.model.Organization.class.
						getName(),
					_getServiceBuilderOrganizationId(parentOrganizationId))
			).build(),
			parentOrganizationId, flatten, filter, search, pagination, sorts);
	}

	@Override
	public Page<Organization> getOrganizationsPage(
			Boolean flatten, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getOrganizationsPage(
			HashMapBuilder.put(
				"create",
				addAction(
					"ADD_ORGANIZATION", "postOrganization",
					com.liferay.portal.kernel.model.Organization.class.
						getName(),
					0L)
			).put(
				"get",
				addAction(
					"VIEW", "getOrganizationsPage",
					com.liferay.portal.kernel.model.Organization.class.
						getName(),
					0L)
			).build(),
			null, flatten, filter, search, pagination, sorts);
	}

	@Override
	public void postAccountByExternalReferenceCodeOrganization(
			String externalReferenceCode, String organizationId)
		throws Exception {

		long accountId = _accountResourceDTOConverter.getAccountEntryId(
			externalReferenceCode);

		postAccountOrganization(accountId, organizationId);
	}

	@Override
	public void postAccountOrganization(Long accountId, String organizationId)
		throws Exception {

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountId, GetterUtil.getLong(organizationId));
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
			_getDTOConverterContext(
				String.valueOf(serviceBuilderOrganization.getOrganizationId())),
			serviceBuilderOrganization);
	}

	@Override
	public UserAccount postUserAccountByEmailAddress(
			String organizationId, String emailAddress)
		throws Exception {

		User user = _organizationService.addOrganizationUserByEmailAddress(
			emailAddress, _getServiceBuilderOrganizationId(organizationId),
			new ServiceContext() {
				{
					setCompanyId(contextCompany.getCompanyId());
					setLanguageId(
						contextAcceptLanguage.getPreferredLanguageId());
					setUserId(contextUser.getUserId());
				}
			});

		return _userResourceDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, user.getUserId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			user);
	}

	@Override
	public Page<UserAccount> postUserAccountsByEmailAddress(
			String organizationId, String organizationRoleIds,
			String[] emailAddresses)
		throws Exception {

		List<UserAccount> userAccounts = transformToList(
			emailAddresses,
			emailAddress -> postUserAccountByEmailAddress(
				organizationId, emailAddress));

		if (Validator.isNull(organizationRoleIds)) {
			return Page.of(userAccounts);
		}

		String[] organizationRoleIdsArray = StringUtil.split(
			organizationRoleIds, CharPool.COMMA);

		for (UserAccount userAccount : userAccounts) {
			for (String organizationRoleId : organizationRoleIdsArray) {
				_roleResource.postOrganizationRoleUserAccountAssociation(
					GetterUtil.getLong(organizationRoleId), userAccount.getId(),
					GetterUtil.getLong(organizationId));
			}
		}

		return Page.of(
			transform(
				userAccounts,
				userAccount -> _userResourceDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						contextAcceptLanguage.isAcceptAllLanguages(), null,
						_dtoConverterRegistry, userAccount.getId(),
						contextAcceptLanguage.getPreferredLocale(),
						contextUriInfo, contextUser),
					_userService.getUserByEmailAddress(
						contextCompany.getCompanyId(),
						userAccount.getEmailAddress()))));
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
	public Organization putOrganizationByExternalReferenceCode(
			String externalReferenceCode, Organization organization)
		throws Exception {

		com.liferay.portal.kernel.model.Organization
			serviceBuilderOrganization =
				_organizationLocalService.fetchOrganizationByReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		String type = OrganizationConstants.TYPE_ORGANIZATION;

		if (serviceBuilderOrganization != null) {
			type = serviceBuilderOrganization.getType();
		}

		long countryId = _getCountryId(organization);

		long statusId = ListTypeConstants.ORGANIZATION_STATUS_DEFAULT;
		boolean site = false;

		if (serviceBuilderOrganization != null) {
			statusId = serviceBuilderOrganization.getStatusId();

			Group group = serviceBuilderOrganization.getGroup();

			site = group.isSite();
		}

		serviceBuilderOrganization =
			_organizationService.addOrUpdateOrganization(
				externalReferenceCode,
				_getDefaultParentOrganizationId(organization),
				organization.getName(), type,
				_getRegionId(organization, countryId), countryId, statusId,
				organization.getComment(), false, null, site,
				_getAddresses(organization), _getEmailAddresses(organization),
				_getOrgLabors(organization), _getPhones(organization),
				_getWebsites(organization),
				ServiceContextFactory.getInstance(contextHttpServletRequest));

		return _organizationResourceDTOConverter.toDTO(
			_getDTOConverterContext(
				String.valueOf(serviceBuilderOrganization.getOrganizationId())),
			serviceBuilderOrganization);
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

		Organization parentOrganization = organization.getParentOrganization();

		if (parentOrganization != null) {
			try {
				existingOrganization.setParentOrganization(
					_toOrganization(parentOrganization.getId()));
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
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
				transformToList(
					postalAddresses,
					_postalAddress ->
						ServiceBuilderAddressUtil.toServiceBuilderAddress(
							contextCompany.getCompanyId(), _postalAddress,
							ListTypeConstants.ORGANIZATION_ADDRESS)),
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
			addressCountry ->
				ServiceBuilderCountryUtil.toServiceBuilderCountryId(
					contextCompany.getCompanyId(), addressCountry)
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
			String organizationId)
		throws Exception {

		Long serviceBuilderOrganizationId = _getServiceBuilderOrganizationId(
			organizationId);

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			HashMapBuilder.put(
				"delete",
				addAction(
					"DELETE", "deleteOrganization",
					com.liferay.portal.kernel.model.Organization.class.
						getName(),
					serviceBuilderOrganizationId)
			).put(
				"get",
				addAction(
					"VIEW", "getOrganization",
					com.liferay.portal.kernel.model.Organization.class.
						getName(),
					serviceBuilderOrganizationId)
			).put(
				"replace",
				addAction(
					"UPDATE", "putOrganization",
					com.liferay.portal.kernel.model.Organization.class.
						getName(),
					serviceBuilderOrganizationId)
			).put(
				"update",
				addAction(
					"UPDATE", "patchOrganization",
					com.liferay.portal.kernel.model.Organization.class.
						getName(),
					serviceBuilderOrganizationId)
			).build(),
			null, organizationId, contextAcceptLanguage.getPreferredLocale(),
			contextUriInfo, contextUser);
	}

	private List<EmailAddress> _getEmailAddresses(Organization organization) {
		return Optional.ofNullable(
			organization.getOrganizationContactInformation()
		).map(
			OrganizationContactInformation::getEmailAddresses
		).map(
			emailAddresses -> ListUtil.filter(
				transformToList(
					emailAddresses,
					emailAddress ->
						ServiceBuilderEmailAddressUtil.
							toServiceBuilderEmailAddress(
								emailAddress,
								ListTypeConstants.ORGANIZATION_EMAIL_ADDRESS)),
				Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
	}

	private Page<Organization> _getOrganizationsPage(
			Map<String, Map<String, String>> actions,
			String parentOrganizationId, Boolean flatten, Filter filter,
			String keywords, Pagination pagination, Sort[] sorts)
		throws Exception {

		long serviceBuilderOrganizationId = _getServiceBuilderOrganizationId(
			parentOrganizationId);

		return SearchUtil.search(
			actions,
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				if (GetterUtil.getBoolean(flatten)) {
					if (serviceBuilderOrganizationId != 0L) {
						booleanFilter.add(
							new QueryFilter(
								new WildcardQueryImpl(
									"treePath",
									"*" + parentOrganizationId + "*")));
						booleanFilter.add(
							new TermFilter(
								"organizationId",
								String.valueOf(parentOrganizationId)),
							BooleanClauseOccur.MUST_NOT);
					}
				}
				else {
					booleanFilter.add(
						new TermFilter(
							"parentOrganizationId",
							String.valueOf(serviceBuilderOrganizationId)),
						BooleanClauseOccur.MUST);
				}
			},
			filter,
			com.liferay.portal.kernel.model.Organization.class.getName(),
			keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toOrganization(
				GetterUtil.getString(document.get(Field.ENTRY_CLASS_PK))));
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

	private List<Phone> _getPhones(Organization organization) {
		return Optional.ofNullable(
			organization.getOrganizationContactInformation()
		).map(
			OrganizationContactInformation::getTelephones
		).map(
			telephones -> ListUtil.filter(
				transformToList(
					telephones,
					telephone -> ServiceBuilderPhoneUtil.toServiceBuilderPhone(
						telephone, ListTypeConstants.ORGANIZATION_PHONE)),
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
			addressRegion -> ServiceBuilderRegionUtil.getServiceBuilderRegionId(
				addressRegion, countryId)
		).orElse(
			(long)0
		);
	}

	private long _getServiceBuilderOrganizationId(String organizationId)
		throws Exception {

		if (organizationId == null) {
			return 0;
		}

		com.liferay.portal.kernel.model.Organization
			serviceBuilderOrganization =
				_organizationResourceDTOConverter.getObject(organizationId);

		if (serviceBuilderOrganization == null) {
			return GetterUtil.getLong(organizationId);
		}

		return serviceBuilderOrganization.getOrganizationId();
	}

	private List<Website> _getWebsites(Organization organization) {
		return Optional.ofNullable(
			organization.getOrganizationContactInformation()
		).map(
			OrganizationContactInformation::getWebUrls
		).map(
			webUrls -> ListUtil.filter(
				transformToList(
					webUrls,
					webUrl -> ServiceBuilderWebsiteUtil.toServiceBuilderWebsite(
						ListTypeConstants.ORGANIZATION_WEBSITE, webUrl)),
				Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
	}

	private Organization _toOrganization(String organizationId)
		throws Exception {

		if (Validator.isBlank(organizationId)) {
			return null;
		}

		return _organizationResourceDTOConverter.toDTO(
			_getDTOConverterContext(organizationId));
	}

	private OrgLabor _toOrgLabor(Service service) {
		long typeId = ServiceBuilderListTypeUtil.toServiceBuilderListTypeId(
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
				_log.warn(parseException);
			}

			return -1;
		}

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat("HHmm");

		return GetterUtil.getInteger(format.format(date));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationResourceImpl.class);

	private static final EntityModel _entityModel =
		new OrganizationEntityModel();

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountResourceDTOConverter _accountResourceDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationResourceDTOConverter _organizationResourceDTOConverter;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private OrgLaborLocalService _orgLaborLocalService;

	@Reference
	private RoleResource _roleResource;

	@Reference
	private UserResourceDTOConverter _userResourceDTOConverter;

	@Reference
	private UserService _userService;

}