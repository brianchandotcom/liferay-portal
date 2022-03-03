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

package com.liferay.content.dashboard.web.internal.provider;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AssetVocabulariesProvider.class)
public class AssetVocabulariesProvider {

	public List<AssetVocabulary> getAssetVocabularies(
		long[] assetVocabularyIds) {

		try {
			LongStream longStream = Arrays.stream(assetVocabularyIds);

			return longStream.boxed(
			).map(
				assetVocabularyId -> {
					try {
						return _assetVocabularyLocalService.getAssetVocabulary(
							assetVocabularyId);
					}
					catch (PortalException portalException) {
						_log.error(portalException);

						return null;
					}
				}
			).filter(
				Objects::nonNull
			).filter(
				assetVocabulary -> assetVocabulary.getCategoriesCount() > 0
			).collect(
				Collectors.toList()
			);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get content dashboard admin configuration",
					exception);
			}

			return Collections.emptyList();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabulariesProvider.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}