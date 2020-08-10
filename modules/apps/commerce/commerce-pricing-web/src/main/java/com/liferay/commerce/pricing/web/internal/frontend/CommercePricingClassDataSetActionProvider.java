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

import com.liferay.commerce.frontend.ClayMenuActionItem;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetAction;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetActionProvider;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;
import com.liferay.commerce.pricing.web.internal.model.PricingClass;
import com.liferay.commerce.pricing.web.servlet.taglib.ui.CommercePricingClassScreenNavigationConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
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
	property = "commerce.data.provider.key=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICING_CLASSES,
	service = ClayDataSetActionProvider.class
)
public class CommercePricingClassDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayDataSetActions = new ArrayList<>();

		PricingClass pricingClass = (PricingClass)model;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (_commercePricingClassModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				pricingClass.getPricingClassId(), ActionKeys.UPDATE)) {

			PortletURL editURL = _getPricingClassEditURL(
				pricingClass.getPricingClassId(), httpServletRequest);

			ClayDataSetAction editClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK, editURL.toString(), StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, Constants.EDIT),
				StringPool.BLANK, false, false);

			clayDataSetActions.add(editClayDataSetAction);
		}

		if (_commercePricingClassModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				pricingClass.getPricingClassId(), ActionKeys.PERMISSIONS)) {

			try {
				PortletURL permissionsURL = _getManageCatalogPermissionsURL(
					pricingClass, httpServletRequest);

				ClayDataSetAction permissionsClayDataSetAction =
					new ClayDataSetAction(
						StringPool.BLANK, permissionsURL.toString(),
						StringPool.BLANK,
						LanguageUtil.get(httpServletRequest, "permissions"),
						StringPool.BLANK, false, false);

				permissionsClayDataSetAction.setTarget(
					ClayMenuActionItem.
						CLAY_MENU_ACTION_ITEM_TARGET_MODAL_PERMISSIONS);

				clayDataSetActions.add(permissionsClayDataSetAction);
			}
			catch (Exception e) {
				throw new PortalException(e);
			}
		}

		if (_commercePricingClassModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				pricingClass.getPricingClassId(), ActionKeys.DELETE)) {

			ClayDataSetAction deleteClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK,
				_getPricingClassDeleteURL(pricingClass.getPricingClassId()),
				StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, Constants.DELETE),
				StringPool.BLANK, false, false);

			deleteClayDataSetAction.setTarget("async");

			deleteClayDataSetAction.setMethod("delete");

			clayDataSetActions.add(deleteClayDataSetAction);
		}

		return clayDataSetActions;
	}

	private PortletURL _getManageCatalogPermissionsURL(
			PricingClass pricingClass, HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest,
			"com_liferay_portlet_configuration_web_portlet_" +
				"PortletConfigurationPortlet",
			ActionRequest.RENDER_PHASE);

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter("mvcPath", "/edit_permissions.jsp");
		portletURL.setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			redirect);
		portletURL.setParameter(
			"modelResource", CommercePricingClass.class.getName());
		portletURL.setParameter(
			"modelResourceDescription", pricingClass.getTitle());
		portletURL.setParameter(
			"resourcePrimKey",
			String.valueOf(pricingClass.getPricingClassId()));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
			throw new PortalException(wse);
		}

		return portletURL;
	}

	private String _getPricingClassDeleteURL(long pricingClassId) {
		return "/o/headless-commerce-admin-catalog/v1.0/product-groups/" +
			pricingClassId;
	}

	private PortletURL _getPricingClassEditURL(
		long pricingClassId, HttpServletRequest httpServletRequest) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest,
			CommercePricingPortletKeys.COMMERCE_PRICING_CLASSES,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommercePricingClass");
		portletURL.setParameter(
			"commercePricingClassId", String.valueOf(pricingClassId));
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CommercePricingClassScreenNavigationConstants.CATEGORY_KEY_DETAILS);

		return portletURL;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.pricing.model.CommercePricingClass)"
	)
	private ModelResourcePermission<CommercePricingClass>
		_commercePricingClassModelResourcePermission;

	@Reference
	private Portal _portal;

}