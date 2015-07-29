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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.repository.cmis.CMISRepositoryUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.ObjectIdImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

/**
 * @author Alexander Chow
 * @author Edward Han
 */
public class CMISStore extends BaseStore {

	public CMISStore() {
		Folder systemRootDir = getFolder(
			SessionHolder.session.getRootFolder(),
			PropsValues.DL_STORE_CMIS_SYSTEM_ROOT_DIR);

		if (systemRootDir == null) {
			systemRootDir = createFolder(
				SessionHolder.session.getRootFolder(),
				PropsValues.DL_STORE_CMIS_SYSTEM_ROOT_DIR);
		}

		_systemRootDir = systemRootDir;
	}

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {

		Folder folder = getRepositoryFolder(companyId, repositoryId);

		String[] dirNames = StringUtil.split(dirName, CharPool.SLASH);

		for (String curDirName : dirNames) {
			Folder subFolder = getFolder(folder, curDirName);

			if (subFolder == null) {
				subFolder = createFolder(folder, curDirName);
			}

			folder = subFolder;
		}
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws DuplicateFileException {

		updateFile(companyId, repositoryId, fileName, VERSION_DEFAULT, is);
	}

	@Override
	public void checkRoot(long companyId) {
	}

	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws DuplicateFileException, NoSuchFileException {

		Folder versioningFolder = getVersioningFolder(
			companyId, repositoryId, fileName, false);

		if (versioningFolder == null) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, fromVersionLabel);
		}

		if (hasFile(companyId, repositoryId, fileName, toVersionLabel)) {
			throw new DuplicateFileException(
				companyId, repositoryId, fileName, toVersionLabel);
		}

		ObjectId versioningFolderObjectId = new ObjectIdImpl(
			versioningFolder.getId());

		Map<String, Object> documentProperties = new HashMap<>();

		documentProperties.put(PropertyIds.NAME, toVersionLabel);

		documentProperties.put(
			PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_DOCUMENT.value());

		Document document = getVersionedDocument(
			companyId, repositoryId, fileName, fromVersionLabel);

		document.copy(
			versioningFolderObjectId, documentProperties, null,
			document.getPolicies(), null, null, null);
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		Folder repositoryFolder = getRepositoryFolder(companyId, repositoryId);

		Folder directory = getFolder(repositoryFolder, dirName);

		if (directory != null) {
			directory.deleteTree(true, UnfileObject.DELETE, false);
		}
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		Folder versioningFolder = getVersioningFolder(
			companyId, repositoryId, fileName, false);

		if (versioningFolder == null) {
			logFailedDeletion(companyId, repositoryId, fileName);
		}
		else {
			versioningFolder.deleteTree(true, UnfileObject.DELETE, false);
		}
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Document document = null;

