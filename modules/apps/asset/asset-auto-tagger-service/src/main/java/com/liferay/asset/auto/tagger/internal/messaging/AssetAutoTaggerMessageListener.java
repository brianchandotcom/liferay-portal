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

package com.liferay.asset.auto.tagger.internal.messaging;

import com.liferay.asset.auto.tagger.AssetAutoTagger;
import com.liferay.asset.auto.tagger.internal.constants.AssetAutoTaggerDestinationNames;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = "destination.name=" + AssetAutoTaggerDestinationNames.ASSET_AUTO_TAGGER,
	service = MessageListener.class
)
public class AssetAutoTaggerMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		AssetEntry assetEntry = (AssetEntry)message.getPayload();

		_assetAutoTagger.tag(assetEntry);
	}

	@Reference
	private AssetAutoTagger _assetAutoTagger;

}