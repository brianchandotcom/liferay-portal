/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.AssetAutoTagger;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.asset.auto.tagger.internal.helper.AssetAutoTaggerHelper;
import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration",
	service = AopService.class
)
public class AssetAutoTaggerImpl implements AopService, AssetAutoTagger {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void tag(AssetEntry assetEntry) throws PortalException {
		if (!_assetAutoTaggerHelper.isAutoTaggable(assetEntry)) {
			return;
		}

		AssetAutoTaggerConfiguration assetAutoTaggerConfiguration =
			_getAssetAutoTaggerConfiguration(assetEntry);

		List<String> assetTagNames = _getAutoAssetTagNames(
			assetEntry,
			assetAutoTaggerConfiguration.getMaximumNumberOfTagsPerAsset());

		if (assetTagNames.isEmpty()) {
			return;
		}

		for (String assetTagName : assetTagNames) {
			try {
				_assetAutoTaggerEntryLocalService.addAssetAutoTaggerEntry(
					assetEntry, assetTagName);
			}
			catch (AssetTagException assetTagException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						String.format(
							"Unable to add auto tag: %s", assetTagName),
						assetTagException);
				}
			}
			catch (PortalException portalException) {
				_log.error(
					String.format("Unable to add auto tag: %s", assetTagName),
					portalException);
			}
		}

		_reindex(assetEntry);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void untag(AssetEntry assetEntry) throws PortalException {
		List<AssetAutoTaggerEntry> assetAutoTaggerEntries =
			_assetAutoTaggerEntryLocalService.getAssetAutoTaggerEntries(
				assetEntry);

		if (assetAutoTaggerEntries.isEmpty()) {
			return;
		}

		for (AssetAutoTaggerEntry assetAutoTaggerEntry :
				assetAutoTaggerEntries) {

			_assetTagLocalService.deleteAssetEntryAssetTag(
				assetAutoTaggerEntry.getAssetEntryId(),
				assetAutoTaggerEntry.getAssetTagId());

			_assetTagLocalService.decrementAssetCount(
				assetAutoTaggerEntry.getAssetTagId(),
				assetEntry.getClassNameId());
		}

		_reindex(assetEntry);
	}

	private AssetAutoTaggerConfiguration _getAssetAutoTaggerConfiguration(
			AssetEntry assetEntry)
		throws PortalException {

		return _assetAutoTaggerConfigurationFactory.
			getGroupAssetAutoTaggerConfiguration(
				_groupLocalService.getGroup(assetEntry.getGroupId()));
	}

	private List<String> _getAutoAssetTagNames(
		AssetEntry assetEntry, int maximumNumberOfTagsPerAsset) {

		AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

		Set<String> assetTagNamesSet = new LinkedHashSet<>();

		for (AssetAutoTagProvider<?> assetAutoTagProvider :
				_assetAutoTaggerHelper.getAssetEntryAssetAutoTagProviders()) {

			AssetAutoTagProvider<AssetEntry> assetEntryAssetAutoTagProvider =
				(AssetAutoTagProvider<AssetEntry>)assetAutoTagProvider;

			assetTagNamesSet.addAll(
				assetEntryAssetAutoTagProvider.getTagNames(assetEntry));
		}

		if (assetRenderer != null) {
			List<AssetAutoTagProvider<?>> assetAutoTagProviders =
				_assetAutoTaggerHelper.getAssetAutoTagProviders(
					assetEntry.getClassName());

			for (AssetAutoTagProvider<?> assetAutoTagProvider :
					assetAutoTagProviders) {

				AssetAutoTagProvider<Object> objectAssetAutoTagProvider =
					(AssetAutoTagProvider<Object>)assetAutoTagProvider;

				assetTagNamesSet.addAll(
					objectAssetAutoTagProvider.getTagNames(
						assetRenderer.getAssetObject()));
			}
		}

		assetTagNamesSet.removeAll(Arrays.asList(assetEntry.getTagNames()));

		List<String> assetTagNames = new ArrayList<>(assetTagNamesSet);

		if (maximumNumberOfTagsPerAsset > 0) {
			return assetTagNames.subList(
				0, Math.min(maximumNumberOfTagsPerAsset, assetTagNames.size()));
		}

		return assetTagNames;
	}

	private void _reindex(AssetEntry assetEntry) throws PortalException {
		Indexer<Object> indexer = _indexerRegistry.getIndexer(
			assetEntry.getClassName());

		if (indexer == null) {
			return;
		}

		AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

		if (assetRenderer == null) {
			indexer.reindex(assetEntry.getClassName(), assetEntry.getClassPK());

			return;
		}

		indexer.reindex(assetRenderer.getAssetObject());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetAutoTaggerImpl.class);

	@Reference
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

	@Reference
	private AssetAutoTaggerEntryLocalService _assetAutoTaggerEntryLocalService;

	@Reference
	private AssetAutoTaggerHelper _assetAutoTaggerHelper;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

}