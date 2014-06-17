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

package com.liferay.portal.convert;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.store.AdvancedFileSystemStore;
import com.liferay.portlet.documentlibrary.store.CMISStore;
import com.liferay.portlet.documentlibrary.store.DBStore;
import com.liferay.portlet.documentlibrary.store.FileSystemStore;
import com.liferay.portlet.documentlibrary.store.JCRStore;
import com.liferay.portlet.documentlibrary.store.S3Store;
import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.portlet.documentlibrary.util.comparator.FileVersionVersionComparator;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.InputStream;

import java.util.List;

/**
 * @author Minhchau Dang
 * @author Alexander Chow
 */
public class ConvertDocumentLibrary extends ConvertProcess {

	@Override
	public String getDescription() {
		return "migrate-documents-from-one-repository-to-another";
	}

	@Override
	public String getParameterDescription() {
		return "please-select-a-new-repository-hook";
	}

	@Override
	public String[] getParameterNames() {
		StringBundler sb = new StringBundler(_HOOKS.length * 2 + 2);

		sb.append(PropsKeys.DL_STORE_IMPL);
		sb.append(StringPool.EQUAL);

		for (String hook : _HOOKS) {
			if (!hook.equals(PropsValues.DL_STORE_IMPL)) {
				sb.append(hook);
				sb.append(StringPool.SEMICOLON);
			}
		}

		return new String[] {sb.toString()};
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	protected void doConvert() throws Exception {
		_sourceStore = StoreFactory.getInstance();

		String[] values = getParameterValues();

		String targetStoreClassName = values[0];

		_targetStore = (Store)InstanceFactory.newInstance(
			ClassLoaderUtil.getPortalClassLoader(), targetStoreClassName);

		migratePortlets();

		StoreFactory.setInstance(_targetStore);

		MaintenanceUtil.appendStatus(
			"Please set " + PropsKeys.DL_STORE_IMPL +
				" in your portal-ext.properties to use " +
					targetStoreClassName);

		PropsValues.DL_STORE_IMPL = targetStoreClassName;
	}

	protected List<DLFileVersion> getDLFileVersions(DLFileEntry dlFileEntry) {
		List<DLFileVersion> dlFileVersions = dlFileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		return ListUtil.sort(
			dlFileVersions, new FileVersionVersionComparator(true));
	}

	protected void migrateDL() throws PortalException {
		int count = DLFileEntryLocalServiceUtil.getFileEntriesCount();

		MaintenanceUtil.appendStatus(
			"Migrating " + count + " documents and media files");

		ActionableDynamicQuery actionableDynamicQuery =
			DLFileEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(classNameIdProperty.eq(0L));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws SystemException {

					DLFileEntry dlFileEntry = (DLFileEntry)object;

					migrateDLFileEntry(
						dlFileEntry.getCompanyId(),
						dlFileEntry.getDataRepositoryId(), dlFileEntry);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void migrateDLFileEntry(
		long companyId, long repositoryId, DLFileEntry dlFileEntry) {

		String fileName = dlFileEntry.getName();

		List<DLFileVersion> dlFileVersions = getDLFileVersions(dlFileEntry);

		if (dlFileVersions.isEmpty()) {
			String versionNumber = Store.VERSION_DEFAULT;

			migrateFile(companyId, repositoryId, fileName, versionNumber);

			return;
		}

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			String versionNumber = dlFileVersion.getVersion();

			migrateFile(companyId, repositoryId, fileName, versionNumber);
		}
	}

	protected void migrateFile(
		long companyId, long repositoryId, String fileName,
		String versionNumber) {

		try {
			InputStream is = _sourceStore.getFileAsStream(
				companyId, repositoryId, fileName, versionNumber);

			if (versionNumber.equals(Store.VERSION_DEFAULT)) {
				_targetStore.addFile(companyId, repositoryId, fileName, is);
			}
			else {
				_targetStore.updateFile(
					companyId, repositoryId, fileName, versionNumber, is);
			}
		}
		catch (Exception e) {
			_log.error("Migration failed for " + fileName, e);
		}
	}

	protected void migrateImages() throws PortalException {
		int count = ImageLocalServiceUtil.getImagesCount();

		MaintenanceUtil.appendStatus("Migrating " + count + " images");

		ActionableDynamicQuery actionableDynamicQuery =
			ImageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					Image image = (Image)object;

					String fileName =
						image.getImageId() + StringPool.PERIOD +
							image.getType();

					migrateFile(0, 0, fileName, Store.VERSION_DEFAULT);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void migrateMB() throws PortalException {
		int count = MBMessageLocalServiceUtil.getMBMessagesCount();

		MaintenanceUtil.appendStatus(
			"Migrating message boards attachments in " + count + " messages");

		ActionableDynamicQuery actionableDynamicQuery =
			MBMessageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					MBMessage mbMessage = (MBMessage)object;

					for (FileEntry fileEntry :
							mbMessage.getAttachmentsFileEntries()) {

						DLFileEntry dlFileEntry =
							(DLFileEntry)fileEntry.getModel();

						migrateDLFileEntry(
							mbMessage.getCompanyId(),
							DLFolderConstants.getDataRepositoryId(
								dlFileEntry.getRepositoryId(),
								dlFileEntry.getFolderId()),
							dlFileEntry);
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void migratePortlets() throws Exception {
		migrateImages();
		migrateDL();
		migrateMB();
		migrateWiki();
	}

	protected void migrateWiki() throws PortalException {
		int count = WikiPageLocalServiceUtil.getWikiPagesCount();

		MaintenanceUtil.appendStatus(
			"Migrating wiki page attachments in " + count + " pages");

		ActionableDynamicQuery actionableDynamicQuery =
			WikiPageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName("head");

					dynamicQuery.add(property.eq(true));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws SystemException {

					WikiPage wikiPage = (WikiPage)object;

					for (FileEntry fileEntry :
							wikiPage.getAttachmentsFileEntries()) {

						DLFileEntry dlFileEntry =
							(DLFileEntry)fileEntry.getModel();

						migrateDLFileEntry(
							wikiPage.getCompanyId(),
							DLFolderConstants.getDataRepositoryId(
								dlFileEntry.getRepositoryId(),
								dlFileEntry.getFolderId()),
							dlFileEntry);
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	private static final String[] _HOOKS = new String[] {
		AdvancedFileSystemStore.class.getName(), CMISStore.class.getName(),
		DBStore.class.getName(), FileSystemStore.class.getName(),
		JCRStore.class.getName(), S3Store.class.getName()
	};

	private static Log _log = LogFactoryUtil.getLog(
		ConvertDocumentLibrary.class);

	private Store _sourceStore;
	private Store _targetStore;

}