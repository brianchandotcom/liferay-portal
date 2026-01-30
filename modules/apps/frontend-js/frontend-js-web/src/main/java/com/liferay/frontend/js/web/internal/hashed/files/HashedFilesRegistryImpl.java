/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.util.FrontendJsWebUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.frontend.hashed.files.CachingStrategy;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = HashedFilesRegistry.class)
public class HashedFilesRegistryImpl implements HashedFilesRegistry {

	public void forEachHashedFileURI(BiConsumer<String, String> biConsumer) {
		_lazyActivate();

		for (Map.Entry<String, DataBag> entry : _dataBags.entrySet()) {
			DataBag dataBag = entry.getValue();

			Map<String, String> hashedFileURIs = dataBag._hashedFileURIs;

			for (Map.Entry<String, String> entry2 : hashedFileURIs.entrySet()) {
				biConsumer.accept(entry2.getKey(), entry2.getValue());
			}
		}
	}

	public void forEachServletContextHash(
		BiConsumer<String, String> biConsumer) {

		_lazyActivate();

		for (Map.Entry<String, DataBag> entry : _dataBags.entrySet()) {
			String servletContextPath = entry.getKey();

			List<String> pathParts = Arrays.asList(
				servletContextPath.split(StringPool.SLASH));

			DataBag dataBag = entry.getValue();

			biConsumer.accept(
				pathParts.get(_resourcePathIndex - 1),
				dataBag._servletContextHash);
		}
	}

	@Override
	public CachingStrategy getCachingStrategy(
		HttpServletRequest httpServletRequest) {

		FrontendCachingConfiguration frontendCachingConfiguration =
			FrontendJsWebUtil.getFrontendCachingConfiguration(
				_portal.getCompanyId(httpServletRequest),
				_configurationProvider);

		return CachingStrategy.fromValue(
			frontendCachingConfiguration.cachingStrategy());
	}

	public String getHashedFileURI(String unhashedFileURI) {
		_lazyActivate();

		List<String> pathParts = Arrays.asList(
			unhashedFileURI.split(StringPool.SLASH));

		DataBag dataBag = _dataBags.get(
			StringUtil.merge(
				pathParts.subList(0, _resourcePathIndex), StringPool.SLASH));

		if (dataBag == null) {
			return null;
		}

		Map<String, String> hashedFileURIs = dataBag._hashedFileURIs;

		return hashedFileURIs.get(unhashedFileURI);
	}

