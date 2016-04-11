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

package com.liferay.portal.osgi.web.wab.generator.internal.util;

import aQute.bnd.osgi.Jar;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.osgi.framework.Constants;

/**
 * @author Raymond Augé
 */
public class ManifestUtil {

	public static boolean isValidOSGiBundle(Manifest manifest) {
		if (manifest == null) {
			return false;
		}

		Attributes attributes = manifest.getMainAttributes();

		String bundleSymbolicName = GetterUtil.getString(
			attributes.getValue(Constants.BUNDLE_SYMBOLICNAME));

		return Validator.isNotNull(bundleSymbolicName);
	}

	public static boolean isValidOSGiBundle(String path) {
		try (Jar jar = new Jar(_WAR, path)) {
			String bundleSymbolicName = jar.getBsn();

			if (bundleSymbolicName != null) {
				return true;
			}
		}
		catch (Exception e) {

			// Ignore this case

		}

		return false;
	}

	private static final String _WAR = "WAR";

}