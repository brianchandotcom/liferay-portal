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

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFormVariationsProvider.class)
public class FileEntryInfoItemFormVariationsProvider
	implements InfoItemFormVariationsProvider<FileEntry> {

	@Override
	public Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long groupId) {

		List<InfoItemFormVariation> infoItemFormVariations = new ArrayList<>();

		infoItemFormVariations.add(getBasicDocumentInfoItemFormVariation());

		try {
			long[] groupIds = _portal.getCurrentAndAncestorSiteGroupIds(
				groupId);

			List<DLFileEntryType> dlFileEntryTypes =
				_dlFileEntryTypeLocalService.getFileEntryTypes(groupIds);

			for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
				new InfoItemFormVariation(
					String.valueOf(dlFileEntryType.getFileEntryTypeId()),
					InfoLocalizedValue.<String>builder(
					).values(
						dlFileEntryType.getNameMap()
					).build());
			}

			return infoItemFormVariations;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"An unexpected error occurred", portalException);
		}
	}

	protected InfoItemFormVariation getBasicDocumentInfoItemFormVariation() {
		DLFileEntryType basicDocumentDLFileEntryType =
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		return new InfoItemFormVariation(
			String.valueOf(basicDocumentDLFileEntryType.getFileEntryTypeId()),
			InfoLocalizedValue.localize(
				FileEntryInfoItemFormVariationsProvider.class,
				DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT));
	}

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFileEntryTypeService _dlFileEntryTypeService;

	@Reference
	private Portal _portal;

}