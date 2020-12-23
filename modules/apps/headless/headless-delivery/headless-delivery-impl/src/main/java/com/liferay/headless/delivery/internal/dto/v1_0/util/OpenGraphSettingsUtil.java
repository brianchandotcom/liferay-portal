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

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.delivery.dto.v1_0.OpenGraphSettings;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

/**
 * @author Jürgen Kappler
 * @author Javier de Arcos
 */
public class OpenGraphSettingsUtil {

	public static OpenGraphSettings getOpenGraphSettings(
		DLAppService dlAppService, DLURLHelper dlURLHelper,
		DTOConverterContext dtoConverterContext,
		LayoutSEOEntryLocalService layoutSEOEntryLocalService, Layout layout) {

		LayoutSEOEntry layoutSEOEntry =
			layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId());

		if (layoutSEOEntry == null) {
			return null;
		}

		return new OpenGraphSettings() {
			{
				description = layoutSEOEntry.getOpenGraphDescription(
					dtoConverterContext.getLocale());
				description_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					layoutSEOEntry.getOpenGraphDescriptionMap());
				title = layoutSEOEntry.getOpenGraphTitle(
					dtoConverterContext.getLocale());
				title_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					layoutSEOEntry.getOpenGraphTitleMap());

				setImage(
					() -> {
						long openGraphImageFileEntryId =
							layoutSEOEntry.getOpenGraphImageFileEntryId();

						if (openGraphImageFileEntryId == 0) {
							return null;
						}

						FileEntry fileEntry = dlAppService.getFileEntry(
							openGraphImageFileEntryId);

						return ContentDocumentUtil.toContentDocument(
							dlURLHelper,
							"openGraphSettings.contentFieldValue.image",
							fileEntry,
							dtoConverterContext.getUriInfoOptional());
					});
			}
		};
	}

}