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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.headless.web.experience.dto.v1_0.ContentListElement;
import com.liferay.headless.web.experience.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.web.experience.dto.v1_0.converter.DefaultDTOConverterContext;
import com.liferay.headless.web.experience.internal.dto.v1_0.converter.DTOConverterRegistry;
import com.liferay.headless.web.experience.resource.v1_0.ContentListElementResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.segments.constants.SegmentsConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-list-element.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentListElementResource.class
)
public class ContentListElementResourceImpl
	extends BaseContentListElementResourceImpl {

	@Override
	public Page<ContentListElement> getContentListContentListElementsPage(
			Long contentListId, Pagination pagination)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryService.fetchAssetListEntry(contentListId);

		return Page.of(
			transform(
				assetListEntry.getAssetEntries(
					SegmentsConstants.SEGMENTS_ENTRY_ID_DEFAULT,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toContentListElement),
			pagination,
			assetListEntry.getAssetEntriesCount(
				SegmentsConstants.SEGMENTS_ENTRY_ID_DEFAULT));
	}

	private ContentListElement _toContentListElement(AssetEntry assetEntry) {
		return new ContentListElement() {
			{
				contentType = assetEntry.getClassName();
				order = assetEntry.getPriority();
				title = assetEntry.getTitle(
					contextAcceptLanguage.getPreferredLocale());

				setContent(
					() -> {
						DTOConverter dtoConverter =
							_dtoConverterRegistry.getDTOConverter(
								assetEntry.getClassName());

						if (dtoConverter == null) {
							return null;
						}

						return dtoConverter.toDTO(
							new DefaultDTOConverterContext(
								contextAcceptLanguage.getPreferredLocale(),
								assetEntry.getClassPK(), contextUriInfo));
					});
			}
		};
	}

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}