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

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class RatingsEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<RatingsEntry> {

	public static final String[] CLASS_NAMES = {RatingsEntry.class.getName()};

	@Override
	public void deleteStagedModel(RatingsEntry ratingsEntry) {
		RatingsEntryLocalServiceUtil.deleteRatingsEntry(ratingsEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		RatingsEntry entry =
			RatingsEntryLocalServiceUtil.fetchRatingsEntryByUuidAndCompanyId(
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
			RatingsEntryLocalServiceUtil.fetchRatingsEntryByUuidAndCompanyId(
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

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			entry);

		RatingsEntry importedEntry = RatingsEntryLocalServiceUtil.updateEntry(
			userId, entry.getClassName(), newClassPK, entry.getScore(),
			serviceContext);

		portletDataContext.importClassedModel(entry, importedEntry);
	}

}