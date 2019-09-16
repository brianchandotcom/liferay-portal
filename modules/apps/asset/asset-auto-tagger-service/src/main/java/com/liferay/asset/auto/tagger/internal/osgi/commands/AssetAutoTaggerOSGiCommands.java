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

import com.liferay.asset.auto.tagger.AssetAutoTagger;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.asset.auto.tagger.internal.AssetAutoTaggerHelper;
import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=commitAutoTags",
		"osgi.command.function=tagAllUntagged",
		"osgi.command.function=untagAll", "osgi.command.scope=assetAutoTagger"
	},
	service = AssetAutoTaggerOSGiCommands.class
)
public class AssetAutoTaggerOSGiCommands {

	public void commitAutoTags(String companyId, String... classNames) {
		_forEachAssetEntry(
			companyId,
			assetEntry -> {
				List<AssetAutoTaggerEntry> assetAutoTaggerEntries =
					_assetAutoTaggerEntryLocalService.getAssetAutoTaggerEntries(
						assetEntry);

				for (AssetAutoTaggerEntry assetAutoTaggerEntry :
						assetAutoTaggerEntries) {

					_assetAutoTaggerEntryLocalService.
						deleteAssetAutoTaggerEntry(assetAutoTaggerEntry);
				}

				if (!assetAutoTaggerEntries.isEmpty()) {
					System.out.println(
						String.format(
							"Commited %d auto tags for asset entry %s",
							assetAutoTaggerEntries.size(),
							assetEntry.getTitle()));
				}
			});
	}

	public void tagAllUntagged(String companyId, String... classNames) {
		AssetAutoTaggerConfiguration assetAutoTaggerConfiguration =
			_assetAutoTaggerConfigurationFactory.
				getSystemAssetAutoTaggerConfiguration();

		if (!assetAutoTaggerConfiguration.isEnabled()) {
			System.out.println("Asset auto tagger is disabled");

			return;
		}

		if (ArrayUtil.isEmpty(classNames)) {
			Set<String> classNamesSet = new HashSet<>(
				_assetAutoTaggerHelper.getClassNames());

			classNamesSet.remove("*");

			classNames = classNamesSet.toArray(new String[0]);
		}

		_forEachAssetEntry(
			companyId,
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

	public void untagAll(String companyId, String... classNames) {
		_forEachAssetEntry(
			companyId,
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

	private void _forEachAssetEntry(
		String companyId,
		UnsafeConsumer<AssetEntry, PortalException> consumer) {

		try {
			ActionableDynamicQuery actionableDynamicQuery =
				_assetEntryLocalService.getActionableDynamicQuery();

			if (Validator.isNotNull(companyId)) {
				actionableDynamicQuery.setCompanyId(Long.valueOf(companyId));
			}

			actionableDynamicQuery.setPerformActionMethod(
				(AssetEntry assetEntry) -> consumer.accept(assetEntry));

			actionableDynamicQuery.performActions();
		}
		catch (Exception pe) {
			_log.error(pe, pe);
		}
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
	private AssetAutoTaggerHelper _assetAutoTaggerHelper;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}