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
import com.liferay.petra.url.pattern.mapper.URLPatternMapper;
import com.liferay.petra.url.pattern.mapper.URLPatternMapperFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

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
	public boolean isWhitelistedURL(String url) {
		if (_urlPatternMapper == null) {
			return false;
		}

		Boolean result = _urlPatternMapper.getValue(url);

		if (result == null) {
			return false;
		}

		return result;
	}

	@Override
	public void rebuildURLPatternMapper() {
		String[] whitelistedURLPatterns =
			_exportImportServiceConfiguration.whitelistedURLPatterns();

		if (ArrayUtil.isEmpty(whitelistedURLPatterns)) {
			_urlPatternMapper = null;

			return;
		}

		Map<String, Boolean> whitelistedURLPatternsMap = new HashMap<>();

		for (String whitelistedURLPattern : whitelistedURLPatterns) {
			whitelistedURLPatternsMap.put(whitelistedURLPattern, true);
		}

		_urlPatternMapper = URLPatternMapperFactory.create(
			whitelistedURLPatternsMap);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_exportImportServiceConfiguration = ConfigurableUtil.createConfigurable(
			ExportImportServiceConfiguration.class, properties);

		rebuildURLPatternMapper();
	}

	private volatile ExportImportServiceConfiguration
		_exportImportServiceConfiguration;
	private URLPatternMapper<Boolean> _urlPatternMapper;

}