/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;
import com.liferay.sharepoint.soap.repository.connector.schema.query.Query;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryField;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryOptionsList;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryValue;
import com.liferay.sharepoint.soap.repository.connector.schema.query.operator.EqOperator;
import com.liferay.sharepoint.soap.repository.connector.schema.query.option.FolderQueryOption;
import com.liferay.sharepoint.soap.repository.connector.schema.query.option.ViewAttributesQueryOption;

/**
 * @author Iván Zaera
 */
public final class GetSharepointObjectByIdOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_getSharepointObjectsByQueryOperation = getOperation(
			GetSharepointObjectsByQueryOperation.class);
	}

	public SharepointObject execute(long sharepointObjectId)
		throws SharepointException {

		Query query = new Query(
			new EqOperator(
				new QueryField("ID"),
				new QueryValue(String.valueOf(sharepointObjectId))));
		QueryOptionsList queryOptionsList = new QueryOptionsList(
			new FolderQueryOption(StringPool.BLANK),
			new ViewAttributesQueryOption(true));

		return getSharepointObject(
			_getSharepointObjectsByQueryOperation.execute(
				query, queryOptionsList));
	}

	private GetSharepointObjectsByQueryOperation
		_getSharepointObjectsByQueryOperation;

}