		try {
			document = getVersionedDocument(
				companyId, repositoryId, fileName, versionLabel);

			document.delete(true);
		}
		catch (NoSuchFileException nsfe) {
			logFailedDeletion(
				companyId, repositoryId, fileName, versionLabel, nsfe);
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

		if (Validator.isNull(versionLabel)) {
			versionLabel = getHeadVersionLabel(
				companyId, repositoryId, fileName);
		}

		Document document = getVersionedDocument(
			companyId, repositoryId, fileName, versionLabel);

		return document.getContentStream().getStream();
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		Folder folder = getRepositoryFolder(companyId, repositoryId);

		List<String> fileNames = new ArrayList<>();

		doGetFileNames(fileNames, StringPool.BLANK, folder);

		return fileNames.toArray(new String[fileNames.size()]);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		Folder directory = getRepositoryFolder(companyId, repositoryId);

		String[] subFolderNames = StringUtil.split(dirName, StringPool.SLASH);

		for (String subFolderName : subFolderNames) {
			directory = getFolder(directory, subFolderName);

			if (directory == null) {
				return new String[0];
			}
		}

		List<String> fileNames = new ArrayList<>();

		doGetFileNames(fileNames, dirName, directory);

		return fileNames.toArray(new String[fileNames.size()]);
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws NoSuchFileException {

		String versionLabel = getHeadVersionLabel(
			companyId, repositoryId, fileName);

		Document document = getVersionedDocument(
			companyId, repositoryId, fileName, versionLabel);

		return document.getContentStreamLength();
	}

	public String getHeadVersionLabel(
			long companyId, long repositoryId, String dirName)
		throws NoSuchFileException {

		Folder versioningFolder = getVersioningFolder(
			companyId, repositoryId, dirName, false);

		if (versioningFolder == null) {
			throw new NoSuchFileException(companyId, repositoryId, dirName);
		}

		String headVersionLabel = VERSION_DEFAULT;

		List<String> versionLabels = getVersionLabels(versioningFolder);

		for (String versionLabel : versionLabels) {
			if (DLUtil.compareVersions(versionLabel, headVersionLabel) > 0) {
				headVersionLabel = versionLabel;
			}
		}

		return headVersionLabel;
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		Folder folder = getRepositoryFolder(companyId, repositoryId);

		String[] dirNames = StringUtil.split(dirName, CharPool.SLASH);

		for (String subdirName : dirNames) {
			Folder subfolder = getFolder(folder, subdirName);

			if (subfolder == null) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Folder versioningFolder = getVersioningFolder(
			companyId, repositoryId, fileName, true);

		Document document = getDocument(versioningFolder, versionLabel);

		if (document == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws DuplicateFileException, NoSuchFileException {

		Folder oldVersioningFolderEntry = getVersioningFolder(
			companyId, repositoryId, fileName, false);

		if (oldVersioningFolderEntry == null) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}

		if (hasFile(companyId, newRepositoryId, fileName)) {
			throw new DuplicateFileException(
				companyId, newRepositoryId, fileName);
		}

		Folder newVersioningFolderEntry = getVersioningFolder(
			companyId, newRepositoryId, fileName, true);

		List<String> versionLabels = getVersionLabels(oldVersioningFolderEntry);

		for (String versionLabel : versionLabels) {
			Document document = getDocument(
				oldVersioningFolderEntry, versionLabel);

			InputStream is = document.getContentStream().getStream();

			createDocument(newVersioningFolderEntry, versionLabel, is);
		}

		oldVersioningFolderEntry.deleteTree(true, UnfileObject.DELETE, false);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws DuplicateFileException, NoSuchFileException {

		Folder oldVersioningFolderEntry = getVersioningFolder(
			companyId, repositoryId, fileName, false);

		if (oldVersioningFolderEntry == null) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}

		if (hasFile(companyId, repositoryId, newFileName)) {
			throw new DuplicateFileException(
				companyId, repositoryId, newFileName);
		}

		Folder newVersioningFolderEntry = getVersioningFolder(
			companyId, repositoryId, newFileName, true);

		List<String> versionLabels = getVersionLabels(oldVersioningFolderEntry);

		for (String versionLabel : versionLabels) {
			Document document = getDocument(
				oldVersioningFolderEntry, versionLabel);

			InputStream is = document.getContentStream().getStream();

			createDocument(newVersioningFolderEntry, versionLabel, is);
		}

		oldVersioningFolderEntry.deleteTree(true, UnfileObject.DELETE, false);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws DuplicateFileException {

		Folder versioningFolder = getVersioningFolder(
			companyId, repositoryId, fileName, true);

		String title = String.valueOf(versionLabel);

		Document document = getDocument(versioningFolder, title);

		if (document != null) {
			throw new DuplicateFileException(
				companyId, repositoryId, fileName, versionLabel);
		}

		createDocument(versioningFolder, title, is);
	}

	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws DuplicateFileException, NoSuchFileException {

		Folder versioningFolder = getVersioningFolder(
			companyId, repositoryId, fileName, false);

		if (versioningFolder == null) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, fromVersionLabel);
		}

		Document document = getDocument(versioningFolder, toVersionLabel);

		if (document != null) {
			throw new DuplicateFileException(
				companyId, repositoryId, fileName, toVersionLabel);
		}

		document = getVersionedDocument(
			companyId, repositoryId, fileName, fromVersionLabel);

		Map<String, Object> documentProperties = new HashMap<>();

		documentProperties.put(PropertyIds.NAME, toVersionLabel);

		document.updateProperties(documentProperties);
	}

	protected Document createDocument(
		Folder versioningFolder, String title, InputStream is) {

		Map<String, Object> documentProperties = new HashMap<>();

		documentProperties.put(PropertyIds.NAME, title);
		documentProperties.put(
			PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_DOCUMENT.value());

		ContentStream contentStream = new ContentStreamImpl(
			null, null, ContentTypes.APPLICATION_OCTET_STREAM, is);

		return versioningFolder.createDocument(
			documentProperties, contentStream, null);
	}

	protected Folder createFolder(ObjectId parentFolderId, String name) {
		Map<String, Object> properties = new HashMap<>();

		properties.put(PropertyIds.NAME, name);
		properties.put(
			PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_FOLDER.value());

		ObjectId objectId = SessionHolder.session.createFolder(
			properties, parentFolderId);

		return (Folder)SessionHolder.session.getObject(objectId);
	}

	protected void doGetFileNames(
		List<String> fileNames, String dirName, Folder folder) {

		List<Folder> folders = getFolders(folder);

		if (ListUtil.isNotEmpty(folders)) {
			for (Folder curFolder : folders) {
				String subDirName = null;

				if (Validator.isBlank(dirName)) {
					subDirName = curFolder.getName();
				}
				else {
					subDirName =
						dirName + StringPool.SLASH + curFolder.getName();
				}

				doGetFileNames(fileNames, subDirName, curFolder);
			}
		}
		else {
			fileNames.add(dirName);
		}
	}

	protected Folder getCompanyFolder(long companyId) {
		String name = String.valueOf(companyId);

		Folder companyFolder = getFolder(_systemRootDir, name);

		if (companyFolder == null) {
			companyFolder = createFolder(_systemRootDir, name);
		}

		return companyFolder;
	}

	protected Document getDocument(Folder parentFolder, String name) {
		ItemIterable<CmisObject> cmisObjects = parentFolder.getChildren();

		for (CmisObject cmisObject : cmisObjects) {
			if (name.equals(cmisObject.getName()) &&
				cmisObject instanceof Document) {

				return (Document)cmisObject;
			}
		}

		return null;
	}

	protected Folder getFolder(Folder parentFolder, String name) {
		ItemIterable<CmisObject> cmisObjects = parentFolder.getChildren();

		for (CmisObject cmisObject : cmisObjects) {
			if (name.equals(cmisObject.getName()) &&
				cmisObject instanceof Folder) {

				return (Folder)cmisObject;
			}
		}

		return null;
	}

	protected List<Folder> getFolders(Folder folder) {
		List<Folder> folders = new ArrayList<>();

		ItemIterable<CmisObject> cmisObjects = folder.getChildren();

		for (CmisObject cmisObject : cmisObjects) {
			if (cmisObject instanceof Folder) {
				folders.add((Folder)cmisObject);
			}
		}

		return folders;
	}

	protected Folder getRepositoryFolder(long companyId, long repositoryId) {
		Folder companyFolder = getCompanyFolder(companyId);

		String name = String.valueOf(repositoryId);

		Folder repositoryFolder = getFolder(companyFolder, name);

		if (repositoryFolder == null) {
			repositoryFolder = createFolder(companyFolder, name);
		}

		return repositoryFolder;
	}

	protected Document getVersionedDocument(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

		Folder versioningFolder = getVersioningFolder(
			companyId, repositoryId, fileName, false);

		if (versioningFolder == null) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}

		Document document = getDocument(versioningFolder, versionLabel);

		if (document == null) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel);
		}

