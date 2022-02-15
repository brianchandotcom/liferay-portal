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

package com.liferay.document.library.internal.configuration.cache;

import com.liferay.document.library.internal.configuration.MimeTypeSizeLimitConfiguration;
import com.liferay.document.library.internal.util.MimeTypeSizeLimitUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = MimeTypeSizeLimitCompanyConfigurationCache.class)
public class MimeTypeSizeLimitCompanyConfigurationCache {

	public void clear(long companyId) {
		_companyMimeTypeSizeLimitCache.remove(companyId);
	}

	public long getCompanyMimeTypeSizeLimit(long companyId, String mimeType) {
		if (Validator.isNull(mimeType)) {
			return 0;
		}

		Map<String, Long> map = _companyMimeTypeSizeLimitCache.computeIfAbsent(
			companyId, this::_computeCompanyMimeTypeSizeLimit);

		return map.getOrDefault(mimeType, 0L);
	}

	@Activate
	protected void activate() {
		_companyMimeTypeSizeLimitCache = new ConcurrentHashMap<>();
	}

	@Deactivate
	protected void deactivate() {
		_companyMimeTypeSizeLimitCache = null;
	}

	private Map<String, Long> _computeCompanyMimeTypeSizeLimit(long companyId) {
		try {
			MimeTypeSizeLimitConfiguration mimeTypeSizeLimitConfiguration =
				_configurationProvider.getCompanyConfiguration(
					MimeTypeSizeLimitConfiguration.class, companyId);

			Map<String, Long> mimeTypeSizeLimits = new HashMap<>();

			for (String mimeTypeSizeLimit :
					mimeTypeSizeLimitConfiguration.contentTypeSizeLimit()) {

				MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
					mimeTypeSizeLimit, mimeTypeSizeLimits::put);
			}

			return mimeTypeSizeLimits;
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);

			return Collections.emptyMap();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MimeTypeSizeLimitCompanyConfigurationCache.class);

	private Map<Long, Map<String, Long>> _companyMimeTypeSizeLimitCache;

	@Reference
	private ConfigurationProvider _configurationProvider;

}