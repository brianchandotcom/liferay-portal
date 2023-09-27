/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointResultException;
import com.liferay.sharepoint.soap.repository.connector.internal.util.RemoteExceptionSharepointExceptionMapper;
import com.liferay.sharepoint.soap.repository.connector.operation.constants.SharepointConstants;
import com.liferay.sharepoint.soap.repository.connector.schema.XMLUtil;
import com.liferay.sharepoint.soap.repository.connector.schema.batch.Batch;

import com.microsoft.schemas.sharepoint.soap.UpdateListItemsDocument;
import com.microsoft.schemas.sharepoint.soap.UpdateListItemsResponseDocument;

import java.rmi.RemoteException;

import java.util.Objects;

import org.w3c.dom.Node;

/**
 * @author Iván Zaera
 */
public final class BatchOperation extends BaseOperation {

	public void execute(Batch batch) throws SharepointException {
		try {
			UpdateListItemsResponseDocument updateListItemsResponseDocument =
				listsSoap12Stub.updateListItems(
					_getUpdateListItemsDocument(batch));

			_processUpdateListItemsResponseDocument(
				updateListItemsResponseDocument);
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}
	}

	private UpdateListItemsDocument _getUpdateListItemsDocument(Batch batch) {
		UpdateListItemsDocument updateListItemsDocument =
			UpdateListItemsDocument.Factory.newInstance();

		UpdateListItemsDocument.UpdateListItems updateListItems =
			updateListItemsDocument.addNewUpdateListItems();

		updateListItems.setListName(sharepointConnectionInfo.getLibraryName());

		UpdateListItemsDocument.UpdateListItems.Updates updates =
			updateListItems.addNewUpdates();

		Node node = updates.getDomNode();

		node.appendChild(XMLUtil.toNode(node.getOwnerDocument(), batch));

		return updateListItemsDocument;
	}

	private void _processUpdateListItemsResponseDocument(
			UpdateListItemsResponseDocument updateListItemsResponseDocument)
		throws SharepointException {

		UpdateListItemsResponseDocument.UpdateListItemsResponse
			updateListItemsResponse =
				updateListItemsResponseDocument.getUpdateListItemsResponse();

		UpdateListItemsResponseDocument.UpdateListItemsResponse.
			UpdateListItemsResult updateListItemsResult =
				updateListItemsResponse.getUpdateListItemsResult();

		Node node = updateListItemsResult.getDomNode();

		Node resultsNode = node.getFirstChild();

		Node resultNode = resultsNode.getFirstChild();

		Node errorCodeNode = XMLUtil.getNode("ErrorCode", resultNode);

		String errorCode = XMLUtil.toString(errorCodeNode.getFirstChild());

		if (!Objects.equals(
				errorCode, SharepointConstants.NUMERIC_STATUS_SUCCESS)) {

			Node errorTextNode = XMLUtil.getNode("ErrorText", resultNode);

			String errorText = XMLUtil.toString(errorTextNode.getFirstChild());

			errorText = errorText.replaceAll(
				StringPool.NEW_LINE, StringPool.PIPE);

			throw new SharepointResultException(errorCode, errorText);
		}
	}

}