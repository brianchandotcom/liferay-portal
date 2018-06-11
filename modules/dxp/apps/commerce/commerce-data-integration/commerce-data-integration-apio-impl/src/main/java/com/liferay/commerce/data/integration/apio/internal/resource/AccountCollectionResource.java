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
import com.liferay.commerce.data.integration.apio.internal.form.AccountForm;
import com.liferay.commerce.data.integration.apio.internal.security.permission.AccountPermissionChecker;
import com.liferay.commerce.data.integration.apio.internal.util.AccountHelper;
import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.petra.string.StringPool;
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
public class AccountCollectionResource
	implements NestedCollectionResource<Organization, Long,
		AccountIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<Organization, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Organization, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).addCreator(
			this::_addAccount, _accountPermissionChecker.forAdding()::apply,
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
			_commerceOrganizationService::getOrganization
		).addRemover(
			idempotent(_commerceOrganizationService::deleteOrganization),
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
			"website", "accounts", WebSiteIdentifier.class, this::_getSiteId
		).addLinkedModel(
			"website", WebSiteIdentifier.class, this::_getSiteId
		).addNumberList(
			"members", this::_getUserIds
		).addString(
			"name", Organization::getName
		).build();
	}

	private Organization _addAccount(
			Long webSiteId, AccountForm accountCreateForm)
		throws Exception {

		Group group = _groupLocalService.getGroup(webSiteId);

		Long parentOrganizationId = group.getClassPK();

		return _accountHelper.createAccount(
			accountCreateForm.getName(), parentOrganizationId,
			accountCreateForm.getUserIds());
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
			_log.error("Error to retrieve users", pe);
		}

		return userIds;
	}

	private Organization _updateAccount(
			Long accountId, AccountForm accountCreateForm, Company company)
		throws PortalException {

		return _accountHelper.updateAccount(
			accountId, accountCreateForm.getName(),
			accountCreateForm.getUserIds());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountCollectionResource.class);

	@Reference
	private AccountHelper _accountHelper;

	@Reference
	private AccountPermissionChecker _accountPermissionChecker;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserService _userService;

}