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
import com.liferay.document.library.repository.external.ExtRepositoryObject;
import com.liferay.document.library.repository.external.ExtRepositoryObjectType;
import com.liferay.document.library.repository.external.ExtRepositorySearchResult;
import com.liferay.document.library.repository.external.search.ExtRepositoryQueryMapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.sharepoint.repository.internal.configuration.SharepointRepositoryConfiguration;
import com.liferay.sharepoint.repository.internal.document.library.repository.external.model.SharepointRootFolder;
import com.liferay.sharepoint.repository.internal.util.SharepointServerResponseConverter;
import com.liferay.sharepoint.repository.internal.util.SharepointURLHelper;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.List;

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

			JSONObject metadata = JSONFactoryUtil.createJSONObject();

			metadata.put("type", "SP.Folder");

			JSONObject body = JSONFactoryUtil.createJSONObject();

			body.put("__metadata", metadata);
			body.put("ServerRelativeUrl", name);

			JSONObject jsonObject = _post(url, body);

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

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteExtRepositoryObject(
			ExtRepositoryObjectType<? extends ExtRepositoryObject>
				extRepositoryObjectType,
			String extRepositoryObjectKey)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String getAuthType() {
		return CompanyConstants.AUTH_TYPE_ID;
	}

	@Override
	public InputStream getContentStream(
			ExtRepositoryFileEntry extRepositoryFileEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getContentStream(
			ExtRepositoryFileVersion extRepositoryFileVersion)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExtRepositoryFileVersion getExtRepositoryFileVersion(
			ExtRepositoryFileEntry extRepositoryFileEntry, String version)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExtRepositoryFileVersionDescriptor
		getExtRepositoryFileVersionDescriptor(
			String extRepositoryFileVersionKey) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<ExtRepositoryFileVersion> getExtRepositoryFileVersions(
			ExtRepositoryFileEntry extRepositoryFileEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey)
		throws PortalException {

		try {
			String url = _sharepointURLHelper.getObjectURL(
				extRepositoryObjectType, extRepositoryObjectKey);

			JSONObject jsonObject = _requestJSONObject(url);

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

		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends ExtRepositoryObject> List<T> getExtRepositoryObjects(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey)
		throws PortalException {

		return Collections.emptyList();
	}

	@Override
	public int getExtRepositoryObjectsCount(
			ExtRepositoryObjectType<? extends ExtRepositoryObject>
				extRepositoryObjectType,
			String extRepositoryFolderKey)
		throws PortalException {

		return 0;
	}

	@Override
	public ExtRepositoryFolder getExtRepositoryParentFolder(
			ExtRepositoryObject extRepositoryObject)
		throws PortalException {

		throw new UnsupportedOperationException();
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

		throw new UnsupportedOperationException();
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

		_siteAbsoluteUrl = _strip(
			GetterUtil.getString(
				typeSettingsProperties.getProperty("site-absolute-url"),
				StringPool.DASH));

		_sharepointURLHelper = new SharepointURLHelper(_siteAbsoluteUrl);

		_sharepointServerResponseConverter =
			new SharepointServerResponseConverter(
				_sharepointURLHelper, this, _siteAbsoluteUrl);
	}

	@Override
	public <T extends ExtRepositoryObject> T moveExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey, String newExtRepositoryFolderKey,
			String newTitle)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<ExtRepositorySearchResult<?>> search(
			SearchContext searchContext, Query query,
			ExtRepositoryQueryMapper extRepositoryQueryMapper)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExtRepositoryFileEntry updateExtRepositoryFileEntry(
			String extRepositoryFileEntryKey, String mimeType,
			InputStream inputStream)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	private static final String _strip(String s) {
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

	private String _getAccessToken() throws PortalException {
		Token token = _tokenStore.get(
			_sharepointRepositoryConfiguration.name(),
			PrincipalThreadLocal.getUserId());

		if ((token == null) || token.isExpired()) {
			throw new PrincipalException();
		}

		return token.getAccessToken();
	}

	private void _post(String url) throws PortalException, UnirestException {
		HttpRequestWithBody httpRequestWithBody = Unirest.post(url);

		httpRequestWithBody.header(
			"Authorization", "Bearer " + _getAccessToken());

		HttpResponse<InputStream> httpResponse = httpRequestWithBody.asBinary();

		if (httpResponse.getStatus() >= 300) {
			throw new PrincipalException(
				String.format(
					"Error while posting to resource %s: %d %s", url,
					httpResponse.getStatus(), httpResponse.getStatusText()));
		}
	}

	private JSONObject _post(String url, InputStream inputStream)
		throws IOException, PortalException, UnirestException {

		HttpRequestWithBody httpRequestWithBody = Unirest.post(url);

		httpRequestWithBody.header("accept", "application/json; odata=verbose");
		httpRequestWithBody.header(
			"Authorization", "Bearer " + _getAccessToken());
		httpRequestWithBody.body(FileUtil.getBytes(inputStream));

		HttpResponse<String> httpResponse = httpRequestWithBody.asString();

		if (httpResponse.getStatus() >= 300) {
			throw new PrincipalException(
				String.format(
					"Error while getting resource %s: %d %s", url,
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
					"Error while getting resource %s: %d %s", url,
					httpResponse.getStatus(), httpResponse.getStatusText()));
		}

		return JSONFactoryUtil.createJSONObject(httpResponse.getBody());
	}

	private JSONObject _requestJSONObject(String url)
		throws PortalException, UnirestException {

		GetRequest getRequest = Unirest.get(url);

		getRequest.header("Accept", "application/json;odata=verbose");
		getRequest.header("Authorization", "Bearer " + _getAccessToken());

		HttpResponse<String> httpResponse = getRequest.asString();

		if (httpResponse.getStatus() >= 300) {
			throw new PrincipalException(
				String.format(
					"Error while getting resource %s: %d %s", url,
					httpResponse.getStatus(), httpResponse.getStatusText()));
		}

		return JSONFactoryUtil.createJSONObject(httpResponse.getBody());
	}

	private String _libraryPath;
	private ExtRepositoryFolder _rootFolder;
	private final SharepointRepositoryConfiguration
		_sharepointRepositoryConfiguration;
	private SharepointServerResponseConverter
		_sharepointServerResponseConverter;
	private SharepointURLHelper _sharepointURLHelper;
	private String _siteAbsoluteUrl;
	private final TokenStore _tokenStore;

}