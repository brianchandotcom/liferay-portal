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

package com.liferay.asset.auto.tagger.internal.osgi.commands;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.AssetAutoTagger;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=tagAllUntagged",
		"osgi.command.function=untagAll", "osgi.command.scope=assetAutoTagger"
	},
	service = AssetAutoTaggerOSGiCommands.class
)
public class AssetAutoTaggerOSGiCommands {

	public void tagAllUntagged(String... classNames) {
		AssetAutoTaggerConfiguration assetAutoTaggerConfiguration =
			_assetAutoTaggerConfigurationFactory.
				getAssetAutoTaggerConfiguration();

		if (!assetAutoTaggerConfiguration.isEnabled()) {
			System.out.println("Asset auto tagger is disabled");

			return;
		}

		if (ArrayUtil.isEmpty(classNames)) {
			Set<String> classNamesSet = new HashSet<>(
				_serviceTrackerMap.keySet());

			classNamesSet.remove("*");

			classNames = classNamesSet.toArray(
				new String[classNamesSet.size()]);
		}

		_forEachAssetEntry(
			classNames,
			assetEntry -> {
				String[] oldAssetTagNames = assetEntry.getTagNames();

				if (oldAssetTagNames.length > 0) {
					return;
				}

				_assetAutoTagger.tag(assetEntry);

				String[] newAssetTagNames = assetEntry.getTagNames();

				if (oldAssetTagNames.length != newAssetTagNames.length) {
					System.out.println(
						String.format(
							"Added %d tags to asset entry %s",
							newAssetTagNames.length - oldAssetTagNames.length,
							assetEntry.getTitle()));
				}
			});
	}

	public void untagAll(String... classNames) {
		_forEachAssetEntry(
			classNames,
			assetEntry -> {
				String[] oldAssetTagNames = assetEntry.getTagNames();

				_assetAutoTagger.untag(assetEntry);

				String[] newAssetTagNames = assetEntry.getTagNames();

				if (oldAssetTagNames.length != newAssetTagNames.length) {
					System.out.println(
						String.format(
							"Deleted %d tags to asset entry %s",
							oldAssetTagNames.length - newAssetTagNames.length,
							assetEntry.getTitle()));
				}
			});
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, AssetAutoTagProvider.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _forEachAssetEntry(
		String[] classNames,
		UnsafeConsumer<AssetEntry, PortalException> consumer) {

		try {
			ActionableDynamicQuery actionableDynamicQuery =
				_assetEntryLocalService.getActionableDynamicQuery();

			if (!ArrayUtil.isEmpty(classNames)) {
				actionableDynamicQuery.setAddCriteriaMethod(
					dynamicQuery -> dynamicQuery.add(
						_getClassNameIdCriterion(classNames)));
			}

			actionableDynamicQuery.setPerformActionMethod(
				(AssetEntry assetEntry) -> consumer.accept(assetEntry));

			actionableDynamicQuery.performActions();
		}
		catch (Exception pe) {
			_log.error(pe, pe);
		}
	}

	private Criterion _getClassNameIdCriterion(String[] classNames) {
		Property property = PropertyFactoryUtil.forName("classNameId");

		Criterion criterion = property.eq(
			_classNameLocalService.getClassNameId(classNames[0]));

		for (int i = 1; i < classNames.length; i++) {
			long classNameId = _classNameLocalService.getClassNameId(
				classNames[i]);

			criterion = RestrictionsFactoryUtil.or(
				criterion, property.eq(classNameId));
		}

		return criterion;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetAutoTaggerOSGiCommands.class);

	@Reference
	private AssetAutoTagger _assetAutoTagger;

	@Reference
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

	@Reference
	private AssetAutoTaggerEntryLocalService _assetAutoTaggerEntryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerMap<String, List<AssetAutoTagProvider>>
		_serviceTrackerMap;

}