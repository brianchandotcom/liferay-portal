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

package com.liferay.commerce.subscription.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.product.util.CPSubscriptionTypeJSPContributorRegistry;
import com.liferay.commerce.product.util.CPSubscriptionTypeRegistry;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.subscription.web.internal.display.context.CommerceSubscriptionEntryDisplayContext;
import com.liferay.commerce.subscription.web.internal.servlet.taglib.ui.constants.CommerceSubscriptionEntryScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
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
		"screen.navigation.category.order:Integer=40",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceSubscriptionEntryPaymentsScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommerceSubscriptionEntry> {

	@Override
	public String getCategoryKey() {
		return CommerceSubscriptionEntryScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_SUBSCRIPTION_ENTRY_PAYMENTS;
	}

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, getCategoryKey());
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceSubscriptionEntryScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_SUBSCRIPTION_ENTRY;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceSubscriptionEntryDisplayContext
			commerceSubscriptionEntryDisplayContext =
				new CommerceSubscriptionEntryDisplayContext(
					_commercePaymentMethodGroupRelLocalService,
					_commerceSubscriptionEntryLocalService,
					_commerceOrderItemLocalService,
					_cpSubscriptionTypeJSPContributorRegistry,
					_cpSubscriptionTypeRegistry, httpServletRequest,
					_portletResourcePermission);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceSubscriptionEntryDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/subscription_entry/payments.jsp");
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Reference
	private CPSubscriptionTypeJSPContributorRegistry
		_cpSubscriptionTypeJSPContributorRegistry;

	@Reference
	private CPSubscriptionTypeRegistry _cpSubscriptionTypeRegistry;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_SUBSCRIPTION + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.subscription.web)"
	)
	private ServletContext _servletContext;

}