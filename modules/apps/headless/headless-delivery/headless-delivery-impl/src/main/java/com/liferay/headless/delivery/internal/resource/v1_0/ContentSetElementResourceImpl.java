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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.headless.delivery.dto.v1_0.ContentSetElement;
import com.liferay.headless.delivery.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.delivery.dto.v1_0.converter.DefaultDTOConverterContext;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.DTOConverterRegistry;
import com.liferay.headless.delivery.resource.v1_0.ContentSetElementResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.segments.provider.SegmentsEntryProvider;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-set-element.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentSetElementResource.class
)
public class ContentSetElementResourceImpl
	extends BaseContentSetElementResourceImpl {

	@Override
	public Page<ContentSetElement> getContentSetContentSetElementsPage(
			Long contentSetId, Pagination pagination)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryService.getAssetListEntry(contentSetId);

		return _getContentSetContentSetElementsPage(assetListEntry, pagination);
	}

	@Override
	public Page<ContentSetElement>
			getContentSpaceContentSetByKeyContentSetElementsPage(
				Long contentSpaceId, String key, Pagination pagination)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryService.getAssetListEntry(contentSpaceId, key);

		return _getContentSetContentSetElementsPage(assetListEntry, pagination);
	}

	@Override
	public Page<ContentSetElement>
			getContentSpaceContentSetByUuidContentSetElementsPage(
				Long contentSpaceId, String uuid, Pagination pagination)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryService.getAssetListEntryByUuidAndGroupId(
				uuid, contentSpaceId);

		return _getContentSetContentSetElementsPage(assetListEntry, pagination);
	}

	private Page<ContentSetElement> _getContentSetContentSetElementsPage(
			AssetListEntry assetListEntry, Pagination pagination)
		throws Exception {

		long[] segmentsEntryIds = _segmentsEntryProvider.getSegmentsEntryIds(
			assetListEntry.getGroupId(), _user.getModelClassName(),
			_user.getPrimaryKey());

		return Page.of(
			transform(
				assetListEntry.getAssetEntries(
					segmentsEntryIds, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toContentSetElement),
			pagination, assetListEntry.getAssetEntriesCount(segmentsEntryIds));
	}

	private ContentSetElement _toContentSetElement(AssetEntry assetEntry) {
		return new ContentSetElement() {
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

	@Reference
	private SegmentsEntryProvider _segmentsEntryProvider;

	@Context
	private User _user;

}