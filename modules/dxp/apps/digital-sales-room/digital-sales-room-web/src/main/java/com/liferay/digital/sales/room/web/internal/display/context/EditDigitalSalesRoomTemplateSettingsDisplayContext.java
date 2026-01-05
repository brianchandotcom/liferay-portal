/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.web.internal.display.context;

import com.liferay.digital.sales.room.web.internal.constants.DigitalSalesRoomScreenNavigationEntryConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Stefano Motta
 */
public class EditDigitalSalesRoomTemplateSettingsDisplayContext {

	public EditDigitalSalesRoomTemplateSettingsDisplayContext(
		long digitalSalesRoomTemplateId,
		HttpServletRequest httpServletRequest) {

		_digitalSalesRoomTemplateId = digitalSalesRoomTemplateId;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);

		RenderResponse renderResponse =
			(RenderResponse)httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_PORTLET_RESPONSE);

		portletDisplay.setURLBack(getCancelURL(renderResponse));

		portletDisplay.setURLBackTitle(portletDisplay.getPortletDisplayName());

		renderResponse.setTitle(
			LanguageUtil.get(httpServletRequest, "template-settings"));
	}

	public String getCancelURL(RenderResponse renderResponse) {
		return PortletURLBuilder.createRenderURL(
			renderResponse
		).setParameter(
			"screenNavigationCategoryKey",
			DigitalSalesRoomScreenNavigationEntryConstants.CATEGORY_KEY_TEMPLATE
		).buildString();
	}

	public long getDigitalSalesRoomTemplateId() {
		return _digitalSalesRoomTemplateId;
	}

	private final long _digitalSalesRoomTemplateId;

}