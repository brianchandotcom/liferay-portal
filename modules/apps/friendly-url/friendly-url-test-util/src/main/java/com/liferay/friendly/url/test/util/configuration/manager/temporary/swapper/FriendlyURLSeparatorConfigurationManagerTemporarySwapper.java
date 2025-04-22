/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.test.util.configuration.manager.temporary.swapper;

import com.liferay.friendly.url.configuration.manager.FriendlyURLSeparatorConfigurationManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Jürgen Kappler
 */
public class FriendlyURLSeparatorConfigurationManagerTemporarySwapper
	implements AutoCloseable {

	public FriendlyURLSeparatorConfigurationManagerTemporarySwapper(
			long companyId, String json)
		throws PortalException {

		_companyId = companyId;

		FriendlyURLSeparatorConfigurationManager
			friendlyURLSeparatorConfigurationManager =
				_serviceTracker.getService();

		_jsonObject =
			friendlyURLSeparatorConfigurationManager.
				getFriendlyURLSeparatorsJSONObject(companyId);

		friendlyURLSeparatorConfigurationManager.
			updateFriendlyURLSeparatorCompanyConfiguration(companyId, json);
	}

	@Override
	public void close() throws Exception {
		FriendlyURLSeparatorConfigurationManager
			friendlyURLSeparatorConfigurationManager =
				_serviceTracker.getService();

		friendlyURLSeparatorConfigurationManager.
			updateFriendlyURLSeparatorCompanyConfiguration(
				_companyId, _jsonObject.toString());
	}

	private static final BundleContext _bundleContext;
	private static final ServiceTracker
		<FriendlyURLSeparatorConfigurationManager,
		 FriendlyURLSeparatorConfigurationManager> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			FriendlyURLSeparatorConfigurationManagerTemporarySwapper.class);

		_bundleContext = bundle.getBundleContext();

		ServiceTracker
			<FriendlyURLSeparatorConfigurationManager,
			 FriendlyURLSeparatorConfigurationManager> serviceTracker =
				new ServiceTracker<>(
					bundle.getBundleContext(),
					FriendlyURLSeparatorConfigurationManager.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

	private final long _companyId;
	private final JSONObject _jsonObject;

}