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

import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.internal.dto.v1_0.helper.OrganizationResourceDTOConverter;
import com.liferay.headless.admin.user.internal.odata.entity.v1_0.OrganizationEntityModel;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

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

	private long _getOrganizationId(String organizationId) {
		if (organizationId == null) {
			return 0;
		}

		com.liferay.portal.kernel.model.Organization organization =
			_organizationLocalService.fetchOrganizationByReferenceCode(
				CompanyThreadLocal.getCompanyId(), organizationId);

		if (organization == null) {
			return GetterUtil.getLong(organizationId);
		}

		return GetterUtil.getLong(organization.getOrganizationId());
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

	private Organization _toOrganization(Object organizationId)
		throws Exception {

		return _organizationResourceDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.getPreferredLocale(), organizationId,
				contextUriInfo, contextUser));
	}

	private static final EntityModel _entityModel =
		new OrganizationEntityModel();

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationResourceDTOConverter _organizationResourceDTOConverter;

	@Reference
	private OrganizationService _organizationService;

}