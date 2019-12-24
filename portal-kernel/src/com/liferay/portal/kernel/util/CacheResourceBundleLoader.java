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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Carlos Sierra Andrés
 */
public class CacheResourceBundleLoader implements ResourceBundleLoader {

	public static void notifyResourceBundleModification() {
		_modifiedCount.incrementAndGet();
	}

	public CacheResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = resourceBundleLoader;
	}

	@Override
	public ResourceBundle loadResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = null;

		long modifiedCount = _modifiedCount.get();

		ResourceBundleCacheEntry resourceBundleCacheEntry =
			_resourceBundleCache.get(locale);

		if ((resourceBundleCacheEntry != null) &&
			(resourceBundleCacheEntry.getModifiedCount() >= modifiedCount)) {

			resourceBundle = resourceBundleCacheEntry.getResourceBundle();
		}

		if (resourceBundle == _nullResourceBundle) {
			return null;
		}

		if (resourceBundle == null) {
			try {
				resourceBundle = _resourceBundleLoader.loadResourceBundle(
					locale);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}

			if (resourceBundle == null) {
				resourceBundleCacheEntry = new ResourceBundleCacheEntry(
					_nullResourceBundle, modifiedCount);
			}
			else {
				resourceBundleCacheEntry = new ResourceBundleCacheEntry(
					resourceBundle, modifiedCount);
			}

			_resourceBundleCache.put(locale, resourceBundleCacheEntry);
		}

		return resourceBundle;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CacheResourceBundleLoader.class);

	private static final AtomicLong _modifiedCount = new AtomicLong(0);

	private static final ResourceBundle _nullResourceBundle =
		new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				throw new UnsupportedOperationException();
			}

			@Override
			protected Object handleGetObject(String key) {
				throw new UnsupportedOperationException();
			}

		};

	private final Map<Locale, ResourceBundleCacheEntry> _resourceBundleCache =
		new ConcurrentHashMap<>();
	private final ResourceBundleLoader _resourceBundleLoader;

	private class ResourceBundleCacheEntry {

		public ResourceBundleCacheEntry(
			ResourceBundle resourceBundle, long modifiedCount) {

			_resourceBundle = resourceBundle;
			_modifiedCount = modifiedCount;
		}

		public long getModifiedCount() {
			return _modifiedCount;
		}

		public ResourceBundle getResourceBundle() {
			return _resourceBundle;
		}

		private final long _modifiedCount;
		private final ResourceBundle _resourceBundle;

	}

}