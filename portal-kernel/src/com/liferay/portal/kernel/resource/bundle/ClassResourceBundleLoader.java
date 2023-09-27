/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.resource.bundle;

import com.liferay.portal.kernel.language.UTF8Control;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Adolfo Pérez
 */
public class ClassResourceBundleLoader implements ResourceBundleLoader {

	public ClassResourceBundleLoader(String baseName, Class<?> clazz) {
		this(baseName, clazz.getClassLoader());
	}

	public ClassResourceBundleLoader(String baseName, ClassLoader classLoader) {
		_baseName = baseName;
		_classLoader = classLoader;
	}

	@Override
	public ResourceBundle loadResourceBundle(Locale locale) {
		return ResourceBundle.getBundle(
			_baseName, locale, _classLoader, UTF8Control.INSTANCE);
	}

	private final String _baseName;
	private final ClassLoader _classLoader;

}