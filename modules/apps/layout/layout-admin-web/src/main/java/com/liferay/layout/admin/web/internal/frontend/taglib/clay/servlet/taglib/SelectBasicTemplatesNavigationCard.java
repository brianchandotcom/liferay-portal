/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.NavigationCard;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletURL;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class SelectBasicTemplatesNavigationCard implements NavigationCard {

	public SelectBasicTemplatesNavigationCard(
		String type, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_type = type;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_deprecated =
			_type.equals(LayoutConstants.TYPE_FULL_PAGE_APPLICATION) ||
			_type.equals(LayoutConstants.TYPE_PANEL) ||
			(_type.equals(LayoutConstants.TYPE_PORTLET) &&
			 FeatureFlagManagerUtil.isEnabled(
				 themeDisplay.getCompanyId(), "LPD-76864"));
	}

	@Override
	public String getCssClass() {
		return "add-layout-action-option";
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		return HashMapBuilder.put(
			"data-add-layout-url", _getAddLayoutURL()
		).put(
			"role", "button"
		).put(
			"tabIndex", "0"
		).build();
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getTitle() {
		return LanguageUtil.get(_httpServletRequest, "layout.types." + _type);
	}

	public boolean isDeprecated() {
		return _deprecated;
	}

	@Override
	public Boolean isSmall() {
		return true;
	}

	private String _getAddLayoutURL() {
		long selPlid = ParamUtil.getLong(_httpServletRequest, "selPlid");

		PortletURL addLayoutURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/layout_admin/add_layout"
		).setBackURL(
			ParamUtil.getString(_httpServletRequest, "redirect")
		).setParameter(
			"privateLayout",
			ParamUtil.getBoolean(_httpServletRequest, "privateLayout")
		).setParameter(
			"selPlid", selPlid
		).setParameter(
			"type", _type
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildPortletURL();

		if (selPlid != LayoutConstants.DEFAULT_PLID) {
			Layout layout = LayoutLocalServiceUtil.fetchLayout(selPlid);

			if ((layout != null) && layout.isTypeEmpty()) {
				addLayoutURL.setParameter(
					"emptyLayout",
					String.valueOf(
						ParamUtil.getBoolean(
							_httpServletRequest, "emptyLayout")));
				addLayoutURL.setParameter(
					"externalReferenceCode", layout.getExternalReferenceCode());
			}
		}

		return addLayoutURL.toString();
	}

	private final boolean _deprecated;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final String _type;

}