		return document;
	}

	protected Folder getVersioningFolder(
		long companyId, long repositoryId, String fileName, boolean create) {

		Folder repositoryFolder = getRepositoryFolder(companyId, repositoryId);

		Folder versioningFolder = repositoryFolder;

		String[] dirNames = StringUtil.split(fileName, CharPool.SLASH);

		for (String dirName : dirNames) {
			Folder subFolder = getFolder(versioningFolder, dirName);

			if (create && (subFolder == null)) {
				subFolder = createFolder(versioningFolder, dirName);
			}

			versioningFolder = subFolder;
		}

		return versioningFolder;
	}

	protected List<String> getVersionLabels(Folder versioningFolder) {
		List<String> versions = new ArrayList<>();

		ItemIterable<CmisObject> cmisObjects = versioningFolder.getChildren();

		for (CmisObject cmisObject : cmisObjects) {
			if (cmisObject instanceof Document) {
				versions.add(cmisObject.getName());
			}
		}

		return versions;
	}

	private final Folder _systemRootDir;

	private static class SessionHolder {

		private static final Session session;

		static {
			Map<String, String> parameters = new HashMap<>();

			parameters.put(
				SessionParameter.ATOMPUB_URL,
				PropsValues.DL_STORE_CMIS_REPOSITORY_URL);
			parameters.put(
				SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
			parameters.put(
				SessionParameter.COMPRESSION, Boolean.TRUE.toString());

			Locale locale = LocaleUtil.getDefault();

			parameters.put(
				SessionParameter.LOCALE_ISO3166_COUNTRY, locale.getCountry());
			parameters.put(
				SessionParameter.LOCALE_ISO639_LANGUAGE, locale.getLanguage());
			parameters.put(
				SessionParameter.PASSWORD,
				PropsValues.DL_STORE_CMIS_CREDENTIALS_PASSWORD);
			parameters.put(
				SessionParameter.USER,
				PropsValues.DL_STORE_CMIS_CREDENTIALS_USERNAME);

			SessionFactory sessionFactory =
				CMISRepositoryUtil.getSessionFactory();

			List<Repository> repositories = sessionFactory.getRepositories(
				parameters);

			Repository repository = repositories.get(0);

			session = repository.createSession();

			session.setDefaultContext(CMISRepositoryUtil.getOperationContext());
		}

	}

}