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

package headless.account.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.commerce.headless.account.apio.identifier.AccountIdentifier;
import com.liferay.commerce.headless.user.apio.identifier.UserIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.site.apio.identifier.WebSiteIdentifier;
import headless.account.apio.internal.form.AccountForm;
import headless.account.apio.internal.security.AccountPermissionChecker;
import headless.account.apio.internal.util.AccountHelper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class AccountCollectionResource
	implements CollectionResource<Organization, Long, AccountIdentifier> {

	@Override
	public CollectionRoutes<Organization> collectionRoutes(
		CollectionRoutes.Builder<Organization> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).addCreator(
			this::_addAccount, Company.class,
			_accountPermissionChecker::forAdding,
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
			_accountPermissionChecker.forDeleting()
		).addUpdater(
			this::_updateAccount, Company.class,
			_accountPermissionChecker.forUpdating(),
			AccountForm::buildForm
		).build();
	}

	@Override
	public Representor<Organization, Long> representor(
		Representor.Builder<Organization, Long> builder) {

		return builder.types(
			"Account"
		).identifier(
			Organization::getOrganizationId
		).addLinkedModel(
			"website", WebSiteIdentifier.class, this::_getSiteId
		).addRelatedCollection(
			"members", UserIdentifier.class
		).addString(
			"name", Organization::getName
		).build();
	}

	private Organization _addAccount(
			AccountForm accountCreateForm, Company company)
			throws Exception {

		Organization organization = _accountHelper.createOrganization(
			accountCreateForm.getName());

		_accountHelper.addMembers(accountCreateForm.getUserIds(), organization);

		return organization;
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

	private Organization _updateAccount(
			Long organizationId, AccountForm accountCreateForm, Company company)
		throws PortalException {

		Organization organization = _accountHelper.updateOrganization(
			organizationId, accountCreateForm.getName());

		_accountHelper.addMembers(accountCreateForm.getUserIds(), organization);

		return organization;
	}

	@Reference
	private AccountHelper _accountHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private AccountPermissionChecker _accountPermissionChecker;

}