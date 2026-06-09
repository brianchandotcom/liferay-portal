/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.headless.delivery.dto.v1_0.ContentSet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(
	property = "dto.class.name=com.liferay.asset.list.model.AssetListEntry",
	service = DTOConverter.class
)
public class ContentSetDTOConverter
	implements DTOConverter<AssetListEntry, ContentSet> {

	@Override
	public String getContentType() {
		return ContentSet.class.getSimpleName();
	}

	@Override
	public ContentSet toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		return toDTO(
			dtoConverterContext,
			_assetListEntryLocalService.getAssetListEntry(
				(Long)dtoConverterContext.getId()));
	}

	@Override
	public ContentSet toDTO(
			DTOConverterContext dtoConverterContext,
			AssetListEntry assetListEntry)
		throws Exception {

		return new ContentSet() {
			{
				setClassNameId(
					() -> _portal.getClassNameId(AssetListEntry.class));
				setClassPK(assetListEntry::getAssetListEntryId);
				setContentSetId(assetListEntry::getAssetListEntryId);
				setDateCreated(assetListEntry::getCreateDate);
				setDateModified(assetListEntry::getModifiedDate);
				setExternalReferenceCode(
					assetListEntry::getExternalReferenceCode);
				setItemSubtype(assetListEntry::getAssetEntrySubtype);
				setItemType(assetListEntry::getAssetEntryType);
				setTitle(assetListEntry::getTitle);
			}
		};
	}

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private Portal _portal;

}