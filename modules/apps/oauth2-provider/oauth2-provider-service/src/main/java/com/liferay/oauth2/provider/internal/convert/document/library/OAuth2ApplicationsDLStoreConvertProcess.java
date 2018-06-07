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

package com.liferay.oauth2.provider.internal.convert.document.library;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.convert.documentlibrary.DLStoreConverter;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.util.MaintenanceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DLStoreConvertProcess.class)
public class OAuth2ApplicationsDLStoreConvertProcess
	implements DLStoreConvertProcess {

	@Override
	public void migrate(final DLStoreConverter dlStoreConverter)
		throws PortalException {

		int count = _oAuth2ApplicationLocalService.getOAuth2ApplicationsCount();

		MaintenanceUtil.appendStatus(
			"Migrating attachments in " + count + " OAuth2Applications");

		ActionableDynamicQuery actionableDynamicQuery =
			_oAuth2ApplicationLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<OAuth2Application>() {

				@Override
				public void performAction(OAuth2Application oAuth2Application)
					throws PortalException {

					long iconFileEntryId =
						oAuth2Application.getIconFileEntryId();

					if (iconFileEntryId == 0) {
						return;
					}

					FileEntry iconFileEntry =
						PortletFileRepositoryUtil.getPortletFileEntry(
							iconFileEntryId);

					dlStoreConverter.migrateDLFileEntry(
						oAuth2Application.getCompanyId(),
						DLFolderConstants.getDataRepositoryId(
							iconFileEntry.getRepositoryId(),
							iconFileEntry.getFolderId()),
						iconFileEntry);
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

}