/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal.model.listener;

import com.liferay.asset.auto.tagger.internal.constants.AssetAutoTaggerDestinationNames;
import com.liferay.asset.auto.tagger.internal.helper.AssetAutoTaggerHelper;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
@Component(service = ModelListener.class)
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
	public void onBeforeUpdate(
			AssetEntry originalAssetEntry, AssetEntry assetEntry)
		throws ModelListenerException {

		boolean updateAutoTags = _isUpdateAutoTags();

		AssetEntry assetEntryFromDatabase =
			_assetEntryLocalService.fetchAssetEntry(assetEntry.getEntryId());

		if (updateAutoTags ||
			(assetEntryFromDatabase.getPublishDate() == null)) {

			TransactionCommitCallbackUtil.registerCallback(
				(Callable<Void>)() -> {
					if (!updateAutoTags &&
						((assetEntry.getPublishDate() == null) ||
						 ListUtil.isNotEmpty(assetEntry.getTags()) ||
						 !_assetAutoTaggerHelper.isAutoTaggable(assetEntry))) {

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

	private boolean _isUpdateAutoTags() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return false;
		}

		return GetterUtil.getBoolean(
			serviceContext.getAttribute("updateAutoTags"));
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