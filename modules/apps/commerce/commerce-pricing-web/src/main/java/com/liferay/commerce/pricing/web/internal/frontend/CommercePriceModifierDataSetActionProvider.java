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

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierService;
import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;
import com.liferay.commerce.pricing.web.internal.model.PriceModifier;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICE_MODIFIERS,
	service = ClayDataSetActionProvider.class
)
public class CommercePriceModifierDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		PriceModifier priceModifier = (PriceModifier)model;

		CommercePriceModifier commercePriceModifier =
			_commercePriceModifierService.getCommercePriceModifier(
				priceModifier.getPriceModifierId());

		return DropdownItemListBuilder.add(
			() -> _commercePriceListModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commercePriceModifier.getCommercePriceListId(),
				ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getPriceModifierEditURL(
						commercePriceModifier, httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("sidePanel");
			}
		).add(
			() -> _commercePriceListModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commercePriceModifier.getCommercePriceListId(),
				ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getPriceModifierDeleteURL(
						commercePriceModifier, httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private PortletURL _getPriceModifierDeleteURL(
		CommercePriceModifier commercePriceModifier,
		HttpServletRequest httpServletRequest) {

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest,
				CommercePricingPortletKeys.COMMERCE_PRICE_LIST,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/commerce_price_list/edit_commerce_price_modifier"
		).setRedirect(
			redirect
		).setParameter(
			Constants.CMD, Constants.DELETE
		).setParameter(
			"commercePriceModifierId",
			String.valueOf(commercePriceModifier.getCommercePriceModifierId())
		).setParameter(
			"commercePriceListId",
			String.valueOf(commercePriceModifier.getCommercePriceListId())
		).build();

		return portletURL;
	}

	private PortletURL _getPriceModifierEditURL(
			CommercePriceModifier commercePriceModifier,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CommercePriceList.class.getName(),
				PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/commerce_price_list/edit_commerce_price_modifier"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"commercePriceModifierId",
			String.valueOf(commercePriceModifier.getCommercePriceModifierId())
		).setParameter(
			"commercePriceListId",
			String.valueOf(commercePriceModifier.getCommercePriceListId())
		).build();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException, windowStateException);
		}

		return portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceModifierDataSetActionProvider.class);

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceList)"
	)
	private ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission;

	@Reference
	private CommercePriceModifierService _commercePriceModifierService;

	@Reference
	private Portal _portal;

}