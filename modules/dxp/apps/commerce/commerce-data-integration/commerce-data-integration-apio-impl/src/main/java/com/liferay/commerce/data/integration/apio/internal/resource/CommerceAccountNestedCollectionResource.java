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
import com.liferay.commerce.data.integration.apio.identifiers.CommerceAccountIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.CommerceAccountCreatorForm;
import com.liferay.commerce.data.integration.apio.internal.util.CommerceAccountHelper;
import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class CommerceAccountNestedCollectionResource
	implements NestedCollectionResource<Organization, Long,
		CommerceAccountIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<Organization, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Organization, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).addCreator(
			this::_addAccount,
			_hasPermission.forAddingIn(CommerceAccountIdentifier.class),
			CommerceAccountCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "commerce-account";
	}

	@Override
	public ItemRoutes<Organization, Long> itemRoutes(
		ItemRoutes.Builder<Organization, Long> builder) {

		return builder.addGetter(
			_commerceOrganizationService::getOrganization
		).addRemover(
			idempotent(_commerceOrganizationService::deleteOrganization),
			_hasPermission::forDeleting
		).addUpdater(
			this::_updateAccount, Company.class, _hasPermission::forUpdating,
			CommerceAccountCreatorForm::buildForm
		).build();
	}

	@Override
	public Representor<Organization> representor(
		Representor.Builder<Organization, Long> builder) {

		return builder.types(
			"CommerceAccount"
		).identifier(
			Organization::getOrganizationId
		).addBidirectionalModel(
			"website", "commerceAccounts", WebSiteIdentifier.class,
			this::_getSiteId
		).addLinkedModel(
			"website", WebSiteIdentifier.class, this::_getSiteId
		).addNumberList(
			"members", this::_getUserIds
		).addString(
			"name", Organization::getName
		).build();
	}

	private Organization _addAccount(
			Long webSiteId,
			CommerceAccountCreatorForm commerceAccountCreatorForm)
		throws Exception {

		Group group = _groupLocalService.getGroup(webSiteId);

		Long parentOrganizationId = group.getClassPK();

		return _commerceAccountHelper.createAccount(
			commerceAccountCreatorForm.getName(), parentOrganizationId,
			commerceAccountCreatorForm.getCommerceUserIds());
	}

	private PageItems<Organization> _getPageItems(
			Pagination pagination, Long webSiteId, Company company)
		throws PortalException {

		long userId = PrincipalThreadLocal.getUserId();

		BaseModelSearchResult<Organization> result =
			_commerceOrganizationService.searchOrganizationsByGroup(
				webSiteId, userId, CommerceOrganizationConstants.TYPE_ACCOUNT,
				StringPool.BLANK, pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		return new PageItems<>(result.getBaseModels(), result.getLength());
	}

	private Long _getSiteId(Organization organization) {
		return Try.success(
			organization.getGroupId()
		).map(
			_groupLocalService::getGroup
		).map(
			Group::getGroupId
		).orElse(
			null
		);
	}

	private List<Number> _getUserIds(Organization organization) {
		List<Number> userIds = new ArrayList<>();

		try {
			long[] ids = _userService.getOrganizationUserIds(
				organization.getOrganizationId());

			for (long id : ids) {
				userIds.add(id);
			}
		}
		catch (PortalException pe) {
			_log.error("Unable to retrieve users", pe);
		}

		return userIds;
	}

	private Organization _updateAccount(
			Long accountId,
			CommerceAccountCreatorForm commerceAccountCreatorForm,
			Company company)
		throws PortalException {

		return _commerceAccountHelper.updateAccount(
			accountId, commerceAccountCreatorForm.getName(),
			commerceAccountCreatorForm.getCommerceUserIds());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountNestedCollectionResource.class);

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Organization)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private UserService _userService;

}