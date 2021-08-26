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

import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelPK;
import com.liferay.headless.commerce.admin.account.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountOrganization;
import com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountOrganizationDTOConverter;
import com.liferay.headless.commerce.admin.account.internal.util.v1_0.AccountOrganizationUtil;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountOrganizationResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/account-organization.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {AccountOrganizationResource.class, NestedFieldSupport.class}
)
public class AccountOrganizationResourceImpl
	extends BaseAccountOrganizationResourceImpl implements NestedFieldSupport {

	@Override
	public Response deleteAccountByExternalReferenceCodeAccountOrganization(
			String externalReferenceCode, Long organizationId)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		_commerceAccountOrganizationRelService.
			deleteCommerceAccountOrganizationRel(
				commerceAccount.getCommerceAccountId(), organizationId);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response deleteAccountIdAccountOrganization(
			Long id, Long organizationId)
		throws Exception {

		_commerceAccountOrganizationRelService.
			deleteCommerceAccountOrganizationRel(id, organizationId);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public AccountOrganization
			getAccountByExternalReferenceCodeAccountOrganization(
				String externalReferenceCode, Long organizationId)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		CommerceAccountOrganizationRel commerceAccountOrganizationRel =
			_commerceAccountOrganizationRelService.
				getCommerceAccountOrganizationRel(
					new CommerceAccountOrganizationRelPK(
						commerceAccount.getCommerceAccountId(),
						organizationId));

		return _accountOrganizationDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountOrganizationRel.getPrimaryKey(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Page<AccountOrganization>
			getAccountByExternalReferenceCodeAccountOrganizationsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		List<CommerceAccountOrganizationRel> commerceAccountOrganizationRels =
			_commerceAccountOrganizationRelService.
				getCommerceAccountOrganizationRels(
					commerceAccount.getCommerceAccountId(),
					pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems =
			_commerceAccountOrganizationRelService.
				getCommerceAccountOrganizationRelsCount(
					commerceAccount.getCommerceAccountId());

		return Page.of(
			_toAccountOrganizations(commerceAccountOrganizationRels),
			pagination, totalItems);
	}

	@Override
	public AccountOrganization getAccountIdAccountOrganization(
			Long id, Long organizationId)
		throws Exception {

		return _accountOrganizationDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				new CommerceAccountOrganizationRelPK(id, organizationId),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = Account.class, value = "organizations")
	@Override
	public Page<AccountOrganization> getAccountIdAccountOrganizationsPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommerceAccountOrganizationRel> commerceAccountOrganizationRels =
			_commerceAccountOrganizationRelService.
				getCommerceAccountOrganizationRels(
					id, pagination.getStartPosition(),
					pagination.getEndPosition());

		int totalItems =
			_commerceAccountOrganizationRelService.
				getCommerceAccountOrganizationRelsCount(id);

		return Page.of(
			_toAccountOrganizations(commerceAccountOrganizationRels),
			pagination, totalItems);
	}

	@Override
	public AccountOrganization
			postAccountByExternalReferenceCodeAccountOrganization(
				String externalReferenceCode,
				AccountOrganization accountOrganization)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		CommerceAccountOrganizationRel commerceAccountOrganizationRel =
			_commerceAccountOrganizationRelService.
				addCommerceAccountOrganizationRel(
					commerceAccount.getCommerceAccountId(),
					AccountOrganizationUtil.getOrganizationId(
						_organizationLocalService, accountOrganization,
						contextCompany.getCompanyId()),
					_serviceContextHelper.getServiceContext());

		return _accountOrganizationDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountOrganizationRel.getPrimaryKey(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public AccountOrganization postAccountIdAccountOrganization(
			Long id, AccountOrganization accountOrganization)
		throws Exception {

		CommerceAccountOrganizationRel commerceAccountOrganizationRel =
			_commerceAccountOrganizationRelService.
				addCommerceAccountOrganizationRel(
					id,
					AccountOrganizationUtil.getOrganizationId(
						_organizationLocalService, accountOrganization,
						contextCompany.getCompanyId()),
					_serviceContextHelper.getServiceContext());

		return _accountOrganizationDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountOrganizationRel.getPrimaryKey(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<AccountOrganization> _toAccountOrganizations(
			List<CommerceAccountOrganizationRel>
				commerceAccountOrganizationRels)
		throws Exception {

		List<AccountOrganization> accountOrganizations = new ArrayList<>();

		for (CommerceAccountOrganizationRel commerceAccountOrganizationRel :
				commerceAccountOrganizationRels) {

			accountOrganizations.add(
				_accountOrganizationDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						commerceAccountOrganizationRel.getPrimaryKey(),
						contextAcceptLanguage.getPreferredLocale())));
		}

		return accountOrganizations;
	}

	@Reference
	private AccountOrganizationDTOConverter _accountOrganizationDTOConverter;

	@Reference
	private CommerceAccountOrganizationRelService
		_commerceAccountOrganizationRelService;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}