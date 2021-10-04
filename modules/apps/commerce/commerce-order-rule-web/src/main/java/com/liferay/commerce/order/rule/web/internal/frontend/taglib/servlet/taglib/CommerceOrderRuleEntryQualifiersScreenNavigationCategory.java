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

package com.liferay.commerce.order.rule.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryRelService;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryService;
import com.liferay.commerce.order.rule.web.internal.display.context.CommerceOrderRuleEntryQualifiersDisplayContext;
import com.liferay.commerce.order.rule.web.internal.frontend.taglib.servlet.taglib.constants.CommerceOrderRuleEntryScreenNavigationConstants;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceOrderRuleEntryQualifiersScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommerceOrderRuleEntry> {

	@Override
	public String getCategoryKey() {
		return CommerceOrderRuleEntryScreenNavigationConstants.
			CATEGORY_KEY_QUALIFIERS;
	}

	@Override
	public String getEntryKey() {
		return CommerceOrderRuleEntryScreenNavigationConstants.
			CATEGORY_KEY_QUALIFIERS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "eligibility");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceOrderRuleEntryScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_ORDER_RULE_ENTRY_GENERAL;
	}

	@Override
	public boolean isVisible(
		User user, CommerceOrderRuleEntry commerceOrderRuleEntry) {

		if (commerceOrderRuleEntry == null) {
			return false;
		}

		boolean hasPermission = false;

		try {
			hasPermission =
				_commerceOrderRuleEntryModelResourcePermission.contains(
					PermissionThreadLocal.getPermissionChecker(),
					commerceOrderRuleEntry.getCommerceOrderRuleEntryId(),
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

		CommerceOrderRuleEntryQualifiersDisplayContext
			commerceOrderRuleEntryQualifiersDisplayContext =
				new CommerceOrderRuleEntryQualifiersDisplayContext(
					httpServletRequest,
					_commerceOrderRuleEntryModelResourcePermission,
					_commerceOrderRuleEntryService,
					_commerceOrderRuleEntryRelService, _portal);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceOrderRuleEntryQualifiersDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/commerce_order_rule_entry/qualifiers.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderRuleEntryQualifiersScreenNavigationCategory.class);

	@Reference(
		target = "(model.class.name=com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry)"
	)
	private ModelResourcePermission<CommerceOrderRuleEntry>
		_commerceOrderRuleEntryModelResourcePermission;

	@Reference
	private CommerceOrderRuleEntryRelService _commerceOrderRuleEntryRelService;

	@Reference
	private CommerceOrderRuleEntryService _commerceOrderRuleEntryService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

}