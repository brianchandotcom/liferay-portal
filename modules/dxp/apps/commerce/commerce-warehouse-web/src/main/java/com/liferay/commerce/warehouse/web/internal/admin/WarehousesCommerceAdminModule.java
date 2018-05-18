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

package com.liferay.commerce.warehouse.web.internal.admin;

import com.liferay.commerce.admin.CommerceAdminModule;
import com.liferay.commerce.configuration.CommerceShippingGroupServiceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceWarehouseLocalService;
import com.liferay.commerce.service.CommerceWarehouseService;
import com.liferay.commerce.warehouse.web.internal.display.context.CommerceWarehousesDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
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
	property = "commerce.admin.module.key=" + WarehousesCommerceAdminModule.KEY
)
public class WarehousesCommerceAdminModule implements CommerceAdminModule {

	public static final String KEY = "warehouses";

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "warehouses");
	}

	@Override
	public PortletURL getSearchURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		CommerceWarehousesDisplayContext commerceWarehousesDisplayContext =
			setCommerceWarehousesDisplayContext(httpServletRequest);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("commerceAdminModuleKey", KEY);
		portletURL.setParameter(
			"commerceCountryId",
			String.valueOf(
				commerceWarehousesDisplayContext.getCommerceCountryId()));

		return portletURL;
	}

	@Override
	public boolean isVisible(long groupId) throws PortalException {
		CommerceShippingGroupServiceConfiguration
			commerceShippingGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					CommerceShippingGroupServiceConfiguration.class,
					new GroupServiceSettingsLocator(
						groupId, CommerceConstants.SHIPPING_SERVICE_NAME));

		String commerceShippingOriginLocatorKey =
			commerceShippingGroupServiceConfiguration.
				commerceShippingOriginLocatorKey();

		if (commerceShippingOriginLocatorKey.equals("address")) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		setCommerceWarehousesDisplayContext(httpServletRequest);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/view.jsp");
	}

	protected CommerceWarehousesDisplayContext
		setCommerceWarehousesDisplayContext(
			HttpServletRequest httpServletRequest) {

		CommerceWarehousesDisplayContext commerceWarehousesDisplayContext =
			(CommerceWarehousesDisplayContext)httpServletRequest.getAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT);

		if (commerceWarehousesDisplayContext == null) {
			commerceWarehousesDisplayContext =
				new CommerceWarehousesDisplayContext(
					_commerceCountryService, _commerceWarehouseService,
					httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceWarehousesDisplayContext);
		}

		return commerceWarehousesDisplayContext;
	}

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceWarehouseLocalService _commerceWarehouseLocalService;

	@Reference
	private CommerceWarehouseService _commerceWarehouseService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.warehouse.web)"
	)
	private ServletContext _servletContext;

}