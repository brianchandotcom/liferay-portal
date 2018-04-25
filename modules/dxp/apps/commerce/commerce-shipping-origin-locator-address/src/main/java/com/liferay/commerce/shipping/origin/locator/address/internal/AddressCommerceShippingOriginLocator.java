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

package com.liferay.commerce.shipping.origin.locator.address.internal;

import com.liferay.commerce.constants.CommerceWarehouseConstants;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShippingOriginLocator;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceWarehouseLocalService;
import com.liferay.commerce.shipping.origin.locator.address.internal.configuration.AddressCommerceShippingOriginLocatorGroupServiceConfiguration;
import com.liferay.commerce.shipping.origin.locator.address.internal.constants.AddressCommerceShippingOriginLocatorConstants;
import com.liferay.commerce.util.SuffixParameterMapSettingsLocator;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
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
	property = "commerce.shipping.origin.locator.key=" + AddressCommerceShippingOriginLocator.KEY,
	service = CommerceShippingOriginLocator.class
)
public class AddressCommerceShippingOriginLocator
	implements CommerceShippingOriginLocator {

	public static final String KEY = "address";

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(
			resourceBundle, "use-single-address-description");
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "use-single-address");
	}

	@Override
	public Map<CommerceAddress, List<CommerceOrderItem>> getOriginAddresses(
			CommerceOrder commerceOrder)
		throws Exception {

		CommerceAddress commerceAddress =
			_commerceAddressLocalService.createCommerceAddress(0);

		CommerceWarehouse commerceWarehouse = _getDefaultCommerceWarehouse();

		commerceAddress.setName(commerceWarehouse.getName());
		commerceAddress.setStreet1(commerceWarehouse.getStreet1());
		commerceAddress.setStreet2(commerceWarehouse.getStreet2());
		commerceAddress.setStreet3(commerceWarehouse.getStreet3());
		commerceAddress.setCity(commerceWarehouse.getCity());
		commerceAddress.setZip(commerceWarehouse.getZip());
		commerceAddress.setCommerceRegionId(
			commerceWarehouse.getCommerceRegionId());
		commerceAddress.setCommerceCountryId(
			commerceWarehouse.getCommerceCountryId());
		commerceAddress.setLatitude(commerceWarehouse.getLatitude());
		commerceAddress.setLongitude(commerceWarehouse.getLongitude());

		return Collections.singletonMap(
			commerceAddress, commerceOrder.getCommerceOrderItems());
	}

	@Override
	public void renderConfiguration(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AddressCommerceShippingOriginLocatorGroupServiceConfiguration
			addressCommerceShippingOriginLocatorGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					AddressCommerceShippingOriginLocatorGroupServiceConfiguration.class,
					new SuffixParameterMapSettingsLocator(
						renderRequest.getParameterMap(), KEY + "Origin--",
						StringPool.DOUBLE_DASH,
						new GroupServiceSettingsLocator(
							themeDisplay.getScopeGroupId(),
							AddressCommerceShippingOriginLocatorConstants.
								SERVICE_NAME)));

		renderRequest.setAttribute(
			AddressCommerceShippingOriginLocatorGroupServiceConfiguration.class.
				getName(),
			addressCommerceShippingOriginLocatorGroupServiceConfiguration);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/configuration.jsp");
	}

	@Override
	public void updateConfiguration(
			Map<String, String> parameterMap, ServiceContext serviceContext)
		throws Exception {

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				serviceContext.getScopeGroupId(),
				AddressCommerceShippingOriginLocatorConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			modifiableSettings.setValue(entry.getKey(), entry.getValue());
		}

		modifiableSettings.store();
	}

	private CommerceWarehouse _getDefaultCommerceWarehouse()
		throws PortalException {

		return _commerceWarehouseLocalService.getCommerceWarehouse(
			CommerceWarehouseConstants.DEFAULT_ID);
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommerceWarehouseLocalService _commerceWarehouseLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.shipping.origin.locator.address)"
	)
	private ServletContext _servletContext;

	@Reference
	private SettingsFactory _settingsFactory;

}