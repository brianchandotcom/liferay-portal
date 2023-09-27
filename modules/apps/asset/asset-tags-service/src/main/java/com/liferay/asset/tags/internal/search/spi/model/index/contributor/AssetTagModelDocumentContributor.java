/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
@Component(
	property = "indexer.class.name=com.liferay.asset.kernel.model.AssetTag",
	service = ModelDocumentContributor.class
)
public class AssetTagModelDocumentContributor
	implements ModelDocumentContributor<AssetTag> {

	@Override
	public void contribute(Document document, AssetTag assetTag) {
		document.addTextSortable(Field.NAME, assetTag.getName());
		document.addNumberSortable("assetCount", assetTag.getAssetCount());
		document.addKeyword(
			"subscribed",
			_subscriptionLocalService.isSubscribed(
				assetTag.getCompanyId(), PrincipalThreadLocal.getUserId(),
				AssetTag.class.getName(), assetTag.getTagId()));
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}