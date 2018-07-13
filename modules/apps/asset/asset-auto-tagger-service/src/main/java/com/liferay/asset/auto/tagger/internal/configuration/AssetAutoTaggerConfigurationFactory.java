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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

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
public class AssetAutoTaggerConfigurationFactory {

	public AssetAutoTaggerConfiguration getAssetAutoTaggerConfiguration(
		long companyId, long groupId) {

		return new ScopedAssetAutoTaggerConfiguration(companyId, groupId);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_assetAutoTaggerSystemConfiguration =
			ConfigurableUtil.createConfigurable(
				AssetAutoTaggerSystemConfiguration.class, properties);
	}

	private volatile AssetAutoTaggerConfiguration
		_assetAutoTaggerSystemConfiguration;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private class ScopedAssetAutoTaggerConfiguration
		implements AssetAutoTaggerConfiguration {

		public ScopedAssetAutoTaggerConfiguration(
			long companyId, long groupId) {

			_companyId = companyId;
			_groupId = groupId;
		}

		@Override
		public boolean enabled() {
			try {
				if (!_assetAutoTaggerSystemConfiguration.enabled()) {
					return false;
				}

				AssetAutoTaggerCompanyConfiguration
					assetAutoTaggerCompanyConfiguration =
						_configurationProvider.getCompanyConfiguration(
							AssetAutoTaggerCompanyConfiguration.class,
							_companyId);

				if (!assetAutoTaggerCompanyConfiguration.enabled()) {
					return false;
				}

				AssetAutoTaggerGroupConfiguration
					assetAutoTaggerGroupConfiguration =
						_configurationProvider.getGroupConfiguration(
							AssetAutoTaggerGroupConfiguration.class, _groupId);

				return assetAutoTaggerGroupConfiguration.enabled();
			}
			catch (ConfigurationException ce) {
				return _assetAutoTaggerSystemConfiguration.enabled();
			}
		}

		@Override
		public int maximumNumberOfTagsPerAsset() {
			try {
				AssetAutoTaggerCompanyConfiguration
					assetAutoTaggerCompanyConfiguration =
						_configurationProvider.getCompanyConfiguration(
							AssetAutoTaggerCompanyConfiguration.class,
							_companyId);

				AssetAutoTaggerGroupConfiguration
					assetAutoTaggerGroupConfiguration =
						_configurationProvider.getGroupConfiguration(
							AssetAutoTaggerGroupConfiguration.class, _groupId);

				return Collections.min(
					Arrays.asList(
						_assetAutoTaggerSystemConfiguration.
							maximumNumberOfTagsPerAsset(),
						assetAutoTaggerCompanyConfiguration.
							maximumNumberOfTagsPerAsset(),
						assetAutoTaggerGroupConfiguration.
							maximumNumberOfTagsPerAsset()),
					new MaximumNumberOfTagsPerAssetComparator());
			}
			catch (ConfigurationException ce) {
				return _assetAutoTaggerSystemConfiguration.
					maximumNumberOfTagsPerAsset();
			}
		}

		private final long _companyId;
		private final long _groupId;

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

}