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

package com.liferay.asset.auto.tagger.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.AssetAutoTagger;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = AssetAutoTagger.class)
public class AssetAutoTaggerImpl implements AssetAutoTagger {

	@Override
	public void tag(AssetEntry assetEntry) throws PortalException {
		if (!ListUtil.isEmpty(assetEntry.getTags())) {
			return;
		}

		List<AssetAutoTagProvider> assetAutoTagProviders =
			_serviceTrackerMap.getService(assetEntry.getClassName());

		if (assetAutoTagProviders == null) {
			return;
		}

		ServiceContext serviceContext = _createServiceContext(assetEntry);

		AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

		for (AssetAutoTagProvider assetAutoTagProvider :
				assetAutoTagProviders) {

			List<String> tags = assetAutoTagProvider.getTags(
				assetRenderer.getAssetObject());

			for (String tag : tags) {
				AssetTag assetTag = _assetTagLocalService.fetchTag(
					assetEntry.getGroupId(), tag);

				if (assetTag == null) {
					assetTag = _assetTagLocalService.addTag(
						assetEntry.getUserId(), assetEntry.getGroupId(), tag,
						serviceContext);
				}

				_assetTagLocalService.addAssetEntryAssetTag(
					assetEntry.getEntryId(), assetTag);
			}
		}

		Indexer<Object> indexer = _indexerRegistry.getIndexer(
			assetEntry.getClassName());

		indexer.reindex(assetEntry.getClassName(), assetEntry.getClassPK());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, AssetAutoTagProvider.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceContext _createServiceContext(AssetEntry assetEntry) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(assetEntry.getGroupId());

		return serviceContext;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	private ServiceTrackerMap<String, List<AssetAutoTagProvider>>
		_serviceTrackerMap;

}