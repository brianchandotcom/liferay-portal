/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.hashed.files.HashedFilesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import jakarta.servlet.ServletContext;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = HashedFilesRegistry.class)
public class HashedFilesRegistryImpl implements HashedFilesRegistry {

	public void forEach(BiConsumer<String, String> biConsumer) {
		_openServiceTracker();

		for (Map.Entry<String, String> entry : _map.entrySet()) {
			biConsumer.accept(entry.getKey(), entry.getValue());
		}
	}

	public String get(String unhashedFileURI) {
		_openServiceTracker();

		return _map.get(unhashedFileURI);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_map.clear();

		if (_serviceTracker != null) {
			_serviceTracker.close();

			_serviceTracker = null;
		}
	}

	private ServiceTrackerCustomizer<ServletContext, Map<String, String>>
		_createServiceTrackerCustomizer() {

		return new ServiceTrackerCustomizer<>() {

			@Override
			public Map<String, String> addingService(
				ServiceReference<ServletContext> serviceReference) {

				ServletContext servletContext = _bundleContext.getService(
					serviceReference);

				try {
					Set<String> hashedResourcePaths;

					URL url = servletContext.getResource(
						"/WEB-INF/liferay-look-and-feel.xml");

					if (url != null) {
						hashedResourcePaths = _getHashedResourcePaths(
							servletContext, "/css/");

						hashedResourcePaths.addAll(
							_getHashedResourcePaths(servletContext, "/js/"));
					}
					else {
						Set<String> completeHashedResourcePaths =
							_getHashedResourcePaths(
								servletContext, "/META-INF/resources/");

						hashedResourcePaths = new HashSet<>();

						for (String completeHashedResourcePath :
								completeHashedResourcePaths) {

							hashedResourcePaths.add(
								completeHashedResourcePath.substring(19));
						}
					}

					Map<String, String> map = new HashMap<>();

					String contextPath = servletContext.getContextPath();

					for (String hashedResourcePath : hashedResourcePaths) {
						map.put(
							contextPath +
								HashedFilesUtil.removeHash(hashedResourcePath),
							contextPath + hashedResourcePath);
					}

					_map.putAll(map);

					return map;
				}
				catch (MalformedURLException malformedURLException) {
					_log.error(malformedURLException);

					return Collections.emptyMap();
				}
				finally {
					_bundleContext.ungetService(serviceReference);
				}
			}

			@Override
			public void modifiedService(
				ServiceReference<ServletContext> serviceReference,
				Map<String, String> map) {

				removedService(serviceReference, map);

				addingService(serviceReference);
			}

			@Override
			public void removedService(
				ServiceReference<ServletContext> serviceReference,
				Map<String, String> map) {

				for (String key : map.keySet()) {
					_map.remove(key);
				}
			}

		};
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
			else if (HashedFilesUtil.containsHash(resourcePath)) {
				hashedResourcePaths.add(resourcePath);
			}
		}

		return hashedResourcePaths;
	}

	private void _openServiceTracker() {
		if (_serviceTracker != null) {
			return;
		}

		synchronized (this) {
			if (_serviceTracker != null) {
				return;
			}

			_serviceTracker = new ServiceTracker<>(
				_bundleContext, ServletContext.class,
				_createServiceTrackerCustomizer());

			_serviceTracker.open();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HashedFilesRegistryImpl.class);

	private BundleContext _bundleContext;
	private final Map<String, String> _map = new ConcurrentHashMap<>();
	private volatile ServiceTracker<ServletContext, Map<String, String>>
		_serviceTracker;

}