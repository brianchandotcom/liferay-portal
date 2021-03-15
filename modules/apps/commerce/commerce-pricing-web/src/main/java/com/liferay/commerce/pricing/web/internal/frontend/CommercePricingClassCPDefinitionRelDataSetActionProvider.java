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

package com.liferay.commerce.pricing.web.internal.frontend;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelLocalService;
import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;
import com.liferay.commerce.pricing.web.internal.model.PricingClassCPDefinitionRel;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICING_CLASSES_PRODUCT_DEFINITIONS,
	service = ClayDataSetActionProvider.class
)
public class CommercePricingClassCPDefinitionRelDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		PricingClassCPDefinitionRel pricingClassCPDefinitionRel =
			(PricingClassCPDefinitionRel)model;

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel =
				_commercePricingClassCPDefinitionRelLocalService.
					getCommercePricingClassCPDefinitionRel(
						pricingClassCPDefinitionRel.
							getPricingClassCPDefinitionRelId());

		return DropdownItemListBuilder.add(
			() -> _commercePricingClassModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commercePricingClassCPDefinitionRel.getCommercePricingClassId(),
				ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getCPDefinitionEditURL(
						pricingClassCPDefinitionRel.getCPDefinitionId(),
						httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
			}
		).add(
			() -> _commercePricingClassModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commercePricingClassCPDefinitionRel.getCommercePricingClassId(),
				ActionKeys.DELETE),
			dropdownItem -> {
				dropdownItem.putData("method", "delete");
				dropdownItem.setHref(
					_getPricingClassCPDefinitionRelDeleteURL(
						commercePricingClassCPDefinitionRel.
							getCommercePricingClassCPDefinitionRelId()));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "remove"));
				dropdownItem.setTarget("async");
			}
		).build();
	}

	private PortletURL _getCPDefinitionEditURL(
			long cpDefinitionId, HttpServletRequest httpServletRequest)
		throws PortalException {

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CPDefinition.class.getName(),
				PortletProvider.Action.MANAGE)
		).setMVCRenderCommandName(
			"/cp_definitions/edit_cp_definition"
		).setParameter(
			"cpDefinitionId", cpDefinitionId
		).setParameter(
			"screenNavigationCategoryKey", "details"
		).build();
	}

	private String _getPricingClassCPDefinitionRelDeleteURL(
		long pricingClassCPDefinitionRelId) {

		return "/o/headless-commerce-admin-catalog/v1.0" +
			"/product-group-products/" + pricingClassCPDefinitionRelId;
	}

	@Reference
	private CommercePricingClassCPDefinitionRelLocalService
		_commercePricingClassCPDefinitionRelLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.pricing.model.CommercePricingClass)"
	)
	private ModelResourcePermission<CommercePricingClass>
		_commercePricingClassModelResourcePermission;

	@Reference
	private Portal _portal;

}