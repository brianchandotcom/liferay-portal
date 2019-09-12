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

package com.liferay.friendly.url.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.friendly.url.internal.exportimport.lar.FriendlyURLExportImportPathUtil;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.friendly.url.model.FriendlyURLEntry",
	service = StagedModelRepository.class
)
public class FriendlyURLEntryStagedModelRepository
	implements StagedModelRepository<FriendlyURLEntry> {

	@Override
	public FriendlyURLEntry addStagedModel(
			PortletDataContext portletDataContext,
			FriendlyURLEntry friendlyURLEntry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			friendlyURLEntry);

		serviceContext.setUuid(friendlyURLEntry.getUuid());

		return _friendlyURLEntryLocalService.addFriendlyURLEntry(
			friendlyURLEntry.getGroupId(), friendlyURLEntry.getClassNameId(),
			friendlyURLEntry.getClassPK(),
			_getLocalizationMap(portletDataContext, friendlyURLEntry),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(FriendlyURLEntry friendlyURLEntry) {
		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(friendlyURLEntry);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		FriendlyURLEntry friendlyURLEntry = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (friendlyURLEntry != null) {
			deleteStagedModel(friendlyURLEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext) {
		long classNameId = MapUtil.getLong(
			portletDataContext.getParameterMap(),
			"parentStagedModelClassNameId");

		_friendlyURLEntryLocalService.deleteGroupFriendlyURLEntries(
			portletDataContext.getGroupId(), classNameId);
	}

	@Override
	public FriendlyURLEntry fetchMissingReference(String uuid, long groupId) {
		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public FriendlyURLEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _friendlyURLEntryLocalService.
			fetchFriendlyURLEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<FriendlyURLEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _friendlyURLEntryLocalService.
			getFriendlyURLEntriesByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _friendlyURLEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public FriendlyURLEntry getStagedModel(long friendlyURLEntryId)
		throws PortalException {

		return _friendlyURLEntryLocalService.getFriendlyURLEntry(
			friendlyURLEntryId);
	}

	@Override
	public void restoreStagedModel(
		PortletDataContext portletDataContext, FriendlyURLEntry stagedModel) {
	}

	@Override
	public FriendlyURLEntry saveStagedModel(FriendlyURLEntry friendlyURLEntry) {
		List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations =
			_friendlyURLEntryLocalService.getFriendlyURLEntryLocalizations(
				friendlyURLEntry.getFriendlyURLEntryId());

		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
				friendlyURLEntryLocalizations) {

			friendlyURLEntryLocalization.setUrlTitle(
				_friendlyURLEntryLocalService.getUniqueUrlTitle(
					friendlyURLEntry.getGroupId(),
					friendlyURLEntry.getClassNameId(),
					friendlyURLEntry.getClassPK(),
					friendlyURLEntryLocalization.getUrlTitle()));

			_friendlyURLEntryLocalService.updateFriendlyURLLocalization(
				friendlyURLEntryLocalization);
		}

		return _friendlyURLEntryLocalService.updateFriendlyURLEntry(
			friendlyURLEntry);
	}

	@Override
	public FriendlyURLEntry updateStagedModel(
			PortletDataContext portletDataContext,
			FriendlyURLEntry friendlyURLEntry)
		throws PortalException {

		return _friendlyURLEntryLocalService.updateFriendlyURLEntry(
			friendlyURLEntry.getFriendlyURLEntryId(),
			friendlyURLEntry.getClassNameId(), friendlyURLEntry.getClassPK(),
			friendlyURLEntry.getDefaultLanguageId(),
			_getLocalizationMap(portletDataContext, friendlyURLEntry));
	}

	private Map<String, String> _getLocalizationMap(
		PortletDataContext portletDataContext,
		FriendlyURLEntry friendlyURLEntry) {

		String modelPath = FriendlyURLExportImportPathUtil.getModelPath(
			portletDataContext, friendlyURLEntry);

		Map<Locale, String> localeLocalizationMap =
			LocalizationUtil.getLocalizationMap(
				portletDataContext.getZipEntryAsString(modelPath));

		Map<String, String> languageIdLocalizationMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry :
				localeLocalizationMap.entrySet()) {

			languageIdLocalizationMap.put(
				_language.getLanguageId(entry.getKey()), entry.getValue());
		}

		return languageIdLocalizationMap;
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Language _language;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}