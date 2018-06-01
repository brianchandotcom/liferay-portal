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

package com.liferay.commerce.data.integration.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.AccountIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.CommerceOrganizationIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.UserIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.AccountForm;
import com.liferay.commerce.data.integration.apio.internal.security.permission.AccountPermissionChecker;
import com.liferay.commerce.data.integration.apio.internal.util.AccountHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class AccountCollectionResource
	implements NestedCollectionResource<Organization, Long, AccountIdentifier, Long, CommerceOrganizationIdentifier> {

	@Override
	public NestedCollectionRoutes<Organization, Long, Long> collectionRoutes(
			NestedCollectionRoutes.Builder<Organization, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).addCreator(
			this::_addAccount,
			_accountPermissionChecker.forAdding()::apply,
			 AccountForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "accounts";
	}

	@Override
	public ItemRoutes<Organization, Long> itemRoutes(
		ItemRoutes.Builder<Organization, Long> builder) {

		return builder.addGetter(
			_organizationService::getOrganization
		).addRemover(
			idempotent(_organizationService::deleteOrganization),
			_accountPermissionChecker.forDeleting()::apply
		).addUpdater(
			this::_updateAccount, Company.class,
			_accountPermissionChecker.forUpdating()::apply,
			AccountForm::buildForm
		).build();
	}

	@Override
	public Representor<Organization> representor(
		Representor.Builder<Organization, Long> builder) {

		return builder.types(
			"Account"
		).identifier(
			Organization::getOrganizationId
		).addBidirectionalModel(
			"organization", "accounts", CommerceOrganizationIdentifier.class,
			Organization::getParentOrganizationId
		).addRelatedCollection(
			"members", UserIdentifier.class
		).addString(
			"name", Organization::getName
		).build();
	}

	private Organization _addAccount(
			Long organizationId, AccountForm accountCreateForm)
		throws Exception {

		Organization organization = _accountHelper.createAccount(
			accountCreateForm.getName(), organizationId);

		_accountHelper.addMembers(accountCreateForm.getUserIds(), organization);

		return organization;
	}

	private PageItems<Organization> _getPageItems(
		Pagination pagination, Long organizationId, Company company) {

		List<Organization> organizations =
			_organizationService.getOrganizations(
				company.getCompanyId(), organizationId, pagination.getStartPosition(),
				pagination.getEndPosition());

		int count = _organizationService.getOrganizationsCount(
			company.getCompanyId(), organizationId);

		return new PageItems<>(organizations, count);
	}

	private Long _getSiteId(Organization organization) {
		return Try.success(
			organization.getGroupId()
		).map(
			_groupLocalService::getGroup
		).filter(
			Group::isSite
		).map(
			Group::getGroupId
		).orElse(
			null
		);
	}

	public static Long _getParentOrganizationId(Organization organization) {
		long parentOrganizationId = organization.getParentOrganizationId();

		if (parentOrganizationId <= 0) {
			return null;
		}

		return parentOrganizationId;
	}

	private Organization _updateAccount(
			Long accountId, AccountForm accountCreateForm, Company company)
		throws PortalException {

		Organization organization = _accountHelper.updateAccount(
			accountId, accountCreateForm.getName());

		_accountHelper.addMembers(accountCreateForm.getUserIds(), organization);

		return organization;
	}

	@Reference
	private AccountHelper _accountHelper;

	@Reference
	private AccountPermissionChecker _accountPermissionChecker;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationService _organizationService;

}