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

package com.liferay.commerce.product.internal.permission;

import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.permission.CommerceCatalogPermission;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceProductViewPermission.class
)
public class CommerceProductViewPermissionImpl
	implements CommerceProductViewPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceAccountId,
			long cpDefinitionId)
		throws PortalException {

		if (contains(permissionChecker, commerceAccountId, cpDefinitionId)) {
			return;
		}

		throw new PrincipalException.MustHavePermission(
			permissionChecker.getUserId(), CPDefinition.class.getName(),
			cpDefinitionId, ActionKeys.VIEW);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceAccountId,
			long groupId, long cpDefinitionId)
		throws PortalException {

		if (contains(
				permissionChecker, commerceAccountId, groupId,
				cpDefinitionId)) {

			return;
		}

		throw new PrincipalException.MustHavePermission(
			permissionChecker.getUserId(), CPDefinition.class.getName(),
			cpDefinitionId, ActionKeys.VIEW);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceAccountId,
			long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (_viewCatalog(
				permissionChecker, cpDefinition.getCommerceCatalog()) ||
			_accountEnabled(commerceAccountId, cpDefinition)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceAccountId,
			long groupId, long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (!_channelEnabled(groupId, cpDefinition)) {
			return false;
		}

		if (_viewCatalog(
				permissionChecker, cpDefinition.getCommerceCatalog()) ||
			_accountEnabled(commerceAccountId, cpDefinition)) {

			return true;
		}

		return false;
	}

	private boolean _accountEnabled(
			long commerceAccountId, CPDefinition cpDefinition)
		throws PortalException {

		if (!cpDefinition.isAccountGroupFilterEnabled()) {
			return true;
		}

		List<CommerceAccountGroupRel> commerceAccountGroupRels =
			_commerceAccountGroupRelService.getCommerceAccountGroupRels(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		long[] commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				commerceAccountId);

		for (CommerceAccountGroupRel commerceAccountGroupRel :
				commerceAccountGroupRels) {

			if (ArrayUtil.contains(
					commerceAccountGroupIds,
					commerceAccountGroupRel.getCommerceAccountGroupId())) {

				return true;
			}
		}

		return false;
	}

	private boolean _channelEnabled(long groupId, CPDefinition cpDefinition) {
		if (!cpDefinition.isChannelFilterEnabled()) {
			return true;
		}

		List<CommerceChannelRel> commerceChannelRels =
			_commerceChannelRelLocalService.getCommerceChannelRels(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		long commerceChannelId = _getCommerceChannelId(groupId);

		for (CommerceChannelRel commerceChannelRel : commerceChannelRels) {
			if (commerceChannelRel.getCommerceChannelId() ==
					commerceChannelId) {

				return true;
			}
		}

		return false;
	}

	private long _getCommerceChannelId(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		String className = group.getClassName();

		if (className.equals(CommerceChannel.class.getName())) {
			return group.getClassPK();
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				groupId);

		if (commerceChannel != null) {
			return commerceChannel.getCommerceChannelId();
		}

		return 0;
	}

	private boolean _viewCatalog(
			PermissionChecker permissionChecker,
			CommerceCatalog commerceCatalog)
		throws PortalException {

		return _commerceCatalogPermission.contains(
			permissionChecker, commerceCatalog, ActionKeys.VIEW);
	}

	@Reference
	private CommerceAccountGroupRelService _commerceAccountGroupRelService;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceCatalogPermission _commerceCatalogPermission;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}