/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.kernel.auth.token.provider;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

/**
 * @author Rafael Praxedes
 */
public class MailAuthTokenProviderRegistryUtil {

	public static MailAuthTokenProvider getMailAuthTokenProvider(
		long companyId, String mailServerName, String protocol) {

		MailAuthTokenProvider mailAuthTokenProvider =
			_serviceTrackerMap.getService(mailServerName);

		if ((mailAuthTokenProvider == null) ||
			!mailAuthTokenProvider.isProtocolSupported(companyId, protocol)) {

			return null;
		}

		return mailAuthTokenProvider;
	}

	public static MailAuthTokenProvider getMailAuthTokenProvider(
		String mailServerName) {

		return _serviceTrackerMap.getService(mailServerName);
	}

	private static final ServiceTrackerMap<String, MailAuthTokenProvider>
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			SystemBundleUtil.getBundleContext(), MailAuthTokenProvider.class,
			"mail.server.name");

}