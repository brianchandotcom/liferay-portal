/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.organization.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.organization.constants.CommerceOrganizationScreenNavigationEntryConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.users.admin.constants.UserScreenNavigationEntryConstants;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Mottas
 */
@Component(
	property = "screen.navigation.category.order:Integer=30",
	service = ScreenNavigationCategory.class
)
public class UsersAndOrganizationsCommerceOrganizationScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceOrganizationScreenNavigationEntryConstants.
			CATEGORY_KEY_COMMERCE_ORGANIZATION;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "organization-chart");
	}

	@Override
	public String getScreenNavigationKey() {
		return UserScreenNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_USERS_AND_ORGANIZATIONS;
	}

	@Reference
	private Language _language;

}