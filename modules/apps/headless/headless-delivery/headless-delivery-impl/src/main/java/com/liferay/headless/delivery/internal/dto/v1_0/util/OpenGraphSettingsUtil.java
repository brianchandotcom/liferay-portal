/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.delivery.dto.v1_0.OpenGraphSettings;
import com.liferay.headless.delivery.dto.v1_0.util.ContentDocumentUtil;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.portal.kernel.model.Layout;
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
				imageAlt = layoutSEOEntry.getOpenGraphImageAlt(
					dtoConverterContext.getLocale());
				imageAlt_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					layoutSEOEntry.getOpenGraphImageAltMap());
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

						return ContentDocumentUtil.toContentDocument(
							dlURLHelper,
							"openGraphSettings.contentFieldValue.image",
							dlAppService.getFileEntry(
								openGraphImageFileEntryId),
							dtoConverterContext.getUriInfo());
					});
			}
		};
	}

}