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

package com.liferay.headless.commerce.admin.account.internal.resource.v1_0;

import com.liferay.commerce.account.exception.NoSuchAccountGroupException;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountGroup;
import com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountGroupDTOConverter;
import com.liferay.headless.commerce.admin.account.internal.odata.entity.v1_0.AccountGroupEntityModel;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountGroupResource;
import com.liferay.headless.commerce.core.util.ExpandoUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/account-group.properties",
	scope = ServiceScope.PROTOTYPE, service = AccountGroupResource.class
)
public class AccountGroupResourceImpl
	extends BaseAccountGroupResourceImpl implements EntityModelResource {

	@Override
	public Response deleteAccountGroup(Long id) throws Exception {
		_commerceAccountGroupService.deleteCommerceAccountGroup(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response deleteAccountGroupByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceAccountGroup commerceAccountGroup =
			_commerceAccountGroupService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccountGroup == null) {
			throw new NoSuchAccountGroupException(
				"Unable to find AccountGroup with externalReferenceCode: " +
					externalReferenceCode);
		}

		_commerceAccountGroupService.deleteCommerceAccountGroup(
			commerceAccountGroup.getCommerceAccountGroupId());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public AccountGroup getAccountGroup(Long id) throws Exception {
		return _accountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				GetterUtil.getLong(id),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public AccountGroup getAccountGroupByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceAccountGroup commerceAccountGroup =
			_commerceAccountGroupService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccountGroup == null) {
			throw new NoSuchAccountGroupException(
				"Unable to find AccountGroup with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _accountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountGroup.getCommerceAccountGroupId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Page<AccountGroup> getAccountGroupsPage(
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			com.liferay.account.model.AccountGroup.class, StringPool.BLANK,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setCompanyId(contextCompany.getCompanyId());
				}

			},
			document -> _toAccountGroup(
				_commerceAccountGroupService.getCommerceAccountGroup(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Response patchAccountGroup(Long id, AccountGroup accountGroup)
		throws Exception {

		_commerceAccountGroupService.updateCommerceAccountGroup(
			id, accountGroup.getName(),
			_serviceContextHelper.getServiceContext());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchAccountGroupByExternalReferenceCode(
			String externalReferenceCode, AccountGroup accountGroup)
		throws Exception {

		CommerceAccountGroup commerceAccountGroup =
			_commerceAccountGroupService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccountGroup == null) {
			throw new NoSuchAccountGroupException(
				"Unable to find AccountGroup with externalReferenceCode: " +
					externalReferenceCode);
		}

		_commerceAccountGroupService.updateCommerceAccountGroup(
			commerceAccountGroup.getCommerceAccountGroupId(),
			accountGroup.getName(), _serviceContextHelper.getServiceContext());

		// Expando

		Map<String, ?> customFields = accountGroup.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				contextCompany.getCompanyId(), CommerceAccountGroup.class,
				commerceAccountGroup.getPrimaryKey(), customFields);
		}

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public AccountGroup postAccountGroup(AccountGroup accountGroup)
		throws Exception {

		CommerceAccountGroup commerceAccountGroup = null;

		if (Validator.isNotNull(accountGroup.getExternalReferenceCode())) {
			commerceAccountGroup =
				_commerceAccountGroupService.fetchByExternalReferenceCode(
					contextCompany.getCompanyId(),
					accountGroup.getExternalReferenceCode());
		}

		if (commerceAccountGroup == null) {
			commerceAccountGroup =
				_commerceAccountGroupService.addCommerceAccountGroup(
					contextCompany.getCompanyId(), accountGroup.getName(), 0,
					accountGroup.getExternalReferenceCode(),
					_serviceContextHelper.getServiceContext());
		}
		else {
			commerceAccountGroup =
				_commerceAccountGroupService.updateCommerceAccountGroup(
					commerceAccountGroup.getCommerceAccountGroupId(),
					accountGroup.getName(),
					_serviceContextHelper.getServiceContext());
		}

		// Expando

		Map<String, ?> customFields = accountGroup.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				contextCompany.getCompanyId(), CommerceAccountGroup.class,
				commerceAccountGroup.getPrimaryKey(), customFields);
		}

		return _accountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountGroup.getCommerceAccountGroupId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private AccountGroup _toAccountGroup(
			CommerceAccountGroup commerceAccountGroup)
		throws Exception {

		return _accountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountGroup.getCommerceAccountGroupId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private AccountGroupDTOConverter _accountGroupDTOConverter;

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

	private final EntityModel _entityModel = new AccountGroupEntityModel();

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}