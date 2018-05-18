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

package com.liferay.commerce.payment.engine.money.order.internal;

import com.liferay.commerce.exception.CommercePaymentEngineException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommercePaymentEngine;
import com.liferay.commerce.model.CommercePaymentEngineResult;
import com.liferay.commerce.payment.engine.money.order.internal.configuration.MoneyOrderCommercePaymentEngineGroupServiceConfiguration;
import com.liferay.commerce.payment.engine.money.order.internal.constants.MoneyOrderCommercePaymentEngineConstants;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = "commerce.payment.engine.key=" + MoneyOrderCommercePaymentEngine.KEY,
	service = CommercePaymentEngine.class
)
public class MoneyOrderCommercePaymentEngine implements CommercePaymentEngine {

	public static final String KEY = "money-order";

	@Override
	public CommercePaymentEngineResult cancelPayment(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws CommercePaymentEngineException {

		return new CommercePaymentEngineResult(null);
	}

	@Override
	public CommercePaymentEngineResult completePayment(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws CommercePaymentEngineException {

		return new CommercePaymentEngineResult(null);
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "money-order-description");
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, KEY);
	}

	@Override
	public void renderConfiguration(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		MoneyOrderCommercePaymentEngineGroupServiceConfiguration
			moneyOrderCommercePaymentEngineGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					MoneyOrderCommercePaymentEngineGroupServiceConfiguration.
						class,
					new ParameterMapSettingsLocator(
						renderRequest.getParameterMap(),
						new GroupServiceSettingsLocator(
							themeDisplay.getScopeGroupId(),
							MoneyOrderCommercePaymentEngineConstants.
								SERVICE_NAME)));

		renderRequest.setAttribute(
			MoneyOrderCommercePaymentEngineGroupServiceConfiguration.class.
				getName(),
			moneyOrderCommercePaymentEngineGroupServiceConfiguration);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/configuration.jsp");
	}

	@Override
	public CommercePaymentEngineResult.StartPayment startPayment(
			CommerceOrder commerceOrder, String cancelURL, String returnURL,
			ServiceContext serviceContext)
		throws CommercePaymentEngineException {

		return new CommercePaymentEngineResult.StartPayment(null, null);
	}

	@Override
	public void updateConfiguration(
			Map<String, String> parameterMap, ServiceContext serviceContext)
		throws Exception {

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				serviceContext.getScopeGroupId(),
				MoneyOrderCommercePaymentEngineConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			modifiableSettings.setValue(entry.getKey(), entry.getValue());
		}

		modifiableSettings.store();
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.payment.engine.money.order)"
	)
	private ServletContext _servletContext;

	@Reference
	private SettingsFactory _settingsFactory;

}