/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.currency.web.internal.servlet.taglib.ui;

import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.currency.util.ExchangeRateProviderRegistry;
import com.liferay.commerce.currency.util.RoundingTypeServicesTracker;
import com.liferay.commerce.currency.web.internal.display.context.CommerceCurrenciesDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=10",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceCurrencyCurrenciesScreenNavigationEntry
	implements ScreenNavigationCategory, ScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return CommerceCurrencyScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_CURRENCY_CURRENCIES;
	}

	@Override
	public String getEntryKey() {
		return CommerceCurrencyScreenNavigationConstants.
			ENTRY_KEY_COMMERCE_CURRENCY_CURRENCIES;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceCurrencyScreenNavigationConstants.
				ENTRY_KEY_COMMERCE_CURRENCY_CURRENCIES);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceCurrencyScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_CURRENCY;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		RenderResponse renderResponse =
			(RenderResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		CommerceCurrenciesDisplayContext commerceCurrenciesDisplayContext =
			new CommerceCurrenciesDisplayContext(
				_commerceCurrencyService, _commercePriceFormatter,
				_configurationProvider, _exchangeRateProviderRegistry,
				_roundingTypeServicesTracker, renderRequest, renderResponse);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceCurrenciesDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/currency/currencies.jsp");
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private ExchangeRateProviderRegistry _exchangeRateProviderRegistry;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private RoundingTypeServicesTracker _roundingTypeServicesTracker;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.currency.web)"
	)
	private ServletContext _servletContext;

}