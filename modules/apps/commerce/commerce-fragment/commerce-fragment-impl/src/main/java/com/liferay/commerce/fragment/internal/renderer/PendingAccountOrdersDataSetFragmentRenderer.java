/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.fragment.internal.renderer;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletURL;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = FragmentRenderer.class)
public class PendingAccountOrdersDataSetFragmentRenderer
	implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "COMMERCE_ACCOUNT_SELECTOR_FRAGMENTS";
	}

	@Override
	public JSONObject getConfigurationJSONObject(
		FragmentRendererContext fragmentRendererContext) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.read(
					getClass(),
					"account_orders_data_set/dependencies/configuration.json"));

			return _fragmentEntryConfigurationParser.translateConfiguration(
				jsonObject, resourceBundle);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}
	}

	@Override
	public String getIcon() {
		return "catalog";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "account-orders-data-set");
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		return FeatureFlagManagerUtil.isEnabled("LPD-58472");
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				themeDisplay.getScopeGroupId());

		if (commerceChannel == null) {
			return;
		}

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/fragment/renderer/account_orders_data_set/page.jsp");

			httpServletRequest.setAttribute(
				"liferay-commerce:account-orders-data-set:additionalProps",
				HashMapBuilder.<String, Object>put(
					"setCurrentOrderURL",
					() -> _getEditOrderURL(httpServletRequest)
				).build());
			httpServletRequest.setAttribute(
				"liferay-commerce:account-orders-data-set:apiURL",
				StringBundler.concat(
					"/o/headless-commerce-delivery-cart/v1.0/channels/",
					commerceChannel.getCommerceChannelId(), "/carts"));
			httpServletRequest.setAttribute(
				"liferay-commerce:account-orders-data-set:displayStyle",
				_getConfigurationValue(
					"displayStyle", fragmentRendererContext));
			httpServletRequest.setAttribute(
				"liferay-commerce:account-orders-data-set:" +
					"fdsActionDropdownItems",
				Arrays.asList(
					new FDSActionDropdownItem(
						StringPool.BLANK, "view", "view",
						_language.get(httpServletRequest, "view"), null, null,
						"link")));
			httpServletRequest.setAttribute(
				"liferay-commerce:account-orders-data-set:namespace",
				StringUtil.randomId() + StringPool.UNDERLINE);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new RuntimeException(exception);
		}
	}

	private String _getConfigurationValue(
		String fieldName, FragmentRendererContext fragmentRendererContext) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		return GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				getConfigurationJSONObject(fragmentRendererContext),
				fragmentEntryLink.getEditableValuesJSONObject(),
				fragmentRendererContext.getLocale(), fieldName));
	}

	private String _getEditOrderURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		long plid = _portal.getPlidFromPortletId(
			_portal.getScopeGroupId(httpServletRequest),
			CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);

		if ((plid > 0) || FeatureFlagManagerUtil.isEnabled("LPD-20379")) {
			return PortletURLBuilder.create(
				_getPortletURL(
					httpServletRequest,
					CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT)
			).setActionName(
				"/commerce_open_order_content/edit_commerce_order"
			).setCMD(
				"setCurrent"
			).setParameter(
				"commerceOrderId", "{id}"
			).buildString();
		}

		return StringPool.BLANK;
	}

	private PortletURL _getPortletURL(
			HttpServletRequest httpServletRequest, String portletId)
		throws PortalException {

		long plid = _portal.getPlidFromPortletId(
			_portal.getScopeGroupId(httpServletRequest), portletId);

		if (plid > 0) {
			return _portletURLFactory.create(
				httpServletRequest, portletId, plid,
				PortletRequest.ACTION_PHASE);
		}

		return _portletURLFactory.create(
			httpServletRequest, portletId, PortletRequest.ACTION_PHASE);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PendingAccountOrdersDataSetFragmentRenderer.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.fragment.impl)"
	)
	private ServletContext _servletContext;

}