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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.headless.delivery.dto.v1_0.RelatedContent;
import com.liferay.headless.delivery.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class RelatedContentUtil {

	public static RelatedContent[] toRelatedContents(
			AssetEntryLocalService assetEntryLocalService,
			AssetLinkLocalService assetLinkLocalService, String className,
			long classPK, Locale locale)
		throws Exception {

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);

		return TransformUtil.transformToArray(
			assetLinkLocalService.getDirectLinks(assetEntry.getEntryId()),
			assetLink -> _toRelatedContent(
				assetEntryLocalService.getEntry(assetLink.getEntryId2()),
				locale),
			RelatedContent.class);
	}

	private static RelatedContent _toRelatedContent(
		AssetEntry assetEntry, Locale locale) {

		if (assetEntry == null) {
			return null;
		}

		return new RelatedContent() {
			{
				id = assetEntry.getClassPK();
				title = assetEntry.getTitle(locale);

				setContentType(
					() -> {
						DTOConverter dtoConverter =
							DTOConverterRegistry.getDTOConverter(
								assetEntry.getClassName());

						if (dtoConverter == null) {
							return assetEntry.getClassName();
						}

						return dtoConverter.getContentType();
					});
			}
		};
	}

}