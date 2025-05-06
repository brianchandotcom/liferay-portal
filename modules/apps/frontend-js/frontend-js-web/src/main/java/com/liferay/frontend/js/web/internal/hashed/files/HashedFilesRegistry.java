/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files;

import com.liferay.frontend.js.web.internal.hashed.files.osgi.util.tracker.HashedFilesServiceTrackerCustomizer;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = HashedFilesRegistry.class)
public class HashedFilesRegistry {

	public void forEachHashedFile(BiConsumer<String, String> biConsumer) {
		_loadHashedFiles();

		for (Map.Entry<String, String> entry : _hashedFiles.entrySet()) {
			biConsumer.accept(entry.getKey(), entry.getValue());
		}
	}

	public String getHashedFile(String virtualFile) {
		_loadHashedFiles();

		return _hashedFiles.get(virtualFile);
	}

	public void putAll(Map<String, String> hashedFiles) {
		_hashedFiles.putAll(hashedFiles);
	}

	public void removeAll(Map<String, String> hashedFiles) {
		for (String key : hashedFiles.keySet()) {
			_hashedFiles.remove(key);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTracker != null) {
			_serviceTracker.close();

			_serviceTracker = null;
		}

		_bundleContext = null;

		_hashedFiles.clear();
	}

	private synchronized void _loadHashedFiles() {
		if (_serviceTracker != null) {
			return;
		}

		if (_bundleContext == null) {
			throw new IllegalStateException("Bundle context is null");
		}

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, ServletContext.class,
			new HashedFilesServiceTrackerCustomizer(_bundleContext, this));

		_serviceTracker.open();
	}

	private BundleContext _bundleContext;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private final Map<String, String> _hashedFiles = new ConcurrentHashMap<>();

	@Reference
	private Portal _portal;

	private ServiceTracker<ServletContext, Map<String, String>> _serviceTracker;

}