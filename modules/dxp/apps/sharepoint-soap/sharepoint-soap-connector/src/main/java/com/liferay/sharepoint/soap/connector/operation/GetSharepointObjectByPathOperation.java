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
import com.liferay.sharepoint.soap.connector.SharepointObject;
import com.liferay.sharepoint.soap.connector.schema.query.Query;
import com.liferay.sharepoint.soap.connector.schema.query.QueryField;
import com.liferay.sharepoint.soap.connector.schema.query.QueryOptionsList;
import com.liferay.sharepoint.soap.connector.schema.query.QueryValue;
import com.liferay.sharepoint.soap.connector.schema.query.operator.EqOperator;
import com.liferay.sharepoint.soap.connector.schema.query.option.FolderQueryOption;
import com.liferay.sharepoint.soap.connector.schema.query.option.ViewAttributesQueryOption;

/**
 * @author Iván Zaera
 */
public class GetSharepointObjectByPathOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_getSharepointObjectsByQueryOperation = getOperation(
			GetSharepointObjectsByQueryOperation.class);
	}

	public SharepointObject execute(String path) throws SharepointException {
		String fullPath = toFullPath(path);

		Query query = new Query(
			new EqOperator(
				new QueryField("FileRef"),
				new QueryValue(fullPath.substring(1))));

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