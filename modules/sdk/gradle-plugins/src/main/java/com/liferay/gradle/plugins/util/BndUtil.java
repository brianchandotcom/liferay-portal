/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.util;

import com.liferay.gradle.plugins.extensions.BundleExtension;

import org.gradle.api.plugins.ExtensionContainer;

/**
 * @author Andrea Di Giorgi
 * @author Raymond Augé
 */
public class BndUtil {

	public static BundleExtension getBundleExtension(
		ExtensionContainer extensionContainer) {

		BundleExtension bundleExtension = extensionContainer.findByType(
			BundleExtension.class);

		if (bundleExtension == null) {
			bundleExtension = new BundleExtension();

			extensionContainer.add(
				BundleExtension.class, "bundle", bundleExtension);
		}

		return bundleExtension;
	}

}