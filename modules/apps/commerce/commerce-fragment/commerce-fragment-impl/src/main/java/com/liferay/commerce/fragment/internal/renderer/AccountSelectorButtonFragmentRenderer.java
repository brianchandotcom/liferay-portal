/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.fragment.internal.renderer;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.taglib.react.servlet.taglib.ComponentTag;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Account;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.taglib.servlet.PageContextFactoryUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = FragmentRenderer.class)
public class AccountSelectorButtonFragmentRenderer implements FragmentRenderer {

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
					"account_selector_button/dependencies/configuration.json"));

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
		return _language.get(locale, "account-selector-button");
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
			CommerceChannel commerceChannel =
				_commerceChannelLocalService.fetchCommerceChannel(
					commerceContext.getCommerceChannelId());

			if (commerceChannel == null) {
				_printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"this-site-does-not-have-a-channel");

				return;
			}

			FragmentEntryLink fragmentEntryLink =
				fragmentRendererContext.getFragmentEntryLink();

			ComponentTag componentTag = new ComponentTag();

			componentTag.setModule(
				"{AccountSelectorButton} from commerce-fragment-impl");

			componentTag.setPageContext(
				PageContextFactoryUtil.create(
					httpServletRequest, httpServletResponse));
			componentTag.setServletContext(_servletContext);

			AccountEntry accountEntry = commerceContext.getAccountEntry();
			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			JSONObject configurationJSONObject = getConfigurationJSONObject(
				fragmentRendererContext);

			componentTag.setProps(
				HashMapBuilder.<String, Object>put(
					"account",
					() -> {
						if (accountEntry == null) {
							return null;
						}

						return _accountDTOConverter.toDTO(
							new DefaultDTOConverterContext(
								_dtoConverterRegistry,
								accountEntry.getAccountEntryId(),
								fragmentRendererContext.getLocale(), null,
								_portal.getUser(httpServletRequest)));
					}
				).put(
					"label",
					_language.get(
						fragmentRendererContext.getLocale(),
						GetterUtil.getString(
							_fragmentEntryConfigurationParser.getFieldValue(
								configurationJSONObject,
								fragmentEntryLink.getEditableValuesJSONObject(),
								fragmentRendererContext.getLocale(), "label")))
				).put(
					"order",
					() -> {
						if (commerceOrder == null) {
							return null;
						}

						return _cartDTOConverter.toDTO(
							new DefaultDTOConverterContext(
								_dtoConverterRegistry,
								commerceOrder.getCommerceOrderId(),
								fragmentRendererContext.getLocale(), null,
								_portal.getUser(httpServletRequest)));
					}
				).put(
					"showAccountImage",
					_isConfigurationValue(
						configurationJSONObject, fragmentEntryLink,
						fragmentRendererContext.getLocale(), "showAccountImage")
				).put(
					"showAccountInfo",
					_isConfigurationValue(
						configurationJSONObject, fragmentEntryLink,
						fragmentRendererContext.getLocale(), "showAccountInfo")
				).put(
					"showButtonIcon",
					_isConfigurationValue(
						configurationJSONObject, fragmentEntryLink,
						fragmentRendererContext.getLocale(), "showButtonIcon")
				).put(
					"showOrderInfo",
					_isConfigurationValue(
						configurationJSONObject, fragmentEntryLink,
						fragmentRendererContext.getLocale(), "showOrderInfo")
				).build());

			componentTag.doStartTag();

			componentTag.doEndTag();
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new RuntimeException(exception);
		}
	}

	private boolean _isConfigurationValue(
		JSONObject configurationJSONObject, FragmentEntryLink fragmentEntryLink,
		Locale locale, String name) {

		return GetterUtil.getBoolean(
			_fragmentEntryConfigurationParser.getFieldValue(
				configurationJSONObject,
				fragmentEntryLink.getEditableValuesJSONObject(), locale, name));
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
		AccountSelectorButtonFragmentRenderer.class);

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.AccountDTOConverter)"
	)
	private DTOConverter<AccountEntry, Account> _accountDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.converter.CartDTOConverter)"
	)
	private DTOConverter<CommerceOrder, Cart> _cartDTOConverter;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.fragment.impl)"
	)
	private ServletContext _servletContext;

}