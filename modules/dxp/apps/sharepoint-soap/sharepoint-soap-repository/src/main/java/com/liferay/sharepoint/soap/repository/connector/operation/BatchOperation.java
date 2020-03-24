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

import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointResultException;
import com.liferay.sharepoint.soap.repository.connector.internal.util.RemoteExceptionSharepointExceptionMapper;
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
		UpdateListItemsResponseDocument updateListItemsResponseDocument = null;

		try {
			updateListItemsResponseDocument = listsSoap12Stub.updateListItems(
				getUpdateListItemsDocument(batch));
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}

		processUpdateListItemsResponseDocument(updateListItemsResponseDocument);
	}

	protected UpdateListItemsDocument getUpdateListItemsDocument(Batch batch) {
		UpdateListItemsDocument updateListItemsDocument =
			UpdateListItemsDocument.Factory.newInstance();

		UpdateListItemsDocument.UpdateListItems updateListItems =
			updateListItemsDocument.addNewUpdateListItems();

		updateListItems.setListName(sharepointConnectionInfo.getLibraryName());

		UpdateListItemsDocument.UpdateListItems.Updates updates =
			updateListItems.addNewUpdates();

		Node node = updates.getDomNode();

		for (Node childNode : XMLUtil.toNodes(node.getOwnerDocument(), batch)) {
			node.appendChild(childNode);
		}

		return updateListItemsDocument;
	}

	protected Void processUpdateListItemsResponseDocument(
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

		return null;
	}

}