	@Override
	public URL getResource(String path) {
		_lazyActivate();

		if (!HashedFilesUtil.containsHash(path)) {
			String hashedFileURI = getHashedFileURI(path);

			if (hashedFileURI != null) {
				path = hashedFileURI;
			}
		}

		List<String> pathParts = Arrays.asList(path.split(StringPool.SLASH));

		DataBag dataBag = _dataBags.get(
			StringUtil.merge(
				pathParts.subList(0, _resourcePathIndex), StringPool.SLASH));

		if (dataBag == null) {
			return null;
		}

		String subpath = StringUtil.merge(
			pathParts.subList(_resourcePathIndex, pathParts.size()),
			StringPool.SLASH);

		subpath = StringPool.SLASH + subpath;

		try {
			ServletContext servletContext = dataBag._servletContext;

			return servletContext.getResource(subpath);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	@Override
	public String getServletContextHash(String servletContextName) {
		_lazyActivate();

		DataBag dataBag = _dataBags.get(
			StringBundler.concat(
				_portalContextPath, Portal.PATH_MODULE, StringPool.SLASH,
				servletContextName));

		if (dataBag == null) {
			return null;
		}

		return dataBag._servletContextHash;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_portalContextPath = _portal.getPathContext();

		String proxyPath = _portal.getPathProxy();

		_portalContextPath = _portalContextPath.substring(proxyPath.length());

		_resourcePathIndex = _portalContextPath.isEmpty() ? 3 : 4;
	}

	@Deactivate
	protected void deactivate() {
		_dataBags.clear();

		if (_serviceTracker != null) {
			_serviceTracker.close();

			_serviceTracker = null;
		}
	}

	protected static class DataBag {

		protected DataBag(
			Map<String, String> hashedFileURIs, ServletContext servletContext,
			String servletContextHash) {

			_hashedFileURIs = hashedFileURIs;
			_servletContext = servletContext;
			_servletContextHash = servletContextHash;
		}

		private final Map<String, String> _hashedFileURIs;
		private final ServletContext _servletContext;
		private final String _servletContextHash;

	}

	private ServiceTrackerCustomizer<ServletContext, Void>
		_createServiceTrackerCustomizer() {

		return new ServiceTrackerCustomizer<>() {

			@Override
			public Void addingService(
				ServiceReference<ServletContext> serviceReference) {

				ServletContext servletContext = _bundleContext.getService(
					serviceReference);

				try {
					Map<String, String> hashedFileURIs = _getHashedFileURIs(
						servletContext);

					if (hashedFileURIs.isEmpty()) {
						return null;
					}

					_dataBags.put(
						servletContext.getContextPath(),
						new DataBag(
							hashedFileURIs, servletContext,
							_getServletContextHash(hashedFileURIs)));
				}
				finally {
					_bundleContext.ungetService(serviceReference);
				}

				return null;
			}

			@Override
			public void modifiedService(
				ServiceReference<ServletContext> serviceReference, Void v) {

				addingService(serviceReference);
			}

			@Override
			public void removedService(
				ServiceReference<ServletContext> serviceReference, Void v) {

				ServletContext servletContext = _bundleContext.getService(
					serviceReference);

				try {
					_dataBags.remove(servletContext.getContextPath());
				}
				finally {
					_bundleContext.ungetService(serviceReference);
				}
			}

		};
	}

	private Map<String, String> _getHashedFileURIs(
		ServletContext servletContext) {

		boolean theme = false;

		String contextPath = servletContext.getContextPath();

		try {
			URL url = servletContext.getResource(
				"/WEB-INF/liferay-look-and-feel.xml");

			if (url != null) {
				theme = true;
			}
		}
		catch (MalformedURLException malformedURLException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check if context path " + contextPath +
						" contains a theme",
					malformedURLException);
			}
		}

		Set<String> hashedResourcePaths = new HashSet<>();

		if (theme) {
			hashedResourcePaths.addAll(
				_getHashedResourcePaths(servletContext, "/css/"));
			hashedResourcePaths.addAll(
				_getHashedResourcePaths(servletContext, "/js/"));
		}
		else {
			Set<String> hashedResourceFullPaths = _getHashedResourcePaths(
				servletContext, "/META-INF/resources/");

			for (String hashedResourceFullPath : hashedResourceFullPaths) {
				hashedResourcePaths.add(hashedResourceFullPath.substring(19));
			}
		}

		Map<String, String> hashedFileURIs = new HashMap<>();

		for (String hashedResourcePath : hashedResourcePaths) {
			hashedFileURIs.put(
				contextPath + HashedFilesUtil.removeHash(hashedResourcePath),
				contextPath + hashedResourcePath);
		}

		return hashedFileURIs;
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

	private String _getServletContextHash(Map<String, String> hashedFileURIs) {
		Set<String> hashesSet = new HashSet<>();

		for (String hashedFileURI : hashedFileURIs.values()) {
			hashesSet.add(HashedFilesUtil.getHash(hashedFileURI));
		}

		ArrayList<String> hashesList = new ArrayList<>(hashesSet);

		Collections.sort(hashesList);

		String hashesString = StringUtil.merge(hashesList, StringPool.PIPE);

		byte[] hash = DigesterUtil.digestRaw(DigesterUtil.MD5, hashesString);

		byte[] truncatedHash = new byte[8];

		System.arraycopy(hash, 0, truncatedHash, 0, truncatedHash.length);

		String encodedTruncatedHash = Base64.encode(truncatedHash);

		encodedTruncatedHash = StringUtil.replace(
			encodedTruncatedHash, CharPool.PLUS, CharPool.DOLLAR);
		encodedTruncatedHash = StringUtil.replace(
			encodedTruncatedHash, CharPool.SLASH, CharPool.AT);
		encodedTruncatedHash = StringUtil.removeSubstring(
			encodedTruncatedHash, StringPool.EQUAL);

		return encodedTruncatedHash;
	}

	private void _lazyActivate() {
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

	@Reference
	private ConfigurationProvider _configurationProvider;

	private final Map<String, DataBag> _dataBags = new ConcurrentHashMap<>();

	@Reference
	private Portal _portal;

	private String _portalContextPath;
	private int _resourcePathIndex;
	private volatile ServiceTracker<ServletContext, Void> _serviceTracker;

}