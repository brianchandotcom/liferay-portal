/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.csp;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Iván Zaera Avellón
 */
public class CSPNonceProviderUtil {

	/**
	 * @see CSPNonceProvider#getCSPNonce(HttpServletRequest)
	 */
	public static String getCSPNonce(HttpServletRequest httpServletRequest) {
		CSPNonceProvider cspNonceProvider = getCSPNonceProvider();

		if (cspNonceProvider == null) {
			_log.error("Using empty CSP nonce because provider is not present");

			return StringPool.BLANK;
		}

		return cspNonceProvider.getCSPNonce(httpServletRequest);
	}

	public static CSPNonceProvider getCSPNonceProvider() {
		return _serviceTracker.getService();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSPNonceProviderUtil.class);

	private static final ServiceTracker<CSPNonceProvider, CSPNonceProvider>
		_serviceTracker =
			new ServiceTracker<CSPNonceProvider, CSPNonceProvider>(
				SystemBundleUtil.getBundleContext(), CSPNonceProvider.class,
				null) {

				{
					open();
				}
			};

}