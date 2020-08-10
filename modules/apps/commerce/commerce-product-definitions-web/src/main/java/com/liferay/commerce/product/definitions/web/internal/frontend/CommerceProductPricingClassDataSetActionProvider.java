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

import com.liferay.commerce.frontend.clay.data.set.ClayDataSetAction;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetActionProvider;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelService;
import com.liferay.commerce.product.definitions.web.internal.model.ProductPricingClass;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	immediate = true,
	property = "commerce.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_PRICING_CLASSES,
	service = ClayDataSetActionProvider.class
)
public class CommerceProductPricingClassDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayDataSetActions = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ProductPricingClass productPricingClass = (ProductPricingClass)model;

		if (_commercePricingClassModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				productPricingClass.getPricingClassId(), ActionKeys.UPDATE)) {

			PortletURL editURL = _getPricingClassEditURL(
				productPricingClass.getPricingClassId(), httpServletRequest);

			ClayDataSetAction editClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK, editURL.toString(), StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, Constants.EDIT),
				StringPool.BLANK, false, false);

			editClayDataSetAction.setTarget("sidePanel");

			clayDataSetActions.add(editClayDataSetAction);
		}

		if (_commercePricingClassModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				productPricingClass.getPricingClassId(), ActionKeys.DELETE)) {

			ClayDataSetAction deleteClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK,
				_getProductPricingClassDeleteURL(
					productPricingClass.getPricingClassId(),
					productPricingClass.getCpDefinitionId()),
				StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, Constants.DELETE),
				StringPool.BLANK, false, false);

			deleteClayDataSetAction.setTarget("async");

			deleteClayDataSetAction.setMethod("delete");

			clayDataSetActions.add(deleteClayDataSetAction);
		}

		return clayDataSetActions;
	}

	private PortletURL _getPricingClassEditURL(
			long pricingClassId, HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CPDefinition.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommercePricingClass");
		portletURL.setParameter(
			"commercePricingClassId", String.valueOf(pricingClassId));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
			_log.error(wse, wse);
		}

		return portletURL;
	}

	private String _getProductPricingClassDeleteURL(
			long pricingClassId, long cpDefinitionId)
		throws PortalException {

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel =
				_commercePricingClassCPDefinitionRelService.
					fetchCommercePricingClassCPDefinitionRel(
						pricingClassId, cpDefinitionId);

		return "/o/headless-commerce-admin-catalog/v1.0" +
			"/product-group-products/" +
				commercePricingClassCPDefinitionRel.
					getCommercePricingClassCPDefinitionRelId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductPricingClassDataSetActionProvider.class);

	@Reference
	private CommercePricingClassCPDefinitionRelService
		_commercePricingClassCPDefinitionRelService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.pricing.model.CommercePricingClass)"
	)
	private ModelResourcePermission<CommercePricingClass>
		_commercePricingClassModelResourcePermission;

	@Reference
	private Portal _portal;

}