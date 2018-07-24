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

package com.liferay.asset.auto.tagger.internal.configuration;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.asset.auto.tagger.constants.AssetAutoTaggerConstants;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.asset.auto.tagger.internal.configuration.AssetAutoTaggerSystemConfiguration",
	service = AssetAutoTaggerConfigurationFactory.class
)
public class AssetAutoTaggerConfigurationFactoryImpl
	implements AssetAutoTaggerConfigurationFactory {

	public AssetAutoTaggerConfiguration getAssetAutoTaggerConfiguration() {
		return new AssetAutoTaggerConfiguration() {

			@Override
			public int getMaximumNumberOfTagsPerAsset() {
				return _assetAutoTaggerSystemConfiguration.
					maximumNumberOfTagsPerAsset();
			}

			@Override
			public boolean isAvailable() {
				return true;
			}

			@Override
			public boolean isEnabled() {
				return _assetAutoTaggerSystemConfiguration.enabled();
			}

		};
	}

	public AssetAutoTaggerConfiguration getAssetAutoTaggerConfiguration(
		Company company) {

		try {
			return new CompanyAssetAutoTaggerConfiguration(company);
		}
		catch (ConfigurationException ce) {
			_log.error(ce, ce);

			return getAssetAutoTaggerConfiguration();
		}
	}

	public AssetAutoTaggerConfiguration getAssetAutoTaggerConfiguration(
		Group group) {

		try {
			return new GroupAssetAutoTaggerConfiguration(group);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return getAssetAutoTaggerConfiguration();
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_assetAutoTaggerSystemConfiguration =
			ConfigurableUtil.createConfigurable(
				AssetAutoTaggerSystemConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetAutoTaggerConfigurationFactoryImpl.class);

	private volatile AssetAutoTaggerSystemConfiguration
		_assetAutoTaggerSystemConfiguration;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private class CompanyAssetAutoTaggerConfiguration
		implements AssetAutoTaggerConfiguration {

		public CompanyAssetAutoTaggerConfiguration(Company company)
			throws ConfigurationException {

			_assetAutoTaggerCompanyConfiguration =
				_configurationProvider.getConfiguration(
					AssetAutoTaggerCompanyConfiguration.class,
					new CompanyServiceSettingsLocator(
						company.getCompanyId(),
						AssetAutoTaggerConstants.SERVICE_NAME));
		}

		@Override
		public int getMaximumNumberOfTagsPerAsset() {
			return Collections.min(
				Arrays.asList(
					_assetAutoTaggerSystemConfiguration.
						maximumNumberOfTagsPerAsset(),
					_assetAutoTaggerCompanyConfiguration.
						maximumNumberOfTagsPerAsset()),
				new MaximumNumberOfTagsPerAssetComparator());
		}

		@Override
		public boolean isAvailable() {
			return _assetAutoTaggerSystemConfiguration.enabled();
		}

		@Override
		public boolean isEnabled() {
			if (!_assetAutoTaggerSystemConfiguration.enabled()) {
				return false;
			}

			return _assetAutoTaggerCompanyConfiguration.enabled();
		}

		private AssetAutoTaggerCompanyConfiguration
			_assetAutoTaggerCompanyConfiguration;

		private class MaximumNumberOfTagsPerAssetComparator
			implements Comparator<Integer> {

			@Override
			public int compare(
				Integer maximumNumberOfTagsPerAsset1,
				Integer maximumNumberOfTagsPerAsset2) {

				if (maximumNumberOfTagsPerAsset1 == -1) {
					return 1;
				}

				if (maximumNumberOfTagsPerAsset2 == -1) {
					return -1;
				}

				return maximumNumberOfTagsPerAsset1 -
					maximumNumberOfTagsPerAsset2;
			}

		}

	}

	private class GroupAssetAutoTaggerConfiguration
		implements AssetAutoTaggerConfiguration {

		public GroupAssetAutoTaggerConfiguration(Group group)
			throws PortalException {

			_group = group;

			_assetAutoTaggerCompanyConfiguration =
				new CompanyAssetAutoTaggerConfiguration(
					_companyLocalService.getCompany(_group.getCompanyId()));
		}

		@Override
		public int getMaximumNumberOfTagsPerAsset() {
			return _assetAutoTaggerCompanyConfiguration.
				getMaximumNumberOfTagsPerAsset();
		}

		@Override
		public boolean isAvailable() {
			return _assetAutoTaggerCompanyConfiguration.isEnabled();
		}

		@Override
		public boolean isEnabled() {
			try {
				if (!_assetAutoTaggerCompanyConfiguration.isEnabled()) {
					return false;
				}

				String assetAutoTaggingEnabledProperty =
					_group.getTypeSettingsProperty("assetAutoTaggingEnabled");

				if (Validator.isNotNull(assetAutoTaggingEnabledProperty)) {
					return Boolean.valueOf(assetAutoTaggingEnabledProperty);
				}
				else {
					AssetAutoTaggerGroupConfiguration
						assetAutoTaggerGroupConfiguration =
							_configurationProvider.getGroupConfiguration(
								AssetAutoTaggerGroupConfiguration.class,
								_group.getGroupId());

					return assetAutoTaggerGroupConfiguration.enabled();
				}
			}
			catch (ConfigurationException ce) {
				return _assetAutoTaggerCompanyConfiguration.isEnabled();
			}
		}

		private AssetAutoTaggerConfiguration
			_assetAutoTaggerCompanyConfiguration;
		private final Group _group;

	}

}