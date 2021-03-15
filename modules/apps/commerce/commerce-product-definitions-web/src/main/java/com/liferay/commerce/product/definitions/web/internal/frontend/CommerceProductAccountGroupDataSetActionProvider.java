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

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.commerce.product.definitions.web.internal.model.AccountGroup;
import com.liferay.commerce.product.definitions.web.internal.security.permission.resource.CommerceCatalogPermission;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_ACCOUNT_GROUPS,
	service = ClayDataSetActionProvider.class
)
public class CommerceProductAccountGroupDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		AccountGroup accountGroup = (AccountGroup)model;

		CommerceAccountGroupRel commerceAccountGroupRel =
			_commerceAccountGroupRelService.getCommerceAccountGroupRel(
				accountGroup.getCommerceAccountGroupRelId());

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			commerceAccountGroupRel.getClassPK());

		return DropdownItemListBuilder.add(
			() -> CommerceCatalogPermission.contains(
				PermissionThreadLocal.getPermissionChecker(), cpDefinition,
				ActionKeys.UPDATE),
			dropdownItem -> {
				PortletURL deleteURL = _getAccountGroupDeleteURL(
					commerceAccountGroupRel, httpServletRequest);

				dropdownItem.setHref(deleteURL.toString());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private PortletURL _getAccountGroupDeleteURL(
		CommerceAccountGroupRel commerceAccountGroupRel,
		HttpServletRequest httpServletRequest) {

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, CPPortletKeys.CP_DEFINITIONS,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/cp_definitions/edit_cp_definition"
		).setRedirect(
			redirect
		).setParameter(
			Constants.CMD, "deleteAccountGroup"
		).setParameter(
			"commerceAccountGroupRelId",
			String.valueOf(
				commerceAccountGroupRel.getCommerceAccountGroupRelId())
		).setParameter(
			"cpDefinitionId",
			String.valueOf(commerceAccountGroupRel.getClassPK())
		).build();

		return portletURL;
	}

	@Reference
	private CommerceAccountGroupRelService _commerceAccountGroupRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private Portal _portal;

}