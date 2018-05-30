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

package com.liferay.blogs.internal.convert.document.library;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.convert.documentlibrary.DLStoreConverter;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.util.MaintenanceUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DLStoreConvertProcess.class)
public class BlogsDLStoreConvertProcess implements DLStoreConvertProcess {

	@Override
	public void migrate(final DLStoreConverter dlStoreConverter)
		throws PortalException {

		migrateBlogsEntriesFileEntries(dlStoreConverter);
		migrateBlogsServiceFileEntries(dlStoreConverter);
	}

	protected void migrateBlogsEntriesFileEntries(
			DLStoreConverter dlStoreConverter)
		throws PortalException {

		int blogsEntriesCount = _blogsEntryLocalService.getBlogsEntriesCount();

		MaintenanceUtil.appendStatus(
			"Migrating images from " + blogsEntriesCount + " blog entries");

		ActionableDynamicQuery actionableDynamicQuery =
			_blogsEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<BlogsEntry>() {

				@Override
				public void performAction(BlogsEntry entry)
					throws PortalException {

					long coverImageFileEntryId =
						entry.getCoverImageFileEntryId();

					if (coverImageFileEntryId > 0) {
						FileEntry coverImageFileEntry =
							PortletFileRepositoryUtil.getPortletFileEntry(
								coverImageFileEntryId);

						dlStoreConverter.migrateDLFileEntry(
							entry.getCompanyId(),
							DLFolderConstants.getDataRepositoryId(
								coverImageFileEntry.getRepositoryId(),
								coverImageFileEntry.getFolderId()),
							coverImageFileEntry);
					}

					long smallImageFileEntryId =
						entry.getSmallImageFileEntryId();

					if (smallImageFileEntryId > 0) {
						FileEntry smallImageFileEntry =
							PortletFileRepositoryUtil.getPortletFileEntry(
								smallImageFileEntryId);

						dlStoreConverter.migrateDLFileEntry(
							entry.getCompanyId(),
							DLFolderConstants.getDataRepositoryId(
								smallImageFileEntry.getRepositoryId(),
								smallImageFileEntry.getFolderId()),
							smallImageFileEntry);
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void migrateBlogsServiceFileEntries(
			DLStoreConverter dlStoreConverter)
		throws PortalException {

		MaintenanceUtil.appendStatus("Migrating blogs repository attachments");

		ActionableDynamicQuery actionableDynamicQuery =
			_repositoryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property portletIdProperty = PropertyFactoryUtil.forName(
						"portletId");

					dynamicQuery.add(
						portletIdProperty.eq(BlogsConstants.SERVICE_NAME));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Repository>() {

				@Override
				public void performAction(Repository repository)
					throws PortalException {

					LocalRepository localRepository =
						RepositoryProviderUtil.getLocalRepository(
							repository.getRepositoryId());

					Folder folder = null;

					try {
						folder = localRepository.getFolder(
							DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
							BlogsConstants.SERVICE_NAME);
					}
					catch (NoSuchFolderException nsfe) {
						if (_log.isWarnEnabled()) {
							_log.warn(nsfe, nsfe);
						}

						return;
					}

					List<FileEntry> fileEntries =
						PortletFileRepositoryUtil.getPortletFileEntries(
							folder.getGroupId(), folder.getFolderId());

					for (FileEntry fileEntry : fileEntries) {
						DLFileEntry dlFileEntry =
							(DLFileEntry)fileEntry.getModel();

						dlStoreConverter.migrateDLFileEntry(
							fileEntry.getCompanyId(),
							DLFolderConstants.getDataRepositoryId(
								dlFileEntry.getRepositoryId(),
								dlFileEntry.getFolderId()),
							new LiferayFileEntry(dlFileEntry));
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsDLStoreConvertProcess.class);

	@Reference(unbind = "-")
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference(unbind = "-")
	private RepositoryLocalService _repositoryLocalService;

}