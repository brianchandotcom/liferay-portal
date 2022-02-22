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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
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
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFormVariationsProvider.class)
public class JournalArticleInfoItemFormVariationsProvider
	implements InfoItemFormVariationsProvider<JournalArticle> {

	@Override
	public InfoItemFormVariation getInfoItemFormVariation(
		long groupId, String formVariationKey) {

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			GetterUtil.getLong(formVariationKey));

		if (ddmStructure == null) {
			ddmStructure = _ddmStructureLocalService.fetchStructure(
				groupId, _portal.getClassNameId(JournalArticle.class.getName()),
				formVariationKey);
		}

		if (ddmStructure == null) {
			return null;
		}

		return new InfoItemFormVariation(
			groupId, String.valueOf(ddmStructure.getStructureId()),
			InfoLocalizedValue.<String>builder(
			).values(
				ddmStructure.getNameMap()
			).build());
	}

	@Override
	public Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long groupId) {

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

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getStructures(
				groupIds,
				_portal.getClassNameId(JournalArticle.class.getName()));

		for (DDMStructure ddmStructure : ddmStructures) {
			infoItemFormVariations.add(
				new InfoItemFormVariation(
					ddmStructure.getGroupId(),
					String.valueOf(ddmStructure.getStructureId()),
					InfoLocalizedValue.<String>builder(
					).values(
						ddmStructure.getNameMap()
					).build()));
		}

		return infoItemFormVariations;
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

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private Portal _portal;

}