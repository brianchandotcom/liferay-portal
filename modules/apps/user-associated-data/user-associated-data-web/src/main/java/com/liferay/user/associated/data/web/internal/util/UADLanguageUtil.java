/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
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
			return applicationKey;
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