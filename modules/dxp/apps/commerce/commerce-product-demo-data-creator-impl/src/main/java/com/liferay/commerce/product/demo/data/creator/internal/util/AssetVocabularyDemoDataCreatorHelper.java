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

import com.liferay.asset.kernel.exception.NoSuchVocabularyException;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
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

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				userId, groupId, title, serviceContext);

		_assetVocabularyIds.add(assetVocabulary.getVocabularyId());

		return assetVocabulary;
	}

	public void deleteAssetVocabularies() throws PortalException {
		for (long assetVocabularyId : _assetVocabularyIds) {
			try {
				_assetVocabularyLocalService.deleteVocabulary(
					assetVocabularyId);
			}
			catch (NoSuchVocabularyException nsve) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsve);
				}
			}

			_assetVocabularyIds.remove(assetVocabularyId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularyDemoDataCreatorHelper.class);

	private final List<Long> _assetVocabularyIds = new CopyOnWriteArrayList<>();

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}