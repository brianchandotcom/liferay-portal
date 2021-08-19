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

package com.liferay.document.library.web.internal.info.item.provider;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adolfo Pérez
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFormVariationsProvider.class)
public class FileEntryInfoItemFormVariationsProvider
	implements InfoItemFormVariationsProvider<FileEntry> {

	@Override
	public InfoItemFormVariation getInfoItemFormVariation(
		long groupId, String formVariationKey) {

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(
				GetterUtil.getLong(formVariationKey));

		if (dlFileEntryType == null) {
			return null;
		}

		return new InfoItemFormVariation(
			groupId, String.valueOf(dlFileEntryType.getFileEntryTypeId()),
			InfoLocalizedValue.<String>builder(
			).values(
				dlFileEntryType.getNameMap()
			).build());
	}

	@Override
	public Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long groupId) {

		List<InfoItemFormVariation> infoItemFormVariations = new ArrayList<>();

		infoItemFormVariations.add(getBasicDocumentInfoItemFormVariation());

		try {
			return getInfoItemFormVariations(
				_getCurrentAndAncestorSiteGroupIds(groupId));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"An unexpected error occurred", portalException);
		}
	}

	@Override
	public Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long[] groupIds) {

		List<InfoItemFormVariation> infoItemFormVariations = new ArrayList<>();

		infoItemFormVariations.add(getBasicDocumentInfoItemFormVariation());

		List<DLFileEntryType> dlFileEntryTypes =
			_dlFileEntryTypeLocalService.getFileEntryTypes(groupIds);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			infoItemFormVariations.add(
				new InfoItemFormVariation(
					dlFileEntryType.getGroupId(),
					String.valueOf(dlFileEntryType.getFileEntryTypeId()),
					InfoLocalizedValue.<String>builder(
					).values(
						dlFileEntryType.getNameMap()
					).build()));
		}

		return infoItemFormVariations;
	}

	protected InfoItemFormVariation getBasicDocumentInfoItemFormVariation() {
		DLFileEntryType basicDocumentDLFileEntryType =
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		return new InfoItemFormVariation(
			basicDocumentDLFileEntryType.getGroupId(),
			String.valueOf(basicDocumentDLFileEntryType.getFileEntryTypeId()),
			InfoLocalizedValue.localize(
				FileEntryInfoItemFormVariationsProvider.class,
				DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT));
	}

	private long[] _getCurrentAndAncestorSiteGroupIds(long groupId)
		throws PortalException {

		DepotEntryLocalService depotEntryLocalService = _depotEntryLocalService;

		if (depotEntryLocalService == null) {
			return _portal.getCurrentAndAncestorSiteGroupIds(groupId);
		}

		return ArrayUtil.append(
			_portal.getCurrentAndAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				depotEntryLocalService.getGroupConnectedDepotEntries(
					groupId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFileEntryTypeService _dlFileEntryTypeService;

	@Reference
	private Portal _portal;

}