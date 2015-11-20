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

package com.liferay.wiki.convert;

import com.liferay.portal.convert.DLStoreConvertProcess;
import com.liferay.portal.convert.DLStoreConverter;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(service = DLStoreConvertProcess.class)
public class WikiDLStoreConvertProcess implements DLStoreConvertProcess {

	@Override
	public void migrate(final DLStoreConverter dlStoreConverter)
		throws PortalException {

		int count = _wikiPageLocalService.getWikiPagesCount();

		MaintenanceUtil.appendStatus(
			"Migrating wiki page attachments in " + count + " pages");

		ActionableDynamicQuery actionableDynamicQuery =
			_wikiPageLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName("head");

					dynamicQuery.add(property.eq(true));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<WikiPage>() {

				@Override
				public void performAction(WikiPage wikiPage)
					throws PortalException {

					for (FileEntry fileEntry :
							wikiPage.getAttachmentsFileEntries()) {

						dlStoreConverter.migrateDLFileEntry(
							wikiPage.getCompanyId(),
							DLFolderConstants.getDataRepositoryId(
								fileEntry.getRepositoryId(),
								fileEntry.getFolderId()),
							fileEntry);
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Reference(unbind ="-")
	public void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	private volatile WikiPageLocalService _wikiPageLocalService;

}