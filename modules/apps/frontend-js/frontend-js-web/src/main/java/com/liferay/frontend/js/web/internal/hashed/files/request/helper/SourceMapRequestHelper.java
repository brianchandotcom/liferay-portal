/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.hashed.files.HashedFilesRegistry;
import com.liferay.frontend.js.web.internal.hashed.files.request.AbstractStaticFileRequestHelper;
import com.liferay.frontend.js.web.internal.hashed.files.request.StaticFileCachingInfo;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class SourceMapRequestHelper extends AbstractStaticFileRequestHelper {

	public SourceMapRequestHelper(
		ConfigurationProvider configurationProvider,
		HashedFilesRegistry hashedFilesRegistry, Portal portal,
		ServiceTrackerMap<String, ServletContext> serviceTrackerMap) {

		super(
			ContentTypes.APPLICATION_JSON, ".map", hashedFilesRegistry,
			serviceTrackerMap);

		_configurationProvider = configurationProvider;
		_portal = portal;
	}

	@Override
	protected StaticFileCachingInfo getStaticFileCachingInfo(
		HttpServletRequest httpServletRequest) {

		long maxAge = 86400;
		boolean sendNoCache = false;

		try {
			FrontendCachingConfiguration frontendCachingConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FrontendCachingConfiguration.class,
					_portal.getCompanyId(httpServletRequest));

			maxAge = frontendCachingConfiguration.esModulesMaxAge();
			sendNoCache =
				frontendCachingConfiguration.sendNoCacheForESModules();
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to get ES modules frontend caching configuration: " +
					"will use reasonable defaults instead",
				configurationException);
		}

		return new StaticFileCachingInfo(maxAge, sendNoCache);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SourceMapRequestHelper.class);

	private final ConfigurationProvider _configurationProvider;
	private final Portal _portal;

}