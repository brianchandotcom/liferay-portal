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