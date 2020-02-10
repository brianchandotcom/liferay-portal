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

package com.liferay.sharepoint.soap.connector.operation;

import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.soap.connector.SharepointException;
import com.liferay.sharepoint.soap.connector.SharepointResultException;
import com.liferay.sharepoint.soap.connector.internal.util.RemoteExceptionSharepointExceptionMapper;
import com.liferay.sharepoint.soap.connector.schema.batch.Batch;

import com.microsoft.schemas.sharepoint.soap.UpdateListItemsResponseUpdateListItemsResult;
import com.microsoft.schemas.sharepoint.soap.UpdateListItemsUpdates;

import java.rmi.RemoteException;

import org.apache.axis.message.MessageElement;

import org.w3c.dom.Element;

/**
 * @author Iván Zaera
 */
public final class BatchOperation extends BaseOperation {

	public void execute(Batch batch) throws SharepointException {
		UpdateListItemsUpdates updateListItemsUpdates =
			new UpdateListItemsUpdates();

		Element element = xmlHelper.toElement(batch);

		MessageElement messageElement = new MessageElement(element);

		updateListItemsUpdates.set_any(new MessageElement[] {messageElement});

		UpdateListItemsResponseUpdateListItemsResult
			updateListItemsResponseUpdateListItemsResult = null;

		try {
			updateListItemsResponseUpdateListItemsResult =
				listsSoap.updateListItems(
					sharepointConnectionInfo.getLibraryName(),
					updateListItemsUpdates);
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(remoteException);
		}

		_parseUpdateListItemsResponseUpdateListItemsResult(
			updateListItemsResponseUpdateListItemsResult);
	}

	private void _parseUpdateListItemsResponseUpdateListItemsResult(
			UpdateListItemsResponseUpdateListItemsResult
				updateListItemsResponseUpdateListItemsResult)
		throws SharepointException {

		Element updateListItemsResponseUpdateListItemsResultElement =
			xmlHelper.getElement(updateListItemsResponseUpdateListItemsResult);

		Element resultElement = xmlHelper.getElement(
			"Result", updateListItemsResponseUpdateListItemsResultElement);

		Element errorCodeElement = xmlHelper.getElement(
			"ErrorCode", resultElement);

		String errorCode = errorCodeElement.getTextContent();

		if (!errorCode.equals(SharepointConstants.NUMERIC_STATUS_SUCCESS)) {
			Element errorTextElement = xmlHelper.getElement(
				"ErrorText", resultElement);

			String errorText = errorTextElement.getTextContent();

			errorText = errorText.replaceAll(
				StringPool.NEW_LINE, StringPool.PIPE);

			throw new SharepointResultException(errorCode, errorText);
		}
	}

}