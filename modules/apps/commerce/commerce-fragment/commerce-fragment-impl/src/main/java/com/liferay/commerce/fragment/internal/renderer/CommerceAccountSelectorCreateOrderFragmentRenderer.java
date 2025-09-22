/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.fragment.internal.renderer;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.configuration.CommerceOrderCheckoutConfiguration;
import com.liferay.commerce.configuration.CommerceOrderFieldsConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.taglib.react.servlet.taglib.ComponentTag;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PageContextFactoryUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Francesco Acciaro
 * @author Michele Vigilante
 */
@Component(service = FragmentRenderer.class)
public class CommerceAccountSelectorCreateOrderFragmentRenderer
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
					"account_selector_cta/dependencies/configuration.json"));

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
		return "button";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "account-selector-create-order-button");
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

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		if (commerceContext == null) {
			_printPortletMessageInfo(
				httpServletRequest, httpServletResponse,
				"this-site-does-not-have-a-channel");

			return;
		}

		try {
			FragmentEntryLink fragmentEntryLink =
				fragmentRendererContext.getFragmentEntryLink();

			ComponentTag componentTag = new ComponentTag();

			componentTag.setModule("{CreateOrder} from commerce-frontend-js");

			componentTag.setPageContext(
				PageContextFactoryUtil.create(
					httpServletRequest, httpServletResponse));
			componentTag.setServletContext(_servletContext);

			CommerceCurrency commerceCurrency =
				commerceContext.getCommerceCurrency();
			_accountEntry = commerceContext.getAccountEntry();

			Map<String, Object> props = HashMapBuilder.<String, Object>put(
				"addCommerceOrderURL",
				_commerceOrderHttpHelper.getCommerceCartBaseURL(
					httpServletRequest)
			).put(
				"commerceChannelId", commerceContext.getCommerceChannelId()
			).put(
				"currencyCode", commerceCurrency.getCode()
			).put(
				"hasAddCommerceOrderPermission",
				_hasAddCommerceOrderPermission()
			).put(
				"label",
				_language.get(
					fragmentRendererContext.getLocale(),
					_getConfigurationValue(
						fragmentRendererContext, fragmentEntryLink, "label"))
			).build();

			if (_accountEntry != null) {
				props.put(
					"currentCommerceAccountId",
					_accountEntry.getAccountEntryId());
			}

			_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

			_commerceChannelGroupId =
				commerceContext.getCommerceChannelGroupId();

			componentTag.setProps(props);

			componentTag.doStartTag();

			componentTag.doEndTag();
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	private String _getConfigurationValue(
		FragmentRendererContext fragmentRendererContext,
		FragmentEntryLink fragmentEntryLink, String fieldName) {

		return GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				getConfigurationJSONObject(fragmentRendererContext),
				fragmentEntryLink.getEditableValuesJSONObject(),
				fragmentRendererContext.getLocale(), fieldName));
	}

	private boolean _hasAddCommerceOrderPermission() {
		if ((_accountEntry == null) || (_themeDisplay == null)) {
			return false;
		}

		try {
			CommerceOrderFieldsConfiguration commerceOrderFieldsConfiguration =
				_configurationProvider.getConfiguration(
					CommerceOrderFieldsConfiguration.class,
					new GroupServiceSettingsLocator(
						_commerceChannelGroupId,
						CommerceConstants.SERVICE_NAME_COMMERCE_ORDER_FIELDS));

			int commerceOrdersCount =
				(int)_commerceOrderService.getPendingCommerceOrdersCount(
					_accountEntry.getCompanyId(), _commerceChannelGroupId);

			if ((commerceOrderFieldsConfiguration.accountCartMaxAllowed() >
					0) &&
				(commerceOrdersCount >=
					commerceOrderFieldsConfiguration.accountCartMaxAllowed())) {

				return false;
			}

			CommerceOrderCheckoutConfiguration
				commerceOrderCheckoutConfiguration =
					_configurationProvider.getConfiguration(
						CommerceOrderCheckoutConfiguration.class,
						new GroupServiceSettingsLocator(
							_commerceChannelGroupId,
							CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

			if (_accountEntry.isGuestAccount() &&
				commerceOrderCheckoutConfiguration.guestCheckoutEnabled()) {

				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return _commerceOrderPortletResourcePermission.contains(
			_themeDisplay.getPermissionChecker(),
			_accountEntry.getAccountEntryGroupId(),
			CommerceOrderActionKeys.ADD_COMMERCE_ORDER);
	}

	private void _printPortletMessageInfo(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String message) {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			StringBundler sb = new StringBundler(3);

			sb.append("<div class=\"portlet-msg-info\">");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			sb.append(themeDisplay.translate(message));

			sb.append("</div>");

			printWriter.write(sb.toString());
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountSelectorCreateOrderFragmentRenderer.class);

	private AccountEntry _accountEntry;
	private long _commerceChannelGroupId;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference(
		target = "(resource.name=" + CommerceOrderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _commerceOrderPortletResourcePermission;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.fragment.impl)"
	)
	private ServletContext _servletContext;

	private ThemeDisplay _themeDisplay;

}