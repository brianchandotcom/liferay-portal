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

package com.liferay.commerce.product.demo.data.creator.internal.util;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = AssetVocabularyDemoDataCreatorHelper.class)
public class AssetVocabularyDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public AssetVocabulary createAssetVocabulary(
			long userId, long groupId, String title)
		throws PortalException {

		AssetVocabulary assetVocabulary = _assetVocabularies.get(title);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		assetVocabulary = _assetVocabularyLocalService.getGroupVocabulary(
			groupId, title);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		assetVocabulary = _assetVocabularyLocalService.addVocabulary(
			userId, groupId, title, serviceContext);

		_assetVocabularies.put(title, assetVocabulary);

		return assetVocabulary;
	}

	public void deleteAssetVocabularies() throws PortalException {
		Set<Map.Entry<String, AssetVocabulary>> entrySet =
			_assetVocabularies.entrySet();

		Iterator<Map.Entry<String, AssetVocabulary>> iterator =
			entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, AssetVocabulary> entry = iterator.next();

			_assetVocabularyLocalService.deleteAssetVocabulary(
				entry.getValue());

			iterator.remove();
		}
	}

	public void init() {
		_assetVocabularies = new HashMap<>();
	}

	@Activate
	protected void activate() {
		init();
	}

	@Deactivate
	protected void deactivate() {
		_assetVocabularies = null;
	}

	private Map<String, AssetVocabulary> _assetVocabularies;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}