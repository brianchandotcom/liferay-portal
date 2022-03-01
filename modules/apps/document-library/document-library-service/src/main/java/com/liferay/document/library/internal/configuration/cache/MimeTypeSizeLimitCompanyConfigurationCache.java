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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.internal.configuration.MimeTypeSizeLimitConfiguration",
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.document.library.internal.configuration.MimeTypeSizeLimitConfiguration.scoped",
	service = MimeTypeSizeLimitCompanyConfigurationCache.class
)
public class MimeTypeSizeLimitCompanyConfigurationCache
	implements ManagedServiceFactory {

	public void clear(long companyId) {
		_companyMimeTypeSizeLimitCache.remove(companyId);
	}

	@Override
	public void deleted(String pid) {
		_unmapPid(pid);
	}

	public long getCompanyMimeTypeSizeLimit(long companyId, String mimeType) {
		if (Validator.isNull(mimeType)) {
			return 0;
		}

		Map<String, Long> map = _companyMimeTypeSizeLimitCache.computeIfAbsent(
			companyId, this::_computeCompanyMimeTypeSizeLimit);

		return map.getOrDefault(mimeType, 0L);
	}

	@Override
	public String getName() {
		return "com.liferay.document.library.internal.configuration." +
			"MimeTypeSizeLimitConfiguration.scoped";
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		_unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_pidCompanyIdMapping.put(pid, companyId);

			_companyConfigurationBeans.put(
				companyId,
				ConfigurableUtil.createConfigurable(
					MimeTypeSizeLimitConfiguration.class, dictionary));
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_companyMimeTypeSizeLimitCache = new ConcurrentHashMap<>();
		_systemMimeTypeSizeLimitConfiguration =
			ConfigurableUtil.createConfigurable(
				MimeTypeSizeLimitConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_companyMimeTypeSizeLimitCache = null;
	}

	private Map<String, Long> _computeCompanyMimeTypeSizeLimit(long companyId) {
		MimeTypeSizeLimitConfiguration mimeTypeSizeLimitConfiguration =
			_getCompanyMimeTypeSizeLimitConfiguration(companyId);

		Map<String, Long> mimeTypeSizeLimits = new HashMap<>();

		for (String mimeTypeSizeLimit :
				mimeTypeSizeLimitConfiguration.contentTypeSizeLimit()) {

			MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
				mimeTypeSizeLimit, mimeTypeSizeLimits::put);
		}

		return mimeTypeSizeLimits;
	}

	private MimeTypeSizeLimitConfiguration
		_getCompanyMimeTypeSizeLimitConfiguration(long companyId) {

		if (_companyConfigurationBeans.containsKey(companyId)) {
			return _companyConfigurationBeans.get(companyId);
		}

		return _systemMimeTypeSizeLimitConfiguration;
	}

	private void _unmapPid(String pid) {
		if (_pidCompanyIdMapping.containsKey(pid)) {
			long companyId = _pidCompanyIdMapping.remove(pid);

			_companyConfigurationBeans.remove(companyId);
		}
	}

	private final Map<Long, MimeTypeSizeLimitConfiguration>
		_companyConfigurationBeans = new ConcurrentHashMap<>();
	private Map<Long, Map<String, Long>> _companyMimeTypeSizeLimitCache;
	private final Map<String, Long> _pidCompanyIdMapping =
		new ConcurrentHashMap<>();
	private volatile MimeTypeSizeLimitConfiguration
		_systemMimeTypeSizeLimitConfiguration;

}