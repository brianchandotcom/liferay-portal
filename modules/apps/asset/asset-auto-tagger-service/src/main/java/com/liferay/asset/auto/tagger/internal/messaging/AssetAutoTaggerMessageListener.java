/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal.messaging;

import com.liferay.asset.auto.tagger.AssetAutoTagger;
import com.liferay.asset.auto.tagger.internal.constants.AssetAutoTaggerDestinationNames;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.GroupThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "destination.name=" + AssetAutoTaggerDestinationNames.ASSET_AUTO_TAGGER,
	service = MessageListener.class
)
public class AssetAutoTaggerMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		try (SafeCloseable safeCloseable =
				GroupThreadLocal.setGroupIdWithSafeCloseable(
					message.getLong("groupId"))) {

			_assetAutoTagger.tag((AssetEntry)message.get("assetEntry"));
		}
	}

	@Reference
	private AssetAutoTagger _assetAutoTagger;

}