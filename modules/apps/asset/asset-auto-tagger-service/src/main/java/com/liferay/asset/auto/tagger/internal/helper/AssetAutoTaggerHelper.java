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

package com.liferay.asset.auto.tagger.internal.helper;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = AssetAutoTaggerHelper.class)
public class AssetAutoTaggerHelper {

	public List<AssetAutoTagProvider<?>> getAssetAutoTagProviders(
		String className) {

		List<AssetAutoTagProvider<?>> assetAutoTagProviders = new ArrayList<>();

		List<AssetAutoTagProvider<?>> generalAssetAutoTagProviders =
			_serviceTrackerMap.getService("*");

		if (ListUtil.isNotEmpty(generalAssetAutoTagProviders)) {
			assetAutoTagProviders.addAll(generalAssetAutoTagProviders);
		}

		if (Validator.isNotNull(className)) {
			List<AssetAutoTagProvider<?>> classNameAssetAutoTagProviders =
				_serviceTrackerMap.getService(className);

			if (ListUtil.isNotEmpty(classNameAssetAutoTagProviders)) {
				assetAutoTagProviders.addAll(classNameAssetAutoTagProviders);
			}
		}

		return assetAutoTagProviders;
	}

	public List<AssetAutoTagProvider<?>> getAssetEntryAssetAutoTagProviders() {
		return _serviceTrackerMap.getService(AssetEntry.class.getName());
	}

	public Set<String> getClassNames() {
		return _serviceTrackerMap.keySet();
	}

	public boolean isAutoTaggable(AssetEntry assetEntry) {
		try {
			AssetAutoTaggerConfiguration assetAutoTaggerConfiguration =
				_getAssetAutoTaggerConfiguration(assetEntry);

			if (assetAutoTaggerConfiguration.isEnabled() &&
				assetEntry.isVisible() &&
				(ListUtil.isNotEmpty(
					getAssetAutoTagProviders(assetEntry.getClassName())) ||
				 ListUtil.isNotEmpty(getAssetEntryAssetAutoTagProviders()))) {

				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return false;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap<String, List<AssetAutoTagProvider<?>>>)
				(ServiceTrackerMap)ServiceTrackerMapFactory.openMultiValueMap(
					bundleContext, AssetAutoTagProvider.class,
					"model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private AssetAutoTaggerConfiguration _getAssetAutoTaggerConfiguration(
			AssetEntry assetEntry)
		throws PortalException {

		return _assetAutoTaggerConfigurationFactory.
			getGroupAssetAutoTaggerConfiguration(
				_groupLocalService.getGroup(assetEntry.getGroupId()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetAutoTaggerHelper.class);

	@Reference
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	private ServiceTrackerMap<String, List<AssetAutoTagProvider<?>>>
		_serviceTrackerMap;

}