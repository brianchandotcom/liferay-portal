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