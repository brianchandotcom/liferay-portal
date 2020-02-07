/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.repository;

import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.exception.SourceFileNameException;
import com.liferay.document.library.repository.external.CredentialsProvider;
import com.liferay.document.library.repository.external.ExtRepository;
import com.liferay.document.library.repository.external.ExtRepositoryAdapter;
import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;
import com.liferay.document.library.repository.external.ExtRepositoryFileVersionDescriptor;
import com.liferay.document.library.repository.external.ExtRepositoryFolder;
import com.liferay.document.library.repository.external.ExtRepositoryObject;
import com.liferay.document.library.repository.external.ExtRepositoryObjectType;
import com.liferay.document.library.repository.external.ExtRepositorySearchResult;
import com.liferay.document.library.repository.external.cache.ConnectionBuilder;
import com.liferay.document.library.repository.external.cache.ConnectionCache;
import com.liferay.document.library.repository.external.search.ExtRepositoryQueryMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharepoint.connector.SharepointConnection;
import com.liferay.sharepoint.connector.SharepointConnectionFactory;
import com.liferay.sharepoint.connector.SharepointException;
import com.liferay.sharepoint.connector.SharepointObject;
import com.liferay.sharepoint.connector.SharepointRuntimeException;
import com.liferay.sharepoint.connector.SharepointVersion;
import com.liferay.sharepoint.connector.operation.PathHelper;
import com.liferay.sharepoint.connector.operation.URLHelper;
import com.liferay.sharepoint.soap.repository.model.SharepointWSFileEntry;
import com.liferay.sharepoint.soap.repository.model.SharepointWSFileVersion;
import com.liferay.sharepoint.soap.repository.model.SharepointWSFolder;
import com.liferay.sharepoint.soap.repository.model.SharepointWSObject;
import com.liferay.sharepoint.soap.repository.search.SharepointQueryBuilder;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class SharepointWSRepository
	extends ExtRepositoryAdapter
	implements ConnectionBuilder<SharepointConnection>, ExtRepository {

	public SharepointWSRepository() {
		super(null);
	}

	@Override
	public ExtRepositoryFileEntry addExtRepositoryFileEntry(
			String extRepositoryParentFolderKey, String mimeType, String title,
			String description, String changeLog, InputStream inputStream)
		throws PortalException {

		String filePath = null;

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject parentFolderSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryParentFolderKey));

			String parentFolderPath = parentFolderSharepointObject.getPath();

			filePath = pathHelper.buildPath(parentFolderPath, title);

			sharepointConnection.addFile(
				parentFolderPath, title, changeLog, inputStream);

			SharepointObject fileSharepointObject =
				sharepointConnection.getSharepointObject(filePath);

			return new SharepointWSFileEntry(fileSharepointObject);
		}
		catch (SharepointException sharepointException) {
			processSharepointObjectException(
				sharepointException, false, filePath, title);

			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public ExtRepositoryFolder addExtRepositoryFolder(
			String extRepositoryParentFolderKey, String name,
			String description)
		throws PortalException {

		String folderPath = null;

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject parentFolderSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryParentFolderKey));

			String parentFolderPath = parentFolderSharepointObject.getPath();

			folderPath = pathHelper.buildPath(parentFolderPath, name);

			sharepointConnection.addFolder(parentFolderPath, name);

			SharepointObject folderSharepointObject =
				sharepointConnection.getSharepointObject(folderPath);

			return new SharepointWSFolder(folderSharepointObject);
		}
		catch (SharepointException sharepointException) {
			processSharepointObjectException(
				sharepointException, true, folderPath, name);

			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public SharepointConnection buildConnection() throws RepositoryException {
		try {
			return SharepointConnectionFactory.getInstance(
				_serverVersion, _protocol, _host, _port, _sitePath,
				_libraryName, _libraryPath, _credentialsProvider.getLogin(),
				_credentialsProvider.getPassword());
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new RepositoryException(
				"Unable to communicate with the Sharepoint server",
				sharepointRuntimeException);
		}
	}

	@Override
	public ExtRepositoryFileVersion cancelCheckOut(
		String extRepositoryFileEntryKey) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject fileSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryFileEntryKey));

			String filePath = fileSharepointObject.getPath();

			sharepointConnection.cancelCheckOutFile(filePath);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}

		return null;
	}

	@Override
	public void checkInExtRepositoryFileEntry(
		String extRepositoryFileEntryKey, boolean createMajorVersion,
		String changeLog) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject fileSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryFileEntryKey));

			String filePath = fileSharepointObject.getPath();

			SharepointConnection.CheckInType checkInType = null;

			if (createMajorVersion) {
				checkInType = SharepointConnection.CheckInType.MAJOR;
			}
			else {
				checkInType = SharepointConnection.CheckInType.MINOR;
			}

			sharepointConnection.checkInFile(filePath, changeLog, checkInType);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public ExtRepositoryFileEntry checkOutExtRepositoryFileEntry(
		String extRepositoryFileEntryKey) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject fileSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryFileEntryKey));

			String filePath = fileSharepointObject.getPath();

			sharepointConnection.checkOutFile(filePath);

			return new SharepointWSFileEntry(fileSharepointObject);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public <T extends ExtRepositoryObject> T copyExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFileEntryKey, String newExtRepositoryFolderKey,
			String newTitle)
		throws PortalException {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject fileSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryFileEntryKey));

			String filePath = fileSharepointObject.getPath();

			SharepointObject folderSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(newExtRepositoryFolderKey));

			String folderPath = folderSharepointObject.getPath();

			String newFilePath = pathHelper.buildPath(folderPath, newTitle);

			sharepointConnection.copySharepointObject(filePath, newFilePath);

			SharepointObject newSharepointObject =
				sharepointConnection.getSharepointObject(newFilePath);

			return toExtRepositoryObject(
				extRepositoryObjectType, newSharepointObject);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public void deleteExtRepositoryObject(
		ExtRepositoryObjectType<? extends ExtRepositoryObject>
			extRepositoryObjectType,
		String extRepositoryObjectKey) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject sharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryObjectKey));

			sharepointConnection.deleteSharepointObject(
				sharepointObject.getPath());
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public String getAuthType() {
		return CompanyConstants.AUTH_TYPE_SN;
	}

	@Override
	public InputStream getContentStream(
		ExtRepositoryFileEntry extRepositoryFileEntry) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointWSFileEntry sharepointWSFileEntry =
				(SharepointWSFileEntry)extRepositoryFileEntry;

			SharepointObject fileSharepointObject =
				sharepointWSFileEntry.getSharepointObject();

			return sharepointConnection.getInputStream(fileSharepointObject);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public InputStream getContentStream(
		ExtRepositoryFileVersion extRepositoryFileVersion) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointWSFileVersion sharepointWSFileVersion =
				(SharepointWSFileVersion)extRepositoryFileVersion;

			return sharepointConnection.getInputStream(
				sharepointWSFileVersion.getSharepointVersion());
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public ExtRepositoryFileVersion getExtRepositoryFileVersion(
		ExtRepositoryFileEntry extRepositoryFileEntry, String version) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointWSFileEntry sharepointWSFileEntry =
				(SharepointWSFileEntry)extRepositoryFileEntry;

			SharepointObject fileSharepointObject =
				sharepointWSFileEntry.getSharepointObject();

			String filePath = fileSharepointObject.getPath();

			List<SharepointVersion> sharepointVersions =
				sharepointConnection.getSharepointVersions(filePath);

			for (SharepointVersion sharepointVersion : sharepointVersions) {
				if (version.equals(sharepointVersion.getVersion())) {
					return new SharepointWSFileVersion(sharepointVersion);
				}
			}

			return null;
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public ExtRepositoryFileVersionDescriptor
		getExtRepositoryFileVersionDescriptor(
			String extRepositoryFileVersionKey) {

		String[] extRepositoryFileVersionKeyParts = StringUtil.split(
			extRepositoryFileVersionKey, StringPool.AT);

		String extRepositoryFileEntryKey = extRepositoryFileVersionKeyParts[0];
		String version = extRepositoryFileVersionKeyParts[1];

		return new ExtRepositoryFileVersionDescriptor(
			extRepositoryFileEntryKey, version);
	}

	@Override
	public List<ExtRepositoryFileVersion> getExtRepositoryFileVersions(
		ExtRepositoryFileEntry extRepositoryFileEntry) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointWSFileEntry sharepointWSFileEntry =
				(SharepointWSFileEntry)extRepositoryFileEntry;

			SharepointObject fileSharepointObject =
				sharepointWSFileEntry.getSharepointObject();

			String filePath = fileSharepointObject.getPath();

			List<SharepointVersion> sharepointVersions =
				sharepointConnection.getSharepointVersions(filePath);

			List<ExtRepositoryFileVersion> sharepointWSVersions =
				new ArrayList<>();

			for (SharepointVersion sharepointVersion : sharepointVersions) {
				SharepointWSFileVersion sharepointWSFileVersion =
					new SharepointWSFileVersion(sharepointVersion);

				sharepointWSVersions.add(sharepointWSFileVersion);
			}

			return sharepointWSVersions;
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey)
		throws PortalException {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject sharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryObjectKey));

			if (sharepointObject == null) {
				if (extRepositoryObjectType == ExtRepositoryObjectType.FOLDER) {
					throw new NoSuchFolderException(extRepositoryObjectKey);
				}

				throw new NoSuchFileEntryException(extRepositoryObjectKey);
			}

			return toExtRepositoryObject(
				extRepositoryObjectType, sharepointObject);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey, String title)
		throws PortalException {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject folderSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryFolderKey));

			String folderPath = folderSharepointObject.getPath();

			SharepointConnection.ObjectTypeFilter objectTypeFilter =
				toObjectTypeFilter(extRepositoryObjectType);

			List<SharepointObject> sharepointObjects =
				sharepointConnection.getSharepointObjects(
					folderPath, objectTypeFilter);

			for (SharepointObject sharepointObject : sharepointObjects) {
				if (title.equals(sharepointObject.getName())) {
					return toExtRepositoryObject(
						extRepositoryObjectType, sharepointObject);
				}
			}

			if (extRepositoryObjectType == ExtRepositoryObjectType.FOLDER) {
				throw new NoSuchFolderException(title);
			}

			throw new NoSuchFileEntryException(title);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public <T extends ExtRepositoryObject> List<T> getExtRepositoryObjects(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey)
		throws PortalException {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject folderSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryFolderKey));

			String folderPath = folderSharepointObject.getPath();

			SharepointConnection.ObjectTypeFilter objectTypeFilter =
				toObjectTypeFilter(extRepositoryObjectType);

			List<SharepointObject> sharepointObjects =
				sharepointConnection.getSharepointObjects(
					folderPath, objectTypeFilter);

			List<T> extRepositoryObjects = new ArrayList<>();

			for (SharepointObject sharepointObject : sharepointObjects) {
				T extRepositoryObject = toExtRepositoryObject(
					extRepositoryObjectType, sharepointObject);

				extRepositoryObjects.add(extRepositoryObject);
			}

			return extRepositoryObjects;
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public int getExtRepositoryObjectsCount(
		ExtRepositoryObjectType<? extends ExtRepositoryObject>
			extRepositoryObjectType,
		String extRepositoryFolderKey) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject folderSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryFolderKey));

			String folderPath = folderSharepointObject.getPath();

			SharepointConnection.ObjectTypeFilter objectTypeFilter =
				toObjectTypeFilter(extRepositoryObjectType);

			return sharepointConnection.getSharepointObjectsCount(
				folderPath, objectTypeFilter);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public ExtRepositoryFolder getExtRepositoryParentFolder(
		ExtRepositoryObject extRepositoryObject) {

		try {
			SharepointWSObject sharepointWSObject =
				(SharepointWSObject)extRepositoryObject;

			SharepointObject sharepointObject =
				sharepointWSObject.getSharepointObject();

			String parentFolderPath = sharepointObject.getFolderPath();

			if (parentFolderPath == null) {
				return null;
			}

			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject parentFolderSharepointObject =
				sharepointConnection.getSharepointObject(parentFolderPath);

			return new SharepointWSFolder(parentFolderSharepointObject);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public String getLiferayLogin(String extRepositoryLogin) {
		int index = extRepositoryLogin.lastIndexOf(StringPool.BACK_SLASH);

		String login = extRepositoryLogin.substring(index + 1);

		index = login.indexOf(",#");

		if (index == -1) {
			return login;
		}

		return login.substring(0, index);
	}

	@Override
	public String getRootFolderKey() {
		return _rootFolderKey;
	}

	public SharepointConnection getSharepointConnection()
		throws RepositoryException {

		return _connectionCache.getConnection();
	}

	public String getSharepointLogin(String liferayLogin) {
		return _host + StringPool.BACK_SLASH + liferayLogin;
	}

	@Override
	public List<String> getSubfolderKeys(
		String extRepositoryFolderKey, boolean recurse) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject folderSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryFolderKey));

			String folderPath = folderSharepointObject.getPath();

			List<String> extRepositoryFolderKeys = new ArrayList<>();

			getSubfolderKeys(folderPath, extRepositoryFolderKeys);

			return extRepositoryFolderKeys;
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
	}

	@Override
	public String[] getSupportedConfigurations() {
		return _SUPPORTED_CONFIGURATIONS;
	}

	@Override
	public String[][] getSupportedParameters() {
		return _SUPPORTED_PARAMETERS;
	}

	@Override
	public void initRepository(
		UnicodeProperties typeSettingsProperties,
		CredentialsProvider credentialsProvider) {

		try {
			_credentialsProvider = credentialsProvider;

			_libraryName = typeSettingsProperties.getProperty(_LIBRARY_NAME);

			_libraryPath = typeSettingsProperties.getProperty(_LIBRARY_PATH);

			if (Validator.isNull(_libraryPath)) {
				_libraryPath = _libraryName;
			}

			String serverVersion = typeSettingsProperties.getProperty(
				_SERVER_VERSION, StringPool.BLANK);

			if (serverVersion.equals(_SHAREPOINT_2013_VALUE)) {
				_serverVersion =
					SharepointConnection.ServerVersion.SHAREPOINT_2013;
			}
			else {
				_serverVersion =
					SharepointConnection.ServerVersion.SHAREPOINT_2010;
			}

			String siteURL = typeSettingsProperties.getProperty(_SITE_URL);

			URL url = urlHelper.toURL(siteURL);

			_host = url.getHost();
			_protocol = url.getProtocol();
			_port = getPort(url);
			_sitePath = url.getPath();

			_connectionCache = new ConnectionCache<>(
				SharepointConnection.class, getRepositoryId(), this);

			SharepointConnection sharepointConnection =
				getSharepointConnection();

			pingSharepointConnection(sharepointConnection);

			SharepointObject rootFolderSharepointObject =
				sharepointConnection.getSharepointObject(StringPool.SLASH);

			_rootFolderKey = String.valueOf(
				rootFolderSharepointObject.getSharepointObjectId());
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
	}

	@Override
	public <T extends ExtRepositoryObject> T moveExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey, String newExtRepositoryFolderKey,
			String newTitle)
		throws PortalException {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject sharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryObjectKey));

			String path = sharepointObject.getPath();

			SharepointObject folderSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(newExtRepositoryFolderKey));

			String folderPath = folderSharepointObject.getPath();

			String newPath = pathHelper.buildPath(folderPath, newTitle);

			validateExtension(path, newPath);

			if (path.equals(newPath)) {
				return toExtRepositoryObject(
					extRepositoryObjectType, sharepointObject);
			}

			sharepointConnection.moveSharepointObject(path, newPath);

			sharepointObject = sharepointConnection.getSharepointObject(
				newPath);

			return toExtRepositoryObject(
				extRepositoryObjectType, sharepointObject);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	@Override
	public List<ExtRepositorySearchResult<?>> search(
			SearchContext searchContext, Query query,
			ExtRepositoryQueryMapper extRepositoryQueryMapper)
		throws PortalException {

		List<ExtRepositorySearchResult<?>> extRepositorySearchResults =
			new ArrayList<>();

		List<SharepointObject> sharepointObjects = doSearch(
			searchContext, query, extRepositoryQueryMapper);

		sharepointObjects = filter(searchContext, sharepointObjects);

		for (SharepointObject sharepointObject : sharepointObjects) {
			ExtRepositoryObject extRepositoryObject = toExtRepositoryObject(
				ExtRepositoryObjectType.OBJECT, sharepointObject);

			ExtRepositorySearchResult<?> extRepositorySearchResult =
				new ExtRepositorySearchResult<>(
					extRepositoryObject, 1, StringPool.BLANK);

			extRepositorySearchResults.add(extRepositorySearchResult);
		}

		return extRepositorySearchResults;
	}

	@Override
	public ExtRepositoryFileEntry updateExtRepositoryFileEntry(
		String extRepositoryFileEntryKey, String mimeType,
		InputStream inputStream) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			SharepointObject fileSharepointObject =
				sharepointConnection.getSharepointObject(
					toSharepointObjectId(extRepositoryFileEntryKey));

			String filePath = fileSharepointObject.getPath();

			sharepointConnection.updateFile(filePath, inputStream);

			return new SharepointWSFileEntry(fileSharepointObject);
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
		catch (SharepointRuntimeException sharepointRuntimeException) {
			throw new SystemException(sharepointRuntimeException);
		}
	}

	protected List<SharepointObject> doSearch(
			SearchContext searchContext, Query query,
			ExtRepositoryQueryMapper extRepositoryQueryMapper)
		throws PortalException {

		try {
			SharepointQueryBuilder sharepointQueryBuilder =
				new SharepointQueryBuilder(
					this, searchContext, query, extRepositoryQueryMapper);

			SharepointConnection sharepointConnection =
				getSharepointConnection();

			return sharepointConnection.getSharepointObjects(
				sharepointQueryBuilder.getQuery(),
				sharepointQueryBuilder.getQueryOptionsList());
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
	}

	protected List<SharepointObject> filter(
		SearchContext searchContext, List<SharepointObject> sharepointObjects) {

		return ListUtil.subList(
			sharepointObjects, searchContext.getStart(),
			searchContext.getEnd());
	}

	protected int getPort(URL url) {
		int port = url.getPort();

		if (port == -1) {
			String protocol = url.getProtocol();

			if (protocol.equals("https")) {
				port = 443;
			}
			else {
				port = 80;
			}
		}

		return port;
	}

	protected void getSubfolderKeys(
		String path, List<String> extRepositoryFolderKeys) {

		try {
			SharepointConnection sharepointConnection =
				getSharepointConnection();

			List<SharepointObject> folderSharepointObjects =
				sharepointConnection.getSharepointObjects(
					path, SharepointConnection.ObjectTypeFilter.FOLDERS);

			for (SharepointObject folderSharepointObject :
					folderSharepointObjects) {

				String extRepositoryFolderKey = String.valueOf(
					folderSharepointObject.getSharepointObjectId());

				extRepositoryFolderKeys.add(extRepositoryFolderKey);

				getSubfolderKeys(
					folderSharepointObject.getPath(), extRepositoryFolderKeys);
			}
		}
		catch (SharepointException sharepointException) {
			throw new SystemException(sharepointException);
		}
	}

	protected void pingSharepointConnection(
			SharepointConnection sharepointConnection)
		throws SharepointException {

		sharepointConnection.getSharepointObjectsCount(
			StringPool.SLASH, SharepointConnection.ObjectTypeFilter.FILES);
	}

	protected void processSharepointObjectException(
			SharepointException sharepointException1, boolean folder,
			String path, String name)
		throws PortalException {

		if (path == null) {
			return;
		}

		SharepointConnection sharepointConnection = getSharepointConnection();

		try {
			SharepointObject sharepointObject =
				sharepointConnection.getSharepointObject(path);

			if (sharepointObject == null) {
				return;
			}

			if (folder) {
				throw new DuplicateFolderNameException(name);
			}

			throw new DuplicateFileEntryException(name);
		}
		catch (SharepointException sharepointException2) {

			// The Sharepoint object does not exist

		}
	}

	protected <T extends ExtRepositoryObject> T toExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			SharepointObject sharepointObject)
		throws PortalException {

		if (sharepointObject.isFile()) {
			if (extRepositoryObjectType == ExtRepositoryObjectType.FOLDER) {
				throw new NoSuchFolderException(
					"Invalid external repository object type " +
						extRepositoryObjectType + " for Sharepoint object " +
							sharepointObject);
			}

			return (T)new SharepointWSFileEntry(sharepointObject);
		}

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			throw new NoSuchFileEntryException(
				"Invalid external repository object type " +
					extRepositoryObjectType + " for Sharepoint object " +
						sharepointObject);
		}

		return (T)new SharepointWSFolder(sharepointObject);
	}

	protected SharepointConnection.ObjectTypeFilter toObjectTypeFilter(
		ExtRepositoryObjectType<? extends ExtRepositoryObject>
			extRepositoryObjectType) {

		SharepointConnection.ObjectTypeFilter objectTypeFilter =
			_objectTypeFilters.get(extRepositoryObjectType);

		if (objectTypeFilter == null) {
			throw new IllegalArgumentException(
				"Invalid external repository object type " +
					extRepositoryObjectType);
		}

		return objectTypeFilter;
	}

	protected long toSharepointObjectId(String key) {
		return GetterUtil.getLong(key);
	}

	protected void validateExtension(String oldPath, String newPath)
		throws PortalException {

		String oldExtension = pathHelper.getExtension(oldPath);

		String newExtension = pathHelper.getExtension(newPath);

		if (!newExtension.equals(oldExtension)) {
			throw new SourceFileNameException(
				"Sharepoint connector does not support changing the file " +
					"extension");
		}
	}

	protected static PathHelper pathHelper = new PathHelper();
	protected static URLHelper urlHelper = new URLHelper();

	private static final String _CONFIGURATION_WS = "SHAREPOINT_WS";

	private static final String _LIBRARY_NAME = "LIBRARY_NAME";

	private static final String _LIBRARY_PATH = "LIBRARY_PATH";

	private static final String _SERVER_VERSION = "SERVER_VERSION";

	private static final String _SHAREPOINT_2013_VALUE =
		SharepointConnection.ServerVersion.SHAREPOINT_2013.getValue();

	private static final String _SITE_URL = "SITE_URL";

	private static final String[] _SUPPORTED_CONFIGURATIONS = {
		_CONFIGURATION_WS
	};

	private static final String[][] _SUPPORTED_PARAMETERS = {
		{_LIBRARY_NAME, _LIBRARY_PATH, _SERVER_VERSION, _SITE_URL}
	};

	private static final Map
		<ExtRepositoryObjectType<?>, SharepointConnection.ObjectTypeFilter>
			_objectTypeFilters =
				HashMapBuilder.
					<ExtRepositoryObjectType<?>,
					 SharepointConnection.ObjectTypeFilter>put(
						ExtRepositoryObjectType.FILE,
						SharepointConnection.ObjectTypeFilter.FILES
					).put(
						ExtRepositoryObjectType.FOLDER,
						SharepointConnection.ObjectTypeFilter.FOLDERS
					).put(
						ExtRepositoryObjectType.OBJECT,
						SharepointConnection.ObjectTypeFilter.ALL
					).build();

	private ConnectionCache<SharepointConnection> _connectionCache;
	private CredentialsProvider _credentialsProvider;
	private String _host;
	private String _libraryName;
	private String _libraryPath;
	private int _port;
	private String _protocol;
	private String _rootFolderKey;
	private SharepointConnection.ServerVersion _serverVersion;
	private String _sitePath;

}