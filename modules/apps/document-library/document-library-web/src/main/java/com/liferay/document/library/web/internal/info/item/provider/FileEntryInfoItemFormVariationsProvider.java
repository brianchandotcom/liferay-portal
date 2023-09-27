/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.info.item.provider;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
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
@Component(service = InfoItemFormVariationsProvider.class)
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
			).defaultLocale(
				LocaleUtil.fromLanguageId(
					dlFileEntryType.getDefaultLanguageId())
			).values(
				dlFileEntryType.getNameMap()
			).build());
	}

	@Override
	public Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long groupId) {

		List<InfoItemFormVariation> infoItemFormVariations = new ArrayList<>();

		infoItemFormVariations.add(_getBasicDocumentInfoItemFormVariation());

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

		infoItemFormVariations.add(_getBasicDocumentInfoItemFormVariation());

		for (DLFileEntryType dlFileEntryType :
				_dlFileEntryTypeLocalService.getFileEntryTypes(groupIds)) {

			infoItemFormVariations.add(
				new InfoItemFormVariation(
					dlFileEntryType.getGroupId(),
					String.valueOf(dlFileEntryType.getFileEntryTypeId()),
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							dlFileEntryType.getDefaultLanguageId())
					).values(
						dlFileEntryType.getNameMap()
					).build()));
		}

		return infoItemFormVariations;
	}

	private InfoItemFormVariation _getBasicDocumentInfoItemFormVariation() {
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

		DepotEntryLocalService depotEntryLocalService =
			_depotEntryLocalServiceSnapshot.get();

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

	private static final Snapshot<DepotEntryLocalService>
		_depotEntryLocalServiceSnapshot = new Snapshot<>(
			FileEntryInfoItemFormVariationsProvider.class,
			DepotEntryLocalService.class, null, true);

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private Portal _portal;

}