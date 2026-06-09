/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.headless.delivery.dto.v1_0.ContentSet;
import com.liferay.headless.delivery.resource.v1_0.ContentSetResource;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Luis Ortiz
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-set.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentSetResource.class
)
public class ContentSetResourceImpl extends BaseContentSetResourceImpl {

	@Override
	public Page<ContentSet> getAssetLibraryContentSetsPage(
			Long assetLibraryId, String itemSubtype, String itemType,
			String search, Pagination pagination)
		throws Exception {

		return _getContentSetsPage(
			assetLibraryId, itemSubtype, itemType, search, pagination);
	}

	@Override
	public Page<ContentSet> getSiteContentSetsPage(
			Long siteId, String itemSubtype, String itemType, String search,
			Pagination pagination)
		throws Exception {

		return _getContentSetsPage(
			siteId, itemSubtype, itemType, search, pagination);
	}

	private Page<ContentSet> _getContentSetsPage(
			Long groupId, String itemSubtype, String itemType, String search,
			Pagination pagination)
		throws Exception {

		long[] groupIds = {groupId};

		int end = pagination.getEndPosition();
		int start = pagination.getStartPosition();

		OrderByComparator<AssetListEntry> orderByComparator =
			OrderByComparatorFactoryUtil.create(
				"AssetListEntry", "createDate", false);

		List<AssetListEntry> assetListEntries = null;
		int totalCount = 0;

		if (Validator.isNotNull(itemSubtype) && Validator.isNotNull(itemType)) {
			if (Validator.isNotNull(search)) {
				assetListEntries = _assetListEntryService.getAssetListEntries(
					groupIds, search, itemSubtype, itemType, start, end,
					orderByComparator);
				totalCount = _assetListEntryService.getAssetListEntriesCount(
					groupIds, search, itemSubtype, itemType);
			}
			else {
				assetListEntries = _assetListEntryService.getAssetListEntries(
					groupIds, itemSubtype, itemType, start, end,
					orderByComparator);
				totalCount = _assetListEntryService.getAssetListEntriesCount(
					groupIds, itemSubtype, itemType);
			}
		}
		else if (Validator.isNotNull(itemType)) {
			String[] assetEntryTypes = {itemType};

			if (Validator.isNotNull(search)) {
				assetListEntries = _assetListEntryService.getAssetListEntries(
					groupIds, search, assetEntryTypes, start, end,
					orderByComparator);
				totalCount = _assetListEntryService.getAssetListEntriesCount(
					groupIds, search, assetEntryTypes);
			}
			else {
				assetListEntries = _assetListEntryService.getAssetListEntries(
					groupIds, assetEntryTypes, start, end, orderByComparator);
				totalCount = _assetListEntryService.getAssetListEntriesCount(
					groupIds, assetEntryTypes);
			}
		}
		else if (Validator.isNotNull(search)) {
			assetListEntries = _assetListEntryService.getAssetListEntries(
				groupIds, search, start, end, orderByComparator);
			totalCount = _assetListEntryService.getAssetListEntriesCount(
				groupIds, search);
		}
		else {
			assetListEntries = _assetListEntryService.getAssetListEntries(
				groupIds, start, end, orderByComparator);
			totalCount = _assetListEntryService.getAssetListEntriesCount(
				groupIds);
		}

		return Page.of(
			TransformUtil.transform(assetListEntries, this::_toContentSet),
			pagination, totalCount);
	}

	private ContentSet _toContentSet(AssetListEntry assetListEntry)
		throws Exception {

		return _contentSetDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				null, assetListEntry.getAssetListEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			assetListEntry);
	}

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference(
		target = "(component.name=com.liferay.headless.delivery.internal.dto.v1_0.converter.ContentSetDTOConverter)"
	)
	private DTOConverter<AssetListEntry, ContentSet> _contentSetDTOConverter;

}