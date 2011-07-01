/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DirectoryNameException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLStoreImpl implements DLStore, IdentifiableBean {

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		if (!isValidName(dirName) || dirName.equals("/")) {
			throw new DirectoryNameException(dirName);
		}

		store.addDirectory(companyId, repositoryId, dirName);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, boolean validateFileExtension,
			ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		validate(fileName, validateFileExtension, is);

		store.addFile(
			companyId, portletId, groupId, repositoryId, fileName,
			serviceContext, is);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, ServiceContext serviceContext, byte[] bytes)
		throws PortalException, SystemException {

		validate(fileName, true, bytes);

		store.addFile(
			companyId, portletId, groupId, repositoryId, fileName,
			serviceContext, bytes);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, ServiceContext serviceContext, File file)
		throws PortalException, SystemException {

		validate(fileName, true, file);

		store.addFile(
			companyId, portletId, groupId, repositoryId, fileName,
			serviceContext, file);
	}

	public void checkRoot(long companyId) throws SystemException {
		store.checkRoot(companyId);
	}

	public void copyFileVersion(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String fromVersionNumber, String toVersionNumber,
			String sourceFileName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		store.copyFileVersion(
			companyId, portletId, groupId, repositoryId, fileName,
			fromVersionNumber, toVersionNumber, sourceFileName, serviceContext);
	}

	public void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		store.deleteDirectory(companyId, portletId, repositoryId, dirName);
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException, SystemException {

		store.deleteFile(companyId, portletId, repositoryId, fileName);
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, String versionNumber)
		throws PortalException, SystemException {

		store.deleteFile(
			companyId, portletId, repositoryId, fileName, versionNumber);
	}

	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	public byte[] getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return store.getFile(companyId, repositoryId, fileName);
	}

	public byte[] getFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return store.getFile(companyId, repositoryId, fileName, versionNumber);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return store.getFileAsStream(companyId, repositoryId, fileName);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return store.getFileAsStream(
			companyId, repositoryId, fileName, versionNumber);
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		return store.getFileNames(companyId, repositoryId, dirName);
	}

	public long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return store.getFileSize(companyId, repositoryId, fileName);
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return store.hasFile(companyId, repositoryId, fileName, versionNumber);
	}

	public void move(String srcDir, String destDir) throws SystemException {
		store.move(srcDir, destDir);
	}

	public Hits search(
			long companyId, String portletId, long groupId,
			long userId, long[] repositoryIds, String keywords, int start,
			int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(Field.PORTLET_ID, portletId);

			if (groupId > 0) {
				Group group = groupLocalService.getGroup(groupId);

				if (group.isLayout()) {
					contextQuery.addRequiredTerm(Field.SCOPE_GROUP_ID, groupId);

					groupId = group.getParentGroupId();
				}

				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if ((repositoryIds != null) && (repositoryIds.length > 0)) {
				BooleanQuery repositoryIdsQuery =
					BooleanQueryFactoryUtil.create();

				for (long repositoryId : repositoryIds) {
					try {
						if (userId > 0) {
							PermissionChecker permissionChecker =
								PermissionThreadLocal.getPermissionChecker();

							DLFolderPermission.check(
								permissionChecker, groupId, repositoryId,
								ActionKeys.VIEW);
						}

						if (repositoryId ==
								DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

							repositoryId = groupId;
						}

						TermQuery termQuery = TermQueryFactoryUtil.create(
							"repositoryId", repositoryId);

						repositoryIdsQuery.add(
							termQuery, BooleanClauseOccur.SHOULD);
					}
					catch (Exception e) {
					}
				}

				contextQuery.add(repositoryIdsQuery, BooleanClauseOccur.MUST);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			searchQuery.addTerms(_KEYWORDS_FIELDS, keywords);

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(
				companyId, new long[] {groupId}, userId,
				DLFileEntry.class.getName(), fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName)
		throws PortalException, SystemException {

		store.updateFile(
			companyId, portletId, groupId, repositoryId, newRepositoryId,
			fileName);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String newFileName)
		throws PortalException, SystemException {

		store.updateFile(
			companyId, portletId, groupId, repositoryId, fileName, newFileName);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String fileExtension,
			boolean validateFileExtension, String versionNumber,
			String sourceFileName, ServiceContext serviceContext,
			InputStream is)
		throws PortalException, SystemException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension, is);

		store.updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, serviceContext, is);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			ServiceContext serviceContext, byte[] bytes)
		throws PortalException, SystemException {

		validate(fileName, true, bytes);

		store.updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, serviceContext, bytes);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			ServiceContext serviceContext, File file)
		throws PortalException, SystemException {

		validate(fileName, true, file);

		store.updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, serviceContext, file);
	}

	public void updateFileVersion(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String fromVersionNumber, String toVersionNumber,
			String sourceFileName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		store.updateFileVersion(
			companyId, portletId, groupId, repositoryId, fileName,
			fromVersionNumber, toVersionNumber, sourceFileName, serviceContext);
	}

	public void validate(String fileName, boolean validateFileExtension)
		throws PortalException, SystemException {

		if (!isValidName(fileName)) {
			throw new FileNameException(fileName);
		}

		if (validateFileExtension) {
			boolean validFileExtension = false;

			String[] fileExtensions = PrefsPropsUtil.getStringArray(
				PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA);

			for (int i = 0; i < fileExtensions.length; i++) {
				if (StringPool.STAR.equals(fileExtensions[i]) ||
					StringUtil.endsWith(fileName, fileExtensions[i])) {

					validFileExtension = true;

					break;
				}
			}

			if (!validFileExtension) {
				throw new FileExtensionException(fileName);
			}
		}
	}

	public void validate(
			String fileName, boolean validateFileExtension, byte[] bytes)
		throws PortalException, SystemException {

		validate(fileName, validateFileExtension);

		if ((PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) > 0) &&
			((bytes == null) ||
			(bytes.length >
				 PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE)))) {

			throw new FileSizeException(fileName);
		}
	}

	public void validate(
			String fileName, boolean validateFileExtension, File file)
		throws PortalException, SystemException {

		validate(fileName, validateFileExtension);

		if ((PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) > 0) &&
			((file == null) ||
			 (file.length() >
				PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE)))) {

			throw new FileSizeException(fileName);
		}
	}

	public void validate(
			String fileName, boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		validate(fileName, validateFileExtension);

		// LEP-4851

		try {
			if ((PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) > 0) &&
				((is == null) ||
				(is.available() >
					 PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE)))) {

				throw new FileSizeException(fileName);
			}
		}
		catch (IOException ioe) {
			throw new FileSizeException(ioe.getMessage());
		}
	}

	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		String sourceFileExtension = FileUtil.getExtension(sourceFileName);

		if (Validator.isNotNull(sourceFileName) &&
			PropsValues.DL_FILE_EXTENSIONS_STRICT_CHECK &&
			!fileExtension.equals(sourceFileExtension)) {

			throw new SourceFileNameException(sourceFileExtension);
		}

		validate(fileName, validateFileExtension);

		try {
			if ((is != null) &&
				(PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) > 0) &&
				(is.available() >
					PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE))) {

				throw new FileSizeException(fileName);
			}
		}
		catch (IOException ioe) {
			throw new FileSizeException(ioe.getMessage());
		}
	}

	protected boolean isValidName(String name) {
		if ((name == null) ||
			(name.contains("\\")) ||
			(name.contains("\\\\")) ||
			(name.contains("//")) ||
			(name.contains(":")) ||
			(name.contains("*")) ||
			(name.contains("?")) ||
			(name.contains("\"")) ||
			(name.contains("<")) ||
			(name.contains(">")) ||
			(name.contains("|")) ||
			(name.contains("[")) ||
			(name.contains("]")) ||
			(name.contains("'")) ||
			(name.contains("..\\")) ||
			(name.contains("../")) ||
			(name.contains("\\..")) ||
			(name.contains("/.."))) {

			return false;
		}

		return true;
	}

	private static final String[] _KEYWORDS_FIELDS = {
		Field.ASSET_TAG_NAMES, Field.CONTENT, Field.PROPERTIES
	};

	@BeanReference(type = GroupLocalService.class)
	protected GroupLocalService groupLocalService;

	@BeanReference(type = Store.class)
	protected Store store;

	private String _beanIdentifier;

}