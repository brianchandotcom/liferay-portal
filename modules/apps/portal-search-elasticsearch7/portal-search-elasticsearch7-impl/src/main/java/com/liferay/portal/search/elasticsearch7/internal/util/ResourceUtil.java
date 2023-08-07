/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.util;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Michael C. Han
 */
public class ResourceUtil {

	public static String getResourceAsString(
		Class<?> clazz, String resourceName) {

		try (InputStream inputStream = clazz.getResourceAsStream(
				resourceName)) {

			return StringUtil.read(inputStream);
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to load resource: " + resourceName, exception);
		}
	}

	public static List<String> getResourcesAsStrings(
		BundleContext bundleContext, String directory) {

		List<String> resources = new ArrayList<>();

		Bundle bundle = bundleContext.getBundle();

		Enumeration<URL> enumeration = bundle.findEntries(
			directory, "*.json", true);

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				try (InputStream inputStream = url.openStream()) {
					resources.add(StringUtil.read(inputStream));
				}
				catch (Exception exception) {
					throw new RuntimeException(
						"Unable to load resource: " + url, exception);
				}
			}
		}

		return resources;
	}

}