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

package com.liferay.portal.remote.cors.internal.configuration.admin.service;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.url.pattern.mapper.URLPatternMapperFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration;
import com.liferay.portal.remote.cors.internal.CORSSupport;
import com.liferay.portal.remote.cors.internal.util.PortalCORSRegistryUtil;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = Constants.SERVICE_PID + "=com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration",
	service = ManagedServiceFactory.class
)
public class PortalCORSManagedServiceFactory implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		Dictionary<String, ?> properties =
			PortalCORSRegistryUtil.removeConfigurationPidsProperties(pid);

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId == CompanyConstants.SYSTEM) {
			_rebuild();
		}
		else {
			_rebuild(companyId);
		}
	}

	@Override
	public String getName() {
		return StringPool.BLANK;
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties)
		throws ConfigurationException {

		Dictionary<String, ?> oldProperties =
			PortalCORSRegistryUtil.updateConfigurationPidsProperties(
				pid, properties);

		long companyId = GetterUtil.getLong(
			properties.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId == CompanyConstants.SYSTEM) {
			_rebuild();

			return;
		}

		if (oldProperties != null) {
			long oldCompanyId = GetterUtil.getLong(
				oldProperties.get("companyId"));

			if (oldCompanyId == CompanyConstants.SYSTEM) {
				_rebuild();

				return;
			}

			if (oldCompanyId != companyId) {
				_rebuild(oldCompanyId);
			}
		}

		_rebuild(companyId);
	}

	private void _mergeCORSConfiguration(
		Map<String, CORSSupport> corsSupports, long companyId) {

		for (Dictionary<String, ?> properties :
				PortalCORSRegistryUtil.getValuesConfigurationPidsProperties()) {

			if (companyId != GetterUtil.getLong(properties.get("companyId"))) {
				continue;
			}

			PortalCORSConfiguration portalCORSConfiguration =
				ConfigurableUtil.createConfigurable(
					PortalCORSConfiguration.class, properties);

			_populateCORSSupports(corsSupports, portalCORSConfiguration);
		}
	}

	private void _populateCORSSupports(
		Map<String, CORSSupport> corsSupports,
		PortalCORSConfiguration portalCORSConfiguration) {

		CORSSupport corsSupport = new CORSSupport();

		corsSupport.setCORSHeaders(
			CORSSupport.buildCORSHeaders(portalCORSConfiguration.headers()));

		for (String urlPattern :
				portalCORSConfiguration.filterMappingURLPatterns()) {

			if (!corsSupports.containsKey(urlPattern)) {
				corsSupports.put(urlPattern, corsSupport);
			}
		}
	}

	private void _rebuild() {
		Map<String, CORSSupport> corsSupports = new HashMap<>();

		_mergeCORSConfiguration(corsSupports, CompanyConstants.SYSTEM);

		if (corsSupports.isEmpty()) {
			PortalCORSRegistryUtil.removeUrlPatternMappers(
				CompanyConstants.SYSTEM);
		}
		else {
			PortalCORSRegistryUtil.updateUrlPattensMappers(
				CompanyConstants.SYSTEM,
				URLPatternMapperFactory.create(corsSupports));
		}

		_companyLocalService.forEachCompanyId(
			companyId -> {
				if (companyId != CompanyConstants.SYSTEM) {
					_rebuild(companyId);
				}
			},
			ArrayUtil.toLongArray(
				PortalCORSRegistryUtil.getKeySetUrlPatternMappers()));
	}

	private void _rebuild(long companyId) {
		Map<String, CORSSupport> corsSupports = new HashMap<>();

		_mergeCORSConfiguration(corsSupports, companyId);

		if (corsSupports.isEmpty()) {
			PortalCORSRegistryUtil.removeUrlPatternMappers(companyId);

			return;
		}

		_mergeCORSConfiguration(corsSupports, CompanyConstants.SYSTEM);

		PortalCORSRegistryUtil.updateUrlPattensMappers(
			companyId, URLPatternMapperFactory.create(corsSupports));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

}