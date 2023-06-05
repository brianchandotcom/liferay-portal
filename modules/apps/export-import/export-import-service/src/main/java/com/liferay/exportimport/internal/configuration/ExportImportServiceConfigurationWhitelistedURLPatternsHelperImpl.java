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

package com.liferay.exportimport.internal.configuration;

import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
import com.liferay.exportimport.configuration.ExportImportServiceConfigurationWhitelistedURLPatternsHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.url.pattern.mapper.URLPatternMapper;
import com.liferay.petra.url.pattern.mapper.URLPatternMapperFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Bowerman
 */
@Component(
	configurationPid = "com.liferay.exportimport.configuration.ExportImportServiceConfiguration",
	service = ExportImportServiceConfigurationWhitelistedURLPatternsHelper.class
)
public class ExportImportServiceConfigurationWhitelistedURLPatternsHelperImpl
	implements ExportImportServiceConfigurationWhitelistedURLPatternsHelper {

	@Override
	public boolean isWhitelistedURL(long companyId, String url) {
		URLPatternMapper<Boolean> urlPatternMapper = _urlPatternMappers.get(
			companyId);

		if (urlPatternMapper == null) {
			urlPatternMapper = _urlPatternMappers.get(CompanyConstants.SYSTEM);

			if (urlPatternMapper == null) {
				return false;
			}
		}

		Boolean result = urlPatternMapper.getValue(url);

		if (result == null) {
			return false;
		}

		return result;
	}

	@Override
	public void rebuildURLPatternMapper(long companyId) throws Exception {
		rebuildURLPatternMapper(companyId, null);
	}

	@Override
	public void rebuildURLPatternMapper(
			long companyId,
			ExportImportServiceConfiguration exportImportServiceConfiguration)
		throws Exception {

		if (exportImportServiceConfiguration == null) {
			exportImportServiceConfiguration =
				_configurationProvider.getCompanyConfiguration(
					ExportImportServiceConfiguration.class, companyId);
		}

		String[] whitelistedURLPatterns =
			exportImportServiceConfiguration.whitelistedURLPatterns();

		if (ArrayUtil.isEmpty(whitelistedURLPatterns)) {
			_urlPatternMappers.remove(companyId);

			return;
		}

		Map<String, Boolean> whitelistedURLPatternsMap = new HashMap<>();

		for (String whitelistedURLPattern : whitelistedURLPatterns) {
			whitelistedURLPatternsMap.put(whitelistedURLPattern, true);
		}

		_urlPatternMappers.put(
			companyId,
			URLPatternMapperFactory.create(whitelistedURLPatternsMap));
	}

	public void rebuildURLPatternMappers() {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				try {
					rebuildURLPatternMapper(companyId);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Unable to instantiate URL pattern mapper for ",
								"company ", companyId),
							exception);
					}
					else {
						_log.error(
							StringBundler.concat(
								"Unable to instantiate URL pattern mapper for ",
								"company ", companyId, ": ",
								exception.getMessage()));
					}
				}
			});
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		rebuildURLPatternMappers();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportServiceConfigurationWhitelistedURLPatternsHelperImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private SettingsLocatorHelper _settingsLocatorHelper;

	private final Map<Long, URLPatternMapper<Boolean>> _urlPatternMappers =
		Collections.synchronizedMap(new LinkedHashMap<>());

}