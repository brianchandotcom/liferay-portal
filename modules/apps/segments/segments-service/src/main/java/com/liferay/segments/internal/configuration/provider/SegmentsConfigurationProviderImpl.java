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

package com.liferay.segments.internal.configuration.provider;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.segments.configuration.SegmentsCompanyConfiguration;
import com.liferay.segments.configuration.SegmentsConfiguration;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;

import java.io.IOException;

import java.util.Map;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	configurationPid = "com.liferay.segments.configuration.SegmentsConfiguration",
	service = SegmentsConfigurationProvider.class
)
public class SegmentsConfigurationProviderImpl
	implements SegmentsConfigurationProvider {

	@Override
	public boolean isRoleSegmentationEnabled(long companyId)
		throws ConfigurationException {

		if (!_segmentsConfiguration.roleSegmentationEnabled()) {
			return false;
		}

		if (!_isSegmentsCompanyConfigurationDefined(companyId)) {
			return _segmentsConfiguration.roleSegmentationEnabled();
		}

		SegmentsCompanyConfiguration segmentsCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SegmentsCompanyConfiguration.class, companyId);

		if (!segmentsCompanyConfiguration.roleSegmentationEnabled()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isSegmentationEnabled(long companyId)
		throws ConfigurationException {

		if (!_segmentsConfiguration.segmentationEnabled()) {
			return false;
		}

		if (!_isSegmentsCompanyConfigurationDefined(companyId)) {
			return _segmentsConfiguration.segmentationEnabled();
		}

		SegmentsCompanyConfiguration segmentsCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SegmentsCompanyConfiguration.class, companyId);

		if (!segmentsCompanyConfiguration.segmentationEnabled()) {
			return false;
		}

		return true;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_segmentsConfiguration = ConfigurableUtil.createConfigurable(
			SegmentsConfiguration.class, properties);
	}

	private boolean _isSegmentsCompanyConfigurationDefined(long companyId)
		throws ConfigurationException {

		try {
			String filterString = StringBundler.concat(
				"(&(", ConfigurationAdmin.SERVICE_FACTORYPID, StringPool.EQUAL,
				SegmentsCompanyConfiguration.class.getName(), ".scoped",
				")(companyId=", companyId, "))");

			Configuration[] configuration =
				_configurationAdmin.listConfigurations(filterString);

			if (configuration != null) {
				return true;
			}

			return false;
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new ConfigurationException(exception);
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile SegmentsConfiguration _segmentsConfiguration;

}