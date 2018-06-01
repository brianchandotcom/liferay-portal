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
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.CommerceOrganizationIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.UserIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.CommerceOrganizationForm;
import com.liferay.commerce.data.integration.apio.internal.security.permission.CommerceOrganizationPermissionChecker;
import com.liferay.commerce.data.integration.apio.internal.util.CommerceOrganizationHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class CommerceOrganizationCollectionResource
	implements CollectionResource<Organization, Long,
		CommerceOrganizationIdentifier> {

	@Override
	public CollectionRoutes<Organization, Long> collectionRoutes(
		CollectionRoutes.Builder<Organization, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).addCreator(
			this::_addCommerceOrganization, Company.class,
			_commerceOrganizationPermissionChecker::forAdding,
			CommerceOrganizationForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "commerce-organizations";
	}

	@Override
	public ItemRoutes<Organization, Long> itemRoutes(
		ItemRoutes.Builder<Organization, Long> builder) {

		return builder.addGetter(
			_organizationService::getOrganization
		).addRemover(
			idempotent(_organizationService::deleteOrganization),
			_commerceOrganizationPermissionChecker.forDeleting()::apply
		).addUpdater(
			this::_updateCommerceOrganization, Company.class,
			_commerceOrganizationPermissionChecker.forUpdating()::apply,
			CommerceOrganizationForm::buildForm
		).build();
	}

	@Override
	public Representor<Organization> representor(
		Representor.Builder<Organization, Long> builder) {

		return builder.types(
			"CommerceOrganization"
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

	private Organization _addCommerceOrganization(
			CommerceOrganizationForm commerceOrganizationCreateForm,
			Company company)
		throws Exception {

		Organization organization =
			_commerceOrganizationHelper.createOrganization(
				commerceOrganizationCreateForm.getName());

		_commerceOrganizationHelper.addMembers(
			commerceOrganizationCreateForm.getUserIds(), organization);

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

	private Organization _updateCommerceOrganization(
			Long organizationId,
			CommerceOrganizationForm commerceOrganizationForm, Company company)
		throws PortalException {

		Organization organization =
			_commerceOrganizationHelper.updateOrganization(
				organizationId, commerceOrganizationForm.getName());

		_commerceOrganizationHelper.addMembers(
			commerceOrganizationForm.getUserIds(), organization);

		return organization;
	}

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommerceOrganizationPermissionChecker
		_commerceOrganizationPermissionChecker;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationService _organizationService;

}