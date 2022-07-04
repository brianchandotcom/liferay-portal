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

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextThreadLocal;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = InfoItemPermissionProvider.class)
public class CPDefinitionSpecificationOptionValueInfoItemPermissionProvider
	implements InfoItemPermissionProvider
		<CPDefinitionSpecificationOptionValue> {

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue,
			String actionId)
		throws InfoItemPermissionException {

		CommerceContext commerceContext = CommerceContextThreadLocal.get();

		if (commerceContext == null) {
			return false;
		}

		try {
			return _commerceProductViewPermission.contains(
				permissionChecker,
				CommerceUtil.getCommerceAccountId(commerceContext),
				commerceContext.getCommerceChannelGroupId(),
				cpDefinitionSpecificationOptionValue.getCPDefinitionId());
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(
				cpDefinitionSpecificationOptionValue.
					getCPDefinitionSpecificationOptionValueId(),
				portalException);
		}
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			InfoItemReference infoItemReference, String actionId)
		throws InfoItemPermissionException {

		CommerceContext commerceContext = CommerceContextThreadLocal.get();

		if (commerceContext == null) {
			return false;
		}

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return false;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		try {
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue =
					_definitionSpecificationOptionValueLocalService.
						getCPDefinitionSpecificationOptionValue(
							classPKInfoItemIdentifier.getClassPK());

			return _commerceProductViewPermission.contains(
				permissionChecker,
				CommerceUtil.getCommerceAccountId(commerceContext),
				commerceContext.getCommerceChannelGroupId(),
				cpDefinitionSpecificationOptionValue.getCPDefinitionId());
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(
				classPKInfoItemIdentifier.getClassPK(), portalException);
		}
	}

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_definitionSpecificationOptionValueLocalService;

}