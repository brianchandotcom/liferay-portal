/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.digital.sales.room.web.internal.constants.DigitalSalesRoomScreenNavigationEntryConstants;
import com.liferay.digital.sales.room.web.internal.display.context.ViewDigitalSalesRoomTemplateListDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class DigitalSalesRoomTemplateScreenNavigationEntry
	implements ScreenNavigationEntry<Organization> {

	@Override
	public String getCategoryKey() {
		return DigitalSalesRoomScreenNavigationEntryConstants.
			CATEGORY_KEY_TEMPLATE;
	}

	@Override
	public String getEntryKey() {
		return DigitalSalesRoomScreenNavigationEntryConstants.
			CATEGORY_KEY_TEMPLATE;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale,
			DigitalSalesRoomScreenNavigationEntryConstants.
				CATEGORY_KEY_TEMPLATE);
	}

	@Override
	public String getScreenNavigationKey() {
		return DigitalSalesRoomScreenNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_DIGITAL_SALES_ROOM;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		ViewDigitalSalesRoomTemplateListDisplayContext
			viewDigitalSalesRoomTemplateListDisplayContext =
				new ViewDigitalSalesRoomTemplateListDisplayContext(
					httpServletRequest);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			viewDigitalSalesRoomTemplateListDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse, "/template/view.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _language;

}