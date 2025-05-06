/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.osgi.util.tracker;

import com.liferay.frontend.js.web.internal.hashed.files.HashedFilesRegistry;
import com.liferay.petra.string.StringPool;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class HashedFilesServiceTrackerCustomizer
	implements ServiceTrackerCustomizer<ServletContext, Map<String, String>> {

	public HashedFilesServiceTrackerCustomizer(
		BundleContext bundleContext, HashedFilesRegistry hashedFilesRegistry) {

		_bundleContext = bundleContext;
		_hashedFilesRegistry = hashedFilesRegistry;
	}

	@Override
	public Map<String, String> addingService(
		ServiceReference<ServletContext> serviceReference) {

		ServletContext servletContext = _bundleContext.getService(
			serviceReference);

		try {
			String contextPath = servletContext.getContextPath();

			Set<String> hashedResourcePaths = _getHashedResourcePaths(
				servletContext, "/META-INF/resources/__liferay__/");

			Map<String, String> hashedFiles = new HashMap<>();

			for (String hashedResourcePath : hashedResourcePaths) {

				// Remove "/META-INF/resources" from path

				hashedResourcePath = hashedResourcePath.substring(19);

				String baseName = hashedResourcePath.substring(
					0, hashedResourcePath.lastIndexOf(".("));

				String extension = hashedResourcePath.substring(
					hashedResourcePath.lastIndexOf(").") + 1);

				hashedFiles.put(
					contextPath + baseName + extension,
					contextPath + hashedResourcePath);
			}

			_hashedFilesRegistry.putAll(hashedFiles);

			return hashedFiles;
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		Map<String, String> hashedFiles) {

		removedService(serviceReference, hashedFiles);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		Map<String, String> hashedFiles) {

		_hashedFilesRegistry.removeAll(hashedFiles);
	}

	private Set<String> _getHashedResourcePaths(
		ServletContext servletContext, String folderPath) {

		Set<String> resourcePaths = servletContext.getResourcePaths(folderPath);

		if (resourcePaths == null) {
			return Collections.emptySet();
		}

		Set<String> hashedResourcePaths = new HashSet<>();

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith(StringPool.SLASH)) {
				hashedResourcePaths.addAll(
					_getHashedResourcePaths(servletContext, resourcePath));
			}
			else if (resourcePath.contains(".(") &&
					 resourcePath.contains(").")) {

				hashedResourcePaths.add(resourcePath);
			}
		}

		return hashedResourcePaths;
	}

	private final BundleContext _bundleContext;
	private final HashedFilesRegistry _hashedFilesRegistry;

}