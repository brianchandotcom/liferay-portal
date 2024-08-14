/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (Exception exception) {
				_log.error("Unable to refresh " + bundle, exception);
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
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
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