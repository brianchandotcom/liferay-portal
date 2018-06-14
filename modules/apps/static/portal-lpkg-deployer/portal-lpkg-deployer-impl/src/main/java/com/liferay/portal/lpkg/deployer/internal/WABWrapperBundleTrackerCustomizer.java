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

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Matthew Tambara
 */
public class WABWrapperBundleTrackerCustomizer
	implements BundleTrackerCustomizer<Bundle> {

	public WABWrapperBundleTrackerCustomizer(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public Bundle addingBundle(Bundle bundle, BundleEvent event) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (Boolean.valueOf(headers.get("Wrapper-Bundle"))) {
			return bundle;
		}

		return null;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent, Bundle object) {

		if ((bundle.getState() == Bundle.RESOLVED) &&
			(bundleEvent.getType() == BundleEvent.RESOLVED)) {

			try {
				Bundle wabBundle = _getWABBundle(bundle);

				if (wabBundle != null) {
					wabBundle.start();
				}
			}
			catch (Exception e) {
				_log.error("Unable to refresh " + bundle, e);
			}
		}
	}

	@Override
	public void removedBundle(Bundle bundle, BundleEvent event, Bundle object) {
		if (bundle.getState() == Bundle.UNINSTALLED) {
			try {
				Bundle wabBundle = _getWABBundle(bundle);

				if (wabBundle != null) {
					wabBundle.uninstall();
				}
			}
			catch (Exception e) {
			}
		}
	}

	private Bundle _getWABBundle(Bundle bundle) throws MalformedURLException {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		URL lpkgURL = new URL(headers.get("Liferay-WAB-LPKG-URL"));

		String wabLocation = WABWrapperUtil.generateWABLocation(
			lpkgURL, bundle.getVersion(),
			headers.get("Liferay-WAB-Context-Name"));

		return _bundleContext.getBundle(wabLocation);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WABWrapperBundleTrackerCustomizer.class);

	private final BundleContext _bundleContext;

}