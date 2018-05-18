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

package com.liferay.commerce.shipping.web.internal.servlet.taglib.ui;

import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceAddressRestrictionService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.web.internal.display.context.CommerceShippingMethodRestrictionsDisplayContext;
import com.liferay.commerce.shipping.web.servlet.taglib.ui.CommerceShippingScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.language.LanguageUtil;
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
	property = "screen.navigation.entry.order:Integer=100",
	service = ScreenNavigationEntry.class
)
public class CommerceShippingMethodCountryRestrictionScreenNavigationEntry
	implements ScreenNavigationEntry<CommerceShippingMethod> {

	@Override
	public String getCategoryKey() {
		return CommerceShippingScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_SHIPPING_METHOD_DETAILS;
	}

	@Override
	public String getEntryKey() {
		return CommerceShippingScreenNavigationConstants.
			ENTRY_KEY_COMMERCE_SHIPPING_METHOD_RESTRICTIONS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "restrictions");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceShippingScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_SHIPPING_METHOD;
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

		CommerceShippingMethodRestrictionsDisplayContext
			commerceShippingMethodRestrictionsDisplayContext =
				new CommerceShippingMethodRestrictionsDisplayContext(
					_commerceAddressRestrictionService,
					_commerceShippingMethodService, _itemSelector,
					renderRequest, renderResponse);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceShippingMethodRestrictionsDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/shipping_method/restrictions.jsp");
	}

	@Reference
	private CommerceAddressRestrictionService
		_commerceAddressRestrictionService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.shipping.web)"
	)
	private ServletContext _servletContext;

}