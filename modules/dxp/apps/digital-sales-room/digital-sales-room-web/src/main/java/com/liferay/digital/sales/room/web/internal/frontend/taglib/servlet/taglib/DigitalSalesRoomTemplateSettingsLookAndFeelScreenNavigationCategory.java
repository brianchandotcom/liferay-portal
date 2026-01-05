/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.digital.sales.room.web.internal.constants.DigitalSalesRoomScreenNavigationEntryConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "screen.navigation.category.order:Integer=20",
	service = ScreenNavigationCategory.class
)
public class DigitalSalesRoomTemplateSettingsLookAndFeelScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return DigitalSalesRoomScreenNavigationEntryConstants.
			CATEGORY_KEY_LOOK_AND_FEEL;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale,
			DigitalSalesRoomScreenNavigationEntryConstants.
				CATEGORY_KEY_LOOK_AND_FEEL);
	}

	@Override
	public String getScreenNavigationKey() {
		return DigitalSalesRoomScreenNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_DIGITAL_SALES_ROOM_TEMPLATE_SETTINGS;
	}

	@Reference
	private Language _language;

}