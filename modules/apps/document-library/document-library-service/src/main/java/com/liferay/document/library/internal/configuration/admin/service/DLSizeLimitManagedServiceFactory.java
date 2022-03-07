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

package com.liferay.document.library.internal.configuration.admin.service;

import com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration;
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
	configurationPid = "com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration",
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration.scoped",
	service = {
		DLSizeLimitManagedServiceFactory.class, ManagedServiceFactory.class
	}
)
public class DLSizeLimitManagedServiceFactory implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		_unmapPid(pid);
	}

	public long getCompanyMimeTypeSizeLimit(long companyId, String mimeType) {
		if (Validator.isNull(mimeType)) {
			return 0;
		}

		Map<String, Long> map = _mimeTypeSizeLimitsMap.computeIfAbsent(
			companyId, this::_computeCompanyMimeTypeSizeLimit);

		return map.getOrDefault(mimeType, 0L);
	}

	@Override
	public String getName() {
		return "com.liferay.document.library.internal.configuration." +
			"DLSizeLimitConfiguration.scoped";
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		_unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_companyConfigurationBeans.put(
				companyId,
				ConfigurableUtil.createConfigurable(
					DLSizeLimitConfiguration.class, dictionary));
			_companyIds.put(pid, companyId);
			_mimeTypeSizeLimitsMap.remove(companyId);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_mimeTypeSizeLimitsMap = new ConcurrentHashMap<>();
		_systemDLSizeLimitConfiguration = ConfigurableUtil.createConfigurable(
			DLSizeLimitConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_mimeTypeSizeLimitsMap = null;
	}

	private Map<String, Long> _computeCompanyMimeTypeSizeLimit(long companyId) {
		DLSizeLimitConfiguration dlSizeLimitConfiguration =
			_getCompanyDLSizeLimitConfiguration(companyId);

		Map<String, Long> mimeTypeSizeLimits = new HashMap<>();

		for (String mimeTypeSizeLimit :
				dlSizeLimitConfiguration.mimeTypeSizeLimit()) {

			MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
				mimeTypeSizeLimit, mimeTypeSizeLimits::put);
		}

		return mimeTypeSizeLimits;
	}

	private DLSizeLimitConfiguration _getCompanyDLSizeLimitConfiguration(
		long companyId) {

		if (_companyConfigurationBeans.containsKey(companyId)) {
			return _companyConfigurationBeans.get(companyId);
		}

		return _systemDLSizeLimitConfiguration;
	}

	private void _unmapPid(String pid) {
		if (_companyIds.containsKey(pid)) {
			long companyId = _companyIds.remove(pid);

			_companyConfigurationBeans.remove(companyId);
			_mimeTypeSizeLimitsMap.remove(companyId);
		}
	}

	private final Map<Long, DLSizeLimitConfiguration>
		_companyConfigurationBeans = new ConcurrentHashMap<>();
	private final Map<String, Long> _companyIds = new ConcurrentHashMap<>();
	private Map<Long, Map<String, Long>> _mimeTypeSizeLimitsMap;
	private volatile DLSizeLimitConfiguration _systemDLSizeLimitConfiguration;

}