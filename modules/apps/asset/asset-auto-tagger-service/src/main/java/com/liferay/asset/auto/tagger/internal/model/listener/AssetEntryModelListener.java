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

package com.liferay.asset.auto.tagger.internal.model.listener;

import com.liferay.asset.auto.tagger.internal.AssetAutoTaggerHelper;
import com.liferay.asset.auto.tagger.internal.constants.AssetAutoTaggerDestinationNames;
import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.concurrent.Callable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ModelListener.class)
public class AssetEntryModelListener extends BaseModelListener<AssetEntry> {

	@Override
	public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		if (associationClassName.equals(AssetTag.class.getName())) {
			AssetAutoTaggerEntry assetAutoTaggerEntry =
				_assetAutoTaggerEntryLocalService.fetchAssetAutoTaggerEntry(
					(Long)classPK, (Long)associationClassPK);

			if (assetAutoTaggerEntry != null) {
				_assetAutoTaggerEntryLocalService.deleteAssetAutoTaggerEntry(
					assetAutoTaggerEntry);
			}
		}
	}

	@Override
	public void onBeforeUpdate(AssetEntry assetEntry)
		throws ModelListenerException {

		AssetEntry assetEntryFromDatabase =
			_assetEntryLocalService.fetchAssetEntry(assetEntry.getEntryId());

		if (assetEntryFromDatabase.getPublishDate() == null) {
			TransactionCommitCallbackUtil.registerCallback(
				(Callable<Void>)() -> {
					if ((assetEntry.getPublishDate() == null) ||
						!ListUtil.isEmpty(assetEntry.getTags())) {

						return null;
					}

					if (!_assetAutoTaggerHelper.isAutoTaggable(assetEntry)) {
						return null;
					}

					Message message = new Message();

					message.setPayload(assetEntry);

					_messageBus.sendMessage(
						AssetAutoTaggerDestinationNames.ASSET_AUTO_TAGGER,
						message);

					return null;
				});
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				AssetAutoTaggerDestinationNames.ASSET_AUTO_TAGGER);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_destinationServiceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_destinationServiceRegistration.unregister();
	}

	@Reference
	private AssetAutoTaggerEntryLocalService _assetAutoTaggerEntryLocalService;

	@Reference
	private AssetAutoTaggerHelper _assetAutoTaggerHelper;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _destinationServiceRegistration;

	@Reference
	private MessageBus _messageBus;

}