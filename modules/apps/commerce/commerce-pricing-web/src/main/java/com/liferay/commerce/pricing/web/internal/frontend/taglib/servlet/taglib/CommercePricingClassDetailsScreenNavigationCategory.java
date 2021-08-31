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

package com.liferay.commerce.pricing.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.commerce.pricing.web.internal.display.context.CommercePricingClassDisplayContext;
import com.liferay.commerce.pricing.web.internal.servlet.taglib.ui.constants.CommercePricingClassScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=10",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommercePricingClassDetailsScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommercePricingClass> {

	@Override
	public String getCategoryKey() {
		return CommercePricingClassScreenNavigationConstants.
			CATEGORY_KEY_DETAILS;
	}

	@Override
	public String getEntryKey() {
		return CommercePricingClassScreenNavigationConstants.
			CATEGORY_KEY_DETAILS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommercePricingClassScreenNavigationConstants.CATEGORY_KEY_DETAILS);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommercePricingClassScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_PRICING_CLASS_GENERAL;
	}

	@Override
	public boolean isVisible(
		User user, CommercePricingClass commercePricingClass) {

		if (commercePricingClass == null) {
			return false;
		}

		boolean hasPermission = false;

		try {
			hasPermission =
				_commercePricingClassModelResourcePermission.contains(
					PermissionThreadLocal.getPermissionChecker(),
					commercePricingClass.getCommercePricingClassId(),
					ActionKeys.UPDATE);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return hasPermission;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommercePricingClassDisplayContext commercePricingClassDisplayContext =
			new CommercePricingClassDisplayContext(
				httpServletRequest,
				_commercePricingClassModelResourcePermission,
				_commercePricingClassService, _portal);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commercePricingClassDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/commerce_pricing_class/details.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePricingClassDetailsScreenNavigationCategory.class);

	@Reference(
		target = "(model.class.name=com.liferay.commerce.pricing.model.CommercePricingClass)"
	)
	private ModelResourcePermission<CommercePricingClass>
		_commercePricingClassModelResourcePermission;

	@Reference
	private CommercePricingClassService _commercePricingClassService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

}