/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.subscription.web.internal.constants.CommerceSubscriptionEntryScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "screen.navigation.category.order:Integer=20",
	service = ScreenNavigationCategory.class
)
public class CommerceSubscriptionEntryGeneralScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceSubscriptionEntryScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_SUBSCRIPTION_ENTRY_GENERAL;
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(locale, getCategoryKey());
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceSubscriptionEntryScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_SUBSCRIPTION_ENTRY;
	}

	@Reference
	protected Language language;

}