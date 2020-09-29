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

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.pricing.constants.CommercePricingClassActionKeys;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassDisplayContext
	extends BasePricingDisplayContext {

	public CommercePricingClassDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<CommercePricingClass>
			commercePricingClassModelResourcePermission,
		CommercePricingClassService commercePricingClassService,
		Portal portal) {

		super(httpServletRequest);

		_commercePricingClassModelResourcePermission =
			commercePricingClassModelResourcePermission;
		_commercePricingClassService = commercePricingClassService;
		_portal = portal;
	}

	public String getAddCommercePricingClassRenderURL() throws Exception {
		LiferayPortletResponse liferayPortletResponse =
			commercePricingRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_pricing_classes/add_commerce_pricing_class");
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public CommercePricingClass getCommercePricingClass()
		throws PortalException {

		long commercePricingClassId = ParamUtil.getLong(
			commercePricingRequestHelper.getRequest(),
			"commercePricingClassId");

		if (commercePricingClassId == 0) {
			return null;
		}

		return _commercePricingClassService.fetchCommercePricingClass(
			commercePricingClassId);
	}

	public long getCommercePricingClassId() throws PortalException {
		CommercePricingClass commercePricingClass = getCommercePricingClass();

		if (commercePricingClass == null) {
			return 0;
		}

		return commercePricingClass.getCommercePricingClassId();
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (hasAddPermission()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(getAddCommercePricingClassRenderURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							commercePricingRequestHelper.getRequest(),
							"add-product-group"));
					dropdownItem.setTarget("modal");
				});
		}

		return creationMenu;
	}

	public String getEditCommercePricingClassActionURL() throws Exception {
		CommercePricingClass commercePricingClass = getCommercePricingClass();

		if (commercePricingClass == null) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			commercePricingRequestHelper.getRequest(),
			CommercePricingPortletKeys.COMMERCE_PRICING_CLASSES,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/commerce_pricing_classes/edit_commerce_pricing_class");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE);
		portletURL.setParameter(
			"commercePricingClassId",
			String.valueOf(commercePricingClass.getCommercePricingClassId()));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public PortletURL getEditCommercePricingClassRenderURL() {
		PortletURL portletURL = _portal.getControlPanelPortletURL(
			commercePricingRequestHelper.getRequest(),
			CommercePricingPortletKeys.COMMERCE_PRICING_CLASSES,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_pricing_classes/edit_commerce_pricing_class");

		return portletURL;
	}

	public List<HeaderActionModel> getHeaderActionModels() throws Exception {
		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		RenderResponse renderResponse =
			commercePricingRequestHelper.getRenderResponse();

		RenderURL cancelURL = renderResponse.createRenderURL();

		HeaderActionModel cancelHeaderActionModel = new HeaderActionModel(
			null, cancelURL.toString(), null, "cancel");

		headerActionModels.add(cancelHeaderActionModel);

		if (hasPermission(ActionKeys.UPDATE)) {
			headerActionModels.add(
				new HeaderActionModel(
					"btn-primary", liferayPortletResponse.getNamespace() + "fm",
					getEditCommercePricingClassActionURL(), null, "save"));
		}

		return headerActionModels;
	}

	public boolean hasAddPermission() throws PortalException {
		PortletResourcePermission portletResourcePermission =
			_commercePricingClassModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			commercePricingRequestHelper.getPermissionChecker(), null,
			CommercePricingClassActionKeys.ADD_COMMERCE_PRICING_CLASS);
	}

	public boolean hasPermission(String actionId) throws PortalException {
		return _commercePricingClassModelResourcePermission.contains(
			commercePricingRequestHelper.getPermissionChecker(),
			getCommercePricingClassId(), actionId);
	}

	private final ModelResourcePermission<CommercePricingClass>
		_commercePricingClassModelResourcePermission;
	private final CommercePricingClassService _commercePricingClassService;
	private final Portal _portal;

}