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

package com.liferay.sharepoint.repository.internal.document.library.repository.external;

import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.document.library.repository.authorization.oauth2.TokenStore;
import com.liferay.document.library.repository.external.CredentialsProvider;
import com.liferay.document.library.repository.external.ExtRepository;
import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;
import com.liferay.document.library.repository.external.ExtRepositoryFileVersionDescriptor;
import com.liferay.document.library.repository.external.ExtRepositoryFolder;
import com.liferay.document.library.repository.external.ExtRepositoryModel;
import com.liferay.document.library.repository.external.ExtRepositoryObject;
import com.liferay.document.library.repository.external.ExtRepositoryObjectType;
import com.liferay.document.library.repository.external.ExtRepositorySearchResult;
import com.liferay.document.library.repository.external.search.ExtRepositoryQueryMapper;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharepoint.repository.internal.configuration.SharepointRepositoryConfiguration;
import com.liferay.sharepoint.repository.internal.document.library.repository.external.model.SharepointFileEntry;
import com.liferay.sharepoint.repository.internal.document.library.repository.external.model.SharepointModel;
import com.liferay.sharepoint.repository.internal.document.library.repository.external.model.SharepointRootFolder;
import com.liferay.sharepoint.repository.internal.search.kql.KQLQuery;
import com.liferay.sharepoint.repository.internal.search.kql.KQLQueryVisitor;
import com.liferay.sharepoint.repository.internal.util.SharepointServerResponseConverter;
import com.liferay.sharepoint.repository.internal.util.SharepointURLHelper;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public class SharepointExtRepository implements ExtRepository {

	public SharepointExtRepository(
		TokenStore tokenStore,
		SharepointRepositoryConfiguration sharepointRepositoryConfiguration) {

		_tokenStore = tokenStore;
		_sharepointRepositoryConfiguration = sharepointRepositoryConfiguration;
	}

	@Override
	public ExtRepositoryFileEntry addExtRepositoryFileEntry(
			String extRepositoryParentFolderKey, String mimeType, String title,
			String description, String changeLog, InputStream inputStream)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getAddFileURL(
				extRepositoryParentFolderKey, title);

			JSONObject jsonObject = _post(url, inputStream);

			return _sharepointServerResponseConverter.getExtRepositoryFileEntry(
				jsonObject);
		}
		catch (IOException | UnirestException e) {
			throw new PortalException(e);
		}
	}

	@Override
	public ExtRepositoryFolder addExtRepositoryFolder(
			String extRepositoryParentFolderKey, String name,
			String description)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getAddFolderURL(
				extRepositoryParentFolderKey);

			JSONObject bodyJSONObject = JSONFactoryUtil.createJSONObject();

			JSONObject metadataJSONObject = JSONFactoryUtil.createJSONObject();

			metadataJSONObject.put("type", "SP.Folder");

			bodyJSONObject.put("__metadata", metadataJSONObject);

			bodyJSONObject.put("ServerRelativeUrl", name);

			JSONObject jsonObject = _post(url, bodyJSONObject);

			return _sharepointServerResponseConverter.getExtRepositoryFolder(
				jsonObject);
		}
		catch (IOException | UnirestException e) {
			throw new PortalException(e);
		}
	}

	@Override
	public ExtRepositoryFileVersion cancelCheckOut(
			String extRepositoryFileEntryKey)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getCancelCheckedOutFileURL(
				extRepositoryFileEntryKey);

			_post(url);

			return null;
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public void checkInExtRepositoryFileEntry(
			String extRepositoryFileEntryKey, boolean createMajorVersion,
			String changeLog)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getCheckInFileURL(
				extRepositoryFileEntryKey, createMajorVersion, changeLog);

			_post(url);
		}
		catch (PrincipalException pe) {

			// See LPS-75604

			String message = pe.getMessage();

			if ((message == null) || !message.endsWith("423 Locked")) {
				throw pe;
			}
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public ExtRepositoryFileEntry checkOutExtRepositoryFileEntry(
			String extRepositoryFileEntryKey)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getCheckOutFileURL(
				extRepositoryFileEntryKey);

			_post(url);

			return getExtRepositoryObject(
				ExtRepositoryObjectType.FILE, extRepositoryFileEntryKey);
		}
		catch (PrincipalException pe) {

			// See LPS-75604

			String message = pe.getMessage();

			if ((message != null) && message.endsWith("423 Locked")) {
				return getExtRepositoryObject(
					ExtRepositoryObjectType.FILE, extRepositoryFileEntryKey);
			}

			throw pe;
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public <T extends ExtRepositoryObject> T copyExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFileEntryKey, String newExtRepositoryFolderKey,
			String newTitle)
		throws PortalException {

		try {
			if (extRepositoryObjectType != ExtRepositoryObjectType.FILE) {
				throw new UnsupportedOperationException();
			}

			String url = _sharepointURLHelper.getCopyFileURL(
				extRepositoryFileEntryKey, newExtRepositoryFolderKey, newTitle);

			_post(url);

			return getExtRepositoryObject(
				extRepositoryObjectType,
				newExtRepositoryFolderKey + StringPool.SLASH + newTitle);
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public void deleteExtRepositoryObject(
			ExtRepositoryObjectType<? extends ExtRepositoryObject>
				extRepositoryObjectType,
			String extRepositoryObjectKey)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getDeleteObjectURL(
				extRepositoryObjectType, extRepositoryObjectKey);

			_delete(url);
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public String getAuthType() {
		return CompanyConstants.AUTH_TYPE_ID;
	}

	@Override
	public InputStream getContentStream(
			ExtRepositoryFileEntry extRepositoryFileEntry)
		throws PortalException {

		try {
			return _getInputStream((SharepointModel)extRepositoryFileEntry);
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public InputStream getContentStream(
			ExtRepositoryFileVersion extRepositoryFileVersion)
		throws PortalException {

		try {
			return _getInputStream((SharepointModel)extRepositoryFileVersion);
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public ExtRepositoryFileVersion getExtRepositoryFileVersion(
			ExtRepositoryFileEntry extRepositoryFileEntry, String version)
		throws PortalException {

		List<ExtRepositoryFileVersion> extRepositoryFileVersions =
			getExtRepositoryFileVersions(extRepositoryFileEntry);

		for (ExtRepositoryFileVersion extRepositoryFileVersion :
				extRepositoryFileVersions) {

			if (version.equals(extRepositoryFileVersion.getVersion())) {
				return extRepositoryFileVersion;
			}
		}

		return null;
	}

	@Override
	public ExtRepositoryFileVersionDescriptor
		getExtRepositoryFileVersionDescriptor(
			String extRepositoryFileVersionKey) {

		String[] parts = extRepositoryFileVersionKey.split(StringPool.COLON);

		return new ExtRepositoryFileVersionDescriptor(parts[0], parts[1]);
	}

	@Override
	public List<ExtRepositoryFileVersion> getExtRepositoryFileVersions(
			ExtRepositoryFileEntry extRepositoryFileEntry)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getFileVersionsURL(
				extRepositoryFileEntry);

			JSONObject jsonObject = _getJSONObject(url);

			return _sharepointServerResponseConverter.
				getExtRepositoryFileVersions(
					(SharepointFileEntry)extRepositoryFileEntry, jsonObject);
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getObjectURL(
				extRepositoryObjectType, extRepositoryObjectKey);

			JSONObject jsonObject = _getJSONObject(url);

			return _sharepointServerResponseConverter.getExtRepositoryObject(
				extRepositoryObjectType, jsonObject);
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey, String title)
		throws PortalException {

		if (extRepositoryObjectType == ExtRepositoryObjectType.OBJECT) {
			throw new UnsupportedOperationException();
		}

		List<T> extRepositoryObjects = getExtRepositoryObjects(
			extRepositoryObjectType, extRepositoryFolderKey);

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			for (ExtRepositoryFileEntry extRepositoryFileEntry :
					(List<ExtRepositoryFileEntry>)extRepositoryObjects) {

				if (title.equals(extRepositoryFileEntry.getTitle())) {
					return (T)extRepositoryFileEntry;
				}
			}

			return null;
		}

		for (ExtRepositoryFolder extRepositoryFolder :
				(List<ExtRepositoryFolder>)extRepositoryObjects) {

			if (title.equals(extRepositoryFolder.getName())) {
				return (T)extRepositoryFolder;
			}
		}

		return null;
	}

	@Override
	public <T extends ExtRepositoryObject> List<T> getExtRepositoryObjects(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey)
		throws PortalException {

		try {
			if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
				return _getExtRepositoryFileEntries(extRepositoryFolderKey);
			}

			if (extRepositoryObjectType == ExtRepositoryObjectType.FOLDER) {
				return _getExtRepositoryFolders(extRepositoryFolderKey);
			}

			List<T> extRepositoryFileEntries = _getExtRepositoryFileEntries(
				extRepositoryFolderKey);

			List<T> extRepositoryFolders = _getExtRepositoryFolders(
				extRepositoryFolderKey);

			List<T> extRepositoryObjects = new ArrayList<>(
				extRepositoryFileEntries.size() + extRepositoryFolders.size());

			extRepositoryObjects.addAll(extRepositoryFileEntries);
			extRepositoryObjects.addAll(extRepositoryFolders);

			return extRepositoryObjects;
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public int getExtRepositoryObjectsCount(
			ExtRepositoryObjectType<? extends ExtRepositoryObject>
				extRepositoryObjectType,
			String extRepositoryFolderKey)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getObjectsCountURL(
				extRepositoryObjectType, extRepositoryFolderKey);

			JSONObject jsonObject = _getJSONObject(url);

			return _sharepointServerResponseConverter.
				getExtRepositoryObjectsCount(jsonObject);
		}
		catch (JSONException | UnirestException e) {
			throw new PortalException(e);
		}
	}

	@Override
	public ExtRepositoryFolder getExtRepositoryParentFolder(
			ExtRepositoryObject extRepositoryObject)
		throws PortalException {

		String extRepositoryModelKey =
			extRepositoryObject.getExtRepositoryModelKey();

		if (extRepositoryModelKey.equals(
				_rootFolder.getExtRepositoryModelKey())) {

			return null;
		}

		int pos = extRepositoryModelKey.lastIndexOf(CharPool.SLASH);

		String parentFolderPath = extRepositoryModelKey.substring(0, pos);

		if (parentFolderPath.equals(_libraryPath) ||
			Validator.isNull(parentFolderPath)) {

			return _rootFolder;
		}

		return getExtRepositoryObject(
			ExtRepositoryObjectType.FOLDER, parentFolderPath);
	}

	@Override
	public String getLiferayLogin(String extRepositoryLogin) {
		return String.valueOf(PrincipalThreadLocal.getUserId());
	}

	@Override
	public String getRootFolderKey() throws PortalException {
		return _libraryPath;
	}

	@Override
	public List<String> getSubfolderKeys(
			String extRepositoryFolderKey, boolean recurse)
		throws PortalException {

		if (!recurse) {
			List<ExtRepositoryFolder> extRepositoryObjects =
				getExtRepositoryObjects(
					ExtRepositoryObjectType.FOLDER, extRepositoryFolderKey);

			Stream<ExtRepositoryFolder> extRepositoryFolderStream =
				extRepositoryObjects.stream();

			return extRepositoryFolderStream.map(
				ExtRepositoryModel::getExtRepositoryModelKey
			).collect(
				Collectors.toList()
			);
		}

		List<String> subfolderKeys = new ArrayList<>();

		_collectSubfolderKeys(extRepositoryFolderKey, subfolderKeys);

		return subfolderKeys;
	}

	@Override
	public String[] getSupportedConfigurations() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[][] getSupportedParameters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void initRepository(
			UnicodeProperties typeSettingsProperties,
			CredentialsProvider credentialsProvider)
		throws PortalException {

		_libraryPath = _strip(
			GetterUtil.getString(
				typeSettingsProperties.getProperty("library-path")));

		_rootFolder = new SharepointRootFolder(_libraryPath);

		_siteAbsoluteURL = _strip(
			GetterUtil.getString(
				typeSettingsProperties.getProperty("site-absolute-url"),
				StringPool.DASH));

		_sharepointURLHelper = new SharepointURLHelper(_siteAbsoluteURL);

		_sharepointServerResponseConverter =
			new SharepointServerResponseConverter(
				_sharepointURLHelper, this, _siteAbsoluteURL, _libraryPath);
	}

	@Override
	public <T extends ExtRepositoryObject> T moveExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey, String newExtRepositoryFolderKey,
			String newTitle)
		throws PortalException {

		if (extRepositoryObjectType != ExtRepositoryObjectType.FILE) {
			ExtRepositoryFolder extRepositoryFolder = _copyExtRepositoryFolder(
				extRepositoryObjectKey, newExtRepositoryFolderKey, newTitle);

			deleteExtRepositoryObject(
				ExtRepositoryObjectType.FOLDER, extRepositoryObjectKey);

			return (T)extRepositoryFolder;
		}

		try {
			String url = _sharepointURLHelper.getMoveFileURL(
				extRepositoryObjectKey, newExtRepositoryFolderKey, newTitle);

			_post(url);

			return getExtRepositoryObject(
				extRepositoryObjectType,
				newExtRepositoryFolderKey + StringPool.SLASH + newTitle);
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public List<ExtRepositorySearchResult<?>> search(
			SearchContext searchContext, Query query,
			ExtRepositoryQueryMapper extRepositoryQueryMapper)
		throws PortalException {

		try {
			KQLQueryVisitor queryVisitor = new KQLQueryVisitor(
				extRepositoryQueryMapper, _siteAbsoluteURL);

			KQLQuery kqlQuery = query.accept(queryVisitor);

			String url = _sharepointURLHelper.getSearchURL(
				kqlQuery.toString(), searchContext.getStart(),
				searchContext.getEnd());

			JSONObject jsonObject = _getJSONObject(url);

			return (List)_sharepointServerResponseConverter.getSearchResults(
				jsonObject);
		}
		catch (UnirestException ue) {
			throw new PortalException(ue);
		}
	}

	@Override
	public ExtRepositoryFileEntry updateExtRepositoryFileEntry(
			String extRepositoryFileEntryKey, String mimeType,
			InputStream inputStream)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getUpdateFileURL(
				extRepositoryFileEntryKey);

			_put(url, inputStream);

			return getExtRepositoryObject(
				ExtRepositoryObjectType.FILE, extRepositoryFileEntryKey);
		}
		catch (IOException | UnirestException e) {
			throw new PortalException(e);
		}
	}

	private void _collectSubfolderKeys(
			String extRepositoryFolderKey, List<String> subfolderKeys)
		throws PortalException {

		List<ExtRepositoryFolder> extRepositoryFolders =
			getExtRepositoryObjects(
				ExtRepositoryObjectType.FOLDER, extRepositoryFolderKey);

		for (ExtRepositoryFolder extRepositoryFolder : extRepositoryFolders) {
			String subfolderKey =
				extRepositoryFolder.getExtRepositoryModelKey();

			subfolderKeys.add(subfolderKey);

			_collectSubfolderKeys(subfolderKey, subfolderKeys);
		}
	}

	private ExtRepositoryFolder _copyExtRepositoryFolder(
			String extRepositoryFolderKey, String newExtRepositoryFolderKey,
			String newTitle)
		throws PortalException {

		ExtRepositoryFolder originalExtRepositoryFolder =
			getExtRepositoryObject(
				ExtRepositoryObjectType.FOLDER, extRepositoryFolderKey);

		if (Validator.isNull(newTitle)) {
			newTitle = originalExtRepositoryFolder.getName();
		}

		ExtRepositoryFolder newExtRepositoryFolder = addExtRepositoryFolder(
			newExtRepositoryFolderKey, newTitle,
			originalExtRepositoryFolder.getDescription());

		List<ExtRepositoryFolder> extRepositoryFolders =
			getExtRepositoryObjects(
				ExtRepositoryObjectType.FOLDER,
				originalExtRepositoryFolder.getExtRepositoryModelKey());

		for (ExtRepositoryFolder extRepositoryFolder : extRepositoryFolders) {
			_copyExtRepositoryFolder(
				extRepositoryFolder.getExtRepositoryModelKey(),
				newExtRepositoryFolder.getExtRepositoryModelKey(), null);
		}

		List<ExtRepositoryFileEntry> extRepositoryFileEntries =
			getExtRepositoryObjects(
				ExtRepositoryObjectType.FILE,
				originalExtRepositoryFolder.getExtRepositoryModelKey());

		for (ExtRepositoryFileEntry extRepositoryFileEntry :
				extRepositoryFileEntries) {

			copyExtRepositoryObject(
				ExtRepositoryObjectType.FILE,
				extRepositoryFileEntry.getExtRepositoryModelKey(),
				newExtRepositoryFolder.getExtRepositoryModelKey(),
				extRepositoryFileEntry.getTitle());
		}

		return newExtRepositoryFolder;
	}

	private void _delete(String url) throws PortalException, UnirestException {
		HttpRequestWithBody httpRequestWithBody = Unirest.delete(url);

		httpRequestWithBody.header(
			"Authorization", "Bearer " + _getAccessToken());

		HttpResponse<InputStream> httpResponse = httpRequestWithBody.asBinary();

		if (httpResponse.getStatus() >= 300) {
			throw new PrincipalException(
				String.format(
					"Unable to delete %s: %d %s", url, httpResponse.getStatus(),
					httpResponse.getStatusText()));
		}
	}

	private String _getAccessToken() throws PortalException {
		Token token = _tokenStore.get(
			_sharepointRepositoryConfiguration.name(),
			PrincipalThreadLocal.getUserId());

		if ((token == null) || token.isExpired()) {
			throw new PrincipalException();
		}

		return token.getAccessToken();
	}

	private <T extends ExtRepositoryObject> List<T>
			_getExtRepositoryFileEntries(String extRepositoryFolderKey)
		throws PortalException, UnirestException {

		String url = _sharepointURLHelper.getFilesURL(extRepositoryFolderKey);

		JSONObject jsonObject = _getJSONObject(url);

		return _sharepointServerResponseConverter.getExtRepositoryFileEntries(
			jsonObject);
	}

	private <T extends ExtRepositoryObject> List<T> _getExtRepositoryFolders(
			String extRepositoryFolderKey)
		throws PortalException, UnirestException {

		String url = _sharepointURLHelper.getFoldersURL(extRepositoryFolderKey);

		JSONObject jsonObject = _getJSONObject(url);

		return _sharepointServerResponseConverter.getExtRepositoryFolders(
			jsonObject);
	}

	private InputStream _getInputStream(SharepointModel sharepointModel)
		throws PortalException, UnirestException {

		return _getInputStream(sharepointModel.getCanonicalContentURL());
	}

	private InputStream _getInputStream(String url)
		throws PortalException, UnirestException {

		GetRequest getRequest = Unirest.get(url);

		getRequest.header("Authorization", "Bearer " + _getAccessToken());

		HttpResponse<InputStream> httpResponse = getRequest.asBinary();

		if (httpResponse.getStatus() >= 300) {
			throw new PrincipalException(
				String.format(
					"Unable to get %s: %d %s", url, httpResponse.getStatus(),
					httpResponse.getStatusText()));
		}

		return httpResponse.getBody();
	}

	private JSONObject _getJSONObject(String url)
		throws PortalException, UnirestException {

		GetRequest getRequest = Unirest.get(url);

		getRequest.header("Accept", "application/json;odata=verbose");
		getRequest.header("Authorization", "Bearer " + _getAccessToken());

		HttpResponse<String> httpResponse = getRequest.asString();

		if (httpResponse.getStatus() >= 300) {
			throw new PrincipalException(
				String.format(
					"Unable to get %s: %d %s", url, httpResponse.getStatus(),
					httpResponse.getStatusText()));
		}

		return JSONFactoryUtil.createJSONObject(httpResponse.getBody());
	}

	private void _post(String url) throws PortalException, UnirestException {
		HttpRequestWithBody httpRequestWithBody = Unirest.post(url);

		httpRequestWithBody.header(
			"Authorization", "Bearer " + _getAccessToken());

		HttpResponse<InputStream> httpResponse = httpRequestWithBody.asBinary();

		if (httpResponse.getStatus() >= 300) {
			throw new PrincipalException(
				String.format(
					"Unable to post to %s: %d %s", url,
					httpResponse.getStatus(), httpResponse.getStatusText()));
		}
	}

	private JSONObject _post(String url, InputStream inputStream)
		throws IOException, PortalException, UnirestException {

		HttpRequestWithBody httpRequestWithBody = Unirest.post(url);

		httpRequestWithBody.body(FileUtil.getBytes(inputStream));
		httpRequestWithBody.header("accept", "application/json; odata=verbose");
		httpRequestWithBody.header(
			"Authorization", "Bearer " + _getAccessToken());

		HttpResponse<String> httpResponse = httpRequestWithBody.asString();

		if (httpResponse.getStatus() >= 300) {
			throw new PrincipalException(
				String.format(
					"Unable to post to %s: %d %s", url,
					httpResponse.getStatus(), httpResponse.getStatusText()));
		}

		return JSONFactoryUtil.createJSONObject(httpResponse.getBody());
	}

	private JSONObject _post(String url, JSONObject jsonObject)
		throws IOException, PortalException, UnirestException {

		HttpRequestWithBody httpRequestWithBody = Unirest.post(url);

		httpRequestWithBody.header("accept", "application/json; odata=verbose");
		httpRequestWithBody.header(
			"Authorization", "Bearer " + _getAccessToken());
		httpRequestWithBody.header(
			"Content-Type", "application/json; odata=verbose");
		httpRequestWithBody.body(jsonObject.toJSONString());

		HttpResponse<String> httpResponse = httpRequestWithBody.asString();

		if (httpResponse.getStatus() >= 300) {
			throw new PrincipalException(
				String.format(
					"Unable to post to %s: %d %s", url,
					httpResponse.getStatus(), httpResponse.getStatusText()));
		}

		return JSONFactoryUtil.createJSONObject(httpResponse.getBody());
	}

	private void _put(String url, InputStream inputStream)
		throws IOException, PortalException, UnirestException {

		HttpRequestWithBody httpRequestWithBody = Unirest.put(url);

		httpRequestWithBody.header("accept", "application/json; odata=verbose");
		httpRequestWithBody.header(
			"Authorization", "Bearer " + _getAccessToken());
		httpRequestWithBody.body(FileUtil.getBytes(inputStream));

		HttpResponse<String> httpResponse = httpRequestWithBody.asString();

		if (httpResponse.getStatus() >= 300) {
			throw new PrincipalException(
				String.format(
					"Unable to post to %s: %d %s", url,
					httpResponse.getStatus(), httpResponse.getStatusText()));
		}
	}

	private String _strip(String s) {
		int i = 0;

		while (s.charAt(i) == CharPool.SLASH) {
			i++;
		}

		int j = s.length() - 1;

		while ((j > i) && (s.charAt(j) == CharPool.SLASH)) {
			j--;
		}

		if (i < j) {
			return s.substring(i, j + 1);
		}

		return s;
	}

	private String _libraryPath;
	private ExtRepositoryFolder _rootFolder;
	private final SharepointRepositoryConfiguration
		_sharepointRepositoryConfiguration;
	private SharepointServerResponseConverter
		_sharepointServerResponseConverter;
	private SharepointURLHelper _sharepointURLHelper;
	private String _siteAbsoluteURL;
	private final TokenStore _tokenStore;

}