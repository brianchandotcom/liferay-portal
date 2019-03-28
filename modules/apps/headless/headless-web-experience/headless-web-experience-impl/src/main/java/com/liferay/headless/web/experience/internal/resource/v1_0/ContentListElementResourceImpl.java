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
import com.liferay.headless.common.spi.osgi.AssetEntryToDTOConverter;
import com.liferay.headless.web.experience.dto.v1_0.ContentListElement;
import com.liferay.headless.web.experience.internal.registry.AssetEntryToDTOConverterRegistry;
import com.liferay.headless.web.experience.resource.v1_0.ContentListElementResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;

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

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.getDefaultSegmentsEntry(
				assetListEntry.getGroupId());

		long segmentsEntryId = segmentsEntry.getSegmentsEntryId();

		return Page.of(
			transform(
				assetListEntry.getAssetEntries(
					segmentsEntryId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toAssetListElement),
			pagination, assetListEntry.getAssetEntriesCount(segmentsEntryId));
	}

	private ContentListElement _toAssetListElement(AssetEntry assetEntry) {
		String className = assetEntry.getClassName();

		Optional<AssetEntryToDTOConverter> assetEntryToDTOConverterOptional =
			_assetEntryToDTOConverterRegistry.getAssetEntryToDTOConverter(
				className);

		return new ContentListElement() {
			{
				contentType = className;
				content = assetEntryToDTOConverterOptional.map(
					assetEntryToDTOConverter -> assetEntryToDTOConverter.toDTO(
						assetEntry, contextAcceptLanguage, contextUriInfo)
				).orElse(
					null
				);
				order = assetEntry.getPriority();
				title = assetEntry.getTitle(
					contextAcceptLanguage.getPreferredLocale());
			}
		};
	}

	@Reference
	private AssetEntryToDTOConverterRegistry _assetEntryToDTOConverterRegistry;

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Context
	private HttpServletRequest _contextHttpServletRequest;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}