/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.user.associated.data.component.UADComponent;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Drew Brokke
 */
public class UADLanguageUtil {

	public static String getApplicationName(
		String applicationKey, Locale locale) {

		if (applicationKey.equals(UADConstants.ALL_APPLICATIONS)) {
			return LanguageUtil.get(locale, UADConstants.ALL_APPLICATIONS);
		}

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(applicationKey);

		if (resourceBundleLoader == null) {
			resourceBundleLoader =
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
		}

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return LanguageUtil.get(
			resourceBundle, "application.name." + applicationKey,
			applicationKey);
	}

	public static <T extends UADComponent> String getApplicationName(
		T uadComponent, Locale locale) {

		Bundle bundle = FrameworkUtil.getBundle(uadComponent.getClass());

		return getApplicationName(bundle.getSymbolicName(), locale);
	}

}