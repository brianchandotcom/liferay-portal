/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.fragment.internal.renderer;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.taglib.react.servlet.taglib.ComponentTag;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
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
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Francesco Acciaro
 * @author Michele Vigilante
 */
@Component(service = FragmentRenderer.class)
public class CreateAccountButtonFragmentRenderer implements FragmentRenderer {

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
					"create_account_button/dependencies/configuration.json"));

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
		return _language.get(locale, "create-account-button");
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

		try {
			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			if ((commerceContext == null) ||
				(commerceContext.getCommerceChannelId() <= 0)) {

				_printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"this-site-does-not-have-a-channel");

				return;
			}

			ComponentTag componentTag = new ComponentTag();

			componentTag.setModule("{CreateAccount} from commerce-frontend-js");
			componentTag.setPageContext(
				PageContextFactoryUtil.create(
					httpServletRequest, httpServletResponse));
			componentTag.setProps(
				HashMapBuilder.<String, Object>put(
					"accountEntryAllowedTypes",
					commerceContext.getAccountEntryAllowedTypes()
				).put(
					"commerceChannelId", commerceContext.getCommerceChannelId()
				).put(
					"currentAccountURL",
					PortalUtil.getPortalURL(httpServletRequest) +
						PortalUtil.getPathContext() +
							"/o/commerce-ui/set-current-account"
				).put(
					"hasAddAccountsPermission", _hasAddAccountsPermission()
				).put(
					"label",
					_language.get(
						fragmentRendererContext.getLocale(),
						_getConfigurationValue(
							"label", fragmentRendererContext))
				).build());
			componentTag.setServletContext(_servletContext);

			componentTag.doStartTag();

			componentTag.doEndTag();
		}
		catch (Exception exception) {
			throw new IOException(exception);
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

	private boolean _hasAddAccountsPermission() {
		return PortalPermissionUtil.contains(
			PermissionThreadLocal.getPermissionChecker(),
			AccountActionKeys.ADD_ACCOUNT_ENTRY);
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
		CreateAccountButtonFragmentRenderer.class);

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

}