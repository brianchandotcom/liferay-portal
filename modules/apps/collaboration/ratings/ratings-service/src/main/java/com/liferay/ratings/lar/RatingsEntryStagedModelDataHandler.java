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

package com.liferay.ratings.lar;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class RatingsEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<RatingsEntry> {

	public static final String[] CLASS_NAMES = {RatingsEntry.class.getName()};

	@Override
	public void deleteStagedModel(RatingsEntry ratingsEntry) {
		_ratingsEntryLocalService.deleteRatingsEntry(ratingsEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		RatingsEntry entry =
			_ratingsEntryLocalService.fetchRatingsEntryByUuidAndCompanyId(
				uuid, group.getCompanyId());

		if (entry != null) {
			deleteStagedModel(entry);
		}
	}

	@Override
	public List<RatingsEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<RatingsEntry> ratingsEntries = new ArrayList<>();

		ratingsEntries.add(
			_ratingsEntryLocalService.fetchRatingsEntryByUuidAndCompanyId(
				uuid, companyId));

		return ratingsEntries;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(RatingsEntry entry) {
		return entry.getUuid();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, RatingsEntry entry)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(entry);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(entry), entry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, RatingsEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		Map<Long, Long> relatedClassPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				entry.getClassName());

		long newClassPK = MapUtil.getLong(
			relatedClassPKs, entry.getClassPK(), entry.getClassPK());

		try {
			PersistedModelLocalService persistedModelLocalService =
				PersistedModelLocalServiceRegistryUtil.
					getPersistedModelLocalService(entry.getClassName());

			persistedModelLocalService.getPersistedModel(newClassPK);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to import ratings entry with id " +
						entry.getEntryId());
			}

			return;
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			entry);

		RatingsEntry importedEntry = _ratingsEntryLocalService.updateEntry(
			userId, entry.getClassName(), newClassPK, entry.getScore(),
			serviceContext);

		portletDataContext.importClassedModel(entry, importedEntry);
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setRatingsEntryLocalService(
		RatingsEntryLocalService ratingsEntryLocalService) {

		_ratingsEntryLocalService = ratingsEntryLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RatingsEntryStagedModelDataHandler.class);

	private GroupLocalService _groupLocalService;
	private RatingsEntryLocalService _ratingsEntryLocalService;

}