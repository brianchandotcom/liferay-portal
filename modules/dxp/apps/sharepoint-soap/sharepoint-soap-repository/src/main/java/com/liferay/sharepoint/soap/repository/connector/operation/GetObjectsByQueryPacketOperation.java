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

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;
import com.liferay.sharepoint.soap.repository.connector.SharepointResultException;
import com.liferay.sharepoint.soap.repository.connector.internal.util.RemoteExceptionSharepointExceptionMapper;

import com.microsoft.webservices.sharepoint.queryservice.QueryServiceSoap12Stub;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import search.microsoft.QueryDocument;
import search.microsoft.QueryResponseDocument;

/**
 * @author Iván Zaera
 */
public final class GetObjectsByQueryPacketOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_getSharepointObjectByPathOperation = getOperation(
			GetSharepointObjectByPathOperation.class);

		_searchPrefix =
			sharepointConnectionInfo.getServiceURL() +
				sharepointConnectionInfo.getLibraryPath();

		_searchPrefixLength = _searchPrefix.length();
	}

	public List<SharepointObject> execute(String queryPacket)
		throws SharepointException {

		QueryResponseDocument queryResponseDocument = null;

		try {
			queryResponseDocument = _queryServiceSoap12Stub.query(
				getQueryDocument(queryPacket));
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}

		return getSharepointObjects(queryResponseDocument);
	}

	public void setQueryServiceSoap12Stub(
		QueryServiceSoap12Stub queryServiceSoap12Stub) {

		_queryServiceSoap12Stub = queryServiceSoap12Stub;
	}

	protected QueryDocument getQueryDocument(String queryPacket) {
		QueryDocument queryDocument = QueryDocument.Factory.newInstance();

		QueryDocument.Query query = queryDocument.addNewQuery();

		query.setQueryXml(queryPacket);

		return queryDocument;
	}

	protected List<SharepointObject> getSharepointObjects(
			QueryResponseDocument queryResponseDocument)
		throws SharepointException {

		QueryResponseDocument.QueryResponse queryResponse =
			queryResponseDocument.getQueryResponse();

		QueryServiceStubResult queryServiceStubResult =
			new QueryServiceStubResult(queryResponse.getQueryResult());

		if (!queryServiceStubResult.isSuccess()) {
			throw new SharepointResultException(
				queryServiceStubResult.getStatus(),
				queryServiceStubResult.getDebugErrorMessage());
		}

		if (queryServiceStubResult.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> queryServiceSoapResultLinkURLs =
			queryServiceStubResult.getLinkURLs();

		List<SharepointObject> sharepointObjects = new ArrayList<>();

		for (String queryServiceSoapResultLinkURL :
				queryServiceSoapResultLinkURLs) {

			if (!queryServiceSoapResultLinkURL.startsWith(_searchPrefix)) {
				continue;
			}

			String path = queryServiceSoapResultLinkURL.substring(
				_searchPrefixLength);

			SharepointObject sharepointObject =
				_getSharepointObjectByPathOperation.execute(path);

			if (sharepointObject == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Ignored Sharepoint object at path " + path);
				}

				continue;
			}

			sharepointObjects.add(sharepointObject);
		}

		return sharepointObjects;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetObjectsByQueryPacketOperation.class);

	private GetSharepointObjectByPathOperation
		_getSharepointObjectByPathOperation;
	private QueryServiceSoap12Stub _queryServiceSoap12Stub;
	private String _searchPrefix;
	private int _searchPrefixLength;

}