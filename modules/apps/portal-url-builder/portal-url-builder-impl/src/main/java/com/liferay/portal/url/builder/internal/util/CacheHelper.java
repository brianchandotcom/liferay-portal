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

package com.liferay.portal.url.builder.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.url.builder.facet.CacheAwareAbsolutePortalURLBuilder.CachePolicy;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = CacheHelper.class)
public class CacheHelper {

	public void appendCacheParam(
		StringBundler sb, Bundle bundle, CachePolicy cachePolicy,
		String resourcePath) {

		if (cachePolicy == CachePolicy.NEVER) {
			appendNeverCacheParam(sb);
		}
		else if (cachePolicy == CachePolicy.UNTIL_CHANGED) {
			String mac = _digest(bundle, "META-INF/resources" + resourcePath);

			if (mac != null) {
				sb.append("mac=");
				sb.append(mac);
			}
		}
	}

	/**
	 * This is a compromise technique that may be used when a resource cannot be
	 * checked for updates.
	 *
	 * It appends a `t` parameter to the URL with the last time the server was
	 * started, which invalidates browser caches after every server restart.
	 *
	 * This is suboptimal in the sense that a server restart doesn't mean that
	 * resources have been changed, but at least, it's efficient if servers
	 * are not restarted very often, and guarantees that any upgrade of the
	 * Portal invalidates browser caches (because you cannot upgrade without
	 * restarting).
	 */
	public void appendLastRestartCacheParam(StringBundler sb) {
		sb.append("t=");
		sb.append(_lastRestartTime);
	}

	public void appendNeverCacheParam(StringBundler sb) {
		sb.append("t=");
		sb.append(System.currentTimeMillis());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (_lastRestartTime == -1) {
			_lastRestartTime = System.currentTimeMillis();
		}

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE | Bundle.STOPPING,
			_bundleBundleTrackerCustomizer);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();

		_bundleTracker = null;
	}

	private String _digest(Bundle bundle, String path) {
		ConcurrentMap<String, String> digests = _digests.get(
			bundle.getBundleId());

		String cacheKey = StringBundler.concat(
			bundle.getBundleId(), StringPool.COLON, path);

		if (digests.containsKey(cacheKey)) {
			return digests.get(cacheKey);
		}

		URL url = bundle.getResource(path);

		if (url == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to find resource ", path, " inside bundle ",
						bundle.getSymbolicName()));
			}

			digests.put(cacheKey, null);

			return null;
		}

		try (InputStream inputStream = url.openStream()) {
			String digest = DigesterUtil.digestBase64("SHA-1", inputStream);

			digests.put(cacheKey, digest);

			return digest;
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to digest resource ", path, " inside bundle ",
						bundle.getSymbolicName()),
					ioException);
			}

			digests.put(cacheKey, null);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CacheHelper.class);

	private final BundleTrackerCustomizer<Bundle>
		_bundleBundleTrackerCustomizer = new BundleTrackerCustomizer<Bundle>() {

			@Override
			public Bundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
				_digests.put(bundle.getBundleId(), new ConcurrentHashMap<>());

				return bundle;
			}

			@Override
			public void modifiedBundle(
				Bundle bundle, BundleEvent bundleEvent, Bundle bundle2) {
			}

			@Override
			public void removedBundle(
				Bundle bundle, BundleEvent bundleEvent, Bundle bundle2) {

				_digests.remove(bundle.getBundleId());
			}

		};

	private BundleTracker<Bundle> _bundleTracker;
	private final ConcurrentMap<Long, ConcurrentMap<String, String>> _digests =
		new ConcurrentHashMap<>();
	private long _lastRestartTime = -1